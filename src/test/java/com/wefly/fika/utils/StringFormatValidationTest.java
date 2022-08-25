package com.wefly.fika.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringFormatValidationTest {

	@Test
	public void emailRegexTest() {
	    //given
		String email = "abc@ab.com";
		String nonEmail = "asdbadsdfw";

	    //when
		boolean isEmail1 = StringFormatValidation.isEmailRegex(email);
		boolean isEmail2 = StringFormatValidation.isEmailRegex(nonEmail);

		//then
		assertThat(isEmail1).isTrue();
		assertThat(isEmail2).isFalse();
	}


	@Test
	public void passwordRegexTest() {
		//given
		String password = "qwe1ab@234@";
		String nonPassword = "))!!:;;asdb";

		//when
		boolean isPassword1 = StringFormatValidation.isPasswordRegex(password);
		boolean isPassword2 = StringFormatValidation.isPasswordRegex(nonPassword);

		//then
		assertThat(isPassword1).isTrue();
		assertThat(isPassword2).isFalse();
	}

	@Test
	public void nicknameRegexTest() {
		//given
		String nickname = "Royce";
		String nonNickname1 = "t";
		String nonNickname2 = "toolongnickname~!";
		String nonNickname3 = "1!@%!@#";
		String nickname4 = "로이스";

		//when
		boolean isNickname1 = StringFormatValidation.isNickNameRegex(nickname);
		boolean isNickname2 = StringFormatValidation.isNickNameRegex(nonNickname1);
		boolean isNickname3 = StringFormatValidation.isNickNameRegex(nonNickname2);
		boolean isNickname4 = StringFormatValidation.isNickNameRegex(nonNickname3);
		boolean isNickname5 = StringFormatValidation.isNickNameRegex(nickname4);

		//then
		assertThat(isNickname1).isTrue();
		assertThat(isNickname2).isFalse();
		assertThat(isNickname3).isFalse();
		assertThat(isNickname4).isFalse();
		assertThat(isNickname5).isTrue();
	}



}