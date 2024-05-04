package uz.pdp.shippingservice.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.pdp.shippingservice.service.AdvertisingService;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CronJobs {

    private final AdvertisingService advertisingService;

//    @Scheduled(cron = "0 0 23 * * *")
    public void deactivateAdvertisingWhenExpire() {
        System.out.println("start deactivateAdvertisingWhenExpire :" + LocalDateTime.now());
        advertisingService.deactivateWhenExpire();
        System.out.println("end   deactivateAdvertisingWhenExpire :" + LocalDateTime.now());
    }

}
