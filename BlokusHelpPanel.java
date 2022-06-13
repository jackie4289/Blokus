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
	
	//Mini Arrays
	String strBoard[][] = new String[5][5];
	//String intARow[];
	int PieceGrid[][] = new int [4][4];
	int BoardGrid[][] = new int[19][19];
	int TempGrid[][] = new int[19][19];
	
	// Integers
	int intRow;
	int intCol;
	int intCount;
	int intCount2;
	
	//coordinates
	int mouseX = 0;
	int mouseY = 0;
	int intPiece = 1;
	int intDropX;
	int intDropY;
	int intColDrop;
	int intRowDrop;
	
	// Images
	BufferedImage help = null;
	BufferedImage white = null;
	BufferedImage red = null;
	BufferedImage blue = null;
	
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
			System.out.println("Invalid picture(help.png)");
		}
		try{
			white = ImageIO.read(this.getClass().getResourceAsStream("whiteblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(whiteblock.png)");
		}
		try{
			red = ImageIO.read(this.getClass().getResourceAsStream("redblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(redblock.png)");
		}
		try{
			blue = ImageIO.read(this.getClass().getResourceAsStream("blueblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(blueblock.png)");
		}
		
	}
}
