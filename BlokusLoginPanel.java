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
	String strName[] = new String[4];
	String strTemp;
	int intConnected = 0;

	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw words and image
		g.drawImage(login, 0, 0, null);
		g.setColor(Color.BLACK);
		//Print player list
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
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
			login = ImageIO.read(this.getClass().getResourceAsStream("login.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(login.png)");
		}
	}
}
