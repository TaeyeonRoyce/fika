package com.wefly.fika.jwt;

import static com.wefly.fika.config.Constant.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.wefly.fika.domain.member.model.Member;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.utils.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {

	private final DateUtil dateUtil;
	private final MemberRepository memberRepository;

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

	public boolean isExpired(String accessToken) {
		try {
			Claims claims = Jwts.parser()
				.setSigningKey(JWT_SECRET_KEY)
				.parseClaimsJws(accessToken)
				.getBody();
			claims.getExpiration();
			return false;
		} catch (ExpiredJwtException e) {
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	public boolean isNotValid(String accessToken) {
		try {
			Jwts.parser()
				.setSigningKey(JWT_SECRET_KEY) //gitignore에 등록된 KEY
				.parseClaimsJws(accessToken)
				.getBody()
				.get("memberId", Long.class);

			Jwts.parser()
				.setSigningKey(JWT_SECRET_KEY) //gitignore에 등록된 KEY
				.parseClaimsJws(accessToken)
				.getBody()
				.get("memberEmail", String.class);
			return false;
		} catch (JwtException | NullPointerException exception) {
			return true;
		}
	}

	public Long getMemberId(String accessToken) {
		return Jwts.parser()
			.setSigningKey(JWT_SECRET_KEY) //gitignore에 등록된 KEY
			.parseClaimsJws(accessToken)
			.getBody()
			.get("memberId", Long.class);
	}

	public Member getMember(String accessToken) {
		Long memberId = getMemberId(accessToken);
		return memberRepository.findById(memberId).get();
	}
}
