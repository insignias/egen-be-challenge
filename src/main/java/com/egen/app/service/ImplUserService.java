package com.egen.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import spark.Response;

public class ImplUserService implements UserService{
	
	final Logger log = LoggerFactory.getLogger(ImplUserService.class);
	
	private final MongoDatabase mongodb;
    private static MongoCollection<Document> collection;
	
	public ImplUserService(MongoDatabase mongodb, String collection) {
		this.mongodb = mongodb;
		ImplUserService.collection = mongodb.getCollection(collection);
	}
	

	public static MongoCollection<Document> getCollection() {
		return collection;
	}


	public List<Document> getAllUsers() {
		List<Document> documents = new ArrayList<>();
        MongoCursor<Document> mongoCursor = collection.find().iterator();
        log.info("Getting All Users");
        while (mongoCursor.hasNext()) {
            documents.add(mongoCursor.next());
        }
        System.out.println(documents);
        return documents;
	}

	public Document createUser(String req, Response res) {
		Document doc = Document.parse(req);
		doc.append("dateCreated", new Date());
		log.info("Inserting new user");
		collection.insertOne(doc);
		res.status(200);
		return doc;
	}

	public Document findUser(String id, Response res) {
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		Document document = collection.find(query).first();
		if(document!=null){
			res.status(200);
			log.info("User with id: " +id+ " found");
		}
		else{
			res.status(400);
			log.error("User with id: " + id+ "not found");
		}
		return document;
	}

	public Document updateUser(String id, String body, Response res) {
		Document doc = Document.parse(body);
		doc.replace("dateCreated", new Date());
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		Document document = collection.find(query).first();
		if(document!=null){
			collection.updateOne(document, new Document("$set", doc));
			res.status(200);
			log.info("User with id: "+ id + " updated successfully");
		}
		else{
			res.status(400);
			log.error("User with id: " + id+ "not found");
		}
		return document;
		
	}
	
	public Document removeUser(String id, Response res) {
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		Document document = collection.find(query).first();
		if(document!=null){
			collection.deleteOne(document);
			res.status(200);
			log.info("User with id: "+ id + " removed successfully");
		}
		else{
			res.status(400);
			log.error("User with id: " + id+ "not found");
		}
		return document;
	}

}
