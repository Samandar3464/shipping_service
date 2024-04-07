package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.request.CityRequestDto;
import uz.pdp.shippingservice.service.CityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityController {
     private final CityService cityService;

     @PostMapping("/add")
     @PreAuthorize("hasRole('ADMIN')")
     public ApiResponse addRegion(@RequestBody CityRequestDto cityRequestDto) {
          return cityService.saveCity(cityRequestDto);
     }

     @GetMapping("/getList/{id}")
     public ApiResponse getCityList(@PathVariable Integer id){
          return cityService.getCityList(id);
     }

     @GetMapping("/getCityById/{id}")
     public ApiResponse getCityById(@PathVariable Integer id){
          return cityService.getCityById(id);
     }

     @DeleteMapping("/delete/{id}")
     @PreAuthorize("hasAnyRole('ADMIN')")
     public ApiResponse deleteCityById(@PathVariable Integer id) {
          return cityService.deleteCityById(id);
     }
}
