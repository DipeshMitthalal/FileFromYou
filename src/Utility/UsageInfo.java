package Utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UsageInfo {
String pcName;
int total_no_of_cores;
int cpuUsage;
long time;
int pID;
private double totalCPUUsage;

public UsageInfo(String pcName2, long processId, double processCpuLoad,
		double load, int noOfCores) {
	// TODO Auto-generated constructor stub
	this.time = System.currentTimeMillis();
	this.pcName = pcName2;
	this.pID = (int)processId;
	this.cpuUsage = (int) (load);
	this.total_no_of_cores = noOfCores;
	this.totalCPUUsage = processCpuLoad;
	
}
public double getTotalCPUUsage() {
	return totalCPUUsage;
}
public void setTotalCPUUsage(double totalCPUUsage) {
	this.totalCPUUsage = totalCPUUsage;
}
public String getPcName() {
	return pcName;
}
public void setPcName(String pcName) {
	this.pcName = pcName;
}
public int getTotal_no_of_cores() {
	return total_no_of_cores;
}
public void setTotal_no_of_cores(int total_no_of_cores) {
	this.total_no_of_cores = total_no_of_cores;
}
public int getCpuUsage() {
	return cpuUsage;
}
public void setCpuUsage(int cpuUsage) {
	this.cpuUsage = cpuUsage;
}
public long getTime() {
	return time;
}
public void setTime(long time) {
	this.time = time;
}
public int getpID() {
	return pID;
}
public void setpID(int pID) {
	this.pID = pID;
}
}
