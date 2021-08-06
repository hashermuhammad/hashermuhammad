package trivzia.jnas.dbupload;

import redis.clients.jedis.Jedis;
import trivzia.jnas.dao.LuckyDrawDao;
import trivzia.jnas.helper.LuckyDrawHelper;

public class UploadUserController
{
	public String realtime = "RealtimeTest";
	UploadUserController(boolean test) {
		long start1 = System.currentTimeMillis();
		
		
	LuckyDrawHelper hp= new LuckyDrawHelper();
	Jedis jedis=hp.writeConnection(5);
	LuckyDrawDao dao =new LuckyDrawDao();
	dao.uploadUsers(jedis);
	hp.closeConnection(jedis);
	long end1 = System.currentTimeMillis();
	System.out.println("Elapsed Time in milli seconds: " + (end1 - start1));
		
		
	}
	public static void main(String[] args)
	{
		
	//	long start1 = System.currentTimeMillis();
		String realtimeTestValue = "";
		if(args.length>0)
		{
		realtimeTestValue = args[0];
		}
		UploadUserController upload = new UploadUserController(realtimeTestValue.equalsIgnoreCase("test"));
	
	}
	
}
