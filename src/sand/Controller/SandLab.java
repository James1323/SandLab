package Controller;
import java.awt.*;
import java.util.*;

import View.SandDisplay;

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
				//empty
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
		
		contact(WATER, SALT, randomRow, randomCol);
		contact(SWATER, SALT, randomRow, randomCol);
		contact(SWATER, WATER, randomRow, randomCol);
		
		stonePhysics(STONE, randomRow, randomCol);

		powderPhysics(SAND, randomRow, randomCol);
		powderPhysics(SALT, randomRow, randomCol);
		powderPhysics(RUST, randomRow, randomCol);

		waterPhysics(WATER, randomRow, randomCol);
		waterPhysics(SWATER, randomRow, randomCol);

		combinePhysics(WATER, SALT, SWATER, randomRow, randomCol);
		combinePhysics(METAL, WATER, RUST, randomRow, randomCol);
		combinePhysics(METAL, SWATER, RUST, randomRow, randomCol);
		

		gasPhysics(ACIDGAS, randomRow, randomCol);










	}

	//HELPER FUNCTIONS

	//	public int randomGridLength(String rowOrCol)
	//	{
	//		int randomRow = (int) (Math.random() * grid.length);
	//		int randomCol = (int) (Math.random() * grid.length);
	//		if(rowOrCol.equalsIgnoreCase("row"))
	//		{
	//		return randomRow; 
	//		}
	//		else if(rowOrCol.equalsIgnoreCase("col"))
	//		{
	//		return randomCol;
	//		}
	//		else
	//		{
	//			return 0;
	//		}
	//	}

	public void colorElement(int rows, int cols, int ELEMENT, Color color )
	{

		if(this.grid[rows][cols] == ELEMENT )
		{
			display.setColor(rows, cols, color);
		}
	}

	public void contact(int ELEMENT, int contactELEMENT, int row, int col)
	{
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");
		int leftOrRight = (int) (Math.random() * 2);
		int upOrDown = (int) (Math.random() * 2);

		if(grid[row][col] == ELEMENT)
			if(row + 1 < grid.length && row -1 >= 0)
			{
				if(col + 1 < grid[0].length && col - 1 >= 0)
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
			}

	}

	public void stonePhysics(int ELEMENT, int row, int col)
	{
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{
			//Border
			if(row + 1 < grid.length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0) 
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

	public void waterPhysics(int ELEMENT, int row, int col)
	{
		//MOVEMENT
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{     //floor						//right wall						left wall			Ceiling
			if(row + 1 < grid[0].length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0)
			{
				//bottom border and space below is empty
				if(grid[row + 1][col] == EMPTY)
				{
					//switch empty with element
					this.grid[row][col] = EMPTY;
					this.grid[row + 1][col] = ELEMENT;
				}

				else if(goLeftOrRight == 0 && col + 1 < grid.length && col - 1 >= 0)
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
							this.grid[row][col] = ELEMENT;
						}
					}
				}


				else if(goLeftOrRight == 1 && col - 1 >= 0 && col + 1 < grid.length)
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

	public void combinePhysics(int ELEMENT, int contactELEMENT, int RESULT, int row, int col)
	{
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");
		if(this.grid[row][col] == ELEMENT)
		{
			if(row + 1 < grid.length && row -1 >= 0)
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

	public void gasPhysics(int ELEMENT, int row, int col)
	{
		int goLeftOrRight = (int) (Math.random() * 2);
		//		int row = randomGridLength("row");
		//		int col = randomGridLength("col");

		if(this.grid[row][col] == ELEMENT)
		{
			//upper border and space above is empty
			if(row + 1 < grid.length && col + 1 < grid[0].length && col - 1 >= 0 && row -1 >= 0)
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


				else if(goLeftOrRight == 1 && col - 1 >= 0 && col + 1 < grid.length)
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
