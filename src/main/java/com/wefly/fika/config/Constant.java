package com.wefly.fika.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

	public static String JWT_SECRET_KEY;

	public Constant(@Value("${jwt.secret}") String jwtSecret) {
		JWT_SECRET_KEY = jwtSecret;
	}
}
