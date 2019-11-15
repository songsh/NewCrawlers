package com.wanfangdata.gne.extract.auto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.ContentExtractor;

public class AutoContentExtractor extends ContentExtractor {

	public AutoContentExtractor(ExtractBean bean) {
		super(bean);
		// TODO Auto-generated constructor stub
	}

	public void extract(Element doc) {
		Element body = doc.select("body").get(0);
		String result;

		List<CALCBean> list = new ArrayList();
		for (Element ele : body.select("*")) {
			int hash = ele.hashCode();
			CALCBean bean = new CALCBean();
			bean.setEle(ele);
			calc_text_density(ele, bean);

			double sbdi = calc_sbdi(bean.getText(), bean);

			int count_text_tag = count_text_tag(ele);
			bean.setSbdi(sbdi);
			bean.setText_tag_count(count_text_tag);
			list.add(bean);
		}
		double std = calc_std(list);
		calc_new_score(list, std);
		Collections.sort(list);
		result = list.get(0).getText();
		extractBean.setContent(list.get(0).getEle().cssSelector());

		System.out.println("content:" + result);

	}

	

}
