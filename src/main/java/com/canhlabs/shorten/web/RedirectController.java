package com.canhlabs.shorten.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequestMapping()
public class RedirectController {
    @GetMapping(value = "{id:^(r)+}")
    public RedirectView redirectUrl(@PathVariable String id) {
        log.info("Received shortened url to redirect: " + id);
        String redirectUrlString = "yahoo.com/" + id;
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + redirectUrlString);
        return redirectView;
    }
}
