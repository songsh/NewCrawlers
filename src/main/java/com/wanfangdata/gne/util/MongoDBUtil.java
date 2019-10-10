package com.wanfangdata.gne.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
 
//mongodb 连接数据库工具类
public class MongoDBUtil {
	private volatile static MongoDatabase mongoDatabase;
    //不通过认证获取连接数据库对象
    public static MongoDatabase getConnect(){
    	if(mongoDatabase == null) {
    		synchronized(MongoDBUtil.class) {
    			if(mongoDatabase ==null) {
    				//连接到 mongodb 服务
    	            MongoClient mongoClient = new MongoClient("42.62.20.22", 27017);
    	            //连接到数据库
    	             mongoDatabase = mongoClient.getDatabase("db");
    			}
    		}
    	}
        return mongoDatabase;
    }
 
    //需要密码认证方式连接
    public static MongoDatabase getConnect2(){
    	if(mongoDatabase == null) {
    		synchronized(MongoDBUtil.class) {
    			if(mongoDatabase ==null) {
    				List<ServerAddress> adds = new ArrayList<>();
    		        //ServerAddress()两个参数分别为 服务器地址 和 端口
    		        ServerAddress serverAddress = new ServerAddress("42.62.20.22", 27017);
    		        adds.add(serverAddress);
    		        
    		        List<MongoCredential> credentials = new ArrayList<>();
    		        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
    		        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("dbadmin", "db", "123456".toCharArray());
    		        credentials.add(mongoCredential);
    		        
    		        //通过连接认证获取MongoDB连接
    		        MongoClient mongoClient = new MongoClient(adds, credentials);
    		 
    		        //连接到数据库
    		        mongoDatabase = mongoClient.getDatabase("db");
    			}
    		}
    	}
        
        return mongoDatabase;
    }
    
    
}
