package com.canhlabs.shorten.config.aop;

import com.canhlabs.shorten.disruptor.SingleEventAuditProducer;
import com.canhlabs.shorten.disruptor.ValueEvent;
import com.canhlabs.shorten.share.AppUtils;
import com.canhlabs.shorten.share.dto.AuditLogDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.canhlabs.shorten.share.AppUtils.JSON;

@Aspect
@Component
@Slf4j
public class LoggingHandler {
    private SingleEventAuditProducer auditProducer;
    @Autowired
    public void injectProducer(SingleEventAuditProducer producer) {
        this.auditProducer = producer;
    }
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
        // using for AOP
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
        // using for AOP
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteMapping() {
        // using for AOP
    }

    //Around -> Any method within resource annotated with @Controller annotation
    @Around("controller() && (postMapping() || deleteMapping() ) && args(.., @RequestBody body)")
    public Object logAround(ProceedingJoinPoint joinPoint, Object body) throws Throwable {
        logInfo(joinPoint, body);
        return joinPoint.proceed();
    }

    private void logInfo(ProceedingJoinPoint joinPoint, Object body) {
        String ip = AppUtils.getClientIP();
        String method = joinPoint.getSignature().getName();
        AuditLogDto info = AuditLogDto.builder()
                .ip(ip)
                .action(method)
                .contentSend(getValue(body))
                .build();
        auditProducer.startProducing(ValueEvent.<AuditLogDto>builder().value(info).build());

    }

    private String getValue(Object object) {
        try {
            if(Objects.nonNull(object)) {
                return JSON.writeValueAsString(object);
            }
        } catch (Exception e) {
            log.error("getValue Error ", e);
        }
        return Strings.EMPTY;
    }
}
