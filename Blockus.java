//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class Blockus implements ActionListener, MouseListener, KeyListener{
	//Properties
	JFrame theFrame = new JFrame("Blockus");
	JTextField usernameField = new JTextField();
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	BlockusPanel thePanel = new BlockusPanel();
	BlockusMenuPanel theMenuPanel = new BlockusMenuPanel();
	BlockusLoginPanel theLoginPanel = new BlockusLoginPanel();
	Timer theTimer = new Timer(1000/60, this);
	Block BlockModel;

	//J Properties
	JButton startButton = new JButton("Start");
	JButton quitButton = new JButton("Quit");

	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}else if(evt.getSource() == quitButton){
			System.exit(0);
		}else if(evt.getSource() == startButton){
			theMenuPanel.setVisible(false);
			theLoginPanel.setVisible(true);
			//FOR TESTING CHANGE TO (thePanel)
			theFrame.setContentPane(theLoginPanel);		
		}
	}
	public void mouseExited(MouseEvent evt){

	}
	public void mouseEntered(MouseEvent evt){

	}
	public void mousePressed(MouseEvent evt){

	}
	public void mouseReleased(MouseEvent evt){

	}
	public void mouseClicked(MouseEvent evt){

	}
	public void keyReleased(KeyEvent evt){

	}
	public void keyPressed(KeyEvent evt){

	}
	public void keyTyped(KeyEvent evt){

	}


	//Constuctor
	public Blockus(){
		//Game panel
		thePanel.setLayout(null);
		thePanel.setPreferredSize(new Dimension(1280, 720));
		thePanel.setVisible(false);
		
		//Menu Panel
		theMenuPanel.setLayout(null);
		theMenuPanel.setPreferredSize(new Dimension(1280, 720));
		theMenuPanel.setVisible(true);
		
		//Login Panel
		theLoginPanel.setLayout(null);
		theLoginPanel.setPreferredSize(new Dimension(1280, 720));
		theLoginPanel.setVisible(false);
		
		theFrame.setContentPane(theMenuPanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
		//Start Button Menu
		startButton.setSize(300, 50);
		startButton.setLocation(170, 500);
		startButton.addActionListener(this);
		theMenuPanel.add(startButton);
		
		//Quit Button Menu
		quitButton.setSize(300, 50);
		quitButton.setLocation(170, 555);
		quitButton.addActionListener(this);
		theMenuPanel.add(quitButton);

		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
	}

	//Main Program
	public static void main(String[] args){
		Blockus Block = new Blockus();
	}
}
