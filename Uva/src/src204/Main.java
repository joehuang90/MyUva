package src204;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src204/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			int b = in.nextInt();
			int a = in.nextInt();
			
			doTask(b, a);
		}
		in.close();
	}
	
	private static void doTask(int b, int a)
	{
		System.out.print(String.format("%d/%d = %d.", b, a, b / a));
		
		int[] q = new int[3001];
		int[] r = new int[3000];
		Arrays.fill(r, -1);
		
		for (int dec = 0; ; dec++)
		{
			q[dec] = b / a;
			b = b % a;
			
			if (r[b] != -1)
			{
				for (int i = 1; i <= (dec <= 50 ? dec : 50); i++)
				{
					if (i == r[b] + 1)
						System.out.print("(");
					System.out.print(q[i]);
				}
				System.out.println(dec <= 50 ? ")" : "...)");
				
				System.out.println(String.format("   %d = number of digits in repeating cycle", dec - r[b]));
				
				break;
			}
			else
				r[b] = dec;
			
			b = b * 10;
		}
		
		System.out.println();
	}
}
