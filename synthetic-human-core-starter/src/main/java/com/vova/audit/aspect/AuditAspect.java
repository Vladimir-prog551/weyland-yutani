package com.vova.audit.aspect;

import com.vova.audit.dto.AuditEvent;
import com.vova.audit.publisher.AuditPublisher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuditAspect {

    private final AuditPublisher auditPublisher;

    public AuditAspect(AuditPublisher auditPublisher) {
        this.auditPublisher = auditPublisher;
    }

    @Pointcut("@annotation(com.vova.audit.annotation.WeylandWatchingYou)")
    public void weylandWatchingYou() {}

    @Around("weylandWatchingYou()")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        try {
            Object result = joinPoint.proceed();
            auditPublisher.publish(new AuditEvent(methodName, args, result));
            return result;
        } catch (Exception e) {
            auditPublisher.publish(new AuditEvent(methodName, args, e.getClass().getSimpleName()));
            throw e;
        }
    }
}
