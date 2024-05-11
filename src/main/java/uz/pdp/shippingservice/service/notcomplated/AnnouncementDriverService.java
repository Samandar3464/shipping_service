package uz.pdp.shippingservice.service.notcomplated;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.exception.AnnouncementNotFoundException;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverDto;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverResponse;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverResponseList;
import uz.pdp.shippingservice.repository.AnnouncementDriverRepository;
import uz.pdp.shippingservice.repository.CityRepository;
import uz.pdp.shippingservice.repository.CountryRepository;
import uz.pdp.shippingservice.repository.RegionRepository;
import uz.pdp.shippingservice.service.AttachmentService;
import uz.pdp.shippingservice.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class AnnouncementDriverService {

    private final AnnouncementDriverRepository announcementDriverRepository;
//    private final CarService carService;
    private final RegionRepository regionRepository;
    private final UserService userService;
    private final AttachmentService attachmentService;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse add(AnnouncementDriverDto announcementDriverDto) {
        UserEntity userEntity = userService.checkUserExistByContext();
//        Car car = carService.getCarByUserId(userEntity.getId());
//        if (announcementPassengerService.existByUserIdAndActiveTrueAndDeletedFalse(user.getId())){
//            throw new AnnouncementAlreadyExistException(ANNOUNCEMENT_PASSENGER_ALREADY_EXIST);
//        }
//        if (existByUserIdAndActiveTrueAndDeletedFalse(userEntity.getId())) {
//            throw new AnnouncementAlreadyExistException(ANNOUNCEMENT_DRIVER_ALREADY_EXIST);
//        }
        AnnouncementDriver announcementDriver = fromAnnouncementDriver(announcementDriverDto, userEntity/*, car*/);
        announcementDriverRepository.save(announcementDriver);
        return new ApiResponse(SUCCESSFULLY, true);
    }

//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getAnnouncementDriverByFilter(GetByFilter getByFilter) {
//        List<AnnouncementDriver> driverList = announcementDriverRepository
//                .findAllByCountryIdOrderByCreatedTimeDesc(getByFilter.getCountryId());
//        List<AnnouncementDriverResponseList> announcementDrivers = new ArrayList<>();
//        driverList.forEach(announcementDriver -> announcementDrivers.add(AnnouncementDriverResponseList.from(announcementDriver)));
//        return new ApiResponse(announcementDrivers, true);
//    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverAnnouncementByIdAndActiveTrue(UUID id) {
        AnnouncementDriver announcementDriver = getByIdAndActiveAndDeletedFalse(id, true);
        return new ApiResponse(fromAnnouncementDriverResponse(announcementDriver), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverAnnouncementByIdDeletedFalse(UUID id) {
        AnnouncementDriver announcementDriver = getByIdAndDeletedFalse(id);
        return new ApiResponse(fromAnnouncementDriverResponse(announcementDriver), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverOwnAnnouncements() {
        UserEntity userEntity = userService.checkUserExistByContext();
//        List<AnnouncementDriver> announcementDrivers = announcementDriverRepository.findAllByUserIdAndDeletedFalseOrderByCreatedTime(userEntity.getId());
        List<AnnouncementDriverResponseList> announcementDriverResponses = new ArrayList<>();
//        announcementDrivers.forEach(obj -> announcementDriverResponses.add(AnnouncementDriverResponseList.from(obj)));
//        return new ApiResponse(announcementDriverResponses, true);
        return null;
    }

//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getDriverOwnAnnouncementsHistory() {
//        User user = userService.checkUserExistByContext();
//        List<AnnouncementDriver> announcementDrivers = announcementDriverRepository.findAllByUserIdAndDeletedFalseOrderByCreatedTime(user.getId());
//        List<AnnouncementDriverResponseList> announcementDriverResponses = new ArrayList<>();
//        announcementDrivers.forEach(obj -> announcementDriverResponses.add(AnnouncementDriverResponseList.from(obj)));
//        return new ApiResponse(announcementDriverResponses, true);
//    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteDriverAnnouncement(UUID id) {
        AnnouncementDriver announcementDriver = announcementDriverRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
        announcementDriver.setDeleted(true);
        announcementDriverRepository.save(announcementDriver);
        return new ApiResponse(DELETED, true);
    }

    public AnnouncementDriver getByIdAndDeletedFalse(UUID announcementId) {
        return announcementDriverRepository.findByIdAndDeletedFalse(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
    }

    public AnnouncementDriver getByIdAndActiveAndDeletedFalse(UUID announcementId, boolean active) {
        return announcementDriverRepository.findByIdAndActiveAndDeletedFalse(announcementId, active)
                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
    }

    public AnnouncementDriver getById(UUID announcementId) {
        return announcementDriverRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
    }

    private AnnouncementDriver fromAnnouncementDriver(AnnouncementDriverDto announcement, UserEntity userEntity/*, Car car*/) {
        AnnouncementDriver announcementDriver = AnnouncementDriver.from(announcement);
//        announcementDriver.setCar(car);
        announcementDriver.setUserEntity(userEntity);
        announcementDriver.setCountry(countryRepository.getById(announcement.getCountryId()));
        announcementDriver.setRegion(announcement.getRegionId() == null ? null : regionRepository.getById(announcement.getRegionId()));
        announcementDriver.setCity(announcement.getCityId() == null ? null : cityRepository.getById(announcement.getCityId()));
        return announcementDriver;
    }

    private AnnouncementDriverResponse fromAnnouncementDriverResponse(AnnouncementDriver announcementDriver) {
//        List<Attachment> attachmentList = announcementDriver.getCar().getCarPhotos();
        List<String> photos = new ArrayList<>();
//        attachmentList.forEach(attach -> photos.add(attachmentService.attachUploadFolder + attach.getPath() + "/" + attach.getNewName() + "." + attach.getType()));
        AnnouncementDriverResponse announcement = AnnouncementDriverResponse.from(announcementDriver);
        announcement.setCarPhotoPath(photos);
//        announcement.setUserResponseDto(userService.fromUserToResponse(announcementDriver.getUserEntity()));
        return announcement;
    }

    public boolean existByUserIdAndActiveTrueAndDeletedFalse(Integer userId) {
//        return announcementDriverRepository.existsByUserIdAndActiveTrueAndDeletedFalse(userId);
        return false;
    }

    public List<AnnouncementDriver> getByUserIdAndActiveAndDeletedFalseList(Integer userId, boolean active) {
//        return announcementDriverRepository.findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(userId, active);
        return null;
    }

    public AnnouncementDriver getByUserIdAndActiveAndDeletedFalse(Long userId, boolean active) {
//        return announcementDriverRepository.findByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(userId, active)
//                .orElseThrow(() -> new AnnouncementNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
        return null;
    }
}
