package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.share.dto.AuditLogDto;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Audit Consumer handle message from Audit Producer
 */
@Slf4j
@Component
public class SingleEventAuditConsumer implements EventConsumer<AuditLogDto> {

    @Override
    public EventHandler<ValueEvent<AuditLogDto>> getEventHandler() {
        // (event, sequence, endOfBatch) -> shortenHandler(event.getValue(), sequence, endOfBatch)
        return this::auditHandler;
    }

    /**
     * Using to write audit log
     *
     * @param valueEvent hold the audit info that send from producer
     * @param sequence   the current cursor in RingBuffer data structure
     * @param endOfBatch true if sequence have a position at end RingBuffer
     */
    public void auditHandler(ValueEvent<AuditLogDto> valueEvent, long sequence, boolean endOfBatch) {
        log.info("Action: {}", valueEvent.getValue().getAction());
        log.info("Content send {}", valueEvent.getValue().getContentSend());
        log.info("IP call: {}", valueEvent.getValue().getIp());
        log.info("Audit log" + " sequence id that was used is " + sequence + " " + endOfBatch);


    }
}
