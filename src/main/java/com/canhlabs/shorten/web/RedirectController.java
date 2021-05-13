package com.canhlabs.shorten.web;

import com.canhlabs.shorten.service.ShortenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequestMapping("/")
public class RedirectController {
    ShortenService shortenService;
    @Autowired
    public void injectService(ShortenService service) {
        this.shortenService = service;
    }
    @GetMapping(value = "{id:[r][a-zA-Z0-9]+}")
    public RedirectView redirectUrl(@PathVariable String id) {
        log.info("Received shortened url to redirect: " + id);
        // remove prefix 'r'
        String hash = id.substring(1);
        String redirectUrlString = shortenService.getOriginLink(hash);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrlString);
        return redirectView;
    }
}
