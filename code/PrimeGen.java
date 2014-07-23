//PrimeGen.java

import java.lang.*;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

class PrimeGen
{
	public int primeT(BigInteger pval)
	{
		BigInteger pminusone=pval.subtract(BigInteger.ONE);
		BigInteger neg=new BigInteger("-1");
		BigInteger two=new BigInteger("2");

		BigInteger[] qandm=getqm(pval);
		BigInteger qval=qandm[0];
		if(qval.compareTo(neg)==0) return(0);
		BigInteger mval=qandm[1];

		SecureRandom r=new SecureRandom();
		BigInteger bval=new BigInteger(pval.bitLength(),r);
		
		if(bval.modPow(mval,pval).compareTo(BigInteger.ONE)==0) return(1);
		BigInteger j=BigInteger.ZERO;
		BigInteger indexval=mval;
		while(j.compareTo(qval)<0)
		{
			if(pminusone.compareTo(bval.modPow(indexval,pval))==0) return(1);
			indexval=indexval.multiply(two);
			j=j.add(BigInteger.ONE);			
		}
		return(0);
	}

	public BigInteger createPrime(int bitlength,int numTests)
	{
		SecureRandom r=new SecureRandom();
		BigInteger p=new BigInteger(bitlength,r);
		int h=0;

		while(h<numTests)
		{
			if(primeT(p)==0)break;
			else h++;
		}
		if(h==numTests) return(p);
		else return(createPrime(bitlength,numTests));
	}

	public BigInteger[] getqm(BigInteger p)
	{
		p=p.subtract(BigInteger.ONE);
		BigInteger two=new BigInteger("2");
		BigInteger neg=new BigInteger("-1");
		BigInteger[] rt={BigInteger.ZERO,BigInteger.ZERO}; //rt={q,m}
		if(p.mod(two).compareTo(BigInteger.ZERO)!=0)
		{
			rt[0]=neg; rt[1]=neg;
			return(rt);
		}

		BigInteger divisor=p.divide(two);
		BigInteger counter=BigInteger.ONE;
		while(divisor.mod(two).compareTo(BigInteger.ZERO)==0)
		{
			counter=counter.add(BigInteger.ONE);
			divisor=divisor.divide(two);
		}
		rt[0]=counter; rt[1]=divisor;
		return(rt);
	}
}
