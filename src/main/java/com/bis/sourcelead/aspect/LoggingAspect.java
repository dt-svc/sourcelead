package com.bis.sourcelead.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 *
 * @author Pradeep kumar Reddy
 */
@Aspect
@Component
public class LoggingAspect {

	@Autowired
	private ActionHistoryService actionHistoryService;

	@Autowired(required=true)
	private HttpServletRequest request;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());


	/*@Pointcut("execution(@javax.inject.Inject * com.bis.sourcelead.web..*.*(..))")
	public void methodAnnotatedWithInjectAndInDemoPackage() {

	}

	@Pointcut("execution(@javax.inject.Inject * com.bis.sourcelead.rest..*.*(..))")
	public void methodAnnotatedWithInjectAndInDemoPackage1() {

	}*/

	/*@Before("execution(* com.bis.sourcelead.web..*.*(..))")
	public void logBeforeWebService(JoinPoint joinPoint) throws Throwable {
		logger.info("**********Web Service Layer Before Method Execution ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.info("Method name : " + joinPoint.getSignature().getName());
		
		ArrayList<String> excludeMethodName = new ArrayList<>();
		excludeMethodName.add("loginPage");
		excludeMethodName.add("loginSession");
		
		String currentMethodName = joinPoint.getSignature().getName();
		//logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));loginSession

		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 		 if(auth != null && auth.getPrincipal() != null && !excludeMethodName.contains(currentMethodName)){
 			String settingsConfigured =  (String)SourceLeadSessionManager.getSessionAttribute(request, "settingsConfigured");
 			if(StringUtils.isNotEmpty(settingsConfigured) && settingsConfigured.equalsIgnoreCase("N")){
 				//dashboardController.projects();
 			}
		 }
		
	}*/

/*
	@AfterReturning(pointcut = "@annotation(org.springframework.security.access.prepost.PreAuthorize) && execution(* com.bis.sourcelead.web..*.*(..))", returning = "result")
	public void logAfterReturningWebService(JoinPoint joinPoint, Object result) throws Exception {
		logger.info("********** Web Service Layer After Method Execution ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.info("Method name : " + joinPoint.getSignature().getName());
		logger.info("Method returned value  : " + result);

		request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();*/

