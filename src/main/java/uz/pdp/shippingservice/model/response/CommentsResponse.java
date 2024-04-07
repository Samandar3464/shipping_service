package uz.pdp.shippingservice.model.response;

import lombok.*;
import uz.pdp.shippingservice.entity.Comments;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsResponse {


    private Integer id;

    private String userName;

    private String phoneNumber;

    private String[] text;

    private boolean read;

    private String createdTime;

    public static CommentsResponse from(Comments comments){
        String[] test = comments.getText().split("/new/");

        return CommentsResponse.builder()
                .id(comments.getId())
                .userName(comments.getName())
                .phoneNumber(comments.getPhoneNumber())
                .text(test)
                .createdTime(comments.getCreatedTime().toString())
                .build();
    }
}
