//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlokusPanel extends JPanel{
	//Properties
	// Arrays
	String strBoard[][] = new String[20][20];
	String intARow[];
	int PieceGrid[][] = new int [4][4];
	// 0 = EMPTY
	// 1 = RED (P1)
	// 2 = BLUE (P2)
	// Booleans
	boolean boolStartGame = false;
	boolean boolYourTurn = true; // we can change this later so that bool value is decided within later code
	
	// Integers
	int intRow;
	int intCol;
	int intCount;
	int intCount2;

	//Player coords
	int P1X;
	int P1Y;
	int P2X;
	int P2Y;
	int P3X;
	int P3Y;
	int P4X;
	int P4Y;
	
	int mouseX = 0;
	int mouseY = 0;
	int intPiece = 0;

	// Strings
	String intLine;
	String strRow;

	// Images
	BufferedImage white = null;
	BufferedImage red = null;
	BufferedImage blue = null;
	BufferedImage green = null;
	BufferedImage yellow = null;

	//Methods
	public void paintComponent(/*graphics variable*/Graphics g){
		Blok BlokObject = new Blok();
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(369, 26, 541, 541);
		//ChatBox
		g.drawRect(369, 580, 541, 135);
		boolStartGame = true;
		
		if(boolStartGame == true){
			// THIS SECTION READS THE MAP AS THE GAME STARTS, SHOULD FOLLOW THE .csv FILE VALUES
			// - we should add a button to set boolStartGame to true, so the game starts (we could use a button)

			//Import Array
			BufferedReader board = null;
			try{
				board = new BufferedReader(new FileReader("board.csv"));
			}catch(FileNotFoundException e){
				System.out.println("File not found!!!");
			}

			//read array
			for(intCount = 0; intCount < 20; intCount++){
				try{
					intLine = board.readLine();
					intARow = intLine.split(",");
				}catch(IOException e){
					intLine = "0";
				}
				for(intCount2 = 0; intCount2 < 20; intCount2++){
					strBoard[intCount][intCount2] = intARow[intCount2];
				}
			}

			//Draw array
			for(intRow = 0; intRow < 20; intRow++){
				for(intCol = 0; intCol < 20; intCol++){
					if(strBoard[intRow][intCol].equals("0")){
						g.drawImage(white, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("1")){
						g.drawImage(red, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(blue, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(green, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(yellow, 370 + intCol * 27, 27 + intRow * 27, null);
					}
				}
			}
			PieceGrid = BlokObject.PickPiece(intPiece);
			
		}else{
			//game not started
		}
	}
	//Constructor
	public BlokusPanel(){
		super();
		//import images
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
		try{
			yellow = ImageIO.read(this.getClass().getResourceAsStream("yellowblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(blueblock.png)");
		}
		try{
			green = ImageIO.read(this.getClass().getResourceAsStream("greenblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(blueblock.png)");
		}
	}
}
