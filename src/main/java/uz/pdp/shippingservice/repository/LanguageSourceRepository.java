package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.shippingservice.entity.language.LanguageSource;


import java.util.List;


@Repository
public interface LanguageSourceRepository extends JpaRepository<LanguageSource, Integer> {

    List<LanguageSource> findAllByLanguage(String language);

    List<LanguageSource> findAllByLanguageBaseWordsId(Integer languageBaseWordId);
}