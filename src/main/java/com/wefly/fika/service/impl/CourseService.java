package com.wefly.fika.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.spot.Spot;
import com.wefly.fika.dto.spot.SpotSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.SpotRepository;
import com.wefly.fika.service.ICourseService;
import com.wefly.fika.service.ISpotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CourseService implements ICourseService {

	private final CourseRepository courseRepository;

}
