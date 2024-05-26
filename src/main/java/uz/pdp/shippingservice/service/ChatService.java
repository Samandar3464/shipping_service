package uz.pdp.shippingservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.chat.ChatListDto;
import uz.pdp.shippingservice.dto.chat.ChatMessageDto;
import uz.pdp.shippingservice.dto.NotificationRequestDto;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.entity.Chat;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.enums.TypeClients;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.repository.ChatRepository;
import uz.pdp.shippingservice.service.AnnouncementClientService;
import uz.pdp.shippingservice.service.AnnouncementDriverService;
import uz.pdp.shippingservice.service.FireBaseMessagingService;
import uz.pdp.shippingservice.service.UserService;
import uz.pdp.shippingservice.utils.AppUtils;

import java.time.LocalDateTime;
import java.util.*;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final AnnouncementDriverService announcementDriverService;
    private final AnnouncementClientService announcementClientService;
    private final UserService userService;
    private final FireBaseMessagingService fireBaseMessagingService;
    private final JdbcTemplate jdbcTemplate;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse sendMessage(NotificationRequestDto dto) {
        UserEntity sender = userService.checkUserExistByContext();
        if (!dto.getSenderId().equals(sender.getId())) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        UserEntity receiver = userService.checkUserExistById(dto.getReceiverId());


        Optional<Chat> chatOptional = null;
        Chat entity;
        if (dto.getType().equals(DRIVER)) {
            chatOptional = chatRepository.findFirstByTypeClientAndAnnouncementIdAndReceiverIdAndAndSenderId(dto.getType(), dto.getAnnouncementId(), dto.getReceiverId(), dto.getSenderId());
            if (!announcementClientService.existById(dto.getAnnouncementId())) {
                throw new RecordNotFoundException(CLIENT_ANNOUNCEMENT_NOT_FOUND);
            }
        } else if (dto.getType().equals(CLIENT)) {
            chatOptional = chatRepository.findFirstByTypeClientAndAnnouncementIdAndReceiverIdAndAndSenderId(dto.getType(), dto.getAnnouncementId(), dto.getSenderId(), dto.getReceiverId());
            if (!announcementDriverService.existById(dto.getAnnouncementId())) {
                throw new RecordNotFoundException(DRIVER_ANNOUNCEMENT_NOT_FOUND);
            }
        }
        if (chatOptional.isEmpty()) {
            entity = Chat.toEntity(dto);
        } else {
            entity = chatOptional.get();
            ArrayList<ChatMessageDto> messageDtos = (ArrayList<ChatMessageDto>) entity.getMessages();
//        TypeReference<List<ChatMessageDto>> typeResolverBuilder = new TypeReference<List<ChatMessageDto>>() {};
//        List<ChatMessageDto> chatMessageDtos = AppUtils.convertWithJackson(chat.getMessages(), typeResolverBuilder);
            messageDtos.add(new ChatMessageDto(dto.getSenderId(), dto.getMessage(), LocalDateTime.now()));
            entity.setMessages(messageDtos);
        }
        entity.setUpdatedAt(LocalDateTime.now());
        Chat savedChat = chatRepository.save(entity);
        fireBaseMessagingService.sentMessageUseingByFirebase(savedChat, receiver.getFirebaseToken(), dto.getMessage());
        return new ApiResponse(SUCCESSFULLY, true);
    }


    public ApiResponse getAllMassage(TypeClients typeClients) {
        UserEntity userEntity = userService.checkUserExistByContext();
        String query = getMessagesForDriver;
        if (typeClients.equals(CLIENT)) {
            query = getMessagesForClient;
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(query, userEntity.getId(), typeClients);
        TypeReference<List<ChatListDto>> typeReference = new TypeReference<List<ChatListDto>>() {
        };
        return new ApiResponse(SUCCESSFULLY, true, AppUtils.convertWithJackson(maps, typeReference));
    }

    public ApiResponse getMessage(UUID chatId) {
        Chat chat = chatRepository.findFirstById(chatId).orElseThrow(() -> new RecordNotFoundException(CHAT_NOT_FOUND));
        return new ApiResponse(chat, true);
    }

    private String getMessagesForClient = "select c.id as chat_id, c.sender_id  as sender_id, c.updated_at,\n" +
            "       c.messages->0 ->> 'messages' as last_message,\n" +
            "       u.surname || ' ' || u.name as sender_name\n" +
            "from chat c, users u where  u.id = c.receiver_id and c.type_clients = 'CLIENT'  \n" +
            "and c.sender_id = ?    order by c.updated_at desc";
    private String getMessagesForDriver = "select c.id as chat_id, c.receiver_id as sender_id , c.updated_at,\n" +
            "       c.messages->0 ->> 'messages' as last_message,\n" +
            "       u.surname || ' ' || u.name as sender_name\n" +
            "from chat c, users u where  u.id = c.sender_id and c.type_clients = 'DRIVER'\n" +
            "  and c.receiver_id = ?    order by c.updated_at desc";
}