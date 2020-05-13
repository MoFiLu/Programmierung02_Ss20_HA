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
	private ArrayList<PrintWriter> writersList;
	private ArrayList<ReaderThread> readerThreads;


	@Override
	public void run() {
		
			try {

				Socket socket = serverSocket.accept();

				Scanner scanner = new Scanner(socket.getInputStream());

				ReaderThread readerThread = new ReaderThread(scanner, messages);
				synchronized (readerThreads) {
					readerThreads.add(readerThread);
				}
				readerThread.start();


				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				synchronized (writersList) {
					writersList.add(printWriter);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
	}


	public ConnectionThread(BlockingQueue<String> messages, ServerSocket serverSocket,
			ArrayList<PrintWriter> writersList, ArrayList<ReaderThread> readerThreads) {
		super();
		this.messages = messages;
		this.serverSocket = serverSocket;
		this.writersList = writersList;
		this.readerThreads = readerThreads;
	}




}
