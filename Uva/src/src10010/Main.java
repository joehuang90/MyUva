package src10010;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src10010/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		int nCase = in.nextInt();
		
		for (int i = 0; i < nCase; i++)
		{
			in.nextLine();
		
			int nLines = in.nextInt();
			int width = in.nextInt();
			
			in.nextLine();
			
			String[] inputs = new String[nLines];
			for (int j = 0; j < nLines; j++)
				inputs[j] = in.nextLine().toLowerCase();
			
			int nQueries = in.nextInt();
			
			in.nextLine();
			
			String[] keys = new String[nQueries];
			for (int j = 0; j < nQueries; j++)
				keys[j] = in.nextLine().toLowerCase();
			
			doTask(inputs, keys);
			
			if (i != nCase - 1)
				System.out.println();
		}
		
		in.close();
	}
	
	private static void doTask(String[] matrix, String[] queries)
	{
		for (String query : queries)
		{
			boolean found = false;
			for (int y = 0; y < matrix.length; y++) {
				for (int x = 0; x < matrix[0].length(); x++) {
					if (findString(matrix, query, y, x)) {
						System.out.println(String.format("%d %d", y + 1, x + 1));
						found = true;
						break;
					}
				}
				if (found) break;
			}
		}
	}
	
	private static boolean findString(String[] matrix, String key, int y, int x)
	{
		return findStringUp(matrix, key, y, x) ||
			   findStringDown(matrix, key, y, x) ||
			   findStringLeft(matrix, key, y, x) ||
			   findStringRight(matrix, key, y, x) ||
			   findStringLeftUp(matrix, key, y, x) ||
			   findStringRightUp(matrix, key, y, x) ||
			   findStringLeftDown(matrix, key, y, x) ||
			   findStringRightDown(matrix, key, y, x);
	}
	
	private static boolean findStringUp(String[] matrix, String key, int y, int x)
	{
		if (y + 1 < key.length())
			return false;
		else {
			for (int i = y; y - i < key.length(); i--)
				if (key.charAt(y - i) != matrix[i].charAt(x))
					return false;
			return true;
		}
	}
	
	private static boolean findStringDown(String[] matrix, String key, int y, int x)
	{
		if (matrix.length - y < key.length())
			return false;
		else {
			for (int i = y; i - y < key.length() ; i++)
				if (key.charAt(i - y) != matrix[i].charAt(x))
					return false;
			return true;
		}
	}
	
	private static boolean findStringLeft(String[] matrix, String key, int y, int x)
	{
		if (x + 1 < key.length())
			return false;
		else {
			for (int i = x; x - i < key.length(); i--)
				if (key.charAt(x - i) != matrix[y].charAt(i))
					return false;
			return true;
		}
	}
	
	private static boolean findStringRight(String[] matrix, String key, int y, int x)
	{
		if (matrix[0].length() - x < key.length())
			return false;
		else {
			for (int i = x; i - x < key.length(); i++)
				if (key.charAt(i - x) != matrix[y].charAt(i))
					return false;
			return true;
		}
	}
	
	private static boolean findStringLeftUp(String[] matrix, String key, int y, int x)
	{
		if (y + 1 < key.length() || x + 1 < key.length())
			return false;
		else {
			for (int i = y; y - i < key.length(); i--)
				if (key.charAt(y - i) != matrix[i].charAt(x - y + i))
					return false;
			return true;
		}
	}
	
	private static boolean findStringRightUp(String[] matrix, String key, int y, int x)
	{
		if (y + 1 < key.length() || matrix[0].length() - x < key.length())
			return false;
		else {
			for (int i = y; y - i < key.length(); i--)
				if (key.charAt(y - i) != matrix[i].charAt(x + y - i))
					return false;
			return true;
		}
	}
	
	private static boolean findStringLeftDown(String[] matrix, String key, int y, int x)
	{
		if (matrix.length - y < key.length() || x + 1 < key.length())
			return false;
		else {
			for (int i = y; i - y < key.length(); i++)
				if (key.charAt(i - y) != matrix[i].charAt(x + y - i))
					return false;
			return true;
		}
	}
	
	private static boolean findStringRightDown(String[] matrix, String key, int y, int x)
	{
		if (matrix.length - y < key.length() || matrix[0].length() - x < key.length())
			return false;
		else {
			for (int i = y; i - y < key.length(); i++)
				if (key.charAt(i - y) != matrix[i].charAt(x - y + i))
					return false;
			return true;
		}
	}
}
