package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientDto;
import uz.pdp.shippingservice.dto.GetByFilter;
import uz.pdp.shippingservice.service.notcomplated.AnnouncementClientService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client-announcements")
public class AnnouncementClientController {

    private final AnnouncementClientService announcementClientService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse createClientAnnouncement(@RequestBody AnnouncementClientDto announcementClientDto) {
        return announcementClientService.create(announcementClientDto);
    }

    @PostMapping("/getAnnouncementClientByFilter")
    public ApiResponse getAnnouncementClientByFilter(@RequestBody GetByFilter getByFilter) {
        return announcementClientService.getAnnouncementClientByFilter(getByFilter);
    }

    @GetMapping("/getActiveAnnouncementById/{id}")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse getClientById(@PathVariable("id") UUID id) {
        return announcementClientService.getDriverAnnouncementByIdAndActiveTrue(id);
    }

    @GetMapping("/getById/{id}")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse getById(@PathVariable("id") UUID id) {
        return announcementClientService.getByIdAndDeleteFalse(id);
    }

    @GetMapping("/getClientOwnAnnouncements")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse getClientAnnouncements() {
        return announcementClientService.getClientOwnAnnouncements();
    }

//    @GetMapping("/getOwnClientAnnouncementsHistory")
////    @PreAuthorize("hasAnyRole(''CLIENT','ADMIN')")
//    public ApiResponse getClientAnnouncementsHistory() {
//        return announcementClientService.getClientOwnAnnouncementsHistory();
//    }


    @DeleteMapping("/deleteClientAnnouncements/{id}")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse deleteClientAnnouncement(@PathVariable UUID id) {
        return announcementClientService.deleteClientAnnouncement(id);
    }
}
