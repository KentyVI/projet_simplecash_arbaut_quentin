package com.example.simplecash.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.example.simplecash.controller..*)")
    public void inControllers() {}

    @Pointcut("within(com.example.simplecash.service..*)")
    public void inServices() {}

    @Around("inControllers()")
    public Object logAroundControllers(ProceedingJoinPoint pjp) throws Throwable {
        String signature = pjp.getSignature().toShortString();
        String args = Arrays.stream(pjp.getArgs())
                .map(this::summarizeArg)
                .collect(Collectors.joining(", "));

        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attrs instanceof ServletRequestAttributes ? ((ServletRequestAttributes) attrs).getRequest() : null;
        String method = req != null ? req.getMethod() : MDC.get("method");
        String path = req != null ? req.getRequestURI() + (req.getQueryString()!=null?"?"+req.getQueryString():"") : MDC.get("path");
        String reqId = MDC.get("reqId");

        long start = System.nanoTime();
        log.info("-> {} {} {} args=[{}]", method, path, signature, args);
        try {
            Object result = pjp.proceed();
            long tookMs = (System.nanoTime() - start) / 1_000_000;
            int status = 200;
            if (result instanceof org.springframework.http.ResponseEntity<?> re) {
                status = re.getStatusCode().value();
            }
            log.info("<- {} {} {} status={} took={}ms", method, path, signature, status, tookMs);
            return result;
        } catch (Throwable ex) {
            long tookMs = (System.nanoTime() - start) / 1_000_000;
            log.error("xx {} {} {} failed after {}ms: {}", method, path, signature, tookMs, ex.toString());
            throw ex;
        }
    }

    @AfterThrowing(pointcut = "inServices()", throwing = "ex")
    public void logServiceExceptions(Exception ex) {
        String where = Thread.currentThread().getName();
        log.error("Service exception [{}]: {}", where, ex.toString());
    }

    private String summarizeArg(Object arg) {
        if (arg == null) return "null";
        if (arg instanceof String s) return '"' + (s.length() > 120 ? s.substring(0, 117) + "..." : s) + '"';
        Class<?> c = arg.getClass();
        // Avoid dumping full entities/DTOs; print simple summary
        try {
            var idField = Arrays.stream(c.getDeclaredFields())
                    .filter(f -> f.getName().equalsIgnoreCase("id") || f.getName().endsWith("Id"))
                    .findFirst().orElse(null);
            if (idField != null) {
                idField.setAccessible(true);
                Object id = idField.get(arg);
                return c.getSimpleName() + "{id=" + id + "}";
            }
        } catch (Exception ignored) {}
        return c.getSimpleName();
    }
}

