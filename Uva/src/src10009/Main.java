package src10009;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10009/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			int nCase = in.nextInt();
			
			for (int i = 0; i < nCase; i++)
			{
				int nRoads = in.nextInt();
				int nQueries = in.nextInt();
				in.nextLine();
				
				String[][] roads = new String[nRoads][];
				String[][] queries = new String[nQueries][];
				
				for (int j = 0; j < nRoads; j++)
				{
					String[] tokens = in.nextLine().split(" ");
					roads[j] = new String[] {tokens[0], tokens[1]};
				}
				
				for (int j = 0; j < nQueries; j++)
				{
					String[] tokens = in.nextLine().split(" ");
					queries[j] = new String[] {tokens[0], tokens[1]};
				}
					
				doTask(roads, queries);
				if (i != nCase - 1)
					System.out.println();
			}
		}
		in.close();
	}
	
	private static void doTask(String[][] roads, String[][] queries)
	{
		Map<String, String> roadFromRome = buildMap(roads);
		
		for (String[] query : queries)
		{
			String roadFrom = roadFromRome.get(query[0]);
			String roadTo = roadFromRome.get(query[1]);
			
			int i = 1;
			for (; i < Math.min(roadFrom.length(), roadTo.length()); i++)
				if (roadFrom.charAt(i) != roadTo.charAt(i))
					break;
			
			StringBuilder sb = new StringBuilder(roadFrom.substring(i - 1)).reverse();
			sb.append(roadTo.substring(i));
			
			System.out.println(sb.toString());
		}
	}
	
	private static Map<String, String> buildMap(String[][] roads)
	{
		List<String[]> roadList = sortRoad(Arrays.asList(roads));
		
		Map<String, String> roadFromRome = new HashMap<String, String>();
		roadFromRome.put("Rome", "R");
		
		for (String[] road : roadList)
			roadFromRome.put(road[1], roadFromRome.get(road[0]).concat(road[1].substring(0, 1)));
		
		return roadFromRome;
	}
	
	private static List<String[]> sortRoad(List<String[]> roads)
	{
		List<String[]> result = new ArrayList<String[]>();
		List<String[]> roadsCopy = new ArrayList<String[]>(roads);
		List<String> visited = new ArrayList<String>();
		visited.add("Rome");
		
		for (int i = 0; i < roadsCopy.size(); i++)
		{
			String[] road = roadsCopy.get(i);
			
			if (visited.contains(road[0])) {
				visited.add(road[1]);
				result.add(road);
			}
			else 
				roadsCopy.add(road);
		}
		
		return result;
	}
}
