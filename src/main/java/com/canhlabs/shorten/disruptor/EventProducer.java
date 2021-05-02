package com.canhlabs.shorten.disruptor;

/**
 * Producer event create/populate data and push it to queue(ring buffer)
 */
public interface EventProducer<T> {
    /**
     * Start the producer that would start producing the values.
     */
    void startProducing(ValueEvent<T> data);

}