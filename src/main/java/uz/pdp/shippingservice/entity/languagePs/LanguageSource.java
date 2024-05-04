package uz.pdp.shippingservice.entity.languagePs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "language_source")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LanguageSource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private LanguageBaseWords languageBaseWords;

    private String language;

    private String translation;

    public LanguageSource(LanguageBaseWords languageBaseWords, String language, String translation) {
        this.languageBaseWords = languageBaseWords;
        this.language = language;
        this.translation = translation;
    }

    public LanguageSource(@JsonProperty("id") Integer id,
                          @JsonProperty("language_ps_id") LanguageBaseWords languageBaseWords,
                          @JsonProperty("language") String language,
                          @JsonProperty("translation") String translation) {
        this.id = id;
        this.languageBaseWords = languageBaseWords;
        this.language = language;
        this.translation = translation;
    }
}