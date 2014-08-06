
public class Flame extends Item{
	private boolean lit;
//	private 
	
	public Flame(String n, int w, boolean b)
	{
		super(n,w);
		lit = b;
	}
	public String getName()
	{
		return super.getName();
	}
	public int getWeight()
	{
		return super.getWeight();
	}
	
	public boolean isLit()
	{
		return lit;
	}
	public void lightFlame(boolean b)
	{
		lit = b;
	}
}
