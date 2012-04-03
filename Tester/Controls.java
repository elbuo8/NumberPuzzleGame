package Tester;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Random;


import javax.swing.JPanel;



public class Controls extends JPanel implements KeyListener, ActionListener{
	/**
	 * Modified addition by Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for user generated game.
	 * Find the dimensions of the matrix, generate the matrix, sort a matrix to be used as result comparator.
	 * Find the black tile.
	 * Create the rectangles with the proper coordinates and sizes.
	 * Add the KeyListener to the frame and draw the game.
	 * @param vector Contains the values to be shown in the game
	 */
	public Controls(int[] vector)
	{
		size = (int) Math.sqrt(vector.length);
		matrix = matrix(vector);
		Arrays.sort(vector);
		result = matrix(vector);
		findZeros();
		createRectangles();
		addKeyListener(this);
		repaint();

	}

	/**
	 * Constructor for randomly generated game.
	 * Set the dimensions of the matrix, generate a vector with the size of the dimensions and fill it 
	 * with numbers from 1 to size^2.
	 * Convert the generated vector into a matrix and store it in result.
	 * Shuffle the vector.
	 * Convert the shuffled vector into a matrix.
	 * Find the black tile in the matrix.
	 * Create the rectangles with the proper specifications.
	 * Add the KeyListener and paint the frame.
	 * @param size Contains the dimensions of the matrix.
	 */
	public Controls(int size) 
	{
		this.size = size;
		int[] vector = new int[size * size]; 
		for(int i = 1; i <= vector.length; i++)
			vector[i-1] = i;
		result = matrix(vector);
		shuffle(vector);
		matrix = matrix(vector);
		findZeros();
		createRectangles();
		addKeyListener(this);
		repaint();
	}

