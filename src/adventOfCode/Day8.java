package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {

	// directions
	List<Character> directions= new ArrayList<>();
	// create a Map of nodes key of node letters and value array of right and left letters
	Map<String, String[]> nodesMap = new HashMap<>();

	String startNode = "AAA";
	String endNode = "ZZZ";
	
	List<String> ghostStartNodes = new ArrayList<>();
	List<String> ghostEndNodes = new ArrayList<>();
	
	Day8() throws IOException {
		File input = new File("src/adventOfCode/resources/day-8-input.txt");
		int lineNumber = 1;
		String curNode = "";
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			while((line=reader.readLine()) != null) {
				if (lineNumber == 1) {
					for (int i=0; i<line.length(); i++) {
						directions.add(line.charAt(i));
					}
				}
				else if (lineNumber == 2) {
				}
				else {
					curNode = line.substring(0,3);
					String left = line.substring(7,10);
					String right = line.substring(12,15);
					nodesMap.put(curNode, new String[] {left,right});
					
					// part two keep track of the possible ghost start and end nodes
					if (curNode.endsWith("A")) {
						ghostStartNodes.add(curNode);
					}
					if (curNode.endsWith("Z")) {
						ghostEndNodes.add(curNode);
					}
				}
				lineNumber++;
			}
		}
	}
	
	int navigateNodes() {
		// keep track of number of steps
		int numSteps = 0;
		String curNode = startNode;
		int dirIndex = 0;
		
		// while the next node is not ZZZ, continue navigating
		while (!curNode.equals(endNode)) {
			// if you reach the end of R/L directions go back to start
			if (dirIndex == directions.size()) {
				dirIndex = 0;
			}
			
			// choose the current direction R or L for the next node and increment index
			char direction = directions.get(dirIndex);
			dirIndex++;
			
			// get the next node from the map
			String[] connectedNodes = nodesMap.get(curNode);
			String nextNode = direction == 'L' ? connectedNodes[0] : connectedNodes[1];
			curNode = nextNode;
			
			numSteps++;
		}
		
		return numSteps;
	}
	
	long navigateSingleGhost(String start) {
		int numSteps = 0;
		String curNode = start;
		int dirIndex = 0;

		boolean foundAnEnd = false;
		
		while (!foundAnEnd) {
			if (dirIndex == directions.size()) {
				dirIndex = 0;
			}
			
			char direction = directions.get(dirIndex);
			dirIndex++;
			
			String[] connectedNodes = nodesMap.get(curNode);
			String nextNode = direction == 'L' ? connectedNodes[0] : connectedNodes[1];
			curNode = nextNode;
			numSteps++;
			
			if (curNode.endsWith("Z")) foundAnEnd = true;
		}
		
		return numSteps;
	}
	
	List<Long> getStepIntervals() {
		List<Long> intervals = new ArrayList<>();
		
		for (int i=0; i<ghostStartNodes.size(); i++) {
			long curStepCount = navigateSingleGhost(ghostStartNodes.get(i));
			intervals.add(curStepCount);
		}
		
		return intervals;
	}
	
	BigInteger findFinalNumSteps(List<Long> stepIntervals) {
		BigInteger numSteps= BigInteger.valueOf(stepIntervals.get(0));
		
		for (int i=1; i<stepIntervals.size(); i++) {
			BigInteger next = BigInteger.valueOf(stepIntervals.get(i));
			numSteps = findLCM(numSteps, next);
		}
		
		return numSteps;
	}
	
		
	BigInteger findLCM(BigInteger a, BigInteger b) {
		BigInteger gcd = a.gcd(b);
        BigInteger product = a.multiply(b);
        return product.divide(gcd);
	}
	
	boolean allEndNodes(List<String> nodes) {
		for (String s : nodes) {
			if (!s.endsWith("Z")) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		Day8 d8 = new Day8();
		
		int steps = d8.navigateNodes();
		System.out.println("steps=" + steps);

		List<Long> minStepCountsList = d8.getStepIntervals();
		BigInteger finalAnswer = d8.findFinalNumSteps(minStepCountsList);
		System.out.println("final answer = " + finalAnswer);
		
	}
}
