package net.ujacha.urlshortening.service;

import lombok.RequiredArgsConstructor;
import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.entity.ShortUrl;
import net.ujacha.urlshortening.repository.ShortUrlRepository;
import net.ujacha.urlshortening.util.Base62Support;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    @Value("${short-url.codec:0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz}")
    String codec;

    @Value("${short-url.offset:10000000000}")
    private long offset;

    @Transactional
    public ShortUrlDTO createShortUrl(String originalUrl) {
        // 1. check exist original url
        // 1.1 exist original url
        //      -> inc create count, save
        //      -> return find short url

        ShortUrl shortUrl = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        if (shortUrl != null) {
            shortUrl.incCreateRequestCount();
            return entityToDTO(shortUrl);
        }

        // 2. build short url entity
        shortUrl = new ShortUrl(originalUrl);
        shortUrlRepository.saveAndFlush(shortUrl);

        // 3. generate short key
        // 4. return short url
        return entityToDTO(shortUrl);
    }

    @Transactional
    public ShortUrlDTO getShortUrl(String shortKey) {

        // 1. find by short key
        ShortUrl shortUrl = shortUrlRepository.findById(Base62Support.decode(codec, offset, shortKey)).orElse(null);

        // 1.1 not found -> return null
        if (shortUrl == null) {
            return null;
        }

        // 2. inc redirect count
        shortUrl.incRedirectRequestCount();

        // 3. return short url dto
        return entityToDTO(shortUrl);
    }

    private ShortUrlDTO entityToDTO(ShortUrl shortUrl) {

        if (shortUrl == null) return null;

        return ShortUrlDTO.builder()
                .shortKey(Base62Support.encode(codec, offset, shortUrl.getSeq()))
                .originalUrl(shortUrl.getOriginalUrl())
                .build();
    }

    public List<ShortUrlDTO> getShortUrlStats(String sort, String filter, int rows) {

        PageRequest pageRequest = PageRequest.of(0, rows, Sort.by(Sort.Order.desc(sort)));

        Page<ShortUrl> page = shortUrlRepository.findAllByFilter(filter, pageRequest);

        return page.get().map(shortUrl -> ShortUrlDTO.builder()
                .shortKey(Base62Support.encode(codec, offset, shortUrl.getSeq()))
                .originalUrl(shortUrl.getOriginalUrl())
                .createRequestCount(shortUrl.getCreateRequestCount())
                .redirectRequestCount(shortUrl.getRedirectRequestCount())
                .createdAt(shortUrl.getCreatedAt())
                .build()).collect(Collectors.toList());
    }
}
