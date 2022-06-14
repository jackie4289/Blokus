//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.lang.Math;

public class BlokusPanelTest extends JPanel{
	//Properties
	// Arrays
	String strBoard[][] = new String[20][20];
	String intARow[];
	int PieceGrid[][] = new int [4][4];
	int BoardGrid[][] = new int[19][19];
	int TempGrid[][] = new int[19][19];
	// 0 = EMPTY
	// 1 = YELLOW (P1)
	// 2 = GREEN (P2)
	// 3 = BLUE (P3)
	// 4 = RED (P4)
	// Booleans
	boolean boolStartGame = true;
	boolean boolDragAndDrop = false; // read below
	boolean boolRotate = false;
	boolean newPiece = true;
	boolean boolDropped = false;
	boolean boolFirstTime = true;
	boolean boolOverlap = false;
	boolean boolCorner = false;
	boolean boolSide = false;
	
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
	int intPiece = 1;
	
	int intDropX;
	int intDropY;
	int intColDrop;
	int intRowDrop;

	// Strings
	String intLine;
	String strRow;
	String strP1Name;
	String strP2Name;
	String strP3Name;
	String strP4Name;
	
	// Images
	BufferedImage white = null;
	BufferedImage red = null;
	BufferedImage blue = null;
	BufferedImage green = null;
	BufferedImage yellow = null;

