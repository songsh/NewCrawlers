package com.wanfangdata.gne.extract.auto;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.AuthorExtractor;

public class AutoAuthorExtractor extends AuthorExtractor {
	
	public AutoAuthorExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	public void extract(Element ele) {
		String content = ele.text();
		String author = extracByPattern(content);
		if(author == "") {
			author = extractByMeta(ele);
			if(author == "") {
				author = extractByH(ele);
			}
		}
	
		System.out.println(author);
	}

}
