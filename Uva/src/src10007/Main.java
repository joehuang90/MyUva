package src10007;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.math.BigInteger;

public class Main {
	
	private static BigInteger[] factorialCache = new BigInteger[301];
	private static BigInteger[] treeFrameCache = new BigInteger[301];
	private static int maxInput = 0;
	
	static {
		factorialCache[0] = BigInteger.ONE;
		treeFrameCache[0] = BigInteger.ONE;
	}
	
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10007/input"));
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
			
			if (n > maxInput)
				maxInput = n;
		}
		in.close();
	}
	
	private static void doTask(int n)
	{
		System.out.println(treeFrame(n).multiply(factorial(n)));
	}
	
	private static BigInteger factorial(int n)
	{
		if (n > maxInput && factorialCache[n] == null)
			for (int i = maxInput + 1; i <= n; i++)
				factorialCache[i] = factorialCache[i - 1].multiply(BigInteger.valueOf(i));
		
		return factorialCache[n];
	}
	
	private static BigInteger treeFrame(int n)
	{
		if (n > maxInput && treeFrameCache[n] == null)
		{
			for (int i = maxInput + 1; i <= n; i++)
			{
				treeFrameCache[i] = BigInteger.ZERO;
				for (int j = 0; j <= i - 1; j++)
					treeFrameCache[i] = treeFrameCache[i].add(treeFrameCache[j].multiply(treeFrameCache[i - j - 1]));
			}
		}
		
		return treeFrameCache[n];
	}
}
