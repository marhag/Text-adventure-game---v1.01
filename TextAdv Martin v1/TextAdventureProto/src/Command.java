
/*
 * 		Command-klassen holder styr på kommandoer,hvor spilleren er
 * 		og input
 */


public class Command
{
	private static Location currentLocation; // Holder styr på hvor playeren er
	private static int attempts = 0;
	private static int cheats = 0;
	private static int dark= 0;
	private static int lit = 0;
	private static int torchLight = 0; // hvis torch er på er denne 1
	private static Location flameLoc =null;
	
	
	public static String titleLoop(Player spiller)
	{
		if(currentLocation.getLight())
		{	
			return title();
		}
		else if((spiller.haveLight() && spiller.lightOn())||currentLocation.lightOn())
		{
			return title();
		}
		return currentLocation.getTitle();
	}
	public static String title()
	{
		String utskrift = "";
		utskrift +=currentLocation.getTitle() +"\n\n" +
					"Exits:\n" +
					currentLocation.getExits();
		return utskrift;
	}

	// Printer ut info om currentLocation, og henter input
	public static String mainLoop(String input, Player spiller)
	{
		boolean ok = false;
		if(input!=null){
			ok = command(input.toUpperCase());
			setCurrentLocation(getCurrentLoc());
			currentLocation.entered();    // NY METODE
		}
		if(input.equals(""))
			return "";
		if(!ok && currentLocation.getExit(getDirection(input)) != null)
		{
			Exit exit = currentLocation.getExit(getDirection(input));
			
			if(exit.isLocked())
				return "\nExit is locked!";
			else if(exit.isBlocked())
				return "\n" + exit.getBlock().getName() + " is blocking exit!";
		}
		else if(!ok)
		{
			return "\nNo open exits that way!";
		}
		if(currentLocation.getLight())
		{
			dark=0;
			return desc();
		}
		else if(spiller.haveLight() && spiller.lightOn())
		{
			dark=0;
			return desc();
		}
		if(dark==1)
		{
			spiller.setAlive(false);
			return "You didnt turn on the light and something killed you!";
		}
		dark++;
		return "\nit's pitch black, you are likely to be eaten by a grue!\n";

	}
	public static String desc()
	{
		String utskrift = "";
		utskrift += "\n" + currentLocation.getDescription();
		utskrift += currentLocation.toStringBlockedExits();
		utskrift += "\n" + currentLocation.toStringItem();
		utskrift += currentLocation.toStringNPC();
		return utskrift;
	}
	public static String itemToPlayer(String name,Player spiller)
	{
		String utskrift = "";
		playerAdd(name,spiller);
		utskrift += "you picked up";
		return utskrift;
		
	}
	public static String itemToLocation(String name,Player spiller)
	{
		String utskrift = "";
		locationAdd(name,spiller);
		utskrift += currentLocation.getTitle();
		return utskrift;
		
	}
	public static boolean command(String input)
	{
		// Sjekker om input er gyldig, og bytter rom
		int dir = getDirection(input);
		
		if(dir != -1 && currentLocation.getExit(dir) != null)
		{
			Exit exit = currentLocation.getExit(dir);
			if(exit.leadsTo() != null && !exit.isHidden() && !exit.isLocked() && !exit.isBlocked())
			{
				setCurrentLocation(currentLocation.getExit(dir).leadsTo());
				return true;
			}
		}
		return false;
	}
	
	public static int getDirection(String input)
	{
		if(input.equals("NORTH") || input.equals("N"))
			return Exit.NORTH;
		else if(input.equals("SOUTH") || input.equals("S"))
			return Exit.SOUTH;
		else if(input.equals("WEST") || input.equals("W"))
			return Exit.WEST;
		else if(input.equals("EAST") || input.equals("E"))
			return Exit.EAST;
		else if(input.equals("UP") || input.equals("U"))
			return Exit.UP;
		else if(input.equals("DOWN") || input.equals("D"))
			return Exit.DOWN;
		
		return -1;
	}
	

	public static void setCurrentLocation(Location loc)
	{
		currentLocation = loc;
	}
	// til vindu, om det er en retining
	
	public static Location getCurrentLoc()
	{
		return currentLocation;
	}
	
