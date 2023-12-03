package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {
	// read each line of the file to convert to a 2D array of characters
	char[][] engine = new char[140][140];
	int numRows = engine.length;
	int numCols = engine[0].length;
	
	
	void convertEngine () throws IOException {
		File input = new File("/home/brian/project-workspace/brian.training/src/adventOfCode/resources/day-3-input.txt");
		try (BufferedReader reader = new BufferedReader (new FileReader(input))) {
			String line;
			int lineNumber = 0;
			while ((line=reader.readLine()) != null) {
				for (int i=0; i<140; i++) {
					engine[lineNumber][i] = line.charAt(i);
				}
				lineNumber++;
			}
		}
	}
	
	int findSumParts() {
		int sumOfParts = 0;
		// use a nested loop to iterate each character
		// for each line
		for (int row=0; row<numRows; row++) {
			// for each character
			for (int col=0; col<numCols; col++) {
				char curChar = engine[row][col];
				if (Character.isDigit(curChar)) {
					// find length of number
					int numLength = 1;
                    while (col + numLength < numCols && Character.isDigit(engine[row][col + numLength])) {
                        numLength++;
                    }
					
					// check adjacent elements for a symbol
					int minRow = row-1 < 0 ? 0 : row-1;
					int maxRow = row+1 > numRows-1 ? row : row+1;
					int minCol = col-1 < 0 ? 0 : col-1;
					int maxCol = col+numLength > numCols-1 ? col+numLength-1 : col+numLength;
					
					int adjSymbols = 0;
					for (int r=minRow; r<=maxRow; r++) {
						for (int c=minCol; c<=maxCol; c++) {
							char adjacent = engine[r][c];
							if (adjacent != '.' && !Character.isLetterOrDigit(adjacent)) { 
								adjSymbols++;
							}
						}
					}
					
					// if symbol is found add number to total 
					if (adjSymbols>0) {
						String curNum = new String(engine[row], col, numLength);
						sumOfParts += Integer.parseInt(curNum);
					} 
					// increment col number past current number
					col += numLength-1;
				}
			}
		}
		
		return sumOfParts;
	}
	
	int findSumGearRatios () {
		int sumGears = 0;
		Set<String> gears = new HashSet<>();
		
		// check for '*' symbol
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numCols; col++) {
				char cur = engine[row][col];
				
				// find adjacent numbers
				if (cur == '*') {
					int minRow = row-1 < 0 ? 0 : row-1;
					int maxRow = row+1 > numRows-1 ? row : row+1;
					int minCol = col-1 < 0 ? 0 : col-1;
					int maxCol = col+1 > numCols-1 ? col : col+1;
					
					List<String> numbers = new ArrayList<>();
					
					for (int r=minRow; r<=maxRow; r++) {
						for (int c=minCol; c<=maxCol; c++) {
							char next = engine[r][c];
							
							// get full number
							if (Character.isDigit(next)) {
								String curNum = ""+next;
								
								// look left
								int left = c-1;
								while (left >= 0 && Character.isDigit(engine[r][left])) {
									curNum = engine[r][left--] + curNum;
								}
								// look right
								int right = c+1;
								while (right < numRows && Character.isDigit(engine[r][right])) {
									curNum += engine[r][right++];
								}
								
								if (!numbers.contains(curNum)) {
									numbers.add(curNum);									
								}
							}
						}
					}
					
					// calculate gear ratio if only 2 adjacent gears
					if (numbers.size() == 2) {
						String fir = numbers.get(0);
						String sec = numbers.get(1);
						
						if (!gears.contains(fir + sec) && !gears.contains(sec + fir)) {
							gears.add(fir + sec);
							gears.add(sec + fir);
							sumGears += Integer.parseInt(fir) * Integer.parseInt(sec);
						}
					}
				}
			}
		}
		
		return sumGears;
	}
	
	public static void main(String[] args) throws IOException {
		Day3 d3 = new Day3();
		d3.convertEngine();
		System.out.println("Sum of part numbers = " + d3.findSumParts());
		System.out.println("Sum of gear ratios = " + d3.findSumGearRatios());
	}
}
