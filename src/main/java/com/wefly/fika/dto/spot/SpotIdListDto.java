package com.wefly.fika.dto.spot;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotIdListDto {
	List<Long> spotIdList;
}
