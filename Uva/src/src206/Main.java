package src206;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src206/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		boolean isFirst = true;
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			if (!isFirst)
				System.out.println("***********************************");
			
			String jobId = in.nextLine();
			int numRoute = in.nextInt();
			int numCustomer = in.nextInt();
			in.nextLine();
			
			List<Customer> customers = new ArrayList<Customer>();
			for (int i = 0; i < numCustomer; i++)
			{
				String name = in.nextLine();
				int x = in.nextInt();
				int y = in.nextInt();
				in.nextLine();
				
				customers.add(new Customer(x, y, name));
			}
			
			doTask(jobId, numRoute, customers);
			isFirst = false;
		}
		in.close();
	}
	
	private static class Customer
	{
		private int x, y;
		private String name;
		
		public Customer(int x, int y, String name)
		{
			this.x = x;
			this.y = y;
			this.name = name;
		}

		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}

		public String getName() {
			return name;
		}
	}
	
	private static void doTask(String id, int numRoute, List<Customer> customers)
	{
		System.out.println(id);
		System.out.format("%-32s%s\n", String.format("Number of Customers: %d", customers.size()), String.format("Number of Routes: %d", numRoute));
		System.out.println();
		
		Collections.sort(customers, new Comparator<Customer>() {
			private double e = 0.00000001;
			
			public int compare(Customer c1, Customer c2)
			{
				double tanC1 = Math.atan2(c1.getY(), c1.getX());
				if (tanC1 < 0) tanC1 += 2 * Math.PI;
				double tanC2 = Math.atan2(c2.getY(), c2.getX());
				if (tanC2 < 0) tanC2 += 2 * Math.PI;
				
				double rC1 = Math.sqrt(Math.pow(c1.getX(), 2) + Math.pow(c1.getY(), 2));
				double rC2 = Math.sqrt(Math.pow(c2.getX(), 2) + Math.pow(c2.getY(), 2));
				
				if (tanC1 - tanC2 < -e)
					return -1;
				else if (tanC1 - tanC2 > e)
					return 1;
				else {
					if (rC1 - rC2 < -e)
						return -1;
					else if (rC1 - rC2 > e)
						return 1;
					else
						return 0;
				}
			}
			
			public boolean equals(Object o)
			{
				return false;
			}
		});
		
		int batchSize = customers.size() / numRoute;
		int counter = 0;
		int totalLength = 0;
		for (int i = 0; i < numRoute; i++)
		{
			System.out.println("Route ==> " + (i + 1));
			int length = 0;
			int currentX = 0;
			int currentY = 0;
			for (int j = 0; j < ((i < customers.size() % numRoute) ? batchSize + 1 : batchSize); j++)
			{
				System.out.println("Customer: " + customers.get(counter).getName());
				length += Math.abs(customers.get(counter).getX() - currentX);
				length += Math.abs(customers.get(counter).getY() - currentY);
				currentX = customers.get(counter).getX();
				currentY = customers.get(counter).getY();
				
				counter++;
			}
			length += Math.abs(currentX);
			length += Math.abs(currentY);
			
			totalLength += length;
			System.out.println("Route Length ==> " + length);
			System.out.println();
		}
		System.out.println("Total Route Length ==> " + totalLength);
	}
}
