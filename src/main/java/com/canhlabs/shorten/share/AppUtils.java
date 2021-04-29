package com.canhlabs.shorten.share;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class AppUtils {
    private AppUtils(){}
    public static final ObjectMapper JSON = new ObjectMapper();
    public static String getClientIP() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest req = ((ServletRequestAttributes) attribs).getRequest();
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
        return "UNKNOWN";


    }
}
