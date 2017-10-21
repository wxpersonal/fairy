package me.weix.fairy.config.dataSource;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	private static Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean(name = "masterDataSource", destroyMethod = "close", initMethod = "init")
	@Primary
	public DataSource masterDataSource() {

		log.debug("----------------masterDataSource init------------------");
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(propertyResolver.getProperty("master.url"));
		datasource.setDriverClassName(propertyResolver.getProperty("master.driverClassName"));
		datasource.setUsername(propertyResolver.getProperty("master.username"));
		datasource.setPassword(propertyResolver.getProperty("master.password"));
		return datasource;
	}

	@Bean(name = "slaveDataSource1", destroyMethod = "close", initMethod = "init")
	public DataSource slaveDataSource1() {

		log.debug("----------------slaveDataSource1 init------------------");
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(propertyResolver.getProperty("slave1.url"));
		datasource.setDriverClassName(propertyResolver.getProperty("slave1.driverClassName"));
		datasource.setUsername(propertyResolver.getProperty("slave1.username"));
		datasource.setPassword(propertyResolver.getProperty("slave1.password"));
		return datasource;
	}

	@Bean(name = "slaveDataSource2", destroyMethod = "close", initMethod = "init")
	public DataSource slaveDataSource2() {

		log.debug("----------------slaveDataSource2 init------------------");
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(propertyResolver.getProperty("slave2.url"));
		datasource.setDriverClassName(propertyResolver.getProperty("slave2.driverClassName"));
		datasource.setUsername(propertyResolver.getProperty("slave2.username"));
		datasource.setPassword(propertyResolver.getProperty("slave2.password"));
		return datasource;
	}

}