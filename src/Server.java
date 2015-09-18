
public class Server
{
	int Queue_size = 32000;
	int next_event_type;
	int num_custs_delayed;
	int num_events=2;
	int num_in_q;
	int server_status;
	int num_delays_required;
	double area_num_in_q;
	double area_server_status;
	double sim_time;
	double time_last_event;
	double total_of_delays;
	double mean_interarrival;
	double mean_service;
	double mean_service1;
	double mean_service2;
	 
	double[] time_arrival = new double[Queue_size];;
	double[] time_next_event=new double[3];
	 
	void initialize()
	{
		sim_time = 0;
		 
		server_status = 0;
		num_in_q = 0;
		time_last_event = 0;
		 
		num_custs_delayed = 0;
		total_of_delays = 0;
		area_num_in_q = 0;
		area_server_status = 0;
		 
		time_next_event[1] = sim_time + MultipleServerQueueingSystem.expon(mean_interarrival) ;
		time_next_event[2] = 1.0e+30;
	}
	 
	public void timing()
	{
		int   i;
		double min_time_next_event = 1.0e+29;
		 
		next_event_type = 0;
		 
		for (i = 1; i <= num_events; ++i)
		{
			if (time_next_event[i] < min_time_next_event)
			{
				min_time_next_event = time_next_event[i];
				next_event_type = i;
			}
		}
	 
	 
		if (next_event_type == 0)
		{
			System.out.println("\nEvent list empty at time"+ sim_time);
		}
		 
		sim_time = min_time_next_event;
	}
	 
	public void arrive(double time)
	{
		double delay;
		time_next_event[1] = time;
		 
		if (server_status == 1)
		{
			num_in_q++;
			if(num_in_q > Queue_size)
			{
				System.out.println("overflow of the array time_arrival at ");
				System.out.println("time " + sim_time);
				System.exit(0);
			}
			time_arrival[num_in_q] = sim_time;
		}
		 
		else
		{
			delay = 0;
			total_of_delays += delay;
			total_of_delays += delay;
			//System.out.println("total arrive departure "+total_of_delays);
			 
			num_custs_delayed++;
			server_status = 1;
			 
			time_next_event[2] = sim_time + MultipleServerQueueingSystem.expon(mean_service);
			//time_next_event[2] = sim_time + MultipleServerQueueingSystem.uniform(15,63);
			//System.out.println("uniform 1st server value"+time_next_event[2]);
		}
	}
	 
	public void depart()
	{
		int   i;
		double delay;
		 
		if (num_in_q == 0)
		{
			server_status = 0;
			time_next_event[2] = 1.0e+30;
		}
		 
		else
		{
			num_in_q--;
			 
			delay = sim_time - time_arrival[1];
			total_of_delays += delay;
			//System.out.println("total delay departure "+total_of_delays);
			 
			num_custs_delayed++;
			//time_next_event[2] = sim_time + MultipleServerQueueingSystem.uniform(126,270);
			time_next_event[2] = sim_time + MultipleServerQueueingSystem.expon(mean_service);
			//System.out.println("uniform 2nd server value"+time_next_event[2]);
			 
			for (i = 1; i <= num_in_q; i++)
			{
				time_arrival[i] = time_arrival[i + 1];
			}
			
		}
	}
	 
	public void report()
	{
		System.out.println( "Total customer uses this server = " + num_custs_delayed + "\n");
		System.out.println( "Average delay in queue = " + total_of_delays / num_custs_delayed + "\n");
		System.out.println( "Average number in queue  = " + area_num_in_q / sim_time + "\n");
		System.out.println( "Server utilization  = " + area_server_status / sim_time + "\n");
	}
	 
	public void update_time_avg_stats()
	{
		double time_since_last_event;
		 
		time_since_last_event = sim_time - time_last_event;
		time_last_event = sim_time;
		 
		area_num_in_q += num_in_q * time_since_last_event;
		 
		area_server_status += server_status * time_since_last_event;
	}
}