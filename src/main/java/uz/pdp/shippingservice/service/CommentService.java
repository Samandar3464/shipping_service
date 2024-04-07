package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Comments;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.response.CommentsResponse;
import uz.pdp.shippingservice.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.pdp.shippingservice.constants.Constants.SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addComment(Comments comments) {
        Comments comment;
        Optional<Comments> byPhoneNumber = commentRepository.findByPhoneNumber(comments.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            comment = byPhoneNumber.get();
            String text = comment.getText();
            String newText = text + "/new/"+ LocalDateTime.now()+"   "+ comments.getText();
            comment.setCreatedTime(LocalDateTime.now());
            comment.setText(newText);
            comment.setRead(false);
            commentRepository.save(comment);
            return new ApiResponse(SUCCESSFULLY, true);
        }
        comment = Comments.builder()
                .phoneNumber(comments.getPhoneNumber())
                .name(comments.getName())
                .read(false)
                .createdTime(LocalDateTime.now())
                .text(LocalDateTime.now()+"   "+comments.getText())
                .build();
        commentRepository.save(comment);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public ApiResponse getAll() {
        List<CommentsResponse> commentsResponses = new ArrayList<>();
        commentRepository.findAllByReadFalse().forEach(obj -> commentsResponses.add(CommentsResponse.from(obj)));
        return new ApiResponse(SUCCESSFULLY, true, commentsResponses);
    }

    public ApiResponse getById(Integer id) {
        Comments comments = commentRepository.findById(id).get();
        return new ApiResponse(CommentsResponse.from(comments), true);
    }
}
