package src10008;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10008/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			int n = in.nextInt();
			String[] inputs = new String[n];
			in.nextLine();
			
			for (int i = 0; i < n; i++)
				inputs[i] = in.nextLine();
			
			doTask(inputs);
		}
		in.close();
	}
	
	private static void doTask(String[] inputs)
	{
		int[] freq = new int[26];
		for (String str : inputs)
			for (char c : str.toUpperCase().toCharArray())
				if (c >= 'A' && c <= 'Z')
					freq[c - 65]++;
		
		SortedMap<Integer, List<Character>> output = new TreeMap<Integer, List<Character>>(Collections.reverseOrder());
		for (int i = 0; i < 26; i++) {
			if (freq[i] > 0) {
				if (!output.containsKey(freq[i]))
					output.put(freq[i], new ArrayList<Character>());
				output.get(freq[i]).add(Character.valueOf((char)(i + 65)));
			}
		}
		
		for (Integer i : output.keySet())
			for (Character c : output.get(i))
				System.out.println(c + " " + i);
	}
}
