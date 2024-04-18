package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.AnnouncementClient;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.AnnouncementAlreadyExistException;
import uz.pdp.shippingservice.exception.AnnouncementNotFoundException;
import uz.pdp.shippingservice.dto.request.AnnouncementClientDto;
import uz.pdp.shippingservice.dto.request.GetByFilter;
import uz.pdp.shippingservice.dto.response.AnnouncementClientResponse;
import uz.pdp.shippingservice.dto.response.AnnouncementClientResponseList;
import uz.pdp.shippingservice.dto.response.UserResponseDto;
import uz.pdp.shippingservice.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class AnnouncementClientService {

    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final UserService userService;
    private final AnnouncementClientRepository announcementClientRepository;
    private final CountryRepository countryRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse add(AnnouncementClientDto announcementClientDto) {
        UserEntity userEntity = userService.checkUserExistByContext();
//        if (announcementDriverRepository.existsByUserIdAndActiveTrueAndDeletedFalse(user.getId())) {
//            throw new AnnouncementAvailable(ANNOUNCEMENT_DRIVER_ALREADY_EXIST);
//        }
//        if (existByUserIdAndActiveTrueAndDeletedFalse(user.getId())) {
//            throw new AnnouncementAlreadyExistException(ANNOUNCEMENT_PASSENGER_ALREADY_EXIST);
//        }
        List<AnnouncementClient> announcementClient1 = getAnnouncementClient(userEntity, true);
        if (announcementClient1.size() > 5) {
            throw new AnnouncementAlreadyExistException(YOU_CAN_ONLY_SET_5_ANNOUNCEMENT);
        }
        AnnouncementClient announcementClient = fromRequest(announcementClientDto, userEntity);
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAnnouncementClientByFilter(GetByFilter getByFilter) {
        List<AnnouncementClient> byFilter = announcementClientRepository
                .findAllByFromRegionIdAndFromCityIdAndTimeToSendBetweenOrderByCreatedTimeDesc(
                        getByFilter.getRegionId(),
                        getByFilter.getCityId(),
                        getByFilter.getTime1(),
                        getByFilter.getTime2());
        List<AnnouncementClientResponseList> passengerResponses = new ArrayList<>();
        byFilter.forEach(obj -> passengerResponses.add(AnnouncementClientResponseList.from(obj)));
        return new ApiResponse(passengerResponses, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverAnnouncementByIdAndActiveTrue(UUID id) {
        AnnouncementClient active = getByIdAndActiveAndDeletedFalse(id, true);
//        UserEntity userEntity = userService.checkUserExistById(active.getUserEntity().getId());
//        UserResponseDto userResponseDto = userService.fromUserToResponse(userEntity);
//        AnnouncementClientResponse passengerResponse = AnnouncementClientResponse.from(active, userResponseDto);
//        return new ApiResponse(passengerResponse, true);
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByIdAndDeleteFalse(UUID id) {
        AnnouncementClient active = announcementClientRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
//        UserEntity userEntity = userService.checkUserExistById(active.getUserEntity().getId());
//        UserResponseDto userResponseDto = userService.fromUserToResponse(userEntity);
//        AnnouncementClientResponse passengerResponse =
//                AnnouncementClientResponse.from(active, userResponseDto);
//        return new ApiResponse(passengerResponse, true);
        return null;
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getClientOwnAnnouncements() {
        UserEntity userEntity = userService.checkUserExistByContext();
//        List<AnnouncementClient> announcementClients = announcementClientRepository.findAllByUserIdAndDeletedFalseOrderByCreatedTime(userEntity.getId());
        List<AnnouncementClientResponseList> anonymousList = new ArrayList<>();
//        announcementClients.forEach(obj -> anonymousList.add(AnnouncementClientResponseList.from(obj)));
        return new ApiResponse(anonymousList, true);
    }
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getClientOwnAnnouncementsHistory() {
//        User user = userService.checkUserExistByContext();
//        List<AnnouncementClient> announcementClients = announcementClientRepository.findAllByUserIdAndDeletedFalseOrderByCreatedTime(user.getId());
//        List<AnnouncementClientResponseList> anonymousList = new ArrayList<>();
//        announcementClients.forEach(obj -> anonymousList.add(AnnouncementClientResponseList.from(obj)));
//        return new ApiResponse(anonymousList, true);
//    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteClientAnnouncement(UUID id) {
        AnnouncementClient announcementClient = announcementClientRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
        announcementClient.setDeleted(false);
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(DELETED, true);
    }

    public AnnouncementClient getByIdAndActiveAndDeletedFalse(UUID announcement_id, boolean active) {
        return announcementClientRepository.findByIdAndActiveAndDeletedFalse(announcement_id, active)
                .orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
    }

    public List<AnnouncementClient> getAnnouncementClient(UserEntity passenger, boolean active) {
//        return announcementClientRepository.findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(passenger.getId(), active);
        return null;
    }

    public AnnouncementClient getByIdAndDeletedFalse(UUID announcement_id) {
        return announcementClientRepository.findByIdAndDeletedFalse(announcement_id)
                .orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
    }

    private AnnouncementClient fromRequest(AnnouncementClientDto announcementClientDto, UserEntity userEntity) {
        AnnouncementClient announcementClient = AnnouncementClient.from(announcementClientDto);
//        announcementClient.setUserEntity(userEntity);
        announcementClient.setFromCountry(countryRepository.getById(announcementClientDto.getFromRegionId()));
        announcementClient.setToCountry(countryRepository.getById(announcementClientDto.getFromRegionId()));
        announcementClient.setFromRegion(announcementClientDto.getFromRegionId() == null? null: regionRepository.getById(announcementClientDto.getFromRegionId()));
        announcementClient.setToRegion(announcementClientDto.getToRegionId() == null? null: regionRepository.getById(announcementClientDto.getToRegionId()));
        announcementClient.setFromCity(announcementClientDto.getFromCityId() == null? null: cityRepository.getById(announcementClientDto.getFromCityId()));
        announcementClient.setToCity(announcementClientDto.getToCityId() == null? null: cityRepository.getById(announcementClientDto.getToCityId()));
        return announcementClient;
    }
}
