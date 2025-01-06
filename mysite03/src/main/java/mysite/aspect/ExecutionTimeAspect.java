package mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ExecutionTimeAspect {
	@Around("execution(* mysite.repository..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = pjp.proceed();

        stopWatch.stop();
        long executionTime = stopWatch.getTotalTimeMillis();

        System.out.println("[Execution Time][" + pjp.getSignature() + "] " + executionTime + " ms");

        return result;
    }
}
