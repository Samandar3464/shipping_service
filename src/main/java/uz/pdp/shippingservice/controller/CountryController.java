package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.CountryDto;
import uz.pdp.shippingservice.service.CountryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse addCountry(@RequestBody CountryDto countryDto) {
        return countryService.addCountry(countryDto);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse addEdit(@RequestBody CountryDto countryDto) {
        return countryService.editCountry(countryDto);
    }

    @GetMapping("/getCountryList")
    public ApiResponse getCountryList() {
        return countryService.getCountryList();
    }

    @GetMapping("/countryById/{id}")
    public ApiResponse getRegionById(@PathVariable Integer id) {
        return countryService.getCountryById(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse deleteRegionById(@PathVariable Integer id) {
        return countryService.deleteRegionById(id);
    }
}
