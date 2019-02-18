package de.pwc.digispace.javadevcourse.backend.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import org.h2.tools.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestaurantApplicationHttpServer extends Thread{

	public static final Logger LOGGER = LoggerFactory.getLogger(RestaurantApplicationHttpServer.class);
	
	Socket connectedClient = null;
	BufferedReader in = null;
	// character output for text

	public RestaurantApplicationHttpServer(Socket client) {
		this.connectedClient = client;
	}
	
	@Override
	public void run() {
		try {
			
			LOGGER.info("j: Client {}:{} is connected.", connectedClient.getInetAddress(), connectedClient.getPort());
			in = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
			
			String requestString = in.readLine();
			
			StringTokenizer tokenizer = new StringTokenizer(requestString);
			String httpMethod = tokenizer.nextToken();
			String httpQueryString = tokenizer.nextToken();
			String contentType = "";
			int contentLength = 0;
			
			//Read HttpHeader
			while( in.ready() && !(requestString = in.readLine()).isEmpty() ) {
				if ( requestString.contains("Content-Type" )) {
					contentType = requestString.replace("Content-Type: ", "");
				} else if ( requestString.contains("content-length" )) {
					contentLength = Integer.parseInt(requestString.replace("content-length: ", ""));
				}
			}

			String httpBody = getHttpBody( contentLength );

			HttpRouter httpRouter = new HttpRouter(connectedClient);
			
			httpRouter.routeHttpRequest(httpMethod, httpQueryString, httpBody.toString());				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getHttpBody( int contentLength ) {
		//Read HttpBody
		StringBuilder httpBody = new StringBuilder();
		try {
			while ( in.ready() || httpBody.toString().getBytes("UTF-8").length < contentLength ) {
				httpBody.append((char) in.read());
			}
			return httpBody.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
				
		final int PORT_NUMBER = 4000;

		Server dbServer = Server.createWebServer().start();
		System.out.println("DB_URL: " + dbServer.getURL());
		
		try (ServerSocket server = new ServerSocket(PORT_NUMBER, 10, InetAddress.getByName("127.0.0.1"))) {
			LOGGER.info("TCP Server waiting for client on port {}", PORT_NUMBER);
			
			boolean runServer = true;
			
			while(runServer) {

				Socket client = server.accept();
				(new RestaurantApplicationHttpServer(client)).start();
			
				String line = "";
				if( line.equals("QUIT") ) {
					LOGGER.info("Shutting down server.");
					runServer = false;
				}
				
			}
			LOGGER.info("reached end of while-loop!");
			
		}

		dbServer.stop();
		
	}
	
}
