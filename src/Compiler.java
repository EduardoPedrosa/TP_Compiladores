/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NATI-PC
 */

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.lang.Math;
import java.util.PriorityQueue;   


public class Compiler
{
   public Compiler(String arg) 
   {

      File file = new File(arg);       
      Lexical parser = new Lexical ();
        
      parser.parse(file);
    
        
          
   }
    
    
    
    
    
}
