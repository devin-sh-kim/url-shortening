package net.ujacha.urlshortening.controller;

import lombok.extern.slf4j.Slf4j;
import net.ujacha.urlshortening.exception.BadRequestException;
import net.ujacha.urlshortening.exception.NotFoundShortKey;
import net.ujacha.urlshortening.payload.ErrorResPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class MvcAdvice {

    @ModelAttribute("host")
    private String buildHost(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme()).append("://").append(request.getServerName());
        int port = request.getServerPort();
        if (port != 80 && port != 443) {
            sb.append(":").append(port);
        }
        sb.append(request.getContextPath()).append("/");

        return sb.toString();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleBadRequest(BadRequestException ex) {

        return ResponseEntity
                .badRequest()
                .body(ErrorResPayload.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleError(Exception ex) {

        log.error("INTERNAL_SERVER_ERROR: {}", ex.getMessage(), ex);

        return ResponseEntity
                .badRequest()
                .body(ErrorResPayload.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(NotFoundShortKey.class)
    public String handleNotFoundShortKey(NotFoundShortKey ex, Model model) {
        model.addAttribute("shortKey", ex.getMessage());
        return "error/not-found-short-key";
    }


}
