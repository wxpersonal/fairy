package me.weix.fairy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(JerseyConfig.class);

	private static RelaxedPropertyResolver relaxedPropertyResolver;

	private static final String ENV_REDIS = "redis.";

	@Bean
	public JedisPoolConfig getRedisConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(Integer.parseInt(relaxedPropertyResolver.getProperty("pool.max-idle")));
		config.setMinIdle(Integer.parseInt(relaxedPropertyResolver.getProperty("pool.min-idle")));
		config.setMaxWaitMillis(Long.parseLong(relaxedPropertyResolver.getProperty("pool.max-wait")));
		config.setMaxTotal(Integer.parseInt(relaxedPropertyResolver.getProperty("pool.max-active")));
		return config;
	}

	@Bean
	public JedisConnectionFactory getConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(relaxedPropertyResolver.getProperty("host"));
		factory.setPassword(relaxedPropertyResolver.getProperty("password"));
		factory.setPort(Integer.parseInt(relaxedPropertyResolver.getProperty("port")));
		factory.setUsePool(true);
		JedisPoolConfig config = getRedisConfig();
		factory.setPoolConfig(config);
		return factory;
	}

	@Bean
	public RedisTemplate<?, ?> getRedisTemplate() {
		RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
		return template;
	}

	@Override
	public void setEnvironment(Environment environment) {
		relaxedPropertyResolver = new RelaxedPropertyResolver(environment, ENV_REDIS);
	}
}
