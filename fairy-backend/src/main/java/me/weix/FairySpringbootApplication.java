package me.weix;

//import me.weix.fairy.common.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ServletComponentScan
public class FairySpringbootApplication {

	public static void main(String[] args) {

		SpringApplication.run(FairySpringbootApplication.class, args);
	}
}
