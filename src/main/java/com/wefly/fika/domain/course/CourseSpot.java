package com.wefly.fika.domain.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.data.SpotData;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CourseSpot extends BaseTimeEntity implements Comparable<CourseSpot> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_spot_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_Data_id")
	private SpotData spotData;

	private int orderIndex;

	@Builder
	public CourseSpot(Course course, SpotData spotData, int orderIndex) {
		this.course = course;
		this.spotData = spotData;
		this.orderIndex = orderIndex;

		course.getSpotList().add(this);
	}

	@Override
	public int compareTo(CourseSpot o) {
		return orderIndex - o.orderIndex;
	}
}
