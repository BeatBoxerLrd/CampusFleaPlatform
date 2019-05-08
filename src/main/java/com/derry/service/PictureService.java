package com.derry.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PictureService {

	/**
	 * 上传，批量上传接口
	 * @param uploadFile
	 * @return
	 */
	Map uploadPicture(MultipartFile uploadFile);
}
