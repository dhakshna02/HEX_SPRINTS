package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class aspectt {

    @Before("execution(* org.example.repo.cartRepo.*(..))")
    public void beformethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Strarted "+ methodName);
    }

    @After("execution(* org.example.repo.cartRepo.*(..))")
    public void Aftermethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("ended "+ methodName);
    }
}
