package com.wefly.fika.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wefly.fika.jwt.JwtInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final JwtInterceptor jwtInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/token/test/**")
			.addPathPatterns("/course")
			.addPathPatterns("/course/scrap/**")
			.addPathPatterns("/spot/scrap/**")
			.addPathPatterns("/course/{*courseId}/spots")
			.addPathPatterns("/course/edit/{*courseId}")
			.addPathPatterns("/spot/my/scrap")
			.addPathPatterns("/course/my/scrap")
			.addPathPatterns("/nav/mypage")
			.addPathPatterns("/nav/main")
			.addPathPatterns("/member/delete")
			.addPathPatterns("/drama/{*dramaId}")
			.addPathPatterns("/course/all**")
			.addPathPatterns("/course/{*courseId}")
			.addPathPatterns("/course/detail/{*courseId}")
			.addPathPatterns("/spot/detail/{*spotId}");
	}

}
