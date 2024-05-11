package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.base.ApiResponse;
import uz.pdp.shippingservice.dto.user.UserStatusDto;
import uz.pdp.shippingservice.entity.UserStatus;
import uz.pdp.shippingservice.enums.Type;
import uz.pdp.shippingservice.repository.UserStatusRepository;

import java.util.List;

import static uz.pdp.shippingservice.constants.Constants.SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserService  userService;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createComment(UserStatusDto dto) {
        UserStatus entity = UserStatus.toEntity(dto);
        entity.setGivenBy(userService.checkUserExistById(dto.getGivenBy()));
        entity.setGivenTo(userService.checkUserExistById(dto.getGivenTo()));
        userStatusRepository.save(entity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public ApiResponse getAllByUserAndType(Long userId , Type type) {
        List<UserStatus> list = userStatusRepository.findAllByGivenToIdAndType(userId, type);
        return new ApiResponse(SUCCESSFULLY, true, list);
    }

}
