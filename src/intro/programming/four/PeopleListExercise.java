package intro.programming.four;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/****************************************************************************
 * <b>Title:</b> PeopleList.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Intro to Progarmming III working with collections of people.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 17, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class PeopleListExercise {
	public static void main(String[] args) {
		
		String[] characters = {
				"Peter Venkman", "Ray Stantz", "Egon Spengler", "Winston Zeddemore", "Dana Barrett", "Louis Tully", "Janine Melnitz"
		};
		
		/**
		 * Convert String[] to list String without using a loop.
		 */
		List<String> charactersList = Arrays.asList(characters); 
		
		/**
		 * Create and populate a list of Persons starting with id 1.		
		 */
		List<Person> persons = new ArrayList<>();
		int id = 1;
		for (String s : charactersList) {
			String[] split = s.split(" ");
			
			Person p = new Person(id++, split[0], split[1]);
			
			persons.add(p);
		}
		
		
		/**
		 * Prints the initial list of persons.
		 */
		PeopleListExercise.printPersons(persons);
		System.out.println("#".repeat(50));
		
		
		/**
		 * Shuffle the list of persons and print.
		 */
		Collections.shuffle(persons);
		PeopleListExercise.printPersons(persons);
		System.out.println("#".repeat(50));
		
		
		/**
		 * Sort the list of persons and print again.
		 */
		Collections.sort(persons, new PersonIdComparator());
		PeopleListExercise.printPersons(persons);		
	}
	
	/**
	 * Static helper method to print the list of persons.
	 * @param list
	 */
	private static void printPersons(List<Person> list) {
		for (Person p : list) {
			System.out.println(p.toString());
		}
	}
 }