	/**
	 * Searches the matrix for the blank tiles (size^2) and stores it's index (location).
	 */
	public void findZeros() 
	{
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(matrix[i][j] == (size*size))
				{
					indexi = i;
					indexj = j;
				}
			}
		}
	}

	/**
	 * Creates the rectangle matrix that should be drawn.
	 * Using a standard size of the frame, tiles will adjust themselves to fit in the frame.
	 * Location will be determined by their location in the matrix.
	 */
	public void createRectangles() {
		rectangles = new Rectangle[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rectangles[i][j] = new Rectangle( i*(sizex/size),j*(sizey/size), ((sizex/size)), ((sizey/size)));
			}
		}

	}
	/**
	 * According to the size it sets the correct size for the font.
	 */
	public void setFontSize(){
		switch(size){ //set the font size and movement of 1 digit numbers in the grid
		case 6:
			fontSize = 24;
			deltaX = 5;
			deltaX2 = 12;
			break;

		case 5:
			fontSize = 32;
			deltaX = 8;
			deltaX2 = 18;
			break;
		
		case 4:
			fontSize = 38;
			deltaX = 10;
			deltaX2 = 22;
			break;

		case 3:
			fontSize = 46;
			deltaX = 12;
			deltaX2 = 0;
			break;

		}
	}
	
	/**
	 * Paint the rectangle matrix. If the current value is size^2 or the number on movement that tile will be blank.
	 * Else the tile obtains a color and cross checks with the matrix to see if the number is even or odd.
	 * The winner flag checks if the game is over.
	 * The animator flag draws a tile in between the location of the blank tile and its next position.
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		setFontSize();
		Font font = new Font("sansserif", Font.BOLD, fontSize); // creates a new font 
		Font font2 = new Font("sansserif", Font.BOLD, 42); // winner banner font
		Color tileColor = new Color(243,243,243);
		
	
		g2.setFont(font);

		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				boolean paint = true;
				if(matrix[j][i] == (size*size) || matrix[j][i] == number)
					paint = false;
				if(paint){
					g2.setColor(tileColor);
					//g2.setFont(font);
			
					g2.fill(rectangles[i][j]);
					g2.setColor(Color.LIGHT_GRAY);
					g2.draw(rectangles[i][j]);	
					if(matrix[j][i] % 2 == 0)
						g2.setColor(Color.BLUE);
					else
						g2.setColor(Color.RED);

					if(Math.floor(matrix[j][i]/10) >= 1){ //to draw numbers greater than 10
			
						g2.drawString(Integer.toString(matrix[j][i]),(int)rectangles[i][j].getCenterX()-deltaX2,
								(int)rectangles[i][j].getCenterY()+font.getSize()/2);

					}

					else{//to draw numbers of 1 digit
						g2.drawString(Integer.toString(matrix[j][i]),(int)rectangles[i][j].getCenterX()-deltaX,
								(int)rectangles[i][j].getCenterY()+font.getSize()/2);
					}
				}
				else {
					g2.setColor(Color.DARK_GRAY);
					g2.draw(rectangles[i][j]);
					g2.fill(rectangles[i][j]);

				}
			}
		}
		if(winner){
			g2.setColor(Color.DARK_GRAY);
			for(int i = 0; i<size; i++){
				for(int j = 0; j<size; j++){

					if(Math.floor(matrix[j][i]/10) >= 1){ //to draw numbers greater than 10
			
						g2.drawString(Integer.toString(matrix[j][i]),(int)rectangles[i][j].getCenterX()-deltaX2,
								(int)rectangles[i][j].getCenterY()+font.getSize()/2);

					}

					else{//to draw numbers of 1 digit
						g2.drawString(Integer.toString(matrix[j][i]),(int)rectangles[i][j].getCenterX()-deltaX,
								(int)rectangles[i][j].getCenterY()+font.getSize()/2);
					}
				}
			}
			g2.setFont(font2);
			g2.setColor(Color.BLUE);
			g2.drawString("Winner winner!!!", 10, 140);
			g2.setColor(Color.RED);
			g2.drawString("chicken dinner!!", 10, 280); // winner award
		}	
			
		if(animator){ // to draw the transition rectangle
			g2.setColor(tileColor);
			g2.fill(tempRectangle);
			g2.setColor(Color.LIGHT_GRAY);
			g2.draw(tempRectangle);
			g2.setFont(font);
			if(number % 2 == 0)
				g2.setColor(Color.BLUE);
			else
				g2.setColor(Color.RED);

			if(Math.floor(number/10.0) >= 1) //#s with two digits
				g2.drawString(Integer.toString(number),(int)tempRectangle.getCenterX()-deltaX2,
						(int)tempRectangle.getCenterY()+font.getSize()/2);

			else{ //#s with one digit
				g2.drawString(Integer.toString(number),(int)tempRectangle.getCenterX()-deltaX, 
						(int)tempRectangle.getCenterY()+font.getSize()/2);}
		}
		number = 0;
		if(matrix[size-1][size-1] == (int)Math.pow(size, 2)) {
			System.out.println("I work bro");
			verify();
		}
	}


	private int[][] result; // Comparator matrix
	private int[][] matrix;	// Game matrix
	private int indexi; // Blank tile location
	private int indexj; // Blank tile location
	private int size; // Dimensions of the matrix
	public Rectangle[][] rectangles; // Rectangle matrix
	public static final int sizex = 400; // Size of frame
	public static final int sizey = 400; // Size of frame
	private boolean winner = false; // Show message when done
	private Graphics2D g2; // The drawer
	private Rectangle tempRectangle; // The "moving" rectangle
	private boolean animator = false; // Activates when action is done
	private int number; // Number to be shown in moving rectangle
	int fontSize = 0; // font size
	int deltaX = 0; // movement in X coordinate for one digit number
	int deltaX2 = 0; //movement in X coordinate for two digits numbers
	

	/**
	 * Mandatory method when implementing ActionListener class.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	/**
	 * Mandatory method when implementing KeyListener class.
	 * Turns of the animation clause and calls repaint again.
	 */
	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			animator = false;
			repaint();
		}
	}
	/**
	 * Mandatory method when implementing KeyListener class.
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Checks the game matrix against the result(sorted) matrix.
	 * If true the paint method should draw a winning message. 
	 * The flag also disables the key inputs
	 */
	public void verify(){
		boolean sorted = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(result[i][j] != matrix[i][j])
					sorted = false;
			}

		}
		if(sorted)
		{
			System.out.println("Winner winner chicken dinner!");
			winner = true;
		}
	}

	/**
	 * Modify the paintComponent in order to draw specific details
	 */
	public void paintComponent(Graphics g)
	{
		g2 = (Graphics2D) g;
		
		draw(g2);
		

	}

	/**
	 * Controls work pretty simple, action listener triggers > keylistener. When it's
	 * triggered it verifies if any of the specified keys were used.
	 * The swapping occurs simulating that indexi = x and indexj = y; 
	 * 
	 * The logic is swap the n^2 with the desired tile and then store the location of n^2 in memory again.
	 * 
	 * Set the animator flag to draw.
	 * Create the "moving" rectangle in the specified location.
	 * Paint the grid and verify the game.
	 * 
	 * Logic is inverted since the "blank tile" is not the one that actually moves.
	 */
	public void left() {	
		try {
			int temp = matrix[indexi][indexj];
			number = matrix[indexi][indexj - 1];
			matrix[indexi][indexj] = matrix[indexi][indexj - 1];
			matrix[indexi][indexj - 1] = temp;
			indexj -= 1;
			tempRectangle = new Rectangle((rectangles[indexi][indexj].y + rectangles[indexi][indexj + 1].y)/2, rectangles[indexi][indexj].x, sizex/size, sizey/size);
			animator = true;
			repaint();
			//verify();
		} catch (Exception e) {}
	}
	public void right() {	
		try {
			int temp = matrix[indexi][indexj];
			number = matrix[indexi][indexj + 1];
			matrix[indexi][indexj] = matrix[indexi][indexj + 1];
			matrix[indexi][indexj + 1] = temp;
			indexj += 1;
			tempRectangle = new Rectangle((rectangles[indexi][indexj].y + rectangles[indexi][indexj - 1].y)/2, rectangles[indexi][indexj].x, sizex/size, sizey/size);
			animator = true;
			repaint();
			//verify();
		} catch (Exception e) {}		
	}
	public void up() {	
		try {
			int temp = matrix[indexi][indexj];
			number = matrix[indexi - 1][indexj];
			matrix[indexi][indexj] = matrix[indexi - 1][indexj];
			matrix[indexi - 1][indexj] = temp;
			indexi -= 1;
			tempRectangle = new Rectangle(rectangles[indexi][indexj].y, (rectangles[indexi][indexj].x + rectangles[indexi + 1][indexj].x)/2, sizex/size, sizey/size);
			animator = true;
			repaint();
			//verify();
		} catch (Exception e) {}
	}
	public void down() {	
		try {
			int temp = matrix[indexi][indexj];
			number = matrix[indexi + 1][indexj];
			matrix[indexi][indexj] = matrix[indexi + 1][indexj];
			matrix[indexi + 1][indexj] = temp;
			indexi += 1;
			tempRectangle = new Rectangle(rectangles[indexi][indexj].y, (rectangles[indexi][indexj].x + rectangles[indexi - 1][indexj].x)/2, sizex/size, sizey/size);
			animator = true;
			repaint();
			//verify();
		} catch (Exception e) {}
	}
	/**
	 * Takes the input from the keyboard and calls the appropriate function.
	 */
	public void keyPressed(KeyEvent e) {
		if(!winner){
			if(e.getKeyCode() == KeyEvent.VK_UP)
				down();
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
				up();
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				right();
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
				left();
		}
	}	

	/**
	 * Fisher Yates Shuffle is used to constantly generate a random game
	 * @param vector with shuffled values
	 */
	private void shuffle(int[] vector) {
		Random random = new Random();
		for (int i = 0; i < vector.length; i++) {
			int j = i + random.nextInt(vector.length - i); 
			int temp = vector[i];
			vector[i] = vector[j];
			vector[j] = temp;	
		}
	}

	/**
	 * Copy shuffled vector into matrix
	 * @param vector Array of values
	 * @return Matrix with shuffled values
	 */
	private int[][] matrix(int[] vector){
		int k = 0;
		int[][] matrix = new int[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				matrix[i][j] = vector[k];
				k++;
			}
		}
		return matrix;
	}
}

