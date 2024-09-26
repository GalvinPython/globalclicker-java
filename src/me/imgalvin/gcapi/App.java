package me.imgalvin.gcapi;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class App {
	public static int clicks = 0;

	public static void main(String[] args) throws Exception {
		System.out.println("Hello, World!");

		// Check if data directory exists
		Fs.CheckDataDirExists.main(null);

		// Create a timer to save the clicks every second (for redundancy)
		new java.util.Timer().schedule( 
			new java.util.TimerTask() {
				@Override
				public void run() {
					Fs.WriteFile.main(null);
				}
			}, 0, 1000);

		// Create a new thread for the HTTP Server
		Thread serverThread = new Thread(() -> {
			try {
				HttpServer httpServer = HttpServer.create(new InetSocketAddress(18432), 0);

				httpServer.createContext("/", new ApiHttpHandler.HttpHandlerRouteRoot());
				httpServer.createContext("/clicks", new ApiHttpHandler.HttpHandlerRouteClicks());
				httpServer.createContext("/clicked", new ApiHttpHandler.HttpHandlerRouteClicked());

				httpServer.setExecutor(null);
				httpServer.start();

				System.out.println("Server started on port 18432");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		serverThread.start();
	}
}
