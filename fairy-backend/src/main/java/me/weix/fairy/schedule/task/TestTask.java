package me.weix.fairy.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public class TestTask {

	/** 日志对象 */
	private Logger log = LoggerFactory.getLogger(TestTask.class);

	public void run() {

		if (log.isInfoEnabled()) {
			log.debug("--------------------test task-------------------");
		}
	}
}
