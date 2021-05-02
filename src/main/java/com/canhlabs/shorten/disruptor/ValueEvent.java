package com.canhlabs.shorten.disruptor;

import com.canhlabs.shorten.share.dto.AuditLogDto;
import com.canhlabs.shorten.share.dto.ShortenDto;
import com.lmax.disruptor.EventFactory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValueEvent<T> {
    /**
     * the value need to handle in queue lmax
     */
    private T value;


    /**
     * create new instance ValueEvent
     */
    public static final EventFactory<ValueEvent<ShortenDto>> EVENT_SHORTEN_FACTORY = ValueEvent::new;

    public static final EventFactory<ValueEvent<AuditLogDto>> EVENT_AUDIT_FACTORY = ValueEvent::new;
}
