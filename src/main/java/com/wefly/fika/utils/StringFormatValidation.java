package com.wefly.fika.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatValidation {
	public static boolean isEmailRegex(String email) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isPasswordRegex(String password) {
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%\"+|=])[A-Za-z\\d~!@#$%\"+|=]{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
