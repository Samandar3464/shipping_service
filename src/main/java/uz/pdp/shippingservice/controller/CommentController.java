package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.request.UserStatusDto;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.enums.Type;
import uz.pdp.shippingservice.service.UserStatusService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/comment")
public class CommentController {

    private final UserStatusService userStatusService;
    @PostMapping("/create")
    public ApiResponse addComment(@RequestBody UserStatusDto dto){
        return userStatusService.createComment(dto);
    }

    @GetMapping("/getAllByUser")
    public ApiResponse getAll(@RequestParam (name = "userId") Long userId ,
                              @RequestParam(name = "type") Type type){
        return userStatusService.getAllByUserAndType(userId , type);
    }

}
