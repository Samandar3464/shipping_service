package uz.pdp.shippingservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.config.jwtConfig.JwtGenerate;
import uz.pdp.shippingservice.dto.user.*;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.exception.UserException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.dto.request.*;
import uz.pdp.shippingservice.dto.response.TokenResponse;
import uz.pdp.shippingservice.repository.*;

import java.util.random.RandomGenerator;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SmsService smsService;
    private final JwtGenerate jwtGenerate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final LocalDateTimeConverter converter;
    private final AttachmentService attachmentService;
    private final FireBaseMessagingService fireBaseMessagingService;

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse register(UserRegisterDto dto) {
        boolean byPhone = userRepository.existsByPhone(dto.getPhone());
        if (byPhone) {
            throw new UserException(USER_ALREADY_EXIST);
        }
        String code = verificationCodeGenerator().toString();
        String message = "Tasdiqlash kodi: " + code + ". Yo'linggiz bexatar  bo'lsin.";
        smsService.send(dto.getPhone(), message, code);
        System.out.println("code ->" + code);
        UserEntity entity = UserEntity.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRoles(roleRepository.findAllByName("users"));
        userRepository.save(entity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse verify(UserVerifyRequestDto dto) {
        UserEntity userEntity = userRepository.findByPhone(dto.getPhone())
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        if (smsService.findByPhoneAndCheck(dto)) {
            userEntity.setBlocked(false);
            userRepository.save(userEntity);
        }
        return new ApiResponse(USER_VERIFIED_SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse reSendSms(String number) {
        String code = verificationCodeGenerator().toString();
        String message = "Tasdiqlash kodi: " + code;
        smsService.send(number, message, code);
        System.out.println("code ->" + code);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse login(UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginRequestDto.getPhone(), userLoginRequestDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
            return new ApiResponse(new TokenResponse(jwtGenerate.generateAccessToken(userEntity), jwtGenerate.generateRefreshToken(userEntity)), true);
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserEntity user = userRepository.findByPhone(userEntity.getUsername()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        GetMe dto = GetMe.toDto(user);
        if (user.getAvatar() != null){
            dto.setAvatarUrl(attachmentService.getUrl(user.getAvatar()));
        }
        return new ApiResponse(dto, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(UserUpdateDto dto) {
        UserEntity userEntity = userRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        UserEntity.update(dto , userEntity);
        userEntity.setBirthDate(converter.convertOnlyDate(dto.getBirthDate()));
        if (dto.getAvatar() != null){
            Attachment attachment = attachmentService.saveToSystem(dto.getAvatar());
            userEntity.setAvatar(attachment);
        }
        userRepository.save(userEntity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse forgetPassword(String number) {
        userRepository.findByPhone(number).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        String code = verificationCodeGenerator().toString();
        String message = "Tasdiqlash kodi: " + code;
        smsService.send(number, message, code);
        System.out.println("code ->" + code);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changePassword(ForgerPasswordDto dto) {
        UserEntity userEntity = userRepository.findByPhone(dto.getPhone()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(userEntity);
        return new ApiResponse(userEntity, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changePasswordToNew(ChangePasswordDto dto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getOldPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
            userEntity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(userEntity);
            return new ApiResponse(userEntity, true);
        } catch (Exception e) {
            throw new UserException(SOMETHING_WRONG);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getToken(HttpServletRequest request) throws Exception {
        String accessTokenByRefresh = jwtGenerate.checkRefreshTokenValidAndGetAccessToken(request);
        return new ApiResponse("NEW ACCESS TOKEN ", true, new TokenResponse(accessTokenByRefresh));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse addBlockUserByID(Long id) {
        UserEntity userEntity = checkUserExistById(id);
        userEntity.setBlocked(true);
        userRepository.save(userEntity);
//        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(userEntity.getFirebaseToken(), BLOCKED, new HashMap<>());
//        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        return new ApiResponse(BLOCKED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse openToBlockUserByID(Long id) {
        UserEntity userEntity = checkUserExistById(id);
        userEntity.setBlocked(true);
        userRepository.save(userEntity);
//        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(userEntity.getFirebaseToken(), OPEN, new HashMap<>());
//        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        return new ApiResponse(OPEN, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse saveFireBaseToken(FireBaseTokenRegisterDto dto) {
        UserEntity userEntity = checkUserExistByPhone(dto.getPhone());
        userEntity.setFirebaseToken(dto.getFireBaseToken());
        userRepository.save(userEntity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByUserId(Long id) {
        UserEntity userEntity = checkUserExistById(id);
        return new ApiResponse(userEntity, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> all = userRepository.findAll(pageable);
        return new ApiResponse(all, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse removeUserFromContext() {
        UserEntity userEntity = checkUserExistByContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName().equals(userEntity.getUsername())) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public UserEntity checkUserExistByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return userRepository.findByPhone(userEntity.getUsername()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public UserEntity checkUserExistById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
    public UserEntity checkUserExistByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    private Integer verificationCodeGenerator() {
        return RandomGenerator.getDefault().nextInt(1000, 9999);
    }

}


