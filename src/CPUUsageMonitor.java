import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import Utility.UsageInfo;

class CPUUsageMonitor {

	private static final int TOTAL_TIME_UPDATE_LIMIT = 2000;

	private final Sigar sigar;
	private final int noOfCores;
	private final long processId;
	private final String pcName;
	private ProcCpu previousCPUUsage;
	private double load;
	private  InetAddress addr;
	CpuPerc  totalCpuPerc;
	Cpu cpu;
	private TimerTask updateCPUUSageInfo = new TimerTask() {
		@Override public void run() {
			System.out.println("in timed task");
			try {

				//Get the process's CPU usage
				ProcCpu currentCpuUsage = sigar.getProcCpu(processId);
				//total time a process used CPU
				long totalperiod = currentCpuUsage.getTotal() - previousCPUUsage.getTotal();
				//moment at which the snapshot is reported
				long snapshotperiod = currentCpuUsage.getLastTime() - previousCPUUsage.getLastTime();
				
					//Ratio of time process used CPU during total time period  and the time used at the moment
					load = 100. * totalperiod / snapshotperiod / noOfCores;
					previousCPUUsage = currentCpuUsage;

				
				CpuPerc c[] = sigar.getCpuPercList();
				CpuInfo[] info = sigar.getCpuInfoList();
					new DataUpdater().pushData(new UsageInfo(pcName, processId, getProcessCpuLoad(), load, noOfCores));
				String [] totalCPU = totalCpuPerc.toString().split(" ");
				System.out.println("CPU percentage"+getProcessCpuLoad() );
			//	System.out.println("total cores"+info.length+"...."+ noOfCores);
				System.out.println("load"+ load);
			} catch (SigarException | MalformedObjectNameException | InstanceNotFoundException | ReflectionException | UnknownHostException ex) {
				throw new RuntimeException(ex);

			}}
		};
	

		CPUUsageMonitor() throws SigarException, UnknownHostException {
			//Get the PC name
			addr = InetAddress.getLocalHost();
			pcName = addr.getHostName();

			sigar = new Sigar();

			//Total CPU usage of the system
			totalCpuPerc = sigar.getCpuPerc();
			cpu = sigar.getCpu();
			noOfCores = sigar.getCpuList().length;
			processId = sigar.getPid();

			//get cpu usage of the current process id
			previousCPUUsage = sigar.getProcCpu(processId);

			load = 0;
			new Timer(true).schedule(updateCPUUSageInfo, 0, 1000);
		}

		
		public double getLoad() {
			return load;
		}

		public ProcCpu getPrevPc() {
			return previousCPUUsage;
		}

		public void setPrevPc(ProcCpu prevPc) {
			this.previousCPUUsage = prevPc;
		}

		public Sigar getSigar() {
			return sigar;
		}

		public int getCpuCount() {
			return noOfCores;
		}

		public long getPid() {
			return processId;
		}
		
		public static double getProcessCpuLoad() throws MalformedObjectNameException, ReflectionException, InstanceNotFoundException {

		    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
		    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
		    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

		    if (list.isEmpty())     return Double.NaN;

		    Attribute att = (Attribute)list.get(0);
		    Double value  = (Double)att.getValue();

		    if (value == -1.0)      return Double.NaN;  // usually takes a couple of seconds before we get real values

		    return ((int)(value * 1000) / 10.0);        // returns a percentage value with 1 decimal point precision
		}

	}