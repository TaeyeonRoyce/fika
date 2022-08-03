package com.wefly.fika.config.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.domain.member.model.Member;
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
			return new ResponseEntity<>(new ApiResponse<>(REQUEST_FIELD_NULL), HttpStatus.BAD_REQUEST);
		}

		boolean existNickname = memberService.isExistNickname(nickname);

		return new ResponseEntity<>(new ApiResponse<>(existNickname), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ApiResponse> memberSingUp(
		@Valid @RequestBody MemberSignUpDto saveDto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(new ApiResponse<>(REQUEST_FIELD_NULL), HttpStatus.BAD_REQUEST);
		}
		String userEmail = saveDto.getEmail();
		String userPassword = saveDto.getPassword();
		if (!StringFormatValidation.isEmailRegex(userEmail)) {
			return new ResponseEntity<>(new ApiResponse<>(NOT_EMAIL_REGEX), HttpStatus.BAD_REQUEST);
		} else if (memberService.isExistNickname(userEmail)) {
			return new ResponseEntity<>(new ApiResponse<>(MEMBER_EMAIL_DUPLICATE), HttpStatus.BAD_REQUEST);
		} else if (!StringFormatValidation.isPasswordRegex(userPassword)) {
			return new ResponseEntity<>(new ApiResponse<>(NOT_PASSWORD_REGEX), HttpStatus.BAD_REQUEST);
		} else if (!saveDto.getPasswordCheck().equals(userPassword)) {
			return new ResponseEntity<>(new ApiResponse<>(NOT_PASSWORD_EXACT), HttpStatus.BAD_REQUEST);
		} else if (memberService.isExistNickname(saveDto.getNickname())) {
			return new ResponseEntity<>(new ApiResponse<>(MEMBER_NICKNAME_DUPLICATE), HttpStatus.BAD_REQUEST);
		}

		Member member = memberService.joinMember(saveDto);
		MemberSignUpResponse response = new MemberSignUpResponse(member.getMemberEmail(),
			member.getMemberAccessToken());

		return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
	}

}
