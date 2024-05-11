package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverCreateDto;
import uz.pdp.shippingservice.service.notcomplated.AnnouncementDriverService;
import uz.pdp.shippingservice.specifacation.AnnouncementPageRequest;
import uz.pdp.shippingservice.specifacation.driver.AnnouncementDriverSearchCriteria;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/driver_info")
public class AnnouncementDriverController {

    private final AnnouncementDriverService announcementDriverService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse createDriverAnnouncement(@RequestBody AnnouncementDriverCreateDto announcementDriverCreateDto){
        return announcementDriverService.create(announcementDriverCreateDto);
    }

//    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    @GetMapping("/getDriverAnnouncementList")
    public ApiResponse getDriverAnnouncementList(AnnouncementPageRequest page, AnnouncementDriverSearchCriteria searchCriteria){
        return announcementDriverService.getAnnouncementDriverByFilter(page, searchCriteria);
    }

    @GetMapping("/getDriverOwnAnnouncements")
//    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    public ApiResponse getDriverOwnAnnouncements(AnnouncementPageRequest page, Boolean active){
        return announcementDriverService.getDriverOwnAnnouncements(page, active);
    }

//    @DeleteMapping("/deactivateDriverAnnouncements/{id}")
////    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
//    public ApiResponse deleteDriverAnnouncement(@PathVariable UUID id){
//        return announcementDriverService.deactivateDriverAnnouncement(id);
//    }
//
//    @DeleteMapping("/deleteDriverAnnouncements/{id}")
////    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
//    public ApiResponse deleteDriverAnnouncement(@PathVariable UUID id){
//        return announcementDriverService.deleteDriverAnnouncement(id);
//    }
}
