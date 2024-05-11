package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.base.PageRequestFilter;
import uz.pdp.shippingservice.dto.base.PageableResponse;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.user.DriverInfoResponseDto;
import uz.pdp.shippingservice.dto.user.DriverUpdateDto;
import uz.pdp.shippingservice.dto.user.GetMe;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.user.DriverEntity;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.exception.DriverException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.repository.DriverInfoRepository;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class DriverInfoService {

    private final DriverInfoRepository driverInfoRepository;
    private final UserService userService;
    private final AttachmentService attachmentService;

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse createDriverInfo(DriverUpdateDto dto) {
        try {
            DriverEntity entity = DriverEntity.toEntity(dto);
            UserEntity user = userService.checkUserExistById(dto.getUserId());
            List<Attachment> carPhotos = attachmentService.saveToSystemListFile(dto.getCarPhotos());
            List<Attachment> driverPassportPhotos = attachmentService.saveToSystemListFile(dto.getDriverPassportPhotos());
            List<Attachment> driverLicensePhotos = attachmentService.saveToSystemListFile(dto.getDriverLicensePhotos());
            List<Attachment> carTexPassport = attachmentService.saveToSystemListFile(dto.getCarTexPassport());
            entity.setUser(user);
            entity.setCarPhotos(carPhotos);
            entity.setDriverPassportPhotos(driverPassportPhotos);
            entity.setDriverLicensePhotos(driverLicensePhotos);
            entity.setCarTexPassport(carTexPassport);
            driverInfoRepository.save(entity);
            userService.addUserRole(user, "driver");
        } catch (Exception e) {
            throw new DriverException(CANNOT_SAVE_DRIVER_INFO);
        }
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public ApiResponse getAllDeActiveDriverList(PageRequestFilter requestFilter) {
        PageRequest pageRequest = PageRequest.of(requestFilter.getPageNumber(), requestFilter.getPageSize());
        Page<DriverEntity> pageList = driverInfoRepository.findAllByActiveIsFalseOrderByCreatedAtDesc(pageRequest);
        List<DriverInfoResponseDto> responseDtoList = new ArrayList<>();
        pageList.getContent().forEach(entity -> {
            DriverInfoResponseDto dto = DriverInfoResponseDto.toDto(entity);
            UserEntity user = entity.getUser();
            GetMe getMe = GetMe.toDto(user);
            if (user.getAvatar() != null) {
                getMe.setAvatarUrl(attachmentService.getUrl(user.getAvatar()));
            }
            dto.setMe(getMe);
            dto.setCarPhotos(attachmentService.getUrlList(entity.getCarPhotos()));
            dto.setCarTexPassport(attachmentService.getUrlList(entity.getCarTexPassport()));
            dto.setDriverPassportPhotos(attachmentService.getUrlList(entity.getDriverPassportPhotos()));
            dto.setDriverLicensePhotos(attachmentService.getUrlList(entity.getDriverLicensePhotos()));
            responseDtoList.add(dto);
        });
        PageableResponse<DriverInfoResponseDto> response = new PageableResponse<>(responseDtoList, pageList.getNumberOfElements(), pageList.getTotalPages(), pageList.getTotalElements());
        return new ApiResponse(response, true);
    }

    public ApiResponse getAllDriverList(PageRequestFilter requestFilter) {
        PageRequest pageRequest = PageRequest.of(requestFilter.getPageNumber(), requestFilter.getPageSize());
        Page<DriverEntity> pageList = driverInfoRepository.findAll(pageRequest);
        List<DriverInfoResponseDto> responseDtoList = new ArrayList<>();
        pageList.getContent().forEach(entity -> {
            DriverInfoResponseDto dto = DriverInfoResponseDto.toDto(entity);
            UserEntity user = entity.getUser();
            GetMe getMe = GetMe.toDto(user);
            if (user.getAvatar() != null) {
                getMe.setAvatarUrl(attachmentService.getUrl(user.getAvatar()));
            }
            dto.setMe(getMe);
            dto.setCarPhotos(attachmentService.getUrlList(entity.getCarPhotos()));
            dto.setCarTexPassport(attachmentService.getUrlList(entity.getCarTexPassport()));
            dto.setDriverPassportPhotos(attachmentService.getUrlList(entity.getDriverPassportPhotos()));
            dto.setDriverLicensePhotos(attachmentService.getUrlList(entity.getDriverLicensePhotos()));
            responseDtoList.add(dto);
        });
        PageableResponse<DriverInfoResponseDto> response = new PageableResponse<>(responseDtoList, pageList.getNumberOfElements(), pageList.getTotalPages(), pageList.getTotalElements());
        return new ApiResponse(response, true);
    }


    public ApiResponse activate(Long id) {
        DriverEntity driverEntity = driverInfoRepository.findById(id).orElseThrow(() -> new DriverException(CANNOT_FIND_DRIVER));
        driverEntity.setActive(true);
        driverInfoRepository.save(driverEntity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public ApiResponse deActivate(Long id) {
        DriverEntity driverEntity = driverInfoRepository.findById(id).orElseThrow(() -> new DriverException(CANNOT_FIND_DRIVER));
        driverEntity.setActive(false);
        driverInfoRepository.save(driverEntity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public DriverEntity getByUserId(Long userId){
       return driverInfoRepository.findFirstByUserIdAndActiveTrue(userId).orElseThrow(()-> new RecordNotFoundException(DRIVER_CAR_NOT_FOUND_NOT_ACTIVATED));
    }
}
