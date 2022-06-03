//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlokusLoginPanel extends JPanel{
	//Properties
	BufferedImage login = null;
	String strP1Name;
	String strP2Name;
	String strP3Name;
	String strP4Name;
	int intConnected = 0;
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw words and image
		g.drawImage(login, 0, 0, null);
		g.setColor(Color.BLACK);
		if(intConnected == 1){
			g.drawString(strP1Name,1000, 290);
		}else if(intConnected == 2){
			g.drawString(strP1Name,1200, 340);
		}else if(intConnected == 3){
			g.drawString(strP1Name,1000, 395);
		}else if(intConnected == 4){
			g.drawString(strP1Name,1000, 450);
		}
		System.out.println(intConnected);
	}
	
	
	
	
	//Constructor
	public BlokusLoginPanel(){
		super();
		//import images
		try{
			login = ImageIO.read(this.getClass().getResourceAsStream("login.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(login.png)");
		}
	}
}
