import java.net.UnknownHostException;

import Utility.UsageInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;


public class DataUpdater {
	Mongo mongo;
	DB db;
	DBCollection collection;

	public DataUpdater() throws UnknownHostException {
		// TODO Auto-generated constructor stub
		mongo = new Mongo("ec2-54-76-141-109.eu-west-1.compute.amazonaws.com", 27017);
		db = mongo.getDB("test");
		collection = db.getCollection("CPUMonitor");
	}

    public void pushData(UsageInfo usageInfo){
    	BasicDBObject stat = new BasicDBObject();
		stat.put("pcname", usageInfo.getPcName());
		stat.put("pid",usageInfo.getpID());
		stat.put("noofcores",usageInfo.getTotal_no_of_cores());
		stat.put("totalCPUUsage",usageInfo.getTotalCPUUsage());
		stat.put("usagebythisprocess",usageInfo.getCpuUsage());
		stat.put("time",usageInfo.getTime());
		collection.insert(stat);
		
    }

	
}

