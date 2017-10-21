package me.weix.fairy.listener;

import me.weix.fairy.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */

@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger log = LoggerFactory.getLogger(ApplicationContextListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		// root application context
		if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
			log.debug(">>>>> spring初始化完毕 <<<<<");
			SpringContextUtil.setApplicationContext(contextRefreshedEvent.getApplicationContext());
			// spring初始化完毕后，通过反射调用所有使用@Service注解的initMapper方法
			Map<String, Object> services = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(org.springframework.stereotype.Service.class);
			for (Object service : services.values()) {
				log.debug(">>>>> {}.initMapper()", service.getClass().getName());
				try {
					Method initMapper = service.getClass().getMethod("initMapper");
					initMapper.invoke(service);
				} catch (Exception e) {
					log.error("初始化BaseService的initMapper方法异常", e);
					e.printStackTrace();
				}
			}

		}
	}

}
