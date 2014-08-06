
/*
 *		Exit-klassen definerer en exit, hvor den leder til og retningen dens 
 */


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
		hidden = true; //kan hende en spiller lukker opp dør med fks nøkkel om "unlock" er true
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
	
	// om spiller i framtiden kanskje skal kunne låse en dør eller gruve kollapser etc.
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
