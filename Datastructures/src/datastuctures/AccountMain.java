
package datastuctures;

import java.util.HashSet;
import java.util.Scanner;

public class AccountMain {
	
	public static void main(String[] args) {
		
		Account account1 = new Account("Me", 123);
		Account account2 = new Account("Him", 456);
		Account account3 = new Account("Her", 789);
		
		
		HashSet<Account> accounts = new HashSet<>();
		
		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Namen eingeben:");
		String name = input.nextLine();
		
		System.out.println("Nummer eingeben:");
		long number = input.nextLong();
		input.close();
		
		accounts.add(new Account(name, number));
		
		for(Account user : accounts) {
			System.out.println(String.format("%15s:\t%d", user.getName(), user.getNumber()));
		}
		
		System.out.println(accounts.size());
		
	}
}