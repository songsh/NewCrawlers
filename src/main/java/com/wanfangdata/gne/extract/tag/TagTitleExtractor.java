package com.wanfangdata.gne.extract.tag;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.TitleExtractor;

public class TagTitleExtractor extends TitleExtractor {

	
	public TagTitleExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void extract(Element ele) {
		String result = "";
		if(extractBean.getTitle() !=null) {
			result = ele.selectFirst(extractBean.getTitle()).text();
		}
		System.out.println(result);
		return;
	}

}
