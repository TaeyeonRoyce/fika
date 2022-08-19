package com.wefly.fika.domain.locage;

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

import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.drama.Drama;

import lombok.AccessLevel;
import lombok.Builder;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spot_data_id")
	private SpotData spotData;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drama_id")
	private Drama drama;

	private String quotes;

	@Builder
	public Locage(SpotData spotData, Drama drama, String quotes) {
		this.spotData = spotData;
		this.drama = drama;
		this.quotes = quotes;
	}
}
