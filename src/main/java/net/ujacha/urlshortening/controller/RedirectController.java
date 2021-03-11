package net.ujacha.urlshortening.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.service.ShortUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class RedirectController {

    private final ShortUrlService shortUrlService;

    @GetMapping("{shortKey}")
    public ResponseEntity findAndRedirect(
            @PathVariable String shortKey
    ){

        ShortUrlDTO shortUrl = shortUrlService.getShortUrl(shortKey);
        if(shortUrl == null){
            return ResponseEntity.notFound().build();
        }

        URI targetUri = URI.create(shortUrl.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(targetUri).build();
    }

}
