package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notification")
public class ChatController {

//    private final NotificationService notificationService;
//
//    @PostMapping("/addNotificationToClient")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
//    public ApiResponse createNotificationToPassenger(@RequestBody NotificationRequestDto notificationRequestDto) {
//        return notificationService.createNotificationForPassenger(notificationRequestDto);
//    }
//
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
//    @GetMapping("/getDriverNotification")
//    public ApiResponse getDriverPostedNotification() {
//        return notificationService.getDriverPostedNotification();
//    }
//
//    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
//    @DeleteMapping("/deleteNotification/{id}")
//    public ApiResponse deleteNotification(@PathVariable UUID id) {
//        return notificationService.deleteNotification(id);
//    }
//
//    @GetMapping("/seeNotificationForPassenger")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
//    public ApiResponse seeNotificationComeToPassenger() {
//        return notificationService.seeNotificationForPassenger();
//    }
//
//    @PostMapping("/acceptDiverRequest")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
//    public ApiResponse joinDiverRequest(@RequestBody AcceptRequestDto acceptRequestDto) {
//        return notificationService.acceptDiverRequest(acceptRequestDto);
//    }
//
//    @GetMapping("/getAcceptedNotificationsForDriver")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
//    public ApiResponse getAcceptedNotificationForDriver() {
//        return notificationService.getAcceptedNotificationForDriver();
//    }
//
//    @GetMapping("/getAcceptedNotificationsForPassenger")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
//    public ApiResponse getAcceptedNotificationForPassenger() {
//        return notificationService.getAcceptedNotificationForClient();
//    }
//
//    @GetMapping("/changeToRead/{id}")
//    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
//    public ApiResponse changeToRead(@PathVariable UUID id) {
//        return notificationService.changeToRead(id);
//    }

}
