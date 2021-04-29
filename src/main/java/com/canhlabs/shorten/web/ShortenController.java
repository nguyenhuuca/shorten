package com.canhlabs.shorten.web;

import com.canhlabs.shorten.facade.ShortenFacade;
import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.ResultObjectInfo;
import com.canhlabs.shorten.share.dto.ShortenRequestDto;
import com.canhlabs.shorten.share.enums.ResultStatus;
import com.canhlabs.shorten.validator.ShortenValidator;
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

    ShortenValidator shortenValidator;

    @Autowired
    public void injectQueue(ShortenFacade shortenFacade) {
        this.shortenFacade = shortenFacade;
    }

    @Autowired
    public void injectValidator(ShortenValidator validator) {
        this.shortenValidator = validator;
    }

    /**
     * API using to generate the shorten link
     * This API do not need any permission
     *
     * @param request url from client
     * @return shorten link
     */
    @PostMapping
    public ResponseEntity<ResultObjectInfo<String>> generateToken(@RequestBody ShortenRequestDto request) {
        shortenValidator.validate(request.getUrl());
        return new ResponseEntity<>(ResultObjectInfo.<String>builder()
                .status(ResultStatus.SUCCESS)
                .data(shortenFacade.shortenLink(request.getUrl()))
                .message("Get data succeed")
                .build(), HttpStatus.OK);
    }

}
