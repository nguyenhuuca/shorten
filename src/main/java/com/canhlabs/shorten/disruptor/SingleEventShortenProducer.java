package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.share.dto.ShortenDto;
import com.lmax.disruptor.BlockingWaitStrategy;
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
public class SingleEventShortenProducer implements EventProducer<ShortenDto> {

    /**
     * using to save shorten to database
     */
    private EventConsumer<ShortenDto> consumer;
    private RingBuffer<ValueEvent<ShortenDto>> ringBuffer;

    @Autowired
    public void injectConsumer(SingleEventShortenConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * start Disruptor for application
     */
    @PostConstruct
    public void init() {
        ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;

        // BlockingWaitStrategy() -> slow but avoid high load cpu, using in-case limit cpu
        // SleepingWaitStrategy() -> similar BlockingWaitStrategy, using in case need to write log.
        // YieldingWaitStrategy() -> Using in case need  high performance
        //  BusySpinWaitStrategy() -> best performance,
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        Disruptor<ValueEvent<ShortenDto>> disruptor
                = new Disruptor<>(
                ValueEvent.EVENT_SHORTEN_FACTORY,
                1024,
                threadFactory,
                ProducerType.SINGLE,
                waitStrategy);
        disruptor.handleEventsWith(consumer.getEventHandler());
        this.ringBuffer = disruptor.start();
        log.info("Disruptor for shorten event was started....");

    }

    /**
     * Transport url data to rings buffer
     * @param data hold the data need to push to ringBuffer
     */
    @Override
    public void startProducing(ValueEvent<ShortenDto> data) {
        produce(ringBuffer, data);
    }

    private void produce(final RingBuffer<ValueEvent<ShortenDto>> ringBuffer, ValueEvent<ShortenDto> data) {
        long sequenceId = ringBuffer.next();
        ValueEvent<ShortenDto> valueEvent = ringBuffer.get(sequenceId);
        valueEvent.setValue(data.getValue());
        ringBuffer.publish(sequenceId);


    }
}
