package com.wanfangdata.gne.extract.factory;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.auto.AutoAuthorExtractor;
import com.wanfangdata.gne.extract.auto.AutoContentExtractor;
import com.wanfangdata.gne.extract.auto.AutoDateExtractor;
import com.wanfangdata.gne.extract.auto.AutoListExtractor;
import com.wanfangdata.gne.extract.auto.AutoTitleExtractor;
import com.wanfangdata.gne.extract.tag.TagAuthorExtractor;
import com.wanfangdata.gne.extract.tag.TagContentExtractor;
import com.wanfangdata.gne.extract.tag.TagDateExtractor;
import com.wanfangdata.gne.extract.tag.TagListExtractor;
import com.wanfangdata.gne.extract.tag.TagTitleExtractor;

public class ExtractFactory {
	
	
	public static Extractor get(String tag,ExtractBean bean) {
		switch(tag) {
		default:
		case "list":
			if(bean.getItems()!=null) {
				return new TagListExtractor(bean);
			}else {
				return new AutoListExtractor(bean);
			}
		case "title":
			if(bean.getTitle()!=null) {
				return new TagTitleExtractor(bean);
			}else {
				return new AutoTitleExtractor(bean);
			}
		case "author":
			if(bean.getAuthor()!=null) {
				return new TagAuthorExtractor(bean);
			}else {
				return new AutoAuthorExtractor(bean);
			}
		case "date":
			if(bean.getDate()!=null) {
				return new TagDateExtractor(bean);
			}else {
				return new AutoDateExtractor(bean);
			}
		case "content":
			if(bean.getContent()!=null) {
				return new TagContentExtractor(bean);
			}else {
				return new AutoContentExtractor(bean);
			}
		}
	}

}
