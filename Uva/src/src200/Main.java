package src200;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
	public static void main(String[] argv)
	{
		if (System.getProperty("Debug") != null)
		{
			try {
				System.setIn(new FileInputStream("src/src200/input"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Scanner in = new Scanner(System.in);
		
		List<String> inputs = new ArrayList<String>();
		while (in.hasNext())
		{
			String buf = in.next();
			if (buf.equals("#")) {
				doTask(inputs);
				break;
			}
			else
				inputs.add(buf);
		}
		in.close();
	}
	
	private static void doTask(List<String> inputs)
	{
		Graph<String> g = new Graph<String>();
		String prev = null; 
				
		for (String next : inputs)
		{
			if (prev == null)
				g.addNode(next.substring(0, 1));
			else
			{
				for (int i = 0; i < next.length(); i++)
				{
					if (i == prev.length())
						break;
					else if (prev.charAt(i) == next.charAt(i))
						continue;
					else {
						g.addNode(prev.substring(i, i + 1));
						g.addNode(next.substring(i, i + 1));
						g.addEdge(prev.substring(i, i + 1), next.substring(i, i + 1));
						break;
					}
				}
			}
			prev = next;
		}
		
		for (String label : g.topoSort())
			System.out.print(label);
		System.out.println();
	}
	
	public static class Graph<T> {
		private Set<Vertex<T>> nodes;
		private Set<Edge<T>> edges;
		
		public Graph()
		{
			nodes = new HashSet<Vertex<T>>();
			edges = new HashSet<Edge<T>>();
		}
		
		public boolean addNode(T vertexId)
		{
			if (existVertex(vertexId))
				return false;
			else {
				nodes.add(new SimpleVertex<T>(vertexId));
				return true;
			}
		}
		
		public boolean existVertex(T vertexId)
		{
			for (Vertex<T> n : nodes)
				if (n.getId().equals(vertexId))
					return true;
			return false;
		}
		
		public boolean addEdge(T src, T dst)
		{
			if (!existVertex(src) || !existVertex(dst) || existEdge(src, dst))
				return false;
			else {
				edges.add(new SimpleEdge<T>(src, dst));
				return true;
			}
		}
		
		public boolean existEdge(T src, T dst)
		{
			for (Edge<T> e : edges)
				if (e.getSrcId().equals(src) && e.getDstId().equals(dst))
					return true;
			return false;
		}
		
		public Set<T> getVertexId()
		{
			Set<T> result = new HashSet<T>();
			for (Vertex<T> v : nodes)
				result.add(v.getId());
			return result;
		}
		
		public Set<T> getSourcesId()
		{
			Set<T> result = getVertexId();
			for (Edge<T> e : edges)
				result.remove(e.getDstId());
			return result;
		}
		
		public Set<T> getDstId(T src)
		{
			Set<T> result = new HashSet<T>();
			for (Edge<T> e : edges)
				if (e.getSrcId().equals(src))
					result.add(e.getDstId());
			return result;
		}
		
		public Set<T> getSrcId(T src)
		{
			Set<T> result = new HashSet<T>();
			for (Edge<T> e : edges)
				if (e.getDstId().equals(src))
					result.add(e.getSrcId());
			
			return result;
		}
		
		public List<T> topoSort()
		{
			List<T> result = new ArrayList<T>();
			Set<Edge<T>> edgesRemoved = new HashSet<Edge<T>>(); 
			Queue<T> toExpand = new LinkedList<T>(getSourcesId());
			while (!toExpand.isEmpty())
			{
				T target = toExpand.remove();
				result.add(target);
				for (Edge<T> e : edges)
				{
					if (e.getSrcId().equals(target))
					{
						edgesRemoved.add(e);
						if (getSrcId(e.getDstId()).size() == 1)
							toExpand.add(e.getDstId());
					}
				}
				edges.removeAll(edgesRemoved);
			}
			
			edges.addAll(edgesRemoved);
			return result;
		}
		
		public static interface Vertex<T>
		{
			public T getId();
		}
		
		public static class SimpleVertex<T> implements Vertex<T>
		{
			private T id;
			
			public SimpleVertex(T id)
			{
				this.id = id;
			}
			
			public T getId()
			{
				return this.id;
			}
		}
		
		public static interface Edge<T>
		{
			public T getSrcId();
			public T getDstId();
		}
		
		public static class SimpleEdge<T> implements Edge<T>
		{
			private T srcId;
			private T dstId;
			
			public SimpleEdge(T src, T dst)
			{
				this.srcId = src;
				this.dstId = dst;
			}
			
			public T getSrcId()
			{
				return this.srcId;
			}
			
			public T getDstId()
			{
				return this.dstId;
			}
		}
	}

}
