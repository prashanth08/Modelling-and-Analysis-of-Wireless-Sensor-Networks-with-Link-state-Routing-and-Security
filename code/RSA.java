//RSA.java

import java.lang.*;
import java.io.*;
import java.math.*;

public class RSA
{
	private int BitLength;
	private BigInteger E;
	private BigInteger N;
	private BigInteger M;
	private BigInteger D;
	
	//constructor
	public RSA()
	{
		BitLength=256;
	}
	
	//get functions
	public BigInteger getE()
	{
		return(E);
	}
	
	public BigInteger getN()
	{
		return(N);
	}
	
	public BigInteger getM()
	{
		return(M);
	}
	
	//set functions
	public void setBitLength(int tBitLength)
	{
		BitLength=tBitLength;
	}
	
	public void setE(BigInteger tE)
	{
		E=tE;
	}
	
	public void setN(BigInteger tN)
	{
		N=tN;
	}
	
	public void setM(BigInteger tM)
	{
		M=tM;
	}
	
	//methods
	public void initializeKA()
	{
		BigInteger P,Q;

		//generate P,Q
		PrimeGen test=new PrimeGen();
		P=test.createPrime(BitLength/2,100);
		Q=test.createPrime(BitLength/2,100);
		//ensure P and Q are not equal
		while(P.compareTo(Q)==0) Q=test.createPrime(BitLength/2,100);

		//Find N: N=P*Q
		N=P.multiply(Q);

		//Find M: M=(P-1)*(Q-1)
		M=(P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));

		//Find E: E must be coprime to M i.e. gcd(M,E)=1
		E=new BigInteger("3");
		while(M.gcd(E).intValue()>1) E=E.add(new BigInteger("2"));
	}
	
	public String encrypt(String strData)
	{
		String strCipher="";
		
		for(int t=0;t<strData.length();t++)
		{
			int ichar=strData.charAt(t);
			BigInteger m_in=new BigInteger(Integer.toString(ichar));

			//Encryption: c=m_in^{E} mod N
			BigInteger c=m_in.modPow(E,N);
			
			strCipher+=c+"-";
		}
		strCipher=strCipher.substring(0,strCipher.length()-1);
		
		return(strCipher);
	}
	
	public void initializeKB()
	{
		//Find D: D must satisfy DE=1 mod M i.e. D=E^-1 mod M
		ExtendedEuclid ee=new ExtendedEuclid();
		BigInteger arra[]=ee.EE(E,M,BigInteger.ZERO,BigInteger.ONE,BigInteger.ONE,BigInteger.ZERO);
		D=arra[1];
		while(D.compareTo(BigInteger.ZERO)<0) D=D.add(M);
	}
	
	public String decrypt(String strCipher)
	{
		String strDecrypted="";
		
		String tarr[]=Utils.split(strCipher,"-");
		for(int t=0;t<tarr.length;t++)
		{
			BigInteger c=new BigInteger(tarr[t]);

			//Decryption: m_out=c^{D} mod N
			BigInteger m_out=c.modPow(D,N);
			
			strDecrypted+=(char)m_out.intValue();
		}
		
		return(strDecrypted);
	}
}
