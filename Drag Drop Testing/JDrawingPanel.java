import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
public class JDrawingPanel extends JPanel{
	//Properties
	//These two variables are for determining where the moving block will be
	//It will also help with deciding which area to put the block in
	int intX;
	int intY;
	
	//We won't need this
	BufferedImage imgPrivate = null;
	
	//We create an array. Imagine this as a mini GOGBoard array
	boolean blnArray[][] = new boolean[3][3];
	
	//This variable tells us that no piece movement is currently happening
	boolean blnActive=false;
	
	//This will help us determine whether we can move to new row or not
	int intOGRow;
	int intOGClm;
	int intNewRow;
	int intNewClm;
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Methods
	//This paints blocks on the panel
	public void paintBlocks(Graphics g){
		int intClm;
		int intRow;
		for(intRow=0;intRow<3;intRow++){
			for(intClm=0;intClm<3;intClm++){
				//if in a certain row and certain column, a block is there(true), then...
				if(blnArray[intRow][intClm]==true){
					//Paint the block at that location
					g.fillRect(intClm*200,intRow*200,200,200);
				}
			}
		}
	}
	
	//When they press on an area with a block, we do the following...
	public void ridBlock(int IntPosX, int IntPosY){
		int intClm;
		int intRow;
		
		//Inside a certain row and column...
		for(intRow=0;intRow<3;intRow++){
			for(intClm=0;intClm<3;intClm++){
				//if the x and y position is...
				//-more than the left side wall
				//-less than the right side wall
				//-more than the top side wall
				//-less than the bottom side wall
				//then...
				if(IntPosX>intClm*200 && IntPosX<(intClm+1)*200 && IntPosY>intRow*200 && IntPosY<(intRow+1)*200){
					//If in that spot, there is already a block there(true), then...
					if(this.blnArray[intRow][intClm]==true){
						//Get rid of that block so we can replace it with an active block that we paint
						this.blnArray[intRow][intClm]=false;
						//We set the boolean active to true because we are now moving a block
						this.blnActive=true;
						//We note the original row and column
						intOGRow=intRow;
						intOGClm=intClm;
					}
				}
			}
		}
	}
	
	//When they release their mouse, we use this method
	public void placeInSlot(){
		int intClm;
		int intRow;
		
		//Inside a certain row and column...
		for(intRow=0;intRow<3;intRow++){
			for(intClm=0;intClm<3;intClm++){
				//if the x and y position is...
				//-more than the left side wall
				//-less than the right side wall
				//-more than the top side wall
				//-less than the bottom side wall
				//then...
				if(intX+100>intClm*200 && intX+100<(intClm+1)*200 && intY+100>intRow*200 && intY+100<(intRow+1)*200){
					//we note this new row and column
					intNewRow=intRow;
					intNewClm=intClm;
					
					//If it's in the same row but moves right by 1, then...
					if(intOGRow==intNewRow && intOGClm+1==intNewClm){
						//place the block in the new row and column
						blnArray[intRow][intClm]=true;
					//If it's in the same row but moves left by 1, then...
					}else if(intOGRow==intNewRow && intOGClm-1==intNewClm){
						//place the block in the new row and column
						blnArray[intRow][intClm]=true;
					//If it's in the same column but moves down by 1, then...
					}else if(intOGRow+1==intNewRow && intOGClm==intNewClm){
						//place the block in the new row and column
						blnArray[intRow][intClm]=true;
					//If it's in the same column but moves up by 1, then...
					}else if(intOGRow-1==intNewRow && intOGClm==intNewClm){
						//place the block in the new row and column
						blnArray[intRow][intClm]=true;
					//If none of this is true, then move it back to the original position before
					//it was pressed
					}else{
						blnArray[intOGRow][intOGClm]=true;
					}
					//Either way, we use this method when they let go so once they let go, no more drag
					//that means it's inative(false)
					this.blnActive=false;
				}
			}
		}
	}
	//We call this in our repaint method
	public void paintActiveBlock(Graphics g){
		//If active is true, which means user is dragging, then we draw the moving block
		if(blnActive==true){
			//We draw the block
			g.setColor(Color.BLACK);
			g.drawRect(intX,intY,200,200);
			
			//We draw white dot to let them know where they are
			g.setColor(Color.WHITE);
			g.fillRect(intX+95,intY+95,10,10);
			
			//Meh
			g.setColor(Color.BLACK);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Use the Graphics object to draw stuff
		g.setColor(Color.BLACK);
		//draw the lines
		g.drawLine(200,0,200,600);
		g.drawLine(400,0,400,600);
		g.drawLine(0,200,600,200);
		g.drawLine(0,400,600,400);
		
		//we paitn the blocks
		paintBlocks(g);
		
		paintActiveBlock(g);
		//g.drawImage(imgPrivate, intX,intY, null);
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Constructor
	public JDrawingPanel(){
		super();
		/*try{
			imgPrivate = ImageIO.read(new File("private.png"));
			System.out.println("Picture loaded successfully");
		}catch(IOException e){
			System.out.println("Invalid Picture");
		}*/
		blnArray[1][0] = true;
		blnArray[1][2] = true;
	}
}
