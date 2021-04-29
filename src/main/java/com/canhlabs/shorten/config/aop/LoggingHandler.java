package com.canhlabs.shorten.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoggingHandler {
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

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
        // using for AOP
    }
    //Around -> Any method within resource annotated with @Controller annotation
    @Around("controller() && (postMapping() || deleteMapping() ) && args(.., @RequestBody body)")
    public Object logAround(ProceedingJoinPoint joinPoint, Object body) throws Throwable {
        logInfo(joinPoint, body);
        return joinPoint.proceed();
    }

    private void logInfo(ProceedingJoinPoint joinPoint, Object body) {
        String env = "";
        if (StringUtils.isEmpty(env) || "prod".equalsIgnoreCase(env)) {
            return;
        }
        String classCall = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        log.info("********Request controller *************");
//        log.info("Domain name: {}", TenantContext.getDomainInfo().getDomainName());
        log.info("Class name: {}", classCall);
        log.info("Method call: {}", method);
        log.info("Body post {}", getValue(body));
    }

    private String getValue(Object object) {
        try {
            if(Objects.nonNull(object)) {
              //return roGson.toJson(MaskUtils.getValueAfterMask(object));
            }
        } catch (Exception e) {
            log.error("getValue Error ", e);
        }
        return Strings.EMPTY;
    }
}
