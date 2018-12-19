// OOSimL v 1.1 File: Car.java, Thu Dec 20 01:32:20 2018
 
import java.util.*; 
import psimjava.*; 
/** 
description
  The model of the car-wash system
  OOSIMl version,  J Garrido, Feb. 2008

  This class defines behavior of car objects
  After a car object is created it joins the queue to wait for
  service. It it finds the server object idle, 
  it reactivates the server object.
*/
 public  class Car  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 private int  customerNum; 
  // customer number 
 private double  arrivalTime; 
  // arrival time of customer 
 private double  service_dur; 
  // cust service interval 
 private int  j; 
  private int  x; 
  private int  min; 
  private int  index; 
  // 
 public Car( String  cname, double  dur) { 
super(cname); 
customerNum =  Carwash.num_arrived;
 arrivalTime = StaticSync.get_clock();
service_dur =  dur;
System.out.println(cname+ 
" arrives at time "+ 
arrivalTime);
 } // end initializer 
 // 
 public double  get_arrivalT(   ) { 
return arrivalTime; 
 }  // end get_arrivalT 
 // 
 public double  get_serv_dur(   ) { 
return service_dur; 
 }  // end get_serv_dur 
 // 
 public void  Main_body(   ) { 
 double  simclock; 
  String  pname; 
  double  a; 
  // bogus 
 simclock = StaticSync.get_clock();
pname =  get_name();
System.out.println(pname+ 
" requests service at time "+ 
simclock);
 // check if there is still space available in the carwash shop 
min =  (Carwash.totalQSize) + (1);
index =  0;
 for (j = 0 ; j <= (Carwash.num_of_queues) - (1); j++) { 
x = Carwash.car_queues [j].length(); 
 if ( x < min) { 
x =  min;
index =  j;
 } // endif 
 } // endfor 
 if ( !Carwash.car_queues [index].full()) { 
 // queue not full 
System.out.println(pname+ 
" joins queue at time "+ 
simclock);
tracedisplay(pname+ 
" joins queue ");
Carwash.car_queues [index].into(this );
 // enqueue this car 
 for (j = 0 ; j <= (Carwash.cap_of_server) - (1); j++) { 
 if ( Carwash.wash_machines [j].idle()) { 
System.out.println("Activating server at time "+ 
simclock);
 reactivate(Carwash.wash_machines [j]);
 // call Thread.yield 
j =  (Carwash.cap_of_server) - (1);
 } // endif 
 } // endfor 
 // 
 // to wait for service         
 deactivate(this);
 // service completed, do final computation 
 // customers serviced 
 Carwash.num_serviced++;
 // total time in the system: custSojournTime 
 simclock = StaticSync.get_clock();
Carwash.custSojournTime =  (Carwash.custSojournTime) + (( (simclock) - (arrivalTime)) );
System.out.println(pname+ 
" terminates at "+ 
simclock);
  terminate();
 // terminates itself       
 }
 else {
 // queue full, abandon hope                                
 Carwash.num_rejected++;
System.out.println(pname+ 
" rejected, queue full ");
tracedisplay(pname+ 
" rejected, queue full ");
  terminate();
 // terminate this object 
 } // endif 
 }  // end Main_body 
} // end class/interf Car 
