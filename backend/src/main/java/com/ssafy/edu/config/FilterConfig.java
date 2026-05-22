package com.ssafy.edu.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ssafy.edu.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

	
	private final JwtAuthenticationFilter jwtauthenticationfilter;
	
	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration(){
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = 
				new FilterRegistrationBean<>();
		
		registrationBean.setFilter(jwtauthenticationfilter);
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
}
  