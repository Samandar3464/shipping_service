package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.City;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.model.request.CityRequestDto;
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
          City city = City.from(cityRequestDto, regionRepository.findById(cityRequestDto.getRegionId()).orElseThrow(() -> new RecordNotFoundException(REGION_NOT_FOUND)));
          City save = cityRepository.save(city);
          return new ApiResponse(SUCCESSFULLY, true,save);
     }
     @ResponseStatus(HttpStatus.OK)
     public ApiResponse getCityList(Integer id) {
          return new ApiResponse(cityRepository.findAllByRegionId(id),true);
     }

     @ResponseStatus(HttpStatus.OK)
     public ApiResponse getCityById(Integer id) {
          return new ApiResponse(cityRepository.findById(id).orElseThrow(()->new RecordNotFoundException(CITY_NOT_FOUND)),true);
     }

     @ResponseStatus(HttpStatus.OK)
     public ApiResponse deleteCityById(Integer id) {
          cityRepository.deleteById(id);
          return new ApiResponse(DELETED,true);
     }
}
