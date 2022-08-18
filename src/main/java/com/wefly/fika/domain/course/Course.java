package com.wefly.fika.domain.course;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.wefly.fika.domain.base.BaseTimeEntity;

import lombok.Getter;

@Getter
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class Course extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long id;

	private String courseTitle;
	private String representDrama;

	private int scrappedCount;
	@OneToMany(mappedBy = "course")
	private List<CourseSpot> spotList = new ArrayList<>();

	private int courseSpotNumber;
	private int savedCount;
}
