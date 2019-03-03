


import org.apache.ignite.*;
import org.apache.ignite.lang.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class IgnitedBooths {
	public static Scanner s = new Scanner(System.in);
   public static void main(String[] args) throws IgniteException {
	   int ValueOne = 0,ValueTwo=0;
	   
        try (Ignite ignite = Ignition.start("/root/GridComputing/ignite-fabric-1.0.0-RC1/examples/config/example-compute.xml")) {
        	IgniteCluster cluster = ignite.cluster();
        	int pairNo=0;
        	System.out.println();
            Scanner scan = new Scanner(System.in);
            System.out.println(">>> Compute closure example started.");
            System.out.println(">>> Enter the numbur of pair you want to multiply : ");
            
            pairNo = scan.nextInt();
            int sizeOfArray = pairNo *2;
            int inputs[] = new int[sizeOfArray];
            Input[] arrayValues = new Input[pairNo];
            ArrayList<Input> al = new ArrayList<Input>();
            for(int i=0;i<pairNo;i++)
            {
            	int j = i;
            	int numberOne,numberTwo;
            	System.out.println("Enter Pair Number "+i+" : ");
            	numberOne=scan.nextInt();
            	numberTwo=scan.nextInt();
            	inputs[j]=numberOne;
            	j = j+1;
            	inputs[j]=numberTwo;
            	if(numberTwo>=numberOne)
            	{
            		ValueOne = numberTwo;
            		ValueTwo = numberOne;
            	}
            	else
            	{
            		ValueOne = numberOne;
            		ValueTwo = numberTwo;
            	}
            	arrayValues[i]=new Input(ValueOne,ValueTwo);
            }
            for(int i=0;i<pairNo;i++)
            {
            	al.add(arrayValues[i]);
            }

            Iterator itr = al.iterator();
            
            while(itr.hasNext())
            {
		    
            	
            	String result = Multi(ignite,al);
            	
            	
            	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/root/akash/result.txt", true)))) {
            	    out.println(result);
            	}catch (IOException e) {
            	   
            	}
            	 itr.next();
            	 itr.remove();
            	 
            }

            
           
        }
    }

public static String Multi(Ignite ignite,ArrayList<Input> al)
   {
	   String result = ignite.compute().apply(
               new IgniteClosure<ArrayList,String>() {
                   @Override public String apply(ArrayList al) {
                       
                       Iterator itr = al.iterator();
                       
                       Input iVal = (Input)itr.next(); 
                       int ValueOne = (int) iVal.NumberOne;
                       int ValueTwo = (int) iVal.NumberTwo;
                       int x = multiply(ValueOne,ValueTwo);
                       
                       
                       
                       
                       
                       System.out.println("\n\n"+ValueOne+" * "+ValueTwo+" = "+x);
                       
                       return ValueOne+"*"+ValueTwo+"="+x;
                      
                       
                   }
               } 	,
               al
           );
	   return result; 
   }
public static int multiply(int n1, int n2)
{
    int[] m = binary(n1);
    int[] m1 = binary(-n1);
    int[] r = binary(n2);        
    int[] A = new int[41];
    int[] S = new int[41];
    int[] P = new int[41];        

    for (int i = 0; i < 20; i++)
    {
        A[i] = m[i];
        S[i] = m1[i];
        P[i + 20] = r[i];
    }

    display(A, 'A');
    display(S, 'S');
    display(P, 'P');        
    System.out.println();
    for (int i = 0; i < 20; i++)
    {
        if (P[39] == 0 && P[40] == 0);
            // do nothing            

        else if (P[39] == 1 && P[40] == 0)
            add(P, S);                            

        else if (P[39] == 0 && P[40] == 1)
            add(P, A);            

        else if (P[39] == 1 && P[40] == 1);
            // do nothing

        rightShift(P);
        display(P, 'P');
    }
    return getDecimal(P);
}

public static int getDecimal(int[] B)
{
    int p = 0;
    int t = 1;
    for (int i = 39; i >= 0; i--, t *= 2)
        p += (B[i] * t);
    if (p > 1048576)
        p = -(1048576 - p);
    return p;        
}

public static void rightShift(int[] A)
{        
    for (int i = 40; i >= 1; i--)
        A[i] = A[i - 1];        
}

public static void add(int[] A, int[] B)
{
    int carry = 0;
    for (int i = 40; i >= 0; i--)
    {
        int temp = A[i] + B[i] + carry;
        A[i] = temp % 2;
        carry = temp / 2;
    }        
}

public static int[] binary(int n)
{
    int[] bin = new int[20];
    int ctr = 19;
    int num = n;
    if (n < 0)
        num = 1048576 + n;
    while (num != 0)
    {
        bin[ctr--] = num % 2;
        num /= 2;
    }
    return bin;
}

public static void display(int[] P, char ch)
{ 
    System.out.print("\n"+ ch +" : ");
    for (int i = 0; i < P.length; i++)
    {
        if (i == 20)
            System.out.print(" ");
        if (i == 40)
            System.out.print(" ");
        System.out.print(P[i]);
    } 
}





}

class Input{  
    long NumberOne;  
    long NumberTwo;  
    Input(long NumberOne,long NumberTwo)
    {  
    this.NumberOne=NumberOne;  
    this.NumberTwo=NumberTwo;  
    }  
  }  
