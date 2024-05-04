package uz.pdp.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.pdp.shippingservice.dto.language.CreateTranslateTextDto;
import uz.pdp.shippingservice.dto.language.LanguageEnum;
import uz.pdp.shippingservice.entity.languagePs.LanguageBaseWords;
import uz.pdp.shippingservice.entity.languagePs.LanguageSource;
import uz.pdp.shippingservice.repository.LanguageRepository;
import uz.pdp.shippingservice.repository.LanguageSourceRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;
    private final LanguageSourceRepository languageSourceRepository;
    private final JdbcTemplate jdbcTemplate;

    @Value("${languages.primaryLang}")
    String primaryLang;

    public void createMainText(HashMap<String, String> dto) {
        String newWord = "";
        for (Map.Entry<String, String> entry : dto.entrySet()) {
            newWord = entry.getValue();
        }
        if (!languageRepository.existsByCategoryAndText("credit", newWord)) {
            LanguageBaseWords languageBaseWords = LanguageBaseWords.builder()
                    .text(newWord)
                    .category("credit")
                    .lang(primaryLang)
                    .build();
            languageRepository.save(languageBaseWords);
        }
    }


    public void createTranslation(CreateTranslateTextDto dto) {
        try {
            Optional<LanguageBaseWords> byId = languageRepository.findById(dto.getId());
            if (byId.isPresent()) {
                LanguageBaseWords languageBaseWords = byId.get();
                List<LanguageSource> allByIdId = languageSourceRepository.findAllByLanguageBaseWordsId(languageBaseWords.getId());
                if (!allByIdId.isEmpty()) {
                    LanguageSource uz = allByIdId.get(0);
                    uz.setTranslation(dto.getTranslations().get(LanguageEnum.Uz) !=null ? dto.getTranslations().get(LanguageEnum.Uz)  : uz.getTranslation());
                    uz.setLanguage("Uz");

                    LanguageSource ru = allByIdId.get(1);
                    ru.setTranslation(dto.getTranslations().get(LanguageEnum.Ru) !=null ? dto.getTranslations().get(LanguageEnum.Ru)  : ru.getTranslation());
                    ru.setLanguage("Ru");

                    LanguageSource en = allByIdId.get(2);
                    en.setTranslation(dto.getTranslations().get(LanguageEnum.En) !=null ? dto.getTranslations().get(LanguageEnum.En)  : en.getTranslation());
                    en.setLanguage("En");

                    languageSourceRepository.saveAll(allByIdId);
                    return;
                }
                HashMap<LanguageEnum, String> translations = dto.getTranslations();

                languageSourceRepository.save(new LanguageSource(languageBaseWords, "Uz", translations.get(LanguageEnum.Uz) !=null ? translations.get(LanguageEnum.Uz) : null));
                languageSourceRepository.save(new LanguageSource(languageBaseWords, "Ru", translations.get(LanguageEnum.Ru) !=null ? translations.get(LanguageEnum.Ru) : null));
                languageSourceRepository.save(new LanguageSource(languageBaseWords, "En", translations.get(LanguageEnum.En)!=null ? translations.get(LanguageEnum.En) : null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Page<LanguageBaseWords> getAllPaginated(int page, int size , String content) {
        Pageable pageable = PageRequest.of(page, size);
        if (content.equals("null")) {
            return languageRepository.findAll(pageable);
        }
        return languageRepository.findAllByTextContainingIgnoreCase(pageable, content);
    }

    public Map<String , String> getAllByLanguage(String language) {
        List<LanguageBaseWords> allByLanguageBaseWords = languageRepository.findAll();
        Map<String , String> languageSourceMap = new HashMap<String, String>();
        if (!allByLanguageBaseWords.isEmpty()){
            for (LanguageBaseWords languageBaseWordsPs : allByLanguageBaseWords) {
                for (LanguageSource languageSource : languageBaseWordsPs.getLanguageSource()) {
                    if (languageSource.getTranslation() !=null && languageSource.getLanguageBaseWords().equals(language)){
                        languageSourceMap.put(languageBaseWordsPs.getText() , languageSource.getTranslation());
                    }
//                    else if (languageSourceP.getTranslation() ==null ) {
//                            languageSourceMap.put(languagePs.getText() , null);
//                    }
                }
            }
        }
        return languageSourceMap;
    }

}
