package net.ujacha.urlshortening.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class PageController {

    @GetMapping()
    public String createShortUrlPage(HttpServletRequest request, Model model){

        String host = buildHost(request);

        model.addAttribute("host", host);

        return "create-short-url";
    }

    private String buildHost(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme()).append("://").append(request.getServerName());
        int port = request.getServerPort();
        if (port != 80 && port != 443) {
            sb.append(":").append(port);
        }
        sb.append(request.getContextPath()).append("/");

        return sb.toString();
    }

}
