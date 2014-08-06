
public class Switch {
	private String label;
	private String description;
	private Exit exits;
	private Item item;
	private boolean open;
	
	public Switch(String l, String d, Exit e)
	{
		label = l;
		description = d;
		exits = e;
		open = false;
	}
	public Switch(String l, String d, Item i)
	{
		label = l;
		description = d;
		item = i;
		open = false;
	}
	public String getLabel()
	{
		return label;
	}
	public String getDescription()
	{
		return description;
	}
	public boolean isOpen()
	{
		return open;
	}
	public void turnOpen(boolean o)
	{
		open = o;
	}
}
