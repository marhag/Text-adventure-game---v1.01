
/*
 * 		Command-klassen holder styr på kommandoer og hvor spilleren er
 */


public class Command
{
	private static Location currentLocation; // Holder styr på hvor playeren er
	
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
		if(!ok)
		{
			return "\nNo open exits that way!";
		}
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
	public static String desc()
	{
		String utskrift = "";
		utskrift += "\n" + currentLocation.getDescription();
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
		
		if(dir != -1 && currentLocation.getExit(dir) != null && currentLocation.getExit(dir).leadsTo() != null &&
				!currentLocation.getExit(dir).isHidden()){
			setCurrentLocation(currentLocation.getExit(dir).leadsTo());
			return true;
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
		if(ny ==null)
			return null;
		currentLocation.pickUp(navn);
		spiller.addItem(ny);
		return ny;
	}
	public static Item playerFromChest(String item, String che,Player spiller, Location curr)
	{// noe feil, tar bare første item i rekka
		Chest skap = curr.finnChest(che);//finner skap
		if(skap == null)
			return null;
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
		if(skap == null)
			return null;
		Item ny = spiller.finn(item);//finner item i spiller
		if(ny ==null)
			return null;
		spiller.dropItem(item);//fjerner fra spiller
		ny.next = null;//passer på at vi bare får med en
		skap.addItem(ny);//legger til item i skap
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
	

//	metode for å skrive ut inventory for spiller
	public static String playerToString(Player spiller)
	{
		return spiller.toString();
	}
//	metode for å skru på lys
	public static String turnOnLight(String title, Player spiller)
	{
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
	public static String killNPC(Location loc, NPC n)
	{
		if(!n.isKillable())
			return "You can not kill " + n.getName();
		loc.killNPC(n.getName());
		return "You killed " + n.getName() + "!";
			
	}
	public static boolean NPC(Location loc,String n)
	{
		NPC ok = loc.finnNPC(n);
		if(ok==null)
			return false;
		return true;
	}
	
	// Input håntering
		// METODE FOR ≈ TOLKE LENGRE INPUTS
			public static String inputHandler(String[] in, Player spiller)
			{
				String ut ="";
				// Use-kommandoer
				// use <item>
				/*if(in.length == 2 && in[0].equals("USE"))
					if(player.findItem(in[1]))
						useItem(findItem[1]);*/
				// PICK UP/TAKE
				//inventory FUNKER
				if((in.length == 1 && in[0].equals("INVENTORY")) ||in.length == 1 &&in[0].equals("I")  || 
				(in.length == 2 && in[0].equals("SHOW") && in[1].equals("INVENTORY") ))
				{
					return playerToString(spiller);
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
				// take <item> FUNKER // test med fra chest
				else if(in.length == 2 && in[0].equals("TAKE"))
				{
					
					if(currentLocation.finn(in[1])!=null){
						playerAdd(in[1],spiller);
						ut += in[1].toLowerCase()+" is picked up by player";
						return ut;
					}
					if(isInChest(in[1])!=null)
					{
						Chest ny = isInChest(in[1]);
						playerFromChest(in[1], ny.getName(), spiller, currentLocation);
						ut += "Got " + in[1].toLowerCase() + " from " + ny.getName().toLowerCase();
						return ut;
					}
					
				}
				
				// take <item> and <item>FUNKER
				else if(in.length == 4 && in[0].equals("TAKE") && in[2].equals("AND") )
				{
					if(currentLocation.finn(in[1])!=null && currentLocation.finn(in[3])!=null){
						playerAdd(in[1],spiller);
						playerAdd(in[3], spiller);
						ut += in[1].toLowerCase()+" and "+in[3].toLowerCase() +" is picked up by player";
						return ut;
					}
					
				}
				
				// pick up <item> FUNKER
				else if(in.length == 3 && in[0].equals("PICK") && in[1].equals("UP"))
				{
					if(currentLocation.finn(in[2])!=null){
						playerAdd(in[2],spiller);
						ut += in[2].toLowerCase()+" is picked up by player";
						return ut;
					}
					if(isInChest(in[2])!=null)
					{
						Chest ny = isInChest(in[2]);
						playerFromChest(in[2], ny.getName(), spiller, currentLocation);
						ut += "Got " + in[2].toLowerCase() + " from " + ny.getName().toLowerCase();
						return ut;
					}
					
				}
				// pick up <item> and <item> FUNKER
				 else if(in.length == 5 && in[0].equals("PICK") && in[1].equals("UP") && in[3].equals("AND"))
				{
					if(currentLocation.finn(in[2])!=null && currentLocation.finn(in[4])!=null){
						playerAdd(in[2],spiller);
						playerAdd(in[4],spiller);
						ut += in[2].toLowerCase()+" and "+in[4].toLowerCase()+" is picked up by player";
						return ut;
					}
				}
				// drop <item> || drop <item> in room FUNKER
				if(in.length == 2 && (in[0].equals("DROP"))||(in.length == 4 && (in[0].equals("DROP")||
						in[0].equals("PUT"))&& (in[2].equals("IN"))&&(in[3].equals("ROOM"))) )
				{
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
				
				// take <item> from <chest> FUNKER
				else if(in.length == 4 && in[0].equals("TAKE") && in[2].equals("FROM"))
				{
					Chest ny = currentLocation.finnChest(in[3]);
					if(ny==null)
					{
						return "No "+ in[3].toLowerCase();
					}
					else if(ny.finn(in[1])!=null){
						playerFromChest(in[1], in[3], spiller, currentLocation);
						ut += "Got " + in[1].toLowerCase() + " from " + in[3].toLowerCase();
						return ut;
					}
					else
						return "there is no " + in[1].toLowerCase();
				}
				// pick up <item> from <chest>FUNKER
				else if(in.length == 5 && in[0].equals("PICK") && in[1].equals("UP") && in[3].equals("FROM"))
				{
					Chest ny = currentLocation.finnChest(in[4]);
					if(ny==null)
					{
						return "No "+ in[4].toLowerCase();
					}
					if(ny.finn(in[2])!=null){
						playerFromChest(in[2], in[4], spiller, currentLocation);
						ut += "Got " + in[2].toLowerCase() + " from " + in[4].toLowerCase();
						return ut;
					}
					else
						return "there is no " + in[2].toLowerCase();
				}
				
				// DROP
				
				// put <item> in <chest> & place <item> in <chest>  FUNKER
				else if(in.length == 4 && (in[0].equals("PUT") || in[0].equals("PLACE")) && in[2].equals("IN"))
				{
					Chest ny = currentLocation.finnChest(in[3]);
					if(ny==null)
					{
						return "No "+ in[3].toLowerCase();
					}
					if(spiller.finn(in[1])!=null){
						playerToChest(in[1], in[3], spiller, currentLocation);
						ut += "Placed " + in[1].toLowerCase() + " in " + in[3].toLowerCase();
						return ut;
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
				//kill <NPC>
				else if(in.length == 2 && in[0].equals("KILL") && NPC(currentLocation, in[1]))
				{
					NPC a = currentLocation.finnNPC(in[1]);
					if(a==null)
						return "there is no one here...";
					return killNPC(currentLocation,a);
					
					
				}
				return null;/*
				// TURN ON
				// turn on <item>
				if(in.length == 3 && in[0].equals("TURN") && in[1].equals("ON"))
					if(findItem[2])
						turnOnItem(findItem(in[2]));	*/
			}}
