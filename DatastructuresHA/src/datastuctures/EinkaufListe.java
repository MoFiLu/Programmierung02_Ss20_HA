package datastuctures;

import java.util.HashMap;
import java.util.Set;

public class EinkaufListe {

	public static void main(String[] args) {

		// key = Artikel, value = Menge

		HashMap<String, String> liste = new HashMap<>();
		liste.put("Milch", "3");
		liste.put("Eier", "2");
		liste.put("schoko", "1");
		liste.put("Bier", "7");

		Set<String> itemname = liste.keySet();
		for (String name : itemname) {
			System.out.println(String.format("%15s:\t%5s", name, liste.get(name)));
		}

	}

}