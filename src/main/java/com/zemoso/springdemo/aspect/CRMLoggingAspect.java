package com.zemoso.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component

public class CRMLoggingAspect {

    //setup logger
    private Logger logger = Logger.getLogger(getClass().getName());

    //setup pointcut declaration
    @Pointcut("execution(* com.zemoso.springdemo.controller.*.*(..))")
    private void forControllerPackage(){}

    //do the same for service and dao
    @Pointcut("execution(* com.zemoso.springdemo.service.*.*(..))")
    private void forServicePackage(){}

    @Pointcut("execution(* com.zemoso.springdemo.dao.*.*(..))")
    private void forDaoPackage(){}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){}

    //add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint){

        //display the method we are calling
        String method = joinPoint.getSignature().toShortString();
        logger.info("======> in @before: calling method: " + method);

        //display the arguments to the method

        //get the args
        Object[] args = joinPoint.getArgs();

        //loop trough and display the args
        for (Object object: args) {
            logger.info("=======> argument : " + object);

        }

    }

    //add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()",returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){

        //display the method we are returning from
        String method = joinPoint.getSignature().toShortString();
        logger.info("======> in @AfterReturning: calling method: " + method);

        //display the data returned
        logger.info("=======> result :" + result);
    }

}
