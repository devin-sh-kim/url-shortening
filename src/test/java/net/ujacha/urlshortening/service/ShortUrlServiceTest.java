package net.ujacha.urlshortening.service;

import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.entity.ShortUrl;
import net.ujacha.urlshortening.repository.ShortUrlRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    public void testCreateShortUrl(){

        // 주어진 URL에 대한 ShortKey를 생성한다 (8 character 이하)

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";

        // when
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);

        // then
        assertNotNull(shortUrl);
        assertTrue(StringUtils.isNotBlank(shortUrl.getOriginalUrl()));
        assertEquals(originalUrl, shortUrl.getOriginalUrl());
        assertTrue(StringUtils.isNotBlank(shortUrl.getShortKey()));
        assertTrue(shortUrl.getShortKey().length() <= 8);

        System.out.println("shortUrl = " + shortUrl);
    }

    @Test
    public void testCreateShortUrlAgain(){

        // 동일한 URL에 대해 동일한 ShortKey를 리턴한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";

        // when
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);
        ShortUrlDTO shortUrlAgain = shortUrlService.createShortUrl(originalUrl);

        // then
        assertNotNull(shortUrl);
        assertTrue(StringUtils.isNotBlank(shortUrl.getOriginalUrl()));
        assertEquals(originalUrl, shortUrl.getOriginalUrl());
        assertTrue(StringUtils.isNotBlank(shortUrl.getShortKey()));
        assertTrue(shortUrl.getShortKey().length() <= 8);

        assertNotNull(shortUrlAgain);
        assertTrue(StringUtils.isNotBlank(shortUrlAgain.getOriginalUrl()));
        assertEquals(originalUrl, shortUrlAgain.getOriginalUrl());
        assertTrue(StringUtils.isNotBlank(shortUrlAgain.getShortKey()));
        assertTrue(shortUrlAgain.getShortKey().length() <= 8);

        assertEquals(shortUrl.getShortKey(), shortUrlAgain.getShortKey());

        System.out.println("shortUrl = " + shortUrl);
        System.out.println("shortUrlAgain = " + shortUrlAgain);
    }


    @Test
    public void testGetShortUrl(){

        // shortKey 에 대한 원본 URL 을 리턴한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);
        System.out.println("shortUrl = " + shortUrl);


        // when
        ShortUrlDTO findShortUrl = shortUrlService.getShortUrl(shortUrl.getShortKey());

        // then
        assertNotNull(findShortUrl);
        assertEquals(shortUrl.getShortKey(), findShortUrl.getShortKey());
        assertEquals(originalUrl, findShortUrl.getOriginalUrl());

        System.out.println("findShortUrl = " + findShortUrl);

    }

    @Test
    public void testNewCreateRequestCount(){
        // 새로운 URL 이 생성 되면 생성 카운트는 1이어야 한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening?" + System.currentTimeMillis();

        // when
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);
        assertNotNull(shortUrl);

        // then
        ShortUrl findShortUrl = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        assertNotNull(findShortUrl);
        int createRequestCount = findShortUrl.getCreateRequestCount();
        System.out.println("createRequestCount = " + createRequestCount);

        assertEquals(1, createRequestCount);

    }

    @Test
    public void testIncCreateRequestCount(){

        // 동일한 URL 의 생성 요청이 발생하면 해당 Short Url의 생성 요청 카운트가 1 증가한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);

        ShortUrl checkCountBefore = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        assertNotNull(checkCountBefore);
        int before = checkCountBefore.getCreateRequestCount();
        System.out.println("before = " + before);

        // when
        ShortUrlDTO createAgain = shortUrlService.createShortUrl(originalUrl);
        assertNotNull(createAgain);

        // then
        ShortUrl checkCountAfter = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        assertNotNull(checkCountAfter);
        int after = checkCountAfter.getCreateRequestCount();
        System.out.println("after = " + after);

        assertEquals(before+ 1, after);

    }

    @Test
    public void testNewRedirectRequestCount(){
        // 새로운 URL 이 생성 되면 변환 카운트는 0이어야 한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening?" + System.currentTimeMillis();

        // when
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);
        assertNotNull(shortUrl);

        // then
        ShortUrl findShortUrl = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        assertNotNull(findShortUrl);
        int redirectRequestCount = findShortUrl.getRedirectRequestCount();
        System.out.println("redirectRequestCount = " + redirectRequestCount);

        assertEquals(0, redirectRequestCount);

    }


    @Test
    public void testIncRedirectRequestCount(){

        // Short Key 에 대한 변환 요청이 발생하면 해당 Short Url의 변환 요청 카운트가 1 증가한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);

        ShortUrl checkCountBefore = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        assertNotNull(checkCountBefore);
        int before = checkCountBefore.getRedirectRequestCount();
        System.out.println("before = " + before);

        // when
        ShortUrlDTO findShortUrl = shortUrlService.getShortUrl(shortUrl.getShortKey());
        assertNotNull(findShortUrl);

        // then
        ShortUrl checkCountAfter = shortUrlRepository.findByOriginalUrl(originalUrl).orElse(null);
        assertNotNull(checkCountAfter);
        int after = checkCountAfter.getRedirectRequestCount();
        System.out.println("after = " + after);

        assertEquals(before + 1, after);

    }
}
