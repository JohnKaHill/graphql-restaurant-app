package de.pwc.digispace.javadevcourse.backend.server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRouter {
	
	
	
	public static final Logger LOGGER = LoggerFactory.getLogger(HttpRouter.class);
	
	Socket connectedClient = null;
	
	// character output for texts
	PrintWriter out = null;
	// binary output for files
	BufferedOutputStream dataOut = null;
	
	public HttpRouter( Socket client) {
		this.connectedClient = client;
	}
	
	public void routeHttpRequest( String httpMethod, String httpRequest, String httpBody ) throws Exception {
		switch (httpMethod) {
		case "GET":
			String responseString = "{ \"msg\":\"Hello, World!\" }";
			LOGGER.debug("{} Method requested. URL: {}.", httpMethod, httpRequest);
			routeGetRequest(httpRequest, responseString);
			break;
			
		case "POST":
			LOGGER.debug("{} Method requested. URL: {}", httpMethod, httpRequest);
			routePostRequest(httpRequest, httpBody);
			break;
		
		default:
			LOGGER.debug("Unknown HTTP Method: {} requested {}.", httpMethod, httpRequest);
			break;
		}
	}
	
	public void routeGetRequest(String httpRequest, String httpBody) throws Exception {
		sendResponse(200, httpBody, false);
	}
	
	public void routePostRequest(String httpRequest, String httpBody) throws Exception {
		String jsonData = new GraphQLEndpoint().executeQuery(httpBody);
		LOGGER.debug("jsonData: {}", jsonData.toString());
		sendResponse(200, jsonData.toString(), false);
	}
	
	public void sendResponse(int statusCode, String responseString, boolean isFile) throws Exception {
		
		out = new PrintWriter( connectedClient.getOutputStream());
//		dataOut = new BufferedOutputStream(connectedClient.getOutputStream());
		if (responseString == null) {
			responseString = "Hello and welcome!";
		}
		String statusLine = null;
		String serverDetails = "Server: Java HTTP Restaurant Server\r\n";
		String date = "Date: " + LocalDateTime.now().toString() + "\r\n";
		String contentLengthLine = null;
		String filename = null;
		String contentTypeLine = "Content-Type: application/json" + "\r\n";
		
		FileInputStream fInputStream = null;
		
		if (statusCode == 200) {
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		} else {
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
		}
		
		if (isFile) {
			filename = responseString;
			fInputStream = new FileInputStream(filename);
			contentLengthLine = "Content-Length: " + Integer.toString(fInputStream.available()) + "\r\n";
			if (!filename.endsWith(".htm") && !filename.endsWith("html")) {
				contentTypeLine = "Content-Length: \r\n";
			}
		} else {
				contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
		}
		
		out.print(statusLine);
		out.print(serverDetails);
		out.print(date);
		out.print(contentTypeLine);
		out.print(contentLengthLine);
		out.print("Connection: close\r\n");
		out.print("\r\n");
		
		if (isFile) {
//			sendFile(fInputStream, out);
		} else {
			out.print(responseString);
		}
		LOGGER.debug("Closing Socket");
		out.flush();
	}
	
	public void sendFile( FileInputStream fileInputStream, DataOutputStream outputStream) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = fileInputStream.read(buffer)) != -1 ) {
			outputStream.write(buffer, 0, bytesRead);
		}
		fileInputStream.close();
	}
}
