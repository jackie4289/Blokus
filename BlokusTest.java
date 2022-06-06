//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BlokusTest implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	//Properties
	BlokusPanelTest theGamePanel = new BlokusPanelTest();

	Timer theTimer = new Timer(1000/60, this);
	Block BlockModel;

	//J Properties

	JFrame theFrame = new JFrame("BlokusTest");
	JTextField sendTextField = new JTextField();
	JTextArea chatArea = new JTextArea();
	JScrollPane chatScroll = new JScrollPane(chatArea);
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			theGamePanel.repaint();
		}
	}
	public void mouseExited(MouseEvent evt){

	}
	public void mouseEntered(MouseEvent evt){

	}
	public void mousePressed(MouseEvent evt){
		
	}
	public void mouseReleased(MouseEvent evt){
		if(evt.getSource() == theGamePanel){
			theGamePanel.boolDragAndDrop = false;
			theGamePanel.intPiece = theGamePanel.intPiece + 1;
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
			
		}
	}
	public void keyReleased(KeyEvent evt){

	}
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyCode() == 32){
			theGamePanel.boolRotate = true;
			
		}
	}
	public void keyTyped(KeyEvent evt){

	}

	//Constuctor
	public BlokusTest(){
		//Game panel
		theGamePanel.setLayout(null);
		theGamePanel.setPreferredSize(new Dimension(1280, 720));
		theGamePanel.setVisible(true);
		theGamePanel.addMouseMotionListener(this);
		theGamePanel.addMouseListener(this);
		theGamePanel.addKeyListener(this);
		
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
		
		//Pack Frame
		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
	}

	//Main Program
	public static void main(String[] args){
		BlokusTest Block = new BlokusTest();
	}
}
