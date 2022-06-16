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
	String strMiniBoard[][] = new String[5][5];
	//String intARow[];
	int PieceGrid[][] = new int [4][4];
	int BoardGrid[][] = new int[5][5];
	int TempGrid[][] = new int[5][5];
	
	// Integers
	int intRow;
	int intCol;
	int intCount;
	int intCount2;
	
	//coordinates
	int mouseX = 0;
	int mouseY = 0;
	int pieceX = 450;
	int pieceY = 300;
	int intPiece = 1;
	int intDropX;
	int intDropY;
	int intColDrop;
	int intRowDrop;
	
	boolean boolRotate = false;
	boolean boolFirstTime = true;
	
	// Images
	BufferedImage help = null;
	BufferedImage white = null;
	BufferedImage red = null;
	BufferedImage blue = null;
	
	//Methods
	
	public void paintComponent(Graphics g){
		BlokTest2 BlokObject = new BlokTest2();
		super.paintComponent(g);
		g.drawImage(help, 0, 0, null);
		
		//set blank board
		for(intCount = 0; intCount < 5; intCount++){
			for(intCount2 = 0; intCount2 < 5; intCount2++){
				strMiniBoard[intCount][intCount2] = "0";
			}
		}
		
		for(intRow = 0; intRow < 5; intRow++){
			for(intCol = 0; intCol < 5; intCol++){
				if(strMiniBoard[intRow][intCol].equals("0")){
					g.drawImage(white, 250 + intCol * 27, 270 + intRow * 27, null);
				}else if(strMiniBoard[intRow][intCol].equals("1")){
					g.drawImage(blue, 250 + intCol * 27, 270 + intRow * 27, null);
				}else if(strMiniBoard[intRow][intCol].equals("2")){
					g.drawImage(red, 250 + intCol * 27, 270 + intRow * 27, null);
				}
			}
		}	
		
		
		//draw piece
		
		//test
		
		g.drawImage(blue, pieceX, pieceY, null);
		
		if(mouseX > pieceX - 50 && mouseX < pieceX +50){
			if(mouseY > pieceY-50 && mouseY < pieceY+50){
				pieceX = mouseX;
				pieceY = mouseY;
			}
		}
		if(pieceX > 600 || pieceX < 100){
			pieceX = 450;
			pieceY = 300;
		}else if(pieceY > 500 || pieceY < 200){
			pieceX = 450;
			pieceY = 300;
		}
		
		if(boolFirstTime == true){
			PieceGrid = BlokObject.PickPiece(4);
			boolFirstTime = false;
		}
		
		if(boolRotate == true){
			System.out.println("ROTATE AGAIN");
			PieceGrid = BlokObject.rotatePiece(PieceGrid);
			boolRotate = false;
		}
		
		for(intCount = 0; intCount < 5; intCount++){
			System.out.println();
			for(intCount2 = 0; intCount2 < 5; intCount2++){
				System.out.print(PieceGrid[intCount][intCount2]);
				if(PieceGrid[intCount][intCount2] == 1){
					g.drawImage(red, 750 + intCount*27, 270 + intCount2*27, null);	
				}else{
					g.drawImage(white, 750 + intCount*27, 270 +intCount2*27,null);
				}
			}
		}
		
		
		
		
		
		
	}

	//Constructor
	public BlokusHelpPanel(){
		super();
		//import images
		try{
			help = ImageIO.read(this.getClass().getResourceAsStream("Assets/panels/help.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(help.png)");
		}
		try{
			white = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/whiteblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(whiteblock.png)");
		}
		try{
			red = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/redblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(redblock.png)");
		}
		try{
			blue = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/blueblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(blueblock.png)");
		}
		
	}
}
