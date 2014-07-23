import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

//<applet code="Manager1" height=800 width=800> </applet>

public class Manager1 extends Applet implements ActionListener
{	
	String msg;	
	Button but1;
	Button but2;
	Button but3;
	
	Font f=new Font("SansSerif",Font.BOLD, 30);
	Font f1=new Font("SansSerif",Font.BOLD, 10);
	Font f2=new Font("SansSerif",Font.BOLD, 20);
	
	public static Socket cs[]=new Socket[5];
	public static ServerSocket ss;
	public static PrintWriter pw;
	public static String mobile[]=new String[5];
	
	String tree="";
	
	public void init()
	{		
		setBackground(new Color(220,122,230));		
		setSize(1000,600);
		setLayout(null);
		repaint();
		
		but1=new Button(" Listen ");
		but1.setBounds(30,400,50,30);
	    add(but1);
		but1.addActionListener(this);

		but2=new Button(" Node Mapping ");
		but2.setBounds(120,400,100,30);
		but2.setVisible(false);
		add(but2);		
		but2.addActionListener(this);		
		
		but3=new Button(" Routing Analaysis ");
		but3.setBounds(270,400,110,30);
		but3.setVisible(true);
		//add(but3);		
		but3.addActionListener(this);	
		
		
	}	

	public void paint(Graphics g)
	{

		if(mobile[0].equals("yes"))
		{
			g.setFont(f1);	
			g.drawString("C (SOURCE)",120,100);			
	 		g.fillOval(100,100,20,20);
		}
		
		if(mobile[1].equals("yes"))
		{	
			g.setFont(f1);	
			g.drawString("C1",30,150);		
	 		g.fillOval(50,150,20,20);			
		}

		if(mobile[2].equals("yes"))
		{	
			g.setFont(f1);
			g.drawString("C2",50,230);			
	 		g.fillOval(70,200,20,20);
			g.setFont(f2);
			if(!mobile[3].equals("yes"))
			{
				g.drawString("PACKETS ROUTING THRO",400,220);
				g.drawString("AlterNate Path: c-->c1-->c2-->c4",400,260);					
			}
		}

		if(mobile[4].equals("yes"))
		{
			g.setFont(f1);
			g.drawString("C4 (DESTINATION)",150,230);			
	 		g.fillOval(120,200,20,20);	
			but2.setVisible(true);		
		}

		if(mobile[3].equals("yes"))
		{
			g.setFont(f1);	
			g.drawString("C3",180,150);		
	 		g.fillOval(150,150,20,20);
			g.setFont(f2);
			g.drawString("PACKETS ROUTING THRO",500,380);	
			g.drawString("Shortest Path : c-->c3-->c4",500,420);		
		}

		if(tree.equals("yes"))
		{
			if(mobile[1].equals("yes"))
			{
				g.drawLine(110,100,60,150);
				new delay();
				g.drawLine(60,150,80,200);
				new delay();
			}
			g.drawLine(90,210,120,210);
			
			if(mobile[3].equals("yes"))
			{				
				g.drawLine(110,100,160,150);
				new delay();
				g.drawLine(160,150,130,200);
			
			}
		}			
		
		String mss1="Achieving Network Level Privacy in Wireless Sensor Networks";
		setForeground(Color.red);
		g.setFont(f);
		g.drawString(mss1,50,40);
		
		String mss2="";
		setForeground(Color.red);
		g.setFont(f);
		g.drawString(mss2,340,70);
		
		String mss="NETWORK MANAGER";
		setForeground(Color.red);
		g.setFont(f);
		g.drawString(mss,550,180);
		
		g.setFont(f2);	
		
		g.drawString("PATHS IN THE NETWORK",500,230);
		g.drawString("Shortest Path : c-->c3-->c4",500,290);
		g.drawString("AlterNate Path: c-->c1-->c2-->c4",500,330);
	}

	public void actionPerformed(ActionEvent e)
	{		
		if(e.getSource()==but1)
		{			
			try
			{				
				ss=new ServerSocket(4545);
				System.out.println("Network Manager is listening.....");
				int i=0;
				while(i<5)
				{			
					new Connect(ss,i);								
					i++;
				}
				System.out.println("Clients are connected....");	  
			}
			catch(Exception er)
			{
				System.out.println("error at :"+er);
			}			   
		} 

		if(e.getSource()==but2)
		{	
			tree="yes";		
			repaint();			
		}	
if(e.getSource()==but3)
		{	
			new SecureRouting();	
		}					
	}

	public Manager1()
	{
		try
		{
			int i=0;
			while(i<5)
		 	{
				mobile[i]="No";
				i++;
			}		
		}
		catch(Exception ee)
		{
			System.out.println("error in:"+ee);
		}
	}   

	public Manager1(String me){}  
}

class delay extends Thread
{
	delay()
	{
		try
		{
			Thread.sleep(500);
		}
		catch(Exception ee){}
	}
}

class Connect 
{
	private ServerSocket ss;	
	private int i;	
	Manager1 m1=new Manager1("initial");

	Connect(ServerSocket ss,int i)
	{
		try
		{
			this.ss=ss;	
			this.i=i; 
			Socket soc=ss.accept();						
			Manager1.mobile[i]="yes";					
			Manager1.cs[i]=soc;
			
			if(i==1 || i==3)
			{						
				new GetMessage(soc,i).start();
			}
			m1.repaint();			
		}
		catch(Exception ex)
		{
			System.out.println("Error in formingStream:"+ex);
		}
	}		
}

class GetMessage extends Thread
{
	private Socket soc;
	private BufferedReader in;
	private int i;
	Manager1 m1=new Manager1("initial");

	GetMessage(Socket soc,int i)
	{
		try
		{
			this.soc=soc;
			this.i=i;
			in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		}
		catch(Exception ex)
		{
			System.out.println("Error in formingStream:"+ex);
		}
	}

	public void run()
	{	
		while(true)
		{	
			try
			{							
				String message=in.readLine();//getting signal from mobile phones 
				if(message.equals("yes"))
					Manager1.mobile[i]="yes";
				else 
					Manager1.mobile[i]="no";				
			}	
			catch(Exception re){}
		}
	}
}
