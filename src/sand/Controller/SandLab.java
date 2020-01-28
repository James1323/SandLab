package sand.Controller;
import java.awt.*;
import java.util.*;

import sand.View.SandDisplay;

public class SandLab
{
	//Step 4,6
	//add constants for particle types here
	public static final int EMPTY = 0;
	public static final int SAND = 1;
	public static final int WATER = 2;
	public static final int ACIDGAS = 3;
	public static final int SALT = 4;
	public static final int SWATER = 5;
	public static final int STONE = 6;
	public static final int METAL = 7;
	public static final int RUST = 8;

	//do not add any more fields below
	private int[][] grid;
	private SandDisplay display;


	/**
	 * Constructor for SandLab
	 * @param numRows The number of rows to start with
	 * @param numCols The number of columns to start with;
	 */
	public SandLab(int numRows, int numCols)
	{
		String[] names;
		int[] liquids;
		// Change this value to add more buttons
		//Step 4,6
		names = new String[10];
		// Each value needs a name for the button
		names[EMPTY] = "Empty";
		names[SAND] = "Sand";
		names[WATER] = "Water";
		names[ACIDGAS] = "AcidGas";
		names[SALT] = "Salt";
		names[SWATER] = "SaltWater";
		names[STONE] = "Stone";
		names[METAL] = "Metal";
		names[RUST] = "Rust";


		//1. Add code to initialize the data member grid with same dimensions
		this.grid = new int [numRows] [numCols];


		display = new SandDisplay("Falling Sand", numRows, numCols, names);
	}

	//called when the user clicks on a location using the given tool
	private void locationClicked(int row, int col, int tool)
	{
		//2. Assign the values associated with the parameters to the grid
		grid [row] [col] = tool;
	}

	//copies each element of grid into the display
	public void updateDisplay()
	{
		//Step 3
		//Hint - use a nested for loop
		for(int rows = 0; rows < grid.length; rows++)
		{
			for(int cols = 0; cols < grid[0].length; cols++)
			{
				//empty is black
				colorElement(rows, cols, EMPTY, Color.black);
				colorElement(rows, cols, METAL, Color.LIGHT_GRAY);
				colorElement(rows, cols, SAND, Color.yellow);
				colorElement(rows, cols, WATER, Color.blue);
				colorElement(rows, cols, ACIDGAS, Color.green);
				colorElement(rows, cols, SALT, Color.white);
				colorElement(rows, cols, SWATER, Color.cyan);
				colorElement(rows, cols, STONE, Color.GRAY);
				colorElement(rows, cols, RUST, Color.red);


			}

		}

	}


	//Step 5,7
	//called repeatedly.
	//causes one random particle in grid to maybe do something.
	public void physics()
	{
		//Remember, you need to access both row and column to specify a spot in the array
		//The scalar refers to how big the value could be
		//int someRandom = (int) (Math.random() * scalar)
		//remember that you need to watch for the edges of the array
		int randomRow = (int) (Math.random() * grid.length);
		int randomCol = (int) (Math.random() * grid.length);

		//salt comes into contact with water mix
		contact(SALT, WATER, "mix", randomRow, randomCol);
		contact(SALT, SWATER, "mix", randomRow, randomCol); //mix around in water
		contact(SWATER, WATER,"sink", randomRow, randomCol);
		contact(WATER, SWATER,"mix", randomRow, randomCol);
		contact(SAND, WATER, "pile", randomRow, randomCol); //pile in water
		contact(SAND, SWATER, "pile", randomRow, randomCol);
		contact(STONE, WATER, "sink", randomRow, randomCol); //sink in water
		contact(STONE, SWATER, "sink", randomRow, randomCol);
		contact(RUST, WATER, "mix", randomRow, randomCol);
		contact(RUST, SWATER, "mix", randomRow, randomCol);
		contact(RUST, WATER, "sink", randomRow, randomCol);
		contact(RUST, SWATER, "sink", randomRow, randomCol);


		//water + salt = swater (saltwater)
		combine(WATER, SALT, SWATER, randomRow, randomCol);
		combine(WATER, METAL, RUST, randomRow, randomCol);
		combine(SWATER, METAL, RUST, randomRow, randomCol);

		//physics of stone (stone and powder physics are currently the same)
		stonePhysics(STONE, randomRow, randomCol);

		//sand has the physics of powder
		powderPhysics(SAND, randomRow, randomCol);
		powderPhysics(SALT, randomRow, randomCol);
		powderPhysics(RUST, randomRow, randomCol);

		//water has the physics of liquid
		liquidPhysics(WATER, randomRow, randomCol);
		liquidPhysics(SWATER, randomRow, randomCol);



		//Deletes everything then deletes itself. The first sadomasochistic physic.
		acidGasPhysics(ACIDGAS, randomRow, randomCol);










	}

