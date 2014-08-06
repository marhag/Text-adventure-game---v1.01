
public class Torch extends Item{
	private boolean lit;
	
	public Torch(String n,int w,boolean b)
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
	
	public boolean islit()
	{
		return lit;
	}
	
	public void setLight(boolean o)
	{
		lit = o;
	}
}
