package ru.otus.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.LocaleHolder;
import ru.otus.service.LocalizedMessageService;

@Service
public class LocalizedMessageServiceImpl implements LocalizedMessageService {

    private final MessageSource messageSource;

    private final LocaleHolder localeHolder;

    public LocalizedMessageServiceImpl(MessageSource messageSource, LocaleHolder localeHolder){
        this.messageSource = messageSource;
        this.localeHolder = localeHolder;
    }

    @Override
    public String getMessage(String key, Object[] args) {
        return messageSource.getMessage(
                key,args, localeHolder.getLocale());
    }
}
