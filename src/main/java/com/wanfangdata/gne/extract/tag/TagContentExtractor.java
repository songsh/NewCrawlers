package com.wanfangdata.gne.extract.tag;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.ContentExtractor;

public class TagContentExtractor extends ContentExtractor {

	public TagContentExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void extract(Element doc) {
		String result;
		result = extractByTag(doc);
		System.out.println(result);
	}
	
	protected String extractByTag(Element body) {
		Element ele = body.selectFirst(extractBean.getContent());
		if(ele!=null) {
			return ele.text();
		}
		return "";
	}

}
