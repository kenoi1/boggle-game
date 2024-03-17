package boggle;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuScreen implements ActionListener {
	JFrame frame = new JFrame();
	JFrame frame2 = new JFrame();
	JButton button = new JButton("Player VS Player");
	JButton button2 = new JButton("Player VS AI");
	JButton instruct = new JButton("Instructions");
	JButton OKbutton = new JButton("OK");
	JLabel title = new JLabel();
	JPanel top = new JPanel();
	MusicPlayer music = new MusicPlayer("boggleTheme.wav");
	MusicPlayer click = new MusicPlayer("click.wav");
	
	String p1Name;
	String p2Name;
	String difficultyAI;
	int tournamentPoints;
	MenuScreen() {
		music.play();
		//create title
		title.setText("Boggle!");
		title.setFont(new Font("Arial", 0, 70));
		top.setBackground(new Color(230,242,255));
		
		//button for instruction
		instruct.setBounds(100,290,200,40);
		instruct.setFocusable(false);
		instruct.addActionListener(this);
		
		//add title to frame
		top.add(title);
		top.setBounds(55,30,300,100);
		
		frame.add(top);
		
		//button for player vs player 
		button.setBounds(100,150,200,40);
		button.setFocusable(false);
		button.addActionListener(this);
		//button for player vs AI
		button2.setBounds(100,220,200,40);
		button2.setFocusable(false);
		button2.addActionListener(this);
		
		//add everything to frame
		frame.getContentPane().setBackground(new Color(242,242,255));
		frame.add(instruct);
		frame.add(button);
		frame.add(button2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		click.play();
		//give option to user for selecting tournament points
		Object[] tournamentOption = {"10", "25", "50"};
		if (e.getSource()==button) {
			//create input dialogs for each player's name
			frame.dispose();
			String response = JOptionPane.showInputDialog("Player 1's name: ");
			p1Name = response;
			click.play();
			String response2 = JOptionPane.showInputDialog("Player 2's name: ");
			p2Name = response2;
			click.play();
			//input dialog for selecting tournament point
			Object tournamentSelection = JOptionPane.showInputDialog(frame, "Select Tournament Point:", "Mode", JOptionPane.PLAIN_MESSAGE, null, tournamentOption, tournamentOption[0]);
			String selectionPoints = tournamentSelection.toString();
			tournamentPoints = Integer.parseInt(selectionPoints);
			click.play();
			Main_GUI window = new Main_GUI(p1Name,p2Name, null, tournamentPoints);
			music.stop();
		}
		if(e.getSource() == button2) {
			click.play();
			//create input dialogs for each player's name
			frame.dispose();
			String response = JOptionPane.showInputDialog("Player 1's name: ");
			p1Name = response;
			click.play();
			//select AI difficulty
			Object[] options = {"EASY", "MEDIUM", "HARD"};
	        //...and passing `frame` instead of `null` as first parameter
	        Object selectionObject = JOptionPane.showInputDialog(frame, "Choose AI difficulty:", "difficulty", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	        String selectionString = selectionObject.toString();
	        click.play();
	        difficultyAI = selectionString;
			Object tournamentSelection = JOptionPane.showInputDialog(frame, "Select Tournament Point:", "Mode", JOptionPane.PLAIN_MESSAGE, null, tournamentOption, tournamentOption[0]);
			String selectionPoints = tournamentSelection.toString();
			click.play();
			tournamentPoints = Integer.parseInt(selectionPoints);
			
	        Main_GUI window = new Main_GUI(p1Name, "Computer", difficultyAI, tournamentPoints);
	        music.stop();
		}
		if(e.getSource() == instruct) {
			//create new frame displaying instructions
			frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame2.setSize(560,490);
			frame2.setLayout(null);
			frame2.setVisible(true);
			
			ImageIcon instPage = new ImageIcon("Instruct.png");
			
			JLabel imgHolder = new JLabel();
			imgHolder.setIcon(instPage);
			imgHolder.setBounds(0,0,560,410);
			imgHolder.setBackground(Color.red);
			
			frame2.add(imgHolder);
			OKbutton.setBounds(170,410,200,40);
			OKbutton.setFocusable(false);
			OKbutton.addActionListener(this);
			frame2.add(OKbutton);
			click.play();
			
			
		}
		if(e.getSource() == OKbutton) {
			click.play();
			//close instruction page if ok is hit
			frame2.dispose();
		}
		
	}

}