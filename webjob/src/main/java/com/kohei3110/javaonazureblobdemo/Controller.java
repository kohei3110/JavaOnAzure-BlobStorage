package com.kohei3110.javaonazureblobdemo;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.kohei3110.javaonazureblobdemo.MonitorFiles.Factory;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.service.DeleteUploadedFileService;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.service.ListFilesService;
import com.kohei3110.javaonazureblobdemo.MonitorFiles.service.UploadBlobService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@ComponentScan("{com.kohei3110.javaonazureblobdemo.MonitorFiles}")
@EnableScheduling
public class Controller {

	static Logger logger = Logger.getLogger(Controller.class.getName());

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Controller.class, args);
	}

	/**
	 * 毎分ディレクトリ内のファイル（最終更新日時が1分以内）をアップロード
	 * @throws Exception
	 */
	@Scheduled(cron = "0 * * * * *", zone = "Asia/Tokyo")
	public static void init() throws Exception {
		ListFilesService listFilesService = injectListFilesService();
		UploadBlobService uploadBlobService = injectUploadBlobService();
		List<String> files = listFilesService.listFiles();
		uploadBlobService.store(files);
	}

	/**
	 * 1時間に1回ディレクトリ内のファイルを削除
	 * @throws IOException
	 */
	@Scheduled(cron = "* 0 * * * *", zone = "Asia/Tokyo")
	public static void delete() throws IOException {
		DeleteUploadedFileService deleteFileService = injectDeleteFileService();
		deleteFileService.deleteFiles();
	}

	static Factory factory = new Factory();

	static ListFilesService injectListFilesService() {
		return factory.getListFilesServiceInstance();
	}

	static UploadBlobService injectUploadBlobService() {
		return factory.getUploadBlobServiceInstance();
	}

	static DeleteUploadedFileService injectDeleteFileService() {
		return factory.getDeleteFileServiceInstance();
	}
}
