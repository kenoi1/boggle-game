package boggle;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.LayoutManager;
import java.awt.GraphicsEnvironment;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * 
 * @author Harry, Paarth, Derek, Alex, Terrence
 *
 */
public class Main_GUI {
	int seconds = 15;
	int counter = 0;
	static boolean gameTurn = true; //true = player 1's turn, false = player 2's turn
	//settings button (used in action listener)
	private static String textInput = "no input yet";

	MusicPlayer music = new MusicPlayer("boggle2007.wav");
	MusicPlayer wow = new MusicPlayer("wow.wav");
	MusicPlayer click = new MusicPlayer("click.wav");
	MusicPlayer wrong = new MusicPlayer("wrong.wav");
	JButton button;
	static JPanel game;
	static JButton shuffleButton = shuffleBoard();
	static JFrame frame;
	static Player Player1 = new Player();
	static Player Player2 = new Player();
	static Player currentPlayer = new Player();
	static int timeouts = 0;
	Main_GUI(String p1Name, String p2Name, String difficultyAI, int tournamentPoints) {
		music.play();
		
		Player1.setName(p1Name);
		Player2.setName(p2Name);
		System.out.println("Player1 name:" + Player1.getName());
		System.out.println("Player2 name:" + Player2.getName());
		assignPlayer();
		
		//new font!!!!!!!
		Font alpha = null;
		try {
			alpha = Font.createFont(Font.TRUETYPE_FONT, new File("alph.ttf")).deriveFont(50f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("alph.ttf")));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create frame
		frame = new JFrame("Boggle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 1000);
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(242,242,255));
		//boxes is an insanciation of GUIFunct. all the functions for boxes are 
		//in GUIFunct, and can be reused whenever by the same instance
		GUIFunct boxes = new GUIFunct();
		
		Timer time = new Timer();
		JLabel timer = new JLabel();
		
		int TournamentScore = tournamentPoints;
		System.out.println(TournamentScore);
		TimerTask task = new TimerTask()
		{
			int timeNum = 0;
			@Override
			public void run() {
				if (seconds >= 0) {
					timer.setText(seconds + " SECONDS");
					//create players' scoreboard
					frame.add(boxes.getScoreboard(Player1.getName(), Player1.getPoints(), 40));
					frame.add(boxes.getScoreboard(Player2.getName(), Player2.getPoints(), 890));
					counter++;
					if(counter%10 == 0) {
						seconds--;
					}
					//add/update wordBank
					JScrollPane scrollPane = wordBank();
					frame.add(scrollPane);
					//create tournament score
					frame.add(boxes.getTournamentScore(TournamentScore));
					//player turn 
					assignPlayer();
					String playerTurn = currentPlayer.getName();
					frame.add(boxes.getPlayerTurn(playerTurn));
				}
				
				else {
					timer.setForeground(Color.blue); // set color of text
					timer.setText("TIMES UP!"); // set text of label
					timer.setFont(new Font("Calibri",Font.BOLD,100)); // set font of text	
					gameTurn = !gameTurn;
					timeouts++;
					seconds = 15;
					assignPlayer();
					
            		
				}
				
				////generate time that the AI will find the word based on difficulty when AIs turn
				if(difficultyAI != null && gameTurn == true) {
					
					if(difficultyAI.equalsIgnoreCase("Easy")) {
						timeNum = (int) (Math.random() * 20 + -10);// generate number from -10 to 10
						
		
					}
					
					else if(difficultyAI.equalsIgnoreCase("Medium")){
						timeNum = (int) (Math.random() * 15 + -5 );//generate number from -5 to 10
					}
					
					else {
						timeNum = (int) (Math.random() * 15 + -1  );//generate number from -1 to 14
		
					}
				}
				
				//run AI at ais turn
				if(seconds == timeNum) {
					if(difficultyAI != null && gameTurn == false) {
            			try {
     
            				runAI(difficultyAI);	
            				
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							System.out.println("hi");
							e1.printStackTrace();
						}
            			
            			gameTurn = !gameTurn;
            			seconds = 15;
            		}
				}

				if (timeouts >= 2) {
					shuffleButton.setVisible(true);
				}
				else {
					shuffleButton.setVisible(false);
				}
				
				timer.setForeground(Color.red); // set color of text
				timer.setFont(new Font("Arial",Font.BOLD,80)); // set font of text	
				timer.setBackground(new Color(242,242,255));
				timer.setOpaque(true);
				
				if (Player1.getPoints() >= tournamentPoints) {
					time.cancel();
					music.stop();
					GUIFunct.winScreen(Player1.getName());
					wow.play();
//					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} else if (Player2.getPoints() >= tournamentPoints){
					time.cancel();
					music.stop();
					GUIFunct.winScreen(Player2.getName());
					wow.play();
//					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					
				}
			}
			
		};
		time.schedule(task, 0, 100);
		
		//create timer at top
		
		timer.setForeground(Color.red); // set color of text
		timer.setFont(new Font("Arial",Font.BOLD,80)); // set font of text	
		timer.setBackground(new Color(242,242,255));
		timer.setOpaque(true);
		timer.setBounds(300,20,570,100);
		timer.setVerticalAlignment(JLabel.CENTER);
		timer.setHorizontalAlignment(JLabel.CENTER);
				
		
		game = gameboard();
		game.setBounds(340, 200, 500, 500);
		// Create game board
		JTextField textField = wordInputBox();
		textField.setBounds(340, 700, 500, 100);
		
		//settings button
		button = new JButton("its working :)");
		button.setBounds(100,160,200,40);
		button.setFocusable(false);

		// shake up the board button
		//ADD EVERYTHING TO FRAME.
		frame.setLayout(null);
		frame.add(timer);
		frame.add(game);
		frame.add(textField);
		frame.add(shuffleButton);
		frame.setVisible(true);
		
		textField.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	assignPlayer(); // checks whose turn it is, currentPlayer
	            	System.out.println(currentPlayer.getName() + "'s turn.");
	            	System.out.println("Enter was pressed.");
	            	textInput = textField.getText();
	            	System.out.println();
	            	
	            	if (textInput.length() == 0) {
	            		System.out.println("No input. Please enter a word.");
	            		wrong.play();
	            	} else if (gameTurn == false && difficultyAI != null) { 
	            		System.out.println("It is currently the computer's turn"); 
	            		wrong.play();
	            	} else {
	            		System.out.println("The input of the TextField: " + textInput);
		            	textField.setText("");
		            	
//		            	System.out.println(GameBoard.validateWord(textInput));
		            	boolean isOnBoard;
		            	if (GameBoard.validateWord(textInput) == null) {
		            		isOnBoard = false;
		            	} else {
		            		isOnBoard = true;
		            	}
		            	textInput = textInput.toLowerCase();
		            	boolean isValidWord = GameBoard.binaryWordSearch(GameBoard.getDictionary(), textInput);
		            	if (isValidWord == true && isOnBoard == true && currentPlayer.checkIfGuessed(textInput) == false && textInput.length() > 2) {
		            		int score = calculateScore(textInput.length());
		            		System.out.println("'" + textInput + "' is a valid word! +" + score + " points");
		            		currentPlayer.addGuessedWord(textInput);
		            		currentPlayer.setPoints(currentPlayer.getPoints() + score);
		            		updatePlayer();
		            		gameTurn = !gameTurn;
		            		seconds = 15;
		            		click.play();
		            	
		            	} else {
		            		System.out.println("'" + textInput + "' is not a valid word.");
		            		wrong.play();
		            	}
		            	System.out.println();
	            	}
	            	
	            	System.out.println("Player 1, " + Player1.getName() + " points: " + Player1.getPoints());
	            	System.out.println("Player 2  " + Player2.getName() + " points: " + Player2.getPoints());
	            }
	        }

	    });
	}
	
	/**
	 * Method returns score of word
	 * @param length
	 * @return
	 */
	public static int calculateScore(int length) {
		if (length == 3 || length == 4) {
			return 1;
		} else if (length == 5) {
			return 2;
		} else if (length == 6) {
			return 3;
		} else if (length == 7) {
			return 5;
		} else {
			return 11;
		}
	}
	public static JPanel gameboard() {
		
		//RECREATE FONT 
		Font alpha = null;
		try {
			alpha = Font.createFont(Font.TRUETYPE_FONT, new File("alph.ttf")).deriveFont(80f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("alph.ttf")));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create panel which dice appear on 
		
		JPanel dicePanel = new JPanel();
		//dicePanel.setPreferredSize(new Dimension(500, 500));
		dicePanel.setBounds(0, 0, 500, 500);
		//dicePanel.setBackground(Color.lightGray);
		
		FlowLayout dicePanelLayout = new FlowLayout(FlowLayout.CENTER, 10, 10);
		dicePanelLayout.setHgap(0);
		dicePanelLayout.setVgap(0);
		dicePanel.setLayout(dicePanelLayout);

		Dice[][] diceGrid = GameBoard.getDiceGrid();
		// for every dice, create a jpanel with a jlabel inside
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// Create a panel representing the dice graphic
				JPanel diceGraphic = new JPanel();
				diceGraphic.setPreferredSize(new Dimension(100, 100));
				diceGraphic.setBackground(Color.lightGray);
				diceGraphic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				// create a label showing the letter
				String text = Character.toString(diceGrid[i][j].getLetter());
				JLabel diceLetter = new JLabel(text, SwingConstants.CENTER);
				diceLetter.setFont(alpha);
				diceLetter.setPreferredSize(new Dimension(100, 100));
				//diceLetter.setIcon(circle);
				//diceLetter.setText(Character.toString(diceGrid[i][j].getLetter()));
				//button.addActionListener(e -> System.out.print(button.getText()));
		
				diceGraphic.add(diceLetter);
				dicePanel.add(diceGraphic);
			}
		}
		
		return dicePanel;			
	}
	
	/**
	 * method that gets difficulty of AI finds words and updates score
	 * @param difficultyAI
	 * @throws Exception
	*/
	public static void runAI(String difficultyAI) throws Exception {
		//initialize variables
		int number;
		int sleepNum;
		String word = null;
		
		System.out.println(currentPlayer.getName() + "'s turn.");
    	System.out.println("Enter was pressed.");
    	System.out.println();
		
		while(true) {
			//if, else if and else statements that generates numbers and get word of that length
			if(difficultyAI.equalsIgnoreCase("Easy")) {
			

				word = Computerplayer.guesser(3);//only generate 3 letter word
				
				
			}
			
			else if(difficultyAI.equalsIgnoreCase("Medium")){
				number = (int) (Math.random() * 3 + 3 );//get numbers 3,4,5
				word = Computerplayer.guesser(number);
			}
			
			else {
				number = (int) (Math.random() * 2 + 4 );//get numbers 4,5
				word = Computerplayer.guesser(number);
				
			}
			
			
			if(currentPlayer.checkIfGuessed(word) == false) {
				break;
			}
			
			
			
			
		}
		
		//update scoreboard 
		int score = calculateScore(word.length());
		currentPlayer.addGuessedWord(word);
		currentPlayer.setPoints(currentPlayer.getPoints() + score);
		updatePlayer();
		
		
		
		
	}
	
	public static int AiGo(String difficultyAI, boolean gameTurn) {
		int timeNum = 0;
		if(difficultyAI != null && gameTurn == false) {
			if(difficultyAI.equalsIgnoreCase("Easy")) {
				timeNum = (int) (Math.random() * 20 + -10);
				System.out.println(timeNum);

			}
			
			else if(difficultyAI.equalsIgnoreCase("Medium")){
				timeNum = (int) (Math.random() * 15 + -5 );
			}
			
			else {
				timeNum = (int) (Math.random() * 15 + -1  );

			}
		}
		
		return timeNum;
	}
	
	public static JButton shuffleBoard() {

		JButton button = new JButton(); // instantiate button
		
		button.setBounds(40, 100, 250, 100); // set size and location of button
		button.setFocusable(false);
		
		// text setup
		button.setText("Shake board!");
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setFont(new Font("Arial", Font.BOLD, 30));
		
		button.addActionListener(e -> {
			GameBoard.fillDiceGrid();
			frame.remove(game);
			game = gameboard();
			game.setBounds(340, 200, 500, 500);
			frame.add(game);
			timeouts = 0;
			//shuffleButton.setVisible(false);
		});
		
		return button;
	}
	
	public static JTextField wordInputBox() {
		JTextField textField = new JTextField(16);
		textField.setFont(new Font("Arial",Font.BOLD,40));
		textField.setHorizontalAlignment(JTextField.CENTER);
		return textField;
	}
	public static void assignPlayer() {
		if (gameTurn == true) {
			currentPlayer = Player1;
		} else {
			currentPlayer = Player2;
		}
	}
	public static void updatePlayer() {
		if (gameTurn == true) {
			Player1 = currentPlayer;
		} else {
			Player2 = currentPlayer;
		}
	}
	
	/**
	 * metod that adds a wordbank to frame
	 * @return
	 */
	private static JScrollPane wordBank() {
		//import arraylist of words guessed
		String word = "";
		ArrayList<String> wordList = Player1.getGuessedWords();
		ArrayList<String> wordList1 = Player2.getGuessedWords();
		
		//instantiate textArea and scrollpane
        JTextArea textArea = new JTextArea(100,100);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        // Set the preferred size of the JScrollPane
        scrollPane.setPreferredSize(new Dimension(100, 100));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setLayout(null);
        scrollPane.setBounds(870,500,300,300);
        
        textArea.setLayout(null);
        textArea.setOpaque(true);
        textArea.setBounds(900, 510, 280, 280);
        
        //add words to JscrollPane from arraylists
        for(int i =0; i < wordList.size(); i++  ) {
        
            word = word + "\n" + "   " + wordList.get(i).toLowerCase();
        }
        for(int i =0; i < wordList1.size(); i++  ) {
            
            word = word + "\n" + "   " + wordList1.get(i).toLowerCase();
        }
        
    	textArea.setText(word);
        

        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 20));

        return scrollPane;
    }

	
}