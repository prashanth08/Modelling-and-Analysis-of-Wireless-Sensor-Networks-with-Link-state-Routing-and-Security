//Point.java

import java.lang.*;
import java.io.*;

public class Point
{
	private double x;
	private double y;
	private int id;
	
	//constructor
	public Point()
	{
		x=0.0;
		y=0.0;
		id=0;
	}
	
	public Point(double tx,double ty)
	{
		x=tx;
		y=ty;
		id=0;
	}
	
	public Point(double tx,double ty,int tid)
	{
		x=tx;
		y=ty;
		id=tid;
	}
	
	//get functions
	public double getx()
	{
		return(x);
	}
	
	public double gety()
	{
		return(y);
	}
	
	public int getid()
	{
		return(id);
	}
	
	//set functions
	public void setx(double tx)
	{
		x=tx;
	}
	
	public void sety(double ty)
	{
		y=ty;
	}
	
	public void setid(int tid)
	{
		id=tid;
	}
	
	//methods
	public static double getDistance(Point pt1,Point pt2)
	{
		double x1=pt1.getx(),y1=pt1.gety();
		double x2=pt2.getx(),y2=pt2.gety();
		
		//find euclidean distance
		double d=Math.sqrt(Math.pow(x2-x1,2.0)+Math.pow(y2-y1,2.0));
		return(d);
	}
	
	public boolean isEquals(Point tPoint)
	{
		boolean flag=false;
		
		if(x==tPoint.getx()&&y==tPoint.gety())
		{
			flag=true;
		}
		
		return(flag);
	}
	
	public boolean isBetween(Point p1,Point p2)
	{
		boolean flag=false;
		
		if(x>=p1.getx()&&x<=p2.getx())
		{
			if(y>=p1.gety()&&y<=p2.gety())
			{
				flag=true;
			}
		}
		
		return(flag);
	}
	
	public String toString()
	{
		String tStr="";
		
		tStr="("+x+","+y+",id: "+id+")";
		
		return(tStr);
	}
}
