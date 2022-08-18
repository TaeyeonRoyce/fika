package com.wefly.fika.domain.course;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wefly.fika.domain.member.Member;

@Entity
@DiscriminatorValue("M")
public class MemberCourse extends Course {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member creatMember;

}
