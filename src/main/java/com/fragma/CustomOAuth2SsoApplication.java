package com.fragma;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fragma.oauth.OAuthProperties;



@SpringBootApplication
@EnableConfigurationProperties(OAuthProperties.class)
public class CustomOAuth2SsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomOAuth2SsoApplication.class, args);
	}

}
