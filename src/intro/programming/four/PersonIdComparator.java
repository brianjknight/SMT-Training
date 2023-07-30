package intro.programming.four;

import java.util.Comparator;

/****************************************************************************
 * <b>Title:</b> PersonComparator.java
 * <b>Project:</b> brian.training
 * <b>Description:</b> Class to compare Persons by id.
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 3.x
 * @since Jul 17, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class PersonIdComparator implements Comparator<Person>{
	/**
	 * Customer comparator which compares a Person based on their id attribute.
	 */
	@Override
	public int compare(Person p1, Person p2) {
		Integer id1 = p1.getId();
		Integer id2 = p2.getId();
		
		return id1.compareTo(id2);
	}

}
