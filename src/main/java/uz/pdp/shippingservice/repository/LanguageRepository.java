package uz.pdp.shippingservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.shippingservice.entity.languagePs.LanguageBaseWords;


@Repository
public interface LanguageRepository extends JpaRepository<LanguageBaseWords, Integer> {
boolean existsByCategoryAndText(String category, String text);

Page<LanguageBaseWords> findAllByTextContainingIgnoreCase(Pageable pageable, String text);

}