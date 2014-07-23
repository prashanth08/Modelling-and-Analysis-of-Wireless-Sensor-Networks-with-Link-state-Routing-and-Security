//PointCollection.java

import java.lang.*;
import java.io.*;
import java.util.*;

public class PointCollection
{
	ArrayList Points;
	Point Centroid;
	
	//constructor
	public PointCollection()
	{
		Points=new ArrayList();
		Centroid=new Point();
	}
	
	//get functions
	public int get_nPoints()
	{
		return(Points.size());
	}
	
	public Point getPoint(int tIndex)
	{
		Point tPoint=new Point();
		
		if(tIndex>=0&&tIndex<=Points.size()-1)
		{
			tPoint=(Point)Points.get(tIndex);
		}
		
		return(tPoint);
	}
	
	public Point getCentroid()
	{
		return(Centroid);
	}
	
	//set functions
	public void setPoint(int tIndex,Point tPoint)
	{
		if(tIndex>=0&&tIndex<=Points.size()-1)
		{
			Points.set(tIndex,tPoint);
			updateCentroid();
		}
	}
	
	//methods
	public void addPoint(Point tPoint)
	{
		Point pt1=new Point(tPoint.getx(),tPoint.gety(),tPoint.getid());
		Points.add(pt1);
		updateCentroid();
	}
	
	public Point removePoint(int tIndex)
	{
		Point pt1=(Point)Points.get(tIndex);
		Point tPoint=new Point(pt1.getx(),pt1.gety(),pt1.getid());
		Points.remove(tIndex);
		updateCentroid();
		return(tPoint);
	}
	
	public Point removePoint(Point tPoint)
	{
		Point pt2=new Point();
		
		for(int t=0;t<=Points.size()-1;t++)
		{
			if(tPoint.isEquals(getPoint(t))==true)
			{
				Point pt1=(Point)Points.get(t);
				pt2=new Point(pt1.getx(),pt1.gety(),pt1.getid());
				removePoint(t);
				break;
			}
		}
		
		return(pt2);
	}
	
	public void updateCentroid()
	{
		double xsum=0.0;
		double ysum=0.0;
		for(int t=0;t<=Points.size()-1;t++)
		{
			xsum+=((Point)Points.get(t)).getx();
			ysum+=((Point)Points.get(t)).gety();
		}
		Centroid.setx(xsum/(double)Points.size());
		Centroid.sety(ysum/(double)Points.size());
	}
	
	public Point getPointNearestToCentroid()
	{
		double minDistance=0.0;
		int tPointID=-1;
		
		for(int t=0;t<=Points.size()-1;t++)
		{
			Point pt1=(Point)Points.get(t);
			double d=Point.getDistance(pt1,Centroid);
			
			if(t==0)
			{
				minDistance=d;
				tPointID=t;
			}
			else
			{
				if(minDistance>d)
				{
					minDistance=d;
					tPointID=t;
				}
			}
		}
		
		Point pt2=(Point)Points.get(tPointID);
		return(pt2);
	}
	
	public Point getPointFarthestFromCentroid()
	{
		double maxDistance=0.0;
		int tPointID=-1;
		
		for(int t=0;t<=Points.size()-1;t++)
		{
			Point pt1=(Point)Points.get(t);
			double d=Point.getDistance(pt1,Centroid);
			
			if(t==0)
			{
				maxDistance=d;
				tPointID=t;
			}
			else
			{
				if(maxDistance<d)
				{
					maxDistance=d;
					tPointID=t;
				}
			}
		}
		
		Point pt2=(Point)Points.get(tPointID);
		return(pt2);
	}
	
	public String toString()
	{
		String tStr="";
		
		for(int t=0;t<=Points.size()-1;t++)
		{
			tStr+=((Point)Points.get(t)).toString()+"\n";
		}
		
		return(tStr);
	}
}
