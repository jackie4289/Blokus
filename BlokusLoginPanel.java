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
	String strName[] = new String[3];
	int intConnected = 0;
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw words and image
		g.drawImage(login, 0, 0, null);
		g.setColor(Color.BLACK);
		if(intConnected == 1){
			g.drawString("" + strName[0],1000, 290);
		}else if(intConnected == 2){
			g.drawString("" + strName[0],1000, 290);
			g.drawString("" + strName[1],1000, 340);
		}else if(intConnected == 3){
			g.drawString("" + strName[0],1000, 290);
			g.drawString("" + strName[1],1000, 340);
			g.drawString("" + strName[2],1000, 395);
		}else if(intConnected == 4){
			g.drawString("" + strName[0],1000, 290);
			g.drawString("" + strName[1],1000, 340);
			g.drawString("" + strName[2],1000, 395);
			g.drawString("" + strName[3],1000, 450);
		}
	
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
