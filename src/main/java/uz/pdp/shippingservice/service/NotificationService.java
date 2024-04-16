package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.AnnouncementClient;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.Notification;
import uz.pdp.shippingservice.entity.User;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.enums.NotificationType;
import uz.pdp.shippingservice.exception.AnnouncementNotFoundException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.dto.request.AcceptRequestDto;
import uz.pdp.shippingservice.dto.request.NotificationRequestDto;
import uz.pdp.shippingservice.dto.response.*;
import uz.pdp.shippingservice.repository.AnnouncementClientRepository;
import uz.pdp.shippingservice.repository.AnnouncementDriverRepository;
import uz.pdp.shippingservice.repository.NotificationRepository;

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
        User user = userService.checkUserExistByContext();
        announcementClientService.getByIdAndActiveAndDeletedFalse(notificationRequestDto.getAnnouncementPassengerId(), true);
        AnnouncementDriver announcementDriver = announcementDriverService.getByUserIdAndActiveAndDeletedFalse(user.getId(), true);

        notificationRequestDto.setAnnouncementDriverId(announcementDriver.getId());
        HashMap<String, String> data = new HashMap<>();
        data.put("announcementId", announcementDriver.getId().toString());
        notificationRequestDto.setDate(data);
        notificationRequestDto.setTitle(YOU_COME_TO_MESSAGE_FROM_DRIVER);
        notificationRequestDto.setNotificationType(NotificationType.CLIENT);
        Notification notification = from(notificationRequestDto, user);
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(notification.getReceiverToken(), YOU_COME_TO_MESSAGE_FROM_DRIVER, data);
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        return new ApiResponse(SUCCESSFULLY, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getDriverPostedNotification() {
        User user = userService.checkUserExistByContext();
        List<Notification> notificationList = notificationRepository.findAllBySenderIdAndDeletedFalseAndNotificationTypeOrderByCreatedTimeDesc(user.getId(), NotificationType.CLIENT);

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
        User user = userService.checkUserExistByContext();

        List<Notification> notifications = notificationRepository
                .findAllByReceiverIdAndActiveAndReceivedAndDeletedFalseAndNotificationTypeOrderByCreatedTime(user.getId(), true, false, NotificationType.CLIENT);

        List<AnnouncementDriverResponseList> announcementDriverResponseLists = new ArrayList<>();
        notifications.forEach(obj -> announcementDriverResponseLists.add(AnnouncementDriverResponseList
                .fromForNotification(announcementDriverService.getByIdAndDeletedFalse(obj.getAnnouncementDriverId()), userService.checkUserExistById(obj.getSenderId()).getFullName())));

        return new ApiResponse(announcementDriverResponseLists, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = {UserNotFoundException.class, AnnouncementNotFoundException.class, RecordNotFoundException.class})
    public ApiResponse acceptDiverRequest(AcceptRequestDto acceptRequestDto) {
        User passenger = userService.checkUserExistByContext();
        User driver = userService.checkUserExistById(acceptRequestDto.getSenderId());
        Notification fromDriverToUser = getNotification(passenger, driver, acceptRequestDto.getAnnouncementClientId());
        AnnouncementDriver announcementDriver = announcementDriverService.getByUserIdAndActiveAndDeletedFalse(driver.getId(), true);
        AnnouncementClient announcementClient = announcementClientService.getByIdAndActiveAndDeletedFalse(acceptRequestDto.getAnnouncementClientId(), true);
        fromDriverToUser.setActive(false);
        fromDriverToUser.setReceived(true);
        notificationRepository.save(fromDriverToUser);
        announcementClient.setActive(false);
        announcementDriver.setActive(true);
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(passenger.getFireBaseToken(), PASSENGER_AGREE, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
        announcementDriverRepository.save(announcementDriver);
        announcementClientRepository.save(announcementClient);
        return new ApiResponse(YOU_ACCEPTED_REQUEST, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAcceptedNotificationForDriver() {
        User receiver = userService.checkUserExistByContext();
        List<Notification> passengerAccepted = notificationRepository.findAllBySenderIdAndReceivedTrueAndNotificationTypeOrderByCreatedTime(receiver.getId(), NotificationType.CLIENT);
        List<AnnouncementClientResponse> allowedAnnouncementResponseForDrivers = new ArrayList<>();

        passengerAccepted.forEach(obj -> {
            AnnouncementClient announcement = announcementClientRepository.findById(obj.getAnnouncementPassengerId()).orElse(null);
            UserResponseDto userResponseDto = UserResponseDto.from(userService.checkUserExistById(obj.getReceiverId()));
            if (announcement != null) {
                allowedAnnouncementResponseForDrivers.add(AnnouncementClientResponse.from(announcement, userResponseDto));
            }
        });
        return new ApiResponse(allowedAnnouncementResponseForDrivers, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAcceptedNotificationForClient() {
        User receiver = userService.checkUserExistByContext();
        List<AnnouncementDriverResponseList> announcementDriverResponseLists = new ArrayList<>();
        List<Notification> passengerAccepted = notificationRepository.findAllByReceiverIdAndReceivedTrueAndNotificationTypeOrderByCreatedTime(receiver.getId(), NotificationType.CLIENT);

        passengerAccepted.forEach(obj -> announcementDriverResponseLists.add(AnnouncementDriverResponseList
                .fromForNotification(announcementDriverService.getById(obj.getAnnouncementDriverId()),
                        userService.checkUserExistById(obj.getSenderId()).getFullName())));
        return new ApiResponse(announcementDriverResponseLists, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse changeToRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
        notification.setRead(true);
        notificationRepository.save(notification);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private Notification getNotification(User user1, User user2, UUID announcementId) {
        return notificationRepository.findFirstBySenderIdAndReceiverIdAndAnnouncementPassengerIdAndActiveTrueAndReceivedFalseOrderByCreatedTimeDesc(user2.getId(), user1.getId(), announcementId)
                .orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
    }

    private Notification from(NotificationRequestDto notificationRequestDto, User user) {
        Notification notification = Notification.from(notificationRequestDto);
        notification.setSenderId(user.getId());
        notification.setUser(user);
        User receiver = userService.checkUserExistById(notificationRequestDto.getReceiverId());
        notification.setReceiverToken(receiver.getFireBaseToken());
        return notificationRepository.save(notification);
    }
}