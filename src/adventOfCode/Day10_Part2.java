package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day10_Part2 {
	// understand
	// On a 2D grid there are figures representing a network of pipes.
	// I need to find the continuous loop from S back to S
	// Each position will only have connectors.
	// so after choosing a direction to start from I need to find the other connector
	// Then continue until I loop back to the starting point
	// Once I find the loop (or possibly during mapping of the loop) find the half way point
	
	// Constraints to consider:
	// Map size. Input is 140x140 so shouldn't need to worry about memory.
	// Using a 2D array of char and no large numbers
	
	
	//plan & execute
	int gridSize;
	Pipe[][] grid;
	Pipe[][] gridCopy;
	// start location [row,col]
	int[] start = new int[2];
	
	
	// Use the input to create a 2D map
	Day10_Part2(int gridSize, String path) throws IOException {
		this.gridSize = gridSize;
		grid = new Pipe[gridSize][gridSize];
		gridCopy = new Pipe[gridSize][gridSize];
		
		for (int row=0; row<gridSize; row++) {
			for (int col=0; col<gridSize; col++) {
				gridCopy[row][col] = new Pipe('O');
			}
		}
		
		File input = new File(path);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			int row = 0;
			while ((line=reader.readLine()) != null) {
				Pipe[] curRow = new Pipe[gridSize];
				for (int col=0; col<line.length(); col++) {
					char c = line.charAt(col);
					Pipe p = new Pipe(line.charAt(col));
					curRow[col] = p;
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
		Pipe pipe = grid[pipeLoc[0]][pipeLoc[1]];
		// switch statement using a case for each pipe character
		return switch (pipe.shape) {
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
	int findNumPipesInLoop(int[] secPipeLoc, char startFromDirection) {
		int numPipesInLoop = 1;
		int[] curLoc = secPipeLoc;
		char fromDir = startFromDirection;
		
		// Part 2
		// *****************************************************
		// STARTING AT SECOND PIPE
		int curRow = curLoc[0];
		int curCol = curLoc[1];
		Pipe curPipe = grid[curRow][curCol];
		curPipe.inner = 'R';
		gridCopy[curRow][curCol] = curPipe;
		
		char prevInner = curPipe.inner;
		// *****************************************************
		
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
			
			// set inner edge
//			setInnerEdge(curPipe, prevInner);;
			
			gridCopy[curRow][curCol] = curPipe;
		}
		
		return numPipesInLoop;
	}
	
	
	
	//Part 2 
	// might need to set both inner and outer edge
	void setInnerEdge(Pipe curPipe, char prevInner, char fromDir) {
		switch (fromDir) {
		case 'N':
			if (curPipe.shape == 'L') {
				
			}
			break;

		default:
			break;
		}
	}
	
	
	
	
	
	
//	int countEnclosed() {
//		int count = 0;
//		
//		for (int row=0; row<gridSize; row++) {
//			boolean isEnclosed = false;
//			for (int col=0; col<gridSize; col++) {
//				Pipe cur = gridCopy[row][col];
//				
//				if (!isEnclosed && ( cur=='|' || cur=='J' || cur=='7') ) {
//					isEnclosed = true;
////					System.out.println("cur tile = " + cur);
//				}
//				if (isEnclosed && ( cur=='|' || cur=='F' || cur=='L') ) {
//					isEnclosed = false;
//				}
//				if (isEnclosed && cur=='O') {
//					count++;
//				}
//			}
//		}
//		
//		return count;
//	}
	
	
	// the number steps to get to the farthest point is number of tiles divided by 2
		
	public static void main(String[] args) throws IOException {
		int gridSize = 10;
		String path = "src/adventOfCode/resources/day-10-input-ex3.txt";
		
		Day10_Part2 d = new Day10_Part2(gridSize, path);
		
		// 'S' is at position [69,88]
		System.out.println(" 'S' position:  " + Arrays.toString( d.start));
		int[] secPipeLoc = {4,12};
		Pipe secondPipe = d.grid[4][12];
		secondPipe.inner = 'R';
		
		System.out.println("secondPipe:" + secondPipe + " outer:" + secondPipe.outer + " inner:" + secondPipe.inner);
		
		
		char startFromDir = 'W';
		
		System.out.println("nextDir: " + d.findNextDirection(secPipeLoc, startFromDir));
		
		int numPipesInLoop = d.findNumPipesInLoop(secPipeLoc, startFromDir);
		System.out.println(" ");
		System.out.println(numPipesInLoop);
		System.out.println("half pipes/steps to middle = " + numPipesInLoop/2);
		System.out.println(" ");
		
//		for (Pipe[] row : d.gridCopy) {
//			System.out.println(Arrays.toString(row));
//		}
		
//		int enclosedGround = d.countEnclosed();
//		System.out.println("enclosed ground = " + enclosedGround);
	}
	
	
	
	class Pipe {
		char shape;
		char inner;
		char outer;
		
		Pipe(char shape) {
			this.shape = shape;
		}
		
		Pipe(char shape, char inner, char outer) {
			this.shape = shape;
			this.inner = inner;
			this.outer = outer;
		}
		
		@Override
		public String toString() {
			return String.valueOf(shape);
		}
	}
}

