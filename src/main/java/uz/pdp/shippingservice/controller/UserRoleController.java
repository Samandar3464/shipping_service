package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.request.UserRoleDto;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.service.UserRoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/userRole")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping("/create")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse create(@RequestBody UserRoleDto dto){
        return userRoleService.create(dto);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(){
        return userRoleService.getAll();
    }


    @GetMapping("/getById/{id}")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse getById(@PathVariable Integer id){
        return userRoleService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAnyRole('DRIVER','ADMIN')")
    public ApiResponse delete(@PathVariable Integer id){
        return userRoleService.delete(id);
    }
}
