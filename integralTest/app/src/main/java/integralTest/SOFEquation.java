package integralTest;

import java.util.Scanner;

public class SOFEquation {
    
    public static void main(String[] args){
        System.out.println(eval("pi(0)"));
        Scanner scan = new Scanner(System.in);

        
        // String f = "x^2";
        // f = f.replaceAll("x",2+"");
        // System.out.println(f);
        f("x^2",2.);
        String func;
        func = scan.nextLine();
        f(func,10); 
        System.out.println("Simpson's approximation: " + 
        simpInt(func,0,1000000));
       
        while(true){
            try{
                // System.out.println(eval(scan.nextLine()));
                func = scan.nextLine();
                // f(func,10); 
                System.out.println("Simpson's approximation: " +
                simpInt(func,0,Double.POSITIVE_INFINITY));

            }catch (Exception e){
                e.printStackTrace();
                System.out.println(". . . . . . .");
            }
        }

       

        //for integral, need to take expression with 'x' in it and plug a value into it
        //f(x) = cos(x)|0 = f(0) = cos(0)
    }

    // public static double evalEquation(final String str, double uBound, double lBound){
        
    // }

    public static double simpInt(String f, double a, double b){
        return ((b-a)/8)*(f(f,a) + 3*f(f,(2*a+b)/3) + 3*f(f,(a+2*b)/3) + f(f,b));
    }

    public static double f(String f, double x){
        f = f.replaceAll("x", x+"");
        f = f.replaceAll("pi", Math.PI + "");
        f = f.replaceAll("e", Math.E + "");
        System.out.println(f + ": " + eval(f));
        return eval(f);
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;
            
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
            
            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            //how to determine when () means * or just a function
            //a function has an equal sign
            //() usually uses numbers

            
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
            
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }
            
            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')||func.equals("pi")) {
                        x = parseExpression();
                        if (!eat(')')&&(!func.equals("pi"))) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("arccos")) x = Math.acos(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else if (func.equals("pi")) x = Math.PI; //need to add support for pi and e
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                
                return x;
            }
        }.parse();
    }
}
