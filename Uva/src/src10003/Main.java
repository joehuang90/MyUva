package src10003;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10003/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			int length = in.nextInt();
			if (length == 0)
				break;
			
			int numCut = in.nextInt();
			
			int[] cuts = new int[numCut];
			for (int i = 0; i < numCut; i++)
				cuts[i] = in.nextInt();
			
			doTask(length, cuts);
		}
		in.close();
	}
	
	private static void doTask(int length, int[] cuts)
	{
		MinPriceMap map = new MinPriceMap(length, cuts);
		System.out.println(String.format("The minimum cutting is %d.", map.minPrice()));
	}
	
	private static class MinPriceMap
	{
		private int[][] map;
		
		public MinPriceMap(int length, int[] cuts)
		{
			int numUnits = cuts.length + 1;
			map = new int[numUnits][];
			for (int i = 0; i < numUnits; i++)
			{
				map[i] = new int[numUnits];
				map[i][i] = 0;
			}
			
			for (int len = 1; len <= cuts.length; len++)
			{
				for (int start = 0; start + len < numUnits; start++)
				{
					int minPrice = Integer.MAX_VALUE;
					for (int cut = start; cut < start + len; cut++)
						if (minPrice(start, cut) + minPrice(cut + 1, start + len) < minPrice)
							minPrice = minPrice(start, cut) + minPrice(cut + 1, start + len);
					
					int lenStart = start == 0 ? 0 : cuts[start - 1];
					int lenEnd = start + len == numUnits - 1 ? length : cuts[start + len];
					map[start][start + len] = (lenEnd - lenStart) + minPrice;				
				}
			}
		}
		
		public int minPrice(int startUnit, int endUnit)
		{
			return map[startUnit][endUnit];
		}
		
		public int minPrice()
		{
			return minPrice(0, map.length - 1);
		}
	}
}
