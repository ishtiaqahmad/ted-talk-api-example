package nl.ted.tech.tedTalkAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TedTalkNotFoundException extends RuntimeException {
    public TedTalkNotFoundException() {
        super();
    }

    public TedTalkNotFoundException(String exception) {
        super(exception);
    }


}
