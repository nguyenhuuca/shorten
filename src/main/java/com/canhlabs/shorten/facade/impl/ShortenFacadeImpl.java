package com.canhlabs.shorten.facade.impl;

import com.canhlabs.shorten.disruptor.EventProducer;
import com.canhlabs.shorten.share.AppProperties;
import com.canhlabs.shorten.disruptor.SingleEventShortenProducer;
import com.canhlabs.shorten.disruptor.ValueEvent;
import com.canhlabs.shorten.facade.ShortenFacade;
import com.canhlabs.shorten.service.KeyGenerateService;
import com.canhlabs.shorten.share.dto.ShortenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShortenFacadeImpl implements ShortenFacade {
    private KeyGenerateService kgs;
    private EventProducer<ShortenDto> producer;
    private AppProperties appProps;


    // setter injection all dependency
    @Autowired
    public void injectKGS(KeyGenerateService kgs) {
        this.kgs = kgs;
    }

    @Autowired
    public void injectQueue(SingleEventShortenProducer singleEventShortenProducer) {
        this.producer = singleEventShortenProducer;
    }

    @Autowired
    public void injectProps(AppProperties appProps) {
        this.appProps = appProps;
    }

    // end injection

    /**
     *
     * After generate the unique key, make the async event to save information to database
     *
     */
    @Override
    public String shortenLink(String url) {
        String hash = kgs.generate();
        String shortLink = appProps.getBaseDomain().concat(appProps.getPrefixRedirect()).concat(hash);
        ShortenDto shortenDto = ShortenDto.builder()
                .hash(hash)
                .originUrl(url)
                .build();
        // async process
        producer.startProducing(ValueEvent.<ShortenDto>builder().value(shortenDto).build());

        return shortLink;
    }
}
