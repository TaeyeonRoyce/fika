package com.wefly.fika.service;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.scene.Scene;
import com.wefly.fika.dto.scene.SceneSaveDto;
import com.wefly.fika.exception.NoSuchDataFound;

public interface ISceneService {

	Scene saveScene(SceneSaveDto saveDto) throws CustomException;
}
