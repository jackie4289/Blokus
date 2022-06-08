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
	BufferedImage help = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(help, 0, 0, null);
	}

	//Constructor
	public BlokusHelpPanel(){
		super();
		//import images
		try{
			help = ImageIO.read(this.getClass().getResourceAsStream("help.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(menu.png)");
		}
	}
}
