package com.wanfangdata.gne.extract.tag;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.DateExtractor;

public class TagDateExtractor extends DateExtractor {

	public TagDateExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void extract(Element ele) {
		String result = extractByTag(ele);
		System.out.println(result);
	}
	
	protected String extractByTag(Element body) {
		Element ele = body.selectFirst(extractBean.getDate());
		if(ele!=null) {
			return ele.text();
		}
		return "";
	}

}
