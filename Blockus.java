//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class Blockus implements ActionListener, MouseListener, KeyListener{
	//Properties
	JFrame theFrame = new JFrame("Blockus");
	BlockusPanel thePanel = new BlockusPanel();
	BlockusMenuPanel theMenuPanel = new BlockusMenuPanel();
	Timer theTimer = new Timer(1000/60, this);
	Block BlockModel;

	//J Properties
	JButton startButton = new JButton("Start");


	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			thePanel.repaint();
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
		thePanel.setLayout(null);
		thePanel.setPreferredSize(new Dimension(1280, 720));
		theMenuPanel.setLayout(null);
		theMenuPanel.setPreferredSize(new Dimension(1280, 720));
		theFrame.setContentPane(thePanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
	
		
		startButton.setSize(300, 50);
		startButton.setLocation(330, 120);
		startButton.addActionListener(this);
		theMenuPanel.add(startButton);

		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
	}

	//Main Program
	public static void main(String[] args){
		Blockus Block = new Blockus();
	}
}
