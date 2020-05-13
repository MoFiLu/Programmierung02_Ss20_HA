
package Chatroom;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMain1 {
	private BlockingQueue messages = new LinkedBlockingQueue<String>();
	private boolean isRunning = true;
	ArrayList<PrintWriter> writersList = new ArrayList<>();
	ArrayList<ReaderThread> readerThreads = new ArrayList<>();
	ArrayList<ConnectionThread> connectionThread = new ArrayList<>();
	private static final int ClientSize = 10;
	public void createServer() {
		try {
			
			WriterThread writerThread = new WriterThread(messages, writersList);
			writerThread.start();
			ServerSocket serverSocket = new ServerSocket(3445, ClientSize);
			
			
			for (int i = 0; i < ClientSize; i++) {
				ConnectionThread connectionThread = new ConnectionThread(messages ,serverSocket, writersList, readerThreads);
				connectionThread.start();
			}
			Scanner scanner = new Scanner(System.in);
			if (scanner.nextLine().contentEquals("Quit")) {
				writerThread.quit();
				for(ReaderThread readerThread : readerThreads) {
					readerThread.quit();
					
				}
				serverSocket.close();
				scanner.close();
				
			} 
			
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerMain1 server = new ServerMain1();
		server.createServer();
	}

}