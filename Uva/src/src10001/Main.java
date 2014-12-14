package src10001;

import java.io.*;
import java.util.*;

class Main {
	private static Map<Integer, Integer[]> forwardLink = new HashMap<Integer, Integer[]>();
	
	static {
		forwardLink.put(0, new Integer[] {0, 1});
		forwardLink.put(1, new Integer[] {2, 3});
		forwardLink.put(2, new Integer[] {4, 5});
		forwardLink.put(3, new Integer[] {6, 7});
		forwardLink.put(4, new Integer[] {0, 1});
		forwardLink.put(5, new Integer[] {2, 3});
		forwardLink.put(6, new Integer[] {4, 5});
		forwardLink.put(7, new Integer[] {5, 6});
	}
	
	public static void main(String args[])
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10001/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			int automata = in.nextInt();
			in.nextInt(); // numInput, no use here
			char[] state = in.next().toCharArray();
			
			doTask(automata, state);
		}
		in.close();
	}
	
	private static void doTask(int automata, char[] state)
	{
		char[] automata_array = reverse(String.format("%8s", Integer.toBinaryString(automata)).replace(' ', '0').toCharArray());
		
		Set<Integer> candidates = null;
		Set<Integer> candidates_0 = new HashSet<Integer>();
		
		boolean ans_found = false;
		while (!ans_found)
		{
			for (int i = 0; i < state.length; i++)
			{
				Set<Integer> candidates_cell = new HashSet<Integer>();
				System.out.println(i + ":(" + state[i] + ")");
				
				for (int j = 0; j < 8; j++)
					if (automata_array[j] == state[i])
						candidates_cell.add(j);
				
				System.out.print("Candidates:");
				for (Integer candidate : candidates_cell)
					System.out.print(candidate + " ");
				System.out.println();
				
				if (candidates == null)	
				{
					candidates = candidates_cell;
					candidates_0 = candidates_cell;
				}
				else if (i != 0)
				{
					candidates_cell.retainAll(forward_candidate_set(candidates));
					candidates = candidates_cell;
					
					if (candidates.size() == 0)
					{
						System.out.println("GARDEN OF EDEN");
						ans_found = true;
						break;
					}
					else if (i == state.length - 1)
					{
						candidates = forward_candidate_set(candidates);
						candidates.retainAll(candidates_0);
						if (candidates.size() == 0)
						{
							System.out.println("GARDEN OF EDEN");
							ans_found = true;
							break;
						}
					}
				}
				
				System.out.print("Remain candidates:");
				for (Integer candidate : candidates)
					System.out.print(candidate + " ");
				System.out.println();
				
				System.out.print("Forward candidates:");
				for (Integer candidate : forward_candidate_set(candidates))
					System.out.print(candidate + " ");
				System.out.println();
			}
			
			if (!ans_found && identical(candidates, candidates_0))
			{
				System.out.println("REACHABLE");
				ans_found = true;
				break;
			}
			else
				candidates_0 = candidates;
		}
	}
	
	private static Set<Integer> forward_candidate_set(Set<Integer> origin_candidates)
	{
		Set<Integer> candidates_f = new HashSet<Integer>();
		for (Integer origin_candidate : origin_candidates)
			for (Integer candidate : forwardLink.get(origin_candidate))
				candidates_f.add(candidate);
		
		return candidates_f;
	}
	
	private static boolean identical(Set<Integer> set1, Set<Integer> set2)
	{
		for (Integer item1 : set1)
			if (!set2.contains(item1))
				return false;
		if (set1.size() == set2.size())
			return true;
		else
			return false;
	}
	
	private static char[] reverse(char[] list)
	{
		for (int i = 0; i < list.length / 2; i++)
		{
			char obj1 = list[i];
			list[i] = list[list.length - i - 1];
			list[list.length - i - 1] = obj1;
		}
		
		return list;
	}
}
