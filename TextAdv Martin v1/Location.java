
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
	private NPC[] npcs = new NPC[2];
	
	//public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3; // Plassert i Exit
	
	// Tittel og description på location'en
	public Location(String tit, String desc,boolean b)
	{
		title = tit;
		description = desc;
		light = b;
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
	
	public String getExits()
	{
		String text = "";
		int teller = 0;
		for(int i = 0; i < exits.length; i++)
		{
			if(exits[i]!=null && !exits[i].isHidden())
			{
				text += exits[i].getDirection() +"\t";
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
	
// finner et item i den liste som henger på "player"
	public Item finn(String item)
	  {
		  Item runner = first;
		  
		  while(runner!=null && runner.getName().compareToIgnoreCase(item)!=0)  
			  runner = runner.next;
		return runner;
	  }
	//finnskap
	public Chest finnChest(String item)
	  {
		  Chest runner2 = second;
		  
		  while(runner2!=null && !runner2.getName().toUpperCase().equals(item.toUpperCase()))  
			  runner2 = runner2.next;
		return runner2;
	  }
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
		  return itemNy; // returnerer navnet på det som fjernes
		}
	
// metode for å legge fra seg item, å "legge" i nytt rom	
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
	
	
	
	//Gjennomløper lista og returnerer alle boktitlene, 
	  //en på hver linje.
	  public String toStringItem()
	  {
		  String res = "\nRommet innholder:\n";
	    String resultat = "";
	    Item runner = first;
	    Chest runner2 = second;

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
	    if ( !resultat.equals( "" ) )
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
		// nÂr spilleren gÂr inn i et rom
		public void entered() 
		{
			//entered = true;	
			unlockHiddenExitsOnEnter();
		}
		
		// lÂser opp exits som kun kan bli Âpnet fra den andre siden (IM A FUcKIN' GENIUS)
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
//		metoder for å legge til og fjerne fra chest
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
		public void addNPC(String n, String d,String a, int damage,boolean b, boolean k)
		  {
			  for(int i = 0; i < npcs.length; i++)
			  {
				  if(npcs[i]==null)
				  {
					  npcs[i] = new NPC(n, d,a, damage,b,k);
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