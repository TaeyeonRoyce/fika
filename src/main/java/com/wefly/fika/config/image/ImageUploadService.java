package com.wefly.fika.config.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageUploadService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${bucket.domain}")
	private String bucketHost;

	private final AmazonS3 amazonS3;

	public List<String> upload(List<MultipartFile> multipartFile) throws Exception {

		List<String> fileNameList = new ArrayList<>();

		multipartFile.forEach(file -> {
			String fileName = createFileName(file.getOriginalFilename());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());
			objectMetadata.setContentType(file.getContentType());

			try (InputStream inputStream = file.getInputStream()) {
				amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				log.warn("[IMAGE UPLOAD FAIL]");
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패");
			}

			fileNameList.add(bucketHost + fileName);
		});

		return fileNameList;
	}

	private String createFileName(String fileName) {
		// 랜덤으로 파일 이름 생성
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	// 파일 확장자 전달
	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			log.warn("[IMAGE UPLOAD FAIL] : Invalid file name format");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 (" + fileName + ") 입니다.");
		}
	}

	public void remove(String imageUrl) throws Exception {
		String fileName = imageUrl.split(bucketHost)[1];
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
	}
}
