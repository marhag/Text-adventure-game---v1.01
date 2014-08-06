public class NPC 
{
	private String name;
	private boolean hostile;
	private String dialog;
	private String answer;
	private int hp;
	private boolean killable;
	
	private Item first;
	private Event second;
	// b�r kanskje ha en varabel for spm og svar? 
	public NPC(String n, String d,String a, int h,boolean b, boolean k)
	{
		name = n;
		dialog = d;
		answer = a;
		hp=h;
		hostile = b;
		killable = k;
		first = null;
	}
	
	public boolean isHostile()
	{
		return hostile;
	}
	public boolean isKillable()
	{
		return killable;
	}
	public boolean hasQuestion()
	{
		if(answer==null)
			return false;
		return true;
	}
	public String getName()
	{
		return name;
	}
	public String getAnswer()
	{
		return answer;
	}
	public void setHostile(boolean inn)
	{
		hostile = inn;
	}
	public void setDialog(String inn)
	{
		dialog = inn;
	}
	public void setAnswer(String inn)
	{
		answer = inn;
	}
	public void setKillable(boolean inn)
	{
		killable = inn;
	}
	public void setHp(int inn)
	{
		hp = inn;
	}
	public int getHp()
	{
		return hp;
	}
	public void addItem(Item ny)
	  {
		  if (first == null)
		  {  
			  first = ny;
		  	  return; 
		  }
		  
		  Item runner = first;
		  
		  while (runner.next != null){
			  runner = runner.next;
		  	}
		  runner.next = ny;
		  ny.next=null;
		  return;
	  }
	public Item finn()
	  {
		  Item runner = first;
		  
		  //if(runner==null)
			//  return null;
		return runner;
	  }
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
	  return itemNy; // returnerer  det som fjernes
	}
	/*******EVENT********/
	public void addEvent(Event ev)
	{
		  if (second == null)
		  {  
			  second = ev;
		  	  return; 
		  }
		  
		  Event runner = second;
		  
		  while (runner.next != null){
			  runner = runner.next;
		  	}
		  runner.next = ev;
		  ev.next=null;
		  return;
	}
	
	public Event findEvent()
	  {
		  Event runner = second;
		  
		  //if(runner==null)
			//  return null;
		return runner;
	  }
	
	public Item eventHandler(Item item)
	{
		if(item == null)
			return null;
					
		while(second.next != null && second.getPlayerReciveItem(item) == null)
		{
			second = second.next;
		}
			
		return second.getPlayerReciveItem(item);
	}
	/*****TOSTRING()***/
	public String toString()
	{
		String text = "";
		
		if(hostile)
			return name + ": (ser aggresiv ut)\n"+dialog;
		
		text = name + ":\n" + dialog;
		
		if(text.equals(""))
			return "";
		return text;
	}
	
}