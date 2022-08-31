package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberLoginDto;
import com.wefly.fika.dto.member.MemberNicknameDto;
import com.wefly.fika.dto.member.SocialSignUpDto;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.dto.member.MemberSignUpResponse;
import com.wefly.fika.service.IMemberService;
import com.wefly.fika.utils.StringFormatValidation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

	private final IMemberService memberService;

	@PostMapping("/valid/nickname")
	public ResponseEntity<ApiResponse> checkNickname(
		@RequestBody MemberNicknameDto requestDto) {
		log.info("[CHECK NICK NAME] : {}", requestDto.getNickname());
		if (requestDto.getNickname() == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		if (memberService.isExistNickname(requestDto.getNickname())) {
			log.warn("[DUPLICATE NICK NAME] : {}", requestDto.getNickname());
			return new ApiResponse<>(MEMBER_NICKNAME_DUPLICATE).toResponseEntity();
		}

		if (!StringFormatValidation.isNickNameRegex(requestDto.getNickname())) {
			log.warn("[INVALID NICK NAME] : {}", requestDto.getNickname());
			return new ApiResponse<>(NOT_NICKNAME_REGEX).toResponseEntity();
		}

		return new ApiResponse<>(true).toResponseEntity();
	}

	@Deprecated
	@GetMapping("/valid/email")
	public ResponseEntity<ApiResponse> checkEmail(@RequestParam String email) {
		if (email == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		boolean validEmail = !memberService.isExistEmail(email);

		return new ApiResponse<>(validEmail).toResponseEntity();
	}

	@Deprecated
	@PostMapping
	public ResponseEntity<ApiResponse> memberSingUp(
		@Valid @RequestBody MemberSignUpDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}
		String userEmail = saveDto.getEmail();
		String userPassword = saveDto.getPassword();
		if (!StringFormatValidation.isEmailRegex(userEmail)) {
			return new ApiResponse<>(NOT_EMAIL_REGEX).toResponseEntity();
		} else if (memberService.isExistEmail(userEmail)) {
			return new ApiResponse<>(MEMBER_EMAIL_DUPLICATE).toResponseEntity();
		} else if (!StringFormatValidation.isPasswordRegex(userPassword)) {
			return new ApiResponse<>(NOT_PASSWORD_REGEX).toResponseEntity();
		} else if (!saveDto.getPasswordCheck().equals(userPassword)) {
			return new ApiResponse<>(NOT_PASSWORD_EXACT).toResponseEntity();
		} else if (memberService.isExistNickname(saveDto.getNickname())) {
			return new ApiResponse<>(MEMBER_NICKNAME_DUPLICATE).toResponseEntity();
		}

		Member member = memberService.joinMember(saveDto);
		MemberSignUpResponse response = new MemberSignUpResponse(member.getMemberEmail(),
			member.getMemberAccessToken());

		return new ApiResponse<>(response).toResponseEntity();
	}

	@Deprecated
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> memberLogin(
		@Valid @RequestBody MemberLoginDto loginDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		try {
			memberService.loginByPassword(loginDto);
			Member member = memberService.getMemberByEmail(loginDto.getEmail());
			MemberSignUpResponse response = new MemberSignUpResponse(member.getMemberEmail(),
				member.getMemberAccessToken());
			return new ApiResponse<>(response).toResponseEntity();
		} catch (Exception e) {
			return new ApiResponse<>(LOGIN_REQUEST_ERROR).toResponseEntity();
		}

	}

	@PostMapping("/social")
	public ResponseEntity<ApiResponse> signUpSocial(
		@RequestBody SocialSignUpDto requestDto) {
		if (requestDto.getNickname() == null || requestDto.getEmail() == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		if (!StringFormatValidation.isNickNameRegex(requestDto.getNickname())) {
			log.warn("[INVALID NICK NAME] : {}", requestDto.getNickname());
			return new ApiResponse<>(NOT_NICKNAME_REGEX).toResponseEntity();
		} else if (memberService.isExistEmail(requestDto.getEmail())) {
			log.warn("[ALREADY SIGN UP EMAIL] : {}", requestDto.getEmail());
			return new ApiResponse<>(MEMBER_EMAIL_DUPLICATE).toResponseEntity();
		} else if (!StringFormatValidation.isEmailRegex(requestDto.getEmail())) {
			log.warn("[INVALID EMAIL] : {}", requestDto.getEmail());
			return new ApiResponse<>(NOT_EMAIL_REGEX).toResponseEntity();
		}

		String socialMemberAccessToken = memberService.joinSocialMember(requestDto);

		return new ApiResponse<>(socialMemberAccessToken).toResponseEntity();
	}

	@PostMapping("/delete")
	public ResponseEntity<ApiResponse> deleteMember(
		@RequestHeader("Access-Token") String accessToken
	) {
		try {
			log.info("[DELETE MEMBER]");
			memberService.deleteMember(accessToken);
			return new ApiResponse<>(SUCCESS_DELETE_USER).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}

	@PatchMapping("/nickname")
	public ResponseEntity<ApiResponse> updateMemberNickname(
		@RequestHeader("Access-Token") String accessToken,
		@RequestBody MemberNicknameDto requestDto
	) {
		try {
			log.info("[UPDATE MEMBER NICKNAME] : change to {}", requestDto.getNickname());
			memberService.updateMemberNickname(accessToken, requestDto);
			return new ApiResponse<>(SUCCESS_UPDATE_NICKNAME).toResponseEntity();
		} catch (CustomException e) {
			return new ApiResponse<>(e.getStatus()).toResponseEntity();
		}
	}
}
