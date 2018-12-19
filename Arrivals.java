// OOSimL v 1.1 File: Arrivals.java, Thu Dec 20 01:29:34 2018
 
import java.util.*; 
import psimjava.*; 
/** 
description
  The model of the Car-wash system
  OOSimL version J Garrido, Jan 2008
  This class defines the behavior of the environment
  that generates car arrivals randomly.
  The object of this class creates and starts the car objects
  at specific instants called arrival events.
  An negative exponential distribution is used to generate 
  random numbers that correspond to the inter-arrival period.
  The service periods for car objects are random
  variables from a Normal distribution.
  */
 public  class Arrivals  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 // car inter-arrival random period 
 // 
 private NegExp arrivalPeriod; 
 // car service period random number  
 private Normal servicePeriod; 
 public Arrivals( String  arrname, double  Mean_arr, double  Mean_serv, double  stdserv) { 
 // 
 // create random number generator using mean inter-arrival period 
super(arrname); 
 // exponential distribution 
 //   
 // create random number generator using mean service period 
arrivalPeriod = new NegExp("Inter-arrival", Mean_arr);
 // Normal distribution 
servicePeriod = new Normal("Service Period", Mean_serv, stdserv);
System.out.println(arrname+ 
" created");
 } // end initializer 
 // 
 public void  Main_body(   ) { 
 // inter-arrival period for car object 
 double  inter_arr; 
  // service period   
 double  serv_per; 
  double  simclock; 
  String  carname; 
  Car carobj; 
 simclock = StaticSync.get_clock();
 // 
System.out.println("Arrivals starting main body at: "+ 
simclock);
 // repeat until time to close arrivals 
 while ( simclock < Carwash.close_arrival ) { 
 // generate inter-arr 
 inter_arr = arrivalPeriod.fdraw(); 
  delay(inter_arr);
 // wait for inter-arr 
 // 
 Carwash.num_arrived++;
 // generate service interval 
 serv_per = servicePeriod.fdraw(); 
simclock =  get_clock();
System.out.println("Arrivals creating Car at: "+ 
simclock);
tracedisplay("Arrivals creating Car");
carname =  "Car" + Carwash.num_arrived;
 // 
carobj = new Car(carname, serv_per);
 carobj.start();
 } // endwhile 
System.out.println("Arrivals terminates");
  terminate();
 }  // end Main_body 
} // end class/interf Arrivals 
