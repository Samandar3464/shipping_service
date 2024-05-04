package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.request.AcceptRequestDto;
import uz.pdp.shippingservice.dto.request.NotificationRequestDto;
import uz.pdp.shippingservice.service.notcomplated.NotificationService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/addNotificationToClient")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse createNotificationToPassenger(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.createNotificationForPassenger(notificationRequestDto);
    }

    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    @GetMapping("/getDriverNotification")
    public ApiResponse getDriverPostedNotification() {
        return notificationService.getDriverPostedNotification();
    }

    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    @DeleteMapping("/deleteNotification/{id}")
    public ApiResponse deleteNotification(@PathVariable UUID id) {
        return notificationService.deleteNotification(id);
    }

    @GetMapping("/seeNotificationForPassenger")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse seeNotificationComeToPassenger() {
        return notificationService.seeNotificationForPassenger();
    }

    @PostMapping("/acceptDiverRequest")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse joinDiverRequest(@RequestBody AcceptRequestDto acceptRequestDto) {
        return notificationService.acceptDiverRequest(acceptRequestDto);
    }

    @GetMapping("/getAcceptedNotificationsForDriver")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse getAcceptedNotificationForDriver() {
        return notificationService.getAcceptedNotificationForDriver();
    }

    @GetMapping("/getAcceptedNotificationsForPassenger")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse getAcceptedNotificationForPassenger() {
        return notificationService.getAcceptedNotificationForClient();
    }

    @GetMapping("/changeToRead/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    public ApiResponse changeToRead(@PathVariable UUID id) {
        return notificationService.changeToRead(id);
    }

}
