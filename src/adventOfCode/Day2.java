package adventOfCode;

import java.io.*;
import java.util.Arrays;

public class Day2 {
	int[] rgbLimits = {12,13,14};
	int sumGameNumbers = 0;
	int sumOfPowers = 0;
	
	void evaluateGames() throws IOException {
		// read each line in the file
		File input = new File("/home/brian/project-workspace/brian.training/src/adventOfCode/resources/day-2-input.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			
			while ((line=reader.readLine()) != null) {
				// for each game record the game number 
				int gameNumber = Integer.parseInt(line.substring(5,line.indexOf(':')));
				boolean isPossible = true;
				
				// keep track of max color amounts
				int[] maxGameCount = new int [3];
				// split into rounds
				String[] rounds = line.substring(line.indexOf(':')+2).split(";");
				
				// for each round find the RGB values
				for (String round : rounds) {
					String[] colors = round.split(",");
					int[] roundCount = new int[3];
					
					for (String color : colors) { 
						String trimmed = color.trim();
						int amount = Integer.parseInt(trimmed.substring(0,trimmed.indexOf(' ')));
						String curColor = trimmed.substring(trimmed.indexOf(' ')+1);
						
						int colorIndex = getColorIndex(curColor);
						
						roundCount[colorIndex] = amount;
						if (amount > maxGameCount[colorIndex]) maxGameCount[colorIndex] = amount;
						
						// if any color in the round is over the amount, game number cannot be included
						if (roundCount[0] > rgbLimits[0] || roundCount[1] > rgbLimits[1] || roundCount[2] > rgbLimits[2])
							isPossible = false;
					}
				}
	
				// if <= allowed cubes add game number to total
				if (isPossible) sumGameNumbers += gameNumber; 
				// multiply max colors for game
				sumOfPowers += maxGameCount[0] * maxGameCount[1] * maxGameCount[2];
			}
		}
	}
	
	int getColorIndex(String color) {
        switch (color) {
	        case "red": 
	            return 0;
	        case "green":
	            return 1;
	        case "blue":
	            return 2;
	        default:
	            return -1;
        }
	}
	
	public static void main(String[] args) throws IOException {
		Day2 d2 = new Day2();
		d2.evaluateGames();
		System.out.println(d2.sumGameNumbers);
		System.out.println(d2.sumOfPowers);
	}
	
}
