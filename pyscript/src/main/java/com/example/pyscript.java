package com.example;

import org.python.util.PythonInterpreter;

public class pyscript {

    public static void main(String[] args){
        //running python script in java
        try(PythonInterpreter pyInterp = new PythonInterpreter()) {
            pyInterp.exec("print('Hello Python World!')");
          }
    }
}
