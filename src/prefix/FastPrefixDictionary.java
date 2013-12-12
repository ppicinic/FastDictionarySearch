package prefix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * FastPrefix Dictionary calculates the sum of all values that start with a given prefix
 * This solution involves saving all possible prefixes and calculating the sum while 
 * building the HashMap data Structure
 * The Con of the Solution is that build time is much slower than the linear Naive approach
 * and size is even larger, however the pros are that this solution is then capable of
 * using HashMap's constant time get, to get sum in constant time.
 * @author Phil Picinic
 *
 */
public class FastPrefixDictionary implements PrefixDictionary {

	private HashMap<String, Long> map;

	/**
	 * Constructor Builds up a HashMap of all existing prefixes and their possible values
	 * @param filename the filename supplied
	 */
	public FastPrefixDictionary(String filename) {
		map = new HashMap<String, Long>();
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			String line;
			String[] lineList;
			while ((line = file.readLine()) != null) {
				lineList = line.split(",");
				String str = lineList[0].trim();
				String str2 = "";
				for(int i = 0; i < str.length(); i++){
					str2 += str.charAt(i);
					if(map.containsKey(str2)){
						map.put(str2, map.get(str2) + Integer.parseInt(lineList[1].trim()));
					}else{
						map.put(str2, (long)Integer.parseInt(lineList[1].trim()));
					}
				}
			}
			file.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Gets the value of the prefix
	 * Time is constant as sum is calculated on build up
	 * @param prefix the prefix given
	 * @return long the sum of the prefix
	 */
	@Override
	public long sum(String prefix) {
		if(map.containsKey(prefix.trim())){
			return map.get(prefix.trim());
		}
		return 0;
	}

}