package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.request.CarRegisterRequestDto;
import uz.pdp.shippingservice.model.response.DenyCar;
import uz.pdp.shippingservice.service.CarService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    public ApiResponse addCar(@ModelAttribute @Validated CarRegisterRequestDto carRegisterRequestDto) {
        return carService.addCar(carRegisterRequestDto);
    }
    @GetMapping("/getCar")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse getCar() {
        return carService.getCar();
    }
    @GetMapping("/getCarById/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse getCatById(@PathVariable UUID id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse deleteCarByID(@PathVariable UUID id) {
        return carService.deleteCarByID(id);
    }

//    @PutMapping("/updateCar/{id}")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
//    public ApiResponse updateCar(@PathVariable UUID id, @RequestBody CarRegisterRequestDto carRegisterRequestDto) {
//        return carService.updateCar(id,carRegisterRequestDto);
//    }


    // Admin
    @GetMapping("/dicActiveCars")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse disActiveCarsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        return carService.disActiveCarList(page, size);
    }

    @GetMapping("/activateCar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse activateCar(@PathVariable("id") UUID id) {
        return carService.activateCar(id);
    }

    @GetMapping("/deactivateCar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse deactivateCar(@PathVariable("id") UUID id) {
        return carService.deactivateCar(id);
    }

    @PostMapping("/denyCar")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse denyCar(@RequestBody DenyCar denyCar) {
        return carService.denyCar(denyCar);
    }




}
