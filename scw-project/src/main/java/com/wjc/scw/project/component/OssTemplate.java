package com.wjc.scw.project.component;

import java.io.InputStream;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Data
@Slf4j
//@Component
public class OssTemplate {

	// @Value("${oss.endpoint}")
	String endpoint;
	String accessKeyId;
	String accessKeySecret;
	String bucket;

	public String upload(String filename, InputStream inputStream) {

		log.debug("endpoint={}", endpoint);
		log.debug("accessKeyId={}", accessKeyId);
		log.debug("accessKeySecret={}", accessKeySecret);
		log.debug("bucket={}", bucket);

		try {
			// 创建OSSClient实例。
			OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

			// 上传文件流。
			// InputStream inputStream = new FileInputStream("D:/temp/p2.jpg");
			ossClient.putObject(bucket, "pic/" + filename, inputStream);

			// 关闭OSSClient。
			ossClient.shutdown();

			String filepath = "https://" + bucket + "." + endpoint + "/pic/" + filename;

			log.debug("文件上传成功-{}", filepath);

			// https://atcrowdfunding20190717.oss-cn-beijing.aliyuncs.com/pic/p1.jpg
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("文件上传失败-{}", filename);
			return null;
		}

	}

}
