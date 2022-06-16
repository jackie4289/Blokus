//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlokusGameOverPanel extends JPanel{
	//Properties
	String strName;
	String strTemp;
	String strLine = "";
	String strSplit[] = new String[3];
	int intCountList = 4;
	int intScore = 0;
	int intCount;
	int intOuter;
	BufferedImage gameover = null;
	BufferedReader scoreFile = null;
	BufferedImage red = null;
	BufferedImage blue = null;
	BufferedImage green = null;
	BufferedImage yellow = null;

	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw image
		g.drawImage(gameover, 0, 0, null);
		g.setColor(Color.BLACK);
	
		//Counting people
		//Open File
		try{
			scoreFile = new BufferedReader(new FileReader("Data_Files/HighScores.txt"));
		}catch(FileNotFoundException e){
			System.out.println("File not found!!!(HighScores.txt)");
		}
		//Count number of names for intCountList
		if(scoreFile != null){
			while(strLine != null){
				try{
					strLine = scoreFile.readLine();
					if(strLine != null){			
						intCountList++;
					}
				}catch(IOException e){
					System.out.println("Error reading from file");
				}
			}
			System.out.println("HighScore list count: " + intCountList);
		}
		//close file
		try{
			scoreFile.close();
		}catch(IOException e){
			System.out.println("error closing file");
		}
		
		//Array names, scores and colors
		try{
			scoreFile = new BufferedReader(new FileReader("Data_Files/HighScores.txt"));
		}catch(FileNotFoundException e){
			System.out.println("File not found!!!(HighScores.txt");
		}
		String strPlayers[][] = new String[intCountList][3]; //0 Name, 1 Score, 2 Color
		for(intCount = 0; intCount < intCountList; intCount++){
			if(scoreFile != null){
				try{
					strLine = scoreFile.readLine();		
					if(strLine != null){
						strSplit = strLine.split(",");
						strPlayers[intCount][0] = strSplit[0];
						strPlayers[intCount][1] = strSplit[1];
						strPlayers[intCount][2] = strSplit[2];
					}
				}catch(IOException e){
					System.out.println("Error reading from file");
				}
			}
		}
		//close file
		try{
			scoreFile.close();
		}catch(IOException e){
			System.out.println("error closing file");
		}
		
		//BubbleSort from highest to lowest depending on score
		for(intOuter = 0; intOuter < intCountList-1; intOuter++){
			for(intCount = 0; intCount < intCountList-1; intCount++){
				// compare "left" to "right"
				if(Integer.parseInt(strPlayers[intCount][1]) < Integer.parseInt(strPlayers[intCount + 1][1])){
					strTemp = strPlayers[intCount][0];
					strPlayers[intCount][0] = strPlayers[intCount + 1][0];
					strPlayers[intCount + 1][0] = strTemp;
				
					strTemp = strPlayers[intCount][1];
					strPlayers[intCount][1] = strPlayers[intCount + 1][1];
					strPlayers[intCount + 1][1] = strTemp;
					
					strTemp = strPlayers[intCount][2];
					strPlayers[intCount][2] = strPlayers[intCount + 1][2];
					strPlayers[intCount + 1][2] = strTemp;
				}
			}
		}
		
		//draw top 4 players on screen
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 28)); 
		for(intCount = 0; intCount < 4; intCount++){
			g.drawString(strPlayers[intCount][0], 491, (intCount *  90) + 295);
			g.drawString(strPlayers[intCount][1], 830, (intCount *  90) + 295 );
			if(strPlayers[intCount][2].equals("yellow")){
				g.drawImage(yellow, 724, (intCount *  92) + 267, null);
			}else if(strPlayers[intCount][2].equals("green")){
				g.drawImage(green, 724, (intCount *  92) + 267, null);
			}else if(strPlayers[intCount][2].equals("blue")){
				g.drawImage(blue, 724, (intCount *  92) + 267, null);
			}else if(strPlayers[intCount][2].equals("red")){
				g.drawImage(red, 724, (intCount *  92) + 267, null);
			}
		}
	}
	//Constructor
	public BlokusGameOverPanel(){
		super();
		//import images
		try{
			gameover = ImageIO.read(this.getClass().getResourceAsStream("Assets/panels/gameover.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(highscores.png)");
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
			System.out.println("Invalid picture(blueblock.png)");
		}
		try{
			green = ImageIO.read(this.getClass().getResourceAsStream("Assets/blocks/greenblock.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(blueblock.png)");
		}
	}
}
