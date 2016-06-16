package com.egen.app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bson.Document;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

public class UserControllerTest {
	
	@BeforeClass
	public static void beforeClass() throws InterruptedException {
		new Bootstrap("testDb", "test");
		Thread.sleep(500);
		System.out.println("server started");
	}
	
	@AfterClass
	public static void afterClass() {
//		ImplUserService.getCollection().drop();
		Spark.stop();
		System.out.println("server stopped");
	}
	
	@Test
	public void Test() {
        
		TestResponse response = request("POST", "/api/users", BODY);
        assertEquals("User not created", 200, response.status);
        
        response = request("GET", "/api/users", null);
        assertEquals("No users found", 200, response.status);
        System.out.println(response.body);
        
        response = request("GET", "/api/users/5761e02115103ab12b7badb3", null);
        assertEquals("User not found", 200, response.status);
        
        response = request("PUT", "/api/users/5761e02115103ab12b7badb3", BODY_UPDATED);
        assertEquals("User updated", 200, response.status);
        
        response = request("DELETE", "/api/users/5761e02115103ab12b7badb3", null);
        assertEquals("User deleted", 200, response.status);
	}
	

	private TestResponse request(String method, String path, String body) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL("http://localhost:8002" + path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			if(body != null){
				connection.setDoOutput(true);
				connection.connect();
				final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(body);
                wr.close();
			}
			InputStream inputStream = null;
			
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            final String respBody = IOUtils.toString(inputStream);
            return new TestResponse(connection.getResponseCode(), respBody);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
			return null;
		}
		finally {
            if (connection != null) {
                connection.disconnect();
            }
		}
	}

	private static class TestResponse {
		public final String body;
		public final int status;

		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

		public Document json() {
			return Document.parse(body);
		}
	}
	
	private final String BODY = "{'firstName':'Dorris','lastName':'Keeling','email':'Darby_Leffler68@gmail.com','address':{'street':'193 Talon Valley','city':'South Tate furt','zip':'47069','state':'IA','country':'US'},'dateCreated':'2016-03-15T07:02:40.896Z','company':{'name':'Denesik Group','website':'http://jodie.org'},'profilePic':'http://lorempixel.com/640/480/people'}";
	private final String BODY_UPDATED = "{'firstName':'Samir','lastName':'Sharan','email':'Darby_Leffler68@gmail.com','address':{'street':'193 Talon Valley','city':'South Tate furt','zip':'47069','state':'IA','country':'US'},'dateCreated':'2016-03-15T07:02:40.896Z','company':{'name':'Denesik Group','website':'http://jodie.org'},'profilePic':'http://lorempixel.com/640/480/people'}";
}
