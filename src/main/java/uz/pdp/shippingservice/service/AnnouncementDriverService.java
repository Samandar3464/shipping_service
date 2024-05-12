package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.user.DriverEntity;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverCreateDto;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverResponse;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.repository.*;
import uz.pdp.shippingservice.specifacation.AnnouncementPageRequest;
import uz.pdp.shippingservice.specifacation.driver.AnnouncementDriverCriteriaRepository;
import uz.pdp.shippingservice.specifacation.driver.AnnouncementDriverSearchCriteria;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        AnnouncementDriver announcementDriver = toEntity(announcementDriverCreateDto, userEntity, driverEntity);
        announcementDriverRepository.save(announcementDriver);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAnnouncementDriverByFilter(AnnouncementPageRequest page, AnnouncementDriverSearchCriteria searchCriteria) {
        return new ApiResponse(announcementDriverCriteriaRepository.findAllWithFilters(page, searchCriteria), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverOwnAnnouncements(AnnouncementPageRequest page, @RequestParam(name = "active", defaultValue = "true") Boolean active) {
        UserEntity userEntity = userService.checkUserExistByContext();
        Page<AnnouncementDriverResponse> clientOwnAnnouncements = announcementDriverCriteriaRepository.getDrierOwnAnnouncements(page, active, userEntity);
        return new ApiResponse(SUCCESSFULLY, true, clientOwnAnnouncements);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteDriverAnnouncement(UUID id) {
        AnnouncementDriver announcementDriver = announcementDriverRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
        announcementDriver.setDeleted(true);
        announcementDriver.setActive(false);
        announcementDriverRepository.save(announcementDriver);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deactivateDriverAnnouncement(UUID id) {
        AnnouncementDriver announcementDriver = announcementDriverRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND));
        announcementDriver.setActive(false);
        announcementDriverRepository.save(announcementDriver);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deactivateAllDriverAnnouncement() {
        LocalDateTime now = LocalDateTime.now();
        List<AnnouncementDriver> list = announcementDriverRepository.findAllByActiveTrueAndDeletedFalseAndTimeToDriveIsBefore(now);
        list.forEach(announcementDriver -> {
            announcementDriver.setActive(false);
            announcementDriverRepository.save(announcementDriver);
        });
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private AnnouncementDriver toEntity(AnnouncementDriverCreateDto dto, UserEntity userEntity, DriverEntity driverEntity) {
        AnnouncementDriver announcementDriver = AnnouncementDriver.toEntity(dto);
        announcementDriver.setUserEntity(userEntity);
        announcementDriver.setDriverEntity(driverEntity);
        announcementDriver.setTimeToDrive(converter.convert(dto.getTimeToDrive()));
        announcementDriver.setCountry(countryRepository.getById(dto.getCountryId()));
        announcementDriver.setRegion(dto.getRegionId() == null ? null : regionRepository.getById(dto.getRegionId()));
        announcementDriver.setCity(dto.getCityId() == null ? null : cityRepository.getById(dto.getCityId()));
        return announcementDriver;
    }
}