	//HELPER FUNCTIONS

	//Probably the most boring function. Still useful though. 
	public void colorElement(int rows, int cols, int ELEMENT, Color color )
	{

		if(this.grid[rows][cols] == ELEMENT )
		{
			display.setColor(rows, cols, color);
		}
	}

	//When you put things together, other things tend to happen. 
	public void contact(int ELEMENT, int contactELEMENT, String mixSinkFloatPile, int row, int col)
	{
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");
		int leftOrRight = (int) (Math.random() * 2);
		int upOrDown = (int) (Math.random() * 2);

		if(grid[row][col] == ELEMENT)
			if(row + 1 < grid[0].length && row -1 >= 0)
			{
				if(col + 1 < grid[0].length && col - 1 >= 0)
				{
					if(mixSinkFloatPile.equalsIgnoreCase("mix"))
					{
						if(leftOrRight == 0 && this.grid[row][col + 1] == contactELEMENT)
						{
							this.grid[row][col + 1] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						} 
						else if(leftOrRight == 1 && this.grid[row][col - 1] == contactELEMENT)
						{
							this.grid[row][col - 1] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						}
						else if(upOrDown == 0 && this.grid[row + 1][col] == contactELEMENT)
						{
							this.grid[row + 1][col] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						}
						else if(upOrDown == 0 && this.grid[row - 1][col] == contactELEMENT)
						{
							this.grid[row - 1][col] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						}
					}
					else if(mixSinkFloatPile.equalsIgnoreCase("sink"))
					{
						if(this.grid[row + 1][col] == contactELEMENT)
						{
							this.grid[row + 1][col] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						}
					}
					else if(mixSinkFloatPile.equalsIgnoreCase("pile"))
					{
						//Border
						if(row + 1 < grid.length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0) 
						{
							//fall if empty below
							if(this.grid[row + 1][col] == contactELEMENT)
							{

								this.grid[row][col] = contactELEMENT;
								this.grid[row + 1][col] = ELEMENT;
							}

							else if(leftOrRight == 0)
							{
								if(this.grid[row + 1 ][col + 1] == contactELEMENT)
								{
									this.grid[row][col] = contactELEMENT;
									this.grid[row][col + 1] = ELEMENT;
								}

								else
								{
									this.grid[row][col] = ELEMENT;
								}
							}
							else if(leftOrRight == 1)
							{
								if(this.grid[row + 1][col - 1] == contactELEMENT )
								{
									this.grid[row][col] = contactELEMENT;
									this.grid[row][col - 1] = ELEMENT;
								}
								else
								{
									this.grid[row][col] = ELEMENT;
								}
							}


						}
					}
					else if(mixSinkFloatPile.equalsIgnoreCase("float"))
					{
						if(this.grid[row - 1][col] == contactELEMENT)
						{
							this.grid[row - 1][col] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						}
					}
					else
					{
						if(this.grid[row + 1][col] == contactELEMENT)
						{
							this.grid[row + 1][col] = ELEMENT;
							this.grid[row][col] = contactELEMENT;
						}
					}
				}
			}

	}

	//I'm telling you, 2 + 2 = 5 and you have to just take my word on it.
	public void combine(int ELEMENT, int contactELEMENT, int RESULT, int row, int col)
	{
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");
		if(this.grid[row][col] == ELEMENT)
		{
			if(row + 1 < grid[0].length && row -1 >= 0)
			{
				if(col + 1 < grid[0].length && col - 1 >= 0)
				{
					if(this.grid[row][col + 1] == contactELEMENT)
					{
						this.grid[row][col + 1] = EMPTY;
						this.grid[row][col] = RESULT;

					} 
					else if(this.grid[row][col - 1] == contactELEMENT)
					{
						this.grid[row][col - 1] = EMPTY;
						this.grid[row][col] = RESULT;
					}
					else if(this.grid[row + 1][col] == contactELEMENT)
					{
						this.grid[row + 1][col] = EMPTY;
						this.grid[row][col] = RESULT;
					}
					else if(this.grid[row - 1][col] == contactELEMENT)
					{
						this.grid[row - 1][col] = EMPTY;
						this.grid[row][col] = RESULT;
					}
				}
			}

		}
	}

