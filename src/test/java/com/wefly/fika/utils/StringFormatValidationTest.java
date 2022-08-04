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


}