//package uz.pdp.shippingservice.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import uz.pdp.shippingservice.entity.*;
//import uz.pdp.shippingservice.entity.locations.City;
//import uz.pdp.shippingservice.entity.locations.Country;
//import uz.pdp.shippingservice.entity.locations.Region;
//import uz.pdp.shippingservice.entity.user.UserEntity;
//import uz.pdp.shippingservice.dto.location.CountryDto;
//import uz.pdp.shippingservice.entity.user.UserRole;
//import uz.pdp.shippingservice.repository.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RegionRepository regionRepository;
//    private final CityRepository cityRepository;
//    private final CountryRepository countryRepository;
//    @Value("${spring.sql.init.mode}")
//    private String initMode;
//
//    @Override
//    public void run(String... args) {
//
//        if (initMode.equals("always")) {
//            //admin
//            UserEntity adminUser = UserEntity.builder()
//                    .phone("998906163464")
//                    .createdAt(LocalDateTime.now())
//                    .password(passwordEncoder.encode("12345678"))
//                    .isBlocked(false)
//                    .isDeleted(false)
//                    .build();
//            UserEntity save = userRepository.save(adminUser);
//            Optional<UserRole> adminRole = roleRepository.findByName("admin");
//            save.setRoles(List.of(adminRole.get()));
//            userRepository.save(save);
//
//
//            Country country = Country.toEntity(new CountryDto("O'zbekistan"));
//            Country savedCountry = countryRepository.save(country);
//            List<Region> regions = List.of(
//                    new Region(1, "Toshkent shahri",savedCountry)
//                    , new Region(2, "Toshkent viloyati",savedCountry)
//                    , new Region(3, "Andijon",savedCountry)
//                    , new Region(4, "Buxoro",savedCountry)
//                    , new Region(5, "Farg`ona",savedCountry)
//                    , new Region(6, "Qoraqalpog‘iston",savedCountry)
//                    , new Region(7, "Jizzax",savedCountry)
//                    , new Region(8, "Qashqadaryo",savedCountry)
//                    , new Region(9, "Navoiy",savedCountry)
//                    , new Region(10, "Namangan",savedCountry)
//                    , new Region(11, "Samarqand",savedCountry)
//                    , new Region(12, "Surxondaryo",savedCountry)
//                    , new Region(13, "Sirdaryo",savedCountry)
//                    , new Region(14, "Xorazm",savedCountry));
//
//            regionRepository.saveAll(regions);
//
//            Region save1 = regionRepository.getById(1);
//            Region save2 = regionRepository.getById(2);
//            Region save3 = regionRepository.getById(3);
//            Region save4 = regionRepository.getById(4);
//            Region save5 = regionRepository.getById(5);
//            Region save6 = regionRepository.getById(6);
//            Region save7 = regionRepository.getById(7);
//            Region save8 = regionRepository.getById(8);
//            Region save9 = regionRepository.getById(9);
//            Region save10 = regionRepository.getById(10);
//            Region save11 = regionRepository.getById(11);
//            Region save12 = regionRepository.getById(12);
//            Region save13 = regionRepository.getById(13);
//            Region save14 = regionRepository.getById(14);
//
//
//            List<City> cities = List.of(
//                    new City("Bektemir tumani", save1),
//                    new City("Mirzo Ulug‘bek tumani", save1),
//                    new City("Mirobod tumani", save1),
//                    new City("Olmazor tumani", save1),
//                    new City("Sirg‘ali tumani", save1),
//                    new City("Uchtepa tumani", save1),
//                    new City("Chilonzor tumani", save1),
//                    new City("Shayxontohur tumani", save1),
//                    new City("Yunusobod tumani", save1),
//                    new City("Yakkasaroy tumani", save1),
//                    new City("Yashnobod tumani", save1),
//
//
//                    new City("Nurafshon shahri", save2),
//                    new City("Angren shahri", save2),
//                    new City("Bekobod shahri", save2),
//                    new City("Olmaliq shahri", save2),
//                    new City("Ohangaron shahri", save2),
//                    new City("Chirchiq shahri", save2),
//                    new City("Yangiyo‘l shahri", save2),
//                    new City("Bekobod tumani", save2),
//                    new City("Bo‘ka tumani", save2),
//                    new City("Bo‘stonliq tumani", save2),
//                    new City("Zangiota tumani", save2),
//                    new City("Qibray tumani", save2),
//                    new City("Quyichirchiq tumani", save2),
//                    new City("Oqqo‘rg‘on tumani", save2),
//                    new City("Ohangaron tumani", save2),
//                    new City("Parkent tumani", save2),
//                    new City("Piskent tumani", save2),
//                    new City("Toshkent tumani", save2),
//                    new City("O‘rtachirchiq tumani", save2),
//                    new City("Chinoz tumani", save2),
//                    new City("Yuqorichirchiq tumani", save2),
//                    new City("Yangiyo‘l tumani", save2),
//
//
//                    new City("Andijon shahri", save3),
//                    new City("Xonabod shahri", save3),
//                    new City("Andijon tumani", save3),
//                    new City("Asaka tumani", save3),
//                    new City("Baliqchi tumani", save3),
//                    new City("Bo‘z tumani", save3),
//                    new City("Buloqboshi tumani", save3),
//                    new City("Jalaquduq tumani", save3),
//                    new City("Izboskan tumani", save3),
//                    new City("Qo‘rg‘ontepa tumani", save3),
//                    new City("Marhamat t.", save3),
//                    new City("Oltinko‘l tumani", save3),
//                    new City("Paxtaobod tumani", save3),
//                    new City("Ulug‘nor tumani", save3),
//                    new City("Xo‘jaobod tumani", save3),
//                    new City("Shxon tumani", save3),
//
//
//                    new City("Buxoro shahri", save4),
//                    new City("Kogon shahri", save4),
//                    new City("Buxoro tumani", save4),
//                    new City("Vobkent tumani", save4),
//                    new City("Jondor tumani", save4),
//                    new City("Kogon tumani", save4),
//                    new City("Olot tumani", save4),
//                    new City("Peshku tumani", save4),
//                    new City("Romitan tumani", save4),
//                    new City("Shofirkon tumani", save4),
//                    new City("Qorovulbozor tumani", save4),
//                    new City("Qorako‘l tumani", save4),
//                    new City("G‘ijduvon tumani", save4),
//
//
//                    new City("Farg‘ona shahri", save5),
//                    new City("Marg‘ilon shahri", save5),
//                    new City("Quvasoy shahri", save5),
//                    new City("Qo‘qon shahri", save5),
//                    new City("Beshariq tumani", save5),
//                    new City("Bog‘dod tumani", save5),
//                    new City("Buvayda tumani", save5),
//                    new City("Dang‘ara tumani", save5),
//                    new City("Yozyovon tumani", save5),
//                    new City("Quva tumani", save5),
//                    new City("Qo‘shtepa tumani", save5),
//                    new City("Oltiariq tumani", save5),
//                    new City("Rishton tumani", save5),
//                    new City("So‘x tumani", save5),
//                    new City("Toshloq tumani", save5),
//                    new City("O‘zbekiston tumani", save5),
//                    new City("Uchko‘prik tumani", save5),
//                    new City("Farg‘ona tumani", save5),
//                    new City("Furqat tumani", save5),
//
//
//                    new City("Nukus shahri", save6),
//                    new City("Amudaryo tumani", save6),
//                    new City("Beruniy tumani", save6),
//                    new City("Kegeyli tumani", save6),
//                    new City("Qanliko‘l tumani", save6),
//                    new City("Qorao‘zak tumani", save6),
//                    new City("Qo‘ng‘irot tumani", save6),
//                    new City("Mo‘ynoq tumani", save6),
//                    new City("Nukus tumani", save6),
//                    new City("Taxiatosh tumani", save6),
//                    new City("Taxtako‘pir tumani", save6),
//                    new City("To‘rtko‘l tumani", save6),
//                    new City("Xo‘jayli tumani", save6),
//                    new City("Chimboy tumani", save6),
//                    new City("Sho‘manoy tumani", save6),
//                    new City("Ellikqal’a tumani", save6),
//
//
//                    new City("Jizzax shahri", save7),
//                    new City("Arnasoy tumani", save7),
//                    new City("Baxmal tumani", save7),
//                    new City("Do‘stlik tumani", save7),
//                    new City("Zarbdor tumani", save7),
//                    new City("Zafarobod tumani", save7),
//                    new City("Zomin tumani", save7),
//                    new City("Mirzacho‘l tumani", save7),
//                    new City("Paxtakor tumani", save7),
//                    new City("Forish tumani", save7),
//                    new City("Sharof Rashidov tumani", save7),
//                    new City("G‘allaorol tumani", save7),
//                    new City("Yangiobod tumani", save7),
//
//
//                    new City("Qarshi shahri", save8),
//                    new City("Shsabz shahri", save8),
//                    new City("Dehqonobod tumani", save8),
//                    new City("Kasbi tumani", save8),
//                    new City("Kitob tumani", save8),
//                    new City("Koson tumani", save8),
//                    new City("Mirishkor tumani", save8),
//                    new City("Muborak tumani", save8),
//                    new City("Nishon tumani", save8),
//                    new City("Chiroqchi tumani", save8),
//                    new City("Shsabz tumani", save8),
//                    new City("Yakkabog‘ tumani", save8),
//                    new City("Qamashi tumani", save8),
//                    new City("Qarshi tumani", save8),
//                    new City("G‘uzor tumani", save8),
//
//
//                    new City("Navoiy shahri", save9),
//                    new City("Zarafshon shahri", save9),
//                    new City("Karmana tumani", save9),
//                    new City("Konimex tumani", save9),
//                    new City("Navbahor tumani", save9),
//                    new City("Nurota tumani", save9),
//                    new City("Tomdi tumani", save9),
//                    new City("Uchquduq tumani", save9),
//                    new City("Xatirchi tumani", save9),
//                    new City("Qiziltepa tumani", save9),
//
//
//                    new City("Namangan shahri", save10),
//                    new City("Kosonsoy tumani", save10),
//                    new City("Mingbuloq tumani", save10),
//                    new City("Namangan tumani", save10),
//                    new City("Norin tumani", save10),
//                    new City("Pop tumani", save10),
//                    new City("To‘raqo‘rg‘on tumani", save10),
//                    new City("Uychi tumani", save10),
//                    new City("Uchqo‘rg‘on tumani", save10),
//                    new City("Chortoq tumani", save10),
//                    new City("Chust tumani", save10),
//                    new City("Yangiqo‘rg‘on tumani", save10),
//
//
//                    new City("Samarqand shahri", save11),
//                    new City("Kattaqo‘rg‘on shahri", save11),
//                    new City("Bulung‘ur tumani", save11),
//                    new City("Jomboy tumani", save11),
//                    new City("Ishtixon tumani", save11),
//                    new City("Kattaqo‘rg‘on tumani", save11),
//                    new City("Narpay tumani", save11),
//                    new City("Nurobod tumani", save11),
//                    new City("Oqdaryo tumani", save11),
//                    new City("Payariq tumani", save11),
//                    new City("Pastdarg‘om tumani", save11),
//                    new City("Paxtachi tumani", save11),
//                    new City("Samarqand tumani", save11),
//                    new City("Toyloq tumani", save11),
//                    new City("Urgut tumani", save11),
//                    new City("Qo‘shrabot tumani", save11),
//
//
//                    new City("Termiz shahri", save12),
//                    new City("Angor tumani", save12),
//                    new City("Boysun tumani", save12),
//                    new City("Denov tumani", save12),
//                    new City("Jarqo‘rg‘on tumani", save12),
//                    new City("Muzrobod tumani", save12),
//                    new City("Oltinsoy tumani", save12),
//                    new City("Sariosiyo tumani", save12),
//                    new City("Termiz tumani", save12),
//                    new City("Uzun tumani", save12),
//                    new City("Sherobod tumani", save12),
//                    new City("Sho‘rchi tumani", save12),
//                    new City("Qiziriq tumani", save12),
//                    new City("Qumqo‘rg‘on tumani", save12),
//
//
//                    new City("Guliston shahri", save13),
//                    new City("Yangiyer shahri", save13),
//                    new City("Shirin shahri", save13),
//                    new City("Boyovut tumani", save13),
//                    new City("Guliston tumani", save13),
//                    new City("Mirzaobod tumani", save13),
//                    new City("Oqoltin tumani", save13),
//                    new City("Sardoba tumani", save13),
//                    new City("Sayxunobod tumani", save13),
//                    new City("Sirdaryo tumani", save13),
//                    new City("Xovos tumani", save13),
//
//
//                    new City("Urganch shahri", save14),
//                    new City("Xiva shahri", save14),
//                    new City("Bog‘ot tumani", save14),
//                    new City("Gurlan tumani", save14),
//                    new City("Urganch tumani", save14),
//                    new City("Xiva tumani", save14),
//                    new City("Xonqa tumani", save14),
//                    new City("Hazorasp tumani", save14),
//                    new City("Shovot tumani", save14),
//                    new City("Yangiariq tumani", save14),
//                    new City("Yangibozor tumani", save14),
//                    new City("Qo‘shko‘pir tumani", save14));
//            cityRepository.saveAll(cities);
//
//        }
//    }
//}