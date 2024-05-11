package uz.pdp.shippingservice.entity.language;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "language_base_words")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class LanguageBaseWords implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private String text;
    private String lang;

    @OneToMany(mappedBy = "languageBaseWords",fetch = FetchType.EAGER)
    private List<LanguageSource> languageSource;

    public LanguageBaseWords(@JsonProperty("id") Integer id,
                             @JsonProperty("category") String category,
                             @JsonProperty("text") String text,
                             @JsonProperty("lang") String lang,
                             @JsonProperty("language_base_word_id") List<LanguageSource> languageSource) {
        this.id = id;
        this.category = category;
        this.text = text;
        this.lang = lang;
        this.languageSource = languageSource;
    }
}