// OOSimL v 1.1 File: Carwash.java, Thu Dec 20 01:32:53 2018
 
import java.util.*; 
import java.io.*; 
import java.text.DecimalFormat; 
import psimjava.*; 
/** 
  description
  A single-server model of a Car-wash system
  arriving cars (customers) join a queue to wait for service.
  The server is a car-wash machine that services one car at
  a time. Service consists of a complete external wash of a car.
  
  OOSimL model J. Garrido, Jan 10, 2008
  Main class: Carwash
  
  This class sets up simulation parameters and creates the
  relevant objects.
  There are four types of active objects of classes: Carwash, 
   Server, Arrivals, and Car.
  There is one passive object, the car_queue of class Squeue.
  The arrivals object creates the various car objects that 
  arrive at random intervals. 
  Random variables: inter-arrival period of a negative exponential
   distribution and service period of a Normal distribution.
   
  File: Carwash.osl
  */
 public  class Carwash  extends psimjava.Process     {
static Scanner scan = new Scanner (System.in);
 // 
 // files for reporting 
 private  static PrintWriter statf; 
 // statistics 
 private  static PrintWriter tracef; 
 // trace 
 // input car queue size 
 //  
 public  static int  totalQSize = 40; 
  // simulation period    
 // 
 public  static double  simPeriod = 1200.0; 
  // time to close arrival of cars 
 // 
 public  static double  close_arrival = 300.5; 
  // mean car inter-arrival interval 
 // 
 public  static double  MeanInterarrival = 7.5; 
  // mean car service interval  
 // 
 public  static double  MeanServiceDur = 11.25; 
  // standard deviation of service interval 
 // 
 // Accumulative variables 
 // 
 public double  StdServiceDur = 1.25; 
  // accumulated customer service time 
 public  static double  custServiceTime = 0.0; 
  // accumulated customer sojourn time 
 public  static double  custSojournTime = 0.0; 
  // accumulated car waiting time   
 public  static double  custWaitTime = 0.0; 
  // accum server idle time 
 public  static int  cap_of_server = 3; 
  public  static int  num_of_queues = 3; 
  public  static double  accum_idle = 0.0; 
  // current number of customers serviced 
 public  static int  num_serviced = 0; 
  // current number of cars that arrived  
 public  static int  num_arrived = 0; 
  // current number of cars rejected 
 public  static int  num_rejected = 0; 
  public  static int  j; 
  //  
 // 
 // Simulation model essential objects 
 // 
 // Queues for the model 
 public  static Simulation sim; 
 // queue for arrived waiting cars 
 public  static Squeue car_queue; 
 //  
 public  static Squeue car_queues[]; 
 // Classes for the active objects of the model 
 public  static Server wash_machine; 
 public  static Server wash_machines[]; 
 // 
 public  static Arrivals genCust; 
 public  static Carwash shop; 
 // 
 public SimModel model; 
 // constructor 
 public Carwash( String ss) { 
super(ss); 
 } // end initializer 
 // 
 public static void main(String[] args) {
 // set-up simulation with an object of class simulation 
 // 
 sim = new Simulation( "Simple Car-Wash System" );
 // setup files for reporting 
 Simulation.set_tracef("carwtrace.txt");
 // 
 // create queues (passive objects) 
 Simulation.set_statf("carwstatf.txt");
 //create car_queue of class Squeue using "Customer Queue", totalQSize 
car_queues = new Squeue [num_of_queues];
 for (j = 0 ; j <= (num_of_queues) - (1); j++) { 
car_queues [j] = new Squeue("Customer Queue", totalQSize);
 // 
 } // endfor 
 // Create and start the main active objects of the model 
shop = new Carwash("Car Wash Shop");
 shop.start();
 // 
 }  // end main 
 // This function is called by the class object with interactive I/O 
 public void  pbegin(  SimModel sm ) { 
 int  jin; 
  double  din; 
 model =  sm;
 // call model.println using "Input workload parameters for Car Wash Model" 
model.println("Simple Model of a Car-Wash System"); 
 // Input parameters 
jin =  model.readInt("Type queue size (40): ");
 if ( jin > 0) { 
totalQSize =  jin;
 } // endif 
 // 
din =  model.readDouble("Type simulation period (1200.0): ");
 if ( din > 0.0) { 
simPeriod =  din;
 } // endif 
 // 
din =  model.readDouble("Type close time (300.5): ");
 if ( din > 0.0) { 
close_arrival =  din;
 } // endif 
 //    
din =  model.readDouble("Type mean inter-arrival period (7.5): ");
 if ( din > 0.0) { 
MeanInterarrival =  din;
 } // endif 
 // 
din =  model.readDouble("Type mean service period (11.25): ");
 if ( din > 0.0) { 
MeanServiceDur =  din;
 } // endif 
 // 
din =  model.readDouble("Type service time standard dev (1.25): ");
 if ( din > 0.0) { 
StdServiceDur =  din;
 // 
 } // endif 
 // set-up simulation 
 // 
 sim = new Simulation( "Simple Model of Car-Wash System" );
 // Set up report files 
 Simulation.set_tracef("carwtrace.txt");
 // 
 // Create  passive objects: queues 
 //create car_queue of class Squeue  
 Simulation.set_statf("carwstatf.txt");
 //   using "Customer Queue", totalQSize   
car_queues = new Squeue [num_of_queues];
 for (j = 0 ; j <= (num_of_queues) - (1); j++) { 
car_queues [j] = new Squeue("Customer Queue", totalQSize);
 // 
 } // endfor 
 // start main active object in model 
 start();
 // 
 }  // end pbegin 
 // 
 public void  Main_body(   ) { 
 String  pname; 
  // 
 double  machutil; 
  // formatting strings 
 String  fmtout1; 
  String  fmtout2; 
  String  fmtout3; 
  String  dispouta; 
  String  dispoutb; 
  // 
 DecimalFormat dfmt; 
 statf = Simulation.get_statfile(); 
 // 
 // create the other active objects of the model 
 //create wash_machine of class Server  
 //       using "Wash_machine"  
wash_machines = new Server [cap_of_server];
 for (j = 0 ; j <= (cap_of_server) - (1); j++) { 
wash_machines [j] = new Server("Wash_machine");
 } // endfor 
genCust = new Arrivals("Arrivals ", MeanInterarrival, MeanServiceDur, StdServiceDur);
 genCust.start();
 for (j = 0 ; j <= (cap_of_server) - (1); j++) { 
 wash_machines [j].start();
 //start thread wash_machine 
 // 
 // display "Workload - car mean inter-arrival: ",  
 //      MeanInterarrival, " mean service: ", MeanServiceDur 
 // 
 } // endfor 
 // starting simulation 
 // 
 sim.start_sim( simPeriod );
 // set formatting to round output to 3 dec places 
dfmt = new DecimalFormat("0.###");
 // 
System.out.println("Total number of cars serviced: "+ 
num_serviced);
statf.println("Total number of cars serviced: "+ 
num_serviced);
statf.flush();
 if ( num_serviced > 0) { 
fmtout1 =  dfmt.format((custWaitTime)/(num_serviced));
System.out.println("Car average wait period: "+ 
fmtout1);
statf.println("Car average wait period: "+ 
fmtout1);
statf.flush();
 // 
fmtout2 =  dfmt.format((custSojournTime)/(num_serviced));
System.out.println("Average period car spends in the shop: "+ 
fmtout2);
statf.println("Average period car spends in the shop: "+ 
fmtout2);
statf.flush();
 // 
 } // endif 
 // machine utilization 
machutil =  (( (simPeriod) - (accum_idle)) )/(simPeriod);
fmtout3 =  dfmt.format(machutil);
 // 
statf.println("Machine utilization: "+ 
fmtout3);
statf.flush();
System.out.println("Machine utilization: "+ 
fmtout3);
 // 
dispouta =  "Cars serviced: " + num_serviced + "\n" + "Avg wait: " + dfmt.format((custWaitTime)/(num_serviced)) + "\n";
dispoutb =  dispouta + "Mach util: " + dfmt.format((( (simPeriod) - (accum_idle)) )/(simPeriod)) + "\n" + "Avg per in shop: " + dfmt.format((custSojournTime)/(num_serviced));
 //call model.println using dispoutb 
 System.exit(0); 
 }  // end Main_body 
} // end class/interf Carwash 
