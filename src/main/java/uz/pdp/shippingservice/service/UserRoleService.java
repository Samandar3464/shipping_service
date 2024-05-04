package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.dto.PageRequestFilter;
import uz.pdp.shippingservice.dto.request.CityRequestDto;
import uz.pdp.shippingservice.dto.request.UserRoleDto;
import uz.pdp.shippingservice.entity.UserRole;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.entity.locations.City;
import uz.pdp.shippingservice.exception.RecordAlreadyExistException;
import uz.pdp.shippingservice.exception.RecordNotFoundException;
import uz.pdp.shippingservice.repository.CityRepository;
import uz.pdp.shippingservice.repository.RegionRepository;
import uz.pdp.shippingservice.repository.RoleRepository;

import static uz.pdp.shippingservice.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final RoleRepository roleRepository;


    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(UserRoleDto dto) {
        if (roleRepository.existsByName(dto.getName())) {
            throw new RecordAlreadyExistException(ROLE_ALREADY_EXIST);
        }
        UserRole entity = UserRole.toEntity(dto);
        roleRepository.save(entity);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll() {
        return new ApiResponse(roleRepository.findAll(), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public UserRole findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer id) {
        return new ApiResponse(roleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND)), true);
    }


    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer id) {
        UserRole userRole = roleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND));
        userRole.setDeleted(true);
        roleRepository.save(userRole);
        return new ApiResponse(DELETED, true);
    }
}
