package com.wefly.fika.domain.course;

import java.util.ArrayList;
import java.util.List;

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
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;

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

	private String baseAddress;
	private int courseSpotNumber;
	private int savedCount;
	@OneToMany(mappedBy = "course")
	private List<CourseSpot> spotList = new ArrayList<>();

	@Builder
	public Course(String courseTitle, Member creatMember, Drama drama, String baseAddress, int courseSpotNumber,
		int savedCount) {
		this.courseTitle = courseTitle;
		this.creatMember = creatMember;
		this.drama = drama;
		this.baseAddress = baseAddress;
		this.courseSpotNumber = courseSpotNumber;
		this.savedCount = savedCount;
	}

	public void update() {
		this.courseSpotNumber = spotList.size();
	}
}
