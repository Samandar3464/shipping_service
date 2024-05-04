package uz.pdp.shippingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.request.*;
import uz.pdp.shippingservice.service.UserService;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }

    @PostMapping("/verify")
    public ApiResponse verify(@RequestBody @Validated UserVerifyRequestDto userVerifyRequestDto) {
        return userService.verify(userVerifyRequestDto);
    }

    @GetMapping("/resend-sms")
    public ApiResponse reSendSms(@RequestParam(name = "phone") String phone) {
        return userService.reSendSms(phone);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Validated UserLoginRequestDto userLoginRequestDto) {
        return userService.login(userLoginRequestDto);
    }

    @GetMapping("/me")
    public ApiResponse checkUserResponseExistById() {
        return userService.getMe();
    }

    @PostMapping("/forget-password")
    public ApiResponse forgetPassword(@RequestBody String number) {
        return userService.forgetPassword(number);
    }


    @PostMapping("/change-password")
    public ApiResponse changePassword(@RequestBody ForgerPasswordDto dto) {
        return userService.changePassword(dto);
    }

    @PutMapping("/change-password")
    public ApiResponse changePassword(@RequestBody ChangePasswordDto dto) {
        return userService.changePasswordToNew(dto);
    }

    @PostMapping("get/token/refresh-token")
    public ApiResponse refreshToken(HttpServletRequest httpServletRequest) throws Exception {
        return userService.getToken(httpServletRequest);
    }

    @GetMapping("/getById/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse getUserById(@PathVariable Integer id) {
        return userService.getByUserId(id);
    }


    @PutMapping("/block/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse blockUserById(@PathVariable Integer id) {
        return userService.addBlockUserByID(id);
    }

    @PutMapping("/openBlock/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse openBlockUserById(@PathVariable Integer id) {
        return userService.openToBlockUserByID(id);
    }

    @PostMapping("/set-firebase-token")
    public ApiResponse setFireBaseToken(@RequestBody FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        return userService.saveFireBaseToken(fireBaseTokenRegisterDto);
    }

    @GetMapping("/get-user-list")
//    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse getUserList(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "5") int size) {
        return userService.getUserList(page, size);
    }

    @GetMapping("/logout")
    public ApiResponse deleteUserFromContext() {
        return userService.removeUserFromContext();
    }
}
