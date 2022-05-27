import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlockusPanel extends JPanel{
	//Properties
		
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(280, 1, 720, 718);
		g.setColor(Color.WHITE);
		g.fillRect(281,2,719,717);
	}
	
	//Constructor
	public BlockusPanel(){
		super(); 
	}
}
