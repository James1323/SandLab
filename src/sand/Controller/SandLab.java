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
			  if(this.grid[rows][cols] == EMPTY)
			  {
				  display.setColor(rows, cols, Color.black);
			  }
			  //metal
			  else if(this.grid[rows][cols] == METAL)
			  {
				  display.setColor(rows, cols, Color.gray);
			  }
			  //sand
			  else if(this.grid[rows][cols] == SAND)
			  {
				  display.setColor(rows, cols, Color.yellow);
			  }
			  //water
			  else if(this.grid[rows][cols] == WATER)
			  {
				  display.setColor(rows, cols, Color.blue);
			  }
			  //gas
			  else if(this.grid[rows][cols] == GAS )
			  {
				  display.setColor(rows, cols, Color.green);
			  }
			  
		  }
		  
	  }
    
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void phyisics()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    //int someRandom = (int) (Math.random() * scalar)
    //remember that you need to watch for the edges of the array
    int randomRow = (int) (Math.random() * grid.length);
    int randomCol = (int) (Math.random() * grid.length);
    
    
    //falling sand
    //if space is sand
    if(this.grid[randomRow][randomCol] == SAND)
    {
    	//if less than grid size and space below is empty
    	if(randomRow +1 < grid.length && grid[randomRow + 1][randomCol] == EMPTY)
    	{
    		//switch empty with sand
    		this.grid[randomRow][randomCol] = EMPTY;
    		this.grid[randomRow + 1][randomCol] = SAND;
    	}
    }
    //gas movement
    
    
    //water movement
    if(this.grid[randomRow][randomCol] == WATER)
    {
    	//less than grid size on height and width
    	if(randomRow +1 < grid.length && randomCol +1 < grid.length) 
    	{
    		//if space below is empty
    		if( grid[randomRow + 1] [randomCol] == EMPTY)
    		//switch empty with water
    		{
    			this.grid[randomRow][randomCol] = EMPTY;
    			this.grid[randomRow + 1][randomCol] = WATER;
    		}
    		//cant go out of bounds on the width and the width is empty
    		else if (randomCol + 1 < grid[0].length && grid[randomRow][randomCol + 1] == EMPTY)
    		{
    			this.grid[randomRow][randomCol] = EMPTY;
    			this.grid[randomRow][randomCol + 1] = WATER;
    		}
    		//
    		else if (randomCol - 1 >= 0 && grid[randomRow][randomCol] == EMPTY)
    		{
    			grid
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
    	  phyisics();
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
