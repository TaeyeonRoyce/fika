package com.wefly.fika.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class KakaoInfoParser {

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
}