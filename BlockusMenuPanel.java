//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlockusMenuPanel extends JPanel{
	//Properties
	BufferedImage menu = null;
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw words and image
		g.drawImage(menu, 0, 0, null);
		System.out.println("djaoiw");
	}
	
	
	
	
	//Constructor
	public BlockusMenuPanel(){
		super();
		//import images
		try{
			menu = ImageIO.read(new File("menu.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(menu.png)");
		}
	}
}
