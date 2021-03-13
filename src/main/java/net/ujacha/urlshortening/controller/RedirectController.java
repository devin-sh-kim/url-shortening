package net.ujacha.urlshortening.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.exception.BadRequestException;
import net.ujacha.urlshortening.exception.NotFoundShortKey;
import net.ujacha.urlshortening.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.CacheControl;
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

    @GetMapping("{shortKey:[a-zA-Z0-9]+}")
    public ResponseEntity findAndRedirect(
            @PathVariable String shortKey
    ){

        if(StringUtils.isBlank(shortKey) || shortKey.length() < 6 || shortKey.length() > 8){
            throw new NotFoundShortKey(shortKey);
        }

        ShortUrlDTO shortUrl = shortUrlService.getShortUrl(shortKey);
        if(shortUrl == null){
            throw new NotFoundShortKey(shortKey);
        }

        URI targetUri = URI.create(shortUrl.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(targetUri)
                .cacheControl(CacheControl.noCache())
                .build();
    }



}
