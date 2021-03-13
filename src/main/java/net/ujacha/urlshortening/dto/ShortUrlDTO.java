package net.ujacha.urlshortening.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ShortUrlDTO {

    private String shortKey;
    private String originalUrl;
    private LocalDateTime createdAt;
    private int createRequestCount;
    private int redirectRequestCount;
}
