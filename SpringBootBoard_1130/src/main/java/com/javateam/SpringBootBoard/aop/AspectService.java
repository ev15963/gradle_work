package com.javateam.SpringBootBoard.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Aspect
@Slf4j
public class AspectService {
		       
	@Pointcut("execution(* *..BoardService.*(..))")
    public void pointcutAspectService() {
    }
 
    @Before("pointcutAspectService()")
    public void adviceBefore() {
    	
        log.info("\n\n#######################################"
                + "########################################"
                + "########################################"
                + "########################################"
                + "########################################\n");
       
        log.info("게시판 모듈 시작");
    }
    
    @After("pointcutAspectService()")
    public void logAfter(JoinPoint jp) {
       
        log.info(jp.getSignature().getName()
                + " 메소드를 끝냈습니다.");
       
        log.info("\n\n#######################################"
                + "########################################"
                + "########################################"
                + "########################################"
                + "########################################\n");
    }
   
    @Around("pointcutAspectService()")
    public Object logAround(ProceedingJoinPoint pjp) {
       
        Object obj = null;
       
        try {
                log.info("AOP Around begin : "
                        + pjp.getSignature().getName()
                        + " 메소드를 시작합니다-1.");
               
                 obj = pjp.proceed();
                 // pjp.proceed(); // 타겟 객체의 원본 객체 실행
                 // 주의사항) Object 리턴을 하지 않고 void 리턴일 경우는
                 // 실행이 중단됨.
                 
                log.info("AOP Around end : "
                        + pjp.getSignature().getName()
                        + " 메소드를 끝냈습니다-1.");
                   
        } catch (Throwable e) {
            log.error("logAround Exception : " + e.getMessage());
        }      
       
        return obj;
    }
   
    @AfterThrowing("pointcutAspectService()")
    public void adviceAfterThrowing() {
        log.info("예외처리되었습니다.");
    }

}