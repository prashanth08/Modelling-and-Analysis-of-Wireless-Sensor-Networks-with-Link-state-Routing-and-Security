//Globals.java

import java.lang.*;
import java.io.*;

class Globals
{
	public static int BitLength=128;
	
	public static int CurrentNodeID=0;
	public static int CellWidth=45; //cell-rectangle width
	public static int CellHeight=65; //cell-rectangle height
	public static int StartX=70; //firstcell's x
	public static int StartY=115; //firstcell's y
	public static int NodeRadius=5;

	//delays
	public static int NodesAddDelay=5;
	public static int PathDrawDelay=400;
	public static int AuthenticationDelay=100;
	
	//global methods
	public static void wait(int delay)
	{
		try
		{
			Thread.sleep(delay);
		}
		catch(Exception err)
		{
			System.out.println("Error: "+err);
			System.exit(-1);
		}
	}
}
