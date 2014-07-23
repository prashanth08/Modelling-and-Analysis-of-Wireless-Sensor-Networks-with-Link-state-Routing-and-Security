//SecureRouting.java

import java.io.*;
import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class SecureRouting extends JFrame implements ActionListener,MouseMotionListener,MouseListener
{
	//ui
	String strTitle="Secure Routing";
	JFrame frmMain=new JFrame("WSN privacy Secure Routing");
	JLabel lblXY=new JLabel("");
	JLabel lblNodeCount=new JLabel("");
	JLabel lblCurrentNode=new JLabel("");
	JTextArea txtAdjacencyList=new JTextArea("");
	JScrollPane spAdjacencyList=new JScrollPane(txtAdjacencyList);
	JTextArea txtInput=new JTextArea("");
	JScrollPane spInput=new JScrollPane(txtInput);
	JButton btAddNodes=new JButton("Add Nodes");
	JButton btInitialize=new JButton("Initialize");
	JButton btBasicRouting=new JButton("Basic Routing");
	JButton btSecureRouting=new JButton("Secure Routing");
	JTextArea txtResult=new JTextArea("");
	JScrollPane spResult=new JScrollPane(txtResult);
	
	//ui parameters
	int frmLeft=80,frmTop=60;
	int frmWidth=800,frmHeight=600;
	int mapWidth=frmWidth-260,mapHeight=frmHeight-100;
	int mapStartX=30,mapStartY=60;
	
	//system parameters
	int CurrentNode=-1;
	int nNodes,nNodesPerCell=6;
	int TransmissionRange=50;
	
	int SourceNode=-1;
	int DestNode=-1;
	boolean sourceSelected=false;
	int DistanceTraveled=0;
	int HopsTraveled=0;
	double authenticationDelayDH=0;
	double authenticationDelayRSA=0;
	int authenticationMethod=-1;
	String AuthTypes[]={"IRL"};
	
	Map map=new Map();
	Cell[] MapCells=map.getMapCells();
	Graphics g;
	
	//constructor
	SecureRouting()
	{
		//get user-paramters
		Object tobj1=JOptionPane.showInputDialog("Enter NodeCount Per Cell: ",""+nNodesPerCell);
		if(tobj1 instanceof String==false) System.exit(-1);
		String strCount=String.valueOf(tobj1);
		nNodesPerCell=Integer.parseInt(strCount);
		
		Object tobj2=JOptionPane.showInputDialog(null,"Select Authentication Method:",strTitle,JOptionPane.QUESTION_MESSAGE,null,AuthTypes,AuthTypes[0]);
		if(tobj2 instanceof String==false) System.exit(-1);
		String strMethod=String.valueOf(tobj2);
		if(strMethod.equals("IRL")==true) authenticationMethod=0;
		else if(strMethod.equals("RVM")==true) authenticationMethod=1;
		
		//setup frmMain
		frmMain.setResizable(false);
		frmMain.setBounds(frmLeft,frmTop,frmWidth,frmHeight);
		frmMain.getContentPane().setLayout(null);
		frmMain.getContentPane().setBackground(new Color(170,150,180));
		frmMain.setLocationRelativeTo(null);
		frmMain.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frmMain.addMouseMotionListener(this);
		frmMain.addMouseListener(this);
		
		lblXY.setBounds(30,10,100,20);
		frmMain.getContentPane().add(lblXY);
		lblNodeCount.setBounds(30,frmHeight-65,150,20);
		frmMain.getContentPane().add(lblNodeCount);
		lblCurrentNode.setBounds(frmWidth-330,frmHeight-65,110,20);
		frmMain.getContentPane().add(lblCurrentNode);
		
		txtAdjacencyList.setEditable(false);
		spAdjacencyList.setBounds(frmWidth-180-40,30,200,80);
		spAdjacencyList.setColumnHeaderView(new JLabel("Adjacency List"));
		txtAdjacencyList.append(map.findAdjListString());
		frmMain.getContentPane().add(spAdjacencyList);
		
		txtInput.setEditable(false);
		spInput.setBounds(frmWidth-180-40,120,200,60);
		spInput.setColumnHeaderView(new JLabel("Input"));
		frmMain.getContentPane().add(spInput);
		
		btAddNodes.setBounds(frmWidth-180-40,190,200,22);
		btAddNodes.addActionListener(this);
		frmMain.getContentPane().add(btAddNodes);
		
		btInitialize.setBounds(frmWidth-180-40,220,200,22);
		btInitialize.addActionListener(this);
		frmMain.getContentPane().add(btInitialize);
		
		btBasicRouting.setBounds(frmWidth-180-40,250,200,22);
		btBasicRouting.addActionListener(this);
		frmMain.getContentPane().add(btBasicRouting);
		
		btSecureRouting.setBounds(frmWidth-180-40,280,200,22);
		btSecureRouting.addActionListener(this);
		frmMain.getContentPane().add(btSecureRouting);
		
		txtResult.setEditable(false);
		spResult.setBounds(frmWidth-180-40,310,200,180);
		spResult.setColumnHeaderView(new JLabel("Result"));
		frmMain.getContentPane().add(spResult);
		
		frmMain.setVisible(true);
		Globals.wait(1000);
		
		g=frmMain.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(new BasicStroke(2.0f));
		g2d.drawRect(mapStartX,mapStartY,mapWidth,mapHeight);
		map.draw(g);
	}

	//events
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource()==btAddNodes)
		{
			addRandomNodes();
			map.draw(g);
			drawLegends();
		}
		else if(evt.getSource()==btInitialize)
		{
			findClusterHeads();
			findGateWays();
			map.draw(g);
		}
		else if(evt.getSource()==btBasicRouting)
		{
			if(SourceNode!=-1&&DestNode!=-1)
			{
				long tstart=System.currentTimeMillis();
				findRoute(SourceNode,DestNode);
				long tend=System.currentTimeMillis();
				double tseconds=((double)(tend-tstart)/(double)1000);
				txtResult.append("Time: "+tseconds+"\r\n\r\n");
			}
		}
		else if(evt.getSource()==btSecureRouting)
		{
			if(SourceNode!=-1&&DestNode!=-1)
			{
				if(authenticationMethod==0)
				{
					authenticationDelayDH=0.0;
					long tstart=System.currentTimeMillis();
					findSecureRoute(SourceNode,DestNode);
					long tend=System.currentTimeMillis();
					double tseconds=((double)(tend-tstart)/(double)1000);
					txtResult.append("Time: "+(tseconds-authenticationDelayDH)+"\r\n");
					txtResult.append("Total Authentication Delay [IRL]: "+authenticationDelayDH+"\r\n\r\n");
				}
				else if(authenticationMethod==1)
				{
					authenticationDelayRSA=0.0;
					long tstart=System.currentTimeMillis();
					findSecureRoute(SourceNode,DestNode);
					long tend=System.currentTimeMillis();
					double tseconds=((double)(tend-tstart)/(double)1000);
					txtResult.append("Time: "+(tseconds-authenticationDelayRSA)+"\r\n");
					txtResult.append("Total Authentication Delay [RVM]: "+authenticationDelayRSA+"\r\n\r\n");
				}
			}
		}
	}
	
	//mouse motion listener events
	public void mouseDragged(MouseEvent me)
	{
		//
	}
	
	public void mouseMoved(MouseEvent me)
	{
		int xposition=me.getX();
		int yposition=me.getY();
		
		if(between(xposition,yposition,mapStartX,mapStartY,mapStartX+mapWidth,mapStartY+mapHeight)==true)
		{
			lblXY.setText("("+(xposition-mapStartX)+","+(yposition-mapStartY)+")");
		}
		
		int n=map.findTotalNodesCount();
		int trad=Globals.NodeRadius-2;
		
		//display currentnode
		boolean flag=false;
		for(int i=0;i<map.getCellCount();i++)
		{
			for(int j=0;j<MapCells[i].getNodeCount();j++)
			{
				int x1=MapCells[i].getNodeX(j)-trad+2;
				int x2=MapCells[i].getNodeX(j)+trad+2;
				int y1=MapCells[i].getNodeY(j)-trad+2;
				int y2=MapCells[i].getNodeY(j)+trad+2;

				if(between(xposition,yposition,x1,y1,x2,y2)==true)
				{
					flag=true;
					CurrentNode=MapCells[i].getNodeID(j);
				}
			}
		}

		//make sure nodeid is displayed only when mouse is over it
		if(flag==false)
		{
			CurrentNode=-1;
		}
		displayStatus();
	}
	
	//mouselistener methods
	public void mouseClicked(MouseEvent me)
	{
		if(CurrentNode>=0)
		{
			if(sourceSelected==false)
			{
				SourceNode=CurrentNode;
				sourceSelected=true;
			}
			else
			{
				if(CurrentNode!=SourceNode)
				{
					DestNode=CurrentNode;
					sourceSelected=false;
				}
			}
			displayStatus();
		}
	}
	
	public void mouseEntered(MouseEvent me)
	{
		//
	}
	
	public void mouseExited(MouseEvent me)
	{
		//
	}
	
	public void mousePressed(MouseEvent me)
	{
		//
	}
	
	public void mouseReleased(MouseEvent me)
	{
		//
	}
	
	//internal methods
	private void displayStatus()
	{
		lblNodeCount.setText("NodeCount: "+map.findTotalNodesCount());
		lblCurrentNode.setText("Current Node: "+CurrentNode);
		String tstr="SourceNode: "+SourceNode+"\nDestNode: "+DestNode;
		txtInput.setText(tstr);
	}

	private int findRandom(int max)
	{
		double trandom=java.lang.Math.random();
		int ans=(int)(max*trandom);
		return(ans);
	}
	
	private boolean between(int x,int y,int x1,int y1,int x2,int y2)
	{
		boolean flag=false;
		
		if(x>=x1 && x<=x2)
		{
			if(y>=y1 && y<=y2)
			{
				flag=true;
			}
		}
		
		return(flag);
	}
	
	//methods
	void addRandomNodes()
	{
		for(int t=0;t<map.getCellCount();t++)
		{
			for(int i=0;i<nNodesPerCell;i++)
			{
				try
				{
					int no=t;
					
					//find random x,y
					double random1=java.lang.Math.random();
					int no1=(int)(((double)Globals.CellWidth-5.0)*random1);
					double random2=java.lang.Math.random();
					int no2=(int)(((double)Globals.CellHeight-5.0)*random2);
					int xposition=MapCells[no].getX()+no1;
					int yposition=MapCells[no].getY()+no2;
					MapCells[no].addNode(xposition,yposition);
					nNodes+=1;
					Globals.wait(Globals.NodesAddDelay);
				}
				catch(Exception ex)
				{
					System.out.println("Error: "+ex.getMessage());
				}
			}
		}
	}
	
	//routing functions
	int findNodeX(int tNodeID)
	{
		int tX=MapCells[map.findCellNoOfNode(tNodeID)].getNodeX(map.findNodeIndexInCell(tNodeID));
		return(tX);
	}
	
	int findNodeY(int tNodeID)
	{
		int tY=MapCells[map.findCellNoOfNode(tNodeID)].getNodeY(map.findNodeIndexInCell(tNodeID));
		return(tY);
	}
	
	int findDistance(int x1,int y1,int x2,int y2)
	{
		double d=Math.sqrt(Math.pow((double)(x2-x1),2.0)+Math.pow((double)(y2-y1),2.0));
		return((int)d);
	}
	
	int findNodeDistance(int tNodeID1,int tNodeID2)
	{
		int x1=findNodeX(tNodeID1);
		int y1=findNodeY(tNodeID1);
		int x2=findNodeX(tNodeID2);
		int y2=findNodeY(tNodeID2);
		double tdistance=findDistance(x1,y1,x2,y2);
		return((int)tdistance);
	}
	
	void drawPath(int tindex1,int tindex2)
	{
		int x=findNodeX(tindex1);
		int y=findNodeY(tindex1);
		int x1=findNodeX(tindex2);
		int y1=findNodeY(tindex2);
		g.drawLine(x,y,x1,y1);
	}

	void findRoute(int tSourceNode,int tDestNode)
	{
		if(tSourceNode==tDestNode) return;
		
		int x[],y[],distances[][];
		int n=map.findTotalNodesCount();
		x=new int[n];
		y=new int[n];
		distances=new int[n][n];
		int x1,x2,y1,y2;

		for(int i=0;i<n;i++)
		{
			x[i]=findNodeX(i);
			y[i]=findNodeY(i);
		}
		
		//find euclidean distance between each node and every other node (routing table)
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				x1=x[i]; x2=x[j];
				y1=y[i]; y2=y[j];
				
				distances[i][j]=findDistance(x1,y1,x2,y2);
			}
		}
		
		int tsource=tSourceNode;
		int tdestination=tDestNode;
		
		int neighbors[]=new int[n];
		int neighborCount=0;
		
		int path[]=new int[n];
		path[0]=tsource;
		int pathCount=1;
		
		boolean destReached=false;
		
		while(destReached==false)
		{
			//find neighbor nodes that are within the transmission range
			neighborCount=0;
			for(int i=0;i<n;i++)
			{
				if(distances[tsource][i]<=TransmissionRange)
				{
					if(i!=tsource) //to ensure source itself is not taken as a neighbor
					{
						//find if this neighbor is already in path
						boolean found=false;
						for(int k=0;k<pathCount;k++)
						{
							if(path[k]==i)
							{
								found=true;
								break;
							}
						}
						
						if(found==false)
						{
							neighbors[neighborCount]=i;
							neighborCount++;
						}
					}
				}
			}

			if(neighborCount>0)
			{
				//check if DestNode reached
				for(int i=0;i<neighborCount;i++)
				{
					if(neighbors[i]==tDestNode)
					{
						path[pathCount]=tDestNode;
						pathCount++;
						destReached=true;
						break;
					}
				}
				if(destReached==true)
				{
					break;
				}
				
				//find the next-node as neighbor-node with shortest distance to the target node
				int nextNode=-1;
				int tnext=0,tdist,shortestDist=0;
				for(int i=0;i<neighborCount;i++)
				{
					int tnode=neighbors[i];

					x1=x[tnode]; x2=x[tDestNode];
					y1=y[tnode]; y2=y[tDestNode];
					
					tdist=findDistance(x1,y1,x2,y2);
					
					if(i==0)
					{
						shortestDist=tdist;
						tnext=tnode;
					}
					else if(shortestDist>tdist)
					{
						shortestDist=tdist;
						tnext=tnode;
					}
				}
				nextNode=tnext;
				
				path[pathCount]=nextNode;
				pathCount++;
				tsource=nextNode;
			}
			else
			{
				//if no neighbor found inside the transmission range
				break;
			}
		}
		
		//display path
		if(pathCount>0)
		{
			String tstr="Path: ";
			int node1,node2;
			int distanceTraveled=0;
			for(int t=0;t<pathCount;t++)
			{
				tstr+=path[t]+"-";
				
				if(t>0)
				{
					//draw path
					node1=path[t-1];
					node2=path[t];
					distanceTraveled+=findNodeDistance(node1,node2);
					g.setColor(new Color(128,0,0));
					drawPath(node1,node2);
				}
				
				Globals.wait(Globals.PathDrawDelay);
			}
			tstr=tstr.substring(0,tstr.length()-1);
			
			if(destReached==false) tstr=tstr+" failed\r\n";
			else tstr=tstr+" success\r\n";
			
			DistanceTraveled=distanceTraveled;
			HopsTraveled=pathCount-1;
			
			tstr+="Distance: "+DistanceTraveled+", Hops: "+HopsTraveled+"\r\n";
			txtResult.append(tstr);
		}
		
		displayStatus();
	}
	
	//secure routing functions
	private void findClusterHeads()
	{
		for(int i=0;i<map.getCellCount();i++)
		{
			PointCollection cluster=new PointCollection();
			for(int t=0;t<MapCells[i].getNodeCount();t++)
			{
				Point pt1=new Point();
				pt1.setx(MapCells[i].getNodeX(t));
				pt1.sety(MapCells[i].getNodeY(t));
				pt1.setid(MapCells[i].getNodeID(t));
				cluster.addPoint(pt1);
			}
			
			//self-organizing cluster head identification
			//set cluster head as node nearest to this group's centroid
			Point CH=cluster.getPointNearestToCentroid();
			int chid=CH.getid();
			int tindex=MapCells[i].findIndexByID(chid);
			MapCells[i].setClusterHead(tindex);
		}
	}
	
	private void findGateWays()
	{
		for(int i=0;i<map.getCellCount();i++)
		{
			PointCollection cluster=new PointCollection();
			for(int t=0;t<MapCells[i].getNodeCount();t++)
			{
				Point pt1=new Point();
				pt1.setx(MapCells[i].getNodeX(t));
				pt1.sety(MapCells[i].getNodeY(t));
				pt1.setid(MapCells[i].getNodeID(t));
				cluster.addPoint(pt1);
			}
			
			//self-organizing gateway identification
			//set gateway as node which is far away from centroid
			Point CH=cluster.getPointFarthestFromCentroid();
			int chid=CH.getid();
			int tindex=MapCells[i].findIndexByID(chid);
			MapCells[i].setGateWay(tindex);
		}
	}
	
	private void drawLegends()
	{
		g.setColor(new Color(0,128,0));
		g.drawOval(frmWidth-180-40+10,533,Globals.NodeRadius,Globals.NodeRadius);
		g.drawString("Node Privacy",frmWidth-180-40+20,540);
		g.setColor(new Color(128,0,0));
		g.drawOval(frmWidth-180-40+10,553,Globals.NodeRadius,Globals.NodeRadius);
		g.drawString("GateWay",frmWidth-180-40+20,560);
		g.setColor(new Color(255,0,0));
		g.drawOval(frmWidth-180-40+10,573,Globals.NodeRadius,Globals.NodeRadius);
		g.drawString("Other Node",frmWidth-180-40+20,580);
		g.setColor(new Color(128,128,128));
		g.drawLine(frmWidth-100,533,frmWidth-100+20,533);
		g.drawString("Route Privacy",frmWidth-100,548);
	}
	
	private void findSecureRoute(int tSourceNode,int tDestNode)
	{
		if(tSourceNode==tDestNode) return;
		
		int x[],y[],distances[][];
		int n=map.findTotalNodesCount();
		x=new int[n];
		y=new int[n];
		distances=new int[n][n];
		int x1,x2,y1,y2;

		for(int i=0;i<n;i++)
		{
			x[i]=findNodeX(i);
			y[i]=findNodeY(i);
		}
		
		//find euclidean distance between each node and every other node (routing table)
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				x1=x[i]; x2=x[j];
				y1=y[i]; y2=y[j];
				
				distances[i][j]=findDistance(x1,y1,x2,y2);
			}
		}
		
		int tsource=tSourceNode;
		int tdestination=tDestNode;
		
		int neighbors[]=new int[n];
		int neighborCount=0;
		
		int path[]=new int[n];
		path[0]=tsource;
		int pathCount=1;
		
		boolean destReached=false;
		while(destReached==false)
		{
			//find neighbor nodes that are within the transmission range
			neighborCount=0;
			for(int i=0;i<n;i++)
			{
				if(distances[tsource][i]<=TransmissionRange)
				{
					if(i!=tsource) //to ensure source itself is not taken as a neighbor
					{
						//find if this neighbor is already in path
						boolean found=false;
						for(int k=0;k<pathCount;k++)
						{
							if(path[k]==i)
							{
								found=true;
								break;
							}
						}
						
						if(found==false)
						{
							neighbors[neighborCount]=i;
							neighborCount++;
							
							if(authenticationMethod==0)
							{
								long tstart=System.currentTimeMillis();
								Authentication kdc=new Authentication();
								kdc.initializeKA();
								kdc.generateKA();
								String encrypted=kdc.Encrypt(""+neighbors[neighborCount]);
								long tend=System.currentTimeMillis();
								double tseconds=((double)(tend-tstart)/(double)1000);
								authenticationDelayDH+=tseconds;
							}
							else if(authenticationMethod==1)
							{
								long tstart=System.currentTimeMillis();
								RSA kdc=new RSA();
								kdc.initializeKA();
								String encrypted=kdc.encrypt(""+neighbors[neighborCount]);
								long tend=System.currentTimeMillis();
								double tseconds=((double)(tend-tstart)/(double)1000);
								authenticationDelayRSA+=tseconds;
							}
						}
					}
				}
			}

			if(neighborCount>0)
			{
				//check if DestNode reached
				for(int i=0;i<neighborCount;i++)
				{
					if(neighbors[i]==tDestNode)
					{
						path[pathCount]=tDestNode;
						pathCount++;
						destReached=true;
						break;
					}
				}
				if(destReached==true)
				{
					break;
				}
				
				//find the next-node as neighbor-node with shortest distance to the target node
				int nextNode=-1;
				int tnext=0,tdist,shortestDist=0;
				for(int i=0;i<neighborCount;i++)
				{
					int tnode=neighbors[i];

					x1=x[tnode]; x2=x[tDestNode];
					y1=y[tnode]; y2=y[tDestNode];
					
					tdist=findDistance(x1,y1,x2,y2);
					
					if(i==0)
					{
						shortestDist=tdist;
						tnext=tnode;
					}
					else if(shortestDist>tdist)
					{
						shortestDist=tdist;
						tnext=tnode;
					}
				}
				nextNode=tnext;
				
				path[pathCount]=nextNode;
				pathCount++;
				tsource=nextNode;
			}
			else
			{
				//if no neighbor found inside the transmission range
				break;
			}
		}
		
		//display path
		if(pathCount>0)
		{
			String tstr="Path: ";
			int node1,node2;
			int distanceTraveled=0;
			for(int t=0;t<pathCount;t++)
			{
				tstr+=path[t]+"-";
				
				if(t>0)
				{
					//draw path
					node1=path[t-1];
					node2=path[t];
					distanceTraveled+=findNodeDistance(node1,node2);
					g.setColor(new Color(0,128,0));
					drawPath(node1,node2);
					
					//authenticate with its cluster head
					int tcellid=map.findCellNoOfNode(node2);
					int ch_index=MapCells[tcellid].getClusterHead();
					int chid=MapCells[tcellid].getNodeID(ch_index);
					g.setColor(new Color(128,128,128));
					drawPath(node2,chid);
				}
				
				Globals.wait(Globals.PathDrawDelay);
			}
			tstr=tstr.substring(0,tstr.length()-1);
			
			if(destReached==false) tstr=tstr+" failed\r\n";
			else tstr=tstr+" success\r\n";
			
			DistanceTraveled=distanceTraveled;
			HopsTraveled=pathCount-1;
			
			tstr+="Distance: "+DistanceTraveled+", Hops: "+HopsTraveled+"\r\n";
			txtResult.append(tstr);
		}
		
		displayStatus();
	}
	
	//main
	public static void main(String args[])
	{
		new SecureRouting();
	}
}
