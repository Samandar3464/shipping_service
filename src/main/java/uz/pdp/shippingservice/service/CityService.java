package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.base.PageRequestFilter;
import uz.pdp.shippingservice.entity.locations.City;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.dto.location.CityRequestDto;
import uz.pdp.shippingservice.repository.CityRepository;
import uz.pdp.shippingservice.repository.RegionRepository;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class CityService {

     private final CityRepository cityRepository;

     private final RegionRepository regionRepository;

     @ResponseStatus(HttpStatus.CREATED)
     public ApiResponse saveCity(CityRequestDto cityRequestDto) {
          if (cityRepository.existsByNameAndRegionId(cityRequestDto.getName(),cityRequestDto.getRegionId())) {
               throw new RecordAlreadyExistException(CITY_ALREADY_EXIST);
          }
          City city = City.toEntity(cityRequestDto, regionRepository.findById(cityRequestDto.getRegionId()).orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND)));
          City save = cityRepository.save(city);
          return new ApiResponse(SUCCESSFULLY, true,save);
     }
     @ResponseStatus(HttpStatus.OK)
     public ApiResponse getCityList(Integer id) {
          return new ApiResponse(cityRepository.findAllByRegionIdAndActiveTrue(id),true);
     }

     @ResponseStatus(HttpStatus.OK)
     public ApiResponse getCityListForAdmin(Integer id , PageRequestFilter request) {
          PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
          if (id == null){
               return new ApiResponse(cityRepository.findAll(pageRequest),true);
          }
          return new ApiResponse(cityRepository.findAllByRegionId(id , pageRequest),true);
     }

     @ResponseStatus(HttpStatus.OK)
     public ApiResponse getCityById(Integer id) {
          return new ApiResponse(cityRepository.findById(id).orElseThrow(()->new RecordNotFoundException(CITY_NOT_FOUND)),true);
     }

     @ResponseStatus(HttpStatus.OK)
     public ApiResponse deleteCityById(Integer id) {
          City city = cityRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CITY_NOT_FOUND));
          city.setActive(false);
          cityRepository.save(city);
          return new ApiResponse(DELETED,true);
     }
}
