package src213;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
				System.setIn(new FileInputStream("src/src213/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			String header = in.nextLine();
			
			doTask(header, in);
		}
		in.close();
	}
	
	private static void doTask(String header, Scanner messageStream)
	{
		Map<String, String> encoding = new HashMap<String, String>();
		int length = 1;
		int counter = 0;
		
		for (int i = 0; i < header.length(); i++)
		{
			String keyFormat = String.format("%%%ds", length);
			String key = String.format(keyFormat, Integer.toBinaryString(counter)).replace(' ', '0');
			
			if (!key.contains("0"))
			{
				length++;
				counter = 0;
				i--;
			}
			else
			{
				counter++;
				encoding.put(key, header.substring(i, i + 1));
			}
		}
		
		//for (String key : encoding.keySet())
		//	System.out.println(key + ":" + encoding.get(key));
		
		int pointer = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(messageStream.nextLine());
		while (true)
		{
			if (sb.length() - pointer < 3)
				sb.append(messageStream.nextLine());
			else
			{
				int codeLength = Integer.parseInt(sb.toString().substring(pointer, pointer + 3), 2);
				
				if (codeLength == 0)
					break;
				
				pointer += 3;
				
				while (true)
				{
					if (sb.length() - pointer < codeLength)
						sb.append(messageStream.nextLine());
					else
					{
						String unit = sb.toString().substring(pointer, pointer + codeLength);
						pointer += codeLength;
						
						if (unit.contains("0"))
							System.out.print(encoding.get(unit));	
						else
							break;
					}
				}
			}
		}
		System.out.println();
	}
}
