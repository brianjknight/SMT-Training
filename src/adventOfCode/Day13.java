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
//		System.out.println("findvertHorNeighborMatches**********");
		
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
		
//		for (List<int[]> list : vertHorNeighborMatches) {
//			for (int[] i : list) { 
//				System.out.println(Arrays.toString(i));
//			}
//		}
		
//		System.out.println("findvertHorNeighborMatches**********");
		return vertHorNeighborMatches;
	}
	
	// find largest horizontal mirror line
	int[] findLargestHorzMirror(List<int[]> horzMatches , char[][] pattern) {
		int largestMirrorSize = 0;
		int[] largestMirrorLine = new int[3];
		
		for (int[] match : horzMatches) {
			int topInd = match[0];
			int botInd = match[1];
			
			int curLargestMirSize = 0;
			
			while (topInd >= 0 && botInd < pattern.length) {
				String top = new String(pattern[topInd]);
				String bottom = new String(pattern[botInd]);
				
				if (!top.equals(bottom)) {
//					if (topInd == 0 || botInd == pattern.length-1) {
//						if (curLargestMirSize > largestMirrorSize) {
//							largestMirrorSize = curLargestMirSize;
//							largestMirrorLine[0] = match[0];
//							largestMirrorLine[1] = match[1];
//						}
//					}
					
					break;
				}
				 
				if (topInd == 0 || botInd == pattern.length-1) {
					curLargestMirSize++;
					if (curLargestMirSize > largestMirrorSize) {
						largestMirrorSize = curLargestMirSize;
						largestMirrorLine[0] = match[0];
						largestMirrorLine[1] = match[1];
					}
					break;
				}
				
				curLargestMirSize++;
				topInd--;
				botInd++;
			} 
		}
		
		largestMirrorLine[2] = largestMirrorSize;
		return largestMirrorLine;
	}
	
	int[] findLargestVertMirror(List<int[]> vertMatches , char[][] pattern) {
//		System.out.println("findLargestVertMirror^^^^^^^^^^^^");
		
		int largestMirrorSize = 0;
		int[] largestMirrorLine = new int[3];
		
		for (int[] match : vertMatches) {
//			System.out.println("match: " + Arrays.toString(match));
			int leftInd = match[0];
			int rightInd = match[1];
//			System.out.println("leftInd=" + leftInd + " rightInd=" + rightInd);
			
			int curLargMirSize = 0;
			
			while (leftInd  >= 0 && rightInd < pattern[0].length) {
//				System.out.println("curLargMirsiz: " + curLargMirSize);
				String left = "";
				String right = "";
				
				
				for (char[] row : pattern) {
					left += row[leftInd];
					right += row[rightInd];
				}
//				System.out.println("leftInd: " + leftInd + " left: " + left);
//				System.out.println("rightInd: " + rightInd + " right: " + right);
//				System.out.println("left.equalsright:" + left.equals(right));
				
				if (!left.equals(right)) {
					if (leftInd == 0 || rightInd == pattern[0].length-1) {
						if (curLargMirSize > largestMirrorSize) {
							largestMirrorSize = curLargMirSize;
							largestMirrorLine[0] = match[0];
							largestMirrorLine[1] = match[1];
						}
					}
					
					break;
				}
				
				if (leftInd == 0 || rightInd == pattern[0].length-1) {
					curLargMirSize++;
					if (curLargMirSize > largestMirrorSize) {
						largestMirrorSize = curLargMirSize;
						largestMirrorLine[0] = match[0];
						largestMirrorLine[1] = match[1];
					}
					break;
				}
				
				curLargMirSize++;
				leftInd--;
				rightInd++;
			}
			
		}
		largestMirrorLine[2] = largestMirrorSize;
		
//		System.out.println("return: " + Arrays.toString(largestMirrorLine));
//		System.out.println("findLargestVertMirror^^^^^^^^^^^^");
		return largestMirrorLine;
	}
	
	int findNumForPattern(char[][] pattern) {
		int num = 0;
		List<List<int[]>> neighborMatches = findvertHorNeighborMatches(pattern);
		
		
		int[] largestHorzMirror = findLargestHorzMirror(neighborMatches.get(1), pattern);
		int[] largestVertMirror = findLargestVertMirror(neighborMatches.get(0), pattern);
		
		
		if (largestHorzMirror[2] > largestVertMirror[2]) {
			int mirrorRow = largestHorzMirror[0];
			int rowsAboveMirror = mirrorRow + 1;
			num = rowsAboveMirror * 100;
		}
		else {
			int mirrorCol = largestVertMirror[0];
			int colsLeftMirrorLine = mirrorCol + 1;
			num = colsLeftMirrorLine;
		}
		
		if (largestHorzMirror[2] > 0 && largestVertMirror[2] > 2) {
			System.out.println("found horz & vert mirrors");
			
			for(char[] p : pattern) {
				for (char c : p) {
					System.out.print(c);
				}
				System.out.println("");
			}
			
			System.out.println("horz: " + Arrays.toString(largestHorzMirror));
			System.err.println("vert: " + Arrays.toString(largestVertMirror));
			
			System.out.println("return num for pattern = " + num);
		}
		
		return num;
	}
	
	// find all resulsts for patterns
	int findAllNumbersForPatterns() {
//		System.out.println("findAllNumbersForPatterns************");
		int total = 0;

		int patternInd = 0;
		
		for (char[][] pattern : allPatterns) {
//			System.out.println("patternInd: " + patternInd);
//			for(char[] p : pattern) {
//				for (char c : p) {
//					System.out.print(c);
//				}
//				System.out.println("");
//			}
			
			
			total += findNumForPattern(pattern);
//			if (patternInd == 9) {
//				System.out.println("patter:");
//				for (char[] p : pattern) {
//					System.out.println(Arrays.toString(p));
//				}
//			}
				
			patternInd++;
		}
//		System.out.println("findAllNumbersForPatterns************\n");
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
//
		for(char[] p : pattern) {
			for (char c : p) {
				System.out.print(c);
			}
			System.out.println("");
		}
		
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
	
		int[] largestHorzMirrorLine = d.findLargestHorzMirror(vertHorNeighborMatches.get(1), pattern);
		System.out.println("largestHorzMirrorLine: " + Arrays.toString(largestHorzMirrorLine));
		
		int[] largestVertMirLine = d.findLargestVertMirror(vertHorNeighborMatches.get(0), pattern);
		System.out.println("largestVertMirLine: " + Arrays.toString(largestVertMirLine));
				
		// test number lines for pattern
		int num = d.findNumForPattern(pattern);
		System.out.println("num for pattern = " + num);
		 
//		int num = d.findNumberForPattern(pattern);
//		System.out.println("num: " + num);
	
//		int solution = d.findAllNumbersForPatterns();
//		System.out.println("solution = " + solution);
		
//		System.out.println("pattern count = " + d.allPatterns.size());
		
//		char[][] last = d.allPatterns.get(99);
//		for(char[] p : last) {
//			for (char c : p) {
//				System.out.print(c);
//			}
//			System.out.println("");
//		}
		
		int answer = d.findAllNumbersForPatterns();
		System.out.println("answer = " + answer);
	}
}
