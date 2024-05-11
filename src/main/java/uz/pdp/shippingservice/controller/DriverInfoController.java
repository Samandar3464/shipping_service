package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.PageRequestFilter;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.user.DriverUpdateDto;
import uz.pdp.shippingservice.service.DriverInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/announcement_driver")
public class DriverInfoController {

    private final DriverInfoService driverInfoService;

    @PostMapping("/create")
    public ApiResponse create(@ModelAttribute DriverUpdateDto dto){
        return driverInfoService.createDriverInfo(dto);
    }

    @GetMapping("/getAllDeActiveDriverList")
    public ApiResponse getAllDeActiveDriverList(PageRequestFilter dto){
        return driverInfoService.getAllDeActiveDriverList(dto);
    }

    @GetMapping("/getAllDriverList")
    public ApiResponse activate(PageRequestFilter dto){
        return driverInfoService.getAllDriverList(dto);
    }


    @GetMapping("/activate/{id}")
    public ApiResponse activate(@PathVariable Long id){
        return driverInfoService.activate(id);
    }


    @GetMapping("/deActivate/{id}")
    public ApiResponse deActivate(@PathVariable Long id){
        return driverInfoService.deActivate(id);
    }



}
