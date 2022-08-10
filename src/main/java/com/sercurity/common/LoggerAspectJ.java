package com.sercurity.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//@Aspect
//@Configuration
public class LoggerAspectJ {
	private Logger logger = LoggerFactory.getLogger(LoggerAspectJ.class);

	@Around("execution(* com.sercurity.api.*.*.*(..))")
    public Object around(ProceedingJoinPoint joinpont) throws Throwable {
		 Long startTime = System.currentTimeMillis();
		logger.info("before called execution"+joinpont.toString()+startTime);
		Object output =  joinpont.proceed(joinpont.getArgs());
		Long EndTime = System.currentTimeMillis()-startTime;
		logger.info("end called execution"+joinpont.toString()+EndTime);
		return output;
	}
}
