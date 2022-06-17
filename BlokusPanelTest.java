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
	String strBoard[][] = new String[24][24];
	String intARow[];
	int PieceGrid[][] = new int [4][4];
	int BoardGrid[][] = new int[23][23];
	int TempGrid[][] = new int[23][23];
	int PlayerGrid[][] = new int[4][4];
	int PieceTaken[][] = new int[4][22]; // (int [player#] [piece#])
	String strSidePieces[][] = new String[14][14];
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
	boolean boolFirstCorner = false;
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
	int intPlayerCount = 1;
	
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
		
		
		// SET BOARD ARRAY TO 0
		for(intCount = 0;intCount < 23; intCount++){
			for(intCount2 = 0;intCount2 < 23; intCount2++){
				BoardGrid[intCount][intCount2] = 0;
				TempGrid[intCount][intCount2] = 0;
			}
		}
		
		// THIS SECTION READS THE MAP AS THE GAME STARTS, SHOULD FOLLOW THE .csv FILE VALUES			
			//Import Array
		if(boolFirstTime == true){
			BufferedReader board = null;
			BufferedReader sideLayout = null;
			try{
				board = new BufferedReader(new FileReader("Data_Files/board.csv"));
			}catch(FileNotFoundException e){
				System.out.println("File not found!!!");
			}
			try{
				sideLayout = new BufferedReader(new FileReader("Data_Files/sidepieces.csv"));
			}catch(FileNotFoundException e){
				System.out.println("File not found!!!");
			}
			//read array
			intRow = 0;
			intCol = 0;
			for(intCount = 0; intCount < 20; intCount++){
				try{
					intLine = board.readLine();
					intARow = intLine.split(",");
				}catch(IOException e){
					intLine = "0";
				}
				for(intCount2 = 0; intCount2 < 20; intCount2++){
					strBoard[intCount+2][intCount2+2] = intARow[intCount2];
				}
			}
			
			// Set player pieces to all on
			for(intCount = 1; intCount < 22; intCount++){
				for(intCount2 = 0; intCount2 < 4; intCount2++){
					PieceTaken[intCount2][intCount] = 1;
				}
			}
		}
		
		BufferedReader sidepieces = null;
		try{
			sidepieces = new BufferedReader(new FileReader("Data_Files/sidepieces.csv"));
		}catch(FileNotFoundException e){
			System.out.println("File not found!!!");
		}
		
		//read array
		intRow = 0;
		intCol = 0;
		for(intCount = 0; intCount < 14; intCount++){
			try{
				intLine = sidepieces.readLine();
				intARow = intLine.split(",");
			}catch(IOException e){
				intLine = "0";
			}
			System.out.println("");
			for(intCount2 = 0; intCount2 < 14; intCount2++){
				strSidePieces[intCount][intCount2] = intARow[intCount2];
				System.out.print(strSidePieces[intCount][intCount2]);
			}
		}
		
		//GAME
		boolStartGame = true;
		if(boolStartGame == true){
			//REMOVE LINE BELOW WHEN INTEGRATING
			intPlayerCount = 0;
			
			// UI
			g.setColor(Color.WHITE);
			g.fillRect(0,0,1280,720);
			
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
			
			//Draw Player pieces
			for(intCount2 = 0; intCount2 < 4; intCount2++){ // Player nums 0-3
				for(intCount = 1;intCount < 22;intCount++){ // Piece Nums 1-21
					if(PieceTaken[intCount2][intCount] == 1){
						PlayerGrid = BlokObject.PickPiece(intCount);
						if(intCount2 == 0){ // Player 1
							if(intCount == 1){
								for (intCol = 0; intCol < 5; intCol++){
									for (intRow = 0; intRow < 5; intRow++){
										if(PlayerGrid[intCol][intRow] == 1){
											g.drawImage(yellow, 1 + (intCol-2) * 27, 40 + (intRow-2) * 27, null);
										}
									}
								}
							}else if(intCount == 2){
								
							}else if(intCount == 3){
								
							}else if(intCount == 4){
								
							}else if(intCount == 5){
								
							}else if(intCount == 6){
								
							}else if(intCount == 7){
								
							}else if(intCount == 8){
								
							}else if(intCount == 9){
								
							}else if(intCount == 10){
								
							}else if(intCount == 11){
								
							}else if(intCount == 12){
								
							}else if(intCount == 13){
								
							}else if(intCount == 14){
								
							}else if(intCount == 15){
								
							}else if(intCount == 16){
								
							}else if(intCount == 17){
								
							}else if(intCount == 18){
								
							}else if(intCount == 19){
								
							}else if(intCount == 20){
								
							}else if(intCount == 21){
								
							}
						}else if(intCount2 == 1){ // Player 2
							
						}else if(intCount2 == 2){ // Player 3
							
						}else if(intCount2 == 3){ // Player 4
							
						}
					}
				}
			}
			
			
			//Draw array
			for(intRow = 2; intRow <22; intRow++){
				for(intCol = 2; intCol <22; intCol++){
					if(strBoard[intRow][intCol].equals("0")){
						g.drawImage(white, 370 + (intCol-2) * 27, 27 + (intRow-2) * 27, null);
					}else if(strBoard[intRow][intCol].equals("1")){
						g.drawImage(yellow, 370 + (intCol-2) * 27, 27 + (intRow-2) * 27, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(green, 370 + (intCol-2) * 27, 27 + (intRow-2) * 27, null);
					}else if(strBoard[intRow][intCol].equals("3")){
						g.drawImage(blue, 370 + (intCol-2) * 27, 27 + (intRow-2) * 27, null);
					}else if(strBoard[intRow][intCol].equals("4")){
						g.drawImage(red, 370 + (intCol-2) * 27, 27 + (intRow-2) * 27, null);
					}
				}
			}
			//Draw Pieces
			if(newPiece == true){
				PieceGrid = BlokObject.PickPiece(intPiece);
				System.out.println("PieceGrid found");
				newPiece = false;
			}
			
			
			if(boolDragAndDrop == true){ // this will be set by mousepressed or mouse released
				
				// Corners for boarder: g.drawRect(369, 26, 541, 541);
				// So all four: (369,26)            (910,26)
				//              (369,567)           (910,567)
				//
				// CURSOR WILL CONTROL BOTTOM LEFT CORNER OF THE PIECE
				// (54,81)
				
				PieceTaken[intPlayerCount][intPiece] = 0;
				if(boolRotate == true){
					System.out.println("ROTATE AGAIN");
					PieceGrid = BlokObject.rotatePiece(PieceGrid);
					boolRotate = false;
				}
				if(mouseX < 369 || mouseX > 910){
					System.out.println("OUT");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(PieceGrid[intCount][intCount2] == 1){
								g.drawImage(yellow, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
							}
						}
					}
				}else if(mouseY < 26 || mouseY > 567){
					System.out.println("OUT");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(PieceGrid[intCount][intCount2] == 1){
								g.drawImage(yellow, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}
						}
					}
				}else{
					System.out.println("IN");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(PieceGrid[intCount][intCount2] == 1){
								g.drawImage(yellow, -68 + (27*intCount2) + mouseX, -68 + (27*intCount) + mouseY,null);
							}
						}
					}
				}
			}
			
			if(boolDropped == true){
				int intTurn = 0;
				intTurn++;
				System.out.println("Turn #: "+intTurn);
				//change the board array instead...
				//place the values (not 0) of piece grid into the board array 
				
				System.out.println("piece dropped");
				System.out.println("drop x "+intDropX+" | drop y " +intDropY);
				
				intColDrop= Math.round((intDropX-369)/27);
				intRowDrop = Math.round((intDropY-26)/27); 









						
				
				System.out.println("Column"+intColDrop+" | Row " +intRowDrop);
				
				if(boolFirstTime == true){
					System.out.println("BOOLFIRSTIME STARTED");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(PieceGrid[intCount][intCount2] == 1){
								//check for overlap
								if(intRowDrop + intCount - 2 == 0 && intColDrop+ intCount2 - 2 == 0){
									boolFirstCorner = true;
								}
							}
						}
					}
					System.out.println(boolFirstCorner);
					if(boolFirstCorner == true){
						for(intCount = 0; intCount < 5; intCount++){
							System.out.println();
							for(intCount2 = 0; intCount2 < 5; intCount2++){
								System.out.print(PieceGrid[intCount][intCount2]);
								if(PieceGrid[intCount][intCount2] == 1){
								//determine row & column based on mouse drop (x,y) coordinates.
									//build from intRow & intCol
									strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "1";
									
								}
							}
						}
						PieceTaken[intPlayerCount][intPiece] = 0;
						boolFirstTime = false;
						intPiece = intPiece+1;
						
					}else{
					}
					newPiece = true;
					boolDropped = false;
				}else{
					boolOverlap = false;	
					for(intCount = 0; intCount < 5; intCount++){
						System.out.println();
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(PieceGrid[intCount][intCount2] == 1){
								//check for overlap
								if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] == "1" || strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] == null){
									boolOverlap = true;
								}
							}
						}
					}
					
					//figure out how to place pieces along the edge
					//then find how to place 1st piece in corner
					if(intTurn == 1){
						
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
									if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2] == "1"){
										boolSide = true;
									}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2] == "1"){
										boolSide = true;
									}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2+1] == "1"){
										boolSide = true;
									}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2-1] == "1"){
										boolSide = true;
									}
								}
							}
						}
						for(intCount = 0; intCount < 5; intCount++){
							System.out.println();
							for(intCount2 = 0; intCount2 < 5; intCount2++){
								if(PieceGrid[intCount][intCount2] == 1){
								//determine row & column based on mouse drop (x,y) coordinates.
									//build from intRow & intCol
									if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2-1] == "1"){
										boolCorner = true;
									}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2+1] == "1"){
										boolCorner = true;
									}else if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2+1] == "1"){
										boolCorner = true;
									}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2-1] == "1"){
										boolCorner = true;
									}	
								}
							}
						}
						
						if(boolCorner == true && boolSide == false){
							for(intCount = 0; intCount < 5; intCount++){
								System.out.println();
								for(intCount2 = 0; intCount2 < 5; intCount2++){
									System.out.print(PieceGrid[intCount][intCount2]);
									if(PieceGrid[intCount][intCount2] == 1){
									//determine row & column based on mouse drop (x,y) coordinates.
										//build from intRow & intCol
										strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "1";
										
									}
								}
							}
							
							
							intPiece = intPiece + 1;
							if(intPiece > 21){
								intPiece = 1;
							}					
							newPiece = true;
						}else{
							PieceTaken[intPlayerCount][intPiece] = 1;
						}
					
						System.out.println("board array");
						
						for(intRow = 0; intRow <24; intRow++){
							System.out.println("");
							for(intCol = 0; intCol <24; intCol++){
								System.out.print(strBoard[intRow][intCol]);
							}
						}
						
						boolDropped = false;
					}else{
						PieceTaken[intPlayerCount][intPiece] = 1;
					}
					System.out.println(PieceTaken[intPlayerCount][intPiece]);
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
		try{
			yellow = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/yellowblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(yellowblock.png)");
		}
		try{
			green = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/greenblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(greenblock.png)");
		}
	}
}
