
public class Light extends Item{
	private boolean on;
	
	public Light(String n,int w,boolean b)
	{
		super(n,w);
		on = b;
	}
	
	public String getName()
	{
		return super.getName();
	}
	public int getWeight()
	{
		return super.getWeight();
	}
	
	public boolean isOn()
	{
		return on;
	}
	
	public void setLight(boolean o)
	{
		on = o;
	}

}
