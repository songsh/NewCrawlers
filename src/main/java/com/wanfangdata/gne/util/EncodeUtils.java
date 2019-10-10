package com.wanfangdata.gne.util;

import java.net.URLEncoder;

public class EncodeUtils {

	public static String gb2312ToUtf8(String str) {

		String urlEncode = "";

		try {

			urlEncode = URLEncoder.encode(str, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlEncode;
	}
}
