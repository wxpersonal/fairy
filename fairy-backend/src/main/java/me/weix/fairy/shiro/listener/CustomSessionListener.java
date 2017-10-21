package me.weix.fairy.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public class CustomSessionListener implements SessionListener {

	private static Logger log = LoggerFactory.getLogger(CustomSessionListener.class);

	@Override
	public void onStart(Session session) {
		log.debug("会话创建：" + session.getId());
	}

	@Override
	public void onStop(Session session) {
		log.debug("会话停止：" + session.getId());
	}

	@Override
	public void onExpiration(Session session) {
		log.debug("会话过期：" + session.getId());
	}

}
