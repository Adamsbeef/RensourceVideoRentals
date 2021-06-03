package com.example.rensourcevideorentals.setup;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageHelperService {

    private final MessageSource messageSource;

    public MessageHelperService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String resource) {
        return messageSource.getMessage(resource, null, LocaleContextHolder.getLocale());
    }

}
