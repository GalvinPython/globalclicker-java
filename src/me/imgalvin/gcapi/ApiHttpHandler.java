package me.imgalvin.gcapi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ApiHttpHandler {
	// Create a thread pool for the HTTP Server
	private static final ExecutorService executor = Executors.newCachedThreadPool();

	// Route: /
	static class HttpHandlerRouteRoot implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			executor.submit(() -> {
				try {
					String response = "Hello, World!";
					exchange.sendResponseHeaders(200, response.length());
					OutputStream os = exchange.getResponseBody();
					os.write(response.getBytes());
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

	// Route: /clicks
	static class HttpHandlerRouteClicks implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			executor.submit(() -> {
				try {
					String response = "{\"success\": true, \"clicks\": " + App.clicks + "}";
					exchange.getResponseHeaders().set("Content-Type", "application/json");
					exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
					exchange.sendResponseHeaders(200, response.length());
					OutputStream os = exchange.getResponseBody();
					os.write(response.getBytes());
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

	// Route: /clicked
	static class HttpHandlerRouteClicked implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			executor.submit(() -> {
				try {
					String response = "Hi!";
					++App.clicks;
					exchange.getResponseHeaders().set("Content-Type", "application/json");
					exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
					exchange.sendResponseHeaders(200, response.length());
					OutputStream os = exchange.getResponseBody();
					os.write(response.getBytes());
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
