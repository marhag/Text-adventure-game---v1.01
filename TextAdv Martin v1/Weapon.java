
public class Weapon extends Item{
	
	private int power;
	
	public Weapon(String n, int w,int p)
	{
		super(n,w);
		power = p;
		
	}
	public String getName()
	{
		return super.getName();
	}
	public int getWeight()
	{
		return super.getWeight();
	}
	public int getPower()
	{
		return power;
	}
	
	public String toString()
	{
		return super.getName() ;// super.getWeight();
	}

}
