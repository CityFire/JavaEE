package com.wjc.scw.project.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.wjc.scw.project.component.OssTemplate;

@SpringBootConfiguration
public class AppProjectConfig {

	@ConfigurationProperties(prefix = "oss")
	@Bean
	public OssTemplate ossTemplate() {
		return new OssTemplate();
	}

}
