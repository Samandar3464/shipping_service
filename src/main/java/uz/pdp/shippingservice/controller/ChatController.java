package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.NotificationRequestDto;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.enums.TypeClients;
import uz.pdp.shippingservice.service.notcomplated.ChatService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/sendMessage")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse sendMessage(@RequestBody NotificationRequestDto dto) {
        return chatService.sendMessage(dto);
    }

//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    @GetMapping("/getAllMessages")
    public ApiResponse getAllMassage(TypeClients typeClients) {
        return chatService.getAllMassage(typeClients);
    }
    //    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    @GetMapping("/getMessage")
    public ApiResponse getMessage(@RequestParam(name = "announcement_id")UUID announcement_id, TypeClients typeClients) {
        return chatService.getMessage(announcement_id,typeClients);
    }
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
