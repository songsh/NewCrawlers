package com.wanfangdata.gne.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

	static OkHttpClient  client;

	static {
		 client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
	}
	
	public static String doGet(String url) {
		final Request request = new Request.Builder().url(url).get()// 默认就是GET请求，可以不写
				.build();
		
		final Call call = client.newCall(request);
		
		try {
			Response response = call.execute();
			if (response.isSuccessful()) {
				String contentType = response.header("Content-Type");
				byte[] byteStr = response.body().bytes();
				String result = new String(byteStr);
				
				if(contentType!=null) {
					if(contentType.toLowerCase().contains("charset=gb2312")) {
							result = new String(byteStr,"GB2312");
							return result;
					}else if(contentType.toLowerCase().contains("charset=gbk".toLowerCase())){
							result = new String(byteStr,"gbk");
							return result;
					}
				}
				
				String lowerResult = result.toLowerCase();
				if(lowerResult.contains("charset=gb2312")) {
					result = new String(byteStr,"GB2312");
					return result;
				}else if(lowerResult.contains("charset=gbk")){
					result = new String(byteStr,"gbk");
					return result;
				}else {
					return result;
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

}
