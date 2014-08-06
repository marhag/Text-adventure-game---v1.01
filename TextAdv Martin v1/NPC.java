public class NPC 
{
	private String name;
	private boolean hostile;
	private String dialog;
	private String answer;
	private int hp;
	private boolean killable;
	// b¿r kanskje ha en varabel for spm og svar? 
	public NPC(String n, String d,String a, int h,boolean b, boolean k)
	{
		name = n;
		dialog = d;
		answer = a;
		hp=h;
		hostile = b;
		killable = k;
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