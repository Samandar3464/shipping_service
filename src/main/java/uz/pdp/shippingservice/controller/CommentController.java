package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.entity.Comments;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/add")
    public ApiResponse addComment(@RequestBody Comments comments){
        return commentService.addComment(comments);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(){
        return commentService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return commentService.getById(id);
    }
}
