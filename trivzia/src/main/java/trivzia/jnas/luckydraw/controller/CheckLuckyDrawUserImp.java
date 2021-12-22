package trivzia.jnas.luckydraw.controller;

import java.util.ArrayList;

import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import trivzia.jnas.constant.SysConstants;
import trivzia.jnas.helper.LuckyDrawHelper;

public class CheckLuckyDrawUserImp extends LuckyDrawHelper implements Runnable {
	String gameType = "RealtimeTest";
	String root = "/usr/local/src/SmartFoxServer_2X/SFS2X/data";
//	String root = "C:\\Users\\LENOVO\\Downloads\\";
	ArrayList<String> keys;
	int threadNumber = 0;
	CheckLuckyDrawUserController luckyDrawUser = null;

	CheckLuckyDrawUserImp(boolean test, ArrayList<String> keys, CheckLuckyDrawUserController luckyDrawUser) {
		if (test) {

			this.gameType = "RealtimeTest";
		} else {

			this.gameType = "Realtime";
		}
		this.keys = keys;
		this.luckyDrawUser = luckyDrawUser;
	}

	@Override
	public void run() {
		callBusinessLogic();

	}

	public void callBusinessLogic() {
		Jedis weekjedis = writeConnection(8);

		for (int j = 1; j < keys.size(); j++) {

			String key = keys.get(j);
			String value = weekjedis.get(key);
			JSONObject json = new JSONObject(value);
			String windate = json.getString("datetime");
			int numofdays = checkLuckyWinnerDate(windate, json);
			if (numofdays > SysConstants.WINNER_LIMIT) {
				weekjedis.del(key);
				System.out.println("Winner Remove From Expiry"+key);
			}
		}

		closeConnection(weekjedis);
	}
}
