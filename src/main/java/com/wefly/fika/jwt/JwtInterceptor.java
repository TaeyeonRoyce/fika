package com.wefly.fika.jwt;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefly.fika.config.TokenNullable;
import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.ApiResponseStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtService jwtService;
	private final ObjectMapper objectMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		log.info("[REQUEST URL] : {}", request.getRequestURL());
		log.info("[Access Token Interceptor]");
		String accessToken = request.getHeader("Access-Token");
		log.info("[Access Token] : {}", accessToken);

		HandlerMethod handlerMethod = (HandlerMethod)handler;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if (handlerMethod.getMethodAnnotation(TokenNullable.class) != null
			&& (accessToken == null || accessToken.isBlank())) {
			return true;
		} else if (accessToken == null || accessToken.isBlank()) { //AccessToken이 존재하지 않은 경우
			log.warn("[JWT TOKEN EXCEPTION] : Token is not found");
			response.setStatus(401);
			getResponseMessage(response, ACCESS_TOKEN_NULL);
			return false;
		}

		if (jwtService.isExpired(accessToken)) {     // AccessToken이 만료된 경우
			log.warn("[JWT TOKEN EXPIRED] : {} is expired", accessToken);
			response.setStatus(401);
			getResponseMessage(response, ACCESS_TOKEN_EXPIRED);
			return false;

		} else if (jwtService.isNotValid(accessToken)) {   // AccessToken이 유효하지 않은 경우
			log.warn("[JWT TOKEN EXCEPTION] : {} is invalid", accessToken);
			response.setStatus(409);
			getResponseMessage(response, ACCESS_TOKEN_INVALID);
			return false;
		}

		return true;
	}

	private void getResponseMessage(HttpServletResponse response, ApiResponseStatus status) throws IOException {
		PrintWriter writer = response.getWriter();
		String jsonMessage = objectMapper.writeValueAsString(new ApiResponse<>(status));
		writer.print(jsonMessage);
		writer.flush();
	}
}
