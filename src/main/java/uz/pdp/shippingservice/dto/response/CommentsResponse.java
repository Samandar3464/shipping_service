package uz.pdp.shippingservice.dto.response;

import lombok.*;
import uz.pdp.shippingservice.entity.Comments;

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
