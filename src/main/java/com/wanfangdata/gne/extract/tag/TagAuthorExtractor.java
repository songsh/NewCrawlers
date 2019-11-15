package com.wanfangdata.gne.extract.tag;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.AuthorExtractor;

public class TagAuthorExtractor extends AuthorExtractor {

	public TagAuthorExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void extract(Element ele) {
		String result;
		result = extractByTag(ele);
		System.out.println(result);

	}
	
	protected String extractByTag(Element body) {
		Element ele = body.selectFirst(extractBean.getAuthor());
		if(ele!=null) {
			return ele.text();
		}
		return "";
	}

}
