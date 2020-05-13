package Chatroom;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class WriterThread extends Thread {

	private BlockingQueue<String> messages;
	ArrayList<PrintWriter> writersList;
	private boolean isRunning = true;

	public WriterThread(BlockingQueue<String> messages, ArrayList<PrintWriter> writersList) {
		super();
		this.messages = messages;
		this.writersList = writersList;
	}

	@Override
	public void run() {
		while (isRunning) {

			try {

				String message = messages.take();
				synchronized (writersList) {
					for (PrintWriter printWriter : writersList) {
						try {
							printWriter.println(message);
							printWriter.flush();
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public void quit() {
		interrupt();
		isRunning = false;
	}

}
