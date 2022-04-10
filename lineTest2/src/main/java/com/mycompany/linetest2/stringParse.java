package com.mycompany.linetest2;

import java.util.Scanner;

public class stringParse {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = "bpdf(10,5,.5)";
        
        System.out.println(parseInput(input));

        while(true){
            System.out.println(parseInput(scan.nextLine()));
        }
        
        
        
    }

    private static double parseInput(String input){
        input = input.toLowerCase();
        double[]in = new double[10];

        int prevIndex = input.indexOf("(");

        int nextIndex = 0;
        int i = 0;

        while(nextIndex!=-1){
            nextIndex = input.indexOf(",", prevIndex+1);
            if (nextIndex>0){
                in[i] = Double.parseDouble(input.substring(prevIndex+1, nextIndex));
            }
            if (nextIndex<0){
                in[i] = Double.parseDouble(input.substring(prevIndex+1, input.indexOf(")",prevIndex+1)));
            }
            System.out.println(in[i]);
            prevIndex = nextIndex;
                        i++;
        }

        if ((input.charAt(0) =='b') && (input.charAt(1)=='p')){
            return (math_functions.binom_dist.pmf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='b') && (input.charAt(1)=='c')){
            return (math_functions.binom_dist.cdf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='n') && (input.charAt(4)=='p')){
            return (math_functions.normpdf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='n') && (input.charAt(4)=='c')){
            return (math_functions.normcdf((int) in[0], (int) in[1],in[2]));
        }
        else if ((input.charAt(0) =='e') && (input.charAt(2)=='f')){
            return math_functions.erf(in[0]);
        }
        else if ((input.charAt(0) =='g') && (input.charAt(3)=='m')){
            return math_functions.gamma(in[0]);
        }
        else if ((input.charAt(0) =='b') && (input.charAt(1)=='e')){
            return math_functions.Beta(in[0], in[1]);
        }
        else if ((input.charAt(0) =='t') && (input.charAt(1)=='p')){
            return math_functions.tpdf(in[0], (int) in[1]);
        }
        else if ((input.charAt(0) =='t') && (input.charAt(1)=='c')){
            return math_functions.tcdf(in[0], (int) in[1]);
        }
        else{
            return -1;
        }
        
        // return 0;
    }
}
