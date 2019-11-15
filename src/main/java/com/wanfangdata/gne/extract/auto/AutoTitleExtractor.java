package com.wanfangdata.gne.extract.auto;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.TitleExtractor;

public class AutoTitleExtractor extends TitleExtractor {

	public AutoTitleExtractor(ExtractBean bean) {
		super(bean);
	}

	public void extract(Element ele) {
		String result;
		result = extractByAuto(ele);
		System.out.println(result);
		return;
	}
}
