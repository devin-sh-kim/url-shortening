package net.ujacha.urlshortening.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.payload.ShortUrlPostReqPayload;
import net.ujacha.urlshortening.service.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/short-url")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity createShortUrl(@RequestBody ShortUrlPostReqPayload payload){

        log.info("originalUrl: {}", payload.getOriginalUrl());

        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(payload.getOriginalUrl());

        log.info("shortUrl: {}", shortUrl);

        return ResponseEntity.ok(shortUrl);

    }

}
