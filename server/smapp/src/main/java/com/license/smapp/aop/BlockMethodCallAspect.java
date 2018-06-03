package com.license.smapp.aop;

import com.license.smapp.exception.BadRequestException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class BlockMethodCallAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockMethodCallAspect.class);

    @Around("@annotation(BlockMethodCall)")
    public Object blockMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
       // long start = System.currentTimeMillis();
       // long executionTime = System.currentTimeMillis() - start;
       // LOGGER.info(String.format("%s executed in %s ms", joinPoint.getSignature(), executionTime));
        throw new BadRequestException("Aplicatia este inchisa!");
       // return joinPoint.proceed();
    }

}
