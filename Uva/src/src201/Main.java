package src201;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src201/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		int counter = 1;
		
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			if (counter > 1)
			{
				System.out.println();
				System.out.println("**********************************");
				System.out.println();
			}
			
			System.out.println("Problem #" + counter);
			System.out.println();
			
			int gridSize = in.nextInt();
			int edgeNum = in.nextInt();
			in.nextLine();
			
			Set<Edge> hEdges = new HashSet<Edge>();
			Set<Edge> vEdges = new HashSet<Edge>();
			
			for (int i = 0; i < edgeNum; i++)
			{
				String[] edgeDesc = in.nextLine().split(" ");
				
				if (edgeDesc[0].equals("H"))
					hEdges.add(new Edge(Integer.parseInt(edgeDesc[2]), Integer.parseInt(edgeDesc[1]), edgeDesc[0]));
				else if (edgeDesc[0].equals("V"))
					vEdges.add(new Edge(Integer.parseInt(edgeDesc[1]), Integer.parseInt(edgeDesc[2]), edgeDesc[0]));
			}
			
			doTask(gridSize, hEdges, vEdges);
			counter++;
		}
		in.close();
	}
	
	private static class Edge
	{
		private int x, y;
		private String direction;
		
		public Edge(int x, int y, String direction)
		{
			this.x = x;
			this.y = y;
			this.direction = direction;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String getDirection() {
			return direction;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o == this)
				return true;
			else if (!(o instanceof Edge))
				return false;
			else {
				Edge e = (Edge)o;
				return this.x == e.getX() && this.y == e.getY() && this.direction.equals(e.getDirection());
			}
		}
		
		@Override
		public int hashCode()
		{
			int result = 17;
			result = 37 * result + this.x;
			result = 37 * result + this.y;
			result = 37 * result + this.direction.hashCode();
			return result;
		}
	}
	
	private static class Square
	{
		private int x, y;
		private int length;
		
		public Square(int x, int y, int length)
		{
			this.x = x;
			this.y = y;
			this.length = length;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getLength() {
			return length;
		}
	}
	
	private static void doTask(int gridSize, Set<Edge> hEdges, Set<Edge> vEdges)
	{		
		List<Square> candidates = new ArrayList<Square>();
		for (int length = 1; length < gridSize; length++)
			for (int i = 1; i <= gridSize - length; i++)
				for (int j = 1; j <= gridSize - length; j++)
					candidates.add(new Square(i, j, length));
		
		Set<Edge> absentHEdges = new HashSet<Edge>();
		for (int x = 1; x <= gridSize - 1; x++)
			for (int y = 1; y <= gridSize; y++)
				if (!hEdges.contains(new Edge(x, y, "H")))
					absentHEdges.add(new Edge(x, y, "H"));
		
		for (Edge hEdge : absentHEdges)
		{
			//System.out.println("H->" + hEdge.getX() + ", " + hEdge.getY());
			
			List<Square> toRemove = new ArrayList<Square>();
			for (Square square : candidates)
			{
				if ((hEdge.getY() == square.getY() || hEdge.getY() == square.getY() + square.getLength()) &&
					(hEdge.getX() >= square.getX() && hEdge.getX() <= square.getX() + square.getLength() - 1))
				{
					//System.out.println("Remove " + square.getX() + ", " + square.getY() + ", size" + square.getLength());
					toRemove.add(square);
				}
			}
			candidates.removeAll(toRemove);
		}
		
		Set<Edge> absentVEdges = new HashSet<Edge>();
		for (int x = 1; x <= gridSize; x++)
			for (int y = 1; y <= gridSize - 1; y++)
				if (!vEdges.contains(new Edge(x, y, "V")))
					absentVEdges.add(new Edge(x, y, "V"));
		
		for (Edge vEdge : absentVEdges)
		{
			//System.out.println("V->" + vEdge.getX() + ", " + vEdge.getY());
			
			List<Square> toRemove = new ArrayList<Square>();
			for (Square square : candidates)
			{
				if ((vEdge.getX() == square.getX() || vEdge.getX() == square.getX() + square.getLength()) &&
					(vEdge.getY() >= square.getY() && vEdge.getY() <= square.getY() + square.getLength() - 1))
				{
					//System.out.println("Remove " + square.getX() + ", " + square.getY() + ", size" + square.getLength());
					toRemove.add(square);
				}
			}
			candidates.removeAll(toRemove);
		}
		
		if (candidates.size() == 0)
			System.out.println("No completed squares can be found.");
		else
		{
			int[] sizeCounter = new int[gridSize];
			for (Square square : candidates)
				sizeCounter[square.getLength()]++;
			
			for (int i = 1; i < gridSize; i++)
				if (sizeCounter[i] > 0)
					System.out.println(sizeCounter[i] + " square (s) of size " + i);
		}
	}
}
