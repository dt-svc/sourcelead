package com.bis.sourcelead.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ActionRolesService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
*
* @author Pradeep kumar Reddy
*/
@Aspect
@Component
public class LoggingAspect {
	
	@Autowired
	private AuditRecordDAO auditRecordDAO;
	
	@Autowired(required=true)
	private HttpServletRequest request;

   private Logger logger = LoggerFactory.getLogger(getClass());
   
   @Pointcut(" execution(java.lang.String com.bis.sourcelead.entity..*.*()) && "
           + "!execution(java.lang.String com.bis.sourcelead.entity..*.set*()) && "
           + "!execution(java.lang.String com.bis.sourcelead.entity..*.get*())")
   private void intercepterMethod() {
   }
   
   @Pointcut("execution(@javax.inject.Inject * com.bis.sourcelead.web..*.*(..))")
   public void methodAnnotatedWithInjectAndInDemoPackage() {
	   
   }

   @Before("intercepterMethod()")
   public void logAround(JoinPoint joinPoint) throws Throwable {
       logger.info("********** Action class Before Method Excution ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.info("Method name : " + joinPoint.getSignature().getName());
       logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));

   }

   @Before("execution(* com.bis.sourcelead.service..*.*(..))")
   public void logBeforeService(JoinPoint joinPoint) throws Throwable {
       logger.info("**********Service Layer Before Method Excution ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.info("Method name : " + joinPoint.getSignature().getName());
       logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));

   }

   @AfterReturning(pointcut = "execution(* com.bis.sourcelead.service..*.*(..))", returning = "result")
   public void logAfterReturningService(JoinPoint joinPoint, Object result) {
       logger.info("********** Service Layer After Method Exectution ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.info("Method name : " + joinPoint.getSignature().getName());
       logger.info("Method returned value  : " + result);
   }

   @AfterThrowing(pointcut = "execution(* com.bis.sourcelead.service.*.*(..))", throwing = "error")
   public void logAfterThrowingService(JoinPoint joinPoint, Throwable error) {
       logger.info("********** Service Layer Method Throwing Error ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.error("Method name : " + joinPoint.getSignature().getName());
       logger.error("Exception : " + error);

   }

   @AfterThrowing(pointcut = "execution(* com.bis.sourcelead.dao.*.*(..))", throwing = "error")
   public void logAfterThrowingDao(JoinPoint joinPoint, Throwable error) {
       logger.info("********** Dao Layer Method Throwing Error ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.error("Method name : " + joinPoint.getSignature().getName());
       logger.error("Exception : " + error);

   }

   @Around("execution(* com.bis.sourcelead.dao..*.*(..))")
   public Object logAroundDao(ProceedingJoinPoint joinPoint) throws Throwable {
       Object result = null;
       logger.info("********** Dao Layer Before Method Excution ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.info("Method name : " + joinPoint.getSignature().getName());
       logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));

       long start = System.currentTimeMillis();
       //result = joinPoint.proceed(); //continue on the intercepted method
       result = joinPoint.proceed(joinPoint.getArgs()); //continue on the intercepted method
       long elapsedTime = System.currentTimeMillis() - start;
       logger.info("********** Dao Layer After Method Excution ************");
       logger.info("Method execution time: " + elapsedTime + " milliseconds.");

       return result;
   }

   @Before("execution(* com.bis.sourcelead.web..*.*(..)) && methodAnnotatedWithInjectAndInDemoPackage() ")
   public void logBeforeWebService(JoinPoint joinPoint) throws Throwable {
       logger.info("**********Web Service Layer Before Method Excution ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.info("Method name : " + joinPoint.getSignature().getName());
       logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));

       System.out.print("Executed the @Injected method: "
               + joinPoint.getSignature() + " with value(s): ");

       for (Object object : joinPoint.getArgs()) {
           System.out.print(object);
       }
       System.out.println();
       
   }

   
   @AfterReturning(pointcut = "execution(* com.bis.sourcelead.web..*.*(..))", returning = "result")
   public void logAfterReturningWebService(JoinPoint joinPoint, Object result) throws Exception {
       logger.info("********** Web Service Layer After Method Exectution ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.info("Method name : " + joinPoint.getSignature().getName());
       logger.info("Method returned value  : " + result);
       
      request = ((ServletRequestAttributes) RequestContextHolder
    	        .getRequestAttributes()).getRequest();
       
       User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
       AuditRecord ar = new AuditRecord();
       ar.setMethodName(joinPoint.getSignature().getName());
       auditRecordDAO.save(ar,user);
       
   	}

   @AfterThrowing(pointcut = "execution(* com.bis.sourcelead.web.*.*(..))", throwing = "error")
   public void logAfterThrowingWebService(JoinPoint joinPoint, Throwable error) {
       logger.info("********** Web Service Layer Method Throwing Error ************");
       logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
       logger.error("Method name : " + joinPoint.getSignature().getName());
       logger.error("Exception : " + error);

   }
   
  
}
//end class