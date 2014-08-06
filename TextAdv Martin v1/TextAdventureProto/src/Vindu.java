
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

public class Vindu extends JFrame
{
	World verden = new World();
	Command com = new Command();
	Location start = verden.getStart();
	
	
	private String[] memory = new String[5]; // 5 siste kommandoer lagres i minnet	  
	private static JTextField input;
	private static JTextArea utskrift, title;
	private static Kommandolytter lytter;
  

  public Vindu()
  {
    super( "Text Adventure Game" );
   Command.setCurrentLocation(start);
 
    lytter = new Kommandolytter();
    Container c = getContentPane();
    c.setLayout( new FlowLayout() );
    c.add( new JLabel( "Spill: " ) );
    /*
    Color color=new Color(96,95,70);
    this.setBackground( color);
    */
    title = new JTextArea( 6,31  );
    title.setEditable( false );
    
    utskrift = new JTextArea( 17,31  );
    utskrift.setEditable( false );
   
    c.add(title);
    c.add( new JScrollPane( utskrift ) );
    input = new JTextField( 31 );
    c.add( input );
    
    input.addActionListener( lytter );
    
    setLocationRelativeTo(null);
    setSize( 400, 500 );
    setVisible( true );
    
    start();// gir start verdi til vindu
 
    addWindowListener(new WindowAdapter()
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	});
  }
  public void start()
  {
	  Command.getCurrentLoc().entered(); // registerer at spiller har bes�kt f�rste rom
	  utskrift.append(/*"Dette er et spill, start:"+"\n"
			  		+ */Command.getCurrentLoc().getDescription()
			  		+"\n" + Command.getCurrentLoc().toStringItem());
	  title.append(Command.getCurrentLoc().getTitle() +"\n\n" +
				"Exits:\n" +
				Command.getCurrentLoc().getExits());
  }
  
  public void action()
  {
	  String action = input.getText();
	  if(action.equals("again"))
	  {
		  input.setText(getMemory());
		  action();
		  return;
	  }
	  
	  memory();

	  if(action.equals(""))//(( (input.getText() ) ).trim().length() == 0 )
		  utskrift.append("\n\n->Undefined");
	  else
	  {
		  
		  String[] deler = action.toUpperCase().split(" ");
		  String ut = "";
		  
		  if(deler.length == 1)
			{
				if(Command.getDirection(deler[0]) != -1)
				{
					move(deler[0],action);
					return;
				}	
			}

		  if( ( deler[0].equals("GO") || deler[0].equals("GOTO")) &&
				  Command.getDirection(deler[1]) !=-1 ) 
			{
			  	
			  	move(deler[1],action);
				return;
			}
		  
		//go to <direction>
		  if(deler[0].equals("GO") && deler[1].equals("TO") && 
				  Command.getDirection(deler[2]) != -1)
			{
			  move(deler[2],action);
			  return;
			}
		  /* ********************************/
		  ut = Command.inputHandler(deler, verden.getPlayer(),verden.getRecipe(),action);
		  
		  if(!verden.getPlayer().isAlive()){
			  title.setText(Command.titleLoop(verden.getPlayer()));
			  utskrift.append("\n\n-> "+action+"\n");
			  utskrift.append(ut);
			  dead();
			  return;
		  }
		  if(ut == null){
			  if(deler.length==1)
			  {
				  utskrift.append("\n\n-> "+action+"\n");
				  utskrift.append("I don't know the word '"+ deler[0].toLowerCase()+"'");//something went horribly wrong
				  title.setText(Command.titleLoop(verden.getPlayer()));
			  }
			  else{
				  title.setText(Command.titleLoop(verden.getPlayer()));
				  utskrift.append("\n\n-> "+action+"\n");
				  utskrift.append("I can't understand that sentence");//something went horribly wrong
			  }
			  
			//  dead();
		  }
		  
			  
		  else
		  {
			  title.setText(Command.titleLoop(verden.getPlayer()));
			  utskrift.append("\n\n-> "+action+"\n");
			  utskrift.append(ut);
		  }
		 
		  utskrift.setCaretPosition(utskrift.getDocument().getLength());//oppdaterer siden
		  title.setText(Command.titleLoop(verden.getPlayer()));//oppdaterer siden
		  input.setText("");//nuller ut input
	  }
		
 
  }
  
  public void move(String action, String in)
  {
	  utskrift.append("\n\n-> "+in+"\n");
	  utskrift.append(Command.mainLoop(action,verden.getPlayer()));
	  title.setText(Command.titleLoop(verden.getPlayer()));
	  utskrift.setCaretPosition(utskrift.getDocument().getLength());//oppdaterer siden
	  input.setText("");//nuller ut input
	  if(!verden.getPlayer().isAlive())
		  {
			  dead();
		  }
	  
	  return;
  }
  
  public static void dead()
  {
	  utskrift.append("\nYou are dead!");
	  utskrift.setCaretPosition(utskrift.getDocument().getLength());//oppdaterer siden
	  input.setText("");//nuller ut input
	  input.setEditable(false);
	  input.removeActionListener(lytter);
	  
	  int reply = JOptionPane.showConfirmDialog(null, "Play again?", "Game over", JOptionPane.YES_NO_OPTION);
      if (reply == JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(null, "HAHAH");
      }
      else {
         JOptionPane.showMessageDialog(null, "GOODBYE");
         System.exit(0);
      }
  }
	//Nye metoder
	
	 public void memory()
	 {
		  for(int i = memory.length-1; i > 0; i--)
		  {
			  if(i > 0)
				  memory[i] = memory[i-1];
		  }
		  memory[0] = input.getText();
	 }
	 
	 public String getMemory()
	 {
		  return memory[0];
	 }
	
	  private class Kommandolytter implements ActionListener
	  {
	    public void actionPerformed( ActionEvent e )
	    {
	      if ( e.getSource() == input )
	        action();
	      
	     }
     
	  }
  }
