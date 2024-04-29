package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.Filter;
import uz.pdp.shippingservice.entity.locations.Country;
import uz.pdp.shippingservice.entity.locations.Region;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.dto.request.RegionRegisterRequestDto;
import uz.pdp.shippingservice.repository.CountryRepository;
import uz.pdp.shippingservice.repository.RegionRepository;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addRegion(RegionRegisterRequestDto dto) {
        if (regionRepository.existsByNameAndCountryId(dto.getName() , dto.getCountryId())) {
            throw new RecordAlreadyExistException(REGION_ALREADY_EXIST);
        }
        Country country = countryRepository.findByIdAndIsActiveTrue(dto.getCountryId())
                .orElseThrow(() -> new RecordNotFoundException(COUNTRY_NOT_FOUND));
        regionRepository.save(Region.toEntity(dto, country));
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionList(Filter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        return new ApiResponse(regionRepository.findAll(pageRequest), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getActiveRegionList(Integer countryId) {
        return new ApiResponse(regionRepository.findAll(), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionById(Integer id) {
        return new ApiResponse(regionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND)), true);
    }

    public ApiResponse deleteRegionById(Integer id) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND));
        region.setIsActive(false);
        regionRepository.save(region);
        return new ApiResponse(DELETED, true);
    }

    public ApiResponse editRegion(RegionRegisterRequestDto dto) {
        if (regionRepository.existsByNameAndCountryId(dto.getName(),dto.getCountryId())) {
            throw new RecordAlreadyExistException(REGION_ALREADY_EXIST);
        }
        Region region = regionRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND));
        Country country = countryRepository.findByIdAndIsActiveTrue(dto.getCountryId())
                .orElseThrow(() -> new RecordNotFoundException(COUNTRY_NOT_FOUND));
        region.setCountry(country);
        region.setIsActive(dto.getActive());
        regionRepository.save(region);
        return new ApiResponse(SUCCESSFULLY, true);
    }
}
