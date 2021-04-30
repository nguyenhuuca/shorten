package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.share.dto.AuditLogDto;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
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
public class SingleEventAuditProducer implements EventProducer<AuditLogDto> {

    /**
     * using to write audit log
     */
    private EventConsumer<AuditLogDto> consumer;
    private RingBuffer<ValueEvent<AuditLogDto>> ringBuffer;

    @Autowired
    public void injectConsumer(SingleEventAuditConsumer consumer) {
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
        WaitStrategy waitStrategy = new SleepingWaitStrategy();
        Disruptor<ValueEvent<AuditLogDto>> disruptor
                = new Disruptor<>(
                ValueEvent.EVENT_AUDIT_FACTORY,
                1024,
                threadFactory,
                ProducerType.SINGLE,
                waitStrategy);
        disruptor.handleEventsWith(consumer.getEventHandler());
        this.ringBuffer = disruptor.start();

    }

    /**
     * Transport audit data to rings buffer
     * @param data hold the data need to push to ringBuffer
     */
    @Override
    public void startProducing(ValueEvent<AuditLogDto> data) {
        produce(ringBuffer, data);
    }

    private void produce(final RingBuffer<ValueEvent<AuditLogDto>> ringBuffer, ValueEvent<AuditLogDto> data) {
        long sequenceId = ringBuffer.next();
        ValueEvent<AuditLogDto> valueEvent = ringBuffer.get(sequenceId);
        valueEvent.setValue(data.getValue());
        ringBuffer.publish(sequenceId);
    }
}
