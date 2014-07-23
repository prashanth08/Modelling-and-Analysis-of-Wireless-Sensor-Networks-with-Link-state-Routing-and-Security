//Utils.java

import java.lang.*;
import java.io.*;
import java.util.*;

public class Utils
{
	//utility functions
	public static String[] split(String str,String demiliter)
	{
		ArrayList alist1=new ArrayList();
		
		//split
		int startIndex=0;
		while(true)
		{
			int tIndex=str.indexOf(demiliter,startIndex);
			if(tIndex==-1)
			{
				if(startIndex<=str.length()) alist1.add(str.substring(startIndex));
				break;
			}
			String tstr=str.substring(startIndex,tIndex);
			startIndex+=tstr.length()+demiliter.length();
			alist1.add(tstr);
		}
		
		//convert arraylist to string-array
		String tarr[]=new String[alist1.size()];
		for(int t=0;t<alist1.size();t++)
		{
			tarr[t]=(String)alist1.get(t);
		}
		
		return(tarr);
	}
}
