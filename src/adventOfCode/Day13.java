package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {

	List<char[][]> allPatterns = new ArrayList<>();
	
	// convert the input of patterns to a list of 2D array
	Day13(String path) throws IOException{
		File input = new File(path);
		
		// read lines until an empty line is found
		try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			// store each string in a list
			List<String> patternRows = new ArrayList<>();

			while ((line=reader.readLine())!=null) {
				if (line.isBlank()) {
					// make 2D array of char[listlength][string length]
					char[][] pattern = new char[patternRows.size()][patternRows.get(0).length()];
					
					for (int row=0; row< patternRows.size(); row++) {
						String curRow = patternRows.get(row);
						
						for (int col=0; col<curRow.length(); col++) {
							pattern[row][col] = curRow.charAt(col);
						}
					}
					
					// store in List of 2D arrays
					allPatterns.add(pattern);
					patternRows = new ArrayList<>();
				}
				else {
					patternRows.add(line);
				}
			}
		}
	}
	
	
	
	
	// find matching rows and cols to later determine if it is vertical or horizontal
	List<List<int[]>> findvertHorNeighborMatches(char[][] pattern) {
		List<List<int[]>> vertHorNeighborMatches = new ArrayList<>();
		
//		System.out.println("findVertHorNeigh:");
//		System.out.println("patter:");
//		for (char[] p : pattern) {
//			System.out.println(Arrays.toString(p));
//		}
		
		// vertical check
		// need to keep a list of each match found
		List<int[]> colMatches = new ArrayList<>();
		// iterate columns comparing one to the next
		for (int col=0; col<pattern[0].length-1; col++) {
			// convert each to a string to see if they are equal
			String firstCol = "";
			String secondCol = "";
			for (int row=0; row<pattern.length; row++) {
				firstCol += pattern[row][col];
				secondCol += pattern[row][col+1];
			}
//			System.out.println("col iter: " + col + "," + (col+1));
//			System.out.println("first: " + firstCol);
//			System.out.println("secon: " + secondCol);
//			System.out.println("equal: " + firstCol.equals(secondCol));
//			System.out.println();
			
			if (firstCol.equals(secondCol)) {
				colMatches.add(new int[] {col,col + 1});
			}
			
		}
		vertHorNeighborMatches.add(colMatches);
		
		// vertical line is middle of matched pairs ???
		List<int[]> rowMatches = new ArrayList<>();
		for(int row=0; row<pattern.length-1; row++) {
			String firstRow = new String(pattern[row]);
			String secondRow = new String(pattern[row+1]);
			if (firstRow.equals(secondRow)) {
				rowMatches.add(new int[] {row,row+1});
			}
		}
		vertHorNeighborMatches.add(rowMatches);
		
		return vertHorNeighborMatches;
	}
	
	// determine vertical or horizontal mirror given matches lists
	boolean isHorizontalMatch(List<List<int[]>> neighborMatches, char[][] pattern) {
		// start from the middle most row matches
		List<int[]> horzMatches = neighborMatches.get(1);
		if (horzMatches.size() == 0) {
			return false;
		}
		
		int[] middleNeighbors = horzMatches.get(horzMatches.size()/2);
		int mirrorRow = middleNeighbors[0];
		
		int topInd = mirrorRow;
		int bottomInd = mirrorRow + 1;
		
		
		while (topInd >= 0 && bottomInd < pattern.length) {
			String top = new String(pattern[topInd]);
			String bottom = new String(pattern[bottomInd]);
			
			if (!top.equals(bottom)) {
				return false;
			}
			topInd--;
			bottomInd++;
		}
		
		return true;
	}
	

	int findNumberForPattern(char[][] pattern) {
		int number = 0;
		
		List<List<int[]>> neighborMatches = findvertHorNeighborMatches(pattern);
		
		boolean isHorizontal = isHorizontalMatch(neighborMatches, pattern);
		System.out.println("findNumForPat isHorizontal: " + isHorizontal);
		
		if (isHorizontal) {
			List<int[]> horzMatches = neighborMatches.get(1);
			int[] middleNeighbors = horzMatches.get(horzMatches.size()/2);
			int mirrorRow = middleNeighbors[0];
//			System.out.println("mirrorRow:" + mirrorRow);
			int rowsAboveMirror = mirrorRow + 1;
			number = rowsAboveMirror * 100;
		}
		else {
			List<int[]> vertMatches = neighborMatches.get(0);
			System.out.println("vertMatches in findNumForPat: " + vertMatches);
			int[] middleNeighbors = vertMatches.get(vertMatches.size()/2);
			int mirrorCol = middleNeighbors[0];
//			System.out.println("mirroCol:" + mirrorCol);
			int colsLeftMirrorLine = mirrorCol + 1;
			number = colsLeftMirrorLine;
		}
		
		return number;
	}
	
	// find all resulsts for patterns
	int findAllNumbersForPatters() {
		int total = 0;

		int patternInd = 0;
		
		for (char[][] pattern : allPatterns) {
			total += findNumberForPattern(pattern);
			System.out.println("patternInd: " + patternInd);
			if (patternInd == 9) {
				System.out.println("patter:");
				for (char[] p : pattern) {
					System.out.println(Arrays.toString(p));
				}
			}
				
			patternInd++;
		}
		
		return total;
	}
	
	public static void main(String[] args) throws IOException{
		String path = "src/adventOfCode/resources/day-13-input.txt";
		Day13 d = new Day13(path);
		
		// test consructor
//		for (char[][] pattern : d.allPatterns) {
//			System.out.println("next pattern:");
//			
//			for (char[] row : pattern) {
//				System.out.println(Arrays.toString(row));
//			}
//			
//			System.out.println();
//		}
		
		char[][] pattern = d.allPatterns.get(0);

		// test findvertHorNeighborMatches line
		List<List<int[]>> vertHorNeighborMatches =  d.findvertHorNeighborMatches(pattern);
		System.out.println("vert mathces:");
		for (int[] verts : vertHorNeighborMatches.get(0)) {
			System.out.println(Arrays.toString(verts));
		}
		System.out.println("horz matches:");
		for (int[] horiz : vertHorNeighborMatches.get(1)) {
			System.out.println(Arrays.toString(horiz));
		}
		
		
		// test isHorizontalMatch
		boolean isHorizontal = d.isHorizontalMatch(vertHorNeighborMatches, pattern);
		System.out.println("isHorizontal = " + isHorizontal);
	
		// test number lines for pattern
		int num = d.findNumberForPattern(pattern);
		System.out.println("num: " + num);
	
		System.out.println("allPatterns size:" + d.allPatterns.size());
//		System.out.println(Arrays.toString(d.allPatterns.get(0)) );
		
		int solution = d.findAllNumbersForPatters();
		System.out.println("solution = " + solution);
	}
}
