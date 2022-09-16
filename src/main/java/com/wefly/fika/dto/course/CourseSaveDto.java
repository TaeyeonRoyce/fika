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

	private Long baseCourseId;
	private Long courseGroupId;

	@NotNull
	private Long locageSpotId;

	private List<Long> spotIdList;

	@Builder
	public CourseSaveDto(Long baseCourseId, Long courseGroupId, Long locageSpotId,
		List<Long> spotIdList) {
		this.baseCourseId = baseCourseId;
		this.courseGroupId = courseGroupId;
		this.locageSpotId = locageSpotId;
		this.spotIdList = spotIdList;
	}
}
