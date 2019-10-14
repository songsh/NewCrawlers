package com.wanfangdata.gne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.AuthorExtractor;
import com.wanfangdata.gne.extract.ContentExtractor;
import com.wanfangdata.gne.extract.DateExtractor;
import com.wanfangdata.gne.extract.ListExtractor;
import com.wanfangdata.gne.extract.TitleExtractor;
import com.wanfangdata.gne.util.HtmlUtils;
import com.wanfangdata.gne.util.HttpUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

//    	String url = "https://news.sina.cn/gn/?from=wrap";
    	String url = "http://www.cjddsb.com/ym/xhy/";
//    	String url = "https://readhub.cn/topics";
//    	String url = "http://www.xinhuanet.com/fortune/";
//    	String url = "https://news.163.com/world/";
//		String url = "http://military.people.com.cn/";
//		String url = "https://new.qq.com/tag/82542";
//		String url = "https://www.toutiao.com/";
//		String url = "http://gongyi.hebnews.cn/";
//		String url = "http://cn.chinadaily.com.cn/";

//		String input = HttpUtils.doGet(url);
		String input = HtmlUtils.getDocument(url);
		input = HtmlUtils.cleanHtml(input);
//		System.out.println(input);
		
		ExtractBean extractBean = ExtractBean.parse(url);
		ListExtractor listExtractor = new ListExtractor(extractBean);
		Set<String> urls = listExtractor.extract(input, url);

		ContentExtractor contentExtractor = new ContentExtractor(extractBean);
		AuthorExtractor authorExtractor = new AuthorExtractor();
		DateExtractor dateExtractor = new DateExtractor();
		TitleExtractor titleExtractor = new TitleExtractor(extractBean);
		for (String url2 : urls) {
			
			String detail = HttpUtils.doGet(url2);
			Document doc = null;
			try {
				doc = Jsoup.parse(detail);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(url2);
			titleExtractor.extract(doc);
			contentExtractor.extract(doc);
			authorExtractor.extract(doc);
			dateExtractor.extract(doc);

		}
		extractBean.writeToFile();
//		System.out.println(extractBean.toString());
		
//		long start = System.currentTimeMillis();
//
//		System.out.println(System.currentTimeMillis() - start);

	}

	

	// @Test
	public void test() {
		String home = System.getProperty("user.dir");
		String filepath = home + File.separator + "test" + File.separator + "163" + File.separator + "2.html";
		File input = new File(filepath);
		long start = System.currentTimeMillis();
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	ContentExtractor contentExtractor = new ContentExtractor();
//    	contentExtractor.extract(doc);
//    	AuthorExtractor authorExtractor = new AuthorExtractor();
//    	authorExtractor.extract(doc);
//    	DateExtractor dateExtractor = new DateExtractor();
//    	dateExtractor.extract(doc);
//    	TitleExtractor titleExtractor = new TitleExtractor();
//    	titleExtractor.extract(doc);
//    	ListExtractor listExtractor = new ListExtractor();
//    	listExtractor.extract(doc);

//    	Element selectFirst = doc.selectFirst("body");
//    	selectFirst.select("body body");
//    	selectFirst.select(" body");
		System.out.println(System.currentTimeMillis() - start);
	}

//	@Test
	public void testP() {
		String url = "http://gongyi.hebnews.cn/";

		String test = HtmlUtils.getDocument(url);
		Document doc = Jsoup.parse(test);
		String attr = doc.selectFirst(".dis").cssSelector();
		System.out.println(attr);
	}
	
	@Test
	public void testJson() {
		String test = "{\"test\":\"aaaa\"}";
		String result = JsonPath.read(test, "$.test");
		System.out.print(result);
	}
}
