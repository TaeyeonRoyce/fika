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
import javax.persistence.Table;

import com.wefly.fika.domain.base.BaseTimeEntity;
import com.wefly.fika.domain.character.Characters;
import com.wefly.fika.domain.course.Course;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.scene.Scene;
import com.wefly.fika.dto.drama.DramaPreviewResponse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "drama_fika")
@Entity
public class Drama extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drama_id")
	private Long id;

	@Column(name = "drama_name_kr")
	private String dramaName;
	@Column(name = "poster_image_kr")
	private String thumbnailUrl;
	@Column(name = "genre_kr")
	private String genre;

	@OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
	private List<Characters> characters = new ArrayList<>();

	@OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
	private List<Scene> scenes = new ArrayList<>();

	@OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
	private List<SpotData> spotDataList = new ArrayList<>();

	@OneToMany(mappedBy = "drama", cascade = CascadeType.ALL)
	private List<Course> courseList = new ArrayList<>();

	@Builder
	public Drama(String dramaName, String thumbnailUrl, String genre) {
		this.dramaName = dramaName;
		this.thumbnailUrl = thumbnailUrl;
		this.genre = genre;
	}

	public DramaPreviewResponse toDramaPreviewResponse() {
		return DramaPreviewResponse.builder()
			.dramaId(this.id)
			.dramaTitle(this.dramaName)
			.thumbnailUrl(this.thumbnailUrl)
			.build();
	}

	//test
	public void initSpotList() {
		this.spotDataList = new ArrayList<>();
	}

}
