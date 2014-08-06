
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

public class Vindu extends JFrame
{
	World verden = new World();
	Command com = new Command();
	Location start = verden.getStart();
	
	  
  private static JTextField input;
  private static JTextArea utskrift, title;
  private Kommandolytter lytter;
  //private JScrollPane scrollPane;
  
  
  

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
	  Command.getCurrentLoc().entered(); // registerer at spiller har besøkt første rom
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
		// turn on <item> && light <item>***************
			if(deler.length == 3 && deler[0].equals("TURN")&& deler[1].equals("ON"))
			{
					  utskrift.append("\n ->  "+ action);
					  String n = Command.turnOnLight(deler[2],verden.getPlayer());
					  if(n!=null)
						  utskrift.append(n);
					  else if(Command.getCurrentLoc().getLight())
						  utskrift.append("\nYour light is on");
					  else 
						  {
						  title.setText(Command.titleLoop(verden.getPlayer()));
						  utskrift.append("\nYour light is now on an you can se around the room\n");
					  	  utskrift.append(Command.mainLoop("",verden.getPlayer()));
						  }
					  utskrift.setCaretPosition(utskrift.getDocument().getLength());
					  input.setText("");
					  return;
				  }
			if( deler.length == 2 && deler[0].equals("LIGHT"))
			{
				utskrift.append("\n ->  "+ action);
				  String n = Command.turnOnLight(deler[1],verden.getPlayer());
				  if(n!=null)
					  utskrift.append(n);
				  else if(Command.getCurrentLoc().getLight())
					  utskrift.append("\nYour light is on");
				  else 
					  {
					  title.setText(Command.titleLoop(verden.getPlayer()));
					  utskrift.append("\nYour light is now on an you can se around the room\n");
				  	  utskrift.append(Command.mainLoop("",verden.getPlayer()));
					  }
				  utskrift.setCaretPosition(utskrift.getDocument().getLength());
				  input.setText("");
				  return;
			}// turn off <item> 
			if(deler.length == 3 && deler[0].equals("TURN")&& deler[1].equals("OFF"))
			{
					  utskrift.append("\n ->  "+ action);
					  String n = Command.turnOffLight(deler[2],verden.getPlayer());
					  if(n!=null)
						  utskrift.append(n);
					  else if(Command.getCurrentLoc().getLight())
						  utskrift.append("\nYour light is off");
					  else 
						  {
						  title.setText(Command.titleLoop(verden.getPlayer()));
						  utskrift.append("\nYour light is off, and its black...\n");
					  	  utskrift.append(Command.mainLoop("",verden.getPlayer()));
						  }
					  utskrift.setCaretPosition(utskrift.getDocument().getLength());
					  input.setText("");
					  return;
				  }
		  /* ********************************/
		  ut = Command.inputHandler(deler, verden.getPlayer());
		  if(ut == null)
			  utskrift.append("\n\n->something went horribly wrong");
		  else
		  {
			  utskrift.append("\n\n-> "+action+"\n");
			  utskrift.append(ut);
		  }
		 
		  utskrift.setCaretPosition(utskrift.getDocument().getLength());//oppdaterer siden
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
	  return;
  }
  public boolean moving(String[] deler, String action)
  {
	  if(deler.length==1)
		{
			if(Command.getDirection(deler[0]) != -1)
				move(deler[0],action);
				return true;
				
		}
	  if( ( deler[0].equals("GO") || deler[0].equals("GOTO")) &&
			  Command.getDirection(deler[1]) !=-1 ) 
		{
		  	
		  	move(deler[1],action);
			return true;
		}
	//go to <direction>
	  if(deler[0].equals("GO") && deler[1].equals("TO") && 
			  Command.getDirection(deler[2]) != -1)
		{
		  move(deler[2],action);
		  return true;
		}
	  return false;
  }
  public static void dead()
  {
	  utskrift.append("\nYou are dead!");
	  utskrift.setCaretPosition(utskrift.getDocument().getLength());//oppdaterer siden
	  input.setText("");//nuller ut input
	  input.setEditable(false);
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