	//Even boring stone is interesting now.
	public void stonePhysics(int ELEMENT, int row, int col)
	{
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{
			//Border
			if(row + 1 < grid[0].length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0) 
			{
				//fall if empty below
				if(this.grid[row + 1][col] == EMPTY)
				{

					this.grid[row][col] = EMPTY;
					this.grid[row + 1][col] = ELEMENT;
				}

				else if(goLeftOrRight == 1)
				{
					if(this.grid[row][col - 1] == EMPTY && this.grid[row][col + 1] == EMPTY )
					{
						this.grid[row][col] = EMPTY;
						this.grid[row][col + 1] = ELEMENT;
					}

					else
					{
						this.grid[row][col] = ELEMENT;
					}
				}
				else if(goLeftOrRight == 0)
				{
					if(this.grid[row][col - 1] == EMPTY && this.grid[row][col + 1] == EMPTY)
					{
						this.grid[row][col] = EMPTY;
						this.grid[row][col - 1] = ELEMENT;
					}
					else
					{
						this.grid[row][col] = ELEMENT;
					}
				}


			}
		}
	}

	//Holy crap there's piles now?
	public void powderPhysics(int ELEMENT, int row, int col)
	{
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{
			//Border
			if(row + 1 < grid[0].length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0) 
			{
				//fall if empty below
				if(this.grid[row + 1][col] == EMPTY)
				{

					this.grid[row][col] = EMPTY;
					this.grid[row + 1][col] = ELEMENT;
				}

				else if(goLeftOrRight == 0)
				{
					if(this.grid[row + 1 ][col + 1] == EMPTY)
					{
						this.grid[row][col] = EMPTY;
						this.grid[row + 1][col + 1] = ELEMENT;
					}

					else
					{
						this.grid[row][col] = ELEMENT;
					}
				}
				else if(goLeftOrRight == 1)
				{
					if(this.grid[row + 1][col - 1] == EMPTY)
					{
						this.grid[row][col] = EMPTY;
						this.grid[row + 1][col - 1] = ELEMENT;
					}
					else
					{
						this.grid[row][col] = ELEMENT;
					}
				}


			}
		}
	}

	//Splish splash, I'm taken a bath!
	public void liquidPhysics(int ELEMENT, int row, int col)
	{
		//MOVEMENT
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{        //floor					//right wall				left wall	    Ceiling
			if(row + 1 < grid[0].length)
			{
				if(col + 1 < grid[0].length)
				{
					if(col - 1 >= 0)
					{
						if(row -1 >= 0)
						{
							
							//bottom border and space below is empty
							if(checker(row + 1, col) == EMPTY)
							{
								move("down", ELEMENT, 1, row, col);
							}
							else
							{



								if(goLeftOrRight == 0)
								{
									//if random is less than grid size and right is empty
									if(grid[row][col + 1] == EMPTY )
									{
										//if bottom is the same element move right and if right is less than grid size
										if(grid[row + 1][col] != EMPTY)
										{
											this.grid[row][col] = EMPTY;
											this.grid[row][col + 1] = ELEMENT;
										}
										else if(checker(row, col - 1) != EMPTY)
										{
											move("left", ELEMENT, 1, row, col);
										}
										else
										{
											this.grid[row][col] = ELEMENT;
										}
									}
								}


								else if(goLeftOrRight == 1)
								{
									//if left is empty
									if (grid[row][col - 1] == EMPTY)
									{
										//if bottom is not empty
										if(grid[row + 1][col] != EMPTY)
										{
											this.grid[row][col] = EMPTY;
											this.grid[row][col - 1] = ELEMENT;
										}
										//if bottom is greater than grid size move left
										else if(checker(row, col + 1) != EMPTY)
										{
											move("right", ELEMENT, 1, row, col);
										}
										else
										{
											this.grid[row][col] = ELEMENT;
										}

									}
								}
							}
						}
					}
				}
			}
			else
			{

			}


		}

	}

	//Make like some gas and get out of here!
	public void gasPhysics(int ELEMENT, int row, int col) 
	{
		if(this.grid[row][col] == ELEMENT)
		{
			if(row + 1 < grid[0].length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0)
			{
				this.grid[row][col] = ELEMENT;
			}
		}
	}

