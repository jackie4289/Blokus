//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Blokus implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	//Properties
	Timer theTimer = new Timer(1000/60, this);
	SuperSocketMaster ssm;
	Blok BlokModel;
	int intPort;
	int intConnected = 0;
	String strIp;
	String strUsername;
	String strRecieve;
	String strMsgSplit[];
	String strName[] = new String[4];
	boolean boolPort = false;
	boolean boolIp = false;
	boolean boolUsername = false;
	

	//J Properties
	BlokusPanel theGamePanel = new BlokusPanel();
	BlokusMenuPanel theMenuPanel = new BlokusMenuPanel();
	BlokusLoginPanel theLoginPanel = new BlokusLoginPanel();
	BlokusHelpPanel theHelpPanel = new BlokusHelpPanel();
	JButton loginButton = new JButton("Login");
	JButton quitButton = new JButton("Quit");
	JButton connectButton = new JButton ("Connect!");
	JButton startButton = new JButton("Start!");
	JFrame theFrame = new JFrame("Blokus");
	JTextField usernameField = new JTextField();
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	JTextField sendTextField = new JTextField();
	JRadioButton serverRButton = new JRadioButton("Server");
	JRadioButton clientRButton = new JRadioButton("Client");
	ButtonGroup buttonGroup = new ButtonGroup();
	JTextArea chatArea = new JTextArea();
	JScrollPane chatScroll = new JScrollPane(chatArea);
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			theGamePanel.repaint();
			theLoginPanel.repaint();
		}else if(evt.getSource() == quitButton){
			System.exit(0);
		}else if(evt.getSource() == loginButton){
			theMenuPanel.setVisible(false);
			theLoginPanel.setVisible(true);
			theFrame.setContentPane(theLoginPanel);		
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
			if(strIp.length() != 0){
				boolIp = true;
			}
			
			//strUsername value
			strUsername = usernameField.getText();
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
						System.out.println("Client connected!");
						//disable all fields and buttons and wait for players
						connectButton.setEnabled(false);
						usernameField.setEnabled(false);
						ipField.setEnabled(false);
						portField.setEnabled(false);
						serverRButton.setEnabled(false);
						clientRButton.setEnabled(false);
						//send name to server
						ssm.sendText(strUsername);
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
						connectButton.setEnabled(false);
						usernameField.setEnabled(false);
						ipField.setEnabled(false);
						portField.setEnabled(false);
						serverRButton.setEnabled(false);
						clientRButton.setEnabled(false);
						startButton.setVisible(true);
						startButton.setEnabled(false);
						theLoginPanel.strName[0] = strUsername;
						theGamePanel.strName[0] = strUsername;
						intConnected++;
						
					}else{
						System.out.println("Server connect failed!");
					}
				}
			}
		}else if(evt.getSource() == serverRButton){
			ipField.setText("localhost");
		}else if(evt.getSource() == clientRButton){
			ipField.setText("127.0.0.1");
		}else if(evt.getSource() == sendTextField){
			//Send Text
			if(ssm != null && theGamePanel.boolStartGame == true){
				//get time
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
				LocalTime localTime = LocalTime.now();
				//Send with name + time
				ssm.sendText("chat,"+dtf.format(localTime)+ " " + strUsername + ": " + sendTextField.getText());
				chatArea.append(dtf.format(localTime)+ " " + strUsername + ": " + sendTextField.getText() + "\n");
				sendTextField.setText("");
				//Focus cycle
				sendTextField.setFocusable(false);
				sendTextField.setFocusable(true);
			}
		}else if(evt.getSource() == ssm){
			//Split command
			String strRecieve = ssm.readText();
			strMsgSplit = strRecieve.split(",");
			if(theGamePanel.boolStartGame == true){
				//Recieve Text
				if(strMsgSplit[0].equals("chat")){
					chatArea.append(strMsgSplit[1] + "\n");
					chatArea.setCaretPosition(chatArea.getDocument().getLength());
				}
			}else if(serverRButton.isSelected() && theGamePanel.boolStartGame == false){
				//Recieve name from client to server
				theLoginPanel.strName[intConnected] = ssm.readText();
				theGamePanel.strName[intConnected] = ssm.readText();
				intConnected++;
				System.out.println("playerCount: " + intConnected);
				if(intConnected == 4 ){
					startButton.setEnabled(true);
				}
				//Send names to clients from server
				if(intConnected <= 2){
					ssm.sendText("P1," + theLoginPanel.strName[0]);
					ssm.sendText("P2," + theLoginPanel.strName[1]);
				}else if(intConnected <= 3){
					ssm.sendText("P1," + theLoginPanel.strName[0]);
					ssm.sendText("P2," + theLoginPanel.strName[1]);
					ssm.sendText("P3," + theLoginPanel.strName[2]);
				}else if(intConnected <= 4){
					ssm.sendText("P1," + theLoginPanel.strName[0]);
					ssm.sendText("P2," + theLoginPanel.strName[1]);
					ssm.sendText("P3," + theLoginPanel.strName[2]);
					ssm.sendText("P4," + theLoginPanel.strName[3]);
				}
					System.out.println("SENT Names");
			}else if(clientRButton.isSelected() && theGamePanel.boolStartGame == false){
				//insert names to client array
				if(strMsgSplit[0].equals("P1")){
					theLoginPanel.strName[0] = strMsgSplit[1];
					theGamePanel.strName[0] = strMsgSplit[1];
				}else if(strMsgSplit[0].equals("P2")){
					theLoginPanel.strName[1] = strMsgSplit[1];
					theGamePanel.strName[1] = strMsgSplit[1];
				}else if(strMsgSplit[0].equals("P3")){
					theLoginPanel.strName[2] = strMsgSplit[1];
					theGamePanel.strName[2] = strMsgSplit[1];
				}else if(strMsgSplit[0].equals("P4")){
					theLoginPanel.strName[3] = strMsgSplit[1];
					theGamePanel.strName[3] = strMsgSplit[1];
				}
				System.out.println("Refreshed Names");
				//start game command for client
				if(strMsgSplit[0].equals("ssmStart")){
					theLoginPanel.setVisible(false);
					theGamePanel.setVisible(true);
					theFrame.setContentPane(theGamePanel);
					theGamePanel.boolStartGame = true;
					System.out.println("STARTS");
					theGamePanel.repaint();
					theFrame.setVisible(false);
					theFrame.setVisible(true);
				}
			}
						
		}else if(evt.getSource() == startButton){
			//Start Game, display game panel
			//server send start signal to client
			if(theGamePanel.boolStartGame == false){
				ssm.sendText("ssmStart,");
			}
			theLoginPanel.setVisible(false);
			theGamePanel.setVisible(true);
			theFrame.setContentPane(theGamePanel);
			theGamePanel.boolStartGame = true;
			System.out.println("STARTS");

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
		if(theGamePanel.boolYourTurn == true){
			if(theGamePanel.boolStartGame == true){
				theGamePanel.mouseX = evt.getX();
				theGamePanel.mouseY = evt.getY();
			}
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
	public Blokus(){
		//Game panel
		theGamePanel.setLayout(null);
		theGamePanel.setPreferredSize(new Dimension(1280, 720));
		theGamePanel.setVisible(false);
		//theGamePanel.setVisible(true);
		theGamePanel.addMouseMotionListener(this);
		theGamePanel.addMouseListener(this);
		theGamePanel.addKeyListener(this);

		
		//Menu Panel
		theMenuPanel.setLayout(null);
		theMenuPanel.setPreferredSize(new Dimension(1280, 720));
		theMenuPanel.setVisible(true);
		//theMenuPanel.setVisible(false);
		
		//Login Panel
		theLoginPanel.setLayout(null);
		theLoginPanel.setPreferredSize(new Dimension(1280, 720));
		theLoginPanel.setVisible(false);
		
		//theFrame.setContentPane(theGamePanel);
		theFrame.setContentPane(theMenuPanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
		//Login Button Menu
		loginButton.setSize(300, 50);
		loginButton.setLocation(170, 500);
		loginButton.addActionListener(this);
		theMenuPanel.add(loginButton);
		
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
		
		//Start Button Login
		startButton.setSize(271, 42);
		startButton.setLocation(948, 485);
		startButton.addActionListener(this);
		theLoginPanel.add(startButton);
		startButton.setVisible(false);
		
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
		clientRButton.addActionListener(this);
		theLoginPanel.add(clientRButton);
		
		//buttonGroups
		buttonGroup.add(clientRButton);
		buttonGroup.add(serverRButton);
		
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
		Blokus Block = new Blokus();
	}
}
