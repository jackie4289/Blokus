//
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;

public class BlokusHighSPanel extends JPanel{
	//Properties
	String strName;
	String strTemp;
	String strLine = "";
	String strSplit[];
	int intCountList = 0;
	int intScore = 0;
	int intCount;
	int intOuter;
	BufferedImage highscore = null;
	BufferedReader scoreFile = null;
	String strPlayers[][] = new String[intCountList][2];
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw image
		g.drawImage(highscore, 0, 0, null);
		g.setColor(Color.BLACK);
		
		//Counting people
		//Open File
		try{
			scoreFile = new BufferedReader(new FileReader("HighScores.txt"));
		}catch(FileNotFoundException e){
			System.out.println("File not found!!!(HighScores.txt");
		}
		//Count number of names
		if(scoreFile != null){
			while(strLine != null){
				try{
					strLine = scoreFile.readLine();
					strSplit = strLine.split(",");
					strPlayers[intCountList][0] = strSplit[0];
					strPlayers[intCountList][1] = strSplit[1];
					strPlayers[intCountList][2] = strSplit[2];
					if(strLine != null){
						intCountList++;
					}
				}catch(IOException e){
					System.out.println("Error reading from file");
				}
			}
			try{
				scoreFile.close();
			}catch(IOException e){
				System.out.println("Unable to close file");
			}
			System.out.println("HighScore list count: " + intCountList);
		}
		

		
		//Array
		for(intCount = 0; intCount < intCountList; intCount++){
			try{
				strName = scoreFile.readLine();
				intScore = Integer.parseInt(scoreFile.readLine());
				strHighScore[intCount][0] = strName;
				strHighScore[intCount][1] = intScore + "";
			}catch(IOException e){
				System.out.println("error");
			}
		scoreFile.close();
		}
		/*
		//BubbleSort
		

		for(intOuter = 0; intOuter < intCountList-1; intOuter++){
			for(intCount = 0; intCount < intCountList-1; intCount++){
				// compare "left" to "right"
				if(Double.parseDouble(strHighScore[intCount][1]) < Double.parseDouble(strHighScore[intCount + 1][1])){
					strTemp = strHighScore[intCount][1];
					strHighScore[intCount][1] = strHighScore[intCount + 1][1];
					strHighScore[intCount + 1][1] = strTemp;

					strTemp = strHighScore[intCount][0];
					strHighScore[intCount][0] = strHighScore[intCount + 1][0];
					strHighScore[intCount + 1][0] = strTemp;
				}
			}
		}
		scoreFile.close();
		//Print player list
		*/
	
	}
	//Constructor
	public BlokusHighSPanel(){
		super();
		//import images
		try{
			highscore = ImageIO.read(this.getClass().getResourceAsStream("highscores.png"));
		}catch(IOException e){
			System.out.println("Invalid picture(highscores.png)");
		}
	}
}
