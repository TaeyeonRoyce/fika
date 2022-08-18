package com.wefly.fika.domain.course;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.drama.Drama;

@Entity
@DiscriminatorValue("D")
public class DramaCourse extends Course {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;


}
