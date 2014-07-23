//Map.java

import java.lang.*;
import java.io.*;
import java.awt.*;

class Map
{
	//system parameters
	int CellCount=45;	//number of cells
	Cell MapCells[];	//cells list

	//constructor
	Map()
	{
		MapCells=new Cell[CellCount];
		
		//set cell IDs
		for(int t=0;t<CellCount;t++)
		{
			MapCells[t]=new Cell();
			MapCells[t].setID(t);
		}
	
		//set Adjcent cells
		MapCells[0].addAdjCell(1);	//
		MapCells[0].addAdjCell(4);
		MapCells[0].addAdjCell(3);
		MapCells[1].addAdjCell(0);	//
		MapCells[1].addAdjCell(4);
		MapCells[1].addAdjCell(2);
		MapCells[2].addAdjCell(1);	//
		MapCells[2].addAdjCell(4);
		MapCells[2].addAdjCell(5);
		MapCells[2].addAdjCell(19);
		MapCells[2].addAdjCell(20);
		MapCells[3].addAdjCell(0);	//
		MapCells[3].addAdjCell(4);
		MapCells[3].addAdjCell(7);
		MapCells[3].addAdjCell(6);
		MapCells[4].addAdjCell(0);	//
		MapCells[4].addAdjCell(1);
		MapCells[4].addAdjCell(2);
		MapCells[4].addAdjCell(3);
		MapCells[4].addAdjCell(7);
		MapCells[4].addAdjCell(5);
		MapCells[5].addAdjCell(2);	//
		MapCells[5].addAdjCell(4);
		MapCells[5].addAdjCell(7);
		MapCells[5].addAdjCell(8);
		MapCells[5].addAdjCell(20);
		MapCells[5].addAdjCell(21);
		MapCells[6].addAdjCell(3);	//
		MapCells[6].addAdjCell(7);
		MapCells[6].addAdjCell(9);
		MapCells[6].addAdjCell(10);
		MapCells[7].addAdjCell(3);	//
		MapCells[7].addAdjCell(4);
		MapCells[7].addAdjCell(5);
		MapCells[7].addAdjCell(6);
		MapCells[7].addAdjCell(9);
		MapCells[7].addAdjCell(8);
		MapCells[8].addAdjCell(5);	//
		MapCells[8].addAdjCell(7);
		MapCells[8].addAdjCell(9);
		MapCells[8].addAdjCell(12);
		MapCells[8].addAdjCell(21);
		MapCells[8].addAdjCell(22);
		MapCells[9].addAdjCell(6);	//
		MapCells[9].addAdjCell(7);
		MapCells[9].addAdjCell(8);
		MapCells[9].addAdjCell(10);
		MapCells[9].addAdjCell(11);
		MapCells[9].addAdjCell(12);
		MapCells[10].addAdjCell(6);	//
		MapCells[10].addAdjCell(9);
		MapCells[10].addAdjCell(11);
		MapCells[10].addAdjCell(13);
		MapCells[11].addAdjCell(10);	//
		MapCells[11].addAdjCell(9);
		MapCells[11].addAdjCell(12);
		MapCells[11].addAdjCell(13);
		MapCells[11].addAdjCell(14);
		MapCells[11].addAdjCell(15);
		MapCells[12].addAdjCell(8);	//
		MapCells[12].addAdjCell(9);
		MapCells[12].addAdjCell(11);
		MapCells[12].addAdjCell(15);
		MapCells[12].addAdjCell(22);
		MapCells[12].addAdjCell(23);
		MapCells[13].addAdjCell(10);	//
		MapCells[13].addAdjCell(11);
		MapCells[13].addAdjCell(14);
		MapCells[13].addAdjCell(16);
		MapCells[14].addAdjCell(11);	//
		MapCells[14].addAdjCell(13);
		MapCells[14].addAdjCell(16);
		MapCells[14].addAdjCell(17);
		MapCells[14].addAdjCell(18);
		MapCells[14].addAdjCell(15);
		MapCells[15].addAdjCell(11);	//
		MapCells[15].addAdjCell(12);
		MapCells[15].addAdjCell(14);
		MapCells[15].addAdjCell(18);
		MapCells[15].addAdjCell(23);
		MapCells[15].addAdjCell(24);
		MapCells[16].addAdjCell(13);	//
		MapCells[16].addAdjCell(14);
		MapCells[16].addAdjCell(17);
		MapCells[17].addAdjCell(16);	//
		MapCells[17].addAdjCell(14);
		MapCells[17].addAdjCell(18);
		MapCells[18].addAdjCell(17);	//
		MapCells[18].addAdjCell(14);
		MapCells[18].addAdjCell(15);
		MapCells[18].addAdjCell(24);
		MapCells[18].addAdjCell(25);
		MapCells[19].addAdjCell(2);		//
		MapCells[19].addAdjCell(20);
		MapCells[19].addAdjCell(26);
		MapCells[20].addAdjCell(2);		//
		MapCells[20].addAdjCell(5);
		MapCells[20].addAdjCell(21);
		MapCells[20].addAdjCell(26);
		MapCells[20].addAdjCell(27);
		MapCells[21].addAdjCell(5);		//
		MapCells[21].addAdjCell(8);
		MapCells[21].addAdjCell(22);
		MapCells[21].addAdjCell(28);
		MapCells[21].addAdjCell(27);
		MapCells[21].addAdjCell(20);
		MapCells[22].addAdjCell(8);		//
		MapCells[22].addAdjCell(12);
		MapCells[22].addAdjCell(23);
		MapCells[22].addAdjCell(29);
		MapCells[22].addAdjCell(28);
		MapCells[22].addAdjCell(21);
		MapCells[23].addAdjCell(12);		//
		MapCells[23].addAdjCell(15);
		MapCells[23].addAdjCell(24);
		MapCells[23].addAdjCell(30);
		MapCells[23].addAdjCell(29);
		MapCells[23].addAdjCell(22);
		MapCells[24].addAdjCell(15);		//
		MapCells[24].addAdjCell(18);
		MapCells[24].addAdjCell(25);
		MapCells[24].addAdjCell(31);
		MapCells[24].addAdjCell(30);
		MapCells[24].addAdjCell(23);
		MapCells[25].addAdjCell(18);		//
		MapCells[25].addAdjCell(24);
		MapCells[25].addAdjCell(31);
		MapCells[26].addAdjCell(19);		//
		MapCells[26].addAdjCell(20);
		MapCells[26].addAdjCell(27);
		MapCells[26].addAdjCell(33);
		MapCells[26].addAdjCell(32);
		MapCells[27].addAdjCell(20);		//
		MapCells[27].addAdjCell(21);
		MapCells[27].addAdjCell(28);
		MapCells[27].addAdjCell(34);
		MapCells[27].addAdjCell(33);
		MapCells[27].addAdjCell(26);
		MapCells[28].addAdjCell(21);		//
		MapCells[28].addAdjCell(22);
		MapCells[28].addAdjCell(29);
		MapCells[28].addAdjCell(35);
		MapCells[28].addAdjCell(34);
		MapCells[28].addAdjCell(27);
		MapCells[29].addAdjCell(22);		//
		MapCells[29].addAdjCell(23);
		MapCells[29].addAdjCell(30);
		MapCells[29].addAdjCell(36);
		MapCells[29].addAdjCell(35);
		MapCells[29].addAdjCell(28);
		MapCells[30].addAdjCell(23);		//
		MapCells[30].addAdjCell(24);
		MapCells[30].addAdjCell(31);
		MapCells[30].addAdjCell(37);
		MapCells[30].addAdjCell(36);
		MapCells[30].addAdjCell(29);
		MapCells[31].addAdjCell(24);		//
		MapCells[31].addAdjCell(25);
		MapCells[31].addAdjCell(38);
		MapCells[31].addAdjCell(37);
		MapCells[31].addAdjCell(30);
		MapCells[32].addAdjCell(26);		//
		MapCells[32].addAdjCell(33);
		MapCells[33].addAdjCell(26);		//
		MapCells[33].addAdjCell(27);
		MapCells[33].addAdjCell(34);
		MapCells[33].addAdjCell(32);
		MapCells[34].addAdjCell(33);		//
		MapCells[34].addAdjCell(27);
		MapCells[34].addAdjCell(28);
		MapCells[34].addAdjCell(35);
		MapCells[35].addAdjCell(34);		//
		MapCells[35].addAdjCell(28);
		MapCells[35].addAdjCell(29);
		MapCells[35].addAdjCell(36);
		MapCells[36].addAdjCell(35);		//
		MapCells[36].addAdjCell(29);
		MapCells[36].addAdjCell(30);
		MapCells[36].addAdjCell(37);
		MapCells[37].addAdjCell(36);		//
		MapCells[37].addAdjCell(30);
		MapCells[37].addAdjCell(31);
		MapCells[37].addAdjCell(38);
		MapCells[38].addAdjCell(37);		//
		MapCells[38].addAdjCell(31);
		MapCells[38].addAdjCell(44);
		MapCells[39].addAdjCell(32);		//
		MapCells[39].addAdjCell(33);
		MapCells[39].addAdjCell(40);
		MapCells[40].addAdjCell(39);		//
		MapCells[40].addAdjCell(33);
		MapCells[40].addAdjCell(34);
		MapCells[40].addAdjCell(41);
		MapCells[41].addAdjCell(40);		//
		MapCells[41].addAdjCell(34);
		MapCells[41].addAdjCell(35);
		MapCells[41].addAdjCell(42);
		MapCells[42].addAdjCell(41);		//
		MapCells[42].addAdjCell(35);
		MapCells[42].addAdjCell(36);
		MapCells[42].addAdjCell(43);
		MapCells[43].addAdjCell(42);		//
		MapCells[43].addAdjCell(36);
		MapCells[43].addAdjCell(37);
		MapCells[43].addAdjCell(44);
		MapCells[44].addAdjCell(43);		//
		MapCells[44].addAdjCell(37);
		MapCells[44].addAdjCell(38);
		
		//set cells screen pos
		MapCells[0].setXY(Globals.StartX,Globals.StartY);
		MapCells[1].setXY(Globals.StartX+Globals.CellWidth+Globals.CellWidth/2,Globals.StartY-Globals.CellHeight/2);
		MapCells[2].setXY(Globals.StartX+Globals.CellWidth*3,Globals.StartY);
		MapCells[3].setXY(Globals.StartX,Globals.StartY+Globals.CellHeight);
		MapCells[4].setXY(Globals.StartX+Globals.CellWidth+Globals.CellWidth/2,Globals.StartY+Globals.CellHeight/2);
		MapCells[5].setXY(Globals.StartX+Globals.CellWidth*3,Globals.StartY+Globals.CellHeight);
		MapCells[6].setXY(Globals.StartX,Globals.StartY+Globals.CellHeight*2);
		MapCells[7].setXY(Globals.StartX+Globals.CellWidth+Globals.CellWidth/2,Globals.StartY+Globals.CellHeight+Globals.CellHeight/2);
		MapCells[8].setXY(Globals.StartX+Globals.CellWidth*3,Globals.StartY+Globals.CellHeight*2);
		MapCells[9].setXY(Globals.StartX+Globals.CellWidth+Globals.CellWidth/2,Globals.StartY+Globals.CellHeight*2+Globals.CellHeight/2);
		MapCells[10].setXY(MapCells[6].getX(),MapCells[6].getY()+Globals.CellHeight);
		MapCells[11].setXY(MapCells[9].getX(),MapCells[9].getY()+Globals.CellHeight);
		MapCells[12].setXY(MapCells[8].getX(),MapCells[8].getY()+Globals.CellHeight);
		MapCells[13].setXY(MapCells[10].getX(),MapCells[10].getY()+Globals.CellHeight);
		MapCells[14].setXY(MapCells[11].getX(),MapCells[11].getY()+Globals.CellHeight);
		MapCells[15].setXY(MapCells[12].getX(),MapCells[12].getY()+Globals.CellHeight);
		MapCells[16].setXY(MapCells[13].getX(),MapCells[13].getY()+Globals.CellHeight);
		MapCells[17].setXY(MapCells[14].getX(),MapCells[14].getY()+Globals.CellHeight);
		MapCells[18].setXY(MapCells[15].getX(),MapCells[15].getY()+Globals.CellHeight);
		MapCells[19].setXY(MapCells[2].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[2].getY()-Globals.CellHeight/2);
		MapCells[20].setXY(MapCells[5].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[5].getY()-Globals.CellHeight/2);
		MapCells[21].setXY(MapCells[8].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[8].getY()-Globals.CellHeight/2);
		MapCells[22].setXY(MapCells[12].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[12].getY()-Globals.CellHeight/2);
		MapCells[23].setXY(MapCells[15].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[15].getY()-Globals.CellHeight/2);
		MapCells[24].setXY(MapCells[18].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[18].getY()-Globals.CellHeight/2);
		MapCells[25].setXY(MapCells[24].getX(),MapCells[24].getY()+Globals.CellHeight);
		MapCells[26].setXY(MapCells[20].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[20].getY()-Globals.CellHeight/2);
		MapCells[27].setXY(MapCells[21].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[21].getY()-Globals.CellHeight/2);
		MapCells[28].setXY(MapCells[22].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[22].getY()-Globals.CellHeight/2);
		MapCells[29].setXY(MapCells[23].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[23].getY()-Globals.CellHeight/2);
		MapCells[30].setXY(MapCells[24].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[24].getY()-Globals.CellHeight/2);
		MapCells[31].setXY(MapCells[25].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[25].getY()-Globals.CellHeight/2);
		MapCells[32].setXY(MapCells[26].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[26].getY()-Globals.CellHeight/2);
		MapCells[33].setXY(MapCells[27].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[27].getY()-Globals.CellHeight/2);
		MapCells[34].setXY(MapCells[28].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[28].getY()-Globals.CellHeight/2);
		MapCells[35].setXY(MapCells[29].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[29].getY()-Globals.CellHeight/2);
		MapCells[36].setXY(MapCells[30].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[30].getY()-Globals.CellHeight/2);
		MapCells[37].setXY(MapCells[31].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[31].getY()-Globals.CellHeight/2);
		MapCells[38].setXY(MapCells[37].getX(),MapCells[37].getY()+Globals.CellHeight);
		MapCells[39].setXY(MapCells[32].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[32].getY()+Globals.CellHeight/2);
		MapCells[40].setXY(MapCells[33].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[33].getY()+Globals.CellHeight/2);
		MapCells[41].setXY(MapCells[34].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[34].getY()+Globals.CellHeight/2);
		MapCells[42].setXY(MapCells[35].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[35].getY()+Globals.CellHeight/2);
		MapCells[43].setXY(MapCells[36].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[36].getY()+Globals.CellHeight/2);
		MapCells[44].setXY(MapCells[37].getX()+Globals.CellWidth+Globals.CellWidth/2,MapCells[37].getY()+Globals.CellHeight/2);
	}
	
