package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.share.dto.ShortenDto;

/**
 * Producer event create/populate data and push it to queue
 */
public interface EventProducer {
    /**
     * Start the producer that would start producing the values.
     */
    void startProducing(ValueEvent<ShortenDto> data);
}