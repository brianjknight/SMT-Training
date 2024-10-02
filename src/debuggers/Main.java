package debuggers;

/****************************************************************************
 * <b>Title:</b> Main.java <br>
 * <b>Project:</b> brian.training <br>
 * <b>Description:</b> SMT Dev Training Debuggers <br>
 * <b>Copyright:</b> Copyright (c) 2022 <br>
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Brian Knight
 * @version 1.x
 * @since Sep 30, 2024 <b>updates:</b>
 * 
 ****************************************************************************/

public class Main {
	public static void main(String[] args) {
		Counter counter = new Counter();
		
		counter.count();
		
		System.out.println("count: " + counter.getCount());
		System.out.println("sum: " + counter.getSum());
		System.out.println("average: " + counter.getAverage());
	}
}


