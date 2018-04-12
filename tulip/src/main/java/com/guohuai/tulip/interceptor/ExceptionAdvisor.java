//package com.guohuai.tulip.interceptor;
//
//import java.lang.reflect.Method;
//
//import javax.annotation.Resource;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.guohuai.tulip.annotation.SystemControllerLog;
//import com.guohuai.tulip.platform.facade.RequestUtil;
//import com.guohuai.tulip.platform.gatewayrequestlog.GatewayRequestLogDao;
//import com.guohuai.tulip.platform.gatewayrequestlog.GatewayRequestLogEntity;
//
//@Component("ExceptionAdvisor")
//public class ExceptionAdvisor {
//	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvisor.class);
//	@Resource
//	private GatewayRequestLogDao gatewayRequestLogDao;
//	
//	public static String getControllerDescription(JoinPoint joinPoint)
//			throws Exception {
//		String targetName = joinPoint.getTarget().getClass().getName();
//		String methodName = joinPoint.getSignature().getName();
//		Object[] arguments = joinPoint.getArgs();
//		Class targetClass = Class.forName(targetName);
//		Method[] methods = targetClass.getMethods();
//		String description = "";
//		for (Method method : methods) {
//			if (method.getName().equals(methodName)) {
//				Class[] clazzs = method.getParameterTypes();
//				if (clazzs.length == arguments.length) {
//					description = method.getAnnotation(SystemControllerLog.class).description();
//					break;
//				}
//			}
//		}
//		return description;
//	}
//
//	@Pointcut("execution(* com.guohuai.tulip.*.service.*.*(..))")
//	public void serviceAspect() {
//
//	}
//
//	@Pointcut("@annotation(com.mtsbw.user.annotation.SystemControllerLog)")
//	public void controlAspect() {
//
//	}
//
////	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
////	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
////		// 获取用户请求方法的参数并序列化为JSON格式字符串
////		try {
////			// ==========数据库日志========= 
////			System.out.println("=====异常通知开始=====");
////			LogDto log = new LogDto();
////			log.setDescription(joinPoint.getSignature().getName()+"异常日志");
////			log.setMethod(getMethod(joinPoint));
////			log.setType(2);
////			log.setErrcode(e.getClass().getName());
////			log.setErrdetail(e.getMessage());
////			log.setIp(RequestUtil.getClientIP());
////			log.setParams(RequestUtil.getParameter());
////			log.setCreateUser(getUserId());
////			// 保存数据库
////			logService.addLog(log);
////			System.out.println("=====异常通知结束=====");
////		} catch (Exception ex) {
////			// 记录本地异常日志
////			logger.error("==异常通知异常==");
////			logger.error("异常信息:{}", ex.getMessage());
////		}
////		 //==========记录本地异常日志========== 
////		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget()
////				.getClass().getName()
////				+ joinPoint.getSignature().getName(), e.getClass().getName(),
////				e.getMessage());
////
////	}
//
//	@Before("controlAspect()")
//	public void doBefore(JoinPoint joinPoint) {
//		// 获取用户请求方法的参数并序列化为JSON格式字符串
//		try {
//			 //==========数据库日志========= 
//			System.out.println("=====日志通知开始=====");
//			GatewayRequestLogEntity log = new GatewayRequestLogEntity();
//			log.setDescription(getControllerDescription(joinPoint));
//			log.setMethod(getMethod(joinPoint));
//			log.setAppId(appId);
//			log.setRequest(request);
//			//log.setIp(RequestUtil.getClientIP());
//			log.setParams(RequestUtil.getParameter());
//			// 保存数据库
//			gatewayRequestLogDao.save(log);
//			System.out.println("=====日志通知结束=====");
//		} catch (Exception ex) {
//			// 记录本地异常日志
//			logger.error("==异常通知异常==");
//			logger.error("异常信息:{}", ex.getMessage());
//		}
//		// ==========记录本地异常日志========== 
//		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName()+ joinPoint.getSignature().getName());
//	}
//	@After("controlAspect()")
//	public void doAfter(JoinPoint joinPoint) {
//		// 获取用户请求方法的参数并序列化为JSON格式字符串
//		try {
//			 //==========数据库日志========= 
//			System.out.println("=====日志通知开始=====");
//			LogDto log = new LogDto();
//			log.setDescription(getControllerDescription(joinPoint));
//			log.setMethod(getMethod(joinPoint));
//			log.setType(1);
//			log.setIp(RequestUtil.getClientIP());
//			log.setParams(RequestUtil.getParameter());
//			log.setCreateUser(getUserId());
//			// 保存数据库
//			logService.addLog(log);
//			System.out.println("=====日志通知结束=====");
//		} catch (Exception ex) {
//			// 记录本地异常日志
//			logger.error("==异常通知异常==");
//			logger.error("异常信息:{}", ex.getMessage());
//		}
//		// ==========记录本地异常日志========== 
//		logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName()+ joinPoint.getSignature().getName());
//	}
//	
//	public Integer getUserId(){
//		return RequestUtil.getUserInfo()==null?null:RequestUtil.getUserInfo().getUserId();
//	}
//	public String getMethod(JoinPoint joinPoint){
//		return (joinPoint.getTarget().getClass().getName() + "."
//				+ joinPoint.getSignature().getName() + "()");
//	}
//}
