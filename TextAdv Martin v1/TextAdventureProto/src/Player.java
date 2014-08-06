
public class Player {
	private Item first;
	private Chest second;
	//private final static int MAXLOAD = 10;
	private static int maxLoad = 10;
	private static int currentLoad = 0;
	private boolean alive;
	
	public Player()
	{
		first = null;
		second = null;
		alive = true;
	}
	
//	sjekke om det er plass
	public boolean isRoom(int w)
	{
		return (currentLoad+w)<=maxLoad;
	}
	public Item getFirst()
	{
		if(first==null)
			return null;
		return first;
	}
	public void moreRoom(int n)
	{
		maxLoad+=n;
	}
	public void lessRoom(int n)
	{
		maxLoad-=n;
	}
	public boolean isAlive()
	{
		return alive;
	}
	public void setAlive(boolean a)
	{
		alive = a;
	}
//	setter inn nytt item, bak
	public void addItem(Item ny)
	  {
		  if (first == null)
		  {  
			  first = ny;
			  currentLoad+=ny.getWeight();
		  	  return; 
		  }
		  
		  Item runner = first;
		  
		  while (runner.next != null){
			  runner = runner.next;
		  	}
		  runner.next = ny;
		  ny.next=null;
		  currentLoad+=runner.next.getWeight();
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
	public Item finnForChest(String item)
	  {
		  Item runner = first;
		  
		  while(runner!=null && runner.getName()==item)  
			  runner = runner.next;
		return runner;
	  }
//	fjerner item
	
	public Item fjern(String item)
		{
		  if(first == null)
			  	return null;
		  if(first.getName().equals(item))
		  {
			  Item bok = first;
			  currentLoad-=bok.getWeight();
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
		  currentLoad-=itemNy.getWeight();
		  runner.next= runner.next.next;
		  return itemNy; // returnerer navnet på det som fjernes
		}
	
// metode for å legge fra seg item, å "legge" i nytt rom	
	public Item dropItem(String item)
	{
		Item ny = finn(item);
		if(ny != null)
		{
			fjern(ny.getName());
			return ny;
		}
		return null; 
	}
// drop all

	
//metode for light
//	metode for å sjekke om han har en lykt
	public boolean haveLight()
	{
		Item runner = first;
		  
		  while(runner!=null){  
			  if(runner instanceof Light)
				  return true;
			  if(runner instanceof Torch)
				  return true;
			  runner = runner.next;
			  
		  }
		return false;
	}
	public boolean lightOn()
	{
		Item runner = first;
		  
		  while(runner!=null){  
			  if(runner instanceof Light){
				  if(((Light) runner).isOn())
				  return true;
			  }
			  else if(runner instanceof Torch){
				  if(((Torch) runner).islit())
				  return true;
			  }
			  runner = runner.next;
			  
		  }
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
	/************************************/
//	metoder for å legge til og fjerne fra chest
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
	  return itemNy; // returnerer navnet på det som fjernes
	}
	public Chest dropChest(String item)
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
	
	//Gjennomrunnerlista og returnerer alle boktitlene, 
	  //en på hver linje.
	  public String toString()
	  {
	    String res = "\nSpiller: \n";
	    String resultat ="";
	    Item runner = first;
	    Chest runner2 = second;
	    int teller = 0;
	    while ( runner != null )
	    {
	      resultat += runner.toString() + ", ";
	      runner = runner.next;
	      teller++;
	      if(teller==3)
	      {
	    	  resultat+="\n";
	    	  teller=0;
	      }
	    }
	    while(runner2!=null)
	    {
	    	
	    	resultat += "\n" + runner2.toString();
	    	runner2 = runner2.next;
	    }

	    if ( !resultat.equals( "" ) )
	      return res + resultat;
	    else
	      return "\nSpiller bårer ingen gjenstander...";
	  }
}

