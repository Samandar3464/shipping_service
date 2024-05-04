package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.request.AdvertisingRequestDto;
import uz.pdp.shippingservice.service.AdvertisingService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/advertising")
public class AdvertisingController {


    private final AdvertisingService advertisingService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody AdvertisingRequestDto dto) {
        return advertisingService.create(dto);
    }

    @GetMapping("/getActiveList/{id}")
    public ApiResponse getCityList() {
        return advertisingService.getAllActiveAdvertising();
    }

    @GetMapping("/getList")
    public ApiResponse getCityListForAdmin() {
        return advertisingService.getAllAdvertising();
    }

    @PostMapping("/activate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse activate(@RequestParam(name = "id ") Long id, @RequestBody AdvertisingRequestDto dto) {
        return advertisingService.activate(id , dto);
    }

    @GetMapping("/deactivate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse deactivate(@PathVariable Long id) {
        return advertisingService.deActivate(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse deleteCityById(@PathVariable Long id) {
        return advertisingService.deleted(id);
    }
}
