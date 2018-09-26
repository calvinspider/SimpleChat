package org.yang.zhang.interceptor;

import java.util.Arrays;

import javax.swing.plaf.synth.SynthScrollBarUI;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.yang.zhang.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 09 26 14:21
 */
@Aspect
@Component
@Slf4j
public class ControllerInterceptor {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object interceptor(ProceedingJoinPoint pjp){
        Long startTime=System.currentTimeMillis();
        if(log.isDebugEnabled()){
            log.debug("===================Controller Request Begin===========");
            log.debug("Arags: "+JsonUtils.toJson(pjp.getArgs()));
            log.debug("Method: "+pjp.getSignature().getName());
            log.debug("Class: "+pjp.getSignature().getDeclaringTypeName());
        }
        Object ret=null;
        try {
            ret=pjp.proceed();
        }catch (Throwable e){
            log.error(e.getMessage());
        }

        Long timeCost=System.currentTimeMillis()-startTime;
        log.debug("Cost Time: "+timeCost);
        log.debug("===================Controller Request End===========");

        return ret;
    }



}
