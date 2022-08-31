package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.domain.member.MemberSaveCourse;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.CourseRepository;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.service.ICourseService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourseServiceTest {



}