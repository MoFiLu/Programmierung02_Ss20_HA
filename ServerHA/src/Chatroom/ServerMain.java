
package Chatroom;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerMain {
	private BlockingQueue messages = new ArrayBlockingQueue(1024);
	private boolean isRunning = true;
	ArrayList<PrintWriter> writersList = new ArrayList<>();
	ArrayList<ConnectionThread> connectionThread = new ArrayList<>();

	public void createServer() {

		int clientSize = 10;

		try {

			ServerSocket serverSocket = new ServerSocket(3445, clientSize);
			for (int i = 0; i < clientSize; i++) {
				connectionThread.add(new ConnectionThread(messages, serverSocket, writersList));
				connectionThread.get(i).start();
			}

			WriterThread wt = new WriterThread(messages, writersList);
			wt.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerMain server = new ServerMain();
		server.createServer();
	}

}