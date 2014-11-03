import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hyperic.sigar.SigarException;




public class Solution {

	

	public static void main(String[] args){
		try {
			String hostname = "Unknown";
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
			System.out.println(hostname);

			try {
				CPUUsageMonitor l = new CPUUsageMonitor();
			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrimeCalculator p = new PrimeCalculator(1000);
			for(int i =0; i<200; i++){

				p.prime();
				p.prime();
				p.prime();
				double t = Math.random() * Math.random();

				//System.out.println("===============");
				//System.out.println(l.getPrevPc().getPercent());
			}


		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}

