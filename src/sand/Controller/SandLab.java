package sand.Controller;
import java.awt.*;
import java.util.*;

import sand.View.SandDisplay;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int GAS = 4;
  public static final int SALT = 5;
  public static final int SWATER = 6;
  public static final int WALL = 7;
  public static final int STONE = 8;
  
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
    // Change this value to add more buttons
    //Step 4,6
    names = new String[10];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[GAS] = "Gas";
    names[SALT] = "Salt";
    names[SWATER] = "SaltWater";
    names[STONE] = "Stone";
    
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
			  colorElement(rows, cols, GAS, Color.green);
			  colorElement(rows, cols, SALT, Color.white);
			  colorElement(rows, cols, SWATER, Color.cyan);
			  colorElement(rows, cols, STONE, Color.GRAY);
			 
			  
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
    stonePhysics(STONE, randomRow, randomCol);
    
    powderPhysics(SAND, randomRow, randomCol);
    powderPhysics(SALT, randomRow, randomCol);
    
    waterPhysics(WATER, randomRow, randomCol);
    waterPhysics(SWATER, randomRow, randomCol);
    
    saltMixPhysics(WATER, randomRow, randomCol);
    
    gasPhysics(GAS, randomRow, randomCol);
    
    
    
    
    
    
    
    
    
    
  }
  
  //HELPER FUNCTIONS
  
  public void colorElement(int rows, int cols, int ELEMENT, Color color )
  {
	  
	  if(this.grid[rows][cols] == ELEMENT )
	  {
		  display.setColor(rows, cols, color);
	  }
  }
  
  public void stonePhysics(int ELEMENT, int randomRow, int randomCol)
  {
	  int goLeftOrRight = (int) (Math.random() * 2);

    if(this.grid[randomRow][randomCol] == ELEMENT)
    {
		//Border
		if(randomRow + 1 < grid.length && randomCol + 1 < grid[0].length && randomCol - 1 >= 0 && randomRow -1 >= 0) 
		{
			//fall if empty below
			if(this.grid[randomRow + 1][randomCol] == EMPTY)
			{
				
				this.grid[randomRow][randomCol] = EMPTY;
				this.grid[randomRow + 1][randomCol] = ELEMENT;
			}

			else if(goLeftOrRight == 1)
			{
				if(this.grid[randomRow][randomCol - 1] == EMPTY && this.grid[randomRow][randomCol + 1] == EMPTY )
				{
					this.grid[randomRow][randomCol] = EMPTY;
					this.grid[randomRow][randomCol + 1] = ELEMENT;
				}
				
				else
				{
					this.grid[randomRow][randomCol] = ELEMENT;
				}
			}
			else if(goLeftOrRight == 0)
			{
				if(this.grid[randomRow][randomCol - 1] == EMPTY && this.grid[randomRow][randomCol + 1] == EMPTY)
				{
					this.grid[randomRow][randomCol] = EMPTY;
					this.grid[randomRow][randomCol - 1] = ELEMENT;
				}
				else
				{
					this.grid[randomRow][randomCol] = ELEMENT;
				}
			}
			
				
		}
    }
  }
  
  public void powderPhysics(int ELEMENT, int randomRow, int randomCol)
  {
	  int goLeftOrRight = (int) (Math.random() * 2);

    if(this.grid[randomRow][randomCol] == ELEMENT)
    {
		//Border
		if(randomRow + 1 < grid.length && randomCol + 1 < grid[0].length && randomCol - 1 >= 0 && randomRow -1 >= 0) 
		{
			//fall if empty below
			if(this.grid[randomRow + 1][randomCol] == EMPTY)
			{
				
				this.grid[randomRow][randomCol] = EMPTY;
				this.grid[randomRow + 1][randomCol] = ELEMENT;
			}

			else if(goLeftOrRight == 1)
			{
				if(this.grid[randomRow][randomCol - 1] == EMPTY && this.grid[randomRow][randomCol + 1] == EMPTY )
				{
					this.grid[randomRow][randomCol] = EMPTY;
					this.grid[randomRow][randomCol + 1] = ELEMENT;
				}
				
				else
				{
					this.grid[randomRow][randomCol] = ELEMENT;
				}
			}
			else if(goLeftOrRight == 0)
			{
				if(this.grid[randomRow][randomCol - 1] == EMPTY && this.grid[randomRow][randomCol + 1] == EMPTY)
				{
					this.grid[randomRow][randomCol] = EMPTY;
					this.grid[randomRow][randomCol - 1] = ELEMENT;
				}
				else
				{
					this.grid[randomRow][randomCol] = ELEMENT;
				}
			}
			
				
		}
    }
}
   
	  

  
  public void waterPhysics(final int ELEMENT, int randomRow, int randomCol)
  {
	  //MOVEMENT
	  int goLeftOrRight = (int) (Math.random() * 2);
	  if(this.grid[randomRow][randomCol] == ELEMENT)
	    {
		  if(randomRow + 1 < grid.length && randomCol + 1 < grid[0].length && randomCol - 1 >= 0 && randomRow -1 >= 0)
		  {
	    	//bottom border and space below is empty
	    	if(grid[randomRow + 1][randomCol] == EMPTY)
	    	{
	    		//switch empty with element
	    		this.grid[randomRow][randomCol] = EMPTY;
	    		this.grid[randomRow + 1][randomCol] = ELEMENT;
	    	}
	    	//left right and bottom border
	    	else if(randomRow + 1 < grid.length && randomCol >= 0 && randomCol < grid.length)
	    	{
	    		if(goLeftOrRight == 0 && randomCol + 1 < grid.length && randomCol - 1 >= 0)
	    		{
		    		//if random is less than grid size and right is empty
			    	if(randomCol + 1 < grid.length && grid[randomRow][randomCol + 1] == EMPTY )
			    	{
			    		//if bottom is the same element move right and if right is less than grid size
						if(randomCol + 1 < grid.length && randomRow + 1 < grid.length && grid[randomRow + 1][randomCol] != EMPTY)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol + 1] = ELEMENT;
						}
						//if bottom is greater than grid size move right
						else if(randomCol + 1 >= 0 && randomRow + 1 < grid.length && grid[randomRow + 1][randomCol] > grid.length)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol + 1] = ELEMENT;
						}
			    	}
	    		}
		    	
		    	
	    		else if(goLeftOrRight == 1 && randomCol - 1 >= 0 && randomCol + 1 < grid.length)
	    		{
	    			//if random is greater than 0 and grid left is empty and left is not element
			    	if (randomCol - 1 >= 0 && grid[randomRow][randomCol - 1] == EMPTY)
			    	{
			    		//if bottom is the same move left
						if(randomCol - 1 >= 0 && randomRow - 1 < grid.length && grid[randomRow + 1][randomCol] != EMPTY)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol - 1] = ELEMENT;
						}
						//if bottom is greater than grid size move left
						else if(randomCol - 1 >= 0 && randomRow - 1 < grid.length && grid[randomRow + 1][randomCol] > grid.length)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol - 1] = ELEMENT;
						}
						
			    	}
	    		}
	    	}

	    }
	    }
	  
  }
  
  public void saltMixPhysics(int ELEMENT, int randomRow, int randomCol)
  {
	  if(this.grid[randomRow][randomCol] == ELEMENT)
	  	{
		  if(randomRow + 1 < grid.length && randomRow -1 >= 0)
		  {
			  if(randomCol + 1 < grid[0].length && randomCol - 1 >= 0)
			  {
				  if(this.grid[randomRow][randomCol + 1] == SALT)
				  {
					this.grid[randomRow][randomCol + 1] = EMPTY;
					this.grid[randomRow][randomCol] = SWATER;
					
				  } 
				  else if(this.grid[randomRow][randomCol - 1] == SALT)
				  {
					this.grid[randomRow][randomCol - 1] = EMPTY;
					this.grid[randomRow][randomCol] = SWATER;
				  }
				  else if(this.grid[randomRow + 1][randomCol] == SALT)
				  {
					this.grid[randomRow + 1][randomCol] = EMPTY;
					this.grid[randomRow][randomCol] = SWATER;
				  }
				  else if(this.grid[randomRow - 1][randomCol] == SALT)
				  {
					this.grid[randomRow - 1][randomCol] = EMPTY;
					this.grid[randomRow][randomCol] = SWATER;
				  }
			  }
		  }
		  
	  	}
  }
  
  public void gasPhysics(int ELEMENT, int randomRow, int randomCol)
  {
	  int goLeftOrRight = (int) (Math.random() * 2);
	  if(this.grid[randomRow][randomCol] == ELEMENT)
	    {
	    	//upper border and space above is empty
		  if(randomRow + 1 < grid.length && randomCol + 1 < grid[0].length && randomCol - 1 >= 0 && randomRow -1 >= 0)
	    	{
	    		//switch empty with element
	    		this.grid[randomRow][randomCol] = EMPTY;
	    		this.grid[randomRow - 1][randomCol] = ELEMENT;
	    	}
	    	//left right and bottom border
	    	else if(randomRow + 1 < grid.length && randomCol >= 0 && randomCol < grid.length)
	    	{
	    		if(goLeftOrRight == 0 && randomCol + 1 < grid.length && randomCol - 1 >= 0)
	    		{
		    		//if random is less than grid size and right is empty
			    	if(randomCol + 1 < grid.length && grid[randomRow][randomCol + 1] == EMPTY )
			    	{
			    		//if bottom is the same element move right and if right is less than grid size
						if(randomCol + 1 < grid.length && randomRow + 1 < grid.length && grid[randomRow + 1][randomCol] != EMPTY)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol + 1] = ELEMENT;
						}
						//if bottom is greater than grid size move right
						else if(randomCol + 1 >= 0 && randomRow + 1 < grid.length && grid[randomRow + 1][randomCol] > grid.length)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol + 1] = ELEMENT;
						}
			    	}
	    		}
		    	
		    	
	    		else if(goLeftOrRight == 1 && randomCol - 1 >= 0 && randomCol + 1 < grid.length)
	    		{
	    			//if random is greater than 0 and grid left is empty and left is not element
			    	if (randomCol - 1 >= 0 && grid[randomRow][randomCol - 1] == EMPTY)
			    	{
			    		//if bottom is the same move left
						if(randomCol - 1 >= 0 && randomRow - 1 < grid.length && grid[randomRow + 1][randomCol] != EMPTY)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol - 1] = ELEMENT;
						}
						//if bottom is greater than grid size move left
						else if(randomCol - 1 >= 0 && randomRow - 1 < grid.length && grid[randomRow + 1][randomCol] > grid.length)
						{
							this.grid[randomRow][randomCol] = EMPTY;
							this.grid[randomRow][randomCol - 1] = ELEMENT;
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
