package net.ujacha.urlshortening.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.exception.BadRequestException;
import net.ujacha.urlshortening.payload.ShortUrlPostReqPayload;
import net.ujacha.urlshortening.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ApiController {

    private final ShortUrlService shortUrlService;

    @PostMapping("short-url")
    public ResponseEntity createShortUrl(@RequestBody ShortUrlPostReqPayload payload) {

        // validate
        if (payload == null) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        if (StringUtils.isBlank(payload.getOriginalUrl())) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        if (!payload.getOriginalUrl().startsWith("http://") && !payload.getOriginalUrl().startsWith("https://")) {
            payload.setOriginalUrl("http://" + payload.getOriginalUrl());
        }

        if (!UrlValidator.getInstance().isValid(payload.getOriginalUrl())) {
            throw new BadRequestException("잘못된 URL입니다.");
        }

        if (payload.getOriginalUrl().length() > 2000) {
            throw new BadRequestException("URL은 2000자를 넘을 수 없습니다.");
        }

        log.debug("originalUrl: {}", payload.getOriginalUrl());

        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(payload.getOriginalUrl());

        log.debug("shortUrl: {}", shortUrl);

        return ResponseEntity.ok().body(shortUrl);

    }

    @GetMapping("stats")
    public ResponseEntity getStats(
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "createdAt") String sort,
            @RequestParam(required = false, defaultValue = "10") int rows
    ) {

        List<ShortUrlDTO> stats = shortUrlService.getShortUrlStats(sort, filter, rows);

        return ResponseEntity.ok(stats);
    }

}
