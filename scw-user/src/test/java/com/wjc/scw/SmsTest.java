package com.wjc.scw;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.wjc.scw.user.component.HttpUtils;

public class SmsTest {

	public static void main(String[] args) {
		String host = "http://dingxin.market.alicloudapi.com";
		String path = "/dx/sendSms";
		String method = "POST";
		String appcode = "62c2ebd07c7c4a95b270feff6c896c6c";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", "18610025821");
		querys.put("param", "code:9966");
		querys.put("tpl_id", "TP1711063");
		Map<String, String> bodys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			// 获取response的body
			// System.out.println(EntityUtils.toString(response.getEntity()));
			//{"return_code":"00000","order_id":"ALY1549881237643456814"}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
