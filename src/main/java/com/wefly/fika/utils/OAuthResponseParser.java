package com.wefly.fika.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class OAuthResponseParser {

	public String getEmailFromAttribute(String attributes) {
		Pattern p = Pattern.compile("([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})");
		Matcher m = p.matcher(attributes);

		while (m.find()) {
			if (m.group(1) != null) {
				break;
			}
		}

		return m.group(1);
	}

	public String getUserIdFromAttribute(String attributes) {
		String[] split = attributes.split(":");
		System.out.println(Arrays.toString(split));

		String[] userId = split[1].split("\"");
		System.out.println(Arrays.toString(userId));
		return userId[1].trim();
	}
}
