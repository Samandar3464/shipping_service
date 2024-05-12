package uz.pdp.shippingservice.service.notcomplated;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.ChatMessageDto;
import uz.pdp.shippingservice.dto.NotificationRequestDto;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.entity.Chat;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.enums.TypeClients;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.exception.UserNotFoundException;
import uz.pdp.shippingservice.repository.AnnouncementClientRepository;
import uz.pdp.shippingservice.repository.AnnouncementDriverRepository;
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
            query =getMessagesForClient;
        }
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(query, userEntity.getId() , typeClients);
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
        };
        return new ApiResponse(SUCCESSFULLY, true, AppUtils.convertWithJackson(maps, typeReference));
    }

    public ApiResponse getMessage(UUID announcementId, TypeClients typeClients) {
        return null;
    }

    private String getMessagesForClient ="";
    private String getMessagesForDriver ="";
}