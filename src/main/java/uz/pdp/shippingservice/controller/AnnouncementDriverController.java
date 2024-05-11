package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverDto;
import uz.pdp.shippingservice.dto.GetByFilter;
import uz.pdp.shippingservice.service.notcomplated.AnnouncementDriverService;


import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/driver")
public class AnnouncementDriverController {

    private final AnnouncementDriverService announcementDriverService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse addDriverAnnouncement(@RequestBody AnnouncementDriverDto announcementDriverDto){
        return announcementDriverService.add(announcementDriverDto);
    }

    @PostMapping("/getAnnouncementDriverByFilter")
    public ApiResponse getByFilter(@RequestBody GetByFilter getByFilter){
        return announcementDriverService.getAnnouncementDriverByFilter(getByFilter);
    }

    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    @GetMapping("/getActiveAnnouncementById/{id}")
    public ApiResponse getDriverAnnouncementByIdAndActiveTrue(@PathVariable("id")UUID id){
        return announcementDriverService.getDriverAnnouncementByIdAndActiveTrue(id);
    }

    @GetMapping("/getbyId/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    public ApiResponse getDriverAnnouncementByIdDeletedFalse(@PathVariable("id")UUID id){
        return announcementDriverService.getDriverAnnouncementByIdDeletedFalse(id);
    }

    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    @GetMapping("/getDriverOwnAnnouncements")
    public ApiResponse getOwnDriverAnnouncements(){
        return announcementDriverService.getDriverOwnAnnouncements();
    }

//    @GetMapping("/getDriverOwnAnnouncementsHistory")
//    public ApiResponse getOwnDriverAnnouncementsHistory(){
//        return announcementDriverService.getDriverOwnAnnouncementsHistory();
//    }


    @DeleteMapping("/deleteDriverAnnouncements/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse deleteDriverAnnouncement(@PathVariable UUID id){
        return announcementDriverService.deleteDriverAnnouncement(id);
    }
}
