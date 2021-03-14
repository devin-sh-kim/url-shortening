package net.ujacha.urlshortening.controller;

import net.ujacha.urlshortening.dto.ShortUrlDTO;
import net.ujacha.urlshortening.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RedirectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void testFindAndRedirect() throws Exception {

        // 생성된 Short Key 를 전달하면, Http Status Code 301, Header 에 Location: originalUrl 을 응답한다

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        ShortUrlDTO shortUrl = shortUrlService.createShortUrl(originalUrl);
        assertNotNull(shortUrl);
        assertTrue(StringUtils.isNotBlank(shortUrl.getShortKey()));

        // when
        // then
        mockMvc.perform(get("/" + shortUrl.getShortKey()))
                .andDo(print())
        .andExpect(status().isMovedPermanently())
        .andExpect(header().string("Location", originalUrl))
        ;
    }

}