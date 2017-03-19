package com.newview.bysj.aop;

import com.newview.bysj.myAnnotation.MethodDescription;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Clock;

@Component
@Aspect
public class PerformanceMonitoring {

    /**
     * 用来在控制台打印消息
     */
    private final static Logger logger = Logger.getLogger(PerformanceMonitoring.class);

    /**
     * 环绕通知，用来获取方法的执行时间
     *
     * @param point 切入点
     * @return 环绕通知需要返回值
     * @throws Throwable
     */
    @Around("execution(* com.newview.bysj.service.*Service.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取方法名
        String methodName = point.getSignature().toString();
        // java 8 新增的时间日期类
        // 获取clock的实例
        Clock clock = Clock.systemUTC();
        // 方法开始执行的时间
        Long startTime = clock.millis();
        // 方法执行
        Object obj = point.proceed();
        // 方法结束执行的时间
        Long endTime = clock.millis();
        // 方法执行所用的时间
        Long executeTime = endTime - startTime;
        //获取需要被织入的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String methodDescription = "not use @MethodDescription to annotation method ！   ";
        //如果该方法使用了MethodDescription注解，则获取注解的内容
        if (method.getAnnotation(MethodDescription.class) != null) {
            methodDescription = "action： " + method.getAnnotation(MethodDescription.class).value() + "  ";
        }
        if (logger.isDebugEnabled()) {
            logger.error("Service performance--> use time：" + executeTime + "ms   method description:" + methodDescription + " method name:" + methodName);
        }
        return obj;
    }
}
