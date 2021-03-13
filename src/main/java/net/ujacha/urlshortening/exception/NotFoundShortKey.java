package net.ujacha.urlshortening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundShortKey extends RuntimeException {

    public NotFoundShortKey(String message) {
        super(message);
    }
}
