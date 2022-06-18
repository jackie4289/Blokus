// Blokus Game Panel
// Authors: Lucas Moran, Kelsie Fung, Jackie Lin
// ICS 4U1
// June 16, 2022
// Version 1.231

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import java.lang.Math;

public class BlokusPanel extends JPanel{
	//Properties
	// Arrays
	String intARow[];
	String strName[] = new String[4];
	int PieceGrid[][] = new int [4][4];
	String strBoard[][] = new String[24][24];
	int BoardGrid[][] = new int[23][23];
	int TempGrid[][] = new int[23][23];
	int PlayerGrid[][] = new int[4][4];
	int PieceTaken[][] = new int[5][22]; // (int [player#] [piece#])
	String strP1SidePieces[][] = new String[15][16]; // (row)(column)
	String strP2SidePieces[][] = new String[15][16]; // (row)(column)
	String strP3SidePieces[][] = new String[15][16]; // (row)(column)
	String strP4SidePieces[][] = new String[15][16]; // (row)(column)
	// 0 = EMPTY
	// 1 = YELLOW (P1)
	// 2 = GREEN (P2)
	// 3 = BLUE (P3)
	// 4 = RED (P4)
	// Booleans
	boolean boolStartGame = false;
	boolean boolDragAndDrop = false; // read below
	boolean boolRotate = false;
	boolean boolDropped = false;
	boolean boolFirstTime = true;
	boolean boolRealFirstTime = true;
	boolean boolFirstCorner = false;
	boolean boolNewTurn = false;
	boolean boolOverlap = false;
	boolean boolCorner = false;
	boolean boolSide = false;
	boolean boolRepaint = true;
	boolean checkPieces = false;
	boolean sidePieceCode = true;
	boolean turnOver = false;
	boolean boolP1Turn = false;
	boolean boolP2Turn = false;
	boolean boolP3Turn = false;
	boolean boolP4Turn = false;
	boolean boolEndGame = false;
	//booleans in main for compile
	boolean boolYourTurn;
	//Strings
	// Integers
	int intRow;
	int intCol;
	int intCount;
	int intCount2;
	int intColPick;
	int intRowPick;
	int intPickX = 0;
	int intPickY = 0;
	int intSkip = 0;
	int intPlayerTurnNumber;
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
	int intTurn = 1;
	int intDropX;
	int intDropY;
	int intColDrop;
	int intRowDrop;
	// Strings
	String intLine;
	String strRow;
	String strUsername;
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
	BufferedImage swhite = null;
	BufferedImage sred = null;
	BufferedImage sblue = null;
	BufferedImage sgreen = null;
	BufferedImage syellow = null;


	//Methods
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
			if(boolRealFirstTime == true){
				BufferedReader board = null;
				BufferedReader sideLayout = null;
				try{
					board = new BufferedReader(new FileReader("Data_Files/board.csv"));
				}catch(FileNotFoundException e){
					//System.out.println("File not found!!!");
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
					for(intCount2 = 1; intCount2 < 5; intCount2++){
						PieceTaken[intCount2][intCount] = 1;
					}
				}
				BufferedReader sidepieces = null;
				try{
					sidepieces = new BufferedReader(new FileReader("Data_Files/sidepieces.csv"));
				}catch(FileNotFoundException e){
					//System.out.println("File not found!!!");
				}
				
				//read array
				intRow = 0;
				intCol = 0;
				for(intCount = 0; intCount < 15; intCount++){
					try{
						intLine = sidepieces.readLine();
						intARow = intLine.split(",");
					}catch(IOException e){
						intLine = "0";
					}
					//System.out.println("");
					for(intCount2 = 0; intCount2 < 16; intCount2++){
						strP1SidePieces[intCount][intCount2] = intARow[intCount2];
					}
				}
				
				for(intCount = 0;intCount < 15; intCount++){
					for(intCount2 = 0; intCount2 < 16; intCount2++){
						strP2SidePieces[intCount][intCount2] = strP1SidePieces[intCount][intCount2];
						strP3SidePieces[intCount][intCount2] = strP1SidePieces[intCount][intCount2];
						strP4SidePieces[intCount][intCount2] = strP1SidePieces[intCount][intCount2];
					}
				}
				boolRealFirstTime = false;
			}
		}
		
		
		
		//GAME
		boolStartGame = true;
		if(boolStartGame == true){
			//REMOVE LINE BELOW WHEN INTEGRATING
			// UI
			//	g.setColor(Color.WHITE);
			//	g.fillRect(0,0,1280,720);			
			
			// P1 (Yellow)
			g.setColor(new Color(255, 208, 132));
			g.fillRect(0, 0, 640, 360);
			g.setColor(new Color(204, 166, 105));
			g.fillRect(350, 0, 290, 360);
			//P1 NameCard
			g.setColor(new Color(178, 145, 92));
			g.fillRect(0, 0, 350, 40);
			g.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.BOLD, 25)); 
			g.drawString("P1: " + strName[0], 15, 30);
			
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
			g.drawString("P2: " + strName[1], 945, 30);
			
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
			g.drawString("P3: " + strName[2], 945, 710);
			
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
			g.drawString("P4: " + strName[3], 15, 710);
			
			//Board
			g.setColor(Color.BLACK);
			g.drawRect(369, 26, 541, 541);
			g.drawRect(350, 0, 580, 719);
			//ChatBox
			g.drawRect(369, 580, 541, 135);
			//Logo
			g.drawString("BLOKUS", 588, 23);
			
			// Draw side pieces	
			for(intRow = 0;intRow < 15;intRow++){
				for(intCol = 0;intCol < 16;intCol++){
					if(strP1SidePieces[intRow][intCol].equals("1")){ // Player 1
						g.drawImage(syellow,12+(intCol*20),49+(intRow*20),null);
					}
					if(strP2SidePieces[intRow][intCol].equals("1")){ // Player 2
						g.drawImage(sgreen,943+(intCol*20),49+(intRow*20),null);
						
					}
					if(strP3SidePieces[intRow][intCol].equals("1")){ // Player 3 
						g.drawImage(sblue,943+(intCol*20),368+(intRow*20),null);
					}
					if(strP4SidePieces[intRow][intCol].equals("1")){ // Player 4
						g.drawImage(sred,12+(intCol*20), 368 + (intRow*20),null);
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
			

			
			//Drag & Drop
			if(boolDragAndDrop == true){ // this will be set by mousepressed or mouse released
				if(intTurn == 1){
					intColPick = Math.round((intPickX-12)/20);
					intRowPick = Math.round((intPickY-49)/20); 
				}else if(intTurn == 2){
					intColPick = Math.round((intPickX-943)/20);
					intRowPick = Math.round((intPickY-49)/20); 
				}else if(intTurn == 3){
					intColPick = Math.round((intPickX-943)/20);
					intRowPick = Math.round((intPickY-368)/20); 
				}else if(intTurn == 4){
					intColPick = Math.round((intPickX-12)/20);
					intRowPick = Math.round((intPickY-368)/20); 
				}
				//test
				//intColPick = Math.round((intPickX-12)/20);
				//intRowPick = Math.round((intPickY-49)/20); 
				//intColPick = Math.round((intPickX-943)/20);
				//intRowPick = Math.round((intPickY-49)/20); 
				//intColPick = Math.round((intPickX-943)/20);
				//intRowPick = Math.round((intPickY-368)/20); 
				//intColPick = Math.round((intPickX-12)/20);
				//intRowPick = Math.round((intPickY-358)/20); 
				
				//System.out.println(intColPick +"," + intRowPick);
				System.out.println(intPiece);
	
				// SIDE PIECES
				if(sidePieceCode == true){
					if(intColPick == 0){
						if(intRowPick == 0){
							intPiece = 1;
						}else if(intRowPick == 2 || intRowPick == 3){
							intPiece = 2;
						}else if(intRowPick == 5 || intRowPick == 6|| intRowPick == 7){
							intPiece = 3;
						}else if(intRowPick == 9 || intRowPick == 10 || intRowPick == 11 || intRowPick == 12 || intRowPick == 13){
							intPiece = 10;
						}
					}else if(intColPick == 2){
						if(intRowPick == 0 || intRowPick == 1){
							intPiece = 8;
						}else if(intRowPick == 4){
							intPiece = 9;
						}else if(intRowPick == 6 || intRowPick == 7|| intRowPick == 8 || intRowPick == 9){
							intPiece = 5;
						}else if(intRowPick == 11 || intRowPick == 12 || intRowPick == 13 || intRowPick == 14){
							intPiece = 11;
						}					
					}else if(intColPick == 3){
						if(intRowPick == 0 || intRowPick == 1){
							intPiece = 8;
						}else if(intRowPick == 3 || intRowPick == 4){
							intPiece = 9;
						}else if(intRowPick == 14){
							intPiece = 11;
						}					
					}else if(intColPick == 4){
						if(intRowPick == 3){
							intPiece = 8;
						}else if(intRowPick == 6 || intRowPick == 7|| intRowPick == 8 ){
							intPiece = 6;
						}else if(intRowPick == 11){
							intPiece = 12;
						}					
					}else if(intColPick == 5){
						if(intRowPick == 1){
							intPiece = 4;
						}else if(intRowPick == 8 ){
							intPiece = 6;
						}else if(intRowPick == 11){
							intPiece = 12;
						}else if(intRowPick == 14){
							intPiece = 13;
						}					
					}else if(intColPick == 6){
						if(intRowPick == 0 || intRowPick == 1){
							intPiece = 4;
						}else if(intRowPick == 4 || intRowPick == 5){
							intPiece = 20; // 20 and 7
						}else if(intRowPick == 10 || intRowPick == 11){
							intPiece = 12;
						}else if(intRowPick == 13 || intRowPick == 14){
							intPiece = 13;
						}					
					}else if(intColPick == 7){
						if( intRowPick == 3 || intRowPick == 4){
							intPiece = 20;
						}else if(intRowPick == 7){
							intPiece = 19;
						}else if(intRowPick == 10){
							intPiece = 12;
						}else if(intRowPick == 13 || intRowPick == 14){
							intPiece = 13;
						}					
					}else if(intColPick == 8){
						if(intRowPick == 0|| intRowPick == 1){
							intPiece = 17;
						}else if(intRowPick == 6 || intRowPick == 7 || intRowPick == 8 ){
							intPiece = 19;
						}else if(intRowPick == 4){
							intPiece = 20;
						}		
					}else if(intColPick == 9){
						if(intRowPick == 0){
							intPiece = 17;
						}else if(intRowPick == 7){
							intPiece = 19;
						}else if(intRowPick == 11){
							intPiece = 18;
						}
					}else if(intColPick == 10){
						if(intRowPick == 0 || intRowPick == 1){
							intPiece = 17;
						}else if(intRowPick == 10 || intRowPick == 11 || intRowPick == 12 || intRowPick == 13){
							intPiece = 18;
						}
					}else if(intColPick == 11){
						if(intRowPick == 5 || intRowPick == 6){
							intPiece = 15;
						}
					}else if(intColPick == 12){
						if(intRowPick == 4 || intRowPick == 5){
							intPiece = 15;
						}else if(intRowPick == 10){
							intPiece = 7;
						}
					}else if(intColPick == 13){
						if(intRowPick == 1 || intRowPick == 2){
							intPiece = 16;
						}else if(intRowPick == 4){
							intPiece = 15;
						}else if(intRowPick == 8){
							intPiece = 21;
						}else if(intRowPick == 10 || intRowPick == 11){
							intPiece = 7;
						}else if(intRowPick == 14){
							intPiece = 14;
						}
					}else if(intColPick == 14){
						if(intRowPick == 1){
							intPiece = 16;
						}else if(intRowPick == 6 || intRowPick == 7 || intRowPick == 8){
							intPiece = 21;
						}else if(intRowPick == 10){
							intPiece = 7;
						}else if(intRowPick == 14){
							intPiece = 14;
						}
					}else if(intColPick == 15){
						if(intRowPick == 0 || intRowPick == 1){
							intPiece = 16;
						}else if(intRowPick == 8){
							intPiece = 21;
						}else if (intRowPick == 12 || intRowPick == 13 || intRowPick == 14){
							intPiece = 14;
						}
					}
					sidePieceCode = false;
					if(PieceTaken[intTurn][intPiece] == 0){
						intPiece = 0;
					}
					if(boolNewTurn == true){
						intPiece = 0;
						boolNewTurn = false;
					}
					PieceGrid = BlokObject.PickPiece(intPiece);
					//System.out.println(intPiece);
				}
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
					//System.out.println("OUT");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if (intTurn == 1){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(yellow, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 2){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(green, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 3){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(blue, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 4){ 
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(red, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
									System.out.println("ok red");
								}
							}
							
						}
					}
				}else if(mouseY < 26 || mouseY > 567){
					//System.out.println("OUT");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(intTurn == 1){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(yellow, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 2){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(green, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 3){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(blue, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 4){ 
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(red, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
									System.out.println("ok red");
								}
							}
							
						}
					}
				}else{
					//System.out.println("IN");
					for(intCount = 0; intCount < 5; intCount++){
						for(intCount2 = 0; intCount2 < 5; intCount2++){
							if(intTurn == 1){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(yellow, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 2){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(green, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 3){
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(blue, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
								}
							}else if(intTurn == 4){ 
								if(PieceGrid[intCount][intCount2] == 1){
									g.drawImage(red, ((-68) + (27*intCount2) + mouseX), ((-68) + (27*intCount) + mouseY),null);
									System.out.println("ok red");
								}
							}
						}
					}
				}
			}
			
			//Dropped
			if(boolDropped == true){
				//System.out.println("Turn #: "+intTurn);
				//change the board array instead...
				//place the values (not 0) of piece grid into the board array 
				
				System.out.println("piece dropped");
				System.out.println("drop x "+intDropX+" | drop y " +intDropY);
				
				intColDrop= Math.round((intDropX-369)/27);
				intRowDrop = Math.round((intDropY-26)/27);
				
				//Draw Pieces
				System.out.println("Column"+intColDrop+" | Row " +intRowDrop);
				if(mouseX < 369 || mouseX > 910){
					boolDropped = false;
				}else if(mouseY < 26 || mouseY > 657){
					boolDropped = false;
				}else{
					if(boolFirstTime == true){
						boolFirstCorner = false;
						System.out.println("BOOLFIRSTIME STARTED");
						for(intCount = 0; intCount < 5; intCount++){
							for(intCount2 = 0; intCount2 < 5; intCount2++){
								if(PieceGrid[intCount][intCount2] == 1){
									//check for overlap
									if(intTurn == 1){
										if(intRowDrop + intCount - 2 == 0 && intColDrop+ intCount2 - 2 == 0){
											boolFirstCorner = true;
										}
									}else if(intTurn == 2){
										if(intRowDrop + intCount - 2 == 0 && intColDrop+ intCount2 - 2 == 19){
											boolFirstCorner = true;
										}
									}else if(intTurn == 3){
										if(intRowDrop + intCount - 2 == 19 && intColDrop+ intCount2 - 2 == 19){
											boolFirstCorner = true;
										}
									}else if(intTurn == 4){ 
										if(intRowDrop + intCount - 2 == 19 && intColDrop+ intCount2 - 2 == 0){
											boolFirstCorner = true;
										}
									}
									
									
								}
							}
						}
						
						if(boolFirstCorner == true){
							System.out.println("boolFirstCorner started");
							for(intCount = 0; intCount < 5; intCount++){
								for(intCount2 = 0; intCount2 < 5; intCount2++){
									if(PieceGrid[intCount][intCount2] == 1){
									//determine row & column based on mouse drop (x,y) coordinates.
										//build from intRow & intCol
										if(intTurn == 1){
											strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "1";
										}else if(intTurn == 2){
											strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "2";
										}else if(intTurn ==3){
											strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "3";
										}else if(intTurn == 4){
											strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "4";
										}
									}
								}
							}
							PieceTaken[intTurn][intPiece] = 0;
							
							// Calculate Pieces for FIRST TIME
							if(intTurn == 1){
								if(intPiece == 1){
									strP1SidePieces[0][0] = "0";
								}else if(intPiece == 2){
									strP1SidePieces[2][0] = "0";
									strP1SidePieces[3][0] = "0";
								}else if(intPiece == 3){
									strP1SidePieces[5][0] = "0";
									strP1SidePieces[6][0] = "0";
									strP1SidePieces[7][0] = "0";
								}else if(intPiece == 4){
									strP1SidePieces[1][5] = "0";
									strP1SidePieces[1][6] = "0";
									strP1SidePieces[0][6] = "0";						
								}else if(intPiece == 5){
									strP1SidePieces[6][2] = "0";
									strP1SidePieces[7][2] = "0";
									strP1SidePieces[8][2] = "0";
									strP1SidePieces[9][2] = "0";
								}else if(intPiece == 6){
									strP1SidePieces[6][4] = "0";
									strP1SidePieces[7][4] = "0";
									strP1SidePieces[8][4] = "0";
									strP1SidePieces[8][5] = "0";								
								}else if(intPiece == 7){
									strP1SidePieces[10][12] = "0";
									strP1SidePieces[10][13] = "0";
									strP1SidePieces[11][13] = "0";
									strP1SidePieces[10][14] = "0";								
								}else if(intPiece == 8){
									strP1SidePieces[0][2] = "0";
									strP1SidePieces[1][2] = "0";
									strP1SidePieces[0][3] = "0";
									strP1SidePieces[1][3] = "0";							
								}else if(intPiece == 9){
									strP1SidePieces[4][2] = "0";
									strP1SidePieces[4][3] = "0";
									strP1SidePieces[3][3] = "0";
									strP1SidePieces[3][4] = "0"; 
								}else if(intPiece == 10){
									strP1SidePieces[9][0] = "0";
									strP1SidePieces[10][0] = "0";
									strP1SidePieces[11][0] = "0";
									strP1SidePieces[12][0] = "0";
									strP1SidePieces[13][0] = "0";
								}else if(intPiece == 11){
									strP1SidePieces[11][2] = "0";
									strP1SidePieces[12][2] = "0";
									strP1SidePieces[13][2] = "0";
									strP1SidePieces[14][2] = "0";
									strP1SidePieces[14][3] = "0";
								}else if(intPiece == 12){
									strP1SidePieces[11][4] = "0";
									strP1SidePieces[11][5] = "0";
									strP1SidePieces[11][6] = "0";
									strP1SidePieces[10][6] = "0";
									strP1SidePieces[10][7] = "0";
								}else if(intPiece == 13){
									strP1SidePieces[14][5] = "0";
									strP1SidePieces[14][6] = "0";
									strP1SidePieces[14][7] = "0";
									strP1SidePieces[13][6] = "0";
									strP1SidePieces[13][7] = "0";
								}else if(intPiece == 14){
									strP1SidePieces[12][15] = "0";
									strP1SidePieces[13][15] = "0";
									strP1SidePieces[14][15] = "0";
									strP1SidePieces[14][14] = "0";
									strP1SidePieces[14][13] = "0";
								}else if(intPiece == 15){
									strP1SidePieces[6][11] = "0";
									strP1SidePieces[5][11] = "0";
									strP1SidePieces[5][12] = "0";
									strP1SidePieces[4][12] = "0";
									strP1SidePieces[4][13] = "0";
								}else if(intPiece == 16){
									strP1SidePieces[2][13] = "0";
									strP1SidePieces[1][13] = "0";
									strP1SidePieces[1][14] = "0";
									strP1SidePieces[1][15] = "0";
									strP1SidePieces[0][15] = "0";
								}else if(intPiece == 17){
									strP1SidePieces[0][8] = "0";
									strP1SidePieces[0][9] = "0";
									strP1SidePieces[0][10] = "0";
									strP1SidePieces[1][8] = "0";
									strP1SidePieces[1][10] = "0";
								}else if(intPiece == 18){
									strP1SidePieces[10][10] = "0";
									strP1SidePieces[11][10] = "0";
									strP1SidePieces[12][10] = "0";
									strP1SidePieces[13][10] = "0";
									strP1SidePieces[11][9] = "0";
								}else if(intPiece == 19){
									strP1SidePieces[6][8] = "0";
									strP1SidePieces[7][7] = "0";
									strP1SidePieces[7][8] = "0";
									strP1SidePieces[7][9] = "0";
									strP1SidePieces[8][8] = "0";
								}else if(intPiece == 20){
									strP1SidePieces[3][7] = "0";
									strP1SidePieces[4][6] = "0";
									strP1SidePieces[4][7] = "0";
									strP1SidePieces[4][8] = "0";
									strP1SidePieces[5][6] = "0";
								}else if(intPiece == 21){
									strP1SidePieces[6][14] = "0";
									strP1SidePieces[7][14] = "0";
									strP1SidePieces[8][14] = "0";
									strP1SidePieces[8][13] = "0";
									strP1SidePieces[8][15] = "0";
								}
							}else if(intTurn == 2){
								if(intPiece == 1){
									strP2SidePieces[0][0] = "0";
								}else if(intPiece == 2){
									strP2SidePieces[2][0] = "0";
									strP2SidePieces[3][0] = "0";
								}else if(intPiece == 3){
									strP2SidePieces[5][0] = "0";
									strP2SidePieces[6][0] = "0";
									strP2SidePieces[7][0] = "0";
								}else if(intPiece == 4){
									strP2SidePieces[1][5] = "0";
									strP2SidePieces[1][6] = "0";
									strP2SidePieces[0][6] = "0";						
								}else if(intPiece == 5){
									strP2SidePieces[6][2] = "0";
									strP2SidePieces[7][2] = "0";
									strP2SidePieces[8][2] = "0";
									strP2SidePieces[9][2] = "0";
								}else if(intPiece == 6){
									strP2SidePieces[6][4] = "0";
									strP2SidePieces[7][4] = "0";
									strP2SidePieces[8][4] = "0";
									strP2SidePieces[8][5] = "0";								
								}else if(intPiece == 7){
									strP2SidePieces[10][12] = "0";
									strP2SidePieces[10][13] = "0";
									strP2SidePieces[11][13] = "0";
									strP2SidePieces[10][14] = "0";								
								}else if(intPiece == 8){
									strP2SidePieces[0][2] = "0";
									strP2SidePieces[1][2] = "0";
									strP2SidePieces[0][3] = "0";
									strP2SidePieces[1][3] = "0";							
								}else if(intPiece == 9){
									strP2SidePieces[4][2] = "0";
									strP2SidePieces[4][3] = "0";
									strP2SidePieces[3][3] = "0";
									strP2SidePieces[3][4] = "0"; 
								}else if(intPiece == 10){
									strP2SidePieces[9][0] = "0";
									strP2SidePieces[10][0] = "0";
									strP2SidePieces[11][0] = "0";
									strP2SidePieces[12][0] = "0";
									strP2SidePieces[13][0] = "0";
								}else if(intPiece == 11){
									strP2SidePieces[11][2] = "0";
									strP2SidePieces[12][2] = "0";
									strP2SidePieces[13][2] = "0";
									strP2SidePieces[14][2] = "0";
									strP2SidePieces[14][3] = "0";
								}else if(intPiece == 12){
									strP2SidePieces[11][4] = "0";
									strP2SidePieces[11][5] = "0";
									strP2SidePieces[11][6] = "0";
									strP2SidePieces[10][6] = "0";
									strP2SidePieces[10][7] = "0";
								}else if(intPiece == 13){
									strP2SidePieces[14][5] = "0";
									strP2SidePieces[14][6] = "0";
									strP2SidePieces[14][7] = "0";
									strP2SidePieces[13][6] = "0";
									strP2SidePieces[13][7] = "0";
								}else if(intPiece == 14){
									strP2SidePieces[12][15] = "0";
									strP2SidePieces[13][15] = "0";
									strP2SidePieces[14][15] = "0";
									strP2SidePieces[14][14] = "0";
									strP2SidePieces[14][13] = "0";
								}else if(intPiece == 15){
									strP2SidePieces[6][11] = "0";
									strP2SidePieces[5][11] = "0";
									strP2SidePieces[5][12] = "0";
									strP2SidePieces[4][12] = "0";
									strP2SidePieces[4][13] = "0";
								}else if(intPiece == 16){
									strP2SidePieces[2][13] = "0";
									strP2SidePieces[1][13] = "0";
									strP2SidePieces[1][14] = "0";
									strP2SidePieces[1][15] = "0";
									strP2SidePieces[0][15] = "0";
								}else if(intPiece == 17){
									strP2SidePieces[0][8] = "0";
									strP2SidePieces[0][9] = "0";
									strP2SidePieces[0][10] = "0";
									strP2SidePieces[1][8] = "0";
									strP2SidePieces[1][10] = "0";
								}else if(intPiece == 18){
									strP2SidePieces[10][10] = "0";
									strP2SidePieces[11][10] = "0";
									strP2SidePieces[12][10] = "0";
									strP2SidePieces[13][10] = "0";
									strP2SidePieces[11][9] = "0";
								}else if(intPiece == 19){
									strP2SidePieces[6][8] = "0";
									strP2SidePieces[7][7] = "0";
									strP2SidePieces[7][8] = "0";
									strP2SidePieces[7][9] = "0";
									strP2SidePieces[8][8] = "0";
								}else if(intPiece == 20){
									strP2SidePieces[3][7] = "0";
									strP2SidePieces[4][6] = "0";
									strP2SidePieces[4][7] = "0";
									strP2SidePieces[4][8] = "0";
									strP2SidePieces[5][6] = "0";
								}else if(intPiece == 21){
									strP2SidePieces[6][14] = "0";
									strP2SidePieces[7][14] = "0";
									strP2SidePieces[8][14] = "0";
									strP2SidePieces[8][13] = "0";
									strP2SidePieces[8][15] = "0";
								}
							}else if(intTurn == 3){
								if(intPiece == 1){
									strP3SidePieces[0][0] = "0";
								}else if(intPiece == 2){
									strP3SidePieces[2][0] = "0";
									strP3SidePieces[3][0] = "0";
								}else if(intPiece == 3){
									strP3SidePieces[5][0] = "0";
									strP3SidePieces[6][0] = "0";
									strP3SidePieces[7][0] = "0";
								}else if(intPiece == 4){
									strP3SidePieces[1][5] = "0";
									strP3SidePieces[1][6] = "0";
									strP3SidePieces[0][6] = "0";						
								}else if(intPiece == 5){
									strP3SidePieces[6][2] = "0";
									strP3SidePieces[7][2] = "0";
									strP3SidePieces[8][2] = "0";
									strP3SidePieces[9][2] = "0";
								}else if(intPiece == 6){
									strP3SidePieces[6][4] = "0";
									strP3SidePieces[7][4] = "0";
									strP3SidePieces[8][4] = "0";
									strP3SidePieces[8][5] = "0";								
								}else if(intPiece == 7){
									strP3SidePieces[10][12] = "0";
									strP3SidePieces[10][13] = "0";
									strP3SidePieces[11][13] = "0";
									strP3SidePieces[10][14] = "0";								
								}else if(intPiece == 8){
									strP3SidePieces[0][2] = "0";
									strP3SidePieces[1][2] = "0";
									strP3SidePieces[0][3] = "0";
									strP3SidePieces[1][3] = "0";							
								}else if(intPiece == 9){
									strP3SidePieces[4][2] = "0";
									strP3SidePieces[4][3] = "0";
									strP3SidePieces[3][3] = "0";
									strP3SidePieces[3][4] = "0"; 
								}else if(intPiece == 10){
									strP3SidePieces[9][0] = "0";
									strP3SidePieces[10][0] = "0";
									strP3SidePieces[11][0] = "0";
									strP3SidePieces[12][0] = "0";
									strP3SidePieces[13][0] = "0";
								}else if(intPiece == 11){
									strP3SidePieces[11][2] = "0";
									strP3SidePieces[12][2] = "0";
									strP3SidePieces[13][2] = "0";
									strP3SidePieces[14][2] = "0";
									strP3SidePieces[14][3] = "0";
								}else if(intPiece == 12){
									strP3SidePieces[11][4] = "0";
									strP3SidePieces[11][5] = "0";
									strP3SidePieces[11][6] = "0";
									strP3SidePieces[10][6] = "0";
									strP3SidePieces[10][7] = "0";
								}else if(intPiece == 13){
									strP3SidePieces[14][5] = "0";
									strP3SidePieces[14][6] = "0";
									strP3SidePieces[14][7] = "0";
									strP3SidePieces[13][6] = "0";
									strP3SidePieces[13][7] = "0";
								}else if(intPiece == 14){
									strP3SidePieces[12][15] = "0";
									strP3SidePieces[13][15] = "0";
									strP3SidePieces[14][15] = "0";
									strP3SidePieces[14][14] = "0";
									strP3SidePieces[14][13] = "0";
								}else if(intPiece == 15){
									strP3SidePieces[6][11] = "0";
									strP3SidePieces[5][11] = "0";
									strP3SidePieces[5][12] = "0";
									strP3SidePieces[4][12] = "0";
									strP3SidePieces[4][13] = "0";
								}else if(intPiece == 16){
									strP3SidePieces[2][13] = "0";
									strP3SidePieces[1][13] = "0";
									strP3SidePieces[1][14] = "0";
									strP3SidePieces[1][15] = "0";
									strP3SidePieces[0][15] = "0";
								}else if(intPiece == 17){
									strP3SidePieces[0][8] = "0";
									strP3SidePieces[0][9] = "0";
									strP3SidePieces[0][10] = "0";
									strP3SidePieces[1][8] = "0";
									strP3SidePieces[1][10] = "0";
								}else if(intPiece == 18){
									strP3SidePieces[10][10] = "0";
									strP3SidePieces[11][10] = "0";
									strP3SidePieces[12][10] = "0";
									strP3SidePieces[13][10] = "0";
									strP3SidePieces[11][9] = "0";
								}else if(intPiece == 19){
									strP3SidePieces[6][8] = "0";
									strP3SidePieces[7][7] = "0";
									strP3SidePieces[7][8] = "0";
									strP3SidePieces[7][9] = "0";
									strP3SidePieces[8][8] = "0";
								}else if(intPiece == 20){
									strP3SidePieces[3][7] = "0";
									strP3SidePieces[4][6] = "0";
									strP3SidePieces[4][7] = "0";
									strP3SidePieces[4][8] = "0";
									strP3SidePieces[5][6] = "0";
								}else if(intPiece == 21){
									strP3SidePieces[6][14] = "0";
									strP3SidePieces[7][14] = "0";
									strP3SidePieces[8][14] = "0";
									strP3SidePieces[8][13] = "0";
									strP3SidePieces[8][15] = "0";
								}
							}else if(intTurn == 4){
								if(intPiece == 1){
									strP4SidePieces[0][0] = "0";
								}else if(intPiece == 2){
									strP4SidePieces[2][0] = "0";
									strP4SidePieces[3][0] = "0";
								}else if(intPiece == 3){
									strP4SidePieces[5][0] = "0";
									strP4SidePieces[6][0] = "0";
									strP4SidePieces[7][0] = "0";
								}else if(intPiece == 4){
									strP4SidePieces[1][5] = "0";
									strP4SidePieces[1][6] = "0";
									strP4SidePieces[0][6] = "0";						
								}else if(intPiece == 5){
									strP4SidePieces[6][2] = "0";
									strP4SidePieces[7][2] = "0";
									strP4SidePieces[8][2] = "0";
									strP4SidePieces[9][2] = "0";
								}else if(intPiece == 6){
									strP4SidePieces[6][4] = "0";
									strP4SidePieces[7][4] = "0";
									strP4SidePieces[8][4] = "0";
									strP4SidePieces[8][5] = "0";								
								}else if(intPiece == 7){
									strP4SidePieces[10][12] = "0";
									strP4SidePieces[10][13] = "0";
									strP4SidePieces[11][13] = "0";
									strP4SidePieces[10][14] = "0";								
								}else if(intPiece == 8){
									strP4SidePieces[0][2] = "0";
									strP4SidePieces[1][2] = "0";
									strP4SidePieces[0][3] = "0";
									strP4SidePieces[1][3] = "0";							
								}else if(intPiece == 9){
									strP4SidePieces[4][2] = "0";
									strP4SidePieces[4][3] = "0";
									strP4SidePieces[3][3] = "0";
									strP4SidePieces[3][4] = "0"; 
								}else if(intPiece == 10){
									strP4SidePieces[9][0] = "0";
									strP4SidePieces[10][0] = "0";
									strP4SidePieces[11][0] = "0";
									strP4SidePieces[12][0] = "0";
									strP4SidePieces[13][0] = "0";
								}else if(intPiece == 11){
									strP4SidePieces[11][2] = "0";
									strP4SidePieces[12][2] = "0";
									strP4SidePieces[13][2] = "0";
									strP4SidePieces[14][2] = "0";
									strP4SidePieces[14][3] = "0";
								}else if(intPiece == 12){
									strP4SidePieces[11][4] = "0";
									strP4SidePieces[11][5] = "0";
									strP4SidePieces[11][6] = "0";
									strP4SidePieces[10][6] = "0";
									strP4SidePieces[10][7] = "0";
								}else if(intPiece == 13){
									strP4SidePieces[14][5] = "0";
									strP4SidePieces[14][6] = "0";
									strP4SidePieces[14][7] = "0";
									strP4SidePieces[13][6] = "0";
									strP4SidePieces[13][7] = "0";
								}else if(intPiece == 14){
									strP4SidePieces[12][15] = "0";
									strP4SidePieces[13][15] = "0";
									strP4SidePieces[14][15] = "0";
									strP4SidePieces[14][14] = "0";
									strP4SidePieces[14][13] = "0";
								}else if(intPiece == 15){
									strP4SidePieces[6][11] = "0";
									strP4SidePieces[5][11] = "0";
									strP4SidePieces[5][12] = "0";
									strP4SidePieces[4][12] = "0";
									strP4SidePieces[4][13] = "0";
								}else if(intPiece == 16){
									strP4SidePieces[2][13] = "0";
									strP4SidePieces[1][13] = "0";
									strP4SidePieces[1][14] = "0";
									strP4SidePieces[1][15] = "0";
									strP4SidePieces[0][15] = "0";
								}else if(intPiece == 17){
									strP4SidePieces[0][8] = "0";
									strP4SidePieces[0][9] = "0";
									strP4SidePieces[0][10] = "0";
									strP4SidePieces[1][8] = "0";
									strP4SidePieces[1][10] = "0";
								}else if(intPiece == 18){
									strP4SidePieces[10][10] = "0";
									strP4SidePieces[11][10] = "0";
									strP4SidePieces[12][10] = "0";
									strP4SidePieces[13][10] = "0";
									strP4SidePieces[11][9] = "0";
								}else if(intPiece == 19){
									strP4SidePieces[6][8] = "0";
									strP4SidePieces[7][7] = "0";
									strP4SidePieces[7][8] = "0";
									strP4SidePieces[7][9] = "0";
									strP4SidePieces[8][8] = "0";
								}else if(intPiece == 20){
									strP4SidePieces[3][7] = "0";
									strP4SidePieces[4][6] = "0";
									strP4SidePieces[4][7] = "0";
									strP4SidePieces[4][8] = "0";
									strP4SidePieces[5][6] = "0";
								}else if(intPiece == 21){
									strP4SidePieces[6][14] = "0";
									strP4SidePieces[7][14] = "0";
									strP4SidePieces[8][14] = "0";
									strP4SidePieces[8][13] = "0";
									strP4SidePieces[8][15] = "0";
								}
							}
							
							for(intCount = 0;intCount < 15;intCount++){
								System.out.println();
								for(intCount2 = 0;intCount2 < 16; intCount2++){
									if(intTurn == 1){
										System.out.print(strP1SidePieces[intCount][intCount2]);
									}else if(intTurn == 2){
										System.out.print(strP2SidePieces[intCount][intCount2]);
									}else if(intTurn == 3){
										System.out.print(strP3SidePieces[intCount][intCount2]);
									}else if(intTurn == 4){
										System.out.print(strP4SidePieces[intCount][intCount2]);
									}
								}
							}
							System.out.println();
							System.out.println("intRowDrop: "+intRowDrop);
							System.out.println("intColDrop: "+intColDrop);
							System.out.println("intTurn: "+intTurn);
							System.out.println("intPiece: "+intPiece);
							
							/*if(intTurn == 1){
								boolP1Turn = true;
							}else if(intTurn == 2){
								boolP2Turn = true;
							}else if(intTurn == 3){
								boolP3Turn = true;
							}else if(intTurn == 4){ 
								boolP4Turn = true;
							}*/
							
							boolFirstTime = false;
							
							intTurn++;
							if(intTurn > 4){
								intTurn = 1;
							}
							boolNewTurn = true;
							
							/*if(boolP1Turn == true && boolP2Turn == true && boolP3Turn == true && boolP4Turn == true){
								boolFirstTime = false;
							}*/
							
							
							checkPieces = true;
						}else{
							PieceTaken[intTurn][intPiece] = 1;
						}
						boolDropped = false;
					}else{
						boolOverlap = false;	
						
						for(intCount = 0; intCount < 5; intCount++){
							for(intCount2 = 0; intCount2 < 5; intCount2++){
								if(PieceGrid[intCount][intCount2] == 1){
								//determine row & column based on mouse drop (x,y) coordinates.
									//build from intRow & intCol
									if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] == "1"){
										boolOverlap = true;
									}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] == "2"){
										boolOverlap = true;
									}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] == "3"){
										boolOverlap = true;
									}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] == "4"){
										boolOverlap = true;
									}
								}
							}
						}
						if(boolOverlap == false){
							boolCorner = false;
							boolSide = false;
							for(intCount = 0; intCount < 5; intCount++){
								for(intCount2 = 0; intCount2 < 5; intCount2++){
									if(PieceGrid[intCount][intCount2] == 1){
									//determine row & column based on mouse drop (x,y) coordinates.
										//build from intRow & intCol
										if(intTurn == 1){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2] == "1"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2] == "1"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2+1] == "1"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2-1] == "1"){
												boolSide = true;
											}
										}else if(intTurn == 2){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2] == "2"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2] == "2"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2+1] == "2"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2-1] == "2"){
												boolSide = true;
											}
										}else if(intTurn == 3){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2] == "3"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2] == "3"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2+1] == "3"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2-1] == "3"){
												boolSide = true;
											}
										}else if(intTurn == 4){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2] == "4"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2] == "4"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2+1] == "4"){
												boolSide = true;
											}else if(strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2-1] == "4"){
												boolSide = true;
											}
										}
									}
								}
							}
							for(intCount = 0; intCount < 5; intCount++){
								for(intCount2 = 0; intCount2 < 5; intCount2++){
									if(PieceGrid[intCount][intCount2] == 1){
									//determine row & column based on mouse drop (x,y) coordinates.
										//build from intRow & intCol
										if(intTurn == 1){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2-1] == "1"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2+1] == "1"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2+1] == "1"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2-1] == "1"){
												boolCorner = true;
											}	
										}else if(intTurn == 2){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2-1] == "2"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2+1] == "2"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2+1] == "2"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2-1] == "2"){
												boolCorner = true;
											}	
										}else if(intTurn == 3){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2-1] == "3"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2+1] == "3"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2+1] == "3"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2-1] == "3"){
												boolCorner = true;
											}	
										}else if(intTurn == 4){
											if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2-1] == "4"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2+1] == "4"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount-1][(intColDrop)+intCount2+1] == "4"){
												boolCorner = true;
											}else if(strBoard[(intRowDrop)+intCount+1][(intColDrop)+intCount2-1] == "4"){
												boolCorner = true;
											}	
										}
									}
								}
							}
							
							if(boolCorner == true && boolSide == false){
								for(intCount = 0; intCount < 5; intCount++){
									for(intCount2 = 0; intCount2 < 5; intCount2++){
										if(PieceGrid[intCount][intCount2] == 1){
										//determine row & column based on mouse drop (x,y) coordinates.
											//build from intRow & intCol
											if(intTurn == 1){
												strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "1";
											}else if(intTurn == 2){
												strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "2";
											}else if(intTurn == 3){
												strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "3";
											}else if(intTurn == 4){
												strBoard[(intRowDrop)+intCount][(intColDrop)+intCount2] = "4";
											}
										}
									}
								}
								turnOver = true;
								//System.out.println("intPiece: "+intPiece);
								PieceTaken[intTurn][intPiece] = 0;
															
								//System.out.println("changed side array");	
								if(intTurn == 1){
									if(intPiece == 1){
										strP1SidePieces[0][0] = "0";
									}else if(intPiece == 2){
										strP1SidePieces[2][0] = "0";
										strP1SidePieces[3][0] = "0";
									}else if(intPiece == 3){
										strP1SidePieces[5][0] = "0";
										strP1SidePieces[6][0] = "0";
										strP1SidePieces[7][0] = "0";
									}else if(intPiece == 4){
										strP1SidePieces[1][5] = "0";
										strP1SidePieces[1][6] = "0";
										strP1SidePieces[0][6] = "0";						
									}else if(intPiece == 5){
										strP1SidePieces[6][2] = "0";
										strP1SidePieces[7][2] = "0";
										strP1SidePieces[8][2] = "0";
										strP1SidePieces[9][2] = "0";
									}else if(intPiece == 6){
										strP1SidePieces[6][4] = "0";
										strP1SidePieces[7][4] = "0";
										strP1SidePieces[8][4] = "0";
										strP1SidePieces[8][5] = "0";								
									}else if(intPiece == 7){
										strP1SidePieces[10][12] = "0";
										strP1SidePieces[10][13] = "0";
										strP1SidePieces[11][13] = "0";
										strP1SidePieces[10][14] = "0";								
									}else if(intPiece == 8){
										strP1SidePieces[0][2] = "0";
										strP1SidePieces[1][2] = "0";
										strP1SidePieces[0][3] = "0";
										strP1SidePieces[1][3] = "0";							
									}else if(intPiece == 9){
										strP1SidePieces[4][2] = "0";
										strP1SidePieces[4][3] = "0";
										strP1SidePieces[3][3] = "0";
										strP1SidePieces[3][4] = "0"; 
									}else if(intPiece == 10){
										strP1SidePieces[9][0] = "0";
										strP1SidePieces[10][0] = "0";
										strP1SidePieces[11][0] = "0";
										strP1SidePieces[12][0] = "0";
										strP1SidePieces[13][0] = "0";
									}else if(intPiece == 11){
										strP1SidePieces[11][2] = "0";
										strP1SidePieces[12][2] = "0";
										strP1SidePieces[13][2] = "0";
										strP1SidePieces[14][2] = "0";
										strP1SidePieces[14][3] = "0";
									}else if(intPiece == 12){
										strP1SidePieces[11][4] = "0";
										strP1SidePieces[11][5] = "0";
										strP1SidePieces[11][6] = "0";
										strP1SidePieces[10][6] = "0";
										strP1SidePieces[10][7] = "0";
									}else if(intPiece == 13){
										strP1SidePieces[14][5] = "0";
										strP1SidePieces[14][6] = "0";
										strP1SidePieces[14][7] = "0";
										strP1SidePieces[13][6] = "0";
										strP1SidePieces[13][7] = "0";
									}else if(intPiece == 14){
										strP1SidePieces[12][15] = "0";
										strP1SidePieces[13][15] = "0";
										strP1SidePieces[14][15] = "0";
										strP1SidePieces[14][14] = "0";
										strP1SidePieces[14][13] = "0";
									}else if(intPiece == 15){
										strP1SidePieces[6][11] = "0";
										strP1SidePieces[5][11] = "0";
										strP1SidePieces[5][12] = "0";
										strP1SidePieces[4][12] = "0";
										strP1SidePieces[4][13] = "0";
									}else if(intPiece == 16){
										strP1SidePieces[2][13] = "0";
										strP1SidePieces[1][13] = "0";
										strP1SidePieces[1][14] = "0";
										strP1SidePieces[1][15] = "0";
										strP1SidePieces[0][15] = "0";
									}else if(intPiece == 17){
										strP1SidePieces[0][8] = "0";
										strP1SidePieces[0][9] = "0";
										strP1SidePieces[0][10] = "0";
										strP1SidePieces[1][8] = "0";
										strP1SidePieces[1][10] = "0";
									}else if(intPiece == 18){
										strP1SidePieces[10][10] = "0";
										strP1SidePieces[11][10] = "0";
										strP1SidePieces[12][10] = "0";
										strP1SidePieces[13][10] = "0";
										strP1SidePieces[11][9] = "0";
									}else if(intPiece == 19){
										strP1SidePieces[6][8] = "0";
										strP1SidePieces[7][7] = "0";
										strP1SidePieces[7][8] = "0";
										strP1SidePieces[7][9] = "0";
										strP1SidePieces[8][8] = "0";
									}else if(intPiece == 20){
										strP1SidePieces[3][7] = "0";
										strP1SidePieces[4][6] = "0";
										strP1SidePieces[4][7] = "0";
										strP1SidePieces[4][8] = "0";
										strP1SidePieces[5][6] = "0";
									}else if(intPiece == 21){
										strP1SidePieces[6][14] = "0";
										strP1SidePieces[7][14] = "0";
										strP1SidePieces[8][14] = "0";
										strP1SidePieces[8][13] = "0";
										strP1SidePieces[8][15] = "0";
									}
								}else if(intTurn == 2){
									if(intPiece == 1){
										strP2SidePieces[0][0] = "0";
									}else if(intPiece == 2){
										strP2SidePieces[2][0] = "0";
										strP2SidePieces[3][0] = "0";
									}else if(intPiece == 3){
										strP2SidePieces[5][0] = "0";
										strP2SidePieces[6][0] = "0";
										strP2SidePieces[7][0] = "0";
									}else if(intPiece == 4){
										strP2SidePieces[1][5] = "0";
										strP2SidePieces[1][6] = "0";
										strP2SidePieces[0][6] = "0";						
									}else if(intPiece == 5){
										strP2SidePieces[6][2] = "0";
										strP2SidePieces[7][2] = "0";
										strP2SidePieces[8][2] = "0";
										strP2SidePieces[9][2] = "0";
									}else if(intPiece == 6){
										strP2SidePieces[6][4] = "0";
										strP2SidePieces[7][4] = "0";
										strP2SidePieces[8][4] = "0";
										strP2SidePieces[8][5] = "0";								
									}else if(intPiece == 7){
										strP2SidePieces[10][12] = "0";
										strP2SidePieces[10][13] = "0";
										strP2SidePieces[11][13] = "0";
										strP2SidePieces[10][14] = "0";								
									}else if(intPiece == 8){
										strP2SidePieces[0][2] = "0";
										strP2SidePieces[1][2] = "0";
										strP2SidePieces[0][3] = "0";
										strP2SidePieces[1][3] = "0";							
									}else if(intPiece == 9){
										strP2SidePieces[4][2] = "0";
										strP2SidePieces[4][3] = "0";
										strP2SidePieces[3][3] = "0";
										strP2SidePieces[3][4] = "0"; 
									}else if(intPiece == 10){
										strP2SidePieces[9][0] = "0";
										strP2SidePieces[10][0] = "0";
										strP2SidePieces[11][0] = "0";
										strP2SidePieces[12][0] = "0";
										strP2SidePieces[13][0] = "0";
									}else if(intPiece == 11){
										strP2SidePieces[11][2] = "0";
										strP2SidePieces[12][2] = "0";
										strP2SidePieces[13][2] = "0";
										strP2SidePieces[14][2] = "0";
										strP2SidePieces[14][3] = "0";
									}else if(intPiece == 12){
										strP2SidePieces[11][4] = "0";
										strP2SidePieces[11][5] = "0";
										strP2SidePieces[11][6] = "0";
										strP2SidePieces[10][6] = "0";
										strP2SidePieces[10][7] = "0";
									}else if(intPiece == 13){
										strP2SidePieces[14][5] = "0";
										strP2SidePieces[14][6] = "0";
										strP2SidePieces[14][7] = "0";
										strP2SidePieces[13][6] = "0";
										strP2SidePieces[13][7] = "0";
									}else if(intPiece == 14){
										strP2SidePieces[12][15] = "0";
										strP2SidePieces[13][15] = "0";
										strP2SidePieces[14][15] = "0";
										strP2SidePieces[14][14] = "0";
										strP2SidePieces[14][13] = "0";
									}else if(intPiece == 15){
										strP2SidePieces[6][11] = "0";
										strP2SidePieces[5][11] = "0";
										strP2SidePieces[5][12] = "0";
										strP2SidePieces[4][12] = "0";
										strP2SidePieces[4][13] = "0";
									}else if(intPiece == 16){
										strP2SidePieces[2][13] = "0";
										strP2SidePieces[1][13] = "0";
										strP2SidePieces[1][14] = "0";
										strP2SidePieces[1][15] = "0";
										strP2SidePieces[0][15] = "0";
									}else if(intPiece == 17){
										strP2SidePieces[0][8] = "0";
										strP2SidePieces[0][9] = "0";
										strP2SidePieces[0][10] = "0";
										strP2SidePieces[1][8] = "0";
										strP2SidePieces[1][10] = "0";
									}else if(intPiece == 18){
										strP2SidePieces[10][10] = "0";
										strP2SidePieces[11][10] = "0";
										strP2SidePieces[12][10] = "0";
										strP2SidePieces[13][10] = "0";
										strP2SidePieces[11][9] = "0";
									}else if(intPiece == 19){
										strP2SidePieces[6][8] = "0";
										strP2SidePieces[7][7] = "0";
										strP2SidePieces[7][8] = "0";
										strP2SidePieces[7][9] = "0";
										strP2SidePieces[8][8] = "0";
									}else if(intPiece == 20){
										strP2SidePieces[3][7] = "0";
										strP2SidePieces[4][6] = "0";
										strP2SidePieces[4][7] = "0";
										strP2SidePieces[4][8] = "0";
										strP2SidePieces[5][6] = "0";
									}else if(intPiece == 21){
										strP2SidePieces[6][14] = "0";
										strP2SidePieces[7][14] = "0";
										strP2SidePieces[8][14] = "0";
										strP2SidePieces[8][13] = "0";
										strP2SidePieces[8][15] = "0";
									}
								}else if(intTurn == 3){
									if(intPiece == 1){
										strP3SidePieces[0][0] = "0";
									}else if(intPiece == 2){
										strP3SidePieces[2][0] = "0";
										strP3SidePieces[3][0] = "0";
									}else if(intPiece == 3){
										strP3SidePieces[5][0] = "0";
										strP3SidePieces[6][0] = "0";
										strP3SidePieces[7][0] = "0";
									}else if(intPiece == 4){
										strP3SidePieces[1][5] = "0";
										strP3SidePieces[1][6] = "0";
										strP3SidePieces[0][6] = "0";						
									}else if(intPiece == 5){
										strP3SidePieces[6][2] = "0";
										strP3SidePieces[7][2] = "0";
										strP3SidePieces[8][2] = "0";
										strP3SidePieces[9][2] = "0";
									}else if(intPiece == 6){
										strP3SidePieces[6][4] = "0";
										strP3SidePieces[7][4] = "0";
										strP3SidePieces[8][4] = "0";
										strP3SidePieces[8][5] = "0";								
									}else if(intPiece == 7){
										strP3SidePieces[10][12] = "0";
										strP3SidePieces[10][13] = "0";
										strP3SidePieces[11][13] = "0";
										strP3SidePieces[10][14] = "0";								
									}else if(intPiece == 8){
										strP3SidePieces[0][2] = "0";
										strP3SidePieces[1][2] = "0";
										strP3SidePieces[0][3] = "0";
										strP3SidePieces[1][3] = "0";							
									}else if(intPiece == 9){
										strP3SidePieces[4][2] = "0";
										strP3SidePieces[4][3] = "0";
										strP3SidePieces[3][3] = "0";
										strP3SidePieces[3][4] = "0"; 
									}else if(intPiece == 10){
										strP3SidePieces[9][0] = "0";
										strP3SidePieces[10][0] = "0";
										strP3SidePieces[11][0] = "0";
										strP3SidePieces[12][0] = "0";
										strP3SidePieces[13][0] = "0";
									}else if(intPiece == 11){
										strP3SidePieces[11][2] = "0";
										strP3SidePieces[12][2] = "0";
										strP3SidePieces[13][2] = "0";
										strP3SidePieces[14][2] = "0";
										strP3SidePieces[14][3] = "0";
									}else if(intPiece == 12){
										strP3SidePieces[11][4] = "0";
										strP3SidePieces[11][5] = "0";
										strP3SidePieces[11][6] = "0";
										strP3SidePieces[10][6] = "0";
										strP3SidePieces[10][7] = "0";
									}else if(intPiece == 13){
										strP3SidePieces[14][5] = "0";
										strP3SidePieces[14][6] = "0";
										strP3SidePieces[14][7] = "0";
										strP3SidePieces[13][6] = "0";
										strP3SidePieces[13][7] = "0";
									}else if(intPiece == 14){
										strP3SidePieces[12][15] = "0";
										strP3SidePieces[13][15] = "0";
										strP3SidePieces[14][15] = "0";
										strP3SidePieces[14][14] = "0";
										strP3SidePieces[14][13] = "0";
									}else if(intPiece == 15){
										strP3SidePieces[6][11] = "0";
										strP3SidePieces[5][11] = "0";
										strP3SidePieces[5][12] = "0";
										strP3SidePieces[4][12] = "0";
										strP3SidePieces[4][13] = "0";
									}else if(intPiece == 16){
										strP3SidePieces[2][13] = "0";
										strP3SidePieces[1][13] = "0";
										strP3SidePieces[1][14] = "0";
										strP3SidePieces[1][15] = "0";
										strP3SidePieces[0][15] = "0";
									}else if(intPiece == 17){
										strP3SidePieces[0][8] = "0";
										strP3SidePieces[0][9] = "0";
										strP3SidePieces[0][10] = "0";
										strP3SidePieces[1][8] = "0";
										strP3SidePieces[1][10] = "0";
									}else if(intPiece == 18){
										strP3SidePieces[10][10] = "0";
										strP3SidePieces[11][10] = "0";
										strP3SidePieces[12][10] = "0";
										strP3SidePieces[13][10] = "0";
										strP3SidePieces[11][9] = "0";
									}else if(intPiece == 19){
										strP3SidePieces[6][8] = "0";
										strP3SidePieces[7][7] = "0";
										strP3SidePieces[7][8] = "0";
										strP3SidePieces[7][9] = "0";
										strP3SidePieces[8][8] = "0";
									}else if(intPiece == 20){
										strP3SidePieces[3][7] = "0";
										strP3SidePieces[4][6] = "0";
										strP3SidePieces[4][7] = "0";
										strP3SidePieces[4][8] = "0";
										strP3SidePieces[5][6] = "0";
									}else if(intPiece == 21){
										strP3SidePieces[6][14] = "0";
										strP3SidePieces[7][14] = "0";
										strP3SidePieces[8][14] = "0";
										strP3SidePieces[8][13] = "0";
										strP3SidePieces[8][15] = "0";
									}
								}else if(intTurn == 4){
									if(intPiece == 1){
										strP4SidePieces[0][0] = "0";
									}else if(intPiece == 2){
										strP4SidePieces[2][0] = "0";
										strP4SidePieces[3][0] = "0";
									}else if(intPiece == 3){
										strP4SidePieces[5][0] = "0";
										strP4SidePieces[6][0] = "0";
										strP4SidePieces[7][0] = "0";
									}else if(intPiece == 4){
										strP4SidePieces[1][5] = "0";
										strP4SidePieces[1][6] = "0";
										strP4SidePieces[0][6] = "0";						
									}else if(intPiece == 5){
										strP4SidePieces[6][2] = "0";
										strP4SidePieces[7][2] = "0";
										strP4SidePieces[8][2] = "0";
										strP4SidePieces[9][2] = "0";
									}else if(intPiece == 6){
										strP4SidePieces[6][4] = "0";
										strP4SidePieces[7][4] = "0";
										strP4SidePieces[8][4] = "0";
										strP4SidePieces[8][5] = "0";								
									}else if(intPiece == 7){
										strP4SidePieces[10][12] = "0";
										strP4SidePieces[10][13] = "0";
										strP4SidePieces[11][13] = "0";
										strP4SidePieces[10][14] = "0";								
									}else if(intPiece == 8){
										strP4SidePieces[0][2] = "0";
										strP4SidePieces[1][2] = "0";
										strP4SidePieces[0][3] = "0";
										strP4SidePieces[1][3] = "0";							
									}else if(intPiece == 9){
										strP4SidePieces[4][2] = "0";
										strP4SidePieces[4][3] = "0";
										strP4SidePieces[3][3] = "0";
										strP4SidePieces[3][4] = "0"; 
									}else if(intPiece == 10){
										strP4SidePieces[9][0] = "0";
										strP4SidePieces[10][0] = "0";
										strP4SidePieces[11][0] = "0";
										strP4SidePieces[12][0] = "0";
										strP4SidePieces[13][0] = "0";
									}else if(intPiece == 11){
										strP4SidePieces[11][2] = "0";
										strP4SidePieces[12][2] = "0";
										strP4SidePieces[13][2] = "0";
										strP4SidePieces[14][2] = "0";
										strP4SidePieces[14][3] = "0";
									}else if(intPiece == 12){
										strP4SidePieces[11][4] = "0";
										strP4SidePieces[11][5] = "0";
										strP4SidePieces[11][6] = "0";
										strP4SidePieces[10][6] = "0";
										strP4SidePieces[10][7] = "0";
									}else if(intPiece == 13){
										strP4SidePieces[14][5] = "0";
										strP4SidePieces[14][6] = "0";
										strP4SidePieces[14][7] = "0";
										strP4SidePieces[13][6] = "0";
										strP4SidePieces[13][7] = "0";
									}else if(intPiece == 14){
										strP4SidePieces[12][15] = "0";
										strP4SidePieces[13][15] = "0";
										strP4SidePieces[14][15] = "0";
										strP4SidePieces[14][14] = "0";
										strP4SidePieces[14][13] = "0";
									}else if(intPiece == 15){
										strP4SidePieces[6][11] = "0";
										strP4SidePieces[5][11] = "0";
										strP4SidePieces[5][12] = "0";
										strP4SidePieces[4][12] = "0";
										strP4SidePieces[4][13] = "0";
									}else if(intPiece == 16){
										strP4SidePieces[2][13] = "0";
										strP4SidePieces[1][13] = "0";
										strP4SidePieces[1][14] = "0";
										strP4SidePieces[1][15] = "0";
										strP4SidePieces[0][15] = "0";
									}else if(intPiece == 17){
										strP4SidePieces[0][8] = "0";
										strP4SidePieces[0][9] = "0";
										strP4SidePieces[0][10] = "0";
										strP4SidePieces[1][8] = "0";
										strP4SidePieces[1][10] = "0";
									}else if(intPiece == 18){
										strP4SidePieces[10][10] = "0";
										strP4SidePieces[11][10] = "0";
										strP4SidePieces[12][10] = "0";
										strP4SidePieces[13][10] = "0";
										strP4SidePieces[11][9] = "0";
									}else if(intPiece == 19){
										strP4SidePieces[6][8] = "0";
										strP4SidePieces[7][7] = "0";
										strP4SidePieces[7][8] = "0";
										strP4SidePieces[7][9] = "0";
										strP4SidePieces[8][8] = "0";
									}else if(intPiece == 20){
										strP4SidePieces[3][7] = "0";
										strP4SidePieces[4][6] = "0";
										strP4SidePieces[4][7] = "0";
										strP4SidePieces[4][8] = "0";
										strP4SidePieces[5][6] = "0";
									}else if(intPiece == 21){
										strP4SidePieces[6][14] = "0";
										strP4SidePieces[7][14] = "0";
										strP4SidePieces[8][14] = "0";
										strP4SidePieces[8][13] = "0";
										strP4SidePieces[8][15] = "0";
									}
								}
								
								PieceTaken[intTurn][intPiece] = 0;
								//System.out.println();
								//System.out.println("Piece Taken: " + PieceTaken[intTurn][intPiece]);
								intTurn++;
								if(intTurn > 4){
									intTurn = 1;
								}
								boolNewTurn = true;
								checkPieces = true;
							}
						
							//System.out.println("board array");
							
							for(intRow = 0; intRow <24; intRow++){
								////System.out.println("");
								for(intCol = 0; intCol <24; intCol++){
									//System.out.print(strBoard[intRow][intCol]);
								}
							}
							boolDropped = false;
						}
						//System.out.println();
						//System.out.println();
						//System.out.println("Piece Taken: " + PieceTaken[intTurn][intPiece]);
						//System.out.println();
						//System.out.println("intPlayerCount: " + intTurn);
						//System.out.println("intPiece: " + intPiece);
						
					}
				}
			}		
		}else{
			//game not started
		}
	}

	//Constructor
	public BlokusPanel(){
		super();
		//import images
		try{
			white = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/whiteblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(whiteblock.png)");
		}
		try{
			red = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/redblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(redblock.png)");
		}
		try{
			blue = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/blueblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(blueblock.png)");
		}
		try{
			yellow = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/yellowblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(yellowblock.png)");
		}
		try{
			green = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/greenblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(greenblock.png)");
		}try{
			swhite = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/swhiteblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(swhiteblock.png)");
		}
		try{
			sred = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/sredblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(sredblock.png)");
		}
		try{
			sblue = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/sblueblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(sblueblock.png)");
		}
		try{
			syellow = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/syellowblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(syellowblock.png)");
		}
		try{
			sgreen = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/sgreenblock.png"));
		}catch(IOException e){
			//System.out.println("Invalid picture(sgreenblock.png)");
		}
	}
}
