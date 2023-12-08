package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Part one answer 255048101

public class Day7 {
	static final char[] CARDS = {'2','3','4','5','6','7','8','9','T','J','Q','K','A'};
	static final char[] WILDS = {'J','2','3','4','5','6','7','8','9','T','Q','K','A'};
	static final String[] TYPES = {"HC","1P","2P","3K","FH","4K","5K"};
	static final Map<Character,Integer> CARD_VALUES = new HashMap<>();
	static final Map<Character,Integer> WILD_VALUES = new HashMap<>();
	static final Map<String,Integer> TYPE_VALUES = new HashMap<>();
	
	static List<String> handsInput = new ArrayList<>();
	
	public Day7() throws IOException {
		// assign card values
		for (int i=0; i<CARDS.length; i++) {
			CARD_VALUES.put(CARDS[i], i);
		}

		// assign values with wild card
		for (int i=0; i<WILDS.length; i++) {
			WILD_VALUES.put(WILDS[i], i);
		}
		
		// assign hand values
		for (int i=0; i<TYPES.length; i++) {
			TYPE_VALUES.put(TYPES[i],i);
		}
		
		// read input to list
		File input = new File("src/adventOfCode/resources/day-7-input.txt");
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			while((line=reader.readLine()) != null) {
				handsInput.add(line);
			}
		}
	}
	
	// determine hand type
	static String determineHandType(String hand) {
		// default type
		String type = "HC";

		boolean OP = false;
		boolean TP = false;
		boolean ThreeOfKind = false;
		
		// keep track of the numbers of cards in the hand
		int[] cardCount = new int[13];
		
		for(char c : hand.toCharArray()) {
			int cardIndex = CARD_VALUES.get(c);
			int newCount = ++cardCount[cardIndex];
			
			switch (newCount) {
			case 2:
				if (ThreeOfKind) {
					type = "FH";
				}
				else if (OP) {
					TP = true;
					type = "2P";
				}
				else {
					OP = true;
					type = "1P";
				}
				break;
			case 3:
				if (TP) {
					type = "FH";
				}
				else {
					ThreeOfKind = true;
					type = "3K";
				}
				break;	
			case 4:
				type = "4K";
				break;
			case 5:
				type = "5K";
				break;
			default:
				break;
			}
		}
		
		return type;
	}
	
	// compare different hand types
	static int compareDifferentHandTypes(String type1, String type2) {
		int value1 = TYPE_VALUES.get(type1);
		int value2 = TYPE_VALUES.get(type2);
		
		if (value1 < value2) return -1;
		if (value1 > value2) return 1;
		
		return 0;
	}
	
	// compare two hands of same type
	static int compareSameHandTypes(String hand1, String hand2, boolean allowWilds) {
		char[] h1 = hand1.toCharArray();
		char[] h2 = hand2.toCharArray();
		
		for (int i=0; i<h1.length; i++) {
			int card1value; 
			int card2value;

			if (allowWilds) {
				card1value = WILD_VALUES.get(h1[i]); 
				card2value = WILD_VALUES.get(h2[i]);
			}
			else {
				card1value = CARD_VALUES.get(h1[i]); 
				card2value = CARD_VALUES.get(h2[i]);
			}
			
			if (card1value<card2value) return -1;
			if (card1value>card2value) return 1;
		}
		
		return 0;
	}
	
	List<String> getHands() {
		return handsInput;
	}
	
	long caclulateWinnings(List<String> rankedHands) {
		long winnings = 0;
		
		for (int i=0; i<rankedHands.size(); i++) {
			long rank = i + 1;
			int bid = Integer.parseInt(rankedHands.get(i).substring(6));
			winnings += rank * bid;
		}
		
		return winnings;
	}
	
	// find best hand with wild cards
	static String determineTypeWithWilds(String hand) {
		String bestType = "HC";
		
		String wildsRemoved = hand.replaceAll("J", "");
		int numWilds = 5-wildsRemoved.length();
		
		String type = determineHandType(wildsRemoved);
		
		switch (numWilds) {
		case 4,5:
			bestType = "5K";
			break;
		case 3: 
			String twoCardType = determineHandType(wildsRemoved);
			if (twoCardType.equals("1P")) {
				bestType = "5K";
			}
			else {
				bestType = "4K";
			}
			break;
		case 2:
			String threeCardType = determineHandType(wildsRemoved);
			if (threeCardType.equals("3K")) {
				bestType = "5K";
			}
			if (threeCardType.equals("1P")) {
				bestType = "4K";
			}
			if (threeCardType.equals("HC")) {
				bestType = "3K";
			}
			break;
		case 1:
			String fourCardType = determineHandType(wildsRemoved);
			if (fourCardType.equals("4K")) {
				bestType = "5K";
			}
			if (fourCardType.equals("3K")) {
				bestType = "4K";
			}
			if (fourCardType.equals("2P")) {
				bestType = "FH";
			}
			if (fourCardType.equals("1P")) {
				bestType = "3K";
			}
			if (fourCardType.equals("HC")) {
				bestType = "1P";
			}
			break;
		default:
			bestType = determineHandType(hand);
		}
		
		return bestType;
	}
	
	public static void main(String[] args) throws IOException {
		Day7 d7 = new Day7();

		List<String> copy1 = new ArrayList<>(d7.getHands());
		Collections.sort(copy1, new HandComparator());
		System.out.println("total winnings = " + d7.caclulateWinnings(copy1));
		
		List<String> copy2 = new ArrayList<>(d7.getHands());
		Collections.sort(copy2, new HandComparatorWithWilds());
		System.out.println("total winnings with wild cards = " + d7.caclulateWinnings(copy2));
	}
	
	// using a comparator to sort a List of hands
	static class HandComparator implements Comparator<String> {
		@Override
		public int compare(String h1, String h2) {
			String hand1 = h1.substring(0,5);
			String hand2 = h2.substring(0,5);
			
			String type1 = determineHandType(hand1);
			String type2 = determineHandType(hand2);
			
			if (type1.equals(type2)) {
				int x = compareSameHandTypes(hand1, hand2, false);
				if (x==-1) return -1;
				if (x==1) return 1;
			}
			else {
				int x = compareDifferentHandTypes(type1, type2);
				if (x==-1) return -1;
				if (x==1) return 1;
			}
			
			return 0;
		}
		
	}
	
	static class HandComparatorWithWilds implements Comparator<String> {
		@Override
		public int compare(String h1, String h2) {
			String hand1 = h1.substring(0,5);
			String hand2 = h2.substring(0,5);
			String type1 = determineTypeWithWilds(hand1);
			String type2 = determineTypeWithWilds(hand2);
			
			if (type1.equals(type2)) {
				int x = compareSameHandTypes(hand1, hand2, true);
				if (x==-1) return -1;
				if (x==1) return 1;
			}
			else {
				int x = compareDifferentHandTypes(type1, type2);
				if (x==-1) return -1;
				if (x==1) return 1;
			}
			
			return 0;
		}
	}
}

