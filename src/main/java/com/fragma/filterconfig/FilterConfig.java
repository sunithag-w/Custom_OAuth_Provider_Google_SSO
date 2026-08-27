package com.fragma.filterconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fragma.filter.CustomOAuth2AuthorizationFilter;
import com.fragma.filter.CustomOAuth2CallbackFilter;

@Configuration
public class FilterConfig {
	 @Bean
	    public CustomOAuth2AuthorizationFilter customOAuth2AuthorizationFilter() {
	        return new CustomOAuth2AuthorizationFilter();
	    }

	 @Bean
	  public CustomOAuth2CallbackFilter customOAuth2CallbackFilter() {
	        return new CustomOAuth2CallbackFilter();
	    }
    @Bean
    public FilterRegistrationBean<CustomOAuth2AuthorizationFilter> authorizationFilter( CustomOAuth2AuthorizationFilter filter) {
    
       FilterRegistrationBean<CustomOAuth2AuthorizationFilter>  registration =new FilterRegistrationBean<>();
       registration.setFilter(filter);      
       registration.addUrlPatterns("/customOAuth2/authorization/*");
     
        return registration;
    }


    @Bean
    public FilterRegistrationBean<CustomOAuth2CallbackFilter> callbackFilter(CustomOAuth2CallbackFilter filter) {
    

        FilterRegistrationBean<CustomOAuth2CallbackFilter> registration = new FilterRegistrationBean<>();
                
               

        registration.setFilter(filter);

        registration.addUrlPatterns("/login/oauth2/code/*");
       
        return registration;
    }
}
