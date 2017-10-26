package me.weix.fairy.config.dataSource;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
public class DataSourceContextHolder {

	private static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void setDataSource(String dataSourceName) {
		holder.set(dataSourceName);
	}

	public static String getDataSource() {
		return holder.get();
	}

	public static void clearDataSource() {
		holder.remove();
	}

}
