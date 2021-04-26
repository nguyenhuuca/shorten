package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.service.ShortenService;
import com.canhlabs.shorten.share.dto.ShortenDto;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shorten Consumer handle message from Shorten Producer
 */
@Slf4j
@Component
public class SingleEventShortenConsumer implements EventConsumer {
    ShortenService shortenService;

    @Autowired
    public void injectShortenService(ShortenService service) {
        this.shortenService = service;
    }

    @Override
    public EventHandler<ValueEvent<ShortenDto>> getEventHandler() {
        // (event, sequence, endOfBatch) -> shortenHandler(event.getValue(), sequence, endOfBatch)
        return this::shortenHandler;
    }

    /**
     * Using to call shorten Service
     *
     * @param valueEvent hold the url that send from producer
     * @param sequence   the current cursor in RingBuffer data structure
     * @param endOfBatch true if sequence have a position at end RingBuffer
     */
    public void shortenHandler(ValueEvent<ShortenDto> valueEvent, long sequence, boolean endOfBatch) {
        log.info("hash  is " + valueEvent.getValue().getHash()
                + " sequence id that was used is " + sequence + " " + endOfBatch);
        shortenService.saveShortenLink(valueEvent.getValue());

    }
}
