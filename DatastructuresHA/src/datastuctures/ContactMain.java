package datastuctures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class ContactMain {

	public static void main(String[] args) {

		LinkedList<Contact> contacts = new LinkedList<Contact>();
		contacts.add(new Contact("John Wick", 456672346L));
		contacts.add(new Contact("Alice", 6805918L));
		contacts.add(new Contact("Big Smoke", 23236977L));
		contacts.add(new Contact("Bob Ross", 12345L));
		contacts.add(new Contact("Kirsten", 312312L));
		contacts.add(new Contact("Daniel", 198345L));
		contacts.add(new Contact("Zorro", 319876L));
		contacts.add(new Contact("Uwe Boll", 917283));
		contacts.add(new Contact("Chuck Norris", 0));
		contacts.add(new Contact("Bruce Lee", 345678));

		Collections.sort(contacts);

//		for(Contact person : contacts) {
//			System.out.println(String.format("%10s:\t%d", person.getName(), person.getNumber()));
//		}

		
		
		
		// CREATE NEW FILE IF FILE DOES NOT EXIST (contacts.txt)
		File file = new File("05Vorlesung\\datastructures\\contacts.txt");
		if (file.exists()) {
			System.out.println("Die Datei existiert\n");
		} else {
			System.out.println("Die Datei wird angelegt\n");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		// WRITE CONTACT OBJECT TO contacts.txt USING ObjectOutputStream
		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream)) {
			oos.writeObject(new Contact("John Wick", 2454369L));
			oos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// READ CONTACT OBJECT FROM contacts.txt USING ObjectInputStream
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fileInputStream);

			try {
				Contact user = (Contact) ois.readObject();

				System.out.println(String.format("%20s:\t%d", user.getName(), user.getNumber()));
				ois.close();
		
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// CREATE file2 (coconut.txt) IF IT DOES NOT EXIST
		File file2 = new File("05Vorlesung\\datastructures\\coconut.txt");
		if (file2.exists()) {
			System.out.println("\ncoconut.txt existiert :D");
		} else {
			System.out.println("\nCoconut wird angelegt!");
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		// WRITE STRING TO coconut.txt USING PrintWriter
		try {
			FileWriter fileWriter = new FileWriter(file2);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			printWriter.write("Put the lime in the coconut and drink it all up!");
			printWriter.close();
			printWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// READ coconut.txt USING Scanner
		try {
			FileReader fileReader = new FileReader(file2);
			Scanner scanner = new Scanner(fileReader);
			
			while(scanner.hasNextLine()) {
				System.out.println("\n" + scanner.nextLine());
			}
			
			scanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}