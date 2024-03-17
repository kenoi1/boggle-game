package boggle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIFunct {
	public GUIFunct(){
	}
	
	//GUI for writing which player's turn to frame
	public JPanel getPlayerTurn(String name) {
		//create instance of object for panels and label
		String turnPlayer = name;
		JPanel tpBox = new JPanel();
		
		JLabel tp1 = new JLabel();
		JLabel tp2 = new JLabel();
		//set text for each label
		tp1.setText(turnPlayer + "'s");
		tp2.setText("Turn!");
		tp1.setFont(new Font("Arial",Font.BOLD,30)); // set font of text	
		tp2.setFont(new Font("Arial",Font.BOLD,50));
		tp2.setForeground(Color.blue);
		//set alignment and color for tpBox, tp1 and tp2
		tp1.setHorizontalAlignment(JLabel.CENTER);
		tp2.setHorizontalAlignment(JLabel.CENTER);
		tpBox.setBounds(25,500,300, 100);
		tpBox.setBackground(new Color(242,242,255));
		tpBox.setLayout(new GridLayout(2,1));
		//add tp1 and tp2 to Tpbox
		tpBox.add(tp1);
		tpBox.add(tp2);
		return tpBox;
	}
	//GUI Function for displaying tournament score
	public JPanel getTournamentScore(int tournamentScore) {
		//create instances 
		JPanel tBox = new JPanel();
		JLabel T1 = new JLabel();
		JLabel T2 = new JLabel();
		JLabel T3 = new JLabel();
		//set text and font
		T1.setText("Tournament");
		T2.setText("Score:");
		T3.setText(tournamentScore + "");
		T1.setFont(new Font("Arial",Font.BOLD,50)); // set font of text	
		T2.setFont(new Font("Arial",Font.BOLD,50));
		T3.setFont(new Font("Arial",Font.BOLD,80));
		//set alignment and color
		T3.setForeground(Color.red);
		T1.setHorizontalAlignment(JLabel.CENTER);
		T2.setHorizontalAlignment(JLabel.CENTER);
		T3.setHorizontalAlignment(JLabel.CENTER);
		tBox.setBounds(875,50,300,200);
		tBox.setBackground(new Color(242,242,255));
		tBox.setLayout(new GridLayout(3,1));
		//add every label to panel
		tBox.add(T1);
		tBox.add(T2);
		tBox.add(T3);
		return tBox;
	}
	//GUI function for displaying score of each player
	public JPanel getScoreboard(String name, int score, int xPos){
		//create instances for labels and panel
		JPanel sBox = new JPanel();
		JLabel S1 = new JLabel();
		JLabel S2 = new JLabel();
		//set text
		S1.setText(name);
		S2.setText(score + "");
		S1.setFont(new Font("Arial",Font.BOLD,50)); // set font of text	
		S2.setFont(new Font("Arial",Font.BOLD,90));
		S2.setForeground(Color.red);
		//set alignment and color
		S1.setHorizontalAlignment(JLabel.CENTER);
		S2.setHorizontalAlignment(JLabel.CENTER);
		
		sBox.setBounds(xPos,300,250,150);
		sBox.setBackground(new Color(242,220,255));
		sBox.setLayout(new GridLayout(2,1));
		//add everything to panel and return it
		sBox.add(S1);
		sBox.add(S2);
		return sBox;
	}
	//GUI Function for displaying which player wins.
	public static void winScreen(String name) {
		System.out.println("hi");
		//creating frame and trophy image
		MusicPlayer music = new MusicPlayer("bogglevictory.wav");
		music.play();
		ImageIcon trophy = new ImageIcon("trophy.png");
		JFrame frame = new JFrame("WINNER WINNER CHICKEN DINNER");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(255,100,0));
		//setting text
		JLabel win = new JLabel();
		win.setText(name + " WINS!!!!!");
		win.setFont(new Font("Arial",Font.BOLD,40));
				
		//alignment
		win.setBounds(0,0,500,500);
		win.setVerticalAlignment(JLabel.CENTER);
		win.setHorizontalAlignment(JLabel.CENTER);
		win.setHorizontalTextPosition(JLabel.CENTER);
		win.setVerticalTextPosition(JLabel.TOP);
		
		//add trophy image
		win.setIcon(trophy);
		//add to frame
		win.setBackground(Color.orange);
		win.setOpaque(true);
		frame.add(win);
		
	}
}

