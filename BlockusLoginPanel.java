//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlockusLoginPanel extends JPanel{
	//Properties
	BufferedImage login = null;
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw words and image
		g.drawImage(login, 0, 0, null);
	}
	
	
	
	
	//Constructor
	public BlockusLoginPanel(){
		super();
		//import images
		try{
			login = ImageIO.read(new File("login.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(login.png)");
		}
	}
}
