package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day5_Part1 {
	static List<Long> seedsList = new ArrayList<>();
	
	static List<long[]> seed_soil_map = new ArrayList<>();
	static List<long[]> soil_fert_map = new ArrayList<>();
	static List<long[]> fert_water_map = new ArrayList<>();
	static List<long[]> water_light_map = new ArrayList<>();
	static List<long[]> light_temp_map = new ArrayList<>();
	static List<long[]> temp_hum_map = new ArrayList<>();
	static List<long[]> hum_loc_map = new ArrayList<>();
	
	// Part 1
	void fillSeedsSimple() {
//		String[] seedsInput = {"79","14","55","13"};
		String seedsString = "432563865 39236501 1476854973 326201032 1004521373 221995697 2457503679 46909145 603710475 11439698 1242281714 12935671 2569215463 456738587 3859706369 129955069 3210146725 618372750 601583464 1413192";
		String seedsInput[] = seedsString.split(" ");

		for (String s : seedsInput) {
			seedsList.add(Long.parseLong(s));
		}
	}
		
	// Convert the input file to data
	void convertInput() throws IOException {
				
		// Get a list of arrays for the maps [src,dest,range]
		seed_soil_map = convertMap("src/adventOfCode/resources/day-5/s2s.txt");
		soil_fert_map = convertMap("src/adventOfCode/resources/day-5/s2f.txt");
		fert_water_map = convertMap("src/adventOfCode/resources/day-5/f2w.txt");
		water_light_map = convertMap("src/adventOfCode/resources/day-5/w2l.txt");
		light_temp_map = convertMap("src/adventOfCode/resources/day-5/l2t.txt");
		temp_hum_map = convertMap("src/adventOfCode/resources/day-5/t2h.txt");
		hum_loc_map = convertMap("src/adventOfCode/resources/day-5/h2l.txt");
	}
	
	// convert a map to list of arrays
	private List<long[]> convertMap(String path) throws IOException {
		File input = new File(path);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input))) {
			String line;
			List<long[]> rangeArrays = new ArrayList<>();
			
			while((line=reader.readLine()) != null) {
				String[] str = line.split(" ");
				long[] toLong = new long[3];
				toLong[0] = Long.parseLong(str[0]);
				toLong[1] = Long.parseLong(str[1]);
				toLong[2] = Long.parseLong(str[2]);
				rangeArrays.add(toLong);
			}
			return rangeArrays;
		}	
	}
	
	// Convert each line in a map to a List consisting of start and end of ranges [dest,src]
	List<List<List<Long>>> createRangesList(List<long[]> map) {
		List<List<List<Long>>> allRanges = new ArrayList<>();
		
		for (long[] line : map) {
			List<List<Long>> rangesForMap = new ArrayList<>();
			
			long range = line[2];
			
			// start and end of range added to destination list
			long destStart = line[0];
			long destEnd = destStart + range - 1;
			List<Long> destRange = Arrays.asList(destStart, destEnd);
			rangesForMap.add(destRange);
			
			// start and end of range added to source list
			long srcStart = line[1];
			long srcEnd = srcStart + range - 1;
			List<Long> srcRange = Arrays.asList(srcStart,srcEnd);
			rangesForMap.add(srcRange);
			
			allRanges.add(rangesForMap);
		}
		
		return allRanges;
	}
	
	// For the ranges of one map, find the destination number from the src number provided
	Long findNextDestination(Long src, List<List<List<Long>>> rangeList) {

		for (List<List<Long>> ranges : rangeList) {
			List<Long> destRange = ranges.get(0);
			List<Long> srcRange = ranges.get(1);
			
			// Get the start and end of the source range
			Long srcStart = srcRange.get(0);
			Long srcEnd = srcRange.get(1);
			
			// if input source is not within the range the dest number does not change
			if (src < srcStart || src > srcEnd) {
				continue;
			} 
			// else calculate the destination number the distance from dest start
			else {
				Long distFromStart = src - srcStart;
				
				return destRange.get(0) + distFromStart;
			}
		}
		
		return src;
	}
	
	// convert to the next destination number for each map until location is returned.
	Long findSmalletsLocationNew() {
		List<Long> locations = new ArrayList<>();
		
		// create list of ranges for each input map
		List<List<List<Long>>> seed_soil_rangesList = createRangesList(seed_soil_map);
		List<List<List<Long>>> soil_fert_rangesList = createRangesList(soil_fert_map);
		List<List<List<Long>>> fert_water_rangesList = createRangesList(fert_water_map);
		List<List<List<Long>>> water_light_rangesList = createRangesList(water_light_map);
		List<List<List<Long>>> light_temp_rangesList = createRangesList(light_temp_map);
		List<List<List<Long>>> temp_hum_rangesList = createRangesList(temp_hum_map);
		List<List<List<Long>>> hum_loc_rangesList = createRangesList(hum_loc_map);
		
		// step through each list of ranges to find final location
		for (Long seed : seedsList) { 
			long soil = findNextDestination(seed, seed_soil_rangesList);
			long fert = findNextDestination(soil, soil_fert_rangesList);
			long water = findNextDestination(fert, fert_water_rangesList);
			long light = findNextDestination(water, water_light_rangesList);
			long temp = findNextDestination(light, light_temp_rangesList);
			long hum = findNextDestination(temp, temp_hum_rangesList);
			long loc = findNextDestination(hum, hum_loc_rangesList);
			
			locations.add(loc);			
		}
		
		Collections.sort(locations);
		return locations.get(0);
	}
	
	public static void main(String[] args) throws IOException {
		Day5 d5 = new Day5();
		d5.fillSeedsSimple();
		d5.convertInput();
		
		Long smallestLocation = d5.findSmalletsLocationNew();
		System.out.println("smallest loc = " + smallestLocation);
	}
}
