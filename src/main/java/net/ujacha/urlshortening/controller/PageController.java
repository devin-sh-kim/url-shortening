package net.ujacha.urlshortening.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class PageController {

    @GetMapping()
    public String createShortUrlPage() {

        return "create-short-url";
    }

    @GetMapping("p/stats")
    public String statsPage() {

        return "stats";
    }

}
