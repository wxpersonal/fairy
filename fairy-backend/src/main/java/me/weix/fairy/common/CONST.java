package me.weix.fairy.common;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public interface CONST {

	/**
	 * 登录类型
	 */
	interface LOGINTYPE {
		Integer EMAIN = 1;
		Integer MOBILE = 2;
	}

	/**
	 * 系统通用状态 0：无效，否…… 1：有效，是……
	 */
	interface SYSTEMSTATUS {
		int NO = 0;
		int YES = 1;
	}

	/**
	 * 数据源类型
	 */
	interface DATASOURCETYPE {
		String WRITE = "write";
		String READ = "read";
	}

}
