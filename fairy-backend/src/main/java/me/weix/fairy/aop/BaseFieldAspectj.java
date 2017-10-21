package me.weix.fairy.aop;

import me.weix.fairy.pojo.BasePojo;
import me.weix.fairy.util.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
 * 实体类通用字段切面
 * @author weix
 */

@Order(1)
@Aspect
@Component
public class BaseFieldAspectj {

	// 定义通用切点
	@Pointcut("execution(* me.weix.fairy.*Mapper.insert*(..)) ||" + "execution(* me.weix.fairy.*Mapper.update*(..))")
	public void baseField() {
	}

	@Around("baseField()")
	public void setWriteDataSource(ProceedingJoinPoint jp) {
		try {
			Object[] args = jp.getArgs();
			String methodName = jp.getSignature().getName();
			for (Object o : args) {
				// 参数为list类型
				if (o instanceof java.util.List || o.getClass().isArray()) {
					List<Object> list = new ArrayList<>();
					if (o.getClass().isArray()) {
						list = Arrays.asList(o);
					}
					if (o instanceof java.util.List) {
						list = (List) o;
					}
					for (Object o1 : list) {
						if (o1 instanceof BasePojo) {
							fixBaseFields(methodName, o1);
						}
					}
				}
				// pojo
				if (o instanceof BasePojo) {
					fixBaseFields(methodName, o);
				}
			}
			jp.proceed();
			// logger.debug("自定义返回通知AfterReturning-->{}");
		} catch (Throwable throwable) {
			// logger.debug("异常处理-->{}");
			throwable.printStackTrace();
		}
		// logger.debug("自定义后置通知After-->{}");
	}

	private void fixBaseFields(String methodName, Object o1) throws IllegalAccessException, InvocationTargetException {
		// 基础getXxx()方法
		Method getCreateBy = ReflectUtil.getAccessibleMethod(o1, "getCreateBy", null);
		Method getCreateTime = ReflectUtil.getAccessibleMethod(o1, "getCreateTime", null);
		Method getUpdateBy = ReflectUtil.getAccessibleMethod(o1, "getUpdateBy", null);
		Method getStatus = ReflectUtil.getAccessibleMethod(o1, "getStatus", null);

		// 基础setXxx()方法
		Method setCreateBy = ReflectUtil.getAccessibleMethod(o1, "setCreateBy", Integer.class);
		Method setCreateTime = ReflectUtil.getAccessibleMethod(o1, "setCreateTime", Date.class);
		Method setUpdateBy = ReflectUtil.getAccessibleMethod(o1, "setUpdateBy", Integer.class);
		Method setStatus = ReflectUtil.getAccessibleMethod(o1, "setStatus", Integer.class);

		// 插入时修改，以后不再修改
		if (methodName.startsWith("insert")) {
			// todo 暂时没有usertoken功能，先设置为1
			if (getCreateBy.invoke(o1, null) == null) {
				setCreateBy.invoke(o1, 1);
			}

			if (getCreateTime.invoke(o1, null) == null) {
				setCreateTime.invoke(o1, new Date());
			}
			// 插入时默认有效
			if (getStatus.invoke(o1, null) == null) {
				setStatus.invoke(o1, 1);
			}
		}

		if (getUpdateBy.invoke(o1, null) == null) {
			setUpdateBy.invoke(o1, 1);
		}
	}

}
