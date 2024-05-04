package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Advertising;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.dto.request.AdvertisingRequestDto;
import uz.pdp.shippingservice.repository.AdvertisingRepository;
import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class AdvertisingService {

    private final AdvertisingRepository advertisingRepository;

    private final AttachmentService attachmentService;

    private final LocalDateTimeConverter converter;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(AdvertisingRequestDto dto) {
        Advertising advertising = Advertising.toEntity(dto);
        advertising.setStartDate(converter.convertDate(dto.getStartDate()));
        advertising.setEndDate(converter.convertDate(dto.getEndDate()));
        if (dto.getContent()!= null){
            Attachment attachment = attachmentService.saveToSystem(dto.getContent());
            advertising.setUrl(attachmentService.getUrl(attachment));
            advertising.setContent(attachment);
        }
        advertisingRepository.save(advertising);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllActiveAdvertising() {
        List<Advertising> list = advertisingRepository.findAllByActiveTrue();
        return new ApiResponse(list, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllAdvertising() {
        List<Advertising> list = advertisingRepository.findAll();
        return new ApiResponse(list, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public void deactivateWhenExpire() {
        List<Advertising> list = advertisingRepository.findAllByActiveTrue();
        if (!list.isEmpty()) {
            list.forEach(obj -> {
                if (obj.getEndDate().isBefore(LocalDateTime.now())) {
                    obj.setActive(false);
                    advertisingRepository.save(obj);
                }
            });
        }
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse activate(Long id, AdvertisingRequestDto dto) {
        Advertising advertising = advertisingRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ADVERTISING_NOT_FOUND));
        Advertising newAdvertising = new Advertising();
        newAdvertising.setPrice(dto.getPrice());
        newAdvertising.setName(dto.getName());
        newAdvertising.setOwnerData(dto.getOwnerData());
        newAdvertising.setStartDate(converter.convertDate(dto.getStartDate()));
        newAdvertising.setEndDate(converter.convertDate(dto.getEndDate()));
        newAdvertising.setActive(true);
        newAdvertising.setUrl(advertising.getUrl());
        advertisingRepository.save(newAdvertising);
        return new ApiResponse(DELETED, true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deActivate(Long id) {
        Advertising advertising = advertisingRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ADVERTISING_NOT_FOUND));
        advertising.setActive(false);
        advertisingRepository.save(advertising);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleted(Long id) {
        Advertising advertising = advertisingRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ADVERTISING_NOT_FOUND));
        attachmentService.deleteNewNameId(advertising.getContent().getNewName());
        advertisingRepository.deleteById(id);
        return new ApiResponse(DELETED, true);
    }
}
