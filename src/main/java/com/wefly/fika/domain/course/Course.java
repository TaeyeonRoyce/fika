package com.wefly.fika.domain.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.dto.course.response.CoursePreviewResponse;
import com.wefly.fika.dto.spot.response.SpotPreviewResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Course extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long id;
	private String courseTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member creatMember;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locage_id")
	private SpotData locage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_group_id")
	private CourseGroup courseGroup;

	private String baseAddress;
	private int courseSpotNumber;
	private int savedCount;
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<CourseSpot> spotList = new ArrayList<>();

	@Builder
	public Course(String courseTitle, Member creatMember, Drama drama, String baseAddress, int courseSpotNumber,
		int savedCount, SpotData locage, CourseGroup courseGroup) {
		this.courseTitle = courseTitle;
		this.creatMember = creatMember;
		this.drama = drama;
		this.baseAddress = baseAddress;
		this.courseSpotNumber = courseSpotNumber;
		this.savedCount = savedCount;
		this.locage = locage;
		this.courseGroup = courseGroup;

		courseGroup.getCourseList().add(this);
		drama.getCourseList().add(this);
	}

	public void updateCourseSpotNumber() {
		this.courseSpotNumber = spotList.size();
	}

	public CoursePreviewResponse toPreviewResponse() {
		List<String> spotTitleList = spotList.stream()
			.map(s -> s.getSpotData().getSpotName())
			.limit(5)
			.collect(Collectors.toList());

		return CoursePreviewResponse.builder()
			.courseId(this.id)
			.courseSavedCount(this.savedCount)
			.baseAddress(this.baseAddress)
			.locageImageUrl(this.spotList.get(0).getSpotData().getImage())
			.courseTitle(this.courseTitle)
			.dramaTitle(this.drama.getDramaName())
			.spotTitleList(spotTitleList)
			.build();
	}

	public List<SpotPreviewResponse> getSortedSpotList() {
		Collections.sort(spotList);
		return spotList.stream()
			.map(CourseSpot::getSpotData)
			.map(SpotData::toSpotPreviewResponse)
			.collect(Collectors.toList());
	}

	public void addSavedCount() {
		this.savedCount += 1;
	}

	public void cancelSavedCount() {
		this.savedCount -= 1;
	}

	public void initSpotData() {
		this.spotList = new ArrayList<>();
	}

	public void updateCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
}
