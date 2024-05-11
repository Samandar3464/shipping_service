package uz.pdp.shippingservice.service.notcomplated;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientResponse;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientResponseList;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverResponseList;
import uz.pdp.shippingservice.dto.NotificationMessageResponse;
import uz.pdp.shippingservice.entity.AnnouncementClient;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.Notification;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.enums.Type;
import uz.pdp.shippingservice.exception.AnnouncementNotFoundException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.dto.AcceptRequestDto;
import uz.pdp.shippingservice.dto.NotificationRequestDto;
import uz.pdp.shippingservice.repository.AnnouncementClientRepository;
import uz.pdp.shippingservice.repository.AnnouncementDriverRepository;
import uz.pdp.shippingservice.repository.NotificationRepository;
import uz.pdp.shippingservice.service.FireBaseMessagingService;
import uz.pdp.shippingservice.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AnnouncementClientRepository announcementClientRepository;
    private final AnnouncementDriverRepository announcementDriverRepository;
    private final AnnouncementDriverService announcementDriverService;
    private final AnnouncementClientService announcementClientService;
    private final UserService userService;
    private final FireBaseMessagingService fireBaseMessagingService;

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse createNotificationForPassenger(NotificationRequestDto notificationRequestDto) {
        UserEntity userEntity = userService.checkUserExistByContext();
        announcementClientService.getByIdAndActiveAndDeletedFalse(notificationRequestDto.getAnnouncementPassengerId(), true);
        AnnouncementDriver announcementDriver = announcementDriverService.getByUserIdAndActiveAndDeletedFalse(userEntity.getId(), true);

        notificationRequestDto.setAnnouncementDriverId(announcementDriver.getId());
        HashMap<String, String> data = new HashMap<>();
        data.put("announcementId", announcementDriver.getId().toString());
        notificationRequestDto.setDate(data);
        notificationRequestDto.setTitle(YOU_COME_TO_MESSAGE_FROM_DRIVER);
        notificationRequestDto.setType(Type.CLIENT);
        Notification notification = from(notificationRequestDto, userEntity);
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(notification.getReceiverToken(), YOU_COME_TO_MESSAGE_FROM_DRIVER, data);
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverPostedNotification() {
        UserEntity userEntity = userService.checkUserExistByContext();
        List<Notification> notificationList = notificationRepository.findAllBySenderIdAndDeletedFalseAndTypeOrderByCreatedTimeDesc(userEntity.getId(), Type.CLIENT);

        List<AnnouncementClient> announcementClientList = new ArrayList<>();
        notificationList.forEach(obj -> {
            AnnouncementClient announcementClient = announcementClientRepository.findByIdAndDeletedFalse(obj.getAnnouncementPassengerId()).orElse(null);
            announcementClientList.add(announcementClient);
        });

        List<AnnouncementClientResponseList> anonymousList = new ArrayList<>();
        announcementClientList.forEach(obj -> {
            if (obj != null) anonymousList.add(AnnouncementClientResponseList.from(obj));
        });
        return new ApiResponse(anonymousList, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteNotification(UUID id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
        notification.setDeleted(true);
        notificationRepository.save(notification);
        return new ApiResponse(DELETED, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse seeNotificationForPassenger() {
        UserEntity userEntity = userService.checkUserExistByContext();

        List<Notification> notifications = notificationRepository
                .findAllByReceiverIdAndActiveAndReceivedAndDeletedFalseAndTypeOrderByCreatedTime(userEntity.getId(), true, false, Type.CLIENT);

        List<AnnouncementDriverResponseList> announcementDriverResponseLists = new ArrayList<>();
        notifications.forEach(obj -> announcementDriverResponseLists.add(AnnouncementDriverResponseList
                .fromForNotification(announcementDriverService.getByIdAndDeletedFalse(obj.getAnnouncementDriverId()), userService.checkUserExistById(obj.getSenderId()).getUsername())));

        return new ApiResponse(announcementDriverResponseLists, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {UserNotFoundException.class, AnnouncementNotFoundException.class, RecordNotFoundException.class})
    public ApiResponse acceptDiverRequest(AcceptRequestDto acceptRequestDto) {
        UserEntity passenger = userService.checkUserExistByContext();
        UserEntity driver = userService.checkUserExistById(acceptRequestDto.getSenderId());
        Notification fromDriverToUser = getNotification(passenger, driver, acceptRequestDto.getAnnouncementClientId());
        AnnouncementDriver announcementDriver = announcementDriverService.getByUserIdAndActiveAndDeletedFalse(driver.getId(), true);
        AnnouncementClient announcementClient = announcementClientService.getByIdAndActiveAndDeletedFalse(acceptRequestDto.getAnnouncementClientId(), true);
        fromDriverToUser.setActive(false);
        fromDriverToUser.setReceived(true);
        notificationRepository.save(fromDriverToUser);
        announcementClient.setActive(false);
        announcementDriver.setActive(true);
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(passenger.getFirebaseToken(), PASSENGER_AGREE, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        announcementDriverRepository.save(announcementDriver);
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(YOU_ACCEPTED_REQUEST, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAcceptedNotificationForDriver() {
        UserEntity receiver = userService.checkUserExistByContext();
        List<Notification> passengerAccepted = notificationRepository.findAllBySenderIdAndReceivedTrueAndTypeOrderByCreatedTime(receiver.getId(), Type.CLIENT);
        List<AnnouncementClientResponse> allowedAnnouncementResponseForDrivers = new ArrayList<>();

        passengerAccepted.forEach(obj -> {
            AnnouncementClient announcement = announcementClientRepository.findById(obj.getAnnouncementPassengerId()).orElse(null);
//            UserResponseDto userResponseDto = UserResponseDto.from(userService.checkUserExistById(obj.getReceiverId()));
            if (announcement != null) {
                allowedAnnouncementResponseForDrivers.add(AnnouncementClientResponse.from(announcement/*, userResponseDto*/));
            }
        });
        return new ApiResponse(allowedAnnouncementResponseForDrivers, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAcceptedNotificationForClient() {
        UserEntity receiver = userService.checkUserExistByContext();
        List<AnnouncementDriverResponseList> announcementDriverResponseLists = new ArrayList<>();
        List<Notification> passengerAccepted = notificationRepository.findAllByReceiverIdAndReceivedTrueAndTypeOrderByCreatedTime(receiver.getId(), Type.CLIENT);

        passengerAccepted.forEach(obj -> announcementDriverResponseLists.add(AnnouncementDriverResponseList
                .fromForNotification(announcementDriverService.getById(obj.getAnnouncementDriverId()),
                        userService.checkUserExistById(obj.getSenderId()).getUsername())));
        return new ApiResponse(announcementDriverResponseLists, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changeToRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
        notification.setRead(true);
        notificationRepository.save(notification);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private Notification getNotification(UserEntity userEntity1, UserEntity userEntity2, UUID announcementId) {
        return notificationRepository.findFirstBySenderIdAndReceiverIdAndAnnouncementPassengerIdAndActiveTrueAndReceivedFalseOrderByCreatedTimeDesc(userEntity2.getId(), userEntity1.getId(), announcementId)
                .orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
    }

    private Notification from(NotificationRequestDto notificationRequestDto, UserEntity userEntity) {
        Notification notification = Notification.from(notificationRequestDto);
        notification.setSenderId(userEntity.getId());
        notification.setUserEntity(userEntity);
        UserEntity receiver = userService.checkUserExistById(notificationRequestDto.getReceiverId());
        notification.setReceiverToken(receiver.getFirebaseToken());
        return notificationRepository.save(notification);
    }
}