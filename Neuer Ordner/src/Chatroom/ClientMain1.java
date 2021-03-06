
package Chatroom;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain1 {

	private boolean isRunning = true;

	public void createClient() {

		try {
			Socket socket = new Socket("127.0.0.1", 3445);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

			Scanner scanner = new Scanner(socket.getInputStream());
			System.out.println("write a Message:");

			writeMessage(printWriter);

			while (isRunning) {

				System.out.println(scanner.nextLine());
			}

			scanner.close();
			printWriter.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void writeMessage(PrintWriter printWriter) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Scanner keyboard = new Scanner(System.in);
				while (isRunning) {
					String text = keyboard.next();
					if (text.equalsIgnoreCase("Quit") || text.equalsIgnoreCase("quit") ) {
						isRunning = false;
					} else {

						printWriter.println(text);
						printWriter.flush();
					}
				}
				keyboard.close();
			}
		}).start();
	}

	public static void main(String[] args) {
		ClientMain1 client = new ClientMain1();
		client.createClient();
	}
}
