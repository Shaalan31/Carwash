// OOSimL v 1.1 File: SimModel.java, Thu Dec 20 01:09:36 2018
 
import java.util.*; 
import acm.program.*; 
import psimjava.*; 
/** 
description
  File: SimModel.java
  ---------------------
  This program uses the ACM classes
  Car Wash simulation.
  Because this version is a DialogProgram, 
  the input and output appear as popup dialogs.
  OOSimL model J Garrido (Nov 2009)
 */
 public  class SimModel  extends DialogProgram    {
static Scanner scan = new Scanner (System.in);
 public  static Carwash mymodel; 
 // 
 public void  run(   ) { 
 SimModel sm =  this ; 
 // display "Simple Car Wash simulation." 
 // 
mymodel = new Carwash("Car Wash Simulation Model");
mymodel.pbegin(sm); 
 try { 
this.wait(); 
 } // end try 
 // wait for notification 
 catch ( 
 InterruptedException e ) { 
 } // end catch 
 // 
 }  // end run 
} // end class/interf SimModel 
