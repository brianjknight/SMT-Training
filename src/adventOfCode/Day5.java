package adventOfCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day5 {
	static List<Long> seeds = new ArrayList<>();
	
	static List<long[]> seed_soil = new ArrayList<>();
	static List<long[]> soil_fert = new ArrayList<>();
	static List<long[]> fert_water = new ArrayList<>();
	static List<long[]> water_light = new ArrayList<>();
	static List<long[]> light_temp = new ArrayList<>();
	static List<long[]> temp_hum = new ArrayList<>();
	static List<long[]> hum_loc = new ArrayList<>();
	
	// Convert the input file to data
	void convertInput() throws IOException {
		// Get a list of seeds
//		String[] seedsInput = {"79","14","55","13"};
		String seedsString = "432563865 39236501 1476854973 326201032 1004521373 221995697 2457503679 46909145 603710475 11439698 1242281714 12935671 2569215463 456738587 3859706369 129955069 3210146725 618372750 601583464 1413192";
		String seedsInput[] = seedsString.split(" ");
		for (String s : seedsInput) {
			seeds.add(Long.parseLong(s));
		}
				
		// Get a list of arrays for the maps [src,dest,range]
		seed_soil = convertMap("src/adventOfCode/resources/day-5/s2s.txt");
		soil_fert = convertMap("src/adventOfCode/resources/day-5/s2f.txt");
		fert_water = convertMap("src/adventOfCode/resources/day-5/f2w.txt");
		water_light = convertMap("src/adventOfCode/resources/day-5/w2l.txt");
		light_temp = convertMap("src/adventOfCode/resources/day-5/l2t.txt");
		temp_hum = convertMap("src/adventOfCode/resources/day-5/t2h.txt");
		hum_loc = convertMap("src/adventOfCode/resources/day-5/h2l.txt");
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
	
	// Helper method to convert each line of a map to ranges [destRange, srcRange]
	// use list of maps instead??
	List<List<List<Long>>> createRanges(List<long[]> map) {
		List<List<List<Long>>> allRanges = new ArrayList<>();
		
		for (long[] line : map) {
			List<List<Long>> rangesForMap = new ArrayList<>();
			
			long[] destSrcLength = line;
			// for each line create 2 new lists
			long range = destSrcLength[2];
			List<Long> srcRange = new ArrayList<>();
			List<Long> destRange = new ArrayList<>();
			
			// fill lists from start (inclusive) to range
			//dest range
			long destStart = destSrcLength[0];
			for (int i=0; i<range; i++) {
				destRange.add(destStart++);
			}
			rangesForMap.add(destRange);
			
			//src range
			long srcStart = destSrcLength[1];
			for (int i=0; i<range; i++) {
				srcRange.add(srcStart++); 
			}
			rangesForMap.add(srcRange);
			
			allRanges.add(rangesForMap);
		} 
		
		return allRanges;
	}
		
	
	
	// Helper method takes in List of map ranges to calculate corresponding source to destination
	List<List<Long>> calculateSourceToDestination(List<List<List<Long>>> mapRanges, List<long[]> map) {
		List<List<Long>> sourceToDestination = new ArrayList<>();

		// create 2 lists to keep track of src numbers and dest numbers
		// src array goes from 0 to max src number
		List<Long> srcNums = new ArrayList<>();
		long maxSrc = findMaxSourceNumForMap(map);
		
		// dest array same as src for now 0 to max
		List<Long> destNums = new ArrayList<>();
		
		// fill arrays
		for (Long l=0L; l<=maxSrc; l++) {
			srcNums.add(l);
			destNums.add(l);
		}
		
		// for each set of srcRange to destRange
		for (List<List<Long>> ranges : mapRanges) {
			List<Long> destRange = ranges.get(0);
			List<Long> srcRange = ranges.get(1);

			for (int ind=0; ind<srcRange.size(); ind++) {
				// get the src element i
				long curSrcNum = srcRange.get(ind);
				int curSrcNumInt = (int) curSrcNum;
				// the element in dest array at src element value is equal to destRange element i
				destNums.set(curSrcNumInt, destRange.get(ind));
			}
		}
		// return the 2 arrays in order to lookup the src to dest between maps	
		
		sourceToDestination.add(srcNums);
		sourceToDestination.add(destNums);
		
		return sourceToDestination;
	}
	
	List<Long> maxSrcNumbersList = new ArrayList<>();
	
	// helper to find max for a map
	long findMaxSourceNumForMap(List<long[]> map) {
		long max = 0L;
		
		for (long[] line : map) {
			long src = line[1];
			long curMax = src + line[2] - 1;
			
			max = Math.max(curMax, max);
		}
		
		return max;
	}
		
	// run conversion for each map to map to get a list of corresponding src to dest arrays.
	long findSmallestLocation() {
		// keep list of location numbers
		List<Long> locationsList = new ArrayList<>();
		
		List<List<List<Long>>> seed_soil_rangesList = createRanges(seed_soil);
		List<List<List<Long>>> soil_fert_rangesList = createRanges(soil_fert);
		List<List<List<Long>>> fert_water_rangesList = createRanges(fert_water);
		List<List<List<Long>>> water_light_rangesList = createRanges(water_light);
		List<List<List<Long>>> light_temp_rangesList = createRanges(light_temp);
		List<List<List<Long>>> temp_hum_rangesList = createRanges(temp_hum);
		List<List<List<Long>>> hum_loc_rangesList = createRanges(hum_loc);
		
		List<List<Long>> seed_soil_srcToDest = calculateSourceToDestination(seed_soil_rangesList,seed_soil);
		List<List<Long>> soil_fert_srcToDest = calculateSourceToDestination(soil_fert_rangesList,soil_fert);
		List<List<Long>> fert_water_srcToDest = calculateSourceToDestination(fert_water_rangesList,fert_water);
		List<List<Long>> water_light_srcToDest = calculateSourceToDestination(water_light_rangesList,water_light);
		List<List<Long>> light_temp_srcToDest = calculateSourceToDestination(light_temp_rangesList,light_temp);
		List<List<Long>> temp_hum_srcToDest = calculateSourceToDestination(temp_hum_rangesList,temp_hum);
		List<List<Long>> hum_loc_srcToDest = calculateSourceToDestination(hum_loc_rangesList,hum_loc);
		
		
		for (Long seed : seeds) { 
			System.out.println("seed #" + seed);
			// for each seed number, step through the source to destination conversions to get the location number
			long soil = findDestination(seed, seed_soil_srcToDest);
			
			long fert = findDestination(soil, soil_fert_srcToDest);
			long water = findDestination(fert, fert_water_srcToDest);
			long light = findDestination(water, water_light_srcToDest);
			long temp = findDestination(light, light_temp_srcToDest);
			long hum = findDestination(temp, temp_hum_srcToDest);
			long loc = findDestination(hum, hum_loc_srcToDest);
			
			locationsList.add(loc);
		}
				
		// sort and return first location
		Collections.sort(locationsList);
		return locationsList.get(0);
	}
	
	long findDestination(long src, List<List<Long>> conversionList) {
		List<Long> destList = conversionList.get(1);
		
		if (src > destList.size()) {
			return src;
		}
		
		return destList.get((int) src);
	}

	public static void main(String[] args) throws IOException {
		Day5 d5 = new Day5();
		d5.convertInput();
		
		long smallestLocation = d5.findSmallestLocation();
		System.out.println("smallest location = " + smallestLocation);
		
	}
	
}
