
import java.util.*;

public class CollectionUtils {
	public static <T> void reverse(List<T> list)
	{
		for (int i = 0; i < list.size() / 2; i++)
		{
			T obj1 = list.get(i);
			list.add(i, list.get(list.size() - i - 1));
			list.add(list.size() - i - 1, obj1);
		}
	}
	
	public static void reverse(Object[] list)
	{
		for (int i = 0; i < list.length / 2; i++)
		{
			Object obj1 = list[i];
			list[i] = list[list.length - i - 1];
			list[list.length - i - 1] = obj1;
		}
	}
}
