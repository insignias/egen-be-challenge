package com.egen.app.service;

import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;

import spark.Response;


public interface UserService {
	
	public List<Document> getAllUsers();
	public Document createUser(String request, Response res);
	public Document findUser(String id, Response res) throws JsonProcessingException;
	public Document updateUser(String id, String body, Response res);
	public Document removeUser(String id, Response res);

}
