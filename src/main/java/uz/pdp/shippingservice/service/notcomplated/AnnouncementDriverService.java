package uz.pdp.shippingservice.service.notcomplated;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.user.DriverEntity;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverCreateDto;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverResponse;
import uz.pdp.shippingservice.repository.*;
import uz.pdp.shippingservice.service.DriverInfoService;
import uz.pdp.shippingservice.service.LocalDateTimeConverter;
import uz.pdp.shippingservice.service.UserService;
import uz.pdp.shippingservice.specifacation.AnnouncementPageRequest;
import uz.pdp.shippingservice.specifacation.driver.AnnouncementDriverCriteriaRepository;
import uz.pdp.shippingservice.specifacation.driver.AnnouncementDriverSearchCriteria;

import static uz.pdp.shippingservice.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class AnnouncementDriverService {

    private final AnnouncementDriverRepository announcementDriverRepository;
    private final AnnouncementDriverCriteriaRepository announcementDriverCriteriaRepository;
    private final DriverInfoService driverInfoService;
    private final UserService userService;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final LocalDateTimeConverter converter;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(AnnouncementDriverCreateDto announcementDriverCreateDto) {
        UserEntity userEntity = userService.checkUserExistByContext();
        DriverEntity driverEntity = driverInfoService.getByUserId(userEntity.getId());
        AnnouncementDriver announcementDriver = toEntity(announcementDriverCreateDto, userEntity , driverEntity);
        announcementDriverRepository.save(announcementDriver);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAnnouncementDriverByFilter(AnnouncementPageRequest page, AnnouncementDriverSearchCriteria searchCriteria) {
        return new ApiResponse(announcementDriverCriteriaRepository.findAllWithFilters(page, searchCriteria), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverOwnAnnouncements(AnnouncementPageRequest page, Boolean active) {
        UserEntity userEntity = userService.checkUserExistByContext();
        Page<AnnouncementDriverResponse> clientOwnAnnouncements = announcementDriverCriteriaRepository.getDrierOwnAnnouncements(page, active, userEntity);
        return new ApiResponse(SUCCESSFULLY, true, clientOwnAnnouncements);
    }


//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getDriverOwnAnnouncements() {
//        UserEntity userEntity = userService.checkUserExistByContext();
////        List<AnnouncementDriver> announcementDrivers = announcementDriverRepository.findAllByUserIdAndDeletedFalseOrderByCreatedTime(userEntity.getId());
//        List<AnnouncementDriverResponseList> announcementDriverResponses = new ArrayList<>();
////        announcementDrivers.forEach(obj -> announcementDriverResponses.add(AnnouncementDriverResponseList.from(obj)));
////        return new ApiResponse(announcementDriverResponses, true);
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse deleteDriverAnnouncement(UUID id) {
//        AnnouncementDriver announcementDriver = announcementDriverRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
//        announcementDriver.setDeleted(true);
//        announcementDriverRepository.save(announcementDriver);
//        return new ApiResponse(DELETED, true);
//    }
//
//    public AnnouncementDriver getByIdAndDeletedFalse(UUID announcementId) {
//        return announcementDriverRepository.findByIdAndDeletedFalse(announcementId)
//                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
//    }
//
//    public AnnouncementDriver getByIdAndActiveAndDeletedFalse(UUID announcementId, boolean active) {
//        return announcementDriverRepository.findByIdAndActiveAndDeletedFalse(announcementId, active)
//                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
//    }
//
//    public AnnouncementDriver getById(UUID announcementId) {
//        return announcementDriverRepository.findById(announcementId)
//                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
//    }


//    private AnnouncementDriverResponse fromAnnouncementDriverResponse(AnnouncementDriver announcementDriver) {
////        List<Attachment> attachmentList = announcementDriver.getCar().getCarPhotos();
//        List<String> photos = new ArrayList<>();
////        attachmentList.forEach(attach -> photos.add(attachmentService.attachUploadFolder + attach.getPath() + "/" + attach.getNewName() + "." + attach.getType()));
//        AnnouncementDriverResponse announcement = AnnouncementDriverResponse.from(announcementDriver);
//        announcement.setCarPhotoPath(photos);
////        announcement.setUserResponseDto(userService.fromUserToResponse(announcementDriver.getUserEntity()));
//        return announcement;
//    }
//
//    public boolean existByUserIdAndActiveTrueAndDeletedFalse(Integer userId) {
////        return announcementDriverRepository.existsByUserIdAndActiveTrueAndDeletedFalse(userId);
//        return false;
//    }
//
//    public List<AnnouncementDriver> getByUserIdAndActiveAndDeletedFalseList(Integer userId, boolean active) {
////        return announcementDriverRepository.findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(userId, active);
//        return null;
//    }

//    public AnnouncementDriver getByUserIdAndActiveAndDeletedFalse(Long userId, boolean active) {
//        return announcementDriverRepository.findByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(userId, active)
//                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
//        return null;
//    }

    private AnnouncementDriver toEntity(AnnouncementDriverCreateDto dto, UserEntity userEntity , DriverEntity driverEntity) {
        AnnouncementDriver announcementDriver = AnnouncementDriver.from(dto);
        announcementDriver.setUserEntity(userEntity);
        announcementDriver.setDriverEntity(driverEntity);
        announcementDriver.setTimeToDrive(converter.convert(dto.getTimeToDrive()));
        announcementDriver.setCountry(countryRepository.getById(dto.getCountryId()));
        announcementDriver.setRegion(dto.getRegionId() == null ? null : regionRepository.getById(dto.getRegionId()));
        announcementDriver.setCity(dto.getCityId() == null ? null : cityRepository.getById(dto.getCityId()));
        return announcementDriver;
    }
}
