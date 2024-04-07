package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Advertising;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.model.request.AdvertisingRequestDto;
import uz.pdp.shippingservice.repository.AdvertisingRepository;

import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class AdvertisingService {

    private final AdvertisingRepository advertisingRepository;

    private final AttachmentService attachmentService;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse add(AdvertisingRequestDto advertisingRequestDto) {
        Attachment attachment = attachmentService.saveToSystem(advertisingRequestDto.getContent());
        Advertising advertising = Advertising.from(advertisingRequestDto);
        advertising.setContent(attachment);
        advertisingRepository.save(advertising);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deActivate(UUID id) {
        Advertising advertising = advertisingRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ADVERTISING_NOT_FOUND));
        advertising.setActive(false);
        advertisingRepository.save(advertising);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleted(UUID id) {
        advertisingRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ADVERTISING_NOT_FOUND));
        advertisingRepository.deleteById(id);
        return new ApiResponse(DELETED, true);
    }
}
