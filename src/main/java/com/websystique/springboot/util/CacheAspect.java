package com.websystique.springboot.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class CacheAspect {
    @Autowired
    RedisTemplate redisTemplate;

    @Around(value = "@annotation(com.websystique.springboot.util.RedisCache)")
    public Object queryCache(ProceedingJoinPoint joinPoint) throws Throwable {
        //redisTemplate.opsForValue().get()
        System.out.println("方法执行之前");
        String keyEL = "";
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        RedisCache cacheAnnotation = method.getAnnotation(RedisCache.class);
        keyEL = cacheAnnotation.key();

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(keyEL);
        EvaluationContext context = new StandardEvaluationContext();

        Object[] args = joinPoint.getArgs();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        String key = expression.getValue(context).toString();

        Object value = redisTemplate.opsForValue().get(keyEL);
        if (value != null) {
            System.out.println("从缓存中取到值");
            return value;
        }

        Object result = joinPoint.proceed();
        System.out.println("方法执行之后");

        redisTemplate.opsForValue().set(keyEL, result);
        return result;
    }

    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());
    }
}
