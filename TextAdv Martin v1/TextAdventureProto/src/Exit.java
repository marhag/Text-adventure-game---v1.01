/*
 *		Exit-klassen definerer en exit, hvor den leder til og retningen dens 
 */


public class Exit 
{
	private Location leadsTo;
	private int direction;
	private boolean hidden = false;
	private boolean unlockOnEnter = false;
	private boolean locked = false;
	private boolean blocked = false;	
	private Item blockItem = null;
	private Switch bryter = null;
	
	private Item key = null;
	
	public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3, UP = 4, DOWN = 5;
	private String[] dir_text = {"NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN"};
	
	//vanlig exit
	public Exit(int dir, Location loc)
	{
		direction = dir;
		leadsTo = loc;
	}
	
	//låst fra ene siden
	public Exit(int dir, Location loc, boolean unlock)
	{
		direction = dir;
		leadsTo = loc;
		hidden = true; //kan hende en spiller lukker opp dår med fks nåkkel om "unlock" er true
		unlockOnEnter = unlock;
	}
	//blocked by item, unlocked with tool
	public Exit(int dir, Location loc, Item blockingItem, Item keyItem, boolean hid)
	{
		direction = dir;
		leadsTo = loc;
		hidden = hid;
		
		blocked = true;
		
		blockItem = blockingItem;
		key = keyItem;
	}
	//lås opp med item
	public Exit(int dir, Location loc, Item item) 
	{
		direction = dir;
		leadsTo = loc;
		locked = true;
		
		key = item;
	}
	//exit med switch - kun EN for nå...
	public Exit(int dir, Location loc, Switch s)
	{
		direction = dir;
		leadsTo = loc;
		bryter = s;
	}
	
	// Location som exit'en leder til
	public Location leadsTo()
	{
		return leadsTo;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public void unlockOnEnter()
	{
		if(unlockOnEnter)
		{
			hidden = false;
			locked = false;
		}
	}
	
	// om spiller i framtiden kanskje skal kunne låse en dår eller gruve kollapser etc.
	public void setHidden(boolean h)
	{
		hidden = h;
	}

	// Konverterer (tungvindt) direction fra int til String  :)
	public String getDirection()
	{
		return dir_text[direction];
	}

	public Item getKey()
	{
		return key;
	}
	
	public boolean keyUnlock(String item)
	{
		if(key != null && key.getName().toUpperCase().equals(item))
		{
			locked = false;
			return true;
		}
		return false;
	}
	
	public boolean isLocked()
	{
		return locked;
	}
	public Item getBlock()
	{
		return blockItem;
	}
	public boolean haveSwitch()
	{
		return !(bryter==null);
	}
	public Switch getSwitch()
	{
		return bryter;
	}

	public boolean isBlocked()
	{
		return blocked;
	}
	
	public boolean needKey()
	{
		if(key == null)
			return true;
		
		return false;
	}
	public boolean needSwitch()
	{
		if(bryter == null)
			return true;
		
		return false;
	}
	
	public boolean moveItem(String blockitem, String tool)
	{
		if(blockitem.toUpperCase().equals(blockItem.getName().toUpperCase()))
		{
			if((key == null && tool == null) || (key != null && key.getName().toUpperCase().equals(tool)))
			{
				blocked = false;
				hidden = false;
				return true;
			}
		}
		return false;
	}
	
}


/*

public class Exit 
{
	private Location leadsTo;
	private int direction;
	
	public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3, UP = 4, DOWN = 5;
	
	public Exit(int dir, Location loc)
	{
		direction = dir;
		leadsTo = loc;
	}
	
	// Location som exit'en leder til
	public Location leadsTo()
	{
		return leadsTo;
	}

	// Konverterer (tungvindt) direction fra int til String  :)
	public String getDirection()
	{
		switch(direction)
		{
			case 0: return "NORTH";
			case 1: return "SOUTH";
			case 2: return "EAST";
			case 3: return "WEST";
			case 4: return "UP";
			case 5: return "DOWN";
			default: return null;
		}
	}
}*/

/*
 *		Exit-klassen definerer en exit, hvor den leder til og retningen dens 
 


public class Exit 
{
	private Location leadsTo;
	private int direction;
	private boolean hidden = false;
	private boolean unlockOnEnter = false;
	
	public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3, UP = 4, DOWN = 5;
	private String[] dir_text = {"NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN"};
	
	public Exit(int dir, Location loc)
	{
		direction = dir;
		leadsTo = loc;
	}
	public Exit(int dir, Location loc, boolean unlock)
	{
		direction = dir;
		leadsTo = loc;
		hidden = true; //kan hende en spiller lukker opp dår med fks nåkkel om "unlock" er true
		unlockOnEnter = unlock;
	}
	
	// Location som exit'en leder til
	public Location leadsTo()
	{
		return leadsTo;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public void unlockOnEnter()
	{
		if(unlockOnEnter)
			hidden = false;
	}
	
	// om spiller i framtiden kanskje skal kunne låse en dår eller gruve kollapser etc.
	public void setHidden(boolean h)
	{
		hidden = h;
	}

	// Konverterer (tungvindt) direction fra int til String  :)
	public String getDirection()
	{
		return dir_text[direction];
	}
}



*/
/*

public class Exit 
{
	private Location leadsTo;
	private int direction;
	
	public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3, UP = 4, DOWN = 5;
	
	public Exit(int dir, Location loc)
	{
		direction = dir;
		leadsTo = loc;
	}
	
	// Location som exit'en leder til
	public Location leadsTo()
	{
		return leadsTo;
	}

	// Konverterer (tungvindt) direction fra int til String  :)
	public String getDirection()
	{
		switch(direction)
		{
			case 0: return "NORTH";
			case 1: return "SOUTH";
			case 2: return "EAST";
			case 3: return "WEST";
			case 4: return "UP";
			case 5: return "DOWN";
			default: return null;
		}
	}
}*/
