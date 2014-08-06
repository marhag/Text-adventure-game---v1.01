
/*
 * 		World-klassen definerer game-verdenen, og alle dens locations
 */

public class World 
{
	private Location[] loc = new Location[10]; // Array over alle locations
	Player spiller = new Player();
	
	public World()
	{		
		// Definerer locations
		loc[0] = new Location("Kjøkken", "Kjøkken var rotete. Det er to synlige veier ut",true);
		loc[1] = new Location("Stue", "Tregulvet passet veldig godt inn.\nDu legger merke til stigen som går oppover",true);
		loc[2] = new Location("Kott", "Kottet er lite. Hvorfor gikk jeg her?",true);
		loc[3] = new Location("Loft", "Trangt, mørkt og kjølig.\nDu vil helst gå ned igjen.\nDet ser ut som at man kan gå videre bortover",true);
		loc[4] = new Location("Loft 2", "Det er enda trangere her.\nDet ser ut som at du kan hoppe ned et hull.\nDet er langt ned", false);
		loc[5] = new Location("Kjeller", "Vannet når deg til navlen.\nDu begynner å fryse.\nEn trapp leder oppover",true);
		
		//Oppretter Items
		Item ost = new Item("Ost");
		Item kniv = new Item("Kniv");
		Item ball = new Item("Ball");
		Item pinne = new Item("Pinne");
		Item sko = new Item("Sko");
		Item såpe = new Item("Såpe");
		Item hylle = new Item("Hylle");
		
		//Oppretter Chests
		Chest skap = new Chest("Skap");
		Chest boks = new Chest("Boks");
		
		//Oppretter Light - arv Item
		Light lykt = new Light("Lykt",false);
		Light fakkel = new Light("Fakkel",false);
		
		//Oppretter Note - arv Item
		Note velkomst = new Note("Lapp","Velkommen", "Detter er kun en test, men siden jeg må sjekke om det blir flere linjer skriver jeg litt til:)");
		
		// Exits for 1. location
		loc[0].addExit(Exit.NORTH, loc[1]);
		loc[0].addExit(Exit.EAST, loc[2]);
		//addItem(Item navn) på kjøkken
		loc[0].addItem(ost);
		loc[0].addItem(ball);
		loc[0].addItem(kniv);
		//addHiddenExit()
		loc[0].addHiddenExit(Exit.DOWN, loc[5]);
		
		// Exits for 2. location
		loc[1].addExit(Exit.SOUTH, loc[0]);
		loc[1].addExit(Exit.UP, loc[3]);
		//NPC i loc[2]
		loc[2].addNPC("Husslave", "Gi meg pisken, ellers!!!?",2,true, true);
		//Item og Chest adds
		//loc[1].addItem(såpe);
		loc[1].addChest(skap);
		loc[1].addChest(boks);
		skap.addItem(velkomst);
		skap.addItem(hylle);
		boks.addItem(lykt);
		boks.addItem(såpe);
		
		// Exits for 3. location
		loc[2].addExit(Exit.WEST, loc[0]);
		
		loc[3].addExit(Exit.DOWN, loc[1]);
		loc[3].addExit(Exit.EAST, loc[4]);
		
		loc[4].addExit(Exit.WEST, loc[3]);
		loc[4].addExit(Exit.DOWN, loc[5]);
		
		loc[5].addExit(Exit.UP, loc[0]);
		
		// Item add
		loc[5].addItem(pinne);
		loc[5].addItem(fakkel);
		
		spiller.addItem(sko);
		//spiller.addItem(lykt);
			
	}
	public Player getPlayer()
	{
		return spiller;
	}
	public Location getStart()
	{
		return loc[0];
	}

}
