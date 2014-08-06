
public class Note extends Item{
	String innhold;
	String head;
	
	public Note(String n,int w,String h,String b)
	{
		super(n,w);
		head = h;
		innhold = b;
	}
	
	public String getName()
	{
		return super.getName();
	}
	public int getWeight()
	{
		return super.getWeight();
	}
	public String head()
	{
		return head;
	}
	
	public String text()
	{
		return innhold;
	}
	
	public String toStringNote()
	{
		String ut ="";
		String[] deler = innhold.split(" ");
		
		ut+= head() +"\n";
		
		int teller = 0;
		for(int i = 0; i< deler.length;i++)
		{
			ut+= deler[i] +" ";
			teller++;
			if(teller == 8){
				ut+="\n";
				teller=0;
			}
		}
		
		if(deler.length==0)
			return ut + "The note is empty...";
		return ut;
	}

}
