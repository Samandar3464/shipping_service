package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.Car;
import uz.pdp.shippingservice.entity.Role;
import uz.pdp.shippingservice.entity.User;
import uz.pdp.shippingservice.entity.api.ApiResponse;
import uz.pdp.shippingservice.exception.CarAlreadyExistException;
import uz.pdp.shippingservice.exception.CarNotFound;
import uz.pdp.shippingservice.model.request.CarRegisterRequestDto;
import uz.pdp.shippingservice.model.request.SmsModel;
import uz.pdp.shippingservice.model.response.CarResponseDto;
import uz.pdp.shippingservice.model.response.CarResponseListForAdmin;
import uz.pdp.shippingservice.model.response.DenyCar;
import uz.pdp.shippingservice.repository.CarRepository;
import uz.pdp.shippingservice.repository.RoleRepository;
import uz.pdp.shippingservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static uz.pdp.shippingservice.constants.Constants.*;


@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final AttachmentService attachmentService;

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final SmsService service;

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse addCar(CarRegisterRequestDto carRegisterRequestDto) {
        User user = userService.checkUserExistByContext();
        List<Role> roles = user.getRoles();
        Role byName = roleRepository.findByName(DRIVER);
        if (!user.getRoles().contains(byName)) {
            roles.add((byName));
        }
        userRepository.save(user);
        Car car = from(carRegisterRequestDto, user);
        carRepository.save(car);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCar() {
        User user = userService.checkUserExistByContext();
        Car car = getCarByUserId(user.getId());
        return new ApiResponse(fromCarToResponse(car), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getCarById(UUID carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        return new ApiResponse(fromCarToResponse(car), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteCarByID(UUID id) {
        Car byId = carRepository.findById(id).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        byId.setActive(false);
        carRepository.save(byId);
        return new ApiResponse(DELETED, true);
    }

    private Car from(CarRegisterRequestDto carRegisterRequestDto, User user) {
        Optional<Car> byUserIdAndActiveTrue = carRepository.findByUserIdAndActiveTrue(user.getId());
        if (byUserIdAndActiveTrue.isPresent()) {
            throw new CarAlreadyExistException(CAR_ALREADY_EXIST);
        }
        Car car = Car.from(carRegisterRequestDto);
        car.setPhotoDriverLicense(attachmentService.saveToSystem(carRegisterRequestDto.getPhotoDriverLicense()));
        car.setTexPassportPhoto(attachmentService.saveToSystem(carRegisterRequestDto.getTexPassportPhoto()));
        car.setCarPhotos(attachmentService.saveToSystemListFile(carRegisterRequestDto.getCarPhotoList()));
        car.setUser(user);
        return car;
    }

    private CarResponseDto fromCarToResponse(Car car) {
        Attachment texPassportPhoto1 = car.getTexPassportPhoto();
        String texPasswordPhotoLink = attachmentService.attachUploadFolder + texPassportPhoto1.getPath() + "/" + texPassportPhoto1.getNewName() + "." + texPassportPhoto1.getType();
        Attachment photoDriverLicense1 = car.getPhotoDriverLicense();
        String photoDriverLicense2 = attachmentService.attachUploadFolder + photoDriverLicense1.getPath() + "/" + photoDriverLicense1.getNewName() + "." + photoDriverLicense1.getType();
        List<Attachment> autoPhotos1 = car.getCarPhotos();
        List<String> carPhotoList = new ArrayList<>();
        for (Attachment attachment : autoPhotos1) {
            carPhotoList.add(attachmentService.attachUploadFolder + attachment.getPath() + "/" + attachment.getNewName() + "." + attachment.getType());
        }
        CarResponseDto carResponseDto = CarResponseDto.from(car);
        carResponseDto.setTexPassportPhotoPath(texPasswordPhotoLink);
        carResponseDto.setPhotoDriverLicense(photoDriverLicense2);
        carResponseDto.setAutoPhotosPath(carPhotoList);
        return carResponseDto;
    }

    public Car getCarByUserId(UUID user_id) {
        return carRepository.findByUserIdAndActiveTrue(user_id).orElseThrow(() ->
                new CarNotFound(CAR_NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse disActiveCarList(int page, int size) {
        List<CarResponseDto> carResponseDtoList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> allByActive = carRepository.findAllByActiveFalse(pageable);
        allByActive.forEach(car -> carResponseDtoList.add(fromCarToResponse(car)));
        return new ApiResponse(new CarResponseListForAdmin(carResponseDtoList, allByActive.getTotalElements(), allByActive.getTotalPages(), allByActive.getNumber()), true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse activateCar(UUID carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        car.setActive(true);
        carRepository.save(car);
        return new ApiResponse(CAR_ACTIVATED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deactivateCar(UUID carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        car.setActive(false);
        carRepository.save(car);
        return new ApiResponse(CAR_DEACTIVATED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse denyCar(DenyCar denyCar) {
        Car car = carRepository.findById(denyCar.getCarId()).orElseThrow(() -> new CarNotFound(CAR_NOT_FOUND));
        User userByCar = userService.checkUserExistById(car.getUser().getId());

        car.getCarPhotos().forEach(obj -> attachmentService.deleteNewNameId(obj.getNewName() + "." + obj.getType()));
        attachmentService.deleteNewNameId(car.getPhotoDriverLicense().getNewName() + "." + car.getPhotoDriverLicense().getType());
        attachmentService.deleteNewNameId(car.getTexPassportPhoto().getNewName() + "." + car.getTexPassportPhoto().getType());
        carRepository.deleteById(car.getId());

        service.sendSms(SmsModel.builder()
                .mobile_phone(userByCar.getPhone())
                .message("DexTaxi. Sizni mashina qo'shish bo'yicha arizangiz bekor qilindi" +
                        " . Sababi :" + denyCar.getMassage() + ". Qaytadan mashina qo'shing. ")
                .from(4546)
                .callback_url("http://0000.uz/test.php")
                .build());
        return new ApiResponse(SUCCESSFULLY, true);
    }

//    @ResponseStatus(HttpStatus.OK)
//    public ApiResponse updateCar(UUID carId, CarRegisterRequestDto carRegisterRequestDto) {
//        User user = userService.checkUserExistByContext();
//        Car car = from(carRegisterRequestDto, user);
//        car.setId(carId);
//        carRepository.save(car);
//        return new ApiResponse(SUCCESSFULLY, true);
//    }
}
