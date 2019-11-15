package com.wanfangdata.gne.extract.abs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wanfangdata.gne.bean.ExtractBean;
import com.wanfangdata.gne.extract.factory.Extractor;

public abstract class ListExtractor implements Extractor{

	protected static final int MAXCHILD = 7;
	protected Set<String> urls = new LinkedHashSet<String>();
	protected Set<Element> eles = new LinkedHashSet<Element>();
	
	protected ExtractBean extractBean;

	public ListExtractor(ExtractBean bean) {
		this.extractBean = bean;
	}
	
	public abstract Set<String> extract(String content,String url) ;
	
	protected void extractByTag(Document doc) {
		Elements select = doc.select(extractBean.getItems() + " > " + extractBean.getItem());
		for (Element child : select) {
			Element childA = child.selectFirst(child.tagName()+ " a");
			if (childA != null && childA.text() != "") {
				String href = childA.attr("abs:href");
				urls.add(href);
			}
		}
		
	}
	protected String getDomain(String url) {
		URI uri;
		try {
			uri = new URI(url);
			String domain = uri.getScheme() +"://"+ uri.getHost() + (uri.getPort()==-1?"":uri.getPort());
			return domain;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	public void extract(Element ele) {
		findList(ele.selectFirst("body"));
		if(eles.size()>0) {
			Element findMax = findMax(eles);
			findHref(findMax, findMax.child(1).tagName());
			extractBean.setItems(findMax.cssSelector());
			extractBean.setItem(findMax.child(1).tagName());
		}
	}

	/**
	 * 选择文本长度最大的ele
	 * @param eles
	 * @return
	 */
	protected Element findMax(Set<Element> eles) {
		Element result = null;
		for(Element ele:eles) {
			if(result == null) {
				result = ele;
			}
			if(result.text().length() < ele.text().length()) {
				result = ele;
			}
		}
		return result;
	}

	/**
	 * 子元素大于10,且标签相同，有a孙元素, a孙元素文本>4
	 * 递归查找list 集合
	 * @param ele
	 */
	public void findList(Element ele) {
		if (ele.children() != null) {
			if (ele.children().size() >= MAXCHILD) {
				
				Element grandChild = ele.child(1);
				String tag = grandChild.tagName();
				String selector;
//				Set<String> grandClassSet = grandChild.classNames();
//				if(grandClassSet!=null && grandClassSet.size()>0) {
//					String grandClass = grandClassSet.iterator().next();
//					selector = ele.cssSelector() + " > "+ tag;
//				}else 
				{
					selector = ele.cssSelector() + " > "+ tag;
				}
				// item 下有a标签,并且a标签文本长度>4 或有img
				if (ele.select(selector).size() >= MAXCHILD && grandChild.select(grandChild.tagName() + " a") != null && (grandChild.select(grandChild.tagName() + " a").text().length()>4 || grandChild.select(grandChild.tagName() + " a>img")!=null)) {
					eles.add(ele);
//					findHref(ele, tag);
//					if (urls.size() >= MAXCHILD) {
//						return;
//					}

				}
			}
			for (Element child : ele.children()) {
				findList(child);
//				if (urls.size() >= MAXCHILD) {
//					return;
//				}
			}

		}
	}

	protected void findHref(Element chi, String tag) {
		Elements select = chi.select(" > " + tag);
		
		for (Element child : select) {
			Element childA = child.selectFirst(child.tagName()+ " a");
			if (childA != null && childA.text() != "") {
				String href = childA.attr("abs:href");
				urls.add(href);
//				System.out.println(child.html());
//				System.out.println(href);
			}

		}

	}

}
