package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientResponse;
import uz.pdp.shippingservice.entity.AnnouncementClient;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.exception.AnnouncementAlreadyExistException;
import uz.pdp.shippingservice.exception.AnnouncementNotFoundException;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientCreateDto;
import uz.pdp.shippingservice.repository.*;
import uz.pdp.shippingservice.specifacation.client.AnnouncementClientCriteriaRepository;
import uz.pdp.shippingservice.specifacation.client.AnnouncementClientSearchCriteria;
import uz.pdp.shippingservice.specifacation.AnnouncementPageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class AnnouncementClientService {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final LocalDateTimeConverter converter;
    private final AttachmentService attachmentService;
    private final AnnouncementClientCriteriaRepository announcementClientCriteriaRepository;

    private final UserService userService;
    private final AnnouncementClientRepository announcementClientRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(AnnouncementClientCreateDto dto) {
        UserEntity userEntity = userService.checkUserExistByContext();
        List<AnnouncementClient> announcements = getAnnouncementClient(userEntity);
        if (announcements.size() > 5) {
            throw new AnnouncementAlreadyExistException(YOU_CAN_ONLY_SET_5_ANNOUNCEMENT);
        }
        AnnouncementClient announcementClient = toEntity(dto, userEntity);
        if (dto.getPhotos() != null) {
            List<Attachment> attachments = attachmentService.saveToSystemListFile(dto.getPhotos());
            announcementClient.setPhotos(attachments);
        }
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAnnouncementClientByFilter(AnnouncementPageRequest page, AnnouncementClientSearchCriteria searchCriteria) {
        return new ApiResponse(announcementClientCriteriaRepository.findAllWithFilters(page, searchCriteria), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getClientOwnAnnouncements(AnnouncementPageRequest page, Boolean active) {
        UserEntity userEntity = userService.checkUserExistByContext();
        Page<AnnouncementClientResponse> clientOwnAnnouncements = announcementClientCriteriaRepository.getClientOwnAnnouncements(page, active, userEntity);
        return new ApiResponse(SUCCESSFULLY, true, clientOwnAnnouncements);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteClientAnnouncement(UUID id) {
        AnnouncementClient announcementClient = announcementClientRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
        announcementClient.setActive(false);
        announcementClient.setDeleted(true);
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(DELETED, true);
    }

    public AnnouncementClient getByIdAndActiveAndDeletedFalse(UUID announcement_id) {
        return announcementClientRepository.findByIdAndActiveTrueAndDeletedFalse(announcement_id)
                .orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deactivateClientAnnouncement(UUID id) {
        AnnouncementClient announcementClient = announcementClientRepository.findById(id).orElseThrow(() -> new AnnouncementNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND));
        announcementClient.setActive(false);
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deactivateAllOldClientAnnouncement() {
        List<AnnouncementClient> list = announcementClientRepository.findAllByDeletedFalseAndActiveTrueAndTimeToSendBefore(LocalDateTime.now());
        list.forEach(announcementClient -> {
            announcementClient.setActive(true);
            announcementClientRepository.save(announcementClient);
        });
        return new ApiResponse(SUCCESSFULLY, true);
    }


    public List<AnnouncementClient> getAnnouncementClient(UserEntity passenger) {
        return announcementClientRepository.findAllByCreatedByIdAndActiveTrueAndDeletedFalse(passenger.getId());
    }

    private AnnouncementClient toEntity(AnnouncementClientCreateDto announcementClientCreateDto, UserEntity userEntity) {
        AnnouncementClient announcementClient = AnnouncementClient.toEntity(announcementClientCreateDto);
        announcementClient.setTimeToSend(converter.convert(announcementClientCreateDto.getTimeToSend()));
        announcementClient.setCreatedBy(userEntity);
        announcementClient.setFromCountry(countryRepository.getById(announcementClientCreateDto.getFromRegionId()));
        announcementClient.setToCountry(countryRepository.getById(announcementClientCreateDto.getFromRegionId()));
        announcementClient.setFromRegion(announcementClientCreateDto.getFromRegionId() == null ? null : regionRepository.getById(announcementClientCreateDto.getFromRegionId()));
        announcementClient.setToRegion(announcementClientCreateDto.getToRegionId() == null ? null : regionRepository.getById(announcementClientCreateDto.getToRegionId()));
        announcementClient.setFromCity(announcementClientCreateDto.getFromCityId() == null ? null : cityRepository.getById(announcementClientCreateDto.getFromCityId()));
        announcementClient.setToCity(announcementClientCreateDto.getToCityId() == null ? null : cityRepository.getById(announcementClientCreateDto.getToCityId()));
        return announcementClient;
    }
}
