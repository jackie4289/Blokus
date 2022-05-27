import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class Blockus implements ActionListener{
	//Properties
	JFrame theFrame = new JFrame("Blockus");
	BlockusPanel thePanel = new BlockusPanel();
	Timer theTimer = new Timer(1000/60, this);
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
	}
	
	//Constuctor 
	public Blockus(){
		thePanel.setLayout(null);
		thePanel.setPreferredSize(new Dimension(1280, 720));
		theFrame.setContentPane(thePanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
	}
	
	//Main Program
	public static void main(String[] args){
		Blockus Block = new Blockus();
	}  
}
	

