package uz.pdp.shippingservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.shippingservice.entity.Chat;
import uz.pdp.shippingservice.exception.FirebaseConnectionException;
import uz.pdp.shippingservice.utils.AppUtils;

import java.util.HashMap;

import static uz.pdp.shippingservice.constants.Constants.FIREBASE_EXCEPTION;
import static uz.pdp.shippingservice.constants.Constants.SUCCESSFULLY;


@Service
@RequiredArgsConstructor
public class FireBaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public void sentMessageUseingByFirebase(Chat chat ,String token,  String title){
        String stringObj = AppUtils.getJsonAsStringByJackson(chat.getMessages());
        HashMap<String , String> data = new HashMap<>();
        data.put("messages", stringObj);
        sendNotificationByToken(title ,"fire body to check",token , data);
    }

    private String sendNotificationByToken(String title , String body , String token , HashMap<String ,String> data) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(data)
                .build();
        try {
            firebaseMessaging.send(message);
            return SUCCESSFULLY;
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new FirebaseConnectionException(FIREBASE_EXCEPTION);
        }
    }
}
