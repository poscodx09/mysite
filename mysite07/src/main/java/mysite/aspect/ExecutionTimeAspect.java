package mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class ExecutionTimeAspect {
	@Around("execution(* *.repository.*.*(..)) || execution(* *.service.*.*(..)) || execution(* *.controller.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = pjp.proceed();

        stopWatch.stop();
        long executionTime = stopWatch.getTotalTimeMillis();
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String taskName = className + "." + methodName;

        log.info("[Execution Time][" + taskName + "] " + executionTime + " ms");

        return result;
    }
}
