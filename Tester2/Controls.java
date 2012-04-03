package Tester2;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;
/**
 * 
 * @author yamilasusta && davidbartolomei
 *
 */
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

	public Controls(int[] vector) {
		size = (int) Math.sqrt(vector.length);
		matrix = matrix(vector);
		matrix2 = matrix(vector);
		Arrays.sort(vector);
		result = matrix(vector);
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				if(matrix[i][j] == vector.length){
					indexi = i;
					indexj = j;
				}
			}
		}
		indexi2 = indexi;
		indexj2 = indexj;
		rectangle1 = new Rectangle[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rectangle1[i][j] = new Rectangle( i*(sizex/size)+50,j*(sizey/size)+100, ((sizex/size)), ((sizey/size)));
			}
		}
		rectangle2 = new Rectangle[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rectangle2[i][j] = new Rectangle( i*(sizex/size) + 550,j*(sizey/size)+100, ((sizex/size)), ((sizey/size)));
			}
		}

		controller2 = new ArrayList<int[][]>();
		controller2.add(matrix);
		controller2.add(matrix2);
		controller = new ArrayList<Rectangle[][]>();
		controller.add(rectangle1);
		controller.add(rectangle2);
		repaint();
		addKeyListener(this);	
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
	public Controls(int size) {
		this.size = size;
		//Generate vector with specified size and fill it.
		int[] vector = new int[size * size];
		for(int i = 1; i <= vector.length; i++)
			vector[i-1] = i;//Fill vector with sorted values
		result = matrix(vector);
		shuffle(vector);
		matrix = matrix(vector);
		matrix2 = matrix(vector);

		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix.length; j++){
				if(matrix[i][j] == vector.length){
					indexi = i;
					indexj = j;
				}
			}
		}
		indexi2 = indexi;
		indexj2 = indexj;		

		/**
		 * Creates the rectangle matrix that should be drawn.
		 * Using a standard size of the frame, tiles will adjust themselves to fit in the frame.
		 * Location will be determined by their location in the matrix.
		 */
		rectangle1 = new Rectangle[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rectangle1[i][j] = new Rectangle( i*(sizex/size)+50,j*(sizey/size)+100, ((sizex/size)), ((sizey/size)));
			}
		}

		/**
		 * Creates the rectangle matrix that should be drawn.
		 * Using a standard size of the frame, tiles will adjust themselves to fit in the frame.
		 * Location will be determined by their location in the matrix.
		 */
		rectangle2 = new Rectangle[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rectangle2[i][j] = new Rectangle( i*(sizex/size) + 550,j*(sizey/size)+100, ((sizex/size)), ((sizey/size)));
			}
		}
		controller2 = new ArrayList<int[][]>();
		controller2.add(matrix);
		controller2.add(matrix2);
		controller = new ArrayList<Rectangle[][]>();
		controller.add(rectangle1);
		controller.add(rectangle2);
		repaint();
		addKeyListener(this);
		number[0] = 0;
		number[1] = 0;

	}

	/**
	 * method that according to the size of the
	 *  matrix define the font size and position in the x coordinate
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
		Font font2 = new Font("sansserif", Font.BOLD, 46); // winner banner font
		Color tileColor = new Color(243,243,243);

		for (int k = 0; k < controller.size(); k++) {
			rectangles = controller.get(k);
			matrixs = controller2.get(k);

			for(int i = 0; i < matrixs.length; i++) {
				for(int j = 0; j < matrixs.length; j++) {
					boolean paint = true;
					if(matrixs[j][i] == (size*size) || matrixs[j][i] == number[k])
						paint = false;
					if(paint){
						g2.setColor(tileColor);
						g2.setFont(font);
						g2.fill(rectangles[i][j]);
						g2.setColor(Color.LIGHT_GRAY);
						g2.draw(rectangles[i][j]);	
						if(matrixs[j][i] % 2 == 0)
							g2.setColor(Color.BLUE);
						else
							g2.setColor(Color.RED);

						if(Math.floor(matrixs[j][i]/10) >= 1){ //to draw numbers greater than 10

							g2.drawString(Integer.toString(matrixs[j][i]),(int)rectangles[i][j].getCenterX()-deltaX2,
									(int)rectangles[i][j].getCenterY()+font.getSize()/2);

						}

						else{//to draw numbers of 1 digit
							g2.drawString(Integer.toString(matrixs[j][i]),(int)rectangles[i][j].getCenterX()-deltaX,
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
		}
		if(winner1){
			g2.setFont(font2);
			g2.setColor(Color.BLACK);
			g2.drawString("Winner Winner!!!", 60, 96);
			g2.setColor(Color.YELLOW);
			g2.drawString("Winner Winner!!!", 62, 98);
			g2.setColor(Color.BLACK);
			g2.drawString("Chicken Dinner!!", 60, 540); // winner award
			g2.setColor(Color.MAGENTA);
			g2.drawString("Chicken Dinner!!", 62, 542); // winner award

		}
		if(winner2){
			g2.setFont(font2);
			g2.setColor(Color.BLACK);
			g2.drawString("Winner Winner!!!", 560, 96);
			g2.setColor(Color.YELLOW);
			g2.drawString("Winner Winner!!!", 562, 98);
			g2.setColor(Color.BLACK);
			g2.drawString("Chicken Dinner!!", 560, 540); // winner award
			g2.setColor(Color.MAGENTA);
			g2.drawString("Chicken Dinner!!", 562, 542); // winner award
		}
		if(animator1) {
			g2.setColor(tileColor);
			g2.fill(tempRectangle);
			g2.setColor(Color.LIGHT_GRAY);
			g2.draw(tempRectangle);
			if(number[0] % 2 == 0)
				g2.setColor(Color.BLUE);
			else
				g2.setColor(Color.RED);
			if(Math.floor(number[0]/10.0) >= 1) //#s with two digits
				g2.drawString(Integer.toString(number[0]),(int)tempRectangle.getCenterX()-deltaX2,
						(int)tempRectangle.getCenterY()+font.getSize()/2);

			else{ //#s with one digit
				g2.drawString(Integer.toString(number[0]),(int)tempRectangle.getCenterX()-deltaX, 
						(int)tempRectangle.getCenterY()+font.getSize()/2);}
		}

		animator1 = false;
		number[0] = 0;

		if(animator2) {
			g2.setColor(tileColor);
			g2.fill(tempRectangle);
			g2.setColor(Color.LIGHT_GRAY);
			g2.draw(tempRectangle);
			if(number[1] % 2 == 0)
				g2.setColor(Color.BLUE);
			else
				g2.setColor(Color.RED);
			if(Math.floor(number[1]/10.0) >= 1) //#s with two digits
				g2.drawString(Integer.toString(number[1]),(int)tempRectangle.getCenterX()-deltaX2,
						(int)tempRectangle.getCenterY()+font.getSize()/2);

			else{ //#s with one digit
				g2.drawString(Integer.toString(number[1]),(int)tempRectangle.getCenterX()-deltaX, 
						(int)tempRectangle.getCenterY()+font.getSize()/2);}	
			animator2 = false;
			number[1] = 0;
		}
	}


	/**
	 * Mandatory methods implemented by KeyListener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}
	/**
	 * Remove the transition rectangle and repaint
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
			tempRectangle = null;
			repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_W) {
			tempRectangle = null;
			repaint();
		}

	}
	/**
	 * Mandatory method implemented by KeyListener
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * Verify is either player is a winner.
	 */
	public void verify(){
		winner1 = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(result[i][j] != matrix[i][j])
					winner1 = false;
			}	
		}
		winner2 = true;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(result[i][j] != matrix2[i][j])
					winner2 = false;
			}	
		}
	}

	/**
	 * Fisher Yates Shuffle
	 * @param vector with shuffled values
	 */
	private void shuffle(int[] vector) {
		Random random = new Random();
		for (int i = 0; i < vector.length; i++) {
			int j = i + random.nextInt(vector.length - i); //Random number from 0 <= j <= i
			//swap values;
			int temp = vector[i];
			vector[i] = vector[j];
			vector[j] = temp;	
		}
	}

	/**
	 * Copy shuffled vector into matrix
	 * @param vector 
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

	/**
	 * Controls work pretty simple, action listener triggers > keylistener. When it's
	 * triggered it verifies if any of the specified keys were used.
	 * The swapping occurs simulating that indexi = y and indexj = x; 
	 * 
	 * The logic is swap the n^2 with the desired tile and then store the location of n^2 in memory again.
	 */
	public void left() {	
		try {
			int temp = matrix[indexi][indexj];
			number[0] = matrix[indexi][indexj - 1];
			matrix[indexi][indexj] = matrix[indexi][indexj - 1];
			matrix[indexi][indexj - 1] = temp;
			indexj -= 1;
			tempRectangle = new Rectangle((rectangle1[indexj][indexi].x + rectangle1[indexj + 1][indexi].x)/2, rectangle1[indexj][indexi].y, sizex/size, sizey/size);
			animator1 = true;
			repaint();
			verify();
		}catch (Exception e) {}
	}
	public void right() {	
		try {
			int temp = matrix[indexi][indexj];
			number[0] = matrix[indexi][indexj + 1];
			matrix[indexi][indexj] = matrix[indexi][indexj + 1];
			matrix[indexi][indexj + 1] = temp;
			indexj += 1;
			tempRectangle = new Rectangle((rectangle1[indexj][indexi].x + rectangle1[indexj - 1][indexi].x)/2, rectangle1[indexj][indexi].y, sizex/size, sizey/size);
			animator1 = true;
			repaint();
			verify();
		} catch (Exception e) {}
	}
	public void up() {	
		try {
			int temp = matrix[indexi][indexj];
			number[0] = matrix[indexi - 1][indexj];
			matrix[indexi][indexj] = matrix[indexi - 1][indexj];
			matrix[indexi - 1][indexj] = temp;
			indexi -= 1;
			tempRectangle = new Rectangle(rectangle1[indexj][indexi].x, (rectangle1[indexj][indexi].y + rectangle1[indexj][indexi + 1].y)/2, sizex/size, sizey/size);
			animator1 = true;
			repaint();
			verify();
		} catch (Exception e) {}
	}
	public  void down() {	//pressing w
		try {
			int temp = matrix[indexi][indexj];
			number[0] = matrix[indexi + 1][indexj];
			matrix[indexi][indexj] = matrix[indexi + 1][indexj];
			matrix[indexi + 1][indexj] = temp;
			indexi += 1;
			tempRectangle = new Rectangle(rectangle1[indexj][indexi].x, (rectangle1[indexj][indexi].y + rectangle1[indexj][indexi - 1].y)/2, sizex/size, sizey/size);
			animator1 = true;
			repaint();
			verify();
		} catch (Exception e) {}
	}

	public void left2() {	
		try {
			int temp = matrix2[indexi2][indexj2];
			number[1] = matrix2[indexi2][indexj2 - 1];
			matrix2[indexi2][indexj2] = matrix2[indexi2][indexj2 - 1];
			matrix2[indexi2][indexj2 - 1] = temp;
			indexj2 -= 1;
			tempRectangle = new Rectangle((rectangle2[indexj2][indexi2].x + rectangle2[indexj2 + 1][indexi2].x)/2, rectangle2[indexj2][indexi2].y, sizex/size, sizey/size);
			animator2 = true;
			repaint();
			verify();
		}catch (Exception e) {}
	}
	public void right2() {	
		try {
			int temp = matrix2[indexi2][indexj2];
			number[1] = matrix2[indexi2][indexj2 + 1];
			matrix2[indexi2][indexj2] = matrix2[indexi2][indexj2 + 1];
			matrix2[indexi2][indexj2 + 1] = temp;
			indexj2 += 1;
			tempRectangle = new Rectangle((rectangle2[indexj2][indexi2].x + rectangle2[indexj2 - 1][indexi2].x)/2, rectangle2[indexj2][indexi2].y, sizex/size, sizey/size);
			animator2 = true;
			repaint();
			verify();
		} catch (Exception e) {}
	}
	public void up2() {	
		try {
			int temp = matrix2[indexi2][indexj2];
			number[1] = matrix2[indexi2 - 1][indexj2];
			matrix2[indexi2][indexj2] = matrix2[indexi2 - 1][indexj2];
			matrix2[indexi2 - 1][indexj2] = temp;
			indexi2 -= 1;
			tempRectangle = new Rectangle(rectangle2[indexj2][indexi2].x, (rectangle2[indexj2][indexi2].y + rectangle2[indexj2][indexi2 + 1].y)/2, sizex/size, sizey/size);
			animator2 = true;
			repaint();
			verify();
		} catch (Exception e) {}
	}
	public void down2() {	
		try {
			int temp = matrix2[indexi2][indexj2];
			number[1] = matrix2[indexi2 + 1][indexj2];
			matrix2[indexi2][indexj2] = matrix2[indexi2 + 1][indexj2];
			matrix2[indexi2 + 1][indexj2] = temp;
			indexi2 += 1;
			tempRectangle = new Rectangle(rectangle2[indexj2][indexi2].x, (rectangle2[indexj2][indexi2].y + rectangle2[indexj2][indexi2 - 1].y)/2, sizex/size, sizey/size);
			animator2 = true;
			repaint();
			verify();
		} catch (Exception e) {}
	}

	/**
	 * key pressed method for the controls
	 */
	public void keyPressed(KeyEvent e) {
		if(!winner1){
			if(!winner2){
				if(e.getKeyCode() == KeyEvent.VK_UP)
					down2();
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
					up2();
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
					right2();
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
					left2();
				if(e.getKeyCode() == KeyEvent.VK_W)
					down();
				if(e.getKeyCode() == KeyEvent.VK_S)
					up();
				if(e.getKeyCode() == KeyEvent.VK_A)
					right();
				if(e.getKeyCode() == KeyEvent.VK_D) 
					left();
			}
		}
		if ((e.getKeyCode()) == (KeyEvent.VK_ALT | KeyEvent.VK_F4)) {

			System.exit(0);

		}
	}
	/**
	 * Paint method modifier
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponents(g2);
		draw(g2);

	}

	private ArrayList<Rectangle[][]> controller; // where the rectangles are moved
	private ArrayList<int[][]> controller2; // where the matrix is moved
	private int[][] result;  //comparator matrix
	private int[][] matrix; //player 1 matrix
	private int[][] matrix2; // player 2 matrix
	private int[][] matrixs; //both matrix together for movements
	private int indexi; //matrix 1 index
	private int indexj; //matrix 1 index
	private int indexi2; // matrix 2 index
	private int indexj2; // matrix 2 index
	private int size; //dimension of the matrix
	private Rectangle[][] rectangles; //all the rectangles to be drawn
	private Rectangle[][] rectangle1; //player 1 tiles
	private Rectangle[][] rectangle2; //player 2 tiles
	private static final int sizex = 400;
	private static final int sizey = 400;
	private Rectangle tempRectangle; //animated rectangle
	private boolean animator1 = false; //animation trigger
	private boolean animator2 = false; //animation trigger
	private boolean winner1 = false; //winner trigger
	private boolean winner2 = false; //winner trigger
	private int[] number = new int[2]; //animator numbers
	private int fontSize = 0; // font size
	private int deltaX = 0; // movement in X coordinate for one digit number
	private int deltaX2 = 0; //movement in X coordinate for two digits numbers

}
