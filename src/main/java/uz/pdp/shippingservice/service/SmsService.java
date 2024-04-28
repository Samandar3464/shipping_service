package uz.pdp.shippingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.shippingservice.dto.request.UserVerifyRequestDto;
import uz.pdp.shippingservice.entity.SmsEntity;
import uz.pdp.shippingservice.entity.SmsServiceTokenEntity;
import uz.pdp.shippingservice.exception.SmsSendingFailException;
import uz.pdp.shippingservice.exception.SmsException;
import uz.pdp.shippingservice.dto.request.SmsModel;
import uz.pdp.shippingservice.dto.request.SmsToken;
import uz.pdp.shippingservice.dto.response.SmsResponse;
import uz.pdp.shippingservice.properties.SmsServiceProperties;
import uz.pdp.shippingservice.repository.SmsRepository;
import uz.pdp.shippingservice.repository.SmsServiceTokenRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsServiceProperties properties;

    private final SmsServiceTokenRepository smsServiceTokenRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    private final SmsRepository smsRepository;

    private static final String GET_TOKEN = "https://notify.eskiz.uz/api/auth/login";
    private static final String RELOAD_TOKEN = "https://notify.eskiz.uz/api/auth/refresh";
    private static final String SMS_SEND = "https://notify.eskiz.uz/api/message/sms/send";

    public String getToken() {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", properties.getUsername());
            requestBody.put("password", properties.getPassword());
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody);
            String url = properties.getUrl() + "/api/auth/login";
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            String body = response.getBody();
            SmsToken smsToken = objectMapper.readValue(body, SmsToken.class);
            SmsServiceTokenEntity entity = smsToken.getData();
            SmsServiceTokenEntity save = smsServiceTokenRepository.save(entity);
            return save.getToken();
        } catch (Exception e) {
            throw new SmsException(CAN_NOT_TAKE_SMS_SENDING_SERVICE_TOKEN);
        }
    }

    public SmsResponse send(String phone, String message, String code) {
        SmsEntity smsEntity = SmsEntity.toEntity(phone, message, code);
        SmsResponse smsResponse = sendSms(SmsModel.builder()
                .phone(phone)
                .message(message)
                .from(4546)
                .callback_url("http://0000.uz/test.php")
                .build());

        smsRepository.save(smsEntity);
        return smsResponse;
    }

    public SmsResponse sendSms(SmsModel smsModel) {
        HttpEntity<Map<String, String>> request = null;
        HttpHeaders headers = new HttpHeaders();
        String token = null;
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);
            List<SmsServiceTokenEntity> all = smsServiceTokenRepository.findAll();
            if (!all.isEmpty()) {
                token = all.get(0).getToken();
            } else {
                token = getToken();
            }
            headers.set("Authorization", "Bearer " + token);
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("mobile_phone", "998" + smsModel.getPhone());
            requestBody.put("message", smsModel.getMessage());
            requestBody.put("from", String.valueOf(smsModel.getFrom()));
            requestBody.put("callback_url", smsModel.getCallback_url());
            request = new HttpEntity<>(requestBody, headers);
            String url = properties.getUrl() + "/api/message/sms/send";
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            String body = response.getBody();
            return objectMapper.readValue(body, SmsResponse.class);
        } catch (Exception e) {
            try {
                token = reFreshToken(token);
                HttpHeaders headers1 = request.getHeaders();
                headers1.setBearerAuth(token);
                String url = properties.getUrl() + "/api/message/sms/send";
                restTemplate.postForEntity(url, request, String.class);
            } catch (Exception e1) {
                throw new SmsSendingFailException(CAN_NOT_SEND_SMS);
            }
        }
        return null;
    }

    private String reFreshToken(String oldToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + oldToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String url = properties.getUrl() + "/api/auth/refresh";
        restTemplate.patchForObject(url, httpEntity, Void.class);
        return getToken();
    }

    public boolean findByPhoneAndCheck(UserVerifyRequestDto dto) {
        SmsEntity smsEntity = smsRepository.findByPhoneAndCode(dto.getPhone(), dto.getVerificationCode()).orElseThrow(() -> new SmsException(SMS_NOT_SEND_THIS_NUMBER));
        if (LocalDateTime.now().isAfter(smsEntity.getExpireAt())) {
            throw new SmsException(SMS_CODE_EXPIRE_TIME);
        }
        return true;
    }
}
