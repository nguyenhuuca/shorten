package com.canhlabs.shorten.config.aop;

import com.canhlabs.shorten.share.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletResponseWrapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.canhlabs.shorten.share.AppUtils.JSON;

/**
 * Using to log info before and after execute function with AuditSys annotation
 */
@Aspect
@Component
@Slf4j
public class AuditSys {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
    @Pointcut("execution(public * (@com.canhlabs.shorten.config.aop.Auditable *).*(..))")
    public void isAuditForClass() {
        // call by AOP
    }

    @Pointcut("execution(@com.canhlabs.shorten.config.aop.Auditable * *(..))")
    public void isAuditForMethod() {
        // call by AOP
    }

    /**
     * Method will add log statement of audit of the methods of class in call which
     * are annotated with @Auditable
     */
    @Around("isAuditForClass() || isAuditForMethod()")
    public Object processAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        methodBefore(joinPoint);
        Object val = joinPoint.proceed();
        stopWatch.stop();
        logAudit(joinPoint, stopWatch);
        return val;
    }

    private void logAudit(ProceedingJoinPoint joinPoint, StopWatch stopWatch) {
        Date dateLog = new Date();
        String ip = AppUtils.getClientIP();
        String classCall = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String user = getCurrentUser();
        log.info("*****************************Audit Info****************************************");
        log.info("Class name: {}", classCall);
        log.info("Method call: {}", method);
        log.info("Execution time: {}ms", stopWatch.getTotalTimeMillis());
        log.info("User performed: {}", user);
        log.info("IP call: {}", ip);
        log.info("Time call: {}", sdf.format(dateLog));
        log.info("*******************************************************************************\n");
    }

    private String getCurrentUser() {
        try {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!StringUtils.isBlank(currentUser)) {
                return currentUser;
            }

        } catch (Exception e) {
            return "UNKNOWN";

        }

        return "UNKNOWN";
    }


    public void methodBefore(JoinPoint joinPoint) {
        // print request content
        try {
            // In the following two arrays, the number and position of parameter values ​​and parameter names are one-to-one correspondence.
            Object[] objs = joinPoint.getArgs();
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // parameter name
            Map<String, Object> paramMap = new HashMap<>();
            for (int i = 0; i < objs.length; i++) {
                if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
                    paramMap.put(argNames[i], objs[i]);
                }
            }
            if (paramMap.size() > 0) {
                String params = JSON.writeValueAsString(paramMap);
                log.info("[] - Parameter: {}", params);
            } else {
                log.info("[] - Parameter: {}", "EMPTY");
            }
        } catch (Exception e) {
            log.error("[] AOP methodBefore:{}", e.getMessage());
        }
    }
}
