/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.integralcalculatorgui;

import java.math.BigInteger;
/**
 *
 * @author abw04
 */
public class Factorial {
    public static long bang(long n) throws ArithmeticException, IllegalArgumentException{  // long can be bigger (integers stored in 64 bits as opposed to 32 bit ints)
        if (n<0)
            throw new IllegalArgumentException("Can't take the factorial of a negative number");
        else if (n==0||n==1)//base case
            return 1;
        else //recursive call
            return Math.multiplyExact(n, bang(n-1));
    }

    public static BigInteger bang(BigInteger n) {  
        if (n.compareTo(BigInteger.ZERO) < 0){
            throw new IllegalArgumentException("Can't take the factorial of a negative number");
        }else if (n.compareTo(BigInteger.ONE) == 0){
            return BigInteger.ONE;
        } else{
            return n.multiply(bang(n.subtract(BigInteger.ONE)));
        }
            
    }
}
