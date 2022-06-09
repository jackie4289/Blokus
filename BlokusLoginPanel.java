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
	int intConnected = 0;
	int intPlayerNum;
	boolean boolFirstTime = true;

	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw image
		g.drawImage(login, 0, 0, null);
		g.setColor(Color.BLACK);
		//Print player list
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
		if(strName[0] != null){
			g.drawString("" + strName[0],1000, 310);
			
			/*if(boolFirstTime == true){
				intPlayerNum = 1;
				boolFirstTime = false;
			}*/
		}
		if(strName[1] != null){
			g.drawString("" + strName[1],1000, 363);
			
			/*if(boolFirstTime == true){
				if(intPlayerNum != 1){
					intPlayerNum = 2;
					boolFirstTime = false;
				}
			}*/
		}
		if(strName[2] != null){
			g.drawString("" + strName[2],1000, 416);
			
			/*if(boolFirstTime == true){
				if(intPlayerNum != 3){
					intPlayerNum = 3;
					boolFirstTime = false;
				}

			}*/
		}
		if(strName[3] != null){
			g.drawString("" + strName[3],1000, 469);
			
			/*if(boolFirstTime == true){
				if(intPlayerNum != 4){
					intPlayerNum = 4;
					boolFirstTime = false;
				}
			}*/
			
		}
		//System.out.println(boolFirstTime);
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
