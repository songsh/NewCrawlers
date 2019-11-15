package com.wanfangdata.gne.extract.abs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.factory.Extractor;

public abstract class DateExtractor implements Extractor{

	protected ExtractBean extractBean;

	public DateExtractor(ExtractBean bean) {
		this.extractBean = bean;
	}
	
	public static final String[] patterns = {
			"(\\d{4}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[0-1]?[0-9]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{4}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[2][0-3]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{4}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[0-1]?[0-9]:[0-5]?[0-9])",
			"(\\d{4}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[2][0-3]:[0-5]?[0-9])",
			"(\\d{4}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[1-24]\\d时[0-60]\\d分)([1-24]\\d时)",
			"(\\d{2}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[0-1]?[0-9]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{2}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[2][0-3]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{2}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[0-1]?[0-9]:[0-5]?[0-9])",
			"(\\d{2}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[2][0-3]:[0-5]?[0-9])",
			"(\\d{2}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2}\\s*?[1-24]\\d时[0-60]\\d分)([1-24]\\d时)",
			"(\\d{4}年\\d{1,2}月\\d{1,2}日\\s*?[0-1]?[0-9]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{4}年\\d{1,2}月\\d{1,2}日\\s*?[2][0-3]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{4}年\\d{1,2}月\\d{1,2}日\\s*?[0-1]?[0-9]:[0-5]?[0-9])",
			"(\\d{4}年\\d{1,2}月\\d{1,2}日\\s*?[2][0-3]:[0-5]?[0-9])",
			"(\\d{4}年\\d{1,2}月\\d{1,2}日\\s*?[1-24]\\d时[0-60]\\d分)([1-24]\\d时)",
			"(\\d{2}年\\d{1,2}月\\d{1,2}日\\s*?[0-1]?[0-9]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{2}年\\d{1,2}月\\d{1,2}日\\s*?[2][0-3]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{2}年\\d{1,2}月\\d{1,2}日\\s*?[0-1]?[0-9]:[0-5]?[0-9])",
			"(\\d{2}年\\d{1,2}月\\d{1,2}日\\s*?[2][0-3]:[0-5]?[0-9])",
			"(\\d{2}年\\d{1,2}月\\d{1,2}日\\s*?[1-24]\\d时[0-60]\\d分)([1-24]\\d时)",
			"(\\d{1,2}月\\d{1,2}日\\s*?[0-1]?[0-9]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{1,2}月\\d{1,2}日\\s*?[2][0-3]:[0-5]?[0-9]:[0-5]?[0-9])",
			"(\\d{1,2}月\\d{1,2}日\\s*?[0-1]?[0-9]:[0-5]?[0-9])", "(\\d{1,2}月\\d{1,2}日\\s*?[2][0-3]:[0-5]?[0-9])",
			"(\\d{1,2}月\\d{1,2}日\\s*?[1-24]\\d时[0-60]\\d分)([1-24]\\d时)", "(\\d{4}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2})",
			"(\\d{2}[-|/|.]\\d{1,2}[-|/|.]\\d{1,2})", "(\\d{4}年\\d{1,2}月\\d{1,2}日)", "(\\d{2}年\\d{1,2}月\\d{1,2}日)",
			"(\\d{1,2}月\\d{1,2}日)" };

	public abstract void extract(Element ele);
}