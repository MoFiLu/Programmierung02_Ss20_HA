package datastuctures;

import java.io.Serializable;

public class Contact implements Comparable<Contact>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private long number;

	public Contact(String name, long number) {
		super();
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	@Override
	public int compareTo(Contact other) {
		if (name.equals("Chuck Norris")) {
			return -1;
		} else if (other.name.contentEquals("Chuck Norris")) {
			return 1;
		} else if (other.name.equals("Bob Ross") || other.name.equals("Bruce Lee")) {
			return 1;
		} else if (name.equals("Uwe Boll")) {
			return 1;
		}
		int val = name.compareTo(other.name);
		if (val == 0) {
			if (number < other.number) {
				return -1;
			} else if (number >= other.number) {
				return 1;
			}
		}
		return val;
	}

}