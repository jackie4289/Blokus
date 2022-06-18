// Blokus Login Panel
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

public class BlokusLoginPanel extends JPanel{
	//Properties
	String strName[] = new String[4];
	int intConnected = 0;
	int intPlayerNum;
	String strAddress;
	boolean boolisServer;
	BufferedImage login = null;
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw image
		g.drawImage(login, 0, 0, null);
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
		//Print ip if server
		g.setColor(Color.BLACK);
		if(boolisServer == true){
			g.drawString("Local IP: " + strAddress, 955, 210);
		}
		//Print player list
		if(strName[0] != null){
			g.drawString("" + strName[0],1000, 310);
		}
		if(strName[1] != null){
			g.drawString("" + strName[1],1000, 363);
		}
		if(strName[2] != null){
			g.drawString("" + strName[2],1000, 416);
		}
		if(strName[3] != null){
			g.drawString("" + strName[3],1000, 469);
		}
	}
	
	//Constructor
	public BlokusLoginPanel(){
		super();
		//import images
		try{
			login = ImageIO.read(this.getClass().getResourceAsStream("Assets/panels/login.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(login.png)");
		}
	}
}
