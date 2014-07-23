import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import java.util.*;

public class Sender1 extends MIDlet implements CommandListener
{
	static protected Display display;
	protected Displayable displayable;

	StreamConnection stream;
	PrintStream out;
	InputStream in;

	Form form1;
	Ticker tick1;
	Command start;

	Form form5;
	Ticker tick3;
	Command back;

	Form form4;
	TextField t;
	Command send;
	
	Form form6;
	Ticker tick6;
	Command back6;
	Command exit;
	
	public Sender1()
	{
		display = Display.getDisplay(this);

		form1=new Form("Link State Unicast Protocol");
		tick1=new Ticker("YOU ARE CONNECTED");
		start=new Command("START",Command.OK,1);
		form1.addCommand(start);
		form1.setCommandListener(this);


		form4=new Form("Chat Screen");
		t=new TextField("Message:","",24,0);
		send=new Command("Send",Command.OK,1);
		form4.append(t);	
		form4.addCommand(send);
		form4.setCommandListener(this);

		form5=new Form("Status");
		tick3=new Ticker("SEND SUCCESSFULLY");
		back=new Command("BACK",Command.OK,1);
		form5.addCommand(back);
		form5.setCommandListener(this);
		
		form6=new Form("NO SIGNAL");
		tick6=new Ticker("SORRY FOR THE DISTRUPTION");
		back6=new Command("BACK",Command.OK,1);
		form6.addCommand(back6);
		exit=new Command("EXIT",Command.OK,2);
		form6.addCommand(exit);
		form6.setCommandListener(this);

	}

	public void startApp()
	{
		try
		{		
			display.setCurrent(form1);
			form1.setTicker(tick1);
	
			stream = (StreamConnection) Connector.open("socket://localhost:3535");
			System.out.println("Connected");
			out = new PrintStream(stream.openOutputStream());
			in = stream.openInputStream();
		
		}
		catch(Exception ex)
		{		
			//System.out.println("Exeption in start"+ex);
    		}    		
	}	

	public void pauseApp(){}

	public void destroyApp(boolean unconditional) 
	{
		display = null;
		displayable = null;
	}

	public void commandAction(Command c,Displayable d)
	{
		if(c==start)
		{
			try
			{
				display.setCurrent(form4);
			}
			catch(Exception ex)
			{
				System.out.println("In Form2:"+ex);
			}
		}
		if(c==send)
		{
			String mes=t.getString();
			out.println(mes);
			t.setString("");			
		}

		if(c==back)
		{
			display.setCurrent(form1);
		}
	}
}

