package uz.pdp.shippingservice.service.notcomplated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

//    private final ChatRepository chatRepository;
//    private final AnnouncementClientRepository announcementClientRepository;
//    private final AnnouncementDriverRepository announcementDriverRepository;
//    private final AnnouncementDriverService announcementDriverService;
//    private final AnnouncementClientService announcementClientService;
//    private final UserService userService;
//    private final FireBaseMessagingService fireBaseMessagingService;
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @Transactional(rollbackFor = {Exception.class})
//    public ApiResponse createNotificationForPassenger(NotificationRequestDto notificationRequestDto) {
//        UserEntity userEntity = userService.checkUserExistByContext();
//        announcementClientService.getByIdAndActiveAndDeletedFalse(notificationRequestDto.getAnnouncementPassengerId());
////        AnnouncementDriver announcementDriver = announcementDriverService.getByUserIdAndActiveAndDeletedFalse(userEntity.getId(), true);
//
////        notificationRequestDto.setAnnouncementDriverId(announcementDriver.getId());
//        HashMap<String, String> data = new HashMap<>();
////        data.put("announcementId", announcementDriver.getId().toString());
//        notificationRequestDto.setDate(data);
//        notificationRequestDto.setTitle(YOU_COME_TO_MESSAGE_FROM_DRIVER);
//        notificationRequestDto.setType(TypeClients.CLIENT);
//        Notification notification = from(notificationRequestDto, userEntity);
//        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(notification.getReceiverToken(), YOU_COME_TO_MESSAGE_FROM_DRIVER, data);
//        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
//        return new ApiResponse(SUCCESSFULLY, true);
//    }
//
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getDriverPostedNotification() {
//        UserEntity userEntity = userService.checkUserExistByContext();
//        List<Notification> notificationList = chatRepository.findAllBySenderIdAndDeletedFalseAndTypeOrderByCreatedTimeDesc(userEntity.getId(), TypeClients.CLIENT);
//
//        List<AnnouncementClient> announcementClientList = new ArrayList<>();
//        notificationList.forEach(obj -> {
//            AnnouncementClient announcementClient = announcementClientRepository.findByIdAndDeletedFalse(obj.getAnnouncementPassengerId()).orElse(null);
//            announcementClientList.add(announcementClient);
//        });
//
////        List<AnnouncementClientResponseList> anonymousList = new ArrayList<>();
////        announcementClientList.forEach(obj -> {
////            if (obj != null) anonymousList.add(AnnouncementClientResponseList.from(obj));
////        });
//        return new ApiResponse(null, true);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse deleteNotification(UUID id) {
//        Notification notification = chatRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
//        notification.setDeleted(true);
//        chatRepository.save(notification);
//        return new ApiResponse(DELETED, true);
//    }
//
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse seeNotificationForPassenger() {
//        UserEntity userEntity = userService.checkUserExistByContext();
//
//        List<Notification> notifications = chatRepository
//                .findAllByReceiverIdAndActiveAndReceivedAndDeletedFalseAndTypeOrderByCreatedTime(userEntity.getId(), true, false, TypeClients.CLIENT);
//
////        List<AnnouncementDriverResponseList> announcementDriverResponseLists = new ArrayList<>();
////        notifications.forEach(obj -> announcementDriverResponseLists.add(AnnouncementDriverResponseList
////                .fromForNotification(announcementDriverService.getByIdAndDeletedFalse(obj.getAnnouncementDriverId()), userService.checkUserExistById(obj.getSenderId()).getUsername())));
//
////        return new ApiResponse(announcementDriverResponseLists, true);
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @Transactional(rollbackFor = {UserNotFoundException.class, AnnouncementNotFoundException.class, RecordNotFoundException.class})
//    public ApiResponse acceptDiverRequest(AcceptRequestDto acceptRequestDto) {
//        UserEntity passenger = userService.checkUserExistByContext();
//        UserEntity driver = userService.checkUserExistById(acceptRequestDto.getSenderId());
//        Notification fromDriverToUser = getNotification(passenger, driver, acceptRequestDto.getAnnouncementClientId());
////        AnnouncementDriver announcementDriver = announcementDriverService.getByUserIdAndActiveAndDeletedFalse(driver.getId(), true);
//        AnnouncementClient announcementClient = announcementClientService.getByIdAndActiveAndDeletedFalse(acceptRequestDto.getAnnouncementClientId());
//        fromDriverToUser.setActive(false);
//        fromDriverToUser.setReceived(true);
//        chatRepository.save(fromDriverToUser);
//        announcementClient.setActive(false);
////        announcementDriver.setActive(true);
//        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(passenger.getFirebaseToken(), PASSENGER_AGREE, new HashMap<>());
//        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
////        announcementDriverRepository.save(announcementDriver);
//        announcementClientRepository.save(announcementClient);
//        return new ApiResponse(YOU_ACCEPTED_REQUEST, true);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getAcceptedNotificationForDriver() {
//        UserEntity receiver = userService.checkUserExistByContext();
//        List<Notification> passengerAccepted = chatRepository.findAllBySenderIdAndReceivedTrueAndTypeOrderByCreatedTime(receiver.getId(), TypeClients.CLIENT);
//        List<AnnouncementClientResponse> allowedAnnouncementResponseForDrivers = new ArrayList<>();
//
//        passengerAccepted.forEach(obj -> {
//            AnnouncementClient announcement = announcementClientRepository.findById(obj.getAnnouncementPassengerId()).orElse(null);
////            UserResponseDto userResponseDto = UserResponseDto.from(userService.checkUserExistById(obj.getReceiverId()));
//            if (announcement != null) {
////                allowedAnnouncementResponseForDrivers.add(AnnouncementClientResponse.from(announcement/*, userResponseDto*/));
//            }
//        });
//        return new ApiResponse(allowedAnnouncementResponseForDrivers, true);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse getAcceptedNotificationForClient() {
//        UserEntity receiver = userService.checkUserExistByContext();
////        List<AnnouncementDriverResponseList> announcementDriverResponseLists = new ArrayList<>();
//        List<Notification> passengerAccepted = chatRepository.findAllByReceiverIdAndReceivedTrueAndTypeOrderByCreatedTime(receiver.getId(), TypeClients.CLIENT);
//
////        passengerAccepted.forEach(obj -> announcementDriverResponseLists.add(AnnouncementDriverResponseList
////                .fromForNotification(announcementDriverService.getById(obj.getAnnouncementDriverId()),
////                        userService.checkUserExistById(obj.getSenderId()).getUsername())));
////        return new ApiResponse(announcementDriverResponseLists, true);
//        return null;
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse changeToRead(UUID notificationId) {
//        Notification notification = chatRepository.findById(notificationId).orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
//        notification.setRead(true);
//        chatRepository.save(notification);
//        return new ApiResponse(SUCCESSFULLY, true);
//    }
//
//    private Notification getNotification(UserEntity userEntity1, UserEntity userEntity2, UUID announcementId) {
//        return chatRepository.findFirstBySenderIdAndReceiverIdAndAnnouncementPassengerIdAndActiveTrueAndReceivedFalseOrderByCreatedTimeDesc(userEntity2.getId(), userEntity1.getId(), announcementId)
//                .orElseThrow(() -> new RecordNotFoundException(NOTIFICATION_NOT_FOUND));
//    }
//
//    private Notification from(NotificationRequestDto notificationRequestDto, UserEntity userEntity) {
//        Notification notification = Notification.from(notificationRequestDto);
//        notification.setSenderId(userEntity.getId());
//        notification.setUserEntity(userEntity);
//        UserEntity receiver = userService.checkUserExistById(notificationRequestDto.getReceiverId());
//        notification.setReceiverToken(receiver.getFirebaseToken());
//        return chatRepository.save(notification);
//    }
}