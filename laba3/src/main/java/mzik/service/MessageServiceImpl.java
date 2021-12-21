package mzik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final LocaleServiceImpl localeService;

    public String localize(String code, Object ... params) {
        return messageSource.getMessage(code, params, localeService.getEnglish());
    }

}
