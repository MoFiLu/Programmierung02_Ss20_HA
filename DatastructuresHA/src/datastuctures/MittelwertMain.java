package datastuctures;

import java.util.ArrayList;
import java.util.Scanner;

public class MittelwertMain {

	public static void main(String[] args) {
		ArrayList<Double> werte = new ArrayList<Double>();

		Scanner input = new Scanner(System.in);

		double wert = 0;

		System.out.println("Zahlen eingeben (mit 'quit' abbrechen):");

		do {
			String text = input.next();
			if(text.equals("quit")) {
				input.close();
				System.out.println((mittelwertRechner(werte)));
				System.exit(0);
			}
			wert = Double.parseDouble(text);
			werte.add(wert);
		} while (!input.equals("quit"));
		
	}

	private static double mittelwertRechner(ArrayList<Double> list) {
		double sum = 0;
		if (!list.isEmpty()) {
			for (Double number : list) {
				sum += number;
			}
			return sum / list.size();
		}
		return sum;
	}

}