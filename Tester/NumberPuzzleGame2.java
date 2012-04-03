package Tester;




import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class NumberPuzzleGame2 {
	/**
	 * @param args
	 */
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		JFrame frame = new JFrame();
		
		 Toolkit tk = Toolkit.getDefaultToolkit(); 
		    Dimension screenSize = tk.getScreenSize();
		    int screenHeight = (screenSize.height/2)-204;
		    int screenWidth = (screenSize.width/2)-215;
		    
	
		    
		frame.setResizable(false);
		frame.setSize(408, 430); // windows size for 400x400
		//frame.setSize(400, 422); // mac size for 400x400
		frame.setLocation(screenWidth, screenHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Icon icon = null;

		//Get name
	
			String name  = JOptionPane.showInputDialog("Welcome to NPG! \nInsert your name: ");

		frame.setTitle("Player 1: "+name);
		
		int selector = JOptionPane.showConfirmDialog(null, "Auto Generated game?", "Selector", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		//Get grid size
		if(selector == JOptionPane.YES_OPTION){	
			int size = 0; 
			
			Object[] opts = {"3", "4", "5", "6"};
				
			String sizeS = (String)JOptionPane.showInputDialog(frame,"Select Board Size: ", "Board", JOptionPane.PLAIN_MESSAGE,icon,opts,"3");
			size = Integer.parseInt(sizeS);
			
			
			JOptionPane.showMessageDialog(null, "Use Arrow Keys for movements");
			Controls controls = new Controls(size);
			frame.addKeyListener(controls);
			frame.add(controls);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
		}
		else if (selector == JOptionPane.NO_OPTION) {
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
			JOptionPane.showMessageDialog(null, "Use Arrow Keys for movements");
			Controls controls = new Controls(vector);
			frame.addKeyListener(controls);
			frame.add(controls);
			
			frame.setVisible(true);
			frame.toFront();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		}
		else {
			System.exit(0);
		}
	}
}
