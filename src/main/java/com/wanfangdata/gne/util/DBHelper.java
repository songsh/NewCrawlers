package com.wanfangdata.gne.util;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class DBHelper {
	
	public void insert(String url){
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect2();
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("sina");
        //要插入的数据
        Document document = new Document("url",url);
        //插入一个文档
        collection.insertOne(document);
    }
	
	public void deleteOne(String url){
	    //获取数据库连接对象
	    MongoDatabase mongoDatabase = MongoDBUtil.getConnect2();
	    //获取集合
	    MongoCollection<Document> collection = mongoDatabase.getCollection("sina");
	    //申明删除条件
	    Bson filter = Filters.eq("url",url);
	    //删除与筛选器匹配的单个文档
	    collection.deleteOne(filter);
	}
	
	public boolean checkExists(String url){
	    //获取数据库连接对象
	    MongoDatabase mongoDatabase = MongoDBUtil.getConnect2();
	    //获取集合
	    MongoCollection<Document> collection = mongoDatabase.getCollection("sina");
	    //指定查询过滤器
	    Bson filter = Filters.eq("url", url);
	    //指定查询过滤器查询
	    FindIterable findIterable = collection.find(filter);
	    MongoCursor cursor = findIterable.iterator();
	    if (cursor.hasNext()) {
	       return true;
	    }else {
	    	return false;
	    }
	}
	
	public void find(String url) {
		MongoDatabase mongoDatabase = MongoDBUtil.getConnect2();
	    //获取集合
	    MongoCollection<Document> collection = mongoDatabase.getCollection("sina");
	    //查找集合中的所有文档
	    FindIterable findIterable = collection.find();
	    MongoCursor cursor = findIterable.iterator();
	    while (cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }

	}
	
	
	public void deleteOneTest(){
	    //获取数据库连接对象
	    MongoDatabase mongoDatabase = MongoDBUtil.getConnect2();
	    //获取集合
	    MongoCollection<Document> collection = mongoDatabase.getCollection("sina");
	    //申明删除条件
	    Bson filter = Filters.eq("age",18);
	    //删除与筛选器匹配的单个文档
	    collection.deleteOne(filter);
	}



}
