package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientCreateDto;
import uz.pdp.shippingservice.service.AnnouncementClientService;
import uz.pdp.shippingservice.specifacation.client.AnnouncementClientSearchCriteria;
import uz.pdp.shippingservice.specifacation.client.AnnouncementPageRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client-announcements")
public class AnnouncementClientController {

    private final AnnouncementClientService announcementClientService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse createClientAnnouncement(@RequestBody AnnouncementClientCreateDto announcementClientCreateDto) {
        return announcementClientService.create(announcementClientCreateDto);
    }

    @GetMapping("/getClientAnnouncementsList")
    public ApiResponse getAnnouncementClientByFilter(AnnouncementPageRequest page, AnnouncementClientSearchCriteria searchCriteria) {
        return announcementClientService.getAnnouncementClientByFilter(page, searchCriteria);
    }

    @GetMapping("/getClientOwnAnnouncements")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse getClientAnnouncements(AnnouncementPageRequest page, @RequestParam(name = "active", defaultValue = "true") Boolean active) {
        return announcementClientService.getClientOwnAnnouncements(page, active);
    }

    @DeleteMapping("/deleteClientAnnouncements/{id}")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse deleteClientAnnouncement(@PathVariable UUID id) {
        return announcementClientService.deleteClientAnnouncement(id);
    }

    @DeleteMapping("/deactivateClientAnnouncements/{id}")
//    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse deactivateClientAnnouncements(@PathVariable UUID id) {
        return announcementClientService.deactivateClientAnnouncement(id);
    }
}
