package com.derry.test;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;


import java.io.File;
import java.io.FileInputStream;

public class PictureFTPTest {

	// 测试 ftp 上传图片功能
	@Test
	public void testFtpClient() throws Exception {
		try {
			// 1. 创建一个FtpClient对象
			FTPClient ftpClient = new FTPClient();
			// 2. 创建 ftp 连接
			ftpClient.connect("39.107.247.211", 21);
			// 3. 登录 ftp 服务器
			ftpClient.login("ftpuser", "liuruidong123");
			// 4. 读取本地文件
			FileInputStream inputStream = new FileInputStream(new File("E:\\Pictures\\1.jpg"));
			// 5.设置为被动模式(如上传文件夹成功，不能上传文件，注释这行，否则报错refused:connect  )
			ftpClient.enterLocalPassiveMode();
			// 6. 设置上传的路径
			ftpClient.changeWorkingDirectory("/usr/local/nginx/html/images/");
			// 7. 修改上传文件的格式为二进制
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 8. 服务器存储文件，第一个参数是存储在服务器的文件名，第二个参数是文件流
			ftpClient.storeFile("ll.jpg", inputStream);
			// 9. 关闭连接
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Test
	public void testFTPClient2() throws Exception {
		try {
			//创建一个FTPClient对象
			FTPClient ftpClient = new FTPClient();
			//创建ftp链接
			ftpClient.connect("39.107.247.211", 21);
			//登录ftp，使用用戶名和密碼
			ftpClient.login("ftpuser", "liuruidong123");
			//读取本地文件
			FileInputStream inputStream = new FileInputStream(new File("E:\\Pictures\\1.jpg"));
			//设置为被动模式(如上传文件夹成功，不能上传文件，注释这行，否则报错refused:connect  )
			ftpClient.enterLocalPassiveMode();
			//设置上传路径
			ftpClient.changeWorkingDirectory("/usr/local/nginx/html/images/");
			//修改上传文件格式
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println("1");
			//上传文件
			ftpClient.storeFile("hello1.jpg", inputStream);
			System.out.println("2");
			//关闭链接
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
}
