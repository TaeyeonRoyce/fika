package com.wefly.fika.config.controller;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wefly.fika.config.response.ApiResponse;
import com.wefly.fika.dto.member.MemberSignUpDto;
import com.wefly.fika.service.IMemberService;

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
			return new ResponseEntity<>(new ApiResponse(REQUEST_FIELD_NULL), HttpStatus.BAD_REQUEST);
		}

		boolean existNickname = memberService.isExistNickname(nickname);

		return new ResponseEntity<>(new ApiResponse(existNickname), HttpStatus.OK);
	}


	@PostMapping
	public void memberSingUp(MemberSignUpDto saveDto) {

	}

}
