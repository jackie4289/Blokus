import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class drawingStuff implements ActionListener, MouseMotionListener, MouseListener{
	//Properties
	JFrame theFrame = new JFrame("Drawing Panel");
	JDrawingPanel thePanel = new JDrawingPanel();
	//60 frames per second
	Timer theTimer = new Timer(1000/60, this);
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Methods
	
	//Every 6
	 //frames, refresh the screen
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			//repaint=paintComponent method
			thePanel.repaint();
		}
	}

	public void mousePressed(MouseEvent evt){
		//If they press down inside the panel...
		if(evt.getSource()==thePanel){
			if(thePanel.blnActive==false){
				//Get rid of the block in that spot so we can paint another block that we can move
				thePanel.ridBlock(evt.getX(), evt.getY());
			}
		}
	}
	
	public void mouseDragged(MouseEvent evt){
		//If they drag across the panel...
		if(evt.getSource()==thePanel){
			//Get the x and y coordinates
			//In the repaint command, we will paint with these coordinates in the panel
			if(thePanel.blnActive==true){
				thePanel.intX = evt.getX()-100;
				thePanel.intY = evt.getY()-100;
			}
		}
	}
	public void mouseReleased(MouseEvent evt){
		//if they let go in the panel...
		if(evt.getSource()==thePanel){
			if(thePanel.blnActive==true){
				//check which area in the grid your piece is in
				//make that area true in the array to signify that part of the grid is filled
				thePanel.placeInSlot();
			}
		}
	}
	
	//Useless methods that I still need to override by making them empty XD
	public void mouseClicked(MouseEvent evt){}	
	public void mouseMoved(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Constructor
	public drawingStuff(){
		thePanel.setLayout(null);
		//Add mouse listeners
		thePanel.addMouseMotionListener(this);
		thePanel.addMouseListener(this);
		
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thePanel.setPreferredSize(new Dimension(600,600));
		theFrame.setResizable(false);
		theFrame.setContentPane(thePanel);
		
		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args){
		new drawingStuff();
	}
}
