package net.ujacha.urlshortening.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateShortUrl() throws Exception {

        // short key 생성
        // 정상적인 생성 요청에 대해 shortKey, originalUrl 을 포함한 응답 확인
        // shortKey : 6~8 자리 이하의 대문자+소문자+숫자 조합의 문자열
        // originalUrl : 요청한 originalUrl 과 동일 값

        // 요청 예시
        // POST /api/v1/short-url
        // Content-Type: application/json
        // {"originalUrl":"https://en.wikipedia.org/wiki/URL_shortening"}

        // 응답 예시
        // Content-Type: application/json
        // {"shortKey":"Aukyob","originalUrl":"https://en.wikipedia.org/wiki/URL_shortening"}

        // given
        String originalUrl = "https://en.wikipedia.org/wiki/URL_shortening";
        String payload = String.format("{\"originalUrl\":\"%s\"}", originalUrl);

        // when

        // then
        requestCreateShoutUrl(payload)
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("originalUrl").value(originalUrl))
                .andExpect(jsonPath("shortKey").isNotEmpty())
        ;
    }

    @Test
    public void testCreateShortUrlBadRequest() throws Exception {

        // 잘못된 요청일 경우 Http Status Code 400 을 리턴한다

        // payload 에 필수 필드(originalUrl)가 없는경우
        requestCreateShoutUrl("{}")
                .andExpect(status().isBadRequest());

        // payload 에 필수 필드(originalUrl)가 빈 값인 경우
        requestCreateShoutUrl("{\"originalUrl\": \"\"}")
                .andExpect(status().isBadRequest());

        // payload 에 originalUrl이 잘못된 URL 형식인 경우
        requestCreateShoutUrl("{\"originalUrl\": \"aaa\"}")
                .andExpect(status().isBadRequest());

        // payload 에 originalUrl이 잘못된 URL 형식인 경우
        requestCreateShoutUrl("{\"originalUrl\": \"http://aaa\"}")
                .andExpect(status().isBadRequest());

        // payload 에 originalUrl이 잘못된 URL 형식인 경우
        requestCreateShoutUrl("{\"originalUrl\": \"httpss://en.wikipedia.org/wiki/URL_shortening\"}")
                .andExpect(status().isBadRequest());

    }

    private ResultActions requestCreateShoutUrl(String payload) throws Exception {
        MockHttpServletRequestBuilder post = post("/api/v1/short-url");

        post.content(payload);

        return mockMvc.perform(post
                .contentType(MediaType.APPLICATION_JSON.toString())
        ).andDo(print());
    }

}
