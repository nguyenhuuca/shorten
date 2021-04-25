package com.canhlabs.shorten.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadFactory;

@Component
@Slf4j
public class SingleEventShortenProducer implements EventProducer {

    /**
     * using to save shorten to database
     */
    SingleEventShortenConsumer singleEventShortenConsumer;

    @Autowired
    public void injectConsumer(SingleEventShortenConsumer consumer) {
        this.singleEventShortenConsumer = consumer;
    }

    RingBuffer<ValueEvent<String>> ringBuffer;

    /**
     * start Disruptor for application
     */
    @PostConstruct
    public void init() {
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

        WaitStrategy waitStrategy = new BusySpinWaitStrategy();
        Disruptor<ValueEvent<String>> disruptor
                = new Disruptor<>(
                ValueEvent.EVENT_FACTORY,
                1024,
                threadFactory,
                ProducerType.SINGLE,
                waitStrategy);
        disruptor.handleEventsWith(singleEventShortenConsumer.getEventHandler());
        this.ringBuffer = disruptor.start();
        log.info("Disruptor for shorten event was started....");

    }

    @Override
    public void startProducing(ValueEvent<String> data) {
        produce(ringBuffer, data);
    }

    private void produce(final RingBuffer<ValueEvent<String>> ringBuffer, ValueEvent<String> data) {
        long sequenceId = ringBuffer.next();
        ValueEvent<String> valueEvent = ringBuffer.get(sequenceId);
        valueEvent.setValue(data.getValue());
        ringBuffer.publish(sequenceId);

    }
}
