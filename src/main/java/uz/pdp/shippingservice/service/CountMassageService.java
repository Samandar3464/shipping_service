package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.request.CountMassageRequest;
import uz.pdp.shippingservice.repository.CountMassageRepository;


@Service
@RequiredArgsConstructor
public class CountMassageService {

    private final CountMassageRepository countMassageRepository;
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllMassagesCount(){
      return new ApiResponse(countMassageRepository.countAllByCount(1),true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllMassagesCountByDate(CountMassageRequest countMassageRequest){
        Integer counted = countMassageRepository.countAllBySandedTimeBetween(countMassageRequest.getTime1(), countMassageRequest.getTime2());
        return new ApiResponse(counted,true);
    }

}