	// metode for at player skal plukke opp et item fra Location sin "liste"
	public static Item playerAdd(String navn, Player spiller)
	{
		Item ny = currentLocation.finn(navn);
		currentLocation.pickUp(navn);
		spiller.addItem(ny);
		return ny;
	}
//	metode for at player skal kunne legge fra seg noe i Location
	public static Item locationAdd(String navn,Player spiller)
	{
		Item ny = spiller.finn(navn);
		if(ny ==null)
			return null;
		spiller.dropItem(navn);
		ny.next = null;
		currentLocation.addItem(ny);
		return ny;
	}
	//add chest to player
	public static Chest playerAddChest(String navn, Player spiller)
	{
		Chest ny = currentLocation.finnChest(navn);
		
		if(ny ==null)
			return null;
		spiller.moreRoom(ny.getExtra());
		currentLocation.pickUpChest(navn);
		spiller.addChest(ny);
		return ny;
	}
//	metode for at player skal legge fra seg chest
	public static Chest locationAddChest(String navn,Player spiller)
	{
		Chest ny = spiller.finnChest(navn);
		if(ny ==null)
			return null;
		spiller.lessRoom(ny.getExtra());
		spiller.dropChest(navn);
		ny.next = null;
		currentLocation.addChest(ny);
		return ny;
	}
	
	public static Item playerFromChest(String item, String che,Player spiller, Location curr)
	{// noe feil, tar bare fårste item i rekka
		Chest skap = curr.finnChest(che);//finner skap
		if(skap == null){
			skap=spiller.finnChest(che);
			if(skap==null)
			return null;
		}
		Item ny = skap.finn(item);//finner item i skap
		if(ny ==null)
			return null;
		skap.dropItem(item);//fjerner item fra skap
		ny.next = null;
		spiller.addItem(ny);//legger til i spiller
		return ny;
	}
	public static Item playerToChest(String item, String che,Player spiller, Location curr)
	{
		Chest skap = curr.finnChest(che);//finner skap
		if(skap == null){
			skap=spiller.finnChest(che);
			if(skap==null)
				return null;
		}
		Item ny = spiller.finn(item);//finner item i spiller
		if(ny ==null){
			ny=currentLocation.finn(item);
			if(ny==null)
				return null;
		}
		spiller.dropItem(item);//fjerner fra spiller
		ny.next = null;//passer på at vi bare får med en
		skap.addItem(ny);//legger til item i skap
		return ny;
	}


