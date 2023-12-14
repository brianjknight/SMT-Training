package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.stream.StreamSource;

public class Day12 {

	List<List<String>> springRows = new ArrayList<>();
	List<List<Integer>> allGroupSizes = new ArrayList<>();
	
	// separate the input into spring row and groups of known damaged spring counts
	Day12(String path) throws IOException {
		File input = new File(path);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			while ((line=reader.readLine()) != null) {
				// split on space
				String[] groupsAndSizes = line.split(" ");

				//split springs into groups
				String group = groupsAndSizes[0];
				List<String> splitGroup = Arrays.stream(group.split("\\.+"))
		                .filter(s -> !s.isEmpty()).toList();
				springRows.add(splitGroup);
				
				// convert damaged spring group sizes to List of ints
				List<Integer> curSizes = Arrays.stream(groupsAndSizes[1].split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
				allGroupSizes.add(curSizes);
			}
		}
	}
	
	
	
	// reduce the string and and groups that are known to fit in a location
	
	
	// match up groups that can fit into sizes for a single row of springs
	List<List<Integer>> conendseGroupSizes(List<String> groupedSprings, List<Integer> groupSizes) {
		List<List<Integer>> condensedGroupSizes = new ArrayList<>();

		System.out.println("groupedSprings: " + groupedSprings);
		System.out.println("groupSizes: " + groupSizes);
		
		if (groupedSprings.size() == groupSizes.size()) {
			condensedGroupSizes.add(groupSizes);
			return condensedGroupSizes;
		}
		else {
			// iterate the groups of springs
			int groupSizeIndex = 0;
			
			for (int i=0; i<groupedSprings.size(); i++) {
				System.out.println("i=" + i);
				
				String curGroup = groupedSprings.get(i);
				System.out.println("curGroup = " + curGroup);
				int curGroupLength = curGroup.length();			
				
				Integer curSize = groupSizes.get(groupSizeIndex);
				System.out.println("curSize = " + curSize);
				
				List<Integer> curSizeList = new ArrayList<>(); 
				
				curSizeList.add(curSize);

				while (groupSizeIndex < groupSizes.size()-1) {
					System.out.println("first while loop");
					System.out.println("curSizeList:" + curSizeList);
					if (curGroup.length() == curSize) {
						condensedGroupSizes.add(curSizeList);
						groupSizeIndex++;
					}
					// if length is NOT equal
					else {
						int runningSizeTotal = curSize;
						curSizeList.add(curSize);
						boolean curSizeListIsFull = false;
						
						while(!curSizeListIsFull) {
							System.out.println("second while loop");
							int nextSize = groupSizes.get(groupSizeIndex + 1);
							System.out.println("nextSize:" + nextSize);
							if (nextSize + 1 + runningSizeTotal <= curGroupLength) {
								runningSizeTotal += nextSize + 1;
								curSizeList.add(nextSize);
								groupSizeIndex++;
								if (groupSizeIndex == groupSizes.size()-1) {
									curSizeListIsFull = true;
								}
							}
							else {
								
								curSizeListIsFull = true;
							}
						}
						
						condensedGroupSizes.add(curSizeList);
						groupSizeIndex++;
					}
					
				}
				
				// if the length is equal to the current size in group sizes, add that list to the new condensed list
			}
		}
		
		return condensedGroupSizes;
	}
	
	// condense all groups
	List<List<List<Integer>>> condenseAllGroupSizes() {
		List<List<List<Integer>>> allCondensed = new ArrayList<>();
		
		
		return allCondensed;
	}
		
	
	
	// remove the group and number from list of groups
	
	
	
	// reduce the list of ints for those groups that must be locked in
		
	
	
	// first find contiguous # substrings
		// remove those lengths from the groups list
	
		// second find contiguous #? substrings
		// see if any of those lengths match the groups list 
		// if so change the ?s to #s
		// remove the known group from string
		// remove the group length from groups list
	
	
		
	
		
	
	
	// for the truly unknown groups, determine how many arrangements are possible
	
	// Input is an updated string and list of groups
	
	// reduce the string size to array of unknown groups
	
	
	
	public static void main(String[] args) throws IOException {
		String path = "src/adventOfCode/resources/day-12-input-ex.txt";
		Day12 d = new Day12(path);
		
		// test reading input
		for (int i=0; i<d.springRows.size(); i++) {
			System.out.println("spring row: " + d.springRows.get(i) + " group sizes: " + d.allGroupSizes.get(i));
		}
		
		List<String> groups = new ArrayList<>(Arrays.asList("???","###"));
		List<Integer> sizes = new ArrayList<>(Arrays.asList(1,1,3));
		
		List<List<Integer>> condensedSizes = d.conendseGroupSizes(groups, sizes);
		System.out.println("condensedSizes");
		System.out.println(condensedSizes);
	}
}
