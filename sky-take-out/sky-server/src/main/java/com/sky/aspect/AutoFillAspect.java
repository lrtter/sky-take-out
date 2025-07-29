package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..))&& @annotation(com.sky.annotation.AutoFill)")
    public void autoFillpointcut(){}

    @Before("autoFillpointcut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行数据填充");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = autoFill.value();

        Object[] args = joinPoint.getArgs();

        if(args==null||args.length==0){
            return;
        }
        Object object = args[0];

        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        if(value== OperationType.INSERT){
            try {
                Method setUpdateTime = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setCreateTime = object.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = object.getClass().getMethod(AutoFillConstant.SET_CREATE_USER , Long.class);

                setUpdateTime.invoke(object,now);
                setUpdateUser.invoke(object,id);
                setCreateTime.invoke(object,now);
                setCreateUser.invoke(object,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                Method setUpdateTime = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = object.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(object,now);
                setUpdateUser.invoke(object,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
