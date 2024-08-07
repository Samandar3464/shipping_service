package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.locations.Country;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.dto.location.CountryDto;
import uz.pdp.shippingservice.repository.CountryRepository;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addCountry(CountryDto countryDto) {
        if (countryRepository.existsByName(countryDto.getName())) {
            throw new RecordAlreadyExistException(COUNTRY_ALREADY_EXIST);
        }
        countryRepository.save(Country.toEntity(countryDto));
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse editCountry(CountryDto countryDto) {
        Country country = countryRepository.findByIdAndName(countryDto.getId(), countryDto.getName()).orElseThrow(() -> new RecordAlreadyExistException(COUNTRY_ALREADY_EXIST));
        country.setName(countryDto.getName() != null ? countryDto.getName() : country.getName());
        country.setActive(countryDto.getActive() != null ? countryDto.getActive() : country.getActive());
        countryRepository.save(country);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCountryList() {
        return new ApiResponse(countryRepository.findAll(), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getActiveCountryList() {
        return new ApiResponse(countryRepository.findAllByActiveTrue(), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCountryById(Integer id) {
        return new ApiResponse(countryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(COUNTRY_NOT_FOUND)), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteRegionById(Integer id) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(COUNTRY_NOT_FOUND));
        country.setActive(false);
        countryRepository.save(country);
        return new ApiResponse(DELETED, true);
    }
}
