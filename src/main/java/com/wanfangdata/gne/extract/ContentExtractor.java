package com.wanfangdata.gne.extract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;
import com.wanfangdata.gne.bean.ExtractBean;

public class ContentExtractor {
	
	private static final String punctuation = "['''！，。？；：“”‘’《》%（）,.?:;'\"!%()''']";
	private ExtractBean extractBean;
	
	public ContentExtractor(ExtractBean bean) {
		this.extractBean = bean;
	}
	public void extract(Document doc) {
		Element body = doc.select("body").get(0);
		String result;
		if(extractBean.getContent()!=null) {
			result = extractByTag(doc);
		}else {
			List<CALCBean> list = new ArrayList();
			for(Element ele:body.select("*")) {
				int hash = ele.hashCode();
				CALCBean bean = new CALCBean();
				bean.setEle(ele);
				calc_text_density(ele,bean);
				
				double sbdi = calc_sbdi(bean.getText(),bean);
				
				int count_text_tag = count_text_tag(ele);
				bean.setSbdi(sbdi);
				bean.setText_tag_count(count_text_tag);
				list.add(bean);
			}
			double std = calc_std(list);
			calc_new_score(list,std);
			Collections.sort(list);
			result = list.get(0).getText();
			extractBean.setContent(list.get(0).getEle().cssSelector());
		}
		System.out.println("content:" + result);
		
	}
	
	private String extractByTag(Element body) {
		Element ele = body.selectFirst(extractBean.getContent());
		if(ele!=null) {
			return ele.text();
		}
		return "";
	}

	/**
	 * 根据公式：
	
               Ti - LTi
        TDi = -----------
              TGi - LTGi
        Ti:节点 i 的字符串字数
        LTi：节点 i 的带链接的字符串字数
        TGi：节点 i 的标签数
        LTGi：节点 i 的带连接的标签数
	 * @param ele
	 * @return
	 */
	private void calc_text_density(Element ele,CALCBean bean) {
		try {
			String ti_text = getAllTextOfElement(ele);
			
			int ti = count_alltext_num(ti_text);
			String lti_text = getAllTextOfElement(ele.select("a"));
			int lti = count_alltext_num(lti_text);
			int tgi = ele.select("*").size();
			int ltgi = ele.select("a").size();
			bean.setTgi(tgi);
			bean.setLtgi(ltgi);
			double density;
			if(tgi - ltgi == 0) {
				density = 0f;
			}else {
				density = (ti-lti) * 1.0f / (tgi - ltgi);
			}
//			System.out.println( ti + " " + lti + " " + tgi + " " + ltgi);
			 
//			 System.out.println(ti_text);
			 bean.setDensity(density);
			 bean.setText(ti_text);
			 bean.setTi(ti);
			 bean.setLti(lti);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 *         Ti - LTi
        SbDi = --------------
                 Sbi + 1

        SbDi: 符号密度
        Sbi：符号数量
	 * @param text
	 * @param ti
	 * @param lti
	 * @return
	 */
	private double calc_sbdi(String text,CALCBean bean) {
		int sbi = count_punctuation_num(text);
		if(sbi < 0) {
			sbi = 0;
		}
		bean.setSbi(sbi);
		double sbdi = (bean.getTi() - bean.getLti()) * 1.0f / (sbi + 1);
		return sbdi;
	}
	
	/**
	 * 统计文本中标点数量
	 * @param text
	 * @return
	 */
	private int count_punctuation_num(String text) {
		String tmp = text.replaceAll(punctuation, "");
		return text.length() - tmp.length();
		
	}
	
	private int count_alltext_num(String text) {
		String tmp = text.replaceAll(punctuation, "").replace(" ", "");
		return text.length();
	}

	private double calc_std(List<CALCBean> list) {
		double total = 0;
		for(CALCBean bean:list) {
			total += bean.getDensity();
		}
		double average = total / list.size();
		total = 0;
		for(CALCBean bean:list) {
			total += (bean.getDensity() - average) * (bean.getDensity() - average);
		}
		double std = Math.sqrt(total / list.size());
//		System.out.println(std);
		return std;
	}
	private void calc_new_score(List<CALCBean> list,double std ) {
		double score = 0.0f;
		for(CALCBean bean:list) {
			if(bean.getDensity() != 0 && bean.getSbdi() !=0) {
				score = Math.log(std) * bean.getDensity() * Math.log10(bean.getText_tag_count() + 1) * Math.log(bean.getSbdi());
				
			}else {
				score = 0;
			}
			if(Double.isNaN(score)) {
				score = 0;
			}
			bean.setScore(score);
		}
	}
	private String getAllTextOfElement(Element ele) {
		return ele.text();
	}
	
	private String getAllTextOfElement(Elements ele) {
		StringBuffer sb = new StringBuffer();
		for(Element eleChild:ele) {
			sb.append(eleChild.text());
		}
		return sb.toString();
		
	}
	private int count_text_tag(Element ele) {
		int result = ele.select("p").size();
		if(ele.tagName() == "p") {
			result --;
		}
		return result;
	}
	
	public static class CALCBean implements Comparable{
		double density;
		int text_tag_count;
		double sbdi;
		String text;
		int ti;
		int lti;
		double score;
		Element ele;
		int tgi;
		int ltgi;
		int sbi;
		
		
		

		public int getSbi() {
			return sbi;
		}
		public void setSbi(int sbi) {
			this.sbi = sbi;
		}
		public int getTgi() {
			return tgi;
		}
		public void setTgi(int tgi) {
			this.tgi = tgi;
		}
		public int getLtgi() {
			return ltgi;
		}
		public void setLtgi(int ltgi) {
			this.ltgi = ltgi;
		}
		public Element getEle() {
			return ele;
		}
		public void setEle(Element ele) {
			this.ele = ele;
		}
		public int getTi() {
			return ti;
		}
		public void setTi(int ti) {
			this.ti = ti;
		}
		public int getLti() {
			return lti;
		}
		public void setLti(int lti) {
			this.lti = lti;
		}
		public double getScore() {
			return score;
		}
		public void setScore(double score) {
			this.score = score;
		}
		public double getDensity() {
			return density;
		}
		public void setDensity(double density) {
			this.density = density;
		}
		public int getText_tag_count() {
			return text_tag_count;
		}
		public void setText_tag_count(int text_tag_count) {
			this.text_tag_count = text_tag_count;
		}
		public double getSbdi() {
			return sbdi;
		}
		public void setSbdi(double sbdi) {
			this.sbdi = sbdi;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int compareTo(Object o) {
			if(this.score < ((CALCBean)o).getScore()) {
				return 1;
			}else if(this.score == ((CALCBean)o).getScore()){
				return 0;
			}else {
				return -1;
			}
		}
		
		
		
		
	}

}