	//Methods
	public void paintComponent(/*graphics variable*/Graphics g){
		BlokTest BlokObject = new BlokTest();
		super.paintComponent(g);
		//UI
		//P1 (Yellow)
		g.setColor(new Color(255, 208, 132));
		g.fillRect(0, 0, 640, 360);
		g.setColor(new Color(204, 166, 105));
		g.fillRect(350, 0, 290, 360);
		//P1 NameCard
		g.setColor(new Color(178, 145, 92));
		g.fillRect(0, 0, 350, 40);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25)); 
		g.drawString("P1: " + strP1Name, 15, 30);
		
		//P2 (Green)
		g.setColor(new Color(98, 218, 166));
		g.fillRect(640, 0, 640, 360);
		g.setColor(new Color(75, 166, 127));
		g.fillRect(640, 0, 290, 360);
		//P2 NameCard
		g.setColor(new Color(63, 140, 106));
		g.fillRect(930, 0, 350, 40);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25)); 
		g.drawString("P2: " + strP1Name, 945, 30);
		
		//P3 (Blue)
		g.setColor(new Color(115, 217, 219));
		g.fillRect(640, 360, 640, 360);
		g.setColor(new Color(88, 166, 167));
		g.fillRect(640, 360, 290, 360);
		//P3 NameCard
		g.setColor(new Color(74, 141, 142));
		g.fillRect(930, 680, 350, 40);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25)); 
		g.drawString("P3: " + strP4Name, 945, 710);
		
		//P4 (Red)
		g.setColor(new Color(226, 97, 95));
		g.fillRect(0, 360, 640, 360);
		g.setColor(new Color(185, 94, 93));
		g.fillRect(350, 360, 290, 360);
		//P4 NameCard
		g.setColor(new Color(149, 64, 62));
		g.fillRect(0, 680, 350, 40);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25)); 
		g.drawString("P4: " + strP4Name, 15, 710);
		
		//Board
		g.setColor(Color.BLACK);
		g.drawRect(369, 26, 541, 541);
		g.drawRect(350, 0, 580, 719);
		//ChatBox
		g.drawRect(369, 580, 541, 135);
		//Logo
		g.drawString("BLOKUS", 588, 23);
		
		// SET BOARD ARRAY TO 0
		for(intCount = 0;intCount < 19; intCount++){
			for(intCount2 = 0;intCount2 < 19; intCount2++){
				BoardGrid[intCount][intCount2] = 0;
				TempGrid[intCount][intCount2] = 0;
			}
		}
		
		// THIS SECTION READS THE MAP AS THE GAME STARTS, SHOULD FOLLOW THE .csv FILE VALUES			
			//Import Array
		if(boolFirstTime == true){
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
			boolFirstTime = false;
		}
		
		//GAME
		boolStartGame = true;
		if(boolStartGame == true){
		
		/*
			// THIS SECTION READS THE MAP AS THE GAME STARTS, SHOULD FOLLOW THE .csv FILE VALUES			
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
		*/

			//Draw array
			for(intRow = 0; intRow < 20; intRow++){
				for(intCol = 0; intCol < 20; intCol++){
					if(strBoard[intRow][intCol].equals("0")){
						g.drawImage(white, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("1")){
						g.drawImage(yellow, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(green, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("3")){
						g.drawImage(blue, 370 + intCol * 27, 27 + intRow * 27, null);
					}else if(strBoard[intRow][intCol].equals("4")){
						g.drawImage(red, 370 + intCol * 27, 27 + intRow * 27, null);
					}
				}
			}
			//Draw Pieces
			if(newPiece == true){
				PieceGrid = BlokObject.PickPiece(intPiece);
				System.out.println("PieceGrid found");
				newPiece = false;
			}
			/*
			if(dropped == true){
				if(mouseX > 369 && mouseX < 910){
					if(mouseY > 26 && mouseY < 567){
						
						intPiece = intPiece + 1;
						newPiece = true;
					}
				}
				dropped = false;		
			}
			*/
			
			if(boolDragAndDrop == true){ // this will be set by mousepressed or mouse released
				
				// Corners for boarder: g.drawRect(369, 26, 541, 541);
				// So all four: (369,26)            (910,26)
				//              (369,567)           (910,567)
				//
				// CURSOR WILL CONTROL BOTTOM LEFT CORNER OF THE PIECE
				// (54,81)
				if(boolRotate == true){
					System.out.println("ROTATE AGAIN");
					PieceGrid = BlokObject.rotatePiece(PieceGrid);
					boolRotate = false;
				}
				if(mouseX < 369 || mouseX > 910){
					for(intCount = 0; intCount < 5; intCount++){
					System.out.println("OUT");
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							System.out.print(PieceGrid[intCount][intCount2]);
							if(PieceGrid[intCount][intCount2] == 1){
								g.drawImage(yellow, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
							}else{
								g.drawImage(blue, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}
						}
					}
				}else if(mouseY < 26 || mouseY > 567){
					for(intCount = 0; intCount < 5; intCount++){
						System.out.println("OUT");
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							System.out.print(PieceGrid[intCount][intCount2]);
							if(PieceGrid[intCount][intCount2] == 1){
								g.drawImage(yellow, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}else{
								g.drawImage(blue, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}
						}
					}
					System.out.println("OUT");
				}else{
					System.out.println("IN");
					for(intCount = 0; intCount < 5; intCount++){
						System.out.println();
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							System.out.print(PieceGrid[intCount][intCount2]);
							if(PieceGrid[intCount][intCount2] == 1){
								g.drawImage(yellow, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}else{
								g.drawImage(blue, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}
						}
					}
				}
			}/*else if(boolDragAndDrop == false && boolDropped == true){
				for(intCount = 0; intCount < 5; intCount++){
					System.out.println();
					for(intCount2 = 0; intCount2 < 5; intCount2++){
						System.out.print(PieceGrid[intCount][intCount2]);
						if(PieceGrid[intCount][intCount2] == 1){
							g.drawImage(yellow, -68 + (27*intCount2) + intDropX, -68 + (27*intCount) + intDropY, null);
						}
					}
				}
			}*/
			
			if(boolDropped == true){
//change the board array instead...
				//place the values (not 0) of piece grid into the board array 
				
				System.out.println("piece dropped: drop x "+intDropX+" | drop y " +intDropY);
				
				intColDrop= Math.round((intDropX-369)/27);
				intRowDrop = Math.round((intDropY-26)/27); 		
				
				System.out.println("Column"+intColDrop+" | Row " +intRowDrop);
				boolOverlap = false;	
				for(intCount = 0; intCount < 5; intCount++){
					System.out.println();
					for(intCount2 = 0; intCount2 < 5; intCount2++){
						if(PieceGrid[intCount][intCount2] == 1){
							//check for overlap
							if(strBoard[(intRowDrop-2)+intCount][(intColDrop-2)+intCount2] == "1"){
								boolOverlap = true;
							}
						}
					}
				}
				
				if(boolOverlap == false){
					boolCorner = false;
					boolSide = false;
					for(intCount = 0; intCount < 5; intCount++){
						System.out.println();
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(PieceGrid[intCount][intCount2] == 1){
							//determine row & column based on mouse drop (x,y) coordinates.
								//build from intRow & intCol
								if(strBoard[(intRowDrop-2)+intCount-1][(intColDrop-2)+intCount2] == "1"){
									boolSide = true;
								}else if(strBoard[(intRowDrop-2)+intCount+1][(intColDrop-2)+intCount2] == "1"){
									boolSide = true;
								}else if(strBoard[(intRowDrop-2)+intCount][(intColDrop-2)+intCount2+1] == "1"){
									boolSide = true;
								}else if(strBoard[(intRowDrop-2)+intCount][(intColDrop-2)+intCount2-1] == "1"){
									boolSide = true;
								}
							}
						}
					}
					
					if(boolCorner == true){
						boolCorner = false;
					}else{
						boolCorner = true;
					}
					
					if(boolCorner == true && boolSide == false){
						for(intCount = 0; intCount < 5; intCount++){
							System.out.println();
							for(intCount2 = 0; intCount2 < 5; intCount2++){
								System.out.print(PieceGrid[intCount][intCount2]);
								if(PieceGrid[intCount][intCount2] == 1){
								//determine row & column based on mouse drop (x,y) coordinates.
									//build from intRow & intCol
									strBoard[(intRowDrop-2)+intCount][(intColDrop-2)+intCount2] = "1";
									
								}
							}
						}
					}
				
					System.out.println("board array");
					
					for(intRow = 0; intRow < 20; intRow++){
						System.out.println("");
						for(intCol = 0; intCol < 20; intCol++){
							System.out.print(strBoard[intRow][intCol]);
						}
					}
						
		// fix errors... array index out of bounds

										
						if(mouseX > 369 && mouseX < 910){
							if(mouseY > 26 && mouseY < 567){
								if(intPiece > 22){
									intPiece = 1;
								}else{	
									intPiece = intPiece + 1;
								}						
								newPiece = true;
							}
						}
						boolDropped = false;
				}
			}		
			
		}else{
			//game not started
		}
	}
	//Constructor
	public BlokusPanelTest(){
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
