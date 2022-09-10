package com.wefly.fika.dto.review;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEditDto {
	private int rate;
	private String reviewContents;
	private Boolean isImageReview;
	private List<String> imageUrls;

}
