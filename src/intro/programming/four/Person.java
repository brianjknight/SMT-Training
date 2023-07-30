package intro.programming.four;

import java.util.Objects;

/****************************************************************************
 * <b>Title:</b> People.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Homework exercise to create a list of people.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 17, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class Person {
	private int id;
	private String firstName;
	private String lastName;
	
	/**
	 * Constructor instantiates a new person. 
	 * @param firstName
	 * @param lastName
	 */
	public Person(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return id + ". " + firstName + " " + lastName;
	}
}
