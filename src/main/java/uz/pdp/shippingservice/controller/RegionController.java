package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.PageRequestFilter;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.location.RegionRegisterRequestDto;
import uz.pdp.shippingservice.service.RegionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/region")
public class RegionController {

     private final RegionService regionService;
     @PostMapping("/add")
//     @PreAuthorize("hasRole('ADMIN')")
     public ApiResponse addRegion(@RequestBody RegionRegisterRequestDto regionRegisterRequestDto) {
          return regionService.addRegion(regionRegisterRequestDto);
     }

     @PutMapping("/edit")
//     @PreAuthorize("hasRole('ADMIN')")
     public ApiResponse editRegion(@RequestBody RegionRegisterRequestDto regionRegisterRequestDto) {
          return regionService.editRegion(regionRegisterRequestDto);
     }

     @GetMapping("/getRegionList")
     public ApiResponse getRegionList(PageRequestFilter pageRequestFilter) {
          return regionService.getRegionList(pageRequestFilter);
     }

     @GetMapping("/getRegionListByCountyId")
     public ApiResponse getRegionListByCountyId(@RequestParam (name = "id") Integer id) {
          return regionService.getActiveRegionList(id);
     }

     @GetMapping("/regionById/{id}")
     public ApiResponse getRegionById(@PathVariable Integer id){
          return regionService.getRegionById(id);
     }

     @DeleteMapping("/delete/{id}")
//     @PreAuthorize("hasAnyRole('ADMIN')")
     public ApiResponse deleteRegionById(@PathVariable Integer id) {
          return regionService.deleteRegionById(id);
     }
}
