// OOSimL v 1.1 File: Server.java, Thu Dec 20 01:32:20 2018
 
import java.util.*; 
import psimjava.*; 
/** 
description
   OOSimL model of the car-wash system
   
   J Garrido, Feb. 2008

   This class defines the server object, which
    is the car-wash machine. It takes the car at 
    the head of the queue and services the car. 
    When the machine completes service of the car,
    it gets another car from the queue.
    If the queue is empty, it goes into its 
    idle state (suspends itself)
*/
 public  class Server  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 private  static int  j; 
  private Car currentCustomer; 
 // current customer 
 private void  serviceCustomer (   ) { 
 double  startTime; 
  // time of start of service 
 double  service_per; 
  // service period for car  
 String  objname; 
  String  ccustname; 
  double  ccustservt; 
  // cust service time 
 double  simclock; 
  int  X; 
  int  max; 
  int  index; 
 max =  0;
index =  0;
 for (j = 0 ; j <= (Carwash.num_of_queues) - (1); j++) { 
X = Carwash.car_queues [j].length(); 
 if ( X > max) { 
X =  max;
index =  j;
 } // endif 
 } // endfor 
 if ( !Carwash.car_queues [index].empty()) { 
 // get customer from head of waiting queue 
currentCustomer = ( Car) Carwash.car_queues [index].out();
 // 
objname =  get_name();
ccustname =  currentCustomer.get_name();
 // get cust service time 
ccustservt =  currentCustomer.get_serv_dur();
 startTime = StaticSync.get_clock();
 // service start time 
System.out.println(objname+ 
" begins service of: "+ 
ccustname+ 
" at: "+ 
startTime+ 
" for "+ 
ccustservt);
 // 
tracedisplay(objname+ 
" begins service of: "+ 
ccustname);
 // accumulate waiting time for this customer (car) 
Carwash.custWaitTime =  (Carwash.custWaitTime) + (( (startTime) - (currentCustomer.get_arrivalT())) );
 // 
service_per =  currentCustomer.get_serv_dur();
  delay(service_per);
 // 
 simclock = StaticSync.get_clock();
 Carwash.custServiceTime += ( (simclock) - (startTime)) ;
System.out.println(objname+ 
" completes service of: "+ 
ccustname+ 
" at: "+ 
simclock);
tracedisplay(objname+ 
" completes service of: "+ 
ccustname);
 reactivate(currentCustomer);
 // let car continue   
 }
 else {
return  ; 
 } // endif 
 }  // end serviceCustomer 
 //     
 public Server( String  sername) { 
super(sername); 
 // display sername, " created at: ", simclock 
 } // end initializer 
 // 
 public void  Main_body(   ) { 
 double  startIdle; 
  double  idle_period; 
  // idle period 
 double  simclock; 
  // simulation time 
 String  cname; 
  // name attribute 
 int  j; 
 cname =  get_name();
 simclock = StaticSync.get_clock();
System.out.println(cname+ 
" starting main body at "+ 
simclock);
 // 
 while ( simclock < Carwash.simPeriod ) { 
 for (j = 0 ; j <= (Carwash.num_of_queues) - (1); j++) { 
 if ( Carwash.car_queues [j].empty()) { 
 // queue is empty 
startIdle =  get_clock();
 // starting time of idle period 
System.out.println(cname+ 
" goes idle at "+ 
simclock);
tracedisplay(cname+ 
" goes idle");
 // suspend server 
 // 
 // reactivated by a car object 
 // queue must now be nonempty 
 deactivate(this);
 // display cname, " reactived" 
simclock =  get_clock();
idle_period =  (simclock) - (startIdle);
 Carwash.accum_idle += idle_period;
System.out.println(cname+ 
(" reactivated at ") + (simclock));
 } // endif 
 } // endfor 
serviceCustomer(); 
 // service the car 
 } // endwhile 
  terminate();
 }  // end Main_body 
 // 
} // end class/interf Server 
