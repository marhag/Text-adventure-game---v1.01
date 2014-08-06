public class Event 
{
	private Item playerGive;
	private Item playerRecive;
	
	Event next;
	
	public Event(Item g, Item r)
	{
		playerGive = g;
		playerRecive = r;
	}
	
	public Item getPlayerReciveItem(Item pg)
	{
		if(pg == playerGive)
			return playerRecive;
		
		return null;
	}
}
