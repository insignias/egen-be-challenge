package com.egen.app.controller;

import com.egen.app.service.UserService;

import static spark.Spark.*;

public class UserController {
	
	private static final String API_CONTEXT = "/api";
	 
    private final UserService userService;
 
    public UserController(UserService userService) {
        this.userService = userService;
        setupEndpoints();
    }
    
    private void setupEndpoints() {
        post(API_CONTEXT + "/users", "application/json", (request, response) -> {
            userService.createUser(request.body(), response);
            return response;
        }); 
 
        get(API_CONTEXT + "/users/:id", "application/json", (request, response)
 
                -> userService.findUser(request.params(":id"), response));
 
        get(API_CONTEXT + "/users", "application/json", (request, response)
 
                -> userService.getAllUsers());
 
        put(API_CONTEXT + "/users/:id", "application/json", (request, response)
 
                -> userService.updateUser(request.params(":id"), request.body(), response));
        
        delete(API_CONTEXT + "/users/:id", "application/json", (request, response)
        		 
                -> userService.removeUser(request.params(":id"), response));
        
    }

}
