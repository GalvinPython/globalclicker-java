package me.imgalvin.gcapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Fs {

	// Check if data directory exists
	static class CheckDataDirExists implements Runnable {
		@Override
		public void run() {
			File dataDir = new File("data");
			if (dataDir.exists()) {
				System.out.println("Data directory exists");
			} else {
				System.out.println("Data directory does not exist");
				try {
					dataDir.mkdir();
					System.out.println("Data directory created");
				} catch (Exception e) {
					System.out.println("Error creating data directory");
					e.printStackTrace();
				}
			}
			new Thread(new CheckDataFileExists()).start();
		}

		public static void main(String[] args) {
			new Thread(new CheckDataDirExists()).start();
		}
	}

	// Check if data file exists
	static class CheckDataFileExists implements Runnable {
		@Override
		public void run() {
			File dataFile = new File("data/clicks.txt");
			if (dataFile.exists()) {
				System.out.println("Data file exists");
			} else {
				System.out.println("Data file does not exist");
				try {
					dataFile.createNewFile();
					System.out.println("Data file created");
				} catch (IOException e) {
					System.out.println("Error creating data file");
					e.printStackTrace();
				}
				new Thread(new WriteFile()).start();
			}
			new Thread(new ReadFile()).start();
		}
	}

	// Read data file
	static class ReadFile implements Runnable {
		@Override
		public void run() {
			File dataFile = new File("data/clicks.txt");
			try {
				try (Scanner scanner = new Scanner(dataFile)) {
					App.clicks = Integer.parseInt(scanner.nextLine().trim());
				}
			} catch (FileNotFoundException e) {
				System.out.println("Error reading data file");
				e.printStackTrace();
				App.clicks = 0;
			}
		}
	}

	// Write data file
	static class WriteFile implements Runnable {
		@Override
		public void run() {
			File dataFile = new File("data/clicks.txt");
			try {
				FileWriter fileWriter = new FileWriter(dataFile);
				System.out.println(App.clicks);
				String clicksString = Integer.toString(App.clicks);
				fileWriter.write(clicksString);
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error writing data file");
				e.printStackTrace();
			}
		}

		public static void main(String[] args) {
			new Thread(new WriteFile()).start();
		}
	}
}
