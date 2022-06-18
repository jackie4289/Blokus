// Blokus Main Program
// Authors: Lucas Moran, Kelsie Fung, Jackie Lin
// ICS 4U1
// June 16, 2022
// Version 1.231

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
	int intServerTurn = 0;
	int intRow = 0;
	int intCol = 0;
	String strIp;
	String strRecieve;
	String strMsgSplit[];
	String strName[] = new String[3];
	boolean boolPort = false;
	boolean boolIp = false;
	boolean boolUsername = false;
	boolean boolTurn = false;

	//J Properties
	BlokusPanel theGamePanel = new BlokusPanel();
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
			if(theGamePanel.boolStartGame == true){
				//detect 
				if(theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn){
					//theGamePanel.requestFocusInWindow();
					boolTurn = true;
					theGamePanel.boolHideSkip = false;
				}else if(theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn-1 || theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn+3){
					if(boolTurn == true){
						ssm.sendText("turnAdd");
						System.out.println("next turn via placement");
						
						//test
						for(intRow = 2; intRow <22; intRow++){
							for(intCol = 2; intCol <22; intCol++){
								ssm.sendText("Board," + theGamePanel.strBoard[intRow][intCol] + "," + intRow + "," + intCol);
							}
						}
						
						for(intRow = 0;intRow < 15;intRow++){
							for(intCol = 0;intCol < 16;intCol++){
								if(theGamePanel.intPlayerTurnNumber == 1){
									ssm.sendText("SidePieces," + theGamePanel.intPlayerTurnNumber + "," + theGamePanel.strP1SidePieces[intRow][intCol] + "," + intRow + "," + intCol);
								}else if(theGamePanel.intPlayerTurnNumber == 2){
									ssm.sendText("SidePieces," + theGamePanel.intPlayerTurnNumber + "," + theGamePanel.strP2SidePieces[intRow][intCol] + "," + intRow + "," + intCol);
								}else if(theGamePanel.intPlayerTurnNumber == 3){
									ssm.sendText("SidePieces," + theGamePanel.intPlayerTurnNumber + "," + theGamePanel.strP3SidePieces[intRow][intCol] + "," + intRow + "," + intCol);
								}else if(theGamePanel.intPlayerTurnNumber == 4){
									ssm.sendText("SidePieces," + theGamePanel.intPlayerTurnNumber + "," + theGamePanel.strP4SidePieces[intRow][intCol] + "," + intRow + "," + intCol);
								}
							}
						}
						
						boolTurn = false;
						theGamePanel.boolHideSkip = true;
					}
				}
				//refresh skip button
				if(theGamePanel.boolHideSkip == false){
					skipButton.setVisible(true);
					if(theGamePanel.intTurn == 1){
						skipButton.setLocation(250, 0);
					}else if(theGamePanel.intTurn == 2){
						skipButton.setLocation(1180, 0);		
					}else if(theGamePanel.intTurn == 3){
						skipButton.setLocation(1180, 680);
					}else if(theGamePanel.intTurn == 4){
						skipButton.setLocation(250, 680);
					}
				}else{
					skipButton.setVisible(false);
				}
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
						System.out.println("Username sent to server!");
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
						theGamePanel.intPlayerTurnNumber = 1;
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
				ssm.sendText("chat,"+dtf.format(localTime)+ " " + theGamePanel.strUsername + ": " + sendTextField.getText());
				chatArea.append(dtf.format(localTime)+ " " + theGamePanel.strUsername + ": " + sendTextField.getText() + "\n");
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
				//next turns
				if(strMsgSplit[0].equals("turnAdd")){
					System.out.println("recieved next turn signal");
					theGamePanel.intTurn++;
					if(theGamePanel.intTurn > 4){
						theGamePanel.intTurn = 1;
					}
					theGamePanel.boolNewTurn = true;
					theGamePanel.checkPieces = true;
				}
				if(strMsgSplit[0].equals("Board")){
					theGamePanel.strBoard[Integer.parseInt(strMsgSplit[2])][Integer.parseInt(strMsgSplit[3])] = strMsgSplit[1];
				}
				if(strMsgSplit[0].equals("SidePieces")){
					if(strMsgSplit[1].equals("1")){
						theGamePanel.strP1SidePieces[Integer.parseInt(strMsgSplit[3])][Integer.parseInt(strMsgSplit[4])] = strMsgSplit[2];
					}else if(strMsgSplit[1].equals("2")){
						theGamePanel.strP2SidePieces[Integer.parseInt(strMsgSplit[3])][Integer.parseInt(strMsgSplit[4])] = strMsgSplit[2];
					}else if(strMsgSplit[1].equals("3")){
						theGamePanel.strP3SidePieces[Integer.parseInt(strMsgSplit[3])][Integer.parseInt(strMsgSplit[4])] = strMsgSplit[2];
					}else if(strMsgSplit[1].equals("4")){
						theGamePanel.strP4SidePieces[Integer.parseInt(strMsgSplit[3])][Integer.parseInt(strMsgSplit[4])] = strMsgSplit[2];
					}
					
				}
				
				
			//SERVER SIDE MESSAGES LOGIN
			}else if(serverRButton.isSelected() && theGamePanel.boolStartGame == false){
				//Recieve name from client to server
				theLoginPanel.strName[intConnected] = ssm.readText();
				theGamePanel.strName[intConnected] = ssm.readText();
				intConnected++;
				System.out.println("playerCount: " + intConnected);
				if(intConnected == 4){
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
				if(strMsgSplit[0].equals("inital")){
					System.out.println("TURN Recieved");
					//Check
					if(strMsgSplit[3].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[3] + " " + theGamePanel.strName[1]);
						if(strMsgSplit[4].equals("2")){
							theGamePanel.intPlayerTurnNumber = 2;
							System.out.println("Im player 2");
						}
					}else if(strMsgSplit[5].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[5] + " " + theGamePanel.strName[2]);
						if(strMsgSplit[6].equals("3")){
							theGamePanel.intPlayerTurnNumber = 3;
							System.out.println("Im player 3");
						}
					}else if(strMsgSplit[7].equals(theGamePanel.strUsername)){
						System.out.println("Name Read: "+ strMsgSplit[7] + " " + theGamePanel.strName[3]);
						if(strMsgSplit[8].equals("4")){
							theGamePanel.intPlayerTurnNumber = 4;
							System.out.println("Im player 4");
						}
					}
				}
			}
		}else if(evt.getSource() == startButton){
			//Start Game, display game panel
			//server send start signal to client
			if(theGamePanel.boolStartGame == false){
				ssm.sendText("ssmStart,");
				System.out.println("Im player 1");
			}
			theLoginPanel.setVisible(false);
			theGamePanel.setVisible(true);
			theFrame.setContentPane(theGamePanel);
			theGamePanel.boolStartGame = true;
			System.out.println("STARTS");
			//Initial turn send
			if(serverRButton.isSelected() && theGamePanel.boolStartGame == true){
				ssm.sendText("inital," + theGamePanel.strName[0] + ",1," + theGamePanel.strName[1] + ",2," + theGamePanel.strName[2] + ",3," + theGamePanel.strName[3] + ",4");
				System.out.println("INITIAL TURNS SENT");
			}
		}else if(evt.getSource() ==  backButton){
			//Back Button
			theHighScorePanel.setVisible(false);
			theLoginPanel.setVisible(false);
			theGamePanel.setVisible(false);
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
			//turns++
			if(theGamePanel.intTurn < 4){
				theGamePanel.intTurn++;
			}else{
				theGamePanel.intTurn = 1;
			}
			theGamePanel.intSkip++;
			System.out.println("Turn: " + theGamePanel.intTurn);
			/*
			//End Game if skip = 4
			if(theGamePanel.intSkip >= 4){
				theGamePanel.setVisible(false);
				theGameOverPanel.add(backButton);
				theGameOverPanel.setVisible(true);
				theFrame.setContentPane(theGameOverPanel);
			}
			*/
		}
	}
	public void mouseExited(MouseEvent evt){

	}
	public void mouseEntered(MouseEvent evt){

	}
	public void mousePressed(MouseEvent evt){
		if(evt.getSource()==theGamePanel){
			if(theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn){
				theGamePanel.mouseX = evt.getX();
				theGamePanel.mouseY = evt.getY();
				theGamePanel.intPickX = evt.getX();
				theGamePanel.intPickY = evt.getY();
				theGamePanel.sidePieceCode = true;
				System.out.println(theGamePanel.intTurn);
				theGamePanel.repaint();
				theFrame.requestFocus();
			}
		}
	}
	public void mouseReleased(MouseEvent evt){
		if(evt.getSource() == theHelpPanel){
			theHelpPanel.intDropX = evt.getX();
			theHelpPanel.intDropY = evt.getY();
			System.out.println("DROP | x: "+theHelpPanel.intDropX+" | y: "+theHelpPanel.intDropY);
		}else if(evt.getSource() == theGamePanel){
			if(theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn){
				theGamePanel.boolDragAndDrop = false;
				theGamePanel.intDropX = evt.getX();
				theGamePanel.intDropY = evt.getY();
				if(theGamePanel.intPiece > 22){
					theGamePanel.intPiece = 0;
				}
				theGamePanel.boolDropped = true;
				theGamePanel.repaint();
				theFrame.requestFocus();
			}
		}
	}
	public void mouseClicked(MouseEvent evt){
		if(evt.getSource() == theGamePanel){
			System.out.println("TURN: "+ theGamePanel.intTurn);
			System.out.println("TURN#: "+ theGamePanel.intPlayerTurnNumber);
		}
	}
	public void mouseMoved(MouseEvent evt){
		if(theGamePanel.boolStartGame == true){
			if(theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn){
				theGamePanel.mouseX = evt.getX();
				theGamePanel.mouseY = evt.getY();
			}
		}		
	}
	public void mouseDragged(MouseEvent evt){
		if(evt.getSource() == theHelpPanel){
			theHelpPanel.mouseX = evt.getX();
			theHelpPanel.mouseY = evt.getY();
			System.out.println("mouse | x: "+theHelpPanel.mouseX+" | y: "+theHelpPanel.mouseY);
		}else if(evt.getSource()==theGamePanel){
			if(theGamePanel.intPlayerTurnNumber == theGamePanel.intTurn){
				theGamePanel.mouseX = evt.getX();
				theGamePanel.mouseY = evt.getY();
				theGamePanel.boolDragAndDrop = true;
				//System.out.println("START");
				theGamePanel.repaint();
				theFrame.requestFocus();
			}
		}
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
	public Blokus(){
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
		skipButton.setVisible(false);
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
		Blokus Block = new Blokus();
	}
}
