package client;

import java.util.Map;

import org.springframework.http.HttpMethod;

import exceptions.CoreException;

public interface IHttpClient {
	String request(final String url,final HttpMethod method,Map<String,String> headers ,
            Map<String,String> queryParams,final String jsonString) throws CoreException;
}
