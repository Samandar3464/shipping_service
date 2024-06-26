package uz.pdp.shippingservice.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.pdp.shippingservice.service.AdvertisingService;
import uz.pdp.shippingservice.service.AnnouncementClientService;
import uz.pdp.shippingservice.service.AnnouncementDriverService;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CronJobs {

    private final AdvertisingService advertisingService;
    private final AnnouncementClientService announcementClientService;
    private final AnnouncementDriverService announcementDriverService;

//    @Scheduled(cron = "0 0 23 * * *")
    public void deactivateAdvertisingWhenExpire() {
        System.out.println("start deactivateAdvertisingWhenExpire :" + LocalDateTime.now());
        advertisingService.deactivateWhenExpire();
        System.out.println("end   deactivateAdvertisingWhenExpire :" + LocalDateTime.now());
    }

    //    @Scheduled(cron = "0 0 23 * * *")
    public void deactivateAllOldClientAnnouncement() {
        System.out.println("start deactivateAllOldClientAnnouncement :" + LocalDateTime.now());
        announcementClientService.deactivateAllOldClientAnnouncement();
        System.out.println("end   deactivateAllOldClientAnnouncement :" + LocalDateTime.now());
    }

    //    @Scheduled(cron = "0 0 23 * * *")
    public void deactivateAllOldDriverAnnouncement() {
        System.out.println("start deactivateAllOldDriverAnnouncement :" + LocalDateTime.now());
        announcementDriverService.deactivateAllDriverAnnouncement();
        System.out.println("end   deactivateAllOldDriverAnnouncement :" + LocalDateTime.now());
    }


}
