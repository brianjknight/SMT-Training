package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {

	// convert input to 2D List
	List<String> rawInput = new ArrayList<>();
	List<List<Character>> universe = new ArrayList<>();
	Map<Integer,int[]> galaxyMap = new HashMap<>();
	
	List<Integer> emptyRows = new ArrayList<>();
	List<Integer> emptyCols = new ArrayList<>();
		
	Day11() {}

	Day11(String path) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			String line;
			while((line=reader.readLine()) != null) {
				rawInput.add(line);
				List<Character> curRow = new ArrayList<>();
				for(char c : line.toCharArray()) {
					curRow.add(c);
				}
				universe.add(curRow);
			}
		}
	}
	
	// find empty rows 
	void findEmptyRows() {
		for (int i=0; i< rawInput.size(); i++) {
			if (!rawInput.get(i).contains("#")) {
				emptyRows.add(i);
			}
		}
	}
	
	// find empty columns
	void findEmptyColumns() {
		COLUMNS: for (int col=0; col<universe.get(0).size(); col++) {
					for (int row=0; row<universe.size(); row++) {
						char cur = universe.get(row).get(col);
						if (cur=='#') {
							continue COLUMNS;
						}
					}
					emptyCols.add(col);
				 }
	}
	
	// map the location of each galaxy Map<Integer, int loc[]
	void mapGalaxies() {
		int galNum = 1;
		
		// add -1 key for total number of galaxies

		for (int row=0; row<universe.size(); row ++) {
			for (int col=0; col<universe.get(0).size(); col++) {
				if (universe.get(row).get(col)=='#') {
					galaxyMap.put(galNum, new int[] {row,col});
					galNum++;
				}
			}
		}
		
		galaxyMap.put(-1,new int[] {galNum-1});
	}
	
	
	// create all the unique pairs
	List<int[]> findUniquePairs() {
		List<int[]> uniquePairs = new ArrayList<>();
		int numGal = galaxyMap.get(-1)[0];
		for (int i = 1; i <= numGal; i++) {
            for (int j = i + 1; j <= numGal; j++) {
            	uniquePairs.add(new int[]{i, j});
            }
        }
		
		return uniquePairs;
	}
	
	
	// Part 2 find distance while inserting rows and columns
	long findDistance(int[] galLoc1, int[] galLoc2, int nInserts) {
		int row1 = galLoc1[0];
		int col1 = galLoc1[1];
		
		int row2 = galLoc2[0];
		int col2 = galLoc2[1];
		
		int minRow = Math.min(row1, row2);
		int maxRow = Math.max(row1, row2);
		
		int minCol = Math.min(col1, col2);
		int maxCol = Math.max(col1, col2);
		
		// from minRow to maxRow, if an empty row is found add n num rows
		long additionalRows = 0;
		for (int row : emptyRows) {
			if (row > minRow && row < maxRow) {
				additionalRows += nInserts;
			}
		}
		
		// from minCol to maxCol if an empty col is found add n num cols
		long additionalCols = 0;
		for (int col : emptyCols) {
			if (col > minCol && col < maxCol) {
				additionalCols += nInserts;
			}
		}
		
		long totalRows = Math.abs(row1 - row2) + additionalRows;
		long totalCols = Math.abs(col1 - col2) + additionalCols;
		
		return totalRows + totalCols;
	}
		
	// find and sum all distances
	long sumAllDistances(List<int[]> uniqueGalaxyPairs, int nInserts) {
		long sumDistances = 0;
		
		for (int[] pair : uniqueGalaxyPairs) {
			int[] loc1 = galaxyMap.get(pair[0]);
			int[] loc2 = galaxyMap.get(pair[1]);
						
			sumDistances += findDistance(loc1, loc2, nInserts);
		}
		
		return sumDistances;
	}
	
	public static void main(String[] args) throws IOException{
		String path = "src/adventOfCode/resources/day-11-input.txt";
		Day11 d = new Day11(path);
		
		d.findEmptyRows();
		d.findEmptyColumns();
		
		d.mapGalaxies();
		List<int[]> pairs = d.findUniquePairs();
				
		int partOneInsert = 1;
		int partTwoInserts = 999_999;
		
		long sumDistancesp1 = d.sumAllDistances(pairs, partOneInsert);
		System.out.println("sum of distances part 1 = " + sumDistancesp1);
		
		long sumDistancesp2 = d.sumAllDistances(pairs, partTwoInserts);
		System.out.println("sum of distances part 2 = " + sumDistancesp2);
	}
}
