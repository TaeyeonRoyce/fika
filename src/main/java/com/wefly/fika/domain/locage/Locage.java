package com.wefly.fika.domain.locage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Locage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locage_id")
	private Long id;
}
