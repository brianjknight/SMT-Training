package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day10 {
	int gridSize;
	char[][] grid;
	char[][] gridCopy;
	int[] start = new int[2];
	
	
	// Use the input to create a 2D map
	Day10(int gridSize, String path) throws IOException {
		this.gridSize = gridSize;
		grid = new char[gridSize][gridSize];
		gridCopy = new char[gridSize][gridSize];
		
		for (int row=0; row<gridSize; row++) {
			for (int col=0; col<gridSize; col++) {
				gridCopy[row][col] = 'O';
			}
		}
		
		File input = new File(path);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			int row = 0;
			while ((line=reader.readLine()) != null) {
				char[] curRow = new char[gridSize];
				for (int col=0; col<line.length(); col++) {
					char c = line.charAt(col);
					curRow[col] = c;
					// find the 'S'(start point)
					if (c=='S') {
						start[0] = row;
						start[1] = col;
					}
				}
				grid[row] = curRow;
				row++;
			}
		}
	}
	
	// Find the new DIRECTION to navigate to, given a pipe and direction you are coming from
	// ASSUMES valid from direction is provided
	char findNextDirection(int[] pipeLoc, char fromDir) {
		char pipe = grid[pipeLoc[0]][pipeLoc[1]];
		// switch statement using a case for each pipe character
		return switch (pipe) {
			case '|' -> fromDir == 'S' ? 'N' : 'S';
			case '-' -> fromDir == 'W' ? 'E' : 'W';
			case 'L' -> fromDir == 'N' ? 'E' : 'N';
			case 'J' -> fromDir == 'W' ? 'N' : 'W';
			case '7' -> fromDir == 'W' ? 'S' : 'W';
			case 'F' -> fromDir == 'S' ? 'E' : 'S';
			default -> throw new IllegalArgumentException("Unexpected pipe value findNextDirection(): " + pipe);
		};
	}
	// Get the NEXT PIPE location give a tile and direction
	// ASSUMES toDir stays in the grid
	int[] findNextPipe(int[] pipeLoc, char toDir) {
		int row = pipeLoc[0];
		int col = pipeLoc[1];
		
		// switch case for direction
		// increment/decrement row/col
		return switch (toDir) {
			case 'N' -> new int[]{row-1,col};
			case 'S' -> new int[]{row+1,col};
			case 'E' -> new int[]{row,col+1};
			case 'W' -> new int[]{row,col-1};
			default -> throw new IllegalArgumentException("Unexpected value: " + toDir);
		};
	}
	
	char oppositeDir(char dir) {
		return switch (dir) {
			case 'N' -> 'S';
			case 'S' -> 'N';
			case 'E' -> 'W';
			case 'W' -> 'E';
			default -> throw new IllegalArgumentException("Unexpected value: " + dir);
			};
	}
	
	
	// Traverse all pipes keeping track of how many tiles are in the loop
	// Input param is current tile and from direction
	int findNumPipesInLoop(int[] secondPipe, char startFromDirection) {
		int numPipesInLoop = 1;
		int[] curLoc = secondPipe;
		char fromDir = startFromDirection;
		
		// Part 2
		int curRow = curLoc[0];
		int curCol = curLoc[1];
		char curPipe = grid[curRow][curCol];
		gridCopy[curRow][curCol] = curPipe;
		
		// until you reach the original starting point keep navigating
		while(!Arrays.equals(start, curLoc)) {
			// find new direction
			char nextDir = findNextDirection(curLoc, fromDir);
			// find next tile grid location
			int[] nextPipe = findNextPipe(curLoc,nextDir);
			// update current location
			curLoc = nextPipe;
			// update from direction
			fromDir = oppositeDir(nextDir);
			// increment number of tiles traversed
			numPipesInLoop++;
			
			//part 2
			curRow = curLoc[0];
			curCol = curLoc[1];
			curPipe = grid[curRow][curCol];
			
			gridCopy[curRow][curCol] = curPipe;
		}
		
		return numPipesInLoop;
	}
	
	public static void main(String[] args) throws IOException {
		int gridSize = 140;
		String path = "src/adventOfCode/resources/day-10-input.txt";
		
		Day10 d = new Day10(gridSize, path);
		
		// 'S' is at position [69,88]
		System.out.println(" 'S' position:  " + Arrays.toString( d.start));
		int[] secondPipe = {69,89};
		System.out.println("secondPipe:" + d.grid[69][89]);
		char startFromDir = 'W';
		
		System.out.println("nextDir: " + d.findNextDirection(secondPipe, startFromDir));
		
		int numPipesInLoop = d.findNumPipesInLoop(secondPipe, startFromDir);
		System.out.println(" ");
		System.out.println(numPipesInLoop);
		System.out.println("half pipes/steps to middle = " + numPipesInLoop/2);
		System.out.println(" ");
//		
//		for (char[] row : d.gridCopy) {
//			System.out.println(Arrays.toString(row));
//		}
		
//		int enclosedGround = d.countEnclosed();
//		System.out.println("enclosed ground = " + enclosedGround);
	}
}

