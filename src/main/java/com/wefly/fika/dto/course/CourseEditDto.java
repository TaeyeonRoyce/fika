package com.wefly.fika.dto.course;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEditDto {
	private String courseTitle;
	private List<Long> spotIdList;
}
