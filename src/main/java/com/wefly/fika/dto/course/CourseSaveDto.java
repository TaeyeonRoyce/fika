package com.wefly.fika.dto.course;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseSaveDto {

	private String courseTitle;
	private String baseAddress;

	@NotNull
	private Long locageSpotId;

	private List<Long> spotIdList;
}
