//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Test implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	//Properties
	Timer theTimer = new Timer(1000/60, this);
	SuperSocketMaster ssm;
	BlokTest2 BlokModel;
	int intPort;
	int intConnected = 0;
	int intServerTurn = 0;
	String strIp;
	String strRecieve;
	String strMsgSplit[];
	String strName[] = new String[3];
	boolean boolPort = false;
	boolean boolIp = false;
	boolean boolUsername = false;

	//J Properties
	BlokusPanelTest3 theGamePanel = new BlokusPanelTest3();
	JFrame theFrame = new JFrame("Blokus");
	JTextField sendTextField = new JTextField();
	JTextArea chatArea = new JTextArea();
	JScrollPane chatScroll = new JScrollPane(chatArea);
	JButton skipButton = new JButton("Skip?");
	BlokusMenuPanel theMenuPanel = new BlokusMenuPanel();
	JButton loginButton = new JButton("Login");
	JButton quitButton = new JButton("Quit");
	JButton helpButton = new JButton("Help");
	JButton highscoreButton = new JButton("High Scores");
	BlokusLoginPanel theLoginPanel = new BlokusLoginPanel();
	JButton connectButton = new JButton ("Connect!");
	JButton startButton = new JButton("Start!");
	JTextField usernameField = new JTextField();
	JTextField ipField = new JTextField();
	JTextField portField = new JTextField();
	JRadioButton serverRButton = new JRadioButton("Server");
	JRadioButton clientRButton = new JRadioButton("Client");
	ButtonGroup buttonGroup = new ButtonGroup();
	BlokusHelpPanel theHelpPanel = new BlokusHelpPanel();
	JButton backButton = new JButton("Return to menu");
	JTextField helpTextField = new JTextField();
	JTextArea helpChatArea = new JTextArea();
	JScrollPane helpChatScroll = new JScrollPane(helpChatArea);
	BlokusHighSPanel theHighScorePanel = new BlokusHighSPanel();
	BlokusGameOverPanel theGameOverPanel = new BlokusGameOverPanel();
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			//timer
			theGamePanel.repaint();
			theHelpPanel.repaint();
			theLoginPanel.repaint();
			//refresh skip button
			if(theGamePanel.intTurn == 1){
				skipButton.setLocation(250, 0);
			}else if(theGamePanel.intTurn == 2){
				skipButton.setLocation(1180, 0);		
			}else if(theGamePanel.intTurn == 3){
				skipButton.setLocation(1180, 680);
			}else if(theGamePanel.intTurn == 4){
				skipButton.setLocation(250, 680);
			}
		}else if(evt.getSource() == quitButton){
			//quit
			System.exit(0);
		}else if(evt.getSource() == helpButton){
			//Help Panel Visible
			theMenuPanel.setVisible(false);
			theHelpPanel.add(backButton);
			theHelpPanel.setVisible(true);
			theFrame.setContentPane(theHelpPanel);	
			theHelpPanel.requestFocusInWindow();
			
		}else if(evt.getSource() == highscoreButton){
			//High Score Panel Visible
			theMenuPanel.setVisible(false);
			theHighScorePanel.add(backButton);
			theHighScorePanel.setVisible(true);
			theFrame.setContentPane(theHighScorePanel);
		}else if(evt.getSource() == loginButton){
			//Login Panel Visible
			theMenuPanel.setVisible(false);
			theLoginPanel.add(backButton);
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
			
			//theGamePanel.strUsername value
			theGamePanel.strUsername = usernameField.getText();
			if(theGamePanel.strUsername.length() != 0){
				boolUsername = true;
			}
			
			System.out.println("port: " + boolPort + " " + intPort);
			System.out.println("ip: " + boolIp + " " + strIp);
			System.out.println("name: " + boolUsername + " " + theGamePanel.strUsername);
			
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
						ssm.sendText(theGamePanel.strUsername);
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
						theLoginPanel.strName[0] = theGamePanel.strUsername;
						theGamePanel.strName[0] = theGamePanel.strUsername;
						theLoginPanel.boolisServer = true;
						theLoginPanel.strAddress = ssm.getMyAddress();
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
				ssm.sendText("chat, CHAT | "+dtf.format(localTime)+ " " + theGamePanel.strUsername + ": " + sendTextField.getText());
				chatArea.append(" CHAT | "+dtf.format(localTime)+ " " + theGamePanel.strUsername + ": " + sendTextField.getText() + "\n");
				sendTextField.setText("");
				
				//Focus cycle
				sendTextField.setFocusable(false);
				sendTextField.setFocusable(true);
			}
		}else if(evt.getSource() == ssm){
			//Split command
			//chat,hello
			//turn,name,turn(1 or 2), (string,string,int)
			String strRecieve = ssm.readText();
			strMsgSplit = strRecieve.split(",");
			System.out.println("raw text" + strRecieve);
			if(theGamePanel.boolStartGame == true){
				//Recieve Chat Text
				if(strMsgSplit[0].equals("chat")){
					chatArea.append(strMsgSplit[1] + "\n");
					chatArea.setCaretPosition(chatArea.getDocument().getLength());
				}
				
			//SERVER SIDE MESSAGES LOGIN
			}else if(serverRButton.isSelected() && theGamePanel.boolStartGame == false){
				//Recieve name from client to server
				theLoginPanel.strName[intConnected] = ssm.readText();
				theGamePanel.strName[intConnected] = ssm.readText();
				intConnected++;
				System.out.println("playerCount: " + intConnected);
				if(intConnected == 2){
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
			//SERVER SIDE MESSAGES GAME
			if(serverRButton.isSelected() && theGamePanel.boolStartGame == true){
				if(theGamePanel.intTurn == 1){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",1," + theGamePanel.strName[1] + ",0," + theGamePanel.strName[2] + ",0," + theGamePanel.strName[3] + ",0");
					intServerTurn = 1;
				}else if(theGamePanel.intTurn == 2){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",0," + theGamePanel.strName[1] + ",1," + theGamePanel.strName[2] + ",0," + theGamePanel.strName[3] + ",0");
					intServerTurn = 0;
				}else if(theGamePanel.intTurn == 3){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",0," + theGamePanel.strName[1] + ",0," + theGamePanel.strName[2] + ",1," + theGamePanel.strName[3] + ",0");
					intServerTurn = 0;
				}else if(theGamePanel.intTurn == 4){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",0," + theGamePanel.strName[1] + ",0," + theGamePanel.strName[2] + ",0," + theGamePanel.strName[3] + ",1");	
					intServerTurn = 0;
				}
				System.out.println("TURNS SENT rbutton");
			}
			//CLIENT SIDE MESSAGES LOGIN
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
			if(clientRButton.isSelected()){
				//Client checking turns
				if(strMsgSplit[0].equals("turn")){
					System.out.println("TURN Recieved");
					//Check
					if(strMsgSplit[1].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[1] + " " + theGamePanel.strName[0]);
						if(strMsgSplit[2].equals("1")){
							System.out.println("turn Read 1");
							theGamePanel.boolYourTurn = true;
						}else if(strMsgSplit[2].equals("0")){
							System.out.println("turn Read 0");
							theGamePanel.boolYourTurn = false;
						}
					}
					if(strMsgSplit[3].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[3] + " " + theGamePanel.strName[1]);
						if(strMsgSplit[4].equals("1")){
							System.out.println("turn Read 1");
							theGamePanel.boolYourTurn = true;
						}else if(strMsgSplit[4].equals("0")){
							System.out.println("turn Read 0");
							theGamePanel.boolYourTurn = false;
						}
					}
					if(strMsgSplit[5].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[5] + " " + theGamePanel.strName[2]);
						if(strMsgSplit[6].equals("1")){
							System.out.println("turn Read 1");
							theGamePanel.boolYourTurn = true;
						}else if(strMsgSplit[6].equals("0")){
							System.out.println("turn Read 0");
							theGamePanel.boolYourTurn = false;
						}
					}
					if(strMsgSplit[7].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[7] + " " + theGamePanel.strName[3]);
						if(strMsgSplit[8].equals("1")){
							System.out.println("turn Read 1");
							theGamePanel.boolYourTurn = true;
						}else if(strMsgSplit[8].equals("0")){
							System.out.println("turn Read 0");
							theGamePanel.boolYourTurn = false;
						}
					}
				}
			}
			if(serverRButton.isSelected()){
				//server checking turns
				if(intServerTurn == 1){
					System.out.println("turn Read 1");
					theGamePanel.boolYourTurn = true;
				}else if(intServerTurn == 0){
					System.out.println("turn Read 0");
					theGamePanel.boolYourTurn = false;
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
			//Initial turn send
			if(serverRButton.isSelected() && theGamePanel.boolStartGame == true){
				if(theGamePanel.intTurn == 1){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",1," + theGamePanel.strName[1] + ",0," + theGamePanel.strName[2] + ",0," + theGamePanel.strName[3] + ",0");
					intServerTurn = 1;
				}else if(theGamePanel.intTurn == 2){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",0," + theGamePanel.strName[1] + ",1," + theGamePanel.strName[2] + ",0," + theGamePanel.strName[3] + ",0");
					intServerTurn = 0;
				}else if(theGamePanel.intTurn == 3){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",0," + theGamePanel.strName[1] + ",0," + theGamePanel.strName[2] + ",1," + theGamePanel.strName[3] + ",0");
					intServerTurn = 0;
				}else if(theGamePanel.intTurn == 4){
					ssm.sendText("turn," + theGamePanel.strName[0] + ",0," + theGamePanel.strName[1] + ",0," + theGamePanel.strName[2] + ",0," + theGamePanel.strName[3] + ",1");	
					intServerTurn = 0;
				}	
				System.out.println("INITIAL TURNS SENT");
				
				chatArea.append(" GAMEPLAY | Turn: P" +theGamePanel.intTurn+ " "+theGamePanel.strUsername + ", Turn #" + theGamePanel.intTurn);
			}
		}else if(evt.getSource() ==  backButton){
			theHelpPanel.setVisible(false);
			theFrame.setContentPane(theMenuPanel);		
			theMenuPanel.setVisible(true);
		}else if(evt.getSource() == helpTextField){
			//get time
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalTime localTime = LocalTime.now();
			
			//Send with name + time
			helpChatArea.append(dtf.format(localTime)+ " You: " + helpTextField.getText() + "\n");
			helpTextField.setText("");
			
			//Focus cycle
			helpTextField.setFocusable(false);
			helpTextField.setFocusable(true);
					
		}else if(evt.getSource() == skipButton){
			//When skip is equal to 4 then end game
			if(theGamePanel.intTurn < 4){
				theGamePanel.intTurn++;
			}else{
				theGamePanel.intTurn = 1;
			}
			theGamePanel.intSkip++;
			System.out.println("Turn: " + theGamePanel.intTurn);
			if(theGamePanel.intSkip >= 4){
				theGamePanel.setVisible(false);
				theGameOverPanel.add(backButton);
				theGameOverPanel.setVisible(true);
				theFrame.setContentPane(theGameOverPanel);
			}
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
			theGamePanel.intPickX = evt.getX();
			theGamePanel.intPickY = evt.getY();
			theGamePanel.sidePieceCode = true;
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
		theHelpPanel.intDropX = evt.getX();
		theHelpPanel.intDropY = evt.getY();
		System.out.println("DROP | x: "+theHelpPanel.intDropX+" | y: "+theHelpPanel.intDropY);
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
		if(evt.getSource()==theGamePanel){
			theGamePanel.mouseX = evt.getX();
			theGamePanel.mouseY = evt.getY();
			theGamePanel.boolDragAndDrop = true;
			System.out.println("START");
			theGamePanel.repaint();
			theFrame.requestFocus();
			
		}
		theHelpPanel.mouseX = evt.getX();
		theHelpPanel.mouseY = evt.getY();
		System.out.println("mouse | x: "+theHelpPanel.mouseX+" | y: "+theHelpPanel.mouseY);
	}
	public void keyReleased(KeyEvent evt){
	
	}
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyCode() == 32){
			theGamePanel.boolRotate = true;
			theHelpPanel.boolRotate = true;
			System.out.println("ROTATE");
		}
	}
	public void keyTyped(KeyEvent evt){
		
	}
	
	
	//Constuctor
	public Test(){
		//Game panel
		theGamePanel.setLayout(null);
		theGamePanel.setPreferredSize(new Dimension(1280, 720));
		theGamePanel.setVisible(false);
		//theGamePanel.setVisible(true);
		theGamePanel.addMouseMotionListener(this);
		theGamePanel.addMouseListener(this);
		theFrame.addKeyListener(this);
		
		//Menu Panel
		theMenuPanel.setLayout(null);
		theMenuPanel.setPreferredSize(new Dimension(1280, 720));
		theMenuPanel.setVisible(true);
		//theMenuPanel.setVisible(false);
		
		//Login Panel
		theLoginPanel.setLayout(null);
		theLoginPanel.setPreferredSize(new Dimension(1280, 720));
		theLoginPanel.setVisible(false);
		
		//Help Panel
		theHelpPanel.setLayout(null);
		theHelpPanel.setPreferredSize(new Dimension(1280, 720));
		theHelpPanel.addMouseMotionListener(this);
		theHelpPanel.addMouseListener(this);
		theHelpPanel.addKeyListener(this);
		theHelpPanel.setVisible(false);
		
		//HighScore Panel
		theHighScorePanel.setLayout(null);
		theHighScorePanel.setPreferredSize(new Dimension(1280, 720));
		theHighScorePanel.setVisible(false);
		
		//GameOver Panel
		theGameOverPanel.setLayout(null);
		theGameOverPanel.setPreferredSize(new Dimension(1280, 720));
		theGameOverPanel.setVisible(false);
		
		//theFrame.setContentPane(theGamePanel);
		theFrame.setContentPane(theMenuPanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setResizable(false);
		
		//Login Button Menu
		loginButton.setSize(240,50);
		loginButton.setLocation(200, 510);
		loginButton.setFont(loginButton.getFont().deriveFont(Font.BOLD, 24));
		loginButton.addActionListener(this);
		theMenuPanel.add(loginButton);
		
		//High Score Button Menu
		highscoreButton.setSize(240,50);
		highscoreButton.setLocation(200, 580);
		highscoreButton.addActionListener(this);
		theMenuPanel.add(highscoreButton);
		
		//Quit Button Menu
		quitButton.setSize(240, 50);
		quitButton.setLocation(200, 635);
		quitButton.addActionListener(this);
		theMenuPanel.add(quitButton);
		
		//Help Button Menu
		helpButton.setSize(300,50);
		helpButton.setLocation(950,50);
		helpButton.addActionListener(this);
		theMenuPanel.add(helpButton);
		
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
		
		//Back Button Help/Highscore
		backButton.setSize(200,55);
		backButton.setLocation(950,600);
		backButton.addActionListener(this);
		//add to other panels when panels is selected...
		
		//Skip Button Game
		skipButton.setSize(100,40);
		skipButton.addActionListener(this);
		theGamePanel.add(skipButton);
		
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
		
		//Help Chat Box
		helpChatScroll.setSize(350,110);
		helpChatScroll.setLocation(150, 500);
		helpChatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		helpChatArea.setLineWrap(true);
		helpChatArea.setEditable(false);
		theHelpPanel.add(helpChatScroll); 
		
		//Help Chat TextField
		helpTextField.setSize(350, 24);
		helpTextField.setLocation(150, 610);
		helpTextField.setForeground(Color.BLACK);
		helpTextField.setBackground(new Color(157, 156, 154));
		helpTextField.addActionListener(this);
		theHelpPanel.add(helpTextField);
		
		//Pack Frame
		theFrame.pack();
		theFrame.setVisible(true);
		theTimer.start();
		theFrame.requestFocus();

	}

	//Main Program
	public static void main(String[] args){
		Test Block = new Test();
	}
}
