package com.canhlabs.shorten.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Using to handle action after run async method(with Async annotation)
 */
@Aspect
@Component
@Slf4j
public class AsyncHandler {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void asyncFind() {
        // using for AOP
    }
    @After("asyncFind()")
    public void asyncExecuted(JoinPoint joinPoint) {
        String classCall = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        log.info("===========asyncExecuted=====");
        log.info("Class name: {}", classCall);
        log.info("Method call: {}", method);
        log.info("===========asyncExecuted=====");
    }


}
