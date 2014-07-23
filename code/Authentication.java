//Authentication.java

import java.lang.*;
import java.io.*;
import java.math.*;
import java.security.*;

//deffie-hellman digital signature scheme
class Authentication
{
	private BigInteger q,a;
	private BigInteger XA,YB;
	private BigInteger XB,YA; //sender private information needed for decryption
	private BigInteger KA; //sender key
	private BigInteger KB; //receiver key
	private String delimiter="-";
	
	//constructor
	public Authentication()
	{
		//
	}
	
	//get functions
	public BigInteger get_q()
	{
		return(q);
	}
	
	public BigInteger getXA()
	{
		return(XA);
	}
	
	public BigInteger getXB()
	{
		return(XB);
	}
	
	public BigInteger getYA()
	{
		return(YA);
	}
	
	public BigInteger getYB()
	{
		return(YB);
	}
	
	//set functions
	public void set_q(BigInteger tq)
	{
		q=tq;
	}
	
	public void setXA(BigInteger tXA)
	{
		XA=tXA;
	}
	
	public void setXB(BigInteger tXB)
	{
		XB=tXB;
	}
	
	public void setYA(BigInteger tYA)
	{
		YA=tYA;
	}
	
	public void setYB(BigInteger tYB)
	{
		YB=tYB;
	}
	
	//methods
	public void initializeKA()
	{
		//generate q,a
		PrimeGen test=new PrimeGen();
		q=test.createPrime(Globals.BitLength,100);
		a=new BigInteger("3");
		
		//generate XA,XB; calculate YA,YB
		SecureRandom r=new SecureRandom();
		XA=new BigInteger(Globals.BitLength-8,r); //to make sure XA<q
		YA=a.modPow(XA,q);
		
		XB=new BigInteger(Globals.BitLength-8,r); //to make sure XB<q
		YB=a.modPow(XB,q);
	}
	
	public void generateKA() //generate sender public key
	{
		//find KA: KA=YB^XA mod q
		KA=YB.modPow(XA,q);
	}
	
	public void generateKB() //generate receiver public key
	{
		//Find KB: KB=YA^XB mod q
		KB=YA.modPow(XB,q);
	}
	
	public String Encrypt(String instr)
	{
		String tans="";
		
		for(int t=0;t<instr.length();t++)
		{
			int ichar=instr.charAt(t);
			BigInteger m_in=new BigInteger(Integer.toString(ichar));
			
			//Encryption: c=m_in*K
			BigInteger c=m_in.multiply(KA);
			
			String cstr=c.toString();
			tans+=cstr+(t==instr.length()-1?"":delimiter);
		}
		
		return(tans);
	}
		
	public String Decrypt(String cipher)
	{
		String tans="";
		
		String tarr[]=Utils.split(cipher,delimiter);
		for(int t=0;t<tarr.length;t++)
		{
			BigInteger c=new BigInteger(tarr[t]);
			
			//Decryption: m_out=c/KB
			BigInteger m_out=c.divide(KB);
			tans+=""+(char)m_out.intValue();
		}
		
		return(tans);
	}
}
