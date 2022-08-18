package com.wefly.fika.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.member.MemberLoginDto;
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

	@GetMapping("/valid/nickname")
	public ResponseEntity<ApiResponse> checkNickname(@RequestParam String nickname) {
		if (nickname == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		boolean validNickname = !memberService.isExistNickname(nickname);

		return new ApiResponse<>(validNickname).toResponseEntity();
	}

	@GetMapping("/valid/email")
	public ResponseEntity<ApiResponse> checkEmail(@RequestParam String email) {
		if (email == null) {
			return new ApiResponse<>(REQUEST_FIELD_NULL).toResponseEntity();
		}

		boolean validEmail = !memberService.isExistEmail(email);

		return new ApiResponse<>(validEmail).toResponseEntity();
	}

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

}
