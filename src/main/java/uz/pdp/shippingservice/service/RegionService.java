package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Country;
import uz.pdp.shippingservice.entity.Region;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.model.request.RegionRegisterRequestDto;
import uz.pdp.shippingservice.repository.CountryRepository;
import uz.pdp.shippingservice.repository.RegionRepository;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addRegion(RegionRegisterRequestDto regionRegisterRequestDto) {
        if (regionRepository.existsByName(regionRegisterRequestDto.getName())) {
            throw new RecordAlreadyExistException(REGION_ALREADY_EXIST);
        }
        Country country = countryRepository.findById(regionRegisterRequestDto.getCountryId())
                .orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND));
        regionRepository.save(Region.from(regionRegisterRequestDto, country));
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionList() {
        return new ApiResponse(regionRepository.findAll(), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionById(Integer id) {
        return new ApiResponse(regionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND)), true);
    }

    public ApiResponse deleteRegionById(Integer id) {
        regionRepository.deleteById(id);
        return new ApiResponse(DELETED, true);
    }

    public ApiResponse editRegion(RegionRegisterRequestDto dto) {
        if (regionRepository.existsByIdAndName(dto.getId(), dto.getName())) {
            throw new RecordAlreadyExistException(REGION_ALREADY_EXIST);
        }
        Region region = regionRepository.findById(dto.getId()).get();
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND));
        regionRepository.save(new Region(region.getId(), dto.getName(), country));
        return new ApiResponse(SUCCESSFULLY, true);
    }
}
