package com.wanfangdata.gne.extract.auto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.abs.DateExtractor;

public class AutoDateExtractor extends DateExtractor {

	public AutoDateExtractor(ExtractBean bean) {
		super(bean);
	}

	public void extract(Element ele) {
		String content = ele.text();
		for(int i=0;i<patterns.length;i++) {
			Pattern compile = Pattern.compile(patterns[i]);
			Matcher matcher = compile.matcher(content);
			if(matcher.find()) {
				String author = matcher.group(1);
				System.out.println(author);
				return;
			}
		}
	}
}
