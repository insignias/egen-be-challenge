package com.egen.app;

import static spark.Spark.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egen.app.controller.UserController;
import com.egen.app.service.ImplUserService;
import com.mongodb.MongoClient;

public class Bootstrap {
	
	final Logger log = LoggerFactory.getLogger(Bootstrap.class);
	
	public Bootstrap(String mongodb, String collection) {
		port(8002);
		log.info("Starting server.....");
		MongoClient mongoClient = new MongoClient( "localhost");
		new UserController(new ImplUserService(mongoClient.getDatabase(mongodb), collection));
	}
	

	public static void main(String[] args) {
		new Bootstrap("userDb", "user");
		
	}

}
