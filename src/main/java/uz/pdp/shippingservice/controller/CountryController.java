package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.location.CountryDto;
import uz.pdp.shippingservice.service.CountryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/country")
public class CountryController {

    private final CountryService countryService;

    @PostMapping("/add")
//    @PreAuthorize("hasRole('admin')")
    public ApiResponse addCountry(@RequestBody CountryDto countryDto) {
        return countryService.addCountry(countryDto);
    }

    @PutMapping("/edit")
//    @PreAuthorize("hasRole('admin')")
    public ApiResponse addEdit(@RequestBody CountryDto countryDto) {
        return countryService.editCountry(countryDto);
    }

    @GetMapping("/getCountryList")
    public ApiResponse getCountryList() {
        return countryService.getCountryList();
    }

    @GetMapping("/getActiveCountryList")
    public ApiResponse getActiveCountryList() {
        return countryService.getActiveCountryList();
    }

    @GetMapping("/countryById/{id}")
    public ApiResponse getRegionById(@PathVariable Integer id) {
        return countryService.getCountryById(id);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyRole('admin')")
    public ApiResponse deleteRegionById(@PathVariable Integer id) {
        return countryService.deleteRegionById(id);
    }
}
