package com.wefly.fika.config.image;

import static com.wefly.fika.config.response.ApiResponseStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.wefly.fika.config.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class ImageController {

	private final ImageUploadService imageUploadService;

	@PostMapping("/file")
	public ResponseEntity<ApiResponse> upload(
		@RequestPart List<MultipartFile> multipartFile) throws Exception {

		try {
			log.debug("[IMAGE UPLOAD]");
			List<String> fileImageList = imageUploadService.upload(multipartFile);
			log.debug("[IMAGE UPLOAD] : Success");
			return new ApiResponse<>(fileImageList).toResponseEntity();
		} catch (MaxUploadSizeExceededException e) {
			return new ApiResponse<>(OVER_FILE_UPLOAD_LIMIT).toResponseEntity();
		}

	}

	@DeleteMapping("/file")
	public ResponseEntity<ApiResponse> remove(@RequestParam String fileName) throws Exception {

		log.debug("[IMAGE DELETE]");
		imageUploadService.remove(fileName);
		log.debug("[IMAGE DELETE] : {}", fileName);

		return new ApiResponse<>(fileName).toResponseEntity();
	}

}
