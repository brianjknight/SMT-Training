package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {
	// understanding the problem
	// to get the next line of a sequence, subtract the next int from the current int
	// continue new lines until the new array is all zeroes
	// reverse the direction adding to the end of the previous array the new end of the current array
	// finally add the final value added to the original line of each sequence
	
	List<List<Integer>> reports = new ArrayList<>();
	
	// convert each line of the report to a List
	Day9() throws IOException {
		File input = new File("src/adventOfCode/resources/day-9-input.txt");
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			while ((line=reader.readLine()) != null) {
				String[] nums = line.split(" ");
				reports.add(Arrays.stream(nums).map(Integer::valueOf).toList());
			}
		}
	}
	
	
	// for each line find the number added to the sequence
	
	// create a new sequence by subtracting each pair of numbers
	List<Integer> findNextSequence(List<Integer> sequence) {
		List<Integer> nextSequence = new ArrayList<>();
		
		for (int i=0; i<sequence.size()-1; i++ ) {
			int diff = sequence.get(i+1) - sequence.get(i);
			nextSequence.add(diff);
		}
		
		return nextSequence;
	}
	
	// continue creating new sequences until all elements are 0
	List<List<Integer>> findAllSequencesUntilZeros(List<Integer> sequence) {
		List<List<Integer>> allNextSequences = new ArrayList<>();
		allNextSequences.add(sequence);
		
		boolean allZeros = false;
		List<Integer> current = sequence;
		
		while (!allZeros) { 
			List<Integer> next = findNextSequence(current);
			allNextSequences.add(next);
			allZeros = hasAllZeros(next);
			current = next;
		}
		
		return allNextSequences;
	}
	
	boolean hasAllZeros(List<Integer> sequence) {
		for (int i : sequence) {
			if (i != 0) return false;
		}
		
		return true;
	}
	
	
	// from the last sequence to first add to end of sequence
	// add the last number of the current sequence  
	// to the last number of the previous sequence, then insert that element on the end of the previous sequence
	// continue until number has been added to original sequence
	int extrapolateToEnd(List<List<Integer>> list) {
		int newEndOfSeq = 0;
		int size = list.size();
		
		List<Integer> curList;
		for (int i=size-2; i>0; i--) {
			curList = list.get(i);
			
			int lastInd = curList.size()-1;
			int lastNum = list.get(i).get(lastInd);
			
			newEndOfSeq = lastNum + newEndOfSeq;
		}
		
		List<Integer> firstList = list.get(0);
		int firstSize = firstList.size();
		
		return firstList.get(firstSize - 1) + newEndOfSeq;
	}
	
	// Part 2
	int extrapolateToBeginning(List<List<Integer>> list) {
		int newBegOfSeq = 0;
		int size = list.size();
		
		List<Integer> curList;
		for (int i=size-2; i>0; i--) {
			int firstNum = list.get(i).get(0);
			
			newBegOfSeq = firstNum - newBegOfSeq;
		}
		
		List<Integer> firstList = list.get(0);
		
		return firstList.get(0) - newBegOfSeq;
	}
	
	
	
	// the final number added goes into the running total/answer
	// param is a full report
	int findSumOfExtrapolations(List<List<Integer>> report, boolean isEnd) {
		int sum = 0;
		
		// find the list of sequences of each line in the report
		List<List<List<Integer>>> listOfSeqToZeros = new ArrayList<>();
		
		// extrapolate each sequence in the report
		for (List<Integer> line : report) {
			List<List<Integer>> listOfSequences = findAllSequencesUntilZeros(line);
			
			listOfSeqToZeros.add(listOfSequences);
		}
		
		for (List<List<Integer>> seqToZero : listOfSeqToZeros) {
			int extrapolated = isEnd ? extrapolateToEnd(seqToZero) : extrapolateToBeginning(seqToZero);
			sum += extrapolated;
		}
		
		return sum;
	}
	
	public static void main(String[] args) throws IOException {
		// test constructor
		Day9 d = new Day9();

		// test full extrapolation sum
		int sumOfExtrapEND = d.findSumOfExtrapolations(d.reports, true);
		System.out.println("sum of extrapolated values = " + sumOfExtrapEND);
		
		int sumOfExtrapBEG = d.findSumOfExtrapolations(d.reports, false);
		System.out.println("extrapolate beginning sum = " + sumOfExtrapBEG);
	}
}
