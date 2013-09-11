package fr.gpereira.bookstore.client.rest;

import org.fusesource.restygwt.client.RestService;

public interface ResourceAsync extends RestService {
	
	// Redirected by proxy
	public static final String PROXY_BASE_URL = "/api/v1.0/";
	public static final String DIRECT_BASE_URL = "http://localhost:9999/bookstore-rest/api/v1.0/";
	//public static final String DIRECT_BASE_URL = "http://to-upload-aws/api/v1.0/";
	
	
}
