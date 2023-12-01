package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdventOfCode {

	public int day1() throws FileNotFoundException, IOException {
		int sum = 0;
		
		// read each line of the input
		File input = new File("/home/brian/project-workspace/brian.training/src/adventOfCode/resources/calibration-values.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			
			while ((line=reader.readLine()) != null) {
				String value = "";

				// find the first and last digits
				for (int i=0; i<line.length(); i++) {
					// check for number as word
					int word = findNumAsWord(line.substring(i));
					if (word > 0) {
						value += word;
						break;
					}
					
					if (Character.isDigit(line.charAt(i))) {
						value += line.charAt(i);
						break;
					}
				}
				
				for (int i=line.length()-1; i>=0; i--) {
					int word = findNumAsWord(line.substring(i));
					if (word > 0) {
						value += word;
						break;
					}
					
					if (Character.isDigit(line.charAt(i))) {
						value += line.charAt(i);
						break;
					}
				}
				
				// add to the running total
				sum += Integer.valueOf(value);
			}
		
			// return answer
			return sum;
		}
	}
	
	// check for number as a word
	private int findNumAsWord(String input) {
		Map<String, Integer> nums = new HashMap<>();
		nums.put("one",1);
		nums.put("two",2);
		nums.put("three",3);
		nums.put("four",4);
		nums.put("five",5);
		nums.put("six",6);
		nums.put("seven",7);
		nums.put("eight",8);
		nums.put("nine",9);
		
		for (Map.Entry<String, Integer> entry : nums.entrySet()) {
			if (input.startsWith(entry.getKey())) {
				return entry.getValue();
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		AdventOfCode avc = new AdventOfCode();
		System.out.println(avc.day1());
	}
}
