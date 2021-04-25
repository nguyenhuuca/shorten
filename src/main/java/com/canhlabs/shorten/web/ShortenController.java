package com.canhlabs.shorten.web;

import com.canhlabs.shorten.disruptor.SingleEventShortenProducer;
import com.canhlabs.shorten.disruptor.ValueEvent;
import com.canhlabs.shorten.facade.ShortenFacade;
import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.ResultObjectInfo;
import com.canhlabs.shorten.share.enums.ResultStatus;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Using to create a shorten
 */
@RestController
@RequestMapping(AppConstant.API.BASE_URL)
@Api(tags = {AppConstant.API.TAG_SHORTEN})
@Validated
@Slf4j
public class ShortenController extends BaseController {

    ShortenFacade shortenFacade;

    @Autowired
    public void injectQueue(ShortenFacade shortenFacade) {
        this.shortenFacade = shortenFacade;
    }

    /**
     * API using to generate the shorten link
     * This API do not need any permission
     *
     * @param url from client
     * @return shorten link
     */
    @PostMapping
    public ResponseEntity<ResultObjectInfo<String>> generateToken(@RequestBody String url) {
        return new ResponseEntity<>(ResultObjectInfo.<String>builder()
                .status(ResultStatus.SUCCESS)
                .data(shortenFacade.shortenLink(url))
                .message("Get data succeed")
                .build(), HttpStatus.OK);
    }
}
