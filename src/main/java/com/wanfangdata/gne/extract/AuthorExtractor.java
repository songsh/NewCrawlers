package com.wanfangdata.gne.extract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AuthorExtractor {
	public static final String[] patterns = {"责编[：|:| |丨|/]\\s*([\\u4E00-\\u9FA5]{2,5})[^\\u4E00-\\u9FA5|:|：]",
											"作者[：|:| |丨|/]\\s*([\\u4E00-\\u9FA5]{2,5})[^\\u4E00-\\u9FA5|:|：]",
	                                          "编辑[：|:| |丨|/]\\s*([\\u4E00-\\u9FA5]{2,5})[^\\u4E00-\\u9FA5|:|：]",
	                                          "文[：|:| |丨|/]\\s*([\\u4E00-\\u9FA5]{2,5})[^\\u4E00-\\u9FA5|:|：]",
	                                          "记者[：|:| |丨|/]\\s*([\\u4E00-\\u9FA5]{2,5})[^\\u4E00-\\u9FA5|:|：]",
	                                          "撰文[：|:| |丨|/]\\s*([\\u4E00-\\u9FA5]{2,5})[^\\u4E00-\\u9FA5|:|：]"};
	
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

	private String extracByPattern(String content) {
		for(int i=0;i<patterns.length;i++) {
			Pattern compile = Pattern.compile(patterns[i]);
			Matcher matcher = compile.matcher(content);
			if(matcher.find()) {
				String author = matcher.group(1);
				
				return author;
			}
		}
		return "";
	}
	
	// h1 存在时，H2为AUTHOR
	private String extractByH(Element ele) {
		if(ele.select("h1").size()>0) {
			return ele.select("h2").text();
		}
		return "";
	}
	private String extractByMeta(Element ele) {
		String result = "";
		Elements select = ele.select("meta");
		if(select!=null) {
			for(Element child:select) {
				Attributes attributes = child.attributes();
				for(Attribute attr:attributes) {
					if(attr.getValue()!="" && attr.getValue().contains("author")) {
						result = child.attr("content");
						if(Pattern.matches("[\\u4E00-\\u9FA5]{2,10}", result)) {
							return result;
						}
						
					}
				}
				
				
			
			}
		}
		return "";
	}

}