	//To be or not to be, that is the question. The answer is to just be in this case.
	public void metalPhysics(int ELEMENT, int row, int col)
	{
		if(this.grid[row][col] == ELEMENT)
		{
			if(row + 1 < grid[0].length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0)
			{
				this.grid[row][col] = ELEMENT;
			}
		}

	}

	//Note: very stupidly made...
	public void acidGasPhysics(int ELEMENT, int row, int col)
	{
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{
			//upper border and space above is empty
			if(row + 1 < grid[0].length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0)
			{
				//switch empty with element
				this.grid[row][col] = EMPTY;
				this.grid[row - 1][col] = ELEMENT;
			}
			else {
				this.grid[row][col] = EMPTY;
			}
			//left right and bottom border
			if(row + 1 < grid.length && col >= 0 && col < grid.length)
			{
				if(goLeftOrRight == 0 && col + 1 < grid.length && col - 1 >= 0)
				{
					//if random is less than grid size and right is empty
					if(col + 1 < grid.length && grid[row][col + 1] == EMPTY )
					{
						//if bottom is the same element move right and if right is less than grid size
						if(col + 1 < grid.length && row + 1 < grid.length && grid[row + 1][col] != EMPTY)
						{
							this.grid[row][col] = EMPTY;
							this.grid[row][col + 1] = ELEMENT;
						}
						//if bottom is greater than grid size move right
						else if(col + 1 >= 0 && row + 1 < grid.length && grid[row + 1][col] > grid.length)
						{
							this.grid[row][col] = EMPTY;
							this.grid[row][col + 1] = ELEMENT;
						}
						else
						{
							this.grid[row][col] = EMPTY;
						}
					}
				}


				else if(goLeftOrRight == 1 && col - 1 >= 0 && col + 1 < grid[0].length)
				{
					//if random is greater than 0 and grid left is empty and left is not element
					if (col - 1 >= 0 && grid[row][col - 1] == EMPTY)
					{
						//if bottom is the same move left
						if(col - 1 >= 0 && row - 1 < grid.length && grid[row + 1][col] != EMPTY)
						{
							this.grid[row][col] = EMPTY;
							this.grid[row][col - 1] = ELEMENT;
						}
						//if bottom is greater than grid size move left
						else if(col - 1 >= 0 && row - 1 < grid.length && grid[row + 1][col] > grid.length)
						{
							this.grid[row][col] = EMPTY;
							this.grid[row][col - 1] = ELEMENT;
						}

					}
				}
			}

		}
	}

	public int checker(int row, int col)
	{
		if(grid[row][col] == EMPTY)
		{
			return EMPTY;
		}
		else
		{
			return 1;
		}
	}

	public void move(String movement, int ELEMENT, int spaces, int row, int col )
	{
		if(movement.equalsIgnoreCase("up"))
		{
			up(row, col, spaces, ELEMENT);
		}
		else if(movement.equalsIgnoreCase("down"))
		{
			down(row, col, spaces, ELEMENT);
		}
		else if(movement.equalsIgnoreCase("left"))
		{
			left(row, col, spaces, ELEMENT);
		}
		else if(movement.equalsIgnoreCase("right"))
		{
			right(row, col, spaces, ELEMENT);
		}
		else
		{
			up(row, col, spaces, ELEMENT);
		}
	}

	public void up(int row, int col, int spaces, int ELEMENT)
	{
		this.grid[row][col] = EMPTY;
		this.grid[row - spaces][col] = ELEMENT;
	}
	public void down(int row, int col, int spaces, int ELEMENT)
	{
		this.grid[row][col] = EMPTY;
		this.grid[row + spaces][col] = ELEMENT;
	}
	public void left(int row, int col, int spaces, int ELEMENT)
	{
		this.grid[row][col] = EMPTY;
		this.grid[row][col - spaces] = ELEMENT;
	}
	public void right(int row, int col, int spaces, int ELEMENT)
	{
		this.grid[row][col] = EMPTY;
		this.grid[row][col + spaces] = ELEMENT;
	}


	//do not modify this method!
	public void run()
	{
		while (true) // infinite loop
		{
			for (int i = 0; i < display.getSpeed(); i++)
			{
				physics();
			}
			updateDisplay();
			display.repaint();
			display.pause(1);  //wait for redrawing and for mouse
			int[] mouseLoc = display.getMouseLocation();
			if (mouseLoc != null)  //test if mouse clicked
			{
				locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
			}
		}
	}
}
