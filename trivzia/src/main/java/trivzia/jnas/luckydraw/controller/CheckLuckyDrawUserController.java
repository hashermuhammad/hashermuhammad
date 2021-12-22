package trivzia.jnas.luckydraw.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import com.google.common.collect.Lists;

import redis.clients.jedis.Jedis;
import trivzia.jnas.helper.LuckyDrawHelper;

public class CheckLuckyDrawUserController {

	public String realtime = "RealtimeTest";
	Thread t;

	String root = "/usr/local/src/SmartFoxServer_2X/SFS2X/data";
//	String root = "C:\\Users\\LENOVO\\Downloads\\";



	String d1 = null;
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	private static CheckLuckyDrawUserController luckyDrawController = null;
	ConcurrentHashMap<String, JSONObject> criteriaFile = new ConcurrentHashMap<>();

	CheckLuckyDrawUserController(final boolean test) {
		try {
			if (test) {

				realtime = "RealtimeTest";
			} else {

				realtime = "Realtime";
			}
			luckyDrawController = this;
			this.t = Thread.currentThread();

			Jedis jedis = LuckyDrawHelper.writeConnection(8);
			Set<String> jKeys = jedis.keys("*");
			ArrayList<String> keysList = new ArrayList<String>();
			keysList.addAll(jKeys);
			int size = Math.round(keysList.size() / 10);
			ExecutorService es = Executors.newCachedThreadPool();
			// System.out.println("size of keys"+keysList.size());

			for (List<String> partition : Lists.partition(keysList, size)) {
				
				// System.out.println("size of partition"+partition.size());
				final ArrayList<String> arr = new ArrayList<String>(partition);

				es.execute(new CheckLuckyDrawUserImp(test, arr, luckyDrawController));

			}
			es.shutdown();

			boolean finished = es.awaitTermination(15, TimeUnit.SECONDS);
			if (finished) {

			}

			t.join();

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// long start1 = System.currentTimeMillis();
		String realtimeTestValue = "";
		if (args.length > 0) {
			realtimeTestValue = args[0];
		}
		new CheckLuckyDrawUserController(realtimeTestValue.equalsIgnoreCase("test"));

	}

}
