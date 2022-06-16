//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BlokusTest2 implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	//Properties
	BlokusPanelTest2 theGamePanel = new BlokusPanelTest2();
	
	Timer theTimer = new Timer(1000/60, this);
	Block BlockModel;

	//J Properties

	JFrame theFrame = new JFrame("BlokusTest");
	JTextField sendTextField = new JTextField();
	JTextArea chatArea = new JTextArea();
	JScrollPane chatScroll = new JScrollPane(chatArea);
	JButton skipButton = new JButton("Skip?");
	//int intTurn = 0;
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			theGamePanel.repaint();
			if(theGamePanel.intTurn == 1){
				skipButton.setLocation(250, 0);
			}else if(theGamePanel.intTurn == 2){
				skipButton.setLocation(1180, 0);		
			}else if(theGamePanel.intTurn == 3){
				skipButton.setLocation(1180, 680);
			}else if(theGamePanel.intTurn == 4){
				skipButton.setLocation(250, 680);
			}
		}else if(evt.getSource() == skipButton){
			if(theGamePanel.intTurn < 4){
				theGamePanel.intTurn++;
			}else{
				theGamePanel.intTurn = 1;
			}
			System.out.println("turn: " + theGamePanel.intTurn);
		}
	}
	public void mouseExited(MouseEvent evt){

	}
	public void mouseEntered(MouseEvent evt){

	}
	public void mousePressed(MouseEvent evt){
		if(evt.getSource()==theGamePanel){
			theGamePanel.mouseX = evt.getX();
			theGamePanel.mouseY = evt.getY();
			theGamePanel.boolDragAndDrop = true;
			System.out.println("START");
			theGamePanel.repaint();
			theFrame.requestFocus();
		}
	}
	public void mouseReleased(MouseEvent evt){
		if(evt.getSource() == theGamePanel){
			theGamePanel.boolDragAndDrop = false;
			theGamePanel.intDropX = evt.getX();
			theGamePanel.intDropY = evt.getY();
			if(theGamePanel.intPiece > 22){
				theGamePanel.intPiece = 0;
			}
			theGamePanel.boolDropped = true;
			theFrame.requestFocus();
		}
		
	}
	public void mouseClicked(MouseEvent evt){
		
	}
	public void mouseMoved(MouseEvent evt){
	}
	public void mouseDragged(MouseEvent evt){
		if(evt.getSource()==theGamePanel){
			theGamePanel.mouseX = evt.getX();
			theGamePanel.mouseY = evt.getY();
			theGamePanel.boolDragAndDrop = true;
			System.out.println("START");
			theGamePanel.repaint();
			theFrame.requestFocus();
			
		}
	}
	public void keyReleased(KeyEvent evt){

	}
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyCode() == 32){
			theGamePanel.boolRotate = true;
			System.out.println("ROTATE");
		}
	}
	public void keyTyped(KeyEvent evt){

	}

	//Constuctor
	public BlokusTest2(){
		//Game panel
		theGamePanel.setLayout(null);
		theGamePanel.setPreferredSize(new Dimension(1280, 720));
		theGamePanel.setVisible(true);
		theGamePanel.addMouseMotionListener(this);
		theGamePanel.addMouseListener(this);
		theFrame.addKeyListener(this);
		
		theFrame.setContentPane(theGamePanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
		//Chat Box
		chatScroll.setSize(540,110);
		chatScroll.setLocation(370, 581);
		chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatArea.setLineWrap(true);
		chatArea.setEditable(false);
		theGamePanel.add(chatScroll); 
		
		//Chat TextField
		sendTextField.setSize(540, 24);
		sendTextField.setLocation(370, 691);
		sendTextField.setForeground(Color.BLACK);
		sendTextField.setBackground(new Color(157, 156, 154));
		sendTextField.addActionListener(this);
		theGamePanel.add(sendTextField);
		
		skipButton.setSize(100,40);
		skipButton.addActionListener(this);
		theGamePanel.add(skipButton);
		
		//Pack Frame
		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
		theFrame.requestFocus();
	}

	//Main Program
	public static void main(String [] args){
		BlokusTest2 Block = new BlokusTest2();
	}
}
