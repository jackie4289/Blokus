//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class Blockus implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	//Properties
	JFrame theFrame = new JFrame("Blockus");
	JTextField usernameField = new JTextField();
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	JRadioButton serverRButton = new JRadioButton("Server");
	JRadioButton clientRButton = new JRadioButton("Client");
	ButtonGroup buttonGroup = new ButtonGroup();
	BlockusPanel theGamePanel = new BlockusPanel();
	BlockusMenuPanel theMenuPanel = new BlockusMenuPanel();
	BlockusLoginPanel theLoginPanel = new BlockusLoginPanel();
	Timer theTimer = new Timer(1000/60, this);
	SuperSocketMaster ssm;
	Block BlockModel;
	int intPort;
	String strIp;
	String strUsername;
	boolean boolPort = false;
	boolean boolIp = false;
	boolean boolUsername = false;

	//J Properties
	JButton startButton = new JButton("Start");
	JButton quitButton = new JButton("Quit");
	JButton connectButton = new JButton ("Connect!");
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			theGamePanel.repaint();
		}else if(evt.getSource() == quitButton){
			System.exit(0);
		}else if(evt.getSource() == startButton){
			theMenuPanel.setVisible(false);
			theLoginPanel.setVisible(true);
			//FOR TESTING CHANGE TO (theGamePanel)
			theFrame.setContentPane(theLoginPanel);		
			//theFrame.setContentPane(theGamePanel);		
		}else if(evt.getSource() == connectButton){
			boolPort = false;
			boolIp = false;
			boolUsername = false;
			//intPort value
			try{
				intPort = Integer.parseInt(portField.getText());
				System.out.println("Port number: " + intPort);
			}catch(NumberFormatException e){
				intPort = 6112;
				System.out.println("Port number error, defaulting to 6112");
			}
			
			boolPort = true;
			
			//strIp value
			strIp = ipField.getText();
			System.out.println("IP: " + strIp);
			if(strIp.length() != 0){
				boolIp = true;
			}
			
			//strUsername value
			strUsername = usernameField.getText();
			System.out.println("Username: " + strUsername);
			if(strUsername.length() != 0){
				boolUsername = true;
			}
			
			System.out.println("port: " + boolPort + " " + intPort);
			System.out.println("ip: " + boolIp + " " + strIp);
			System.out.println("name: " + boolUsername + " " + strUsername);
			
			//Start condition
			if(boolIp == true && boolPort == true && boolUsername == true){
				//Client
				if(clientRButton.isSelected()){
					System.out.println("starting client....");
					ssm = new SuperSocketMaster(strIp, intPort, this);
					boolean boolConnect = ssm.connect();
					System.out.println(boolConnect);
					//if client connection true
					if(boolConnect){
						theLoginPanel.setVisible(false);
						theGamePanel.setVisible(true);
						theFrame.setContentPane(theGamePanel);	
						theGamePanel.boolStartGame = true;
						System.out.println("Client connected!");
					}else{
						System.out.println("Client connect failed!");
					}
				}
				
				//Server
				if(serverRButton.isSelected()){
					System.out.println("starting server....");
					ssm = new SuperSocketMaster(intPort, this);
					boolean boolConnect = ssm.connect();
					System.out.println(boolConnect);
					//if server connection true	
					if(boolConnect){
						theLoginPanel.setVisible(false);
						theGamePanel.setVisible(true);
						theFrame.setContentPane(theGamePanel);	
						theGamePanel.boolStartGame = true;
						System.out.println("Server connected!");
					}else{
						System.out.println("Server connect failed!");
					}
				}
			}
		}else if(evt.getSource() == serverRButton){
			ipField.setText("localhost");
		}
	}
	public void mouseExited(MouseEvent evt){

	}
	public void mouseEntered(MouseEvent evt){

	}
	public void mousePressed(MouseEvent evt){

	}
	public void mouseReleased(MouseEvent evt){

	}
	public void mouseClicked(MouseEvent evt){

	}
	public void mouseMoved(MouseEvent evt){
		if(theGamePanel.boolStartGame == true){
			theGamePanel.mouseX = evt.getX();
			theGamePanel.mouseY = evt.getY();
		}
	}
	public void mouseDragged(MouseEvent evt){
		
	}
	public void keyReleased(KeyEvent evt){

	}
	public void keyPressed(KeyEvent evt){

	}
	public void keyTyped(KeyEvent evt){

	}


	//Constuctor
	public Blockus(){
		//Game panel
		theGamePanel.setLayout(null);
		theGamePanel.setPreferredSize(new Dimension(1280, 720));
		theGamePanel.setVisible(false);
		
		//Menu Panel
		theMenuPanel.setLayout(null);
		theMenuPanel.setPreferredSize(new Dimension(1280, 720));
		theMenuPanel.setVisible(true);
		
		//Login Panel
		theLoginPanel.setLayout(null);
		theLoginPanel.setPreferredSize(new Dimension(1280, 720));
		theLoginPanel.setVisible(false);
		
		theFrame.setContentPane(theMenuPanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
		//Start Button Menu
		startButton.setSize(300, 50);
		startButton.setLocation(170, 500);
		startButton.addActionListener(this);
		theMenuPanel.add(startButton);
		
		//Quit Button Menu
		quitButton.setSize(300, 50);
		quitButton.setLocation(170, 555);
		quitButton.addActionListener(this);
		theMenuPanel.add(quitButton);
		
		//Connect Button Login
		connectButton.setSize(200, 25);
		connectButton.setLocation(556, 510);
		connectButton.addActionListener(this);
		theLoginPanel.add(connectButton);
		
		//username Textfield
		usernameField = new JTextField();
		usernameField.setSize(175, 36);
		usernameField.setLocation(560, 314);
		theLoginPanel.add(usernameField);
		
		//ip Textfield
		ipField = new JTextField();
		ipField.setSize(217, 36);
		ipField.setLocation(447, 392);
		theLoginPanel.add(ipField);
		
		//port Textfield
		portField = new JTextField();
		portField.setSize(117, 36);
		portField.setLocation(713, 392);
		theLoginPanel.add(portField);
		
		//ServerMode RadioButton
		serverRButton.setSize(100,25);
		serverRButton.setLocation(552, 468);
		serverRButton.setOpaque(false);
		serverRButton.addActionListener(this);
		theLoginPanel.add(serverRButton);
		
		//ClientMode RadioButton
		clientRButton.setSize(100,25);
		clientRButton.setLocation(687,468);
		clientRButton.setOpaque(false);
		theLoginPanel.add(clientRButton);
		
		//buttonGroups
		buttonGroup.add(clientRButton);
		buttonGroup.add(serverRButton);
		
		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
	}

	//Main Program
	public static void main(String[] args){
		Blockus Block = new Blockus();
	}
}
