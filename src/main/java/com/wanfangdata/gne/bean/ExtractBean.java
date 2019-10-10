package com.wanfangdata.gne.bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.google.gson.Gson;

public class ExtractBean {
	private String items;
	private String item;
	private String url;
	private String title;
	private String author;
	private String date;
	private String content;

	public static ExtractBean parse(String url) {
		File file = new File(url.hashCode()+".txt");
		if(file.exists()) {
			StringBuffer sb = new StringBuffer();
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));
				String line=null;
				while((line=br.readLine())!=null) {
					sb.append(line);
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Gson gson = new Gson();
			ExtractBean bean = gson.fromJson(sb.toString(), ExtractBean.class);
			return bean;
		}else {
			return new ExtractBean(url);
		}
	}
	public ExtractBean(String url) {
		this.url = url;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ExtractBean [items=" + items + ", item=" + item + ", url=" + url + ", title=" + title + ", author="
				+ author + ", date=" + date + ", content=" + content + "]";
	}

	public void writeToFile() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			File file = new File(this.url.hashCode()+".txt");
			System.out.println(file.getAbsoluteFile());
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(json);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

}