	//get functions
	public Cell[] getMapCells()
	{
		return(MapCells);
	}

	public int getCellCount()
	{
		return(CellCount);
	}
	
	//set functions
	public void setMapCells(Cell[] tMapCells)
	{
		MapCells=tMapCells;
	}

	//methods
	public int findTotalNodesCount()
	{
		int count=0;
		for(int x=0;x<CellCount;x++)
		{
		  count=count+MapCells[x].getNodeCount();	
		}
		return(count);
	}
	
	//returns which cell a node is present
	public int findCellNoOfNode(int tNodeIndex)
	{
		int tCellNo=-1;
		
		for(int t=0;t<CellCount;t++)
		{
			for(int j=0;j<MapCells[t].getNodeCount();j++)
			{
				if(MapCells[t].getNodeID(j)==tNodeIndex)
				{
					tCellNo=t;
				}
			}
		}
		
		return(tCellNo);
	}
	
	//returns index of a node insIDe a cell
	public int findNodeIndexInCell(int tNodeIndex)
	{
		int tIndex=-1;
		
		for(int t=0;t<CellCount;t++)
		{
			for(int j=0;j<MapCells[t].getNodeCount();j++)
			{
				if(MapCells[t].getNodeID(j)==tNodeIndex)
				{
					tIndex=j;
				}
			}
		}
		
		return(tIndex);
	}
	
	public String findAdjListString()
	{
		String tstr="";
		for(int t=0;t<CellCount;t++)
		{
			tstr=tstr+"cell"+Integer.toString(t)+": ";
			if(MapCells[t].getAdjCount()!=0)
			{
				for(int ti=0;ti<MapCells[t].getAdjCount();ti++)
				{
					tstr=tstr+Integer.toString(MapCells[t].getAdjCell(ti))+" ";
				}
			}
			tstr=tstr+"\n";
		}
		return(tstr);
	}
	
	public void draw(Graphics g)
	{
		for(int t=0;t<CellCount;t++) MapCells[t].draw(g);
	}
}
