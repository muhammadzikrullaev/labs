package mzik.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Getter
@Setter
public class LocaleServiceImpl implements LocaleService{

    private Locale english = Locale.ENGLISH;
    private Locale current = Locale.forLanguageTag("ru");
}
