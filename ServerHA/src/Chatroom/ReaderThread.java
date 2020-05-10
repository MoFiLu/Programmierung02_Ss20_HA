package Chatroom;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread{
	private Scanner scanner;
	private BlockingQueue<String> messages;
	private boolean isRunning = true;
	
	
	public ReaderThread(Scanner scanner, BlockingQueue<String> messages) {
		super();
		this.scanner = scanner;
		this.messages = messages;
	}
	
	@Override
	public void run() {
		while(isRunning) {
			try {
				String message = scanner.nextLine();
				System.out.println(message);
				messages.put(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void quit() {
		isRunning = false;
	}
}