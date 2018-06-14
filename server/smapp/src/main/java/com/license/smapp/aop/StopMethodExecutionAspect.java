package com.license.smapp.aop;

import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.entity.model.ApplicationState;
import com.license.smapp.entity.model.State;
import com.license.smapp.entity.repository.ApplicationStateRepository;
import com.license.smapp.entity.repository.StudentRepository;
import com.license.smapp.util.Constants;
import org.aspectj.apache.bcel.classfile.Constant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class StopMethodExecutionAspect {


    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationStateRepository applicationStateRepository;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StopMethodExecutionAspect.class);
    @Around("@annotation(com.license.smapp.aop.StopMethodExecution)")
    public Object beforeMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable{

        ApplicationState state = applicationStateRepository.findOne(Constants.STATE_ID);
        if (state != null && state.getState().equals(State.CLOSED)) {
            throw new BadRequestException("Repartizarea proiectelor s-a incheiat! Nu poti efectua aceasta actiune!");
        }

        return joinPoint.proceed();
    }
}
