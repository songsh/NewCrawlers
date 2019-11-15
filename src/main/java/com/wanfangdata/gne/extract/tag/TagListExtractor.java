package com.wanfangdata.gne.extract.tag;

import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.ListExtractor;

public class TagListExtractor extends ListExtractor {

	
	public TagListExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> extract(String content, String url) {
		Document doc = null;
    	try {
			doc = Jsoup.parse(content);
			doc.setBaseUri(getDomain(url));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(extractBean.getItem()!=null) {
    		extractByTag(doc);
    	}
		return urls;
	}

}
