package uz.pdp.shippingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.model.request.*;
import uz.pdp.shippingservice.service.UserService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ApiResponse registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.registerUser(userRegisterDto);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Validated UserLoginRequestDto userLoginRequestDto) {
        return userService.login(userLoginRequestDto);
    }

    @PostMapping("/verify")
    public ApiResponse verify(@RequestBody @Validated UserVerifyRequestDto userVerifyRequestDto) {
        return userService.verify(userVerifyRequestDto);
    }

    @PostMapping("/forgetPassword")
    public ApiResponse forgetPassword(@RequestBody String number) {
        return userService.forgetPassword(number);
    }
    @PostMapping("get/token/refreshToken")
    public ApiResponse refreshToken(HttpServletRequest httpServletRequest) throws Exception {
        return userService.getToken(httpServletRequest);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse getUserById(@PathVariable UUID id) {
        return userService.getByUserId(id);
    }
    @PostMapping("/setStatus")
    @PreAuthorize("hasAnyRole('HAYDOVCHI','YOLOVCHI','ADMIN')")
    public ApiResponse setStatus(@RequestBody StatusDto statusDto) {
        return userService.setStatus(statusDto);
    }

    @PutMapping("/block/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse blockUserById(@PathVariable UUID id) {
        return userService.addBlockUserByID(id);
    }

    @PutMapping("/openBlock/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse openBlockUserById(@PathVariable UUID id) {
        return userService.openToBlockUserByID(id);
    }

    @PostMapping("/setFireBaseToken")
    public ApiResponse setFireBaseToken(@RequestBody FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        return userService.saveFireBaseToken(fireBaseTokenRegisterDto);
    }

    @PostMapping("/changePassword")
    public ApiResponse changePassword(
            @RequestParam String number,
            @RequestParam String password
    ) {
        return userService.changePassword(number, password);
    }
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('DRIVER','CLIENT','ADMIN')")
    public ApiResponse update(@ModelAttribute UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }
    @GetMapping("/getUserList")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse getUserList(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "5") int size) {
        return userService.getUserList(page, size);
    }

    @GetMapping("/getByToken")
    public ApiResponse checkUserResponseExistById() {
        return userService.checkUserResponseExistById();
    }

    @GetMapping("/reSendSms/{phone}")
    public ApiResponse reSendSms(@PathVariable String phone) {
        return userService.reSendSms(phone);
    }
    @GetMapping("/logout")
    public ApiResponse deleteUserFromContext() {
        return userService.removeUserFromContext();
    }
}
