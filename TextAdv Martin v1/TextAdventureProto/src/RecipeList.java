
public class RecipeList {
	private Recipe first;
	
	public RecipeList()
	{
		first = null;
	}
	public void addRecipe(Recipe ny)
	{
		if (first == null)
		{  
			first = ny;
			return; 
		}
		Recipe runner = first;
		  
		  while (runner.next != null){
			  runner = runner.next;
		  	}
		  runner.next = ny;
		  return; 
	}
	public Item checkRecipe(Item item1, Item item2)
	{
		Recipe runner = first;
		if(runner == null)
			return null;
		while(runner!=null)
		{	
			if(item1.getName().equals(runner.getItem1().getName())&&
			   item2.getName().equals(runner.getItem2().getName()) || 
			   item1.getName().equals(runner.getItem2().getName())&&
			   item2.getName().equals(runner.getItem1().getName()))
				return runner.getOut();
			runner=runner.next;
		}
		return null;	
	}
}
