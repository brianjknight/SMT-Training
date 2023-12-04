package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day4 {
	
	String[][] winNums = new String[221][10];
	String[][] myNums = new String[221][25];
	
	int[] cardCounts = new int[221];
	int[] cardPoints = new int[221];
	
	// convert input and create initial card count
	Day4() throws IOException {
		File input = new File("src/adventOfCode/resources/day-4-input.txt");
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			int cardNum = 1;
			while((line=reader.readLine()) != null) {
				String[] curWinNums = line.substring(10, line.indexOf('|')-1).trim().split("\\s+");
				String[] curMyNums = line.substring(line.indexOf('|')+2).trim().split("\\s+");
				
				winNums[cardNum] = curWinNums;
				myNums[cardNum] = curMyNums;
				
				cardNum++;
			}
		}
		
		// start with 1 of each card
		Arrays.fill(cardCounts, 1);
	}
	
	
	int calcultatePoints() {
		int total = 0;
		
		// for each set of my numbers
		for (int cardNum=1; cardNum<winNums.length; cardNum++) {
			String[] curWinNums = winNums[cardNum];
			String[] curMyNums = myNums[cardNum];
			
			// check if each number is a winning number keeping track of how many winners there are
			int numWinners = 0;
			for (String num : curMyNums) {
				if (containsNum(curWinNums, num) >= 0) {
					numWinners++;
				}
			}
			
			// increment number of card copies added 
			int curCardCount = cardCounts[cardNum];
			for (int i=1; i<=numWinners && i<221; i++) {
				int nextCardNum = cardNum + i;
				cardCounts[nextCardNum] = cardCounts[nextCardNum] + curCardCount;		
			}
			
			// calculate point total for game and add to running total
			int gamePoints = numWinners == 0 ? 0 : numWinners == 1 ? 1: (int) Math.pow(2, numWinners-1);
			cardPoints[cardNum] = gamePoints;
			total += gamePoints; 
		}
		
		return total;
	}
	
	int calculateNumberOfCards() {
		int numCards = 0;
		for (int i=1; i<cardCounts.length; i++) {
			numCards += cardCounts[i];
		}
		
		return numCards;
	}
	
	int containsNum(String[] input, String find) {
		for (int i=0; i<input.length; i++) {
			if (input[i].equals(find)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		Day4 d4 = new Day4();
		System.out.println("Point total = " + d4.calcultatePoints());
		System.out.println("Final number of cards = " + d4.calculateNumberOfCards());
	}
	
}
