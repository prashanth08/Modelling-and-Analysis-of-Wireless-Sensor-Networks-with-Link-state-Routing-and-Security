//Node.java

import java.lang.*;
import java.io.*;

class Node
{
	int ID;
	int X;
	int Y;
	
	//constructors
	Node()
	{
		ID=0;
		X=0;
		Y=0;
	}
	
	Node(int tX,int tY)
	{
		ID=Globals.CurrentNodeID;
		X=tX;
		Y=tY;
		Globals.CurrentNodeID++;
	}
	
	//get functions
	public int getID()
	{
		return(ID);
	}
	
	public int getX()
	{
		return(X);
	}
	
	public int getY()
	{
		return(Y);
	}
	
	//set functions
	public void setX(int tX)
	{
		X=tX;
	}
	
	public void setY(int tY)
	{
		Y=tY;
	}
}
