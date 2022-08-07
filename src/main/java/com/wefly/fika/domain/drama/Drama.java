package com.wefly.fika.domain.drama;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.domain.scene.Scene;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Drama extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drama_id")
	private Long id;

	private String title;
	private String thumbnailUrl;

	@OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
	private List<Characters> characters = new ArrayList<>();

	@OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
	private List<Scene> scenes = new ArrayList<>();

	@Builder
	public Drama(String title, String thumbnailUrl) {
		this.title = title;
		this.thumbnailUrl = thumbnailUrl;
	}

}
