
/*
 *		Location-klassen definerer en location med parametre for tittel,
 *		en forklaring av og exits til location'en 
 */

public class Location 
{
	private String title;
	private String description;
	private Exit[] exits = new Exit[6]; //Antall retninger
	private Item first;
	private Chest second;
	private boolean light;
	private boolean deadNPC;
	private String deadNPCUt;
	private NPC[] npcs = new NPC[2];
	
	//public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3; // Plassert i Exit
	
	// Tittel og description p� location'en
	public Location(String tit, String desc,boolean b)
	{
		title = tit;
		description = desc;
		light = b;
		deadNPC=false;
	}
	
	// Lager ny exit (obviously)
	public void addExit(int dir, Location leadsTo)
	{
		exits[dir] = new Exit(dir,leadsTo);
	}
	public boolean getLight()
	{
		return light;
	}
	public void setLight(boolean boo)
	{
		light = boo;
	}
	public boolean isDeadNPC()
	{
		return deadNPC;
	}
	public void setBooleanDeadNPC(boolean s)
	{
		deadNPC=s;
	}
	public String getExits()
	{
		String text = "";
		int teller = 0;
		for(int i = 0; i < exits.length; i++)
		{
			if(exits[i]!=null && !exits[i].isHidden())
			{
				text += exits[i].getDirection();
				if(exits[i].isLocked()) 
					text += " (locked)";
				if(exits[i].isBlocked()) 
					text += " (blocked)";
				
				text += "\t";
				teller++;
				if(teller==2)
					text +="\n";
			}
		}
		return text;
	}
	
	
	// Henter exit via gitte direction (fra input'en)
	public Exit getExit(int dir)
	{
		return exits[dir];	
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	// Gjenstander
	public String[] getChests()
	{
		String[] chest = new String[5];
		Chest runner = second;
		int teller = 0;
		while(runner!=null)
		{
			chest[teller++] = runner.getName();
			runner = runner.next;
		}
		return chest;
	}
	
	public boolean lightOn()
	{
		Item runner = first;
		  
		  while(runner!=null){  
			  if(runner instanceof Light){
				  if(((Light) runner).isOn())
				  return true;
			  }
			  runner = runner.next;
			  
		  }
		return false;
	}
	 /********LOCKED EXITS*********/
	public void addLockedExit(int dir, Location loc, Item item) 
	{
		exits[dir] = new Exit(dir, loc, item);	
	}
	
	  
	public boolean lockedDoor(String dirtext, String item)
	{
		int dir = Command.getDirection(dirtext);
		
		if(dir != -1 && exits[dir] != null && exits[dir].keyUnlock(item))
			return true;
		
		return false;
	}
	//finn lit Flame
	public Flame finnLit()
	  {
		  Item runner = first;
		  
		  while(runner!=null)  
		  {
			  if(runner instanceof Flame && ((Flame) runner).isLit())
				  return (Flame) runner;
			  runner = runner.next;
		  }
		return null;
	  }
/**************************************************/	
//	setter inn nytt item, bak
	public void addItem(Item ny)
	  {
		  if (first == null)
		  {  first = ny;
		  	  return; 
		  }
		  
		  Item runner = first;
		  
		  while (runner.next != null){
			  runner = runner.next;
		  	}
		  runner.next = ny;
		  ny.next = null;
		  //ny.next.next = null;
		  return;  
	  }
	
// finner et item i den liste som henger p� "player"
	public Item finn(String item)
	  {
		  Item runner = first;
		  
		  while(runner!=null && runner.getName().compareToIgnoreCase(item)!=0)  
			  runner = runner.next;
		return runner;
	  }
	//finnskap
	
//	fjerner item
	
	public Item fjern(String item)
		{
		  if(first == null)
			  	return null;
		  if(first.getName().equals(item))
		  {
			  Item bok = first;
			  first = first.next;
			  return bok;
		  }
		  Item runner = first;
		  Item itemNy = null;
		  while(runner.next != null && !runner.next.getName().equals(item))
		  {
			  runner = runner.next;
		  }
		  if(runner.next == null)
			  return null;
		  else
			 itemNy = runner.next;
		  runner.next= runner.next.next;
		  return itemNy; // returnerer navnet p� det som fjernes
		}
	
// metode for � legge fra seg item, � "legge" i nytt rom	
	public Item pickUp(String item)
	{
		Item ny = finn(item);
		if(ny != null)
		{
			fjern(ny.getName());
			return ny;
		}
		return null;  
	}
	
	public void setdeadNPC(String n)
	{
		deadNPCUt= n + " is laying dead on the floor";
		
	}
	public String getDeadNPC()
	{
		return deadNPCUt;
	}
	
	
	//Gjennoml�per lista og returnerer alle boktitlene, 
	  //en p� hver linje.
	  public String toStringItem()
	  {
		String res = "\nRommet innholder:\n";
	    String resultat = "";
	    Item runner = first;
	    Chest runner2 = second;
	    if(isDeadNPC())
	    	resultat += getDeadNPC()+"\n";
	    while ( runner != null )
	    {
	      resultat += "  -  "+runner.getName() + "\n";
	      runner = runner.next;
	      
	    }
	    
	    while(runner2!=null)
	    {
	    	if(runner2.isOpen())
	    	{
	    		resultat += runner2.toString();
		    	runner2 = runner2.next;
	    	}
	    	else{
	    		resultat += " " + runner2.getName()+"\n";
	    		runner2 = runner2.next;
	    	}
	    }
	    if(isDeadNPC()){
	    	return resultat;
	    }
	    if ( !resultat.equals( "" ) && !isDeadNPC())
	      return res +resultat;
	   
	    else
	      return "";
	  }
 /******************************/
	//hidden exit del  
	  public void addHiddenExit(int dir, Location leadsTo)
		{
			exits[dir] = new Exit(dir, leadsTo, true);
		}
		// n�r spilleren g�r inn i et rom
		public void entered() 
		{
			//entered = true;	
			unlockHiddenExitsOnEnter();
		}
		
		// l�ser opp exits som kun kan bli �pnet fra den andre siden (IM A FUcKIN' GENIUS)
		public void unlockHiddenExitsOnEnter()
		{
			for(int i = 0; i < exits.length; i++)
			{
				if(exits[i] != null)
				{
					for(int j = 0; j < exits[i].leadsTo().exits.length; j++)
					{
						if(exits[i].leadsTo().exits[j] != null && exits[i].leadsTo().exits[j].leadsTo() == this)
							exits[i].leadsTo().exits[j].unlockOnEnter();
					}
				}
			}
		}
/************************************/
//		metoder for � legge til og fjerne fra chest
		public void addChest(Chest ny)
		  {
			  if (second == null)
			  {  second = ny;
			  	  return; 
			  }
			  
			  Chest runner = second;
			  
			  while (runner.next != null){
				  runner = runner.next;
			  	}
			  runner.next = ny;
			  ny.next = null;
			  //ny.next.next = null;
			  return;  
		  }
		public Chest finnChest(String item)
		  {
			  Chest runner2 = second;
			  
			  while(runner2!=null && !runner2.getName().toUpperCase().equals(item.toUpperCase()))  
				  runner2 = runner2.next;
			return runner2;
		  }
		public Chest fjernChest(String item)
		{
		  if(second == null)
			  	return null;
		  if(second.getName().equals(item))
		  {
			  Chest bok = second;
			  second = second.next;
			  return bok;
		  }
		  Chest runner = second;
		  Chest itemNy = null;
		  while(runner.next != null && !runner.next.getName().equals(item))
		  {
			  runner = runner.next;
		  }
		  if(runner.next == null)
			  return null;
		  else
			 itemNy = runner.next;
		  runner.next= runner.next.next;
		  return itemNy; // returnerer navnet p� det som fjernes
		}
		public Chest pickUpChest(String item)
		{
			Chest ny = finnChest(item);
			if(ny != null)
			{
				fjernChest(ny.getName());
				return ny;
			}
			return null;  
		}
/***************************************/
		//switch-bryter
		public void addSwitchExit(int dir, Location loc,Switch bryter) 
		{
			exits[dir] = new Exit(dir, loc, bryter);
		}
		public boolean SwitchExit()
		{
			for(int i = 0; i < exits.length; i++)
			{
				if(exits[i] != null)
				{
					if(exits[i].haveSwitch())
						return true;
				}
			}
			return false;
		}
		
/****************************************/
		//blocked
		public void addBlockedExit(int dir, Location loc, Item blockItem, Item key, boolean hidden) 
		{
			exits[dir] = new Exit(dir, loc, blockItem, key, hidden);
		}
		
		public boolean blockedExit(String blockitem, String key)
		{
			for(int i = 0; i < exits.length; i++)
			{
				if(exits[i] != null)
				{
					if(exits[i].isBlocked() && exits[i].moveItem(blockitem, key))
						return true;
				}
			}
			return false;
		}

		public String toStringBlockedExits() 
		{
			  String text = "";
			  
			  for(int i = 0; i < exits.length; i++)
			  {
				  if(exits[i] != null && exits[i].isBlocked())
				  {
					  text += "\n" + exits[i].getDirection().toLowerCase() + " is blocked by " + exits[i].getBlock().getName().toLowerCase() + "\n";
				  }
			  }
			  
			  return text;
		}
/***************************************/
		//NPC
		public boolean haveNpc()
		{
			for(int i = 0; i < npcs.length; i++)
			  {
				  if(npcs[i]!=null)
				  {
					  return true;
				  }
			  }
			return false;
		}
		public NPC getNPCFromRomm() 
		{
			if(npcs[0]!=null)
				return npcs[0];
			return null;
		}
		public void addNPC(NPC n)//String n, String d,String a, int damage,boolean b, boolean k
		  {
			  for(int i = 0; i < npcs.length; i++)
			  {
				  if(npcs[i]==null)
				  {
					  npcs[i] = n;
					  return;
				  }
			  }
		  }
		public NPC finnNPC(String name)
		{
			for(int i=0;i<npcs.length;i++)
			{
				if(npcs[i]!=null&&npcs[i].getName().toUpperCase().equals(name.toUpperCase()))
				{
					return npcs[i];
				}
			}
			return null;
		}
		public NPC finnNPCA(String a)
		{
			for(int i=0;i<npcs.length;i++)
			{
				if(npcs[i]!=null&&npcs[i].getAnswer().toUpperCase().equals(a))
				{
					return npcs[i];
				}
			}
			return null;
		}
		public String killNPC(String name,int damage)
		{
			for(int i=0;i<npcs.length;i++)
			{
				if(npcs[i]!=null&&npcs[i].getName().toUpperCase().equals(name.toUpperCase()))
				{
					if(npcs[i].getHp()<=damage)
					{
						npcs[i]=null;
						return "";
					}
					else
					{
						npcs[i].setHp((npcs[i].getHp())-damage);
						return "DEAD";
					}
				}
			}
			return name;
		}
		  
		  public String toStringNPC()
		  {
			  String text = "";
			  
			  for(int i = 0; i < npcs.length; i++)
			  {
				  if(npcs[i] != null && i != 0)
				  {
					  text += npcs[i].toString() + "\n";
				  }
				  else if(npcs[i]!=null)
					 text = "\n" + text + npcs[i].toString() + "\n\n";

			  }
			  
			  return text;
		  }


	
	
	
	
}