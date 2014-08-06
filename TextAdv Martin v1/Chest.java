
public class Chest {
	private String name;
	private final static int MAXLOAD = 50;
	private static int currentLoad = 0;
	private boolean locked;
	
	Chest next;
	private Item first;
	
	public Chest(String n,boolean b)
	{
		name = n;
		locked = b;
		next = null;
	}
	public String getName()
	{
		return name;
	}
	public boolean isOpen()
	{
		return locked;
	}
	public void setBlocked(boolean b)
	{
		locked = b;
	}
//	sjekke om det er plass
	public boolean isRoom(int w)
	{
		return (currentLoad+w)<=MAXLOAD;
	}
/*
	public String toString()
	{
		return name + ": vekt = " + weight; 
	}*/
/******************/
//	register for de itemsa som skal v¾re i "chest"
//	setter inn nytt item, bak
	public void addItem(Item ny)
	  {
		  if (first == null)
		  {  first = ny;
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
	
// finner et item i den liste som henger pŒ "player"
	public Item finn(String item)
	  {
		  Item runner = first;
		  
		  while(runner!=null && !runner.getName().toUpperCase().equals(item.toUpperCase())/*runner.getName().compareToIgnoreCase(item)!=0*/)  
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
	  return itemNy; // returnerer navnet pŒ det som fjernes
	}
	
// metode for Œ legge fra seg item, Œ "legge" i spiller	
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
	//Gjennomrunner lista og returnerer alle boktitlene, 
	  //en pŒ hver linje.
	  public String toString()
	  {
	    String resultat ="";
	    String res ="\n  "+getName()+" \n";
	    Item runner = first;

	    while ( runner != null )
	    {
	      resultat += "    -  " + runner.getName() + "\n";
	      runner = runner.next;
	    }

	    if ( !resultat.equals( "" ) )
	      return res + resultat;
	    else
	      return "\n" + getName() +" er tomt";
	  }
	
}
