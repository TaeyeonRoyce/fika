package com.wefly.fika.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OAuthResponseParserTest {

	@Test
	public void parseEmailFromAttributeTest() {
	    //given
		OAuthResponseParser kakaoInfoParser = new OAuthResponseParser();

		String attribute = "{\n"
			+ "\t\t\t\"id\":2345896274,\n"
			+ "\t\t\t\"connected_at\":\"2022-07-18T03:08:05Z\",\n"
			+ "\t\t\t\"properties\":{\"nickname\":\"태연\"},\n"
			+ "\t\t\t\"kakao_account\":{\n"
			+ "\t\t\t\t\"profile_nickname_needs_agreement\":false,\n"
			+ "\t\t\t\t\"profile_image_needs_agreement\":true,\n"
			+ "\t\t\t\t\"profile\":{\"nickname\":\"태연\"},\n"
			+ "\t\t\t\t\"has_email\":true,\n"
			+ "\t\t\t\t\"email_needs_agreement\":false,\n"
			+ "\t\t\t\t\"is_email_valid\":true,\n"
			+ "\t\t\t\t\"is_email_verified\":true,\n"
			+ "\t\t\t\t\"email\":\"testEmail@gmail.com\"\n"
			+ "\t\t\t\t}\n"
			+ "\t\t\t}";
	    //when
		String emailTarget = "testEmail@gmail.com";
		String emailFromAttribute = kakaoInfoParser.getEmailFromAttribute(attribute);

		//then
		assertThat(emailFromAttribute).isEqualTo(emailTarget);

	}


}