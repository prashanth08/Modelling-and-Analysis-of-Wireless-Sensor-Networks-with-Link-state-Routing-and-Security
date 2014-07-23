import java.io.*;
import java.net.*;
import java.util.*;

class client{

	public static Socket ms,soc;	
        public static ServerSocket ss,ss2;
	public static Socket cs[]=new Socket[2];

	public static String mobile[]=new String[2];
	public static boolean flag[]={true,true};
	public static Hashtable has;
	public static SendReceive sendrec;
	public static Connect0 con;
	int length=0;
	int size=0;
	byte message[];
	Random r;

	public client() throws IOException{

		try {

			connect();

		}catch(Exception e) {System.out.println("Exception in client:"+e);}
	}

	public void connect() {

		has = new Hashtable();
		try {
			ms=new Socket(InetAddress.getLocalHost(),4545);
			System.out.println("Client IS CONNECTED TO Network Manager:");
			ss=new ServerSocket(4000);
			System.out.println("client is listening.....");
			int i=0;
			while(i<2){			
				cs[i]=ss.accept();
				mobile[i]="yes";								
				con = new Connect0(i);
				con.start();								
				i++;
			}  
			ss2=new ServerSocket(3535);
		
			//soc=ss2.accept();
			int j=1;
			while(true){

///////////////
				soc=ss2.accept();
				System.out.println("Connected with Sender");
			        has.put("Sender"+j,soc);
  				sendrec = new SendReceive(has,j);	
			        sendrec.start();
				j++;

////////////
			}
		}catch(Exception er){System.out.println("error at :"+er);}	
	}

	public static void main(String ar[])throws IOException{
		
		new client();
	}

class SendReceive extends Thread {
	
	Hashtable has;
	int i;
	
	SendReceive(Hashtable has, int i) {
		
		this.has = has;
		this.i = i;

	}

	public void run() {

		sendMessage();
	}
			
	public void sendMessage(){

		PrintWriter out;	
		DataInputStream in;
		r = new Random();
	
		try{	
			//in = new BufferedReader(new InputStreamReader(has.get("Sender"+i)soc.getInputStream()));
			in=new DataInputStream(((Socket)has.get("Sender"+i)).getInputStream());
			String mess=in.readLine();
			
           

			for(int i=1;i>-1;i--){			   
				if(mobile[i].equals("yes")){	
					 if(flag[i]){										
						out=new PrintWriter(cs[i].getOutputStream(),true); 
						out.println(mess);
						
						System.out.println("Message Recieved :"+mess);
						
						 StringTokenizer token = new StringTokenizer(mobile[i].trim(),",");
			             mess=SECURE1.encmsg(token.nextToken(),"hello");
						
						break;							
					 }
				}
			}
			if(!flag[0] && !flag[1]){
				System.out.println("Both the nearer mobiles are out of Range !!!");
				
			}								    	
		}catch(Exception er){System.out.println(""+er);}		
	  }
     }
}

class Connect0 extends Thread {

	private BufferedReader in;	
	private int i;		

	Connect0(int i){

		try{			
			this.i=i; 			

		}catch(Exception ex){System.out.println("Error in formingStream:"+ex);}
	}	

	public void run(){

		try{			
			while(true){				
				in = new BufferedReader(new InputStreamReader(client.cs[i].getInputStream()));	
				if(in.readLine().equalsIgnoreCase("yes")){
					client.mobile[i]="yes";
					client.flag[i]=true;																
				}
				else{	
					client.mobile[i]="No";	
					client.flag[i]=false;
				}
			}
		}catch(Exception er){System.out.println("Exception in connection with clients:"+er);}
	}
}
