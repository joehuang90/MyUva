package src10006;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	private static final int ceiling = 65000; 
	private static boolean[] isPrime = new boolean[ceiling];
	
	static {
		Arrays.fill(isPrime, true);
		for (int i = 2; i < ceiling; i++)
		{
			if (isPrime[i]) {
				for (int j = 2; i * j < ceiling; j++)
					isPrime[i * j] = false;
			}
		}
	}
	
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10006/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			int n = in.nextInt();
			if (n == 0)
				break;
			doTask(n);
		}
		in.close();
	}
	
	private static void doTask(int n)
	{
		if (isCarmichael(n))
			System.out.println(String.format("The number %d is a Carmichael number.", n));
		else
			System.out.println(String.format("%d is normal.", n));
	}
	
	private static boolean isCarmichael(int n)
	{
		if (isPrime[n])
			return false;
		else
		{
			for (int i = 2; i < n; i++)
			{
				if (modFormula(i, n, n) != i)
					return false;
			}
			return true;
		}
	}
	
	private static int modFormula(int a, int b, int c)
	{
		int answer = 1;
		long tmp = a;
		while (b != 0)
		{
			if ((b & 1) == 1)
				answer = (int) (answer * tmp % c);
			tmp = tmp * tmp % c;
			b >>= 1;
		}
		return answer;
	}
}
