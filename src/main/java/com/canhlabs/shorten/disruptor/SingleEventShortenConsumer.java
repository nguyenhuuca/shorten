package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.service.ShortenService;
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
    public EventHandler<ValueEvent<String>> getEventHandler() {
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
    public void shortenHandler(ValueEvent<String> valueEvent, long sequence, boolean endOfBatch) {
        log.info("Id is " + valueEvent.getValue()
                + " sequence id that was used is " + sequence + " " + endOfBatch);
        String shorLink = shortenService.shortenLink(valueEvent.getValue());
        valueEvent.setValue(shorLink);

    }
}
