// Blokus Menu Panel
// Authors: Lucas Moran, Kelsie Fung, Jackie Lin
// ICS 4U1
// June 16, 2022
// Version 1.231

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlokusMenuPanel extends JPanel{
	//Properties
	BufferedImage menu = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw words and image
		g.drawImage(menu, 0, 0, null);
	}

	//Constructor
	public BlokusMenuPanel(){
		super();
		//import images
		try{
			menu = ImageIO.read(this.getClass().getResourceAsStream("Assets/panels/menu.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(menu.png)");
		}
	}
}
