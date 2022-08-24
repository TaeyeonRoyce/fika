package com.wefly.fika.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseSpot;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.locage.Locage;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.jwt.JwtService;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.CourseSpotRepository;
import com.wefly.fika.repository.LocageRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.ICourseSpotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseSpotService implements ICourseSpotService {
	private final CourseSpotRepository courseSpotRepository;
	private final SpotDataRepository spotDataRepository;

	@Override
	public int addSpotsToCourse(Course course, CourseSaveDto saveDto) {
		saveDto.getSpotIdList().add(saveDto.getLocageSpotId());
		List<SpotData> spotData = spotDataRepository.findAllById(saveDto.getSpotIdList());
		List<CourseSpot> saveList = new ArrayList<>();
		int index = 1;
		for (SpotData spotDatum : spotData) {
			saveList.add(
				CourseSpot.builder()
					.course(course)
					.spotData(spotDatum)
					.orderIndex(index++)
					.build()
			);
		}

		courseSpotRepository.saveAll(saveList);
		course.update();

		return saveList.size();
	}

	public int addSpotsToCourse(Course course, List<Long> spotList) {
		List<SpotData> spotData = spotDataRepository.findAllById(spotList);
		List<CourseSpot> saveList = new ArrayList<>();

		int index = course.getSpotList().size() + 1;
		for (SpotData spotDatum : spotData) {
			saveList.add(
				CourseSpot.builder()
					.course(course)
					.spotData(spotDatum)
					.orderIndex(index++)
					.build()
			);
		}

		courseSpotRepository.saveAll(saveList);
		course.update();

		return saveList.size();
	}
}
