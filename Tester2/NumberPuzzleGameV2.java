package Tester2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author yamilasusta && davidbartolomei
 *
 */
public class NumberPuzzleGameV2 {
	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args){
		Scanner input = new Scanner(System.in); //for text input 
		JFrame frame = new JFrame(); //main frame
		JPanel title1= new JPanel(); //player 1 title
		JPanel title2 = new JPanel(); //player 2 title
		JPanel line = new JPanel(); // line that divides the two grids

		Font font = new Font("calibri", Font.BOLD, 36); //font
		Color bg = new Color(202,0,0); //background color 1
		Color bg2 = new Color(230,0,30); // background color 2
		Color fColor = new Color(240,240,240); //font color

		Icon icon = null;
		/**
		 * to calculate screen size and set the game on the middle of the screen
		 */
		Toolkit tk = Toolkit.getDefaultToolkit(); 

		Dimension screenSize = tk.getScreenSize();

		int screenHeight = (screenSize.height/2)-271;
		int screenWidth = (screenSize.width/2)-500;

		frame.setLocation(screenWidth, screenHeight);

		//settings
		frame.setSize(1000,552);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBackground(bg);
		frame.setUndecorated(true);


		/*
		 * title1size and background
		 */
		title1.setSize(400,60);
		title1.setBackground(bg2);
		/*
		 *title1size and background
		 */
		title2.setSize(400,60);
		title2.setBackground(bg2);
		/*
		 *line size, color 
		 */
		line.setSize(2, 600);
		line.setBackground(fColor);
		line.setLocation(frame.getWidth()/2,frame.getHeight()/200);

		/*
		 * prompt for players names
		 */
		String n1 = JOptionPane.showInputDialog("Player 1: ");
		String n2= JOptionPane.showInputDialog("Player 2: ");	

		/*
		 * adding strings to labels
		 */
		JLabel label1= new JLabel(n1);
		JLabel label2= new JLabel(n2);

		label1.setForeground(fColor);
		label1.setFont(font);

		label2.setForeground(fColor);
		label2.setFont(font);

		title1.add(label1); 
		title1.setLocation(frame.getWidth()/20,frame.getHeight()/28);

		title2.add(label2);
		title2.setLocation(550*frame.getWidth()/1000,frame.getHeight()/28);

		frame.add(line);
		frame.add(title1);
		frame.add(title2);

		int selector = JOptionPane.showConfirmDialog(null, "Auto Generated game?", "Selector", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		/**
		 * auto generated game
		 */
		if(selector == JOptionPane.YES_OPTION){	
			int size = 0; 

			Object[] opts = {"3", "4", "5", "6"};

			String sizeS = (String)JOptionPane.showInputDialog(frame,"Select Board Size: ", "Board", JOptionPane.PLAIN_MESSAGE,icon,opts,"3");
			size = Integer.parseInt(sizeS);

			JOptionPane.showMessageDialog(null, "Player 1: W-A-S-D Keys \nPlayer 2: Arrow Keys");

			Controls controls = new Controls(size);
			frame.addKeyListener(controls);

			frame.add(controls);
			title1.setVisible(true);
			title2.setVisible(true);
			line.setVisible(true);
			frame.setVisible(true);
		}

		/**
		 * manually entered game
		 */

		else if(selector == JOptionPane.NO_OPTION){
			System.out.println("Insert the values starting with the grid size and followed by the appropiate order");
			System.out.println("'Example: 3, 1, 2, 3, 4, 5, 6, 7, 9, 8'\n");
			System.out.print(">>NPG ");
			String[] reader = (input.nextLine().split(", ")); 
			int[] vector = new int[(int) Math.pow(Integer.parseInt(reader[0]), 2)];
			if(Math.sqrt(vector.length) < 3 || Math.sqrt(vector.length) > 6)
			{
				System.out.println("Incorrect value");
				System.exit(0);
			}
			for (int i = 1; i < reader.length; i++) {
				if (reader[i].equals("0")) {
					reader[i] = Integer.toString(vector.length);
				}
				vector[i-1] = Integer.parseInt(reader[i]);
			}
			JOptionPane.showMessageDialog(null, "Player 1: WASD \nPlayer 2: Arrow Keys");
			Controls controls = new Controls(vector);
			frame.addKeyListener(controls);
			frame.add(controls);
			frame.toFront();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			title1.setVisible(true);
			title2.setVisible(true);
			line.setVisible(true);
			frame.setVisible(true);
		} 
		else{
			System.exit(0);
		}
	}
}

