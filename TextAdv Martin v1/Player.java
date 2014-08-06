
public class Player {
	private Item first;
	private final static int MAXLOAD = 10;
	private static int currentLoad = 0;
	private boolean alive;
	
	public Player()
	{
		first = null;
		alive = true;
	}
	
//	sjekke om det er plass
	public boolean isRoom(int w)
	{
		return (currentLoad+w)<=MAXLOAD;
	}
	public Item getFirst()
	{
		if(first==null)
			return null;
		return first;
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
	
// finner et item i den liste som henger pŒ "player"
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
		  return itemNy; // returnerer navnet pŒ det som fjernes
		}
	
// metode for Œ legge fra seg item, Œ "legge" i nytt rom	
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
	
//	metode for Œ sjekke om han har en lykt
	public boolean haveLight()
	{
		Item runner = first;
		  
		  while(runner!=null){  
			  if(runner instanceof Light)
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
			  runner = runner.next;
			  
		  }
		return false;
	}
	
	//Gjennomrunnerlista og returnerer alle boktitlene, 
	  //en pŒ hver linje.
	  public String toString()
	  {
	    String res = "\nSpiller: \n";
	    String resultat ="";
	    Item runner = first;
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

	    if ( !resultat.equals( "" ) )
	      return res + resultat;
	    else
	      return "\nSpiller b¾rer ingen gjenstander...";
	  }
}

