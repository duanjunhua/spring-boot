package com.login.auth.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 *	验证码配置类
 * @author Administrator
 */
@Configuration
public class KaptchaConfig {

	@Bean(name="producer")
	public DefaultKaptcha producer() {
		Properties prop = new Properties();
		prop.put("kaptcha.border", "no");
		prop.put("kaptcha.textproducer.font.color", "black");
		prop.put("kaptcha.textproducer.char.space", "5");
        Config config = new Config(prop);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
	}
}
