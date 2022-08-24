package com.wefly.fika.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.course.CourseSpot;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.dto.course.CourseSaveDto;
import com.wefly.fika.repository.CourseSpotRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.service.ICourseSpotService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CourseSpotService implements ICourseSpotService {
	private final CourseSpotRepository courseSpotRepository;
	private final SpotDataRepository spotDataRepository;

	@Override
	public int addSpotsToCourse(Course course, CourseSaveDto saveDto) {
		List<Long> spotIdList = saveDto.getSpotIdList();
		spotIdList.add(saveDto.getLocageSpotId());
		Map<Long, SpotData> spotDataMap = spotDataRepository.findAllById(spotIdList)
			.stream()
			.collect(Collectors.toMap(SpotData::getId, spotData -> spotData));
		List<CourseSpot> saveList = new ArrayList<>();
		int index = 1;
		saveList.add(
			CourseSpot.builder()
				.course(course)
				.spotData(spotDataMap.get(saveDto.getLocageSpotId()))
				.orderIndex(index++)
				.build()
		);

		for (Long spotDataId : spotIdList) {
			if (spotDataId.equals(saveDto.getLocageSpotId())) {
				continue;
			}
			saveList.add(
				CourseSpot.builder()
					.course(course)
					.spotData(spotDataMap.get(spotDataId))
					.orderIndex(index++)
					.build()
			);
		}

		courseSpotRepository.saveAll(saveList);
		course.update();

		return saveList.size();
	}

	public int addSpotsToCourse(Course course, List<Long> spotList) {
		Map<Long, SpotData> spotDataMap = spotDataRepository.findAllById(spotList)
			.stream()
			.collect(Collectors.toMap(SpotData::getId, spotData -> spotData));
		List<CourseSpot> saveList = new ArrayList<>();

		int index = course.getSpotList().size() + 1;
		for (Long spotDataId : spotList) {
			saveList.add(
				CourseSpot.builder()
					.course(course)
					.spotData(spotDataMap.get(spotDataId))
					.orderIndex(index++)
					.build()
			);
		}

		courseSpotRepository.saveAll(saveList);
		course.update();

		return saveList.size();
	}

	public int updateCourseSpots(Course course, List<Long> spotList) {
		Map<Long, SpotData> spotDataMap = spotDataRepository.findAllById(spotList)
			.stream()
			.collect(Collectors.toMap(SpotData::getId, spotData -> spotData));
		course.initSpotData();
		courseSpotRepository.deleteByCourseId(course.getId());

		List<CourseSpot> saveList = new ArrayList<>();
		int index = 1;
		for (Long spotDataId : spotList) {
			saveList.add(
				CourseSpot.builder()
					.course(course)
					.spotData(spotDataMap.get(spotDataId))
					.orderIndex(index++)
					.build()
			);
		}

		courseSpotRepository.saveAll(saveList);
		course.update();

		return saveList.size();
	}
}
