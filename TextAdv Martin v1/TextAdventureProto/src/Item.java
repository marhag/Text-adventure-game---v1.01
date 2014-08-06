
public class Item {
	private String name;
	private int weight; 
	
	Item next;
	public Item(String n,int w)//, int w
	{
		name = n;
		weight = w;
		next = null;
	}
	
	public String getName()
	{
		return name;
	}
	public int getWeight()
	{
		return weight;
	}
	public String toString()
	{
		return name /*+ ": vekt = " + weight*/; 
	}
	

}
