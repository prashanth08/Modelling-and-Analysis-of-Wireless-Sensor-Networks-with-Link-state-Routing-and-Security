//ExtendedEuclid.java

import java.lang.*;
import java.io.*;
import java.math.BigInteger;

class ExtendedEuclid
{
	//to find gcd(a,b) recursively
	public BigInteger[] EE(BigInteger a,BigInteger b,BigInteger c,BigInteger d,BigInteger e,BigInteger f)
	{
		if(b.compareTo(BigInteger.ZERO)==0)
		{
			BigInteger[] ret={BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO};
			ret[0]=a; //gcd(a,b)
			ret[1]=e; //coefficient of a
			ret[2]=f; //coefficient of b
			return(ret);
		}
		else
		{
			return(EE(b,a.mod(b),e.subtract((a.divide(b)).multiply(c)),f.subtract((a.divide(b)).multiply(d)),c,d));
		}
	}
}
