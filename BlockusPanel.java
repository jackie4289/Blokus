import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlockusPanel extends JPanel{
	//Properties
	String intBoard[][] = new String[20][20];
	// 0 = EMPTY
	// 1 = RED (P1)
	// 2 = BLUE (P2)
	int intRow;
	int intCol;
	int intCount;
	int intCount2;
	String intLine;
	String intARow[];
	String strRow;
	BufferedImage white = null;
	BufferedImage red = null;
	BufferedImage blue = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawRect(279, 1, 721, 718);
		g.setColor(Color.WHITE);
		g.fillRect(281,2,719,717);
		
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
				intBoard[intCount][intCount2] = intARow[intCount2];
			}
		}
			
		//Draw array
		for(intRow = 0; intRow < 20; intRow++){
			for(intCol = 0; intCol < 20; intCol++){
				if(intBoard[intRow][intCol].equals("0")){
					g.drawImage(white, 280 + intCol * 36, intRow * 36, null);
				}else if(intBoard[intRow][intCol].equals("1")){
					g.drawImage(red, 280 + intCol * 36, intRow * 36, null);
				}else if(intBoard[intRow][intCol].equals("2")){
					g.drawImage(blue, 280 + intCol * 36, intRow * 36, null);
				}
			}
		}
	}
	
	//Constructor
	public BlockusPanel(){
		super();
		//import image
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
