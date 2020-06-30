import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

public class LifeModel implements ActionListener
{

	/*
	 *  This is the Model component.
	 */

	private static int SIZE = 60;
	private LifeCell[][] grid;
	
	LifeView myView;
	Timer timer;

	/** Construct a new model using a particular file */
	public LifeModel(LifeView view, String fileName) throws IOException
	{       
		int r, c;
		grid = new LifeCell[SIZE][SIZE];
		for ( r = 0; r < SIZE; r++ )
			for ( c = 0; c < SIZE; c++ )
				grid[r][c] = new LifeCell();

		if ( fileName == null ) //use random population
		{                                           
			for ( r = 0; r < SIZE; r++ )
			{
				for ( c = 0; c < SIZE; c++ )
				{
					if ( Math.random() > 0.85) //15% chance of a cell starting alive
						grid[r][c].setAliveNow(true);
				}
			}
		}
		else
		{                 
			Scanner input = new Scanner(new File(fileName));
			int numInitialCells = input.nextInt();
			for (int count=0; count<numInitialCells; count++)
			{
				r = input.nextInt();
				c = input.nextInt();
				grid[r][c].setAliveNow(true);
			}
			input.close();
		}

		myView = view;
		myView.updateView(grid);

	}

	/** Constructor a randomized model */
	public LifeModel(LifeView view) throws IOException
	{
		this(view, null);
	}

	/** pause the simulation (the pause button in the GUI */
	public void pause()
	{
		timer.stop();
	}
	
	/** resume the simulation (the pause button in the GUI */
	public void resume()
	{
		timer.restart();
	}
	
	/** run the simulation (the pause button in the GUI */
	public void run()
	{
		timer = new Timer(50, this);
		timer.setCoalesce(true);
		timer.start();
	}

	/** called each time timer fires */
	public void actionPerformed(ActionEvent e)
	{
		oneGeneration();
		myView.updateView(grid);
	}

	/** main logic method for updating the state of the grid / simulation */
	private void oneGeneration()
	{
		/* 
		 * student code here 
		 */
		LifeCell [][] newGrid = grid;
		for (int r = 0; r < grid.length; r++)
		{
			for (int c = 0; c < grid[r].length; c++)
			{
				int neighbors = findNeighbors(r, c);
				
				if (grid[r][c].isAliveNow()) // cell is occupied
				{
					if (neighbors < 2 || neighbors > 3)
					{
						grid[r][c].setAliveNow(false);
					}
//					else
//					{
//						grid[r][c].setAliveNow(true);
//						grid[r][c].setAliveNext(true);
//					}
				}
				else
				{
					if (neighbors == 3)
					{
						grid[r][c].setAliveNext(true);
					}
//					else
//					{
//						grid[r][c].setAliveNow(false);
//						grid[r][c].setAliveNext(false);
//					}
				}
				
			}
		}
	}
	
