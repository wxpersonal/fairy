package me.weix.fairy.aop;

import me.weix.fairy.config.dataSource.DataSourceContextHolder;
import me.weix.fairy.config.dataSource.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * 数据源切面
 * @author weix
 */

@Order(1)
@Aspect
@Component
public class DataSourceAspectj {

    private Logger log = LoggerFactory.getLogger(DataSourceAspectj.class);

    // 定义通用切点
    @Pointcut("execution(* me.weix.fairy.service.impl.*ServiceImpl.*(..))" )
//    @Pointcut("execution(* me.weix.fairy.*Mapper.insert*(..))||" + "execution(* me.weix.fairy.*Mapper.update*(..)) ||"  + "execution(* me.weix.fairy.*Mapper.select*(..))")

    public void dataSourcePointcut() {
    }


    /**
     * 数据源切换
     *
     * @param jp
     * @return
     */
    @Around("dataSourcePointcut()")
    public Object routeDataSource(ProceedingJoinPoint jp) {
        try {
            String dataSourceKey;
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            String methodName = jp.getSignature().getName();
            if (methodName.startsWith("del") || methodName.startsWith("update") ||
                    methodName.startsWith("mod") || methodName.startsWith("insert")) {
                dataSourceKey = DataSourceType.master.getName();
            } else {
                dataSourceKey = DataSourceType.slave1.getName();
            }
            DataSourceContextHolder.setDataSource(dataSourceKey);
            Object obj = jp.proceed();
            DataSourceContextHolder.clearDataSource();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
