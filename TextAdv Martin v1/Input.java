import java.awt.*;
import java.awt.event.*;


import javax.swing.*;

public class Input extends JFrame
{
 
  private JTextField input;
  private static JTextArea utskrift;
  private Kommandolytter lytter;
  //private JScrollPane scrollPane;
  
  
  

  public Input()
  {
    super( "Text Adventure Game" );
 
    lytter = new Kommandolytter();
    Container c = getContentPane();
    c.setLayout( new FlowLayout() );
    c.add( new JLabel( "Spill: " ) );
    
    utskrift = new JTextArea( 17,31  );
    utskrift.setEditable( false );
   
    
    c.add( new JScrollPane( utskrift ) );
    input = new JTextField( 31 );
    c.add( input );
    
    input.addActionListener( lytter );
    
    setLocationRelativeTo(null);
    setSize( 400, 400 );
    setVisible( true );
    
 
    addWindowListener(new WindowAdapter()
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	});
  }
  
  public void splitUp()
  {
	  String inn = input.getText();
	  String[] deler = inn.toUpperCase().split(" ");
	  String ut= "";
	  
	  for(int i = 0; i<deler.length;i++)
	  {
		 ut += deler[i] + "\n";
	  }
	  if(deler.length >=7)
	  {
		  utskrift.append("-> " + inn);
		  utskrift.append("\n for lang setning");
	  }
	  else
	  {
		  utskrift.append("-> "+inn);
		  utskrift.append("\n=\n"+ut);
	  }
	  utskrift.setCaretPosition(utskrift.getDocument().getLength());
	  input.setText("");
	  
  }


private class Kommandolytter implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      if ( e.getSource() == input )
        splitUp();
      
     }
     
    }
  public static void main(String[]args)
  {
	  Input ny = new Input();
  }
  }
