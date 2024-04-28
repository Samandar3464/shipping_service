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
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.CountMassage;
import uz.pdp.shippingservice.entity.Status;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.UserAlreadyExistException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.dto.request.*;
import uz.pdp.shippingservice.dto.response.NotificationMessageResponse;
import uz.pdp.shippingservice.dto.response.TokenResponse;
import uz.pdp.shippingservice.dto.response.UserResponseDto;
import uz.pdp.shippingservice.dto.response.UserResponseListForAdmin;
import uz.pdp.shippingservice.repository.CountMassageRepository;
import uz.pdp.shippingservice.repository.RoleRepository;
import uz.pdp.shippingservice.repository.StatusRepository;
import uz.pdp.shippingservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.random.RandomGenerator;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AttachmentService attachmentService;
    private final JwtGenerate jwtGenerate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final SmsService service;
    private final CountMassageRepository countMassageRepository;
    private final FireBaseMessagingService fireBaseMessagingService;

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse registerUser(UserRegisterDto userRegisterDto) {
        boolean byPhone = userRepository.existsByUserName(userRegisterDto.getPhone());
        if (byPhone) {
            throw new UserAlreadyExistException(USER_ALREADY_EXIST);
        }
        Integer verificationCode = verificationCodeGenerator();
//        service.sendSms(SmsModel.builder()
//                .mobile_phone(userRegisterDto.getPhone())
//                .message("Tasdiqlash kodi: " + verificationCode + ". Yo'linggiz bexatar  bo'lsin.")
//                .from(4546)
//                .callback_url("http://0000.uz/test.php")
//                .build());
//        countMassageRepository.save(new CountMassage(userRegisterDto.getPhone(), 1, LocalDateTime.now()));
        System.out.println(verificationCode);
        userRepository.save(from(userRegisterDto, verificationCode));
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse verify(UserVerifyRequestDto userVerifyRequestDto) {
        UserEntity userEntity = userRepository.findByUserName(userVerifyRequestDto.getPhone())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        userEntity.setBlocked(true);
        userRepository.save(userEntity);
        return new ApiResponse(USER_VERIFIED_SUCCESSFULLY, true,new TokenResponse(jwtGenerate.generateAccessToken(userEntity), jwtGenerate.generateRefreshToken(userEntity), fromUserToResponse(userEntity)));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse login(UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginRequestDto.getPhone(), userLoginRequestDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
            return new ApiResponse(new TokenResponse(jwtGenerate.generateAccessToken(userEntity), jwtGenerate.generateRefreshToken(userEntity), fromUserToResponse(userEntity)), true);
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse forgetPassword(String number) {
        UserEntity userEntity = userRepository.findByUserName(number).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Integer verificationCode = verificationCodeGenerator();
        System.out.println("Verification code: " + verificationCode);
//        service.sendSms(SmsModel.builder()
//                .mobile_phone(user.getPhone())
//                .message("Tasdiqlash kodi: " + verificationCode +"Yo'lingiz bexatar  bo'lsin")
//                .from(4546)
//                .callback_url("http://0000.uz/test.php")
//                .build());
        countMassageRepository.save(new CountMassage(userEntity.getUsername(), 1, LocalDateTime.now()));
        return new ApiResponse(SUCCESSFULLY, true, userEntity);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getToken(HttpServletRequest request) throws Exception {
        String accessTokenByRefresh = jwtGenerate.checkRefreshTokenValidAndGetAccessToken(request);
        return new ApiResponse("NEW ACCESS TOKEN ", true, new TokenResponse(accessTokenByRefresh));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByUserId(Integer id) {
        UserEntity userEntity = checkUserExistById(id);
        return new ApiResponse(fromUserToResponse(userEntity), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse setStatus(StatusDto statusDto) {
        UserEntity userEntity = checkUserExistById(statusDto.getUserId());
//        Status status = Status.from(statusDto, user.getStatus());
//        statusRepository.save(status);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse addBlockUserByID(Integer id) {
        UserEntity userEntity = checkUserExistById(id);
        Optional<UserEntity> byId = userRepository.findById(id);
        byId.get().setBlocked(false);
        userRepository.save(byId.get());
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(userEntity.getFirebaseToken(), BLOCKED, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse openToBlockUserByID(Integer id) {
        UserEntity userEntity = checkUserExistById(id);
        Optional<UserEntity> byId = userRepository.findById(id);
        byId.get().setBlocked(true);
        userRepository.save(byId.get());
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(userEntity.getFirebaseToken(), OPEN, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse saveFireBaseToken(FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        UserEntity userEntity = checkUserExistById(fireBaseTokenRegisterDto.getUserId());
        userEntity.setFirebaseToken(fireBaseTokenRegisterDto.getFireBaseToken());
        userRepository.save(userEntity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changePassword(String number, String password) {
        UserEntity userEntity = userRepository.findByUserName(number).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
        return new ApiResponse(userEntity, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> all = userRepository.findAll(pageable);
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        all.forEach(obj -> userResponseDtoList.add(fromUserToResponse(obj)));
        return new ApiResponse(new UserResponseListForAdmin(userResponseDtoList, all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse checkUserResponseExistById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserEntity userEntity1 = userRepository.findByUserName(userEntity.getUsername()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return new ApiResponse(fromUserToResponse(userEntity1), true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse reSendSms(String number) {
        Integer integer = verificationCodeGenerator();
        System.out.println(integer);
        service.sendSms(SmsModel.builder()
                .mobile_phone(number)
                .message("Tasdiqlash kodi: " + integer + ". Yo'linggiz bexatar  bo'lsin.")
                .from(4546)
                .callback_url("http://0000.uz/test.php")
                .build());
        countMassageRepository.save(new CountMassage(number, 1, LocalDateTime.now()));
        return new ApiResponse(SUCCESSFULLY, true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse removeUserFromContext() {
        UserEntity userEntity = checkUserExistByContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getName().equals(userEntity.getUsername())) {
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
        return userRepository.findByUserName(userEntity.getUsername()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public UserEntity checkUserExistById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    private UserEntity from(UserRegisterDto userRegisterDto, int verificationCode) {
        userRegisterDto.setStatus(statusRepository.save(Status.builder().stars(5).build()));
        UserEntity userEntity = UserEntity.from(userRegisterDto);
        userEntity.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
//        user.setRoles(List.of(roleRepository.findByName(CARGO_OWNER)));
        userEntity.setAuthroles(List.of(roleRepository.findByName(CLIENT), roleRepository.findByName(DRIVER)));
        return userEntity;
    }

    public UserResponseDto fromUserToResponse(UserEntity userEntity) {
        String photoLink = "https://sb.kaleidousercontent.com/67418/992x558/7632960ff9/people.png";
//        if (user.getProfilePhoto() != null) {
//            Attachment attachment = user.getProfilePhoto();
//            photoLink = attachmentService.attachUploadFolder + attachment.getPath() + "/" + attachment.getNewName() + "." + attachment.getType();
//        }
        UserResponseDto userResponseDto = UserResponseDto.from(userEntity);
        userResponseDto.setProfilePhotoUrl(photoLink);
        return userResponseDto;
    }

    private Integer verificationCodeGenerator() {
        return RandomGenerator.getDefault().nextInt(1000, 9999);
    }

    private void countMassage() {
        List<CountMassage> all = countMassageRepository.findAll();
        if (all.isEmpty()) {
            countMassageRepository.save(CountMassage.builder().count(1).build());
        } else {
            CountMassage countMassage = all.get(0);
            countMassage.setCount(countMassage.getCount() + 1);
            countMassageRepository.save(countMassage);
        }
    }

    public ApiResponse updateUser(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = checkUserExistByContext();
//        user.setFullName(userUpdateDto.getFullName());
//        userEntity.setGender(userUpdateDto.getGender());
        if (userUpdateDto.getProfilePhoto() != null) {
            Attachment attachment = attachmentService.saveToSystem(userUpdateDto.getProfilePhoto());
//            if (user.getProfilePhoto() != null) {
//                attachmentService.deleteNewNameId(user.getProfilePhoto().getNewName() + "." + user.getProfilePhoto().getType());
//            }
//            user.setProfilePhoto(attachment);
        }
//        userEntity.setBirthDate(userUpdateDto.getBrithDay());
        userRepository.save(userEntity);
        return new ApiResponse(SUCCESSFULLY, true);
    }
}