	public static String dropAll(Player spiller)
	{
		
		return "";
	}
	

//	metode for å skrive ut inventory for spiller
	public static String playerToString(Player spiller)
	{
		return spiller.toString();
	}
//	metode for å skru på lys
	public static String turnOnLight(String title, Player spiller)
	{
		//Light ny = (Light) spiller.finn(title);
		if(!(spiller.finn(title) instanceof Light))
			  return "\n"+title.toLowerCase() + " cant be turnd on";
		Light ny = (Light) spiller.finn(title);
		if(ny==null)
			return "You have to pick up a " + title;
		else if(spiller.haveLight() && spiller.lightOn())
		{
			return "\n"+title.toLowerCase() + " is already on!";
		}
		ny.setLight(true);
		return null;
	}
//	metode for å skru av lys
	public static String turnOffLight(String title, Player spiller)
	{
		if(!(spiller.finn(title) instanceof Light))
			  return "\n"+title.toLowerCase() + " cant be turnd off";
		Light ny = (Light) spiller.finn(title);
		if(ny==null)
			return "You have to pick up a " + title;
		else if(spiller.haveLight() && !spiller.lightOn())
		{
			return "\n"+title.toLowerCase() + " is not on!";
		}
		ny.setLight(false);
		return null;
	}
//metode for å tenne Flame
	public static String lightFlame(String title, Player spiller)
	{
		if(!(spiller.finn(title) instanceof Flame||(currentLocation.finn(title) instanceof Flame)))
			  return "\n"+title.toLowerCase() + " can't be lit";
		Flame ny = (Flame) spiller.finn(title);
		if(ny==null)
			ny = (Flame) currentLocation.finn(title);
		if(ny.isLit())
			return title.toLowerCase()+" is already lit";
		ny.lightFlame(true);
		lit++;
		flameLoc = currentLocation;
		return title.toLowerCase() + " is now lit";
	}
//metode for å tenne Torch
	public static String turnOnTorch(String title,String flame, Player spiller)
	{
		if(!(spiller.finn(flame) instanceof Flame||spiller.finn(flame) instanceof Torch))	
			return "you cant light anything with that";
		if(lit==0&&torchLight==0)
			return "You have to light the " + flame.toLowerCase();
		
		if(!(spiller.finn(title) instanceof Torch||currentLocation.finn(title) instanceof Torch))
			  return "you can't set fire to everything, try a torch or someting insted";
		Torch ny = (Torch) spiller.finn(title);
		if(ny==null){
			ny= (Torch) currentLocation.finn(title);
			if(ny==null)
				return "You have to pick up a " + title;
		}
		else if(spiller.haveLight() && spiller.lightOn())
		{
			return "\n"+title.toLowerCase() + " is already lit!";
		}
		ny.setLight(true);
		torchLight = 1;
		lit=0;
		flameLoc=null;
		
		if(spiller.finn(flame)!=null&&spiller.finn(flame) instanceof Flame)
		{
			Item del = spiller.finn(flame);
			spiller.fjern(del.getName());
		}
		if(spiller.finn(flame) instanceof Torch)
			return title.toLowerCase() + " is now lit";
		return "Your " + title.toLowerCase() + " is now lit, and your " + flame.toLowerCase() + " died out";
	}
//	read metode
	public static String readText(String title,Player spiller)
	{
		Note ny = (Note)spiller.finn(title);
		if(ny==null)
			return "You have to pick up a " + title;
		else
		return "\n"+ny.toStringNote();
	}
	public static Chest isInChest(String item)
	{
		String[] skap = currentLocation.getChests();
		Chest ny = null;
		//sChest dette = null;
		if(skap!=null)
		{
			for(int i = 0; i<skap.length;i++){
				if(skap[i]!=null && currentLocation.finnChest(skap[i])!=null)
				{
					ny = currentLocation.finnChest(skap[i]);
					if(ny.finn(item)!=null)
					return ny;
				}
			}
			//return dette;
		}
		return null;
	}
	//finn NPC
	public static String killNPC(Location loc, NPC n,String title,Player spiller)
	{
		if(!(spiller.finn(title) instanceof Weapon))
			  return "\n"+title.toLowerCase() + " can't kill anything";
		Weapon ny = (Weapon) spiller.finn(title);
		if(ny==null)
			return "You have to pick up a " + title;
		if(n!=null&&!n.isKillable())
			return "You can not kill " + n.getName();
		String dead =loc.killNPC(n.getName(),ny.getPower());
		if(dead.equals("")){
			currentLocation.setBooleanDeadNPC(true);
			currentLocation.setdeadNPC(n.getName());
			itemFromNPC(n);
			return "You killed " + n.getName() + "!";
		}
		attempts++;
		if(attempts==3&&dead.toUpperCase().equals("DEAD")){
			spiller.setAlive(false);
			return "\nYou didn't manage to kill " + n.getName() + " and you died!";
		}
		return n.getName() + " isnt dead! \nYour weapon was not powerful enough, try again!";
			
	}
	public static String killNPCNoWep(Location loc, NPC n,Player spiller)
	{
		if(n!=null&&!n.isKillable())
			return "You can not kill " + n.getName();
		
		String dead = loc.killNPC(n.getName(),1);
		if(dead.equals("")){
			currentLocation.setBooleanDeadNPC(true);
			currentLocation.setdeadNPC(n.getName());
			itemFromNPC(n);
			return "You killed " + n.getName() + "!";
		}
		attempts++;
		if(attempts==3&&dead.toUpperCase().equals("DEAD")){
			spiller.setAlive(false);
			return "\nYou didn't manage to kill " + n.getName() + " and you died!";
		}
		
		return n.getName() + " isnt dead! \nYour weapon was not powerful enough, try again!";	
			
	}
	public static boolean haveHostileNPC()
	{
		if(currentLocation.haveNpc()&&currentLocation.getNPCFromRomm().isHostile())
			return true;
		return false;
		
	}
	public static boolean NPC(Location loc,String n)
	{
		NPC ok = loc.finnNPC(n);
		if(ok==null)
			return false;
		return true;
	}
	public static void itemFromNPC(NPC npc)
	{
		Item ny = npc.finn();
		//if(ny==null)
		//	return;
		currentLocation.addItem(ny);
	}
	public static Chest isChest(String n)
	{
		Chest ny = currentLocation.finnChest(n);
		if(ny==null)
			return null;
		else 
			return ny;
	}
	public static Chest isChestPlayer(String n,Player spiller)
	{
		Chest ny = spiller.finnChest(n);
		if(ny==null)
			return null;
		else 
			return ny;
	}
	public static String useRecipe(String navn1, String navn2,Player spiller, RecipeList recipes)
	{
		Item item1 = spiller.finn(navn1);
		Item item2 = spiller.finn(navn2);
		if( item1 ==null || item2 == null)
			return "you don't have that";
		Item ny = recipes.checkRecipe(item1, item2);
			if(ny==null)
				return "Did you expect that to work!";
		spiller.fjern(item1.getName());
		spiller.fjern(item2.getName());
		spiller.addItem(ny);
		
		return "You combined " + item1.getName().toLowerCase() + " and " + item2.getName().toLowerCase()+"\n" +
				" and made a " + ny.getName()+"!";
	}
	public static String move(String action, String in)
	  {
		  
		  return in;
		  
		 
	  }
	//public
/**********************************************/	
	// Input håntering
		// METODE FOR å TOLKE LENGRE INPUTS
			public static String inputHandler(String[] in, Player spiller,RecipeList list,String action)
			{
				String ut ="";
				
				if(lit>=1)
					lit++;
				if(lit==4)
				{
					Item ny = spiller.finnLit();
					if(ny!=null){
						//Item fla = spiller.finnLit();
						spiller.fjern(ny.getName());
						lit=0;
						ut+= "The flamasasse you lit just died out... \n sorry, what did you say?\n";
					}
					else{
						ny=flameLoc.finnLit();	
						if(ny!=null){
							//Item fla = flameLoc.finnLit();
							flameLoc.fjern(ny.getName());
							lit=0;
							ut+= "The flame you lit just died out... \n sorry, what did you say?\n";
						}
					}
				}
				
				// PICK UP/TAKE
				//  light <flame> 
				if(in.length == 2 && in[0].equals("LIGHT"))
				{
					if((spiller.finn(in[1]) instanceof Flame)||currentLocation.finn(in[1]) instanceof Flame)
					{
						return lightFlame(in[1], spiller);
					}
				}
				//light <torch> with <flame>
				if(in.length == 4 && in[0].equals("LIGHT") && in[2].equals("WITH"))
				{
					if((spiller.finn(in[1]) instanceof Torch)&&((spiller.finn(in[3]) instanceof Flame)||(spiller.finn(in[3]) instanceof Torch)))
					{
						String u2= turnOnTorch(in[1], in[3], spiller);
						return u2;
					}
					else if((currentLocation.finn(in[1]) instanceof Torch)&&((spiller.finn(in[3]) instanceof Flame)||(spiller.finn(in[3]) instanceof Torch)))
					{
						String u2= turnOnTorch(in[1], in[3], spiller);
						return u2;
					}
					
				}
			// turn on <item> && light <item>***************
				if(in.length == 3 && in[0].equals("TURN")&& in[1].equals("ON"))
				{
						  String n = Command.turnOnLight(in[2],spiller);
						  if(n!=null)
							  return n;
						  else if(Command.getCurrentLoc().getLight())
							  return "\nYour light is on";
						  else 
							  {  
							  return "\nYour light is now on an you can se around the room\n";
							  }
					  }
				if( in.length == 2 && in[0].equals("LIGHT"))
				{
					
					  String n = Command.turnOnLight(in[1],spiller);
					  if(n!=null)
						  return n;
					  else if(getCurrentLoc().getLight())
						  return "\nYour light is on";
					  else 
						  {
						  return "\nYour light is now on an you can se around the room\n";
						  }
				}// turn off <item> 
				if(in.length == 3 && in[0].equals("TURN")&& in[1].equals("OFF"))
				{
						  String n = Command.turnOffLight(in[2],spiller);
						  if(n!=null)
							  return n;
						  else if(getCurrentLoc().getLight())
							  return "\nYour light is off";
						  else 
							  {
							  return "\nYour light is off, and its black...\n";
							  }
						  
					  }
				//inventory FUNKER
				if((in.length == 1 && in[0].equals("INVENTORY")) ||in.length == 1 &&in[0].equals("I")  || 
				(in.length == 2 && in[0].equals("SHOW") && in[1].equals("INVENTORY") ))
				{
					if(currentLocation.getLight())
					{
						return playerToString(spiller);
					}
					if(spiller.haveLight() && spiller.lightOn())
					{
						return playerToString(spiller);
					}
					return "It's too dark, ad you cant see...";
				}
				//look around FUNKER
				else if(in.length == 2 && in[0].equals("LOOK") && in[1].equals("AROUND")||
						in.length == 1 && in[0].equals("L"))
				{//return desc();
					if(currentLocation.getLight())
					{
						return desc();
					}
					else if(spiller.haveLight() && spiller.lightOn())
					{
						return desc();
					}
					return "\nit's pitch black, you are likely to be eaten by a grue!\n";
				}
				// take <item> || take <chest> FUNKER // test med fra chest
				else if(in.length == 2 && in[0].equals("TAKE"))
				{
					if(currentLocation.finn(in[1])!=null){
						if(!spiller.isRoom(currentLocation.finn(in[1]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[1],spiller);
						ut += in[1].toLowerCase()+" is picked up by player";
						return ut;
					}
					if(isInChest(in[1])!=null)
					{
						Chest ny = isInChest(in[1]);
						if(ny.isOpen()){
						if(!spiller.isRoom(ny.finn(in[1]).getWeight()))
							return "You are carrying too much!";
						playerFromChest(in[1], ny.getName(), spiller, currentLocation);
						ut += "Got " + in[1].toLowerCase() + " from " + ny.getName().toLowerCase();
						return ut;
						}
						return ny.getName()+" is closed"; 
					}
					if(isChest(in[1])!=null){
						Chest ny =isChest(in[1]);
						if(!spiller.isRoom(ny.getWeight())||!ny.isMove())
							return "This "+in[1].toLowerCase()+" is to heavy!";
						playerAddChest(in[1], spiller);
						ut += "Got " + in[1].toLowerCase() + " from " + currentLocation.getTitle().toLowerCase();
						return ut;
					}
						
					
				}
				
				// take <item> and <item> FUNKER
				else if(in.length == 4 && in[0].equals("TAKE") && in[2].equals("AND") )
				{
					if(currentLocation.finn(in[1])!=null && currentLocation.finn(in[3])!=null){
						if(!spiller.isRoom(currentLocation.finn(in[1]).getWeight()))
							return "You are carrying too much!";
						if(!spiller.isRoom(currentLocation.finn(in[1]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[1],spiller);
						if(!spiller.isRoom(currentLocation.finn(in[3]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[3], spiller);
						ut += in[1].toLowerCase()+" and "+in[3].toLowerCase() +" is picked up by player";
						return ut;
					}
					
				}
				
				// pick up <item> FUNKER
				else if(in.length == 3 && in[0].equals("PICK") && in[1].equals("UP"))
				{
					if(currentLocation.finn(in[2])!=null){
						if(!spiller.isRoom(currentLocation.finn(in[2]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[2],spiller);
						ut += in[2].toLowerCase()+" is picked up by player";
						return ut;
					}
					if(isInChest(in[2])!=null)
					{
						
						Chest ny = isInChest(in[2]);
						if(ny.isOpen()){
						if(!spiller.isRoom(ny.finn(in[2]).getWeight()))
							return "You are carrying too much!";
						playerFromChest(in[2], ny.getName(), spiller, currentLocation);
						ut += "Got " + in[2].toLowerCase() + " from " + currentLocation.getTitle().toLowerCase();
						return ut;}
						return ny.getName()+" is closed";
					}
					if(isChest(in[2])!=null){
						Chest ny =isChest(in[2]);
						if(!spiller.isRoom(ny.getWeight())||!ny.isMove())
							return "This "+in[2].toLowerCase()+" is to heavy!";
						playerAddChest(in[2], spiller);
						ut += "Got " + in[2].toLowerCase() + " from " + ny.getName().toLowerCase();
						return ut;
					}
					
				}
				// pick up <item> and <item> FUNKER
				 else if(in.length == 5 && in[0].equals("PICK") && in[1].equals("UP") && in[3].equals("AND"))
				{
					if(currentLocation.finn(in[2])!=null && currentLocation.finn(in[4])!=null){
						if(!spiller.isRoom(currentLocation.finn(in[2]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[2],spiller);
						if(!spiller.isRoom(currentLocation.finn(in[4]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[4],spiller);
						ut += in[2].toLowerCase()+" and "+in[4].toLowerCase()+" is picked up by player";
						return ut;
					}
				}
				//drop all
				/*else if(in.length == 2 && in[0].equals("DROP")&& in[1].equals("ALL"))
						return dropAll(spiller);*/
				// drop <item> || drop <item> in room FUNKER
				if(in.length == 2 && (in[0].equals("DROP"))||(in.length == 4 && (in[0].equals("DROP")||
						in[0].equals("PUT"))&& (in[2].equals("IN"))&&(in[3].equals("ROOM"))) )
				{//droppe lykt som fortsatt lyser
					/*if(spiller.finn(in[1])!=null&&spiller.finn(in[1]) instanceof Light){
						if(((Light) spiller.finn(in[1])).isOn())
						{
							locationAdd(in[1],spiller);
							((Light)currentLocation.finn(in[1])).setLight(true);
							ut += in[1].toLowerCase()+" is droped in " + currentLocation.getTitle();
							return ut;
						}
						locationAdd(in[1],spiller);
						
						ut += in[1].toLowerCase()+" is droped in " + currentLocation.getTitle();
						return ut;
					}*/
					if(spiller.finn(in[1])!=null){
						locationAdd(in[1],spiller);
						ut += in[1].toLowerCase()+" is droped in " + currentLocation.getTitle();
						return ut;
						
					}
					if(isChestPlayer(in[1],spiller)!=null){
						//Chest ny =isChestPlayer(in[1],spiller);
						locationAddChest(in[1], spiller);
						ut += in[1].toLowerCase()+" is droped in " + currentLocation.getTitle();
						return ut;
					}
					
				}
				//drop <item> and <item>FUNKER
				else if(in.length == 4 && (in[0].equals("DROP") && in[2].equals("AND")))
				{
					if(spiller.finn(in[1])!=null && spiller.finn(in[3])!=null){
						locationAdd(in[1],spiller);
						locationAdd(in[3],spiller);
						ut += in[1].toLowerCase()+" and "+in[3].toLowerCase()+" is droped in " + currentLocation.getTitle();
						return ut;
					}
				}
				//drop <item> and take <item>
				else if(in.length == 5 && (in[0].equals("DROP") && in[2].equals("AND"))&& in[3].equals("TAKE"))
				{
					if(spiller.finn(in[1])!=null&&currentLocation.finn(in[4])!=null){
						locationAdd(in[1],spiller);
						if(!spiller.isRoom(currentLocation.finn(in[4]).getWeight()))
							return "You are carrying too much!";
						playerAdd(in[4],spiller);
						ut += in[1].toLowerCase()+" is droped in " + currentLocation.getTitle();
						ut += "\n and " +in[4].toLowerCase()+" is picked up by player";
						return ut;
						
						
					}
					
				}
				
				// take <item> from <chest> FUNKER
				else if(in.length == 4 && in[0].equals("TAKE") && in[2].equals("FROM"))
				{		
					Chest ny = currentLocation.finnChest(in[3]);
					if(ny==null)
					{
						ny=spiller.finnChest(in[3]);
						if(ny==null)
							return "No "+ in[3].toLowerCase();
					}
					if(ny.finn(in[1])!=null){
						if(ny.isOpen())
						{
						if(!spiller.isRoom(ny.finn(in[1]).getWeight()))
							return "You are carrying too much";
						playerFromChest(in[1], in[3], spiller, currentLocation);
						ut += "Got " + in[1].toLowerCase() + " from " + in[3].toLowerCase();
						return ut;
						}
						return in[3].toLowerCase()+" is closed";
					}
					
					return "there is no " + in[1].toLowerCase();
				}
				// pick up <item> from <chest>FUNKER
				else if(in.length == 5 && in[0].equals("PICK") && in[1].equals("UP") && in[3].equals("FROM"))
				{
					Chest ny = currentLocation.finnChest(in[4]);
					if(ny==null)
					{
						ny=spiller.finnChest(in[4]);
						if(ny==null)
							return "No "+ in[4].toLowerCase();
					}
					if(ny.finn(in[2])!=null){
						if(ny.isOpen()){
						if(!spiller.isRoom(ny.finn(in[2]).getWeight()))
							return "You are carrying too much";
						playerFromChest(in[2], in[4], spiller, currentLocation);
						ut += "Got " + in[2].toLowerCase() + " from " + in[4].toLowerCase();
						return ut;}
						return in[3].toLowerCase()+" is closed";
					}
					else
						return "there is no " + in[2].toLowerCase();
				}

				
				// put <item> in <chest> & place <item> in <chest>  FUNKER
				else if(in.length == 4 && (in[0].equals("PUT") || in[0].equals("PLACE")) && in[2].equals("IN"))
				{
					Chest ny = currentLocation.finnChest(in[3]);
					if(ny==null)
					{
						ny=spiller.finnChest(in[3]);
						if(ny==null)
							return "No "+ in[3].toLowerCase();
					}
					if(spiller.finn(in[1])!=null||currentLocation.finn(in[1])!=null){
						if(ny.isOpen()){
						Item item = spiller.finn(in[1]);
						if(item==null)
						{
							item=currentLocation.finn(in[1]);
							if(item==null)
								return "There is no " + in[1].toLowerCase() + " here";		
						}
						if(!ny.isRoom(item.getWeight()))
							return in[3].toLowerCase() +" dont have enough room";
						playerToChest(in[1], in[3], spiller, currentLocation);
						ut += "Placed " + in[1].toLowerCase() + " in " + in[3].toLowerCase();
						return ut;}
						else
							return in[3].toLowerCase()+" is closed";
					}
					else
						return "you dont have "+in[1].toLowerCase();
					
				}	
				// read <item> 
				else if(in.length == 2 && in[0].equals("READ"))
				{
					if(currentLocation.finn(in[1])!=null){
						playerAdd(in[1],spiller);
						return readText(in[1],spiller);
					}
					if(spiller.finn(in[1])!=null)
						return readText(in[1],spiller);
					return "\n can't find " + in[1].toLowerCase();	
				}
			/*	//ANSWER for NPC     
				//here||yes || no || KICK/PUNCH <NPC>
				if((in.length>=1 &&currentLocation.haveNpc()) &&
						(in[0].equals("YES") || in[0].equals("NO")|| in[0].equals("HERE") || 
								(in[0].equals("KICK")||in[0].equals("PUNCH")) &&NPC(currentLocation, in[1]) )){
						if(in[0].equals("HERE"))
							in[0]="YES";
						NPC finn = currentLocation.finnNPCA(in[0]);
						if(finn!=null)
						{
							finn.setDialog("That was all");
							finn.setAnswer("DONE");
							return "Thank you! Have a nice day!";
						}
						else if(in[0].equals("NO"))
						{
							NPC ny = currentLocation.getNPCFromRomm();
							if(ny==null)
								return "There is no here";
							if(ny.getAnswer().equals("DONE"))
								return "But you gave it to me! so its mine!";
							ny.setHostile(true);
							ny.setKillable(true);
							ny.setDialog("I WILL KILL YOU!");
							return "I WILL KILL YOU!";
						}
						else if((in[0].equals("KICK")||in[0].equals("PUNCH")))
						{
							NPC ny = currentLocation.getNPCFromRomm();
							if(ny==null)
								return "There is no here";
							ny.setHostile(true);
							ny.setKillable(true);
							ny.setDialog("I WILL KILL YOU!");
							return "I WILL KILL YOU!";
						}
								
				
				}*/
				
				//kill <NPC>&& kill <NPC> with <Weapon>
				else if(in.length == 2 && in[0].equals("KILL")||in.length==4 &&
						in[0].equals("KILL")&& in[2].equals("WITH"))
				{
					if(currentLocation.haveNpc())//if(NPC(currentLocation, in[1]))
					{
						if(in.length==2)
						{
						NPC a = currentLocation.finnNPC(in[1]);
						if(a==null)
							return "there is no " + in[1].toLowerCase();
						return killNPCNoWep(currentLocation,a,spiller);
						}
						if(in.length==4)
						{
							NPC a = currentLocation.finnNPC(in[1]);
							if(a==null)
								return "there is no " + in[1].toLowerCase();
							return killNPC(currentLocation,a,in[3], spiller);
						}
					}
					return "There is no "+ in[1].toLowerCase() + " here";
					
				}
				//open <chest>
				if(in.length == 2 && in[0].equals("OPEN"))
				{
					if(currentLocation.finnChest(in[1])!=null)
					{
						if(!currentLocation.finnChest(in[1]).isOpen())
						{
							currentLocation.finnChest(in[1]).setBlocked(true);
							return in[1].toLowerCase() + " is now open\n" + "contanins:" +
									currentLocation.finnChest(in[1]).toString();
						}
						return in[1].toLowerCase() + " is alredy open";
					}
					
					if(getDirection(in[1])!=-1)
						return "What do you want to open "+ in[1].toLowerCase()+" with?";
					return "No "+ in[1].toLowerCase() + " in " + currentLocation.getTitle().toLowerCase();
					
				}
				//close <chest>
				if(in.length == 2 && in[0].equals("CLOSE"))
				{
					if(currentLocation.finnChest(in[1])!=null){
						if(currentLocation.finnChest(in[1]).isOpen()){
							currentLocation.finnChest(in[1]).setBlocked(false);
							return in[1].toLowerCase() + " is now closed";
						}
						return in[1].toLowerCase() + " is alredy closed";
					}
					
					return "No "+ in[1].toLowerCase() + " in " + currentLocation.getTitle().toLowerCase();
					
				}
				//help commando
				if(in.length==1 && in[0].equals("HELP"))
					return "--------------------------------------------\n" +
							"So you need help eyy...?\n\n" +
							"If you need:\n" +
							"\t- help with commands: <command list>\n" +
							"\t- help with quests: <quest guide>\n" +
							"\t- help getting unstuck: <im stuck>\n" +
							"--------------------------------------------\n";
				if(in.length==3 && in[0].equals("MUFC") && in[1].equals("ONE")&&in[2].equals("LOVE"))
				{
					if(cheats==0){
						Weapon lightSaber = new Weapon("Lightsaber", 0, 100);
						spiller.addItem(lightSaber);
						cheats++;
					}
					
				}
				// unlock/open <exit> with <item>/<key>
				else if((in.length == 4 && (in[0].equals("UNLOCK") || in[0].equals("OPEN")) && in[2].equals("WITH")))
				{
					String text = "";
					if(currentLocation.lockedDoor(in[1],in[3]))
						text = "\n" + in[1] + " is unlocked";
					else
						text = "\nSomething went wrong...";
					
					return text;
				}
				else if(in.length == 4 && (in[0].equals("MOVE") && in[2].equals("WITH")))
				{
					String text = "";
					if(currentLocation.blockedExit(in[1],in[3]))
						text = "\n" + in[1].toLowerCase() + " was moved";
					else
						text = "\nNot the " + in[1].toLowerCase() + " or " + in[3].toLowerCase() + " you were looking for...";
					
					return text;
				}
				
				else if(in.length == 2 && (in[0].equals("MOVE")))
				{
					String text = "";
					if(currentLocation.blockedExit(in[1],null))
						text = "\n" + in[1].toLowerCase() + " was moved";
					else
						text = "\nNot the " + in[1].toLowerCase() + " you were looking for...";
					
					return text;
				}
				//combine <item> and <item>
				else if(in.length == 4 && (in[0].equals("COMBINE") && in[2].equals("AND")))
				{
					if(spiller.finn(in[1])!=null&&spiller.finn(in[3])!=null)
					{
						String out ="";
						out=useRecipe(in[1],in[3],spiller,list);
						return out;
					}	
					else
						return "you don't have that";
				}
				//GIVE <ITEM> TO <NPC> - and recive item from npc
				else if(in.length == 4 && (in[0].equals("GIVE")) && in[2].equals("TO"))
				{
					if(spiller.finn(in[1]) != null)
					{
						Item item = spiller.finn(in[1]);
						if(currentLocation.finnNPC(in[3])!=null)
						{
							NPC npc = currentLocation.finnNPC(in[3]);
							if(npc.eventHandler(item) != null)
							{
								spiller.addItem(npc.eventHandler(item));
								spiller.dropItem(in[1]);
								
								npc.setDialog("*Mumbles* My precious...");
								npc.setAnswer("");
								
								return "You gave " + in[1].toLowerCase() + " to " + in[3].toLowerCase() + ", and you got something in return";
							}
							else
								return "Wrong item";
						}
						else
							return in[3].toLowerCase() + " is not here"; 
					}
					else
						return "You don't have any " + in[1].toLowerCase();
				}
				
				//GIVE <NPC> <ITEM> - and recive item from npc
				else if(in.length == 3 && (in[0].equals("GIVE")))
				{
					if(spiller.finn(in[2]) != null)
					{
						Item item = spiller.finn(in[2]);
						if(currentLocation.finnNPC(in[1])!=null)
						{
							NPC npc = currentLocation.finnNPC(in[1]);
							if(npc.eventHandler(item) != null)
							{
								spiller.addItem(npc.eventHandler(item));
								spiller.dropItem(in[2]);
								
								npc.setDialog("*Mumbles* My precious...");
								npc.setAnswer("");
								
								return "You gave " + in[2].toLowerCase() + " to " + in[1].toLowerCase() + ", and you got something in return";
							}
							else
								return "Wrong item";
						}
						else
							return in[1].toLowerCase() + " is not here"; 
					}
					else
						return "You don't have any " + in[2].toLowerCase();
				}
				return null;
				
		}

}
