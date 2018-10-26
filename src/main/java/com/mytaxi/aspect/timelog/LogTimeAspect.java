package com.mytaxi.aspect.timelog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogTimeAspect {

//	@Around("@annotation(com.mytaxi.util.LogExecutionTime)")
	@Around("@annotation(org.springframework.stereotype.Service)")
//	@Around("@annotation(org.springframework.transaction.annotation.Transactional)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		
		Object proceed = joinPoint.proceed();
		
		long executionTime = System.currentTimeMillis() - start;
		System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		return proceed;
	}
}
