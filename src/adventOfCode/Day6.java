package adventOfCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
	int[][] races;
	
	// convert input to pairs of time and record distance
	void setInputPart1() {
		int[] a = {49,298};
		int[] b = {78,1185};
		int[] c = {79,1066};
		int[] d = {80,1181};
		
		races = new int[4][];
		races[0] = a;
		races[1] = b;
		races[2] = c;
		races[3] = d;
	}
		
	// find combinations of press and travel time for each race
	List<List<int[]>> calculateButtonPressAndTravelTimes() {
		List<List<int[]>> combosForEachRace = new ArrayList<>();
		
		// iterate each race
		for (int[] race : races) {
			List<int[]> racePressTravelCombos = new ArrayList<>();
			
			int raceTime = race[0];
			
			// Find possible combinations of button press (a.k.a travel rate) and travel time for a race
			for (int i=1; i<raceTime; i++) {
				int[] curCombo = new int[2];
				curCombo[0] = i;
				//	Travel time is race time minus press time
				curCombo[1] = raceTime - i;
				racePressTravelCombos.add(curCombo);
			}

			combosForEachRace.add(racePressTravelCombos);
		}
		
		return combosForEachRace;
	}
	
	// Find number of combos to go farther for each race
	int[] findWinnersForEachRace(List<List<int[]>> combosForEachRace) {
		int[] numWinners = new int[races.length];
		
		//	For each race 
		for (int i=0; i<races.length; i++) {
			int raceRecord = races[i][1];
			
			// for each set of combos
			List<int[]> raceCombosList = combosForEachRace.get(i);
			for (int[] combo : raceCombosList) {
				int travelRate = combo[0];
				int travelTime = combo[1];
				
				//	Multiply travel rates * travel time
				int distance = travelRate * travelTime;
				
				//	Increment win scenario count if distance beats the record 
				if (distance > raceRecord) {
					numWinners[i] = numWinners[i] + 1;
				}
			}
		}
		
		return numWinners;
	}
	
	
	//	Multiply all counts to get answer
	int multiplyCountOfWinners(int[] winnersForRaces) {
		int product = 0;
		
		for (int i=0; i< winnersForRaces.length; i++) {
			int curWinners = winnersForRaces[i];
			if (curWinners > 0) { 
				if (product == 0) {
					product = curWinners;
				}
				else {
					product *= curWinners;
				}
			}
		}
		
		return product;
	}
	
	BigInteger calculateSingleRace(BigInteger raceTime, BigInteger raceRecord) {
		BigInteger minPress = BigInteger.ZERO;
		BigInteger maxPress = BigInteger.ZERO;
		
		int raceTimeInt = raceTime.intValue();
		// start from press time 1 until you find a winner then stop break the loop
		for (int i=1; i<raceTimeInt; i++) {
			BigInteger pressTime = BigInteger.valueOf(i);
			BigInteger travelTime = raceTime.subtract(BigInteger.valueOf(i));
			BigInteger distance = pressTime.multiply(travelTime);
			
			
			if (distance.compareTo(raceRecord) > 0) { 
				minPress = pressTime;
				maxPress = raceTime.subtract(minPress);
				break;
			}
		}
		
		// number of winners is current max press time - min press time + 1
		return maxPress.subtract(minPress).add(BigInteger.ONE);
	}
	
	public static void main(String[] args) {
		Day6 d6 = new Day6();
		d6.setInputPart1();
		
		List<List<int[]>> pressAndTravelTimes = d6.calculateButtonPressAndTravelTimes();
				
		int[] numWinnersForRaces = d6.findWinnersForEachRace(pressAndTravelTimes);
		
		int product = d6.multiplyCountOfWinners(numWinnersForRaces);
		System.out.println("product=" + product);
		
		BigInteger raceTime = BigInteger.valueOf(49787980);
		BigInteger raceRecord = BigInteger.valueOf(298118510661181L);
		
		BigInteger part2answer = d6.calculateSingleRace(raceTime, raceRecord);
		System.out.println("part 2 answer = " + part2answer.toString());
	}
}
