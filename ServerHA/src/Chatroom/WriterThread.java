package Chatroom;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class WriterThread extends Thread{
	
	private BlockingQueue<String> messages;
	ArrayList<PrintWriter> writersList = new ArrayList<>();
	private boolean isRunning = true;

	public WriterThread(BlockingQueue<String> messages, ArrayList<PrintWriter> writersList) {
		super();
		this.messages = messages;
		this.writersList = writersList;
	}

	@Override
	public void run() {
		while(isRunning) {
			if(!messages.isEmpty()) {
				for (PrintWriter printWriter : writersList) {
					printWriter.println("Hallo from the Server-side");
					printWriter.flush();
				}
				messages.remove();
			}
		}
	}
}
