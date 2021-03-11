package net.ujacha.urlshortening.dto;

import lombok.*;

@Data
@Builder
public class ShortUrlDTO {

    private String shortKey;
    private String originalUrl;

}
