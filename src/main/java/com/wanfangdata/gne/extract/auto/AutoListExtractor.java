package com.wanfangdata.gne.extract.auto;

import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.ListExtractor;

public class AutoListExtractor extends ListExtractor {

	public AutoListExtractor(ExtractBean bean) {
		super(bean);
	}

	public Set<String> extract(String content,String url) {
		Document doc = null;
    	try {
			doc = Jsoup.parse(content);
			doc.setBaseUri(getDomain(url));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	extract(doc);
    	return urls;
    	
	}
}
