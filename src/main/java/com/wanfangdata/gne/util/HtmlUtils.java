package com.wanfangdata.gne.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUtils {

	public static String getDomain(String url) {
		URI uri;
		try {
			uri = new URI(url);
			String domain = uri.getScheme() + "://" + uri.getHost() + (uri.getPort() == -1 ? "" : uri.getPort());
			return domain;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";

	}
	
	public static String getMainDomain(String url) {
		URI uri;
		try {
			uri = new URI(url);
			String domain =  uri.getHost().replace("www.", "");
			return domain;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	public static String cleanHtml(String content) {
		String reg_tag = "<[\\s]*?#t#[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?#t#[\\s]*?>".replace("#t#", "script");  
		content = Pattern.compile(reg_tag,Pattern.CASE_INSENSITIVE).matcher(content).replaceAll(""); 
		String reg_tag2 = "<[\\s]*?#t#[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?#t#[\\s]*?>".replace("#t#", "style");  
		content = Pattern.compile(reg_tag2,Pattern.CASE_INSENSITIVE).matcher(content).replaceAll("");
		return content;
	}
	public static String getDocument(String url) {

		try {
			WebClient wc = new WebClient(BrowserVersion.CHROME);
			// 是否使用不安全的SSL
			wc.getOptions().setUseInsecureSSL(true);
			// 启用JS解释器，默认为true
			wc.getOptions().setJavaScriptEnabled(true);
			// 禁用CSS
			wc.getOptions().setCssEnabled(false);
			// js运行错误时，是否抛出异常
			wc.getOptions().setThrowExceptionOnScriptError(false);
			// 状态码错误时，是否抛出异常
			wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
			// 是否允许使用ActiveX
			wc.getOptions().setActiveXNative(false);
			// 等待js时间
			wc.waitForBackgroundJavaScript(600 * 1000);
			// 设置Ajax异步处理控制器即启用Ajax支持
			wc.setAjaxController(new NicelyResynchronizingAjaxController());
			// 设置超时时间
			wc.getOptions().setTimeout(1000000);
			// 不跟踪抓取
			wc.getOptions().setDoNotTrackEnabled(false);
			WebRequest request = new WebRequest(new URL(url));
			request.setAdditionalHeader("User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.75 Safari/537.36");

			// 模拟浏览器打开一个目标网址
			HtmlPage htmlPage = wc.getPage(request);
			// 为了获取js执行的数据 线程开始沉睡等待
			Thread.sleep(5000);// 这个线程的等待 因为js加载需要时间的
			// 以xml形式获取响应文本
			String xml = htmlPage.asXml();
			return xml;
			// 并转为Document对象return
//			return Jsoup.parse(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