	public int findNeighbors(int r, int c)
	{
		// goal of this method is to find neighbors for cell defined by the row/col parameters passed
		int countNeighbors = 0;
		
		// ROWS & DIAG
		if (r > 0 && r < grid.length - 1) //check if its within
		{
			if (grid[r + 1][c].isAliveNow()) // is below cell alive
				countNeighbors++;
			if (grid[r - 1][c].isAliveNow()) // is above cell alive
				countNeighbors++;
			
			if (c > 0 && c < grid[0].length - 1)
			{
				if (grid[r + 1][c + 1].isAliveNow())
					countNeighbors++;
				if (grid[r - 1][c + 1].isAliveNow()) 
					countNeighbors++;
				if (grid[r + 1][c - 1].isAliveNow()) 
					countNeighbors++;
				if (grid[r - 1][c - 1].isAliveNow()) 
					countNeighbors++;
				if (grid[r][c - 1].isAliveNow()) // is left cell alive
					countNeighbors++;
				if (grid[r][c + 1].isAliveNow()) // is right cell alive
					countNeighbors++;
			}
			else if (c == 0)
			{
				if (grid[r + 1][c + 1].isAliveNow())
					countNeighbors++;
				if (grid[r - 1][c + 1].isAliveNow())
					countNeighbors++;
				if (grid[r][c + 1].isAliveNow())
					countNeighbors++;
			}
			else
			{
				if (grid[r + 1][c - 1].isAliveNow())
					countNeighbors++;
				if (grid[r - 1][c - 1].isAliveNow())
					countNeighbors++;
				if (grid[r][c - 1].isAliveNow())
					countNeighbors++;
			}
		}
		else if (r == 0) // top row exception
		{
			if (grid[r + 1][c].isAliveNow())
				countNeighbors++;
			
			if (c > 0 && c < grid[0].length - 1)
			{
				if (grid[r + 1][c + 1].isAliveNow())
					countNeighbors++;
//				if (grid[r - 1][c + 1].isAliveNow()) 
//					countNeighbors++;
				if (grid[r + 1][c - 1].isAliveNow()) 
					countNeighbors++;
//				if (grid[r - 1][c - 1].isAliveNow()) 
//					countNeighbors++;
				if (grid[r][c - 1].isAliveNow()) // is left cell alive
					countNeighbors++;
				if (grid[r][c + 1].isAliveNow()) // is right cell alive
					countNeighbors++;
			}
			else if (c == 0)
			{
				if (grid[r + 1][c + 1].isAliveNow())
					countNeighbors++;
//				if (grid[r - 1][c + 1].isAliveNow())
//					countNeighbors++;
				if (grid[r][c + 1].isAliveNow())
					countNeighbors++;
			}
			else
			{
				if (grid[r + 1][c - 1].isAliveNow())
					countNeighbors++;
//				if (grid[r - 1][c - 1].isAliveNow())
//					countNeighbors++;
				if (grid[r][c - 1].isAliveNow())
					countNeighbors++;
			}
		}
		else // bottom row exception
		{
			if (grid[r - 1][c].isAliveNow())
				countNeighbors++;
			
			if (c > 0 && c < grid[0].length - 1)
			{
//				if (grid[r + 1][c + 1].isAliveNow())
//					countNeighbors++;
				if (grid[r - 1][c + 1].isAliveNow()) 
					countNeighbors++;
//				if (grid[r + 1][c - 1].isAliveNow()) 
//					countNeighbors++;
				if (grid[r - 1][c - 1].isAliveNow()) 
					countNeighbors++;
				if (grid[r][c - 1].isAliveNow()) // is left cell alive
					countNeighbors++;
				if (grid[r][c + 1].isAliveNow()) // is right cell alive
					countNeighbors++;
			}
			else if (c == 0)
			{
//				if (grid[r + 1][c + 1].isAliveNow())
//					countNeighbors++;
				if (grid[r - 1][c + 1].isAliveNow())
					countNeighbors++;
				if (grid[r][c + 1].isAliveNow())
					countNeighbors++;
			}
			else
			{
//				if (grid[r + 1][c - 1].isAliveNow())
//					countNeighbors++;
				if (grid[r - 1][c - 1].isAliveNow())
					countNeighbors++;
				if (grid[r][c - 1].isAliveNow())
					countNeighbors++;
			}
		}
		
		// COLS
//		if (c > 0 && c < grid[0].length - 1) //check if its within
//		{
//			if (grid[r][c - 1].isAliveNow()) // is left cell alive
//				countNeighbors++;
//			if (grid[r][c + 1].isAliveNow()) // is right cell alive
//				countNeighbors++;
//		}
//		if (c == 0) // left col exception
//		{
//			if (grid[r][c + 1].isAliveNow())
//				countNeighbors++;
//		}
//		if (c == grid[0].length - 1) // right col exception
//		{
//			if (grid[r][c - 1].isAliveNow())
//				countNeighbors++;
//		}
			
		return countNeighbors;
	}
}

