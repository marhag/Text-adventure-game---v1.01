
public class Recipe {
	private Item item1; 
	private Item item2;
	private Item out;
	Recipe next;

	
	public Recipe(Item n, Item m, Item u)
	{
		item1=n;
		item2=m;
		out=u;
		next=null;
	}
	
	public Item getItem1()
	{
		return item1;
	}
	public Item getItem2()
	{
		return item2;
	}
	public Item getOut()
	{
		return out;
	}

	public String toString()
	{
		return item1.getName() + ", " + item2.getName() + "= " + out.getName();
	}
	
}
