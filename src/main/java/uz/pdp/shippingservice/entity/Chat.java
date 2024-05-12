package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.dto.NotificationRequestDto;
import uz.pdp.shippingservice.enums.TypeClients;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_clients")
    private TypeClients typeClient;

    @Column(name = "announcement_id")
    private UUID announcementId;

    @Column(name = "read")
    private boolean read;

    @Column(name = "deleted")
    private boolean deleted;

//    @JdbcTypeCode(SqlTypes.JSON)
////    @org.hibernate.annotations.Type(type = "jsonb")
//    @Column(name="messages", columnDefinition = "jsonb")
//    private List<ChatMessageDto> messages;

    @Column(name = "chat_created_at")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime chatCreatedAt;


    public static Chat toEntity(NotificationRequestDto dto) {
//        ChatMessageDto chatMessageDto = new ChatMessageDto(dto.getSenderId(), dto.getMessage() ,LocalDateTime.now());
        return Chat.builder()
                .receiverId(dto.getReceiverId())
                .senderId(dto.getSenderId())
                .announcementId(dto.getAnnouncementId())
                .chatCreatedAt(LocalDateTime.now())
                .typeClient(dto.getType())
                .read(false)
                .deleted(false)
//                .messages(List.of(chatMessageDto))
                .build();
    }
}
