package com.wanfangdata.gne;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.wanfangdata.gne.util.DBHelper;
import com.wanfangdata.gne.util.HtmlUtils;
import com.wanfangdata.gne.util.HttpUtils;

public class Crawler {
	
	private Queue<String> urls = new ArrayBlockingQueue<String>(2024);
	private List<String> resultUrl = new ArrayList<String>();
	
	ExecutorService pool = Executors.newFixedThreadPool(2);
	
	private DBHelper dbHelper = new DBHelper();
	
	
	public void crawl(String url) {
		urls.add(url);
		
		while(true) {
			final String tmp = urls.poll();
			pool.execute(new Runnable() {

				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					crawlChild(tmp);
					
				}});
		}
		
	}
	public void crawlChild(String url) {
		if(url!=null && !dbHelper.checkExists(url)) {
			dbHelper.insert(url);
			System.out.println(url);
			String content = HttpUtils.doGet(url);
			Document doc = Jsoup.parse(content);
			String domain = HtmlUtils.getDomain(url);
			doc.setBaseUri(domain);
			
			Element body = doc.selectFirst("body");
			Elements hrefs = body.select("a[href*="+ HtmlUtils.getMainDomain(url)+"]");
			for(Element href:hrefs) {
				urls.add(href.absUrl("href"));
			}
			
			
		}	
	}
	
	@Test
	public void testCrawl() {
		crawl("http://www.sina.com.cn");
	}
	
//	@Test
	public void testMongo() {
		DBHelper dbHelper = new DBHelper();
		dbHelper.insert("http://www.baidu.com");
		dbHelper.find("http://www.baidu.com");
	}

}
