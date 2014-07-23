//Clustering.java

import java.lang.*;
import java.io.*;
import java.util.*;

public class Clustering
{
	int nClusters; //number of clusters
	PointCollection Points;
	PointCollection Clusters[];
	
	//constructor
	public Clustering()
	{
		nClusters=0;
		Points=new PointCollection();
	}
	
	//get functions
	public int get_nClusters()
	{
		return(nClusters);
	}
	
	public PointCollection getCluster(int tIndex)
	{
		PointCollection tPointCollection=new PointCollection();
		
		if(tIndex>=0&&tIndex<=nClusters-1)
		{
			tPointCollection=Clusters[tIndex];
		}
		
		return(tPointCollection);
	}
	
	//set functions
	public void set_nClusters(int tnClusters)
	{
		nClusters=tnClusters;
		Clusters=new PointCollection[nClusters];
		for(int t=0;t<=nClusters-1;t++) Clusters[t]=new PointCollection();
	}
	
	public void setPoints(PointCollection tPointCollection)
	{
		Points=tPointCollection;
	}
	
	//methods
	public void FindClusters()
	{
		//initialize: divide points into equal clusters
		int n=Points.get_nPoints();
		int tsum=0;
		int PartitionCounts[]=new int[nClusters];
		int nPartitions=0;
		
		while(true)
		{
			PartitionCounts[nPartitions]=(int)Math.floor((double)n/(double)nClusters);
			tsum+=PartitionCounts[nPartitions];
			nPartitions++;
			if(nPartitions==nClusters-1) break;
		}
		PartitionCounts[nPartitions]=n-tsum;
		nPartitions++;
		
		for(int t=0,i=0;t<nPartitions;t++)
		{
			for(int j=0;j<=PartitionCounts[t]-1;j++)
			{
				Clusters[t].addPoint(Points.getPoint(i));
				i++;
			}
		}
		
		int nMovements=1;
		while(nMovements!=0)
		{
			nMovements=0;
			
			for(int t=0;t<=nClusters-1;t++) //for all clusters
			{
				for(int j=0;j<=Clusters[t].get_nPoints()-1;j++) //for all points in each cluster
				{
					double minDistance=0.0;
					int minCluster=t;
					
					for(int k=0;k<=nClusters-1;k++) //find distance to each cluster
					{
						Point pt1=Clusters[t].getPoint(j);
						Point pt2=Clusters[k].getCentroid();
						double d=Point.getDistance(pt1,pt2);
						if(k==0)
						{
							minDistance=d;
							minCluster=0;
						}
						else
						{
							if(minDistance>d)
							{
								minDistance=d;
								minCluster=k;
							}
						}
					}
					
					if(minCluster!=t) //if point has moved
					{
						Point pt1=Clusters[t].removePoint(j);
						Clusters[minCluster].addPoint(pt1);
						
						nMovements+=1;
					}
				}
			}
		}
	}
	
	public String toString()
	{
		String tStr="";
		tStr+="Input Points:\n"+Points.toString()+"\n";
		
		for(int t=0;t<=nClusters-1;t++)
		{
			tStr+="Cluster"+(t+1)+":\n";
			tStr+=Clusters[t].toString()+"\n";
		}
		
		return(tStr);
	}
}
