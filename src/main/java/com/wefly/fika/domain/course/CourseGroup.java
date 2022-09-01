package com.wefly.fika.domain.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.CourseGroupPreviewResponse;
import com.wefly.fika.dto.course.response.CourseGroupListResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CourseGroup extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_group_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String groupName;

	@OneToMany(mappedBy = "courseGroup")
	private List<Course> courseList = new ArrayList<>();

	@Builder
	public CourseGroup(Member member, String groupName) {
		this.member = member;
		this.groupName = groupName;

		member.getCourseGroups().add(this);
	}

	public CourseGroupPreviewResponse toPreviewResponse() {
		return CourseGroupPreviewResponse.builder()
			.courseGroupId(this.id)
			.courseGroupName(this.groupName)
			.build();
	}

	public CourseGroupListResponse toListResponse() {
		return CourseGroupListResponse.builder()
			.groupId(this.id)
			.groupName(this.groupName)
			.coursePreviewList(
				this.courseList.stream()
					.map(Course::toPreviewResponse)
					.collect(Collectors.toList())
			)
			.build();
	}

	public void updateName(String updateCourseGroupName) {
		this.groupName = updateCourseGroupName;
	}
}
