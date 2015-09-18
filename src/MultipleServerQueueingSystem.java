
import java.io.*;
import java.util.*;
 
public class MultipleServerQueueingSystem
{
	public static Server server1;
	public static Server server2;
	public static Random random;
	public static int direct_out = 0;
	 
	public static void main(String[] args)throws IOException
	{
		Scanner sc = new Scanner(System.in);
		server1 = new Server();
		server2 = new Server();
		System.out.println("Enter mean inter arrival time = ");
		server1.mean_interarrival=sc.nextDouble();
		System.out.println("\nEnter mean service time of server1 = ");
		server1.mean_service=sc.nextDouble();
		System.out.println("\nEnter mean service time of server2 = ");
		server2.mean_service=sc.nextDouble();
		
		System.out.println("\nEnter Number of Customer = ");
		int total_customer = sc.nextInt();
		 
		System.out.println("Multiple-server queueing system\n");
		System.out.println("Mean interarrival time = "+server1.mean_interarrival+" minutes\n");
		System.out.println("Mean service time of server1 = "+server1.mean_service+" minutes\n");
		System.out.println("Mean service time of server2 = "+server2.mean_service+" minutes\n");
		System.out.println("Number of customer = "+ total_customer + "\n\n");
		 
		random = new Random();
		 
		server1.initialize();
		server2.initialize();
		 
		System.out.println("\nEnter simulation time = ");
		double sim_time = sc.nextDouble();
		while(server1.sim_time < sim_time)
		{
			server1_activity();
		}
		
		
		System.out.println("\n\nReport for server 1:\n--------------------\n");
		server1.report();
		System.out.println("\n\nReport for server 2:\n--------------------\n");
		server2.report();
		System.out.println("\n\nTime simulation ended = "+ (server2.sim_time) +"\n");
	}
	 
	private static void server1_activity()
	{
		server1.timing();
		 
		server1.update_time_avg_stats();
		 
		switch (server1.next_event_type)
		{
			case 1: 
				server1.arrive(server1.sim_time+expon(server1.mean_interarrival));
				break;
			case 2: 
				server1.depart();
			if(random.nextDouble() <= 0.3)
			{
				server2_activity(server1.sim_time);
			}
			break;
			
		}
	}
	 
	private static void server2_activity(double time)
	{
		server2.timing();
		 
		server2.update_time_avg_stats();
		 
		switch (server2.next_event_type)
		{
			case 1: 
				server2.arrive(time);
				break;
			case 2: 
				server2.depart();
			server2_activity(time);
		}
	}
	 
	public static double expon(double  mean)
	{
		return -mean * Math.log(random.nextDouble());
	}
	
	public static double uniform(double a, double b)
	{
		Random ran = new Random();
		return (double) (a+((b-a)*ran.nextDouble()));
	}
}