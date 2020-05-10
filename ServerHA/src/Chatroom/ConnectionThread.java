package Chatroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;



public class ConnectionThread extends Thread {

	private BlockingQueue<String> messages;
	private ServerSocket serverSocket;
	ArrayList<PrintWriter> writersList = new ArrayList<>();
	private boolean isRunning = true;

	public ConnectionThread(BlockingQueue<String> messages, ServerSocket serverSocket,
			ArrayList<PrintWriter> writersList) {
		super();
		this.messages = messages;
		this.serverSocket = serverSocket;
		this.writersList = writersList;
	}

	@Override
	public void run() {
		while(isRunning) {
				try {
				
							Socket socket = serverSocket.accept();
							System.out.println(socket + " connection accepted");
				
							Scanner scanner = new Scanner(socket.getInputStream());
				
							ReaderThread readerThread = new ReaderThread(scanner, messages);
							readerThread.start();
				
							PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
							writersList.add(printWriter);
				
						} catch (IOException e) {
							e.printStackTrace();
				}
		}
	}

}
