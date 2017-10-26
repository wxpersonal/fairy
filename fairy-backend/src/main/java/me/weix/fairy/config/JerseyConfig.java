package me.weix.fairy.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@ApplicationPath("api")
@Configuration
public class JerseyConfig extends ResourceConfig implements EnvironmentAware {

	private static final String ENV_SWAGGER = "swagger.";

	private final Logger log = LoggerFactory.getLogger(JerseyConfig.class);

	private static RelaxedPropertyResolver relaxedPropertyResolver;

	public JerseyConfig() {
		log.info("Jersey configuration");
		register(WadlResource.class);
		register(ObjectMapperProvider.class);
		// register(JacksonJsonProvider.class);
		// register(FastJsonProvider.class);
		// packages("me.weix.fairy.rest","com.logic.landseaserver.ws");
		packages("me.weix.fairy.rest");
	}

	@PostConstruct
	public void init() {
		this.configureSwagger();
	}

	private void configureSwagger() {
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
		BeanConfig config = new BeanConfig();
		config.setTitle("仙女娜个人作品集api");
		config.setContact("WeiXiang");
		config.setSchemes(new String[] { "http", "https" });
		config.setBasePath("/api");
		config.setResourcePackage("me.weix.fairy.rest");
		config.setPrettyPrint(true);
		config.setScan(true);
	}

	@Override
	public void setEnvironment(Environment environment) {
		relaxedPropertyResolver = new RelaxedPropertyResolver(environment, ENV_SWAGGER);
	}
}
