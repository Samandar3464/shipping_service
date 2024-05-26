package uz.pdp.shippingservice.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatListDto implements Serializable {

    private UUID chatId;

    private Long senderId;

    private String senderName;

    private String lastMessage;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime updatedAt;

    public ChatListDto(@JsonProperty("chat_id") UUID chatId,
                       @JsonProperty("sender_id") Long senderId,
                       @JsonProperty("sender_name") String senderName,
                       @JsonProperty("last_message") String lastMessage,
                       @JsonProperty("updated_at") Timestamp updatedAt) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.lastMessage = lastMessage;
        this.updatedAt = updatedAt.toLocalDateTime();
    }
}