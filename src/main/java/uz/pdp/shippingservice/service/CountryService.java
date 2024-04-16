package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Country;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.dto.CountryDto;
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
        countryRepository.save(Country.from(countryDto));
        return new ApiResponse(SUCCESSFULLY, true);
    }
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse editCountry(CountryDto countryDto) {
        if (countryRepository.existsByIdAndName(countryDto.getId(),countryDto.getName())) {
            throw new RecordAlreadyExistException(COUNTRY_ALREADY_EXIST);
        }
        countryRepository.save(new Country(countryDto.getId(), countryDto.getName()));
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCountryList() {
        return new ApiResponse(countryRepository.findAll(), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCountryById(Integer id) {
        return new ApiResponse(countryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(COUNTRY_NOT_FOUND)), true);
    }
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteRegionById(Integer id) {
        countryRepository.deleteById(id);
        return new ApiResponse(DELETED, true);
    }
}
