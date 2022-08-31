package com.wefly.fika.dto.course;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseSaveDto {

	private String courseTitle;
	private String baseAddress;
	private Long courseGroupId;

	@NotNull
	private Long locageSpotId;

	private List<Long> spotIdList;

	@Builder
	public CourseSaveDto(String courseTitle, String baseAddress, Long courseGroupId, Long locageSpotId,
		List<Long> spotIdList) {
		this.courseTitle = courseTitle;
		this.baseAddress = baseAddress;
		this.courseGroupId = courseGroupId;
		this.locageSpotId = locageSpotId;
		this.spotIdList = spotIdList;
	}
}
