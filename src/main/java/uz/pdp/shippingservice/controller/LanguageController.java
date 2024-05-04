package uz.pdp.shippingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shippingservice.dto.language.CreateTranslateTextDto;
import uz.pdp.shippingservice.entity.languagePs.LanguageBaseWords;
import uz.pdp.shippingservice.service.LanguageService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/create-text")
    public void createMainText(@RequestBody HashMap<String, String> dto) {
        languageService.createMainText(dto);
    }

    @Secured({"admin","translator"})
    @PostMapping("/translate")
    public void createTranslation(@RequestBody CreateTranslateTextDto dto) {
        languageService.createTranslation(dto);
    }

    @Secured({"admin","translator"})
    @GetMapping("/get-all-words")
    public Page<LanguageBaseWords> getAllPaginated(@RequestParam(name = "size", defaultValue = "10") int size,
                                                   @RequestParam(name = "pageNumber", defaultValue = "0")  int page,
                                                   @RequestParam(name = "content" , defaultValue = "null")  String  content) {
        return languageService.getAllPaginated(page, size, content);
    }
    @GetMapping("/get-all-by-language")
    public Map<String , String> getAllByLanguage(@RequestParam(name = "language", defaultValue = "en") String language) {
        return languageService.getAllByLanguage(language);
    }
}
