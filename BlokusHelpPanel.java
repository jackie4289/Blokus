//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlokusHelpPanel extends JPanel{
	//Properties
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
	}

	//Constructor
	public BlokusHelpPanel(){
		super();
		//import images
		/*
		try{
		= ImageIO.read(this.getClass().getResourceAsStream("menu.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(menu.png)");
		}
		*/
	}
}
