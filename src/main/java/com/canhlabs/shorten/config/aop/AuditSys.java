package com.canhlabs.shorten.config.aop;

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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * are annotated with @RoAudit
     */
    @Around("isAuditForClass() || isAuditForMethod()")
    public Object processAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        int totalSize = 0;
        stopWatch.start();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        methodBefore(joinPoint, uuid);
        Object val = joinPoint.proceed();
        if (val instanceof Collection) {
            totalSize = ((Collection<?>) val).size();
        }
        stopWatch.stop();
        logAudit(joinPoint, stopWatch, totalSize, uuid);
        return val;
    }

    private void logAudit(ProceedingJoinPoint joinPoint, StopWatch stopWatch, int totalSize, String uuid) {
        Date dateLog = new Date();
        String ip = StringUtils.EMPTY;
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            ip = getClientIP(request);
        }

        String classCall = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String user = getCurrentUser();
        log.info("*****************************Audit Info****************************************");
        log.info("UUID: {}", uuid);
        //log.info("Domain name: {}", TenantContext.getDomainInfo().getDomainName());
        log.info("Class name: {}", classCall);
        log.info("Method call: {}", method);
        log.info("Total items: {}", totalSize);
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

    private String getClientIP(HttpServletRequest req) {
        String ip = req.getHeader("X-Real-IP");
        if (ip == null || ip.isEmpty()) {
            ip = req.getRemoteAddr();
        }
        if (ip == null) {
            ip = req.getHeader("x-forwarded-for");
        }
        if (ip == null) {
            ip = req.getHeader("X-Forwarded-For");
        }
        return ip;
    }

    public void methodBefore(JoinPoint joinPoint, String uuid) {
        // print request content
        try {
            // In the following two arrays, the number and position of parameter values ​​and parameter names are one-to-one correspondence.
            Object[] objs = joinPoint.getArgs();
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // parameter name
            Map<String, Object> paramMap = new HashMap<>();
            for (int i = 0; i < objs.length; i++) {
//                if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
//                    paramMap.put(argNames[i], MaskUtils.getValueAfterMask(objs[i]));
//                }
            }
            if (paramMap.size() > 0) {
                // for security, no need to log old value and sensitive data
                // String maskOldValue = oldFieldPattern.matcher(roGson.toJson(paramMap)).replaceAll("[");
                // String params = maskPattern.matcher(maskOldValue).replaceAll("*******");
                // log.info("[{}] - Parameter: {}", uuid, params);
            } else {
                log.info("[{}] - Parameter: {}", uuid, "EMPTY");
            }
        } catch (Exception e) {
            log.error("[{}]AOP methodBefore:", uuid, e);
        }
    }
}
