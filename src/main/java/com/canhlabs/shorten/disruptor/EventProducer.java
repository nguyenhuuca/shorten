package com.canhlabs.shorten.disruptor;

/**
 * Producer event create/populate data and push it to queue
 */
public interface EventProducer {
    /**
     * Start the producer that would start producing the values.
     */
    void startProducing(ValueEvent<String> data);
}