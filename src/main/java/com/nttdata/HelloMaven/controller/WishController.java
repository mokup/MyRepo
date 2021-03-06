package com.nttdata.HelloMaven.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;
import com.nttdata.HelloMaven.dao.WishDao;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WishController {
	
	private static final Logger logger = LoggerFactory.getLogger(WishController.class);
	
	@Resource
    WishDao showDao;
	

	@GetMapping("/welcome")
	public String greeting() {
		return "welcome at all 2";
	}   
	
	@GetMapping("/storeData")
	public String storeData() {
		 Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		 KeyFactory keyFactory= datastore.newKeyFactory().setKind("prova");
		 Key taskKey1 = keyFactory.newKey("prova1");
		 Entity task1 = Entity.newBuilder(taskKey1)
			        .set("category", "Personal")
			        .set("done", false)
			        .set("priority", 4)
			        .set("description", "Learn Cloud Datastore")
			        .build();
			  
			    datastore.put(task1);
			    return "OK";
		    
		   
		    
//		    String keyName = "test id:5634161670881280";
//		 Key key = datastore.newKeyFactory().setKind("test").newKey(keyName);
//		 Entity entity = datastore.get(key);
//		 return entity.toString();
	}   
	
	@GetMapping("/getData")
	public String getData() {
		String string = "";
		EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder().setKind("prova");
		 Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
			QueryResults<Entity> tasks = datastore.run(queryBuilder.build());
			while (tasks.hasNext()) {
			  Entity task = tasks.next();
			  // do something with the task
			  string+=task.getKey().getName()+",";
			}
		    return string;
		   
		    
//		    String keyName = "test id:5634161670881280";
//		 Key key = datastore.newKeyFactory().setKind("test").newKey(keyName);
//		 Entity entity = datastore.get(key);
//		 return entity.toString();
	}   
	
	@GetMapping("/getDataByKeyName")
	public String getDataByKeyName() {
		

		 Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		 Key key = datastore.newKeyFactory().setKind("prova").newKey("prova1");

		 
		 return datastore.get(key).getValue("category").get().toString();
		   
		
	}   
	
	@GetMapping("/getDataByKeyName2")
	public String getDataByKeyName2() {
		

		 DatastoreOptions options =
			        DatastoreOptions.newBuilder().setNamespace("EsempioNS").build();
		 
		 logger.info("getDataByKeyName2 - fase 1 ["+options.toString()+"]");
		 
		 
		 Datastore datastore = options.getService();
		 
		 logger.info("getDataByKeyName2 - fase 2 ["+datastore.toString()+"]");
		 
		 
		 
		 Key key = datastore.newKeyFactory().setKind("EsempioKind").newKey(Long.parseLong("5636645067948032"));

		 logger.info("getDataByKeyName2 - fase 3 ["+key.toString()+"]");
		 
		 return datastore.get(key).getValue("Prop1").get().toString();
		   
		
	}   
	
	
	@GetMapping("/getDataByFile")
	public String getDataByFile() {
		
		String string="";
		
		 try (InputStream input = WishController.class.getClassLoader().getResourceAsStream("application.properties")) {

	            Properties prop = new Properties();

	            // load a properties file
	            prop.load(input);

	            // get the property value and print it out
	            string = prop.getProperty("spring.datasource.url");
	            string += "    "+prop.getProperty("spring.datasource.username");
	            string += "    "+prop.getProperty("spring.datasource.password");

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		   
		 return string;
		
	}   
	
	@GetMapping("/getDatabaseConnection")
	public String getDatabaseConnection() {
		
		String string="";
		
		 try (InputStream input = WishController.class.getClassLoader().getResourceAsStream("application.properties")) {

	            Properties prop = new Properties();

	            // load a properties file
	            prop.load(input);

	            // get the property value and print it out
	            String kind  = prop.getProperty("datastore.kind");
	            String key = prop.getProperty("datastore.kind.key");
	            String url  = prop.getProperty("datastore.kind.db1.url");
	            String username = prop.getProperty("datastore.kind.db1.username");
	            String password = prop.getProperty("datastore.kind.db1.password");
	            
	            
	       	 Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
			 Key k = datastore.newKeyFactory().setKind(kind).newKey(key);
			 
			 string+=datastore.get(k).getValue(url).get().toString()+" -> "+datastore.get(k).getValue(username).get().toString()
					 +" -> "+datastore.get(k).getValue(password).get().toString();
			 
			 return string;

	        } catch (IOException ex) {
	            return ex.getMessage();
	        }
		   
		
		
	}   

}