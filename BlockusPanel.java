// 
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlockusPanel extends JPanel{
	//Properties
	// Arrays
	String strBoard[][] = new String[20][20];
	String intARow[];
	// 0 = EMPTY
	// 1 = RED (P1)
	// 2 = BLUE (P2)
	// Booleans
	boolean boolStartGame = false;

	// Integers
	int intRow;
	int intCol;
	int intCount;
	int intCount2;

	// Strings
	String intLine;
	String strRow;

	// Images
	BufferedImage white = null;
	BufferedImage red = null;
	BufferedImage blue = null;

	//Methods
	public void paintComponent(/*graphics variable*/Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(279, 1, 721, 718);
		g.setColor(Color.WHITE);
		g.fillRect(281,2,719,717);

		if(boolStartGame == false){
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
						g.drawImage(white, 280 + intCol * 36, intRow * 36, null);
					}else if(strBoard[intRow][intCol].equals("1")){
						g.drawImage(red, 280 + intCol * 36, intRow * 36, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(blue, 280 + intCol * 36, intRow * 36, null);
					}
				}
			}
			// this is just to test, will remove it later and replace with button
			boolStartGame = true;
		}else{
			// NOW THE GAME STARTS








			//Draw array
			for(intRow = 0; intRow < 20; intRow++){
				for(intCol = 0; intCol < 20; intCol++){
					if(strBoard[intRow][intCol].equals("0")){
						g.drawImage(white, 280 + intCol * 36, intRow * 36, null);
					}else if(strBoard[intRow][intCol].equals("1")){
						g.drawImage(red, 280 + intCol * 36, intRow * 36, null);
					}else if(strBoard[intRow][intCol].equals("2")){
						g.drawImage(blue, 280 + intCol * 36, intRow * 36, null);
					}
				}
			}
		}
	}

	//Constructor
	public BlockusPanel(){
		super();
		//import images
		try{
			white = ImageIO.read(new File("whiteblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(whiteblock.png)");
		}
		try{
			red = ImageIO.read(new File("redblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(redblock.png)");
		}
		try{
			blue = ImageIO.read(new File("blueblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(blueblock.png)");
		}
	}
}
