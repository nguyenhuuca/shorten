package com.canhlabs.shorten.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Receive the message(info data) from producer and handle business based on message.
 */
public interface EventConsumer {

     /**
      * One event handler to handle event from ring buffer.
      */
    EventHandler<ValueEvent<String>> getEventHandler();
}
