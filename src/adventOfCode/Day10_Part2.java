package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day10_Part2 {
	int numRows;
	int numCols;
	Pipe[][] grid;
	Pipe[][] gridCopy;
	// start location [row,col]
	int[] start = new int[2];
	
	// Use the input to create a 2D map
	Day10_Part2(int gridRows, int gridCols, String path) throws IOException {
		numRows = gridRows;
		numCols = gridCols;
		grid = new Pipe[numRows][numCols];
		gridCopy = new Pipe[numRows][numCols];
		
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numCols; col++) {
				gridCopy[row][col] = new Pipe('$');
			}
		}
		
		File input = new File(path);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			int row = 0;
			while ((line=reader.readLine()) != null) {
				Pipe[] curRow = new Pipe[numCols];
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
		Pipe prevPipe = grid[curRow][curCol];
		Pipe curPipe = grid[curRow][curCol];
		
		// insert Pipe into copy
		gridCopy[curRow][curCol] = curPipe;
		// *****************************************************
		
		// until you reach the original starting point keep navigating
		while(!Arrays.equals(start, curLoc)) {
			// find new direction
			char nextDir = findNextDirection(curLoc, fromDir);
			// find next tile grid location
			int[] nextPipeLoc = findNextPipe(curLoc,nextDir);
			// update current location
			curLoc = nextPipeLoc;
			// update from direction
			fromDir = oppositeDir(nextDir);
			// increment number of tiles traversed
			numPipesInLoop++;
			
			//part 2
			curRow = curLoc[0];
			curCol = curLoc[1];
			
			prevPipe = curPipe;
			curPipe = grid[curRow][curCol];
			
			// Part 2 set edges to mark inbounds and out of bounds
			setEdges(prevPipe,curPipe,fromDir);
			
			
			gridCopy[curRow][curCol] = curPipe;
		}
		
		// When back to start set the pipe shape and edges for 'S'
		Pipe startPipe = new Pipe('F');
		startPipe.U = 'I';
		startPipe.L = 'I';
		
		gridCopy[start[0]][start[1]] = startPipe;
		
		return numPipesInLoop;
	}
	
	
	
	//Part 2 
	// set the inner of the curPipe given the last pipe and direction it came from
	void setEdges(Pipe prevPipe, Pipe curPipe, char fromDir) {
		char prevShape = prevPipe.shape;
		
		switch (fromDir) {
			case 'N':
				if (prevShape == '|') {
					switch (curPipe.shape) {
						case '|':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							break;
						case 'L':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.L == 'I' ? 'I' : 'O';
							break;
						case 'J':
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.R == 'I' ? 'I' : 'O';						
							break;
						default:
							break;
					}
				}
				if (prevShape == 'F') {
					switch (curPipe.shape) {
						case '|':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.L == 'I' ? 'O' : 'I';
							break;
						case 'L':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.L == 'I' ? 'I' : 'O';
							break;
						case 'J':
							curPipe.R = prevPipe.L == 'I' ? 'O' : 'I';
							curPipe.D = prevPipe.L == 'I' ? 'O' : 'I';
							break;							
						default:
							break;
					}
				}
				if (prevShape == '7') {
					switch (curPipe.shape) {
						case '|':
							curPipe.L = prevPipe.R == 'I' ? 'O' : 'I';
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							break;
						case 'L':
							curPipe.L = prevPipe.R == 'I' ? 'O' : 'I';
							curPipe.D = prevPipe.R == 'I' ? 'O' : 'I';
							break;
						case 'J':
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.R == 'I' ? 'I' : 'O';
							break;							
						default:
							break;
					}
				}
				break;
			case 'S':
				if (prevShape == '|') {
					switch (curPipe.shape) {
						case '|':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							break;
						case '7':
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O'; 
							curPipe.U = prevPipe.R == 'I' ? 'I' : 'O'; 
							break;
						case 'F':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.U = prevPipe.L == 'I' ? 'I' : 'O';
							break;
						default:
							break;
					}
				}
				if (prevShape == 'J') {
					switch (curPipe.shape) {
						case '|':
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							curPipe.L = prevPipe.R == 'I' ? 'O' : 'I';
							break;
						case '7':
							curPipe.R = prevPipe.R == 'I' ? 'I' : 'O';
							curPipe.U = prevPipe.R == 'I' ? 'I' : 'O';
							break;
						case 'F':
							curPipe.L = prevPipe.R == 'I' ? 'O' : 'I';
							curPipe.U = prevPipe.R == 'I' ? 'O' : 'I';
							break;
						default:
							break;
					}
				}
				if (prevShape == 'L') {
					switch (curPipe.shape) {
						case '|':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.L == 'I' ? 'O' : 'I';
							break;
						case '7':
							curPipe.R = prevPipe.L == 'I' ? 'O' : 'I';
							curPipe.U = prevPipe.L == 'I' ? 'O' : 'I';
							break;
						case 'F':
							curPipe.L = prevPipe.L == 'I' ? 'I' : 'O';
							curPipe.U = prevPipe.L == 'I' ? 'I' : 'O';
							break;
						default:
							break;
					}
				}
				break;
			case 'E':
				if (prevShape == '-') {
					switch (curPipe.shape) {
						case '-':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							break;
						case 'F':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.L = prevPipe.U == 'I' ? 'I' : 'O';
							break;
						case 'L':
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							curPipe.L = prevPipe.D == 'I' ? 'I' : 'O';
							break;
						default:
							break;
					}
				}
				if (prevShape == '7') {
					switch (curPipe.shape) {
						case '-':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.U == 'I' ? 'O' : 'I';
							break;
						case 'F':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.L = prevPipe.U == 'I' ? 'I' : 'O';
							break;
						case 'L':
							curPipe.D = prevPipe.U == 'I' ? 'O' : 'I';
							curPipe.L = prevPipe.U == 'I' ? 'O' : 'I';
							break;
						default:
							break;
					}					
				}
				if (prevShape == 'J') {
					switch (curPipe.shape) {
						case '-':
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							curPipe.U = prevPipe.D == 'I' ? 'O' : 'I';
							break;
						case 'F':
							curPipe.U = prevPipe.D == 'I' ? 'O' : 'I';
							curPipe.L = prevPipe.D == 'I' ? 'O' : 'I';
							break;
						case 'L':
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							curPipe.L = prevPipe.D == 'I' ? 'I' : 'O';
							break;
						default:
							break;
					}					
				}				
				break;
			case 'W':
				if (prevShape == '-') {
					switch (curPipe.shape) {
						case '-':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							break;
						case '7':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.U == 'I' ? 'I' : 'O';
							break;
						case 'J':
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.D == 'I' ? 'I' : 'O';
							break;
						default:
							break;
					}					
				}
				if (prevShape == 'F') {
					switch (curPipe.shape) {
						case '-':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.D = prevPipe.U == 'I' ? 'O' : 'I';
							break;
						case '7':
							curPipe.U = prevPipe.U == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.U == 'I' ? 'I' : 'O';
							break;
						case 'J':
							curPipe.D = prevPipe.U == 'I' ? 'O' : 'I';
							curPipe.R = prevPipe.U == 'I' ? 'O' : 'I';
							break;
						default:
							break;
					}
				}
				if (prevShape == 'L') {
					switch (curPipe.shape) {
						case '-':
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							curPipe.U = prevPipe.D == 'I' ? 'O' : 'I';
							break;
						case '7':
							curPipe.U = prevPipe.D == 'I' ? 'O' : 'I';
							curPipe.R = prevPipe.D == 'I' ? 'O' : 'I';
							break;
						case 'J':
							curPipe.D = prevPipe.D == 'I' ? 'I' : 'O';
							curPipe.R = prevPipe.D == 'I' ? 'I' : 'O';
							break;
						default:
							break;
					}
				}				
				break;			
				
			default:
				break;
			}
	}
	
	
	// method to count the inner tiles
	int countEnclosedTiles() {
		int total = 0;
		
		// for reach row in the gridCopy
		for (int row=0; row<gridCopy.length; row++) {
			// for each col in the row
			// start inbounds false
			boolean inbounds = false;
			
			for (int col=0; col<gridCopy[0].length; col++) {
				Pipe curPipe = gridCopy[row][col];
				char rightEdge = curPipe.R;
				char leftEdge = curPipe.L;
				char curShape = curPipe.shape;
				
				// if you reach a inbounds right marker inbounds is true
				if (rightEdge == 'I') {
					inbounds = true;
				}
				
				// if you reach inbounds left marker inbounds becomes false
				if (leftEdge == 'I') {
					inbounds = false;
				}
				
				// while inbounds and if a character equals $ increment the count
				if (inbounds && curShape == '$') {
					total++;
				}
			}
		}
		
		return total;
	}
	
	
	
	
	// the number steps to get to the farthest point is number of tiles divided by 2
		
	public static void main(String[] args) throws IOException {
		int gridRows = 140;
		int gridCols = 140;
		String path = "src/adventOfCode/resources/day-10-input.txt";
		
		Day10_Part2 d = new Day10_Part2(gridRows, gridCols, path);
		
		// 'S' is at position [69,88]
		int[] secPipeLoc = {69,89};
		Pipe secondPipe = d.grid[69][89];
		
		secondPipe.R = 'O';
		secondPipe.U = 'O';
		
		char startFromDir = 'W';
		
		int numPipesInLoop = d.findNumPipesInLoop(secPipeLoc, startFromDir);
		System.out.println("pipes in loop = " + numPipesInLoop);
		System.out.println("half pipes/steps to middle = " + numPipesInLoop/2);
		
		int inboundTiles = d.countEnclosedTiles();
		System.out.println("inbound tiles = " + inboundTiles);
	}
	
	
	
	class Pipe {
		char shape;
		char U = '*';
		char D = '*';
		char L = '*';
		char R = '*';
		
		Pipe(char shape) {
			this.shape = shape;
		}
		
		@Override
		public String toString() {
			return String.valueOf(shape);
		}
	}
}

