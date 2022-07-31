package com.wefly.fika.jwt;

import static com.wefly.fika.config.Constant.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.wefly.fika.utils.DateUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {

	private final DateUtil dateUtil;

	public String createMemberAccessToken(Long id, String email) {
		LocalDateTime now = LocalDateTime.now();

		return Jwts.builder()
			.setHeaderParam("type", "jwt")
			.claim("memberId", id)
			.claim("memberEmail", email)
			.setIssuedAt(dateUtil.toDate(now))
			.setExpiration(dateUtil.toDate(now.plusDays(7)))
			.signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
			.compact();
	}
}
