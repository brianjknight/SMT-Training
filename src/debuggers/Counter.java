package debuggers;

/****************************************************************************
 * <b>Title:</b> Counter.java <br>
 * <b>Project:</b> brian.training <br>
 * <b>Description:</b> CHANGE ME! <br>
 * <b>Copyright:</b> Copyright (c) 2022 <br>
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 1.x
 * @since Sep 30, 2024 <b>updates:</b>
 * 
 ****************************************************************************/

public class Counter {
	
	private int count;
	private int sum;
	private double average;
		
	public void count() {
		for (int i=1; i<=1000; i++) {
			count += 1;
			sum += i;
			average = sum/(double) count;
		}
	}

	public int getCount() {
		return count;
	}

	public int getSum() {
		return sum;
	}

	public double getAverage() {
		return average;
	}
	
}