//		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//		String methodName = joinPoint.getSignature().getName();
//		if(user != null)
//			actionHistoryService.saveActionHistory(methodName,user);

	/*}*/

	/*@AfterThrowing(pointcut = "execution(* com.bis.sourcelead.web.*.*(..))", throwing = "error")
	public void logAfterThrowingWebService(JoinPoint joinPoint, Throwable error) {
		logger.info("********** Web Service Layer Method Throwing Error ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.error("Method name : " + joinPoint.getSignature().getName());
		logger.error("Exception : " + error);

	}*/

	/*@Before("execution(* com.bis.sourcelead.rest..*.*(..)) && methodAnnotatedWithInjectAndInDemoPackage1() ")
	public void logBeforeRestService(JoinPoint joinPoint) throws Throwable {
		logger.info("**********Rest Service Layer Before Method Execution ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.info("Method name : " + joinPoint.getSignature().getName());
		logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));

		System.out.print("Executed the @Injected method: "
				+ joinPoint.getSignature() + " with value(s): ");

		for (Object object : joinPoint.getArgs()) {
			System.out.print(object);
		}
	}*/


	@SuppressWarnings("unchecked")
	@AfterReturning(pointcut = "execution(* com.bis.sourcelead.web..*.*(..)) || execution(* com.bis.sourcelead.rest..*.*(..))", returning = "result")
	public void logAfterReturningRestService(JoinPoint joinPoint, Object result) throws Exception {
		//logger.info("********** Rest Service Layer After Method Exectution ************");
		//logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		//logger.info("Method name : " + joinPoint.getSignature().getName());
		//logger.info("Method returned value  : " + result);

		request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"userId");
		String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request,"actionUUID");
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations loginOrgLocation = null;
		if(orgLocId != null) {
			loginOrgLocation = orgLocationService.getOrgLocationById(orgLocId);
		}
		UserLoginLogoutSession userLoginLogoutSession = null;
		String methodName = joinPoint.getSignature().getName();
		String type = "";
		if("searchClientContactDetails".equalsIgnoreCase(methodName) || "saveVenodrContact".equalsIgnoreCase(methodName) || "searchClientDetails".equalsIgnoreCase(methodName)){
			Object[] args = joinPoint.getArgs();
			Object arg=args[0];
			Class c=arg.getClass();
			type=(String)c.getMethod("getResponderType").invoke(arg);
		}else if("getSpocAndOwnerDetailsById".equalsIgnoreCase(methodName) || "terminateSpoc".equalsIgnoreCase(methodName))
		{
			Object[] args = joinPoint.getArgs();
			Object arg=args[4];
			type=arg.toString();	
		}
		if("storeCandidate".equalsIgnoreCase(methodName) || "updateCandidate".equalsIgnoreCase(methodName)){
			Object[] args = joinPoint.getArgs();
			Object arg=args[0];
			Class c=arg.getClass();
			String str=(String)c.getMethod("getIsAssociate").invoke(arg);
			if("Y".equalsIgnoreCase(str))
				type = Constants.ASSOCIATE;
			else
				type = Constants.Candidate;
		}
		if("deleteCandidateByCandidateId".equalsIgnoreCase(methodName)){
			Object[] args = joinPoint.getArgs();
			Object arg=args[1];
			String str=arg.toString();
			if("Associates".equalsIgnoreCase(str))
				type = Constants.ASSOCIATE;
			else
				type = Constants.Candidate;
		}
		if("saveMarketPlaceData".equalsIgnoreCase(methodName)){
			type="saveMarketPlaceData";
		}
		if("withdrawCandidateFromMarketplace".equalsIgnoreCase(methodName)){
			type="withdrawCandidateFromMarketplace";
		}
		if("withdrawCandidatesFromMarketPlace".equalsIgnoreCase(methodName)){
			type="withdrawCandidatesFromMarketPlace";
		}
		if("initMarketplacePayment".equalsIgnoreCase(methodName)){
			type="initMarketplacePayment";
		}

		if(StringUtils.isNotBlank(actionUUID) && loginOrgLocation != null) {
			userLoginLogoutSession = new UserLoginLogoutSession();
		    userLoginLogoutSession.setId(actionId);
		    User user = new User();
		    user.setId(userId);
		    userLoginLogoutSession.setUserId(user);
			actionHistoryService.saveActionHistory(methodName,userLoginLogoutSession,loginOrgLocation,type,userId);
		}
		    
	}

	/*@AfterThrowing(pointcut = "execution(* com.bis.sourcelead.rest.*.*(..))", throwing = "error")
	public void logAfterThrowingRestService(JoinPoint joinPoint, Throwable error) {
		logger.info("********** Rest Service Layer Method Throwing Error ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.error("Method name : " + joinPoint.getSignature().getName());
		logger.error("Exception : " + error);

	}*/

	/*@AfterThrowing(pointcut = "execution(* com.bis.sourcelead.dao.*.*(..))", throwing = "error")
	public void logAfterThrowingDao(JoinPoint joinPoint, Throwable error) {
		logger.info("********** Dao Layer Method Throwing Error ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.error("Method name : " + joinPoint.getSignature().getName());
		logger.error("Exception : " + error);

	}
*/
	/*@Around("execution(* com.bis.sourcelead.dao..*.*(..))")
	public Object logAroundDao(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;
		logger.info("********** Dao Layer Before Method Execution ************");
		logger.info("Class name : " + joinPoint.getTarget().getClass().getSimpleName());
		logger.info("Method name : " + joinPoint.getSignature().getName());
		logger.info("Parameters name : " + Arrays.toString(joinPoint.getArgs()));

		long start = System.currentTimeMillis();
		result = joinPoint.proceed(joinPoint.getArgs()); 
		long elapsedTime = System.currentTimeMillis() - start;
		logger.info("********** Dao Layer After Method Execution ************");
		logger.info("Method execution time: " + elapsedTime + " milliseconds.");

		return result;
	}*/

}