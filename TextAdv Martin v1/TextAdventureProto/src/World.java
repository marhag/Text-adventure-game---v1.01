
/*
 * 		World-klassen definerer game-verdenen, og alle dens locations
 */

public class World 
{
	private Location[] loc = new Location[10]; // Array over alle locations
	Player spiller = new Player();
	RecipeList recipes = new RecipeList();
	
	public World()
	{		
		// Definerer locations
		loc[0] = new Location("Kjøkken", "Kjøkken var rotete. Det er to synlige veier ut",true);
		loc[1] = new Location("Stue", "Tregulvet passet veldig godt inn.\nDu legger merke til stigen som går oppover",true);
		loc[2] = new Location("Kott", "Kottet er lite. Hvorfor gikk jeg her?",true);
		loc[3] = new Location("Loft", "Trangt, mørkt og kjølig.\nDu vil helst gå ned igjen.\nDet ser ut som at man kan gå videre bortover",true);
		loc[4] = new Location("Loft 2", "Det er enda trangere her.\nDet ser ut som at du kan hoppe ned et hull.\nDet er langt ned", false);
		loc[7] = new Location("Soverom", "Du står i et stort åpent soverom \n det er en fireplace i hjørnet", false);
		loc[5] = new Location("Kjeller", "Vannet når deg til navlen.\nDu begynner å fryse.\nEn trapp leder oppover",true);
		loc[6] = new Location("Hage", "Sola står midt på himmelen og du ser kunn skog rundt deg\nSkogen er for tett til at du kan gå inn",true);
		loc[8] = new Location("Stort Skap", "Du står i et tomt skap",false);
		
		//Oppretter Items
		Item ost = new Item("Ost",1);
		Item ball = new Item("Ball",1);
		Item pinne = new Item("Pinne",1);
		Item sko = new Item("Sko",1);
		Item soap = new Item("Soap",1);
		Item hylle = new Item("Hylle",1);
		Item coin = new Item("Coin",1);
		Item horn = new Item("Horn",1);
		Item key = new Item("Key",0);
		Item coal = new Item("Coal",0);
		Item stick = new Item("Stick",0);
		Item cola = new Item("Cola-boks",7);
		Item plank = new Item("Plank",0);
		Item barrel = new Item("Barrel",100);
		Item shaft = new Item("Shaft",1);
		Item stone = new Item("Stone",2);
		
		
		
		//oppretter NPC
		NPC sondre = new NPC("Sondre", "Please give me a coin..","YES",8,false, false);
		NPC troll = new NPC("Troll", "Suddenly you se a Troll!\n" +
				"im going to kill you!",null,50,true, true);
		
		//Oppretter Chests
		Chest skap = new Chest("Skap",true,100,0);
		Chest boks = new Chest("Boks",false,100,0);
		Chest eske = new Chest("Eske",false,50,13);
		Chest bag = new Chest("Bag",true,3,15);
		
		//Oppretter Light - arv Item
		Light lykt = new Light("Lykt",0,false);
		Light fakkel = new Light("Fakkel",1,false);
		//Light torch = new Light("Torch",1,false);
		
		//oretter Torch
		Torch torch = new Torch("Torch",1,false);
		Torch fireplace = new Torch("Fireplace",99,false);
		
		//oppretter Flame
		Flame match = new Flame("Match", 0, false);
		
		//Oppretter Note - arv Item
		Note velkomst = new Note("Lapp",1,"Velkommen", "Detter er kun en test, men siden jeg må sjekke om det blir flere linjer skriver jeg litt til:)");
		Note leaflet = new Note("Leaflet",1,"Velkommen", "Hallo og velkommen til dette spillet!");
		
		//Oppretter Weapon - arv Item
		Weapon sword = new Weapon("Sword", 3, 100);
		Weapon lightsaber = new Weapon("Lightsaber", 0, 1000);
		Weapon kniv = new Weapon("Kniv",1,3);
		Weapon axe = new Weapon("Axe",2,20);
		// Exits for 1. location
		loc[0].addExit(Exit.NORTH, loc[1]);
		//loc[0].addExit(Exit.EAST, loc[2]);
		//addItem(Item navn) på kjåkken
		loc[0].addItem(ost);
		loc[0].addItem(ball);
		loc[0].addItem(kniv);
		loc[0].addItem(leaflet);
		//addHiddenExit()
		loc[0].addHiddenExit(Exit.DOWN, loc[5]);
		loc[0].addBlockedExit(Exit.EAST, loc[2], barrel, plank, false);
		
		// Exits for 2. location
		loc[1].addExit(Exit.SOUTH, loc[0]);
		loc[1].addExit(Exit.UP, loc[3]);
		loc[1].addExit(Exit.NORTH, loc[6]);
		//loc[1].addLockedExit(Exit.NORTH, loc[6], såpe); //der key er en vanlig <item> som heter key
		
		//NPC i loc[2]
		loc[2].addNPC(sondre);
		//NPC i loc[6]
		loc[6].addNPC(troll);
		//Item og Chest adds
		//loc[1].addItem(såpe);
		loc[1].addChest(skap);
		loc[1].addChest(boks);
		skap.addItem(velkomst);
		skap.addItem(hylle);
		boks.addItem(lykt);
		boks.addItem(soap);
		boks.addItem(sword);
		
		// Exits for 3. location
		loc[2].addExit(Exit.WEST, loc[0]);
		loc[2].addLockedExit(Exit.NORTH, loc[8], key); //der key er en vanlig <item> som heter key
		
		loc[3].addExit(Exit.DOWN, loc[1]);
		loc[3].addExit(Exit.EAST, loc[4]);
		
		loc[4].addExit(Exit.WEST, loc[3]);
		loc[4].addExit(Exit.DOWN, loc[5]);
		loc[4].addExit(Exit.NORTH, loc[7]);
		
		loc[5].addExit(Exit.UP, loc[0]);
		
		loc[6].addExit(Exit.SOUTH, loc[1]);
		
		loc[7].addExit(Exit.SOUTH, loc[4]);
		
		loc[8].addExit(Exit.SOUTH, loc[2]);
		
		// Item add
		loc[5].addItem(pinne);
		loc[5].addItem(fakkel);
		loc[5].addChest(bag);
		loc[5].addItem(shaft);
		
		//Item add loc[6]
		loc[6].addChest(eske);
		eske.addItem(cola);
		eske.addItem(key);
		
		//Item add loc[7]
		loc[7].addItem(fireplace);
		//Player addItem
		spiller.addItem(stick);
		spiller.addItem(coal);
		spiller.addItem(match);
		spiller.addItem(coin);
		
		//NPC addItem
		//sondre.addItem(coin);
		sondre.addItem(key);
		troll.addItem(plank);
		troll.addItem(stone);
		
		//spiller.addItem(...);
		//Makes Events
		Event event = new Event(coin, lightsaber); //give coin, get lightsaber
		sondre.addEvent(event);
		
		
		/****RECIPES****/
		Recipe Rtorch = new Recipe(stick,coal,torch);
		recipes.addRecipe(Rtorch);
		Recipe Raxe = new Recipe(shaft,stone,axe);
		recipes.addRecipe(Raxe);
			
	}
	public Player getPlayer()
	{
		return spiller;
	}
	public Location getStart()
	{
		return loc[0];
	}
	public RecipeList getRecipe()
	{
		return recipes;
	}

}
