package net.ujacha.urlshortening.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrl {

    @Id
    @SequenceGenerator(name = "short_key_seq", sequenceName = "short_key_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "short_key_seq", strategy = GenerationType.SEQUENCE)
    private Long seq;

//    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(8)")
//    private String shortKey;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(2000)")
    private String originalUrl;

    @Column(nullable = false)
    private Integer createRequestCount;

    @Column(nullable = false)
    private Integer redirectRequestCount = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt;

//    @Column(nullable = false)
//    private LocalDateTime expireAt;

    @Builder
    public ShortUrl(String originalUrl){
        this.originalUrl = originalUrl;
        this.createRequestCount = 1;
        this.redirectRequestCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void incCreateRequestCount() {
        this.createRequestCount += 1;
    }

    public void incRedirectRequestCount() {
        this.redirectRequestCount += 1;
    }
}
