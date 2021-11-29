package trivzia.jnas.datamining.updated;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import redis.clients.jedis.Jedis;
import trivzia.jnas.datamining.DataMiningBo;
import trivzia.jnas.datamining.DataminingHelper;

public class PollDataController1 extends DataminingHelper {
//	static String root="/usr/local/src/SmartFoxServer_2X/SFS2X/data/";
	static String root = "C:\\Users\\LENOVO\\Downloads\\PollDb\\";

	public static void main(String[] args) {
		ArrayList<DataMiningBo> polluser = new ArrayList<DataMiningBo>();
		String realtimeTestValue = "";
		if (args.length > 0) {
			realtimeTestValue = args[0];
		}
		int count = 0;
		String resourceName = root + "12.csv";

		// Try block to check exception
		try {

			InputStream is = PollDataController1.class.getResourceAsStream(resourceName);
			if (is == null) {
				throw new NullPointerException("Cannot find resource file " + resourceName);
			}

			JSONTokener tokener = new JSONTokener(is);

			JSONArray a = null;
			try {
				a = new JSONArray(tokener);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			JSONArray iparr = new JSONArray();
			JSONArray ipres = null;
			int ipcount = 0;
			for (int i = 0; i < a.length(); i++) {
				count++;
				JSONObject root = a.getJSONObject(i);

				if (root.has("ip_address") && !root.getString("ip_address").equals("")
						&& root.getString("ip_address") != null) {
//				json = readJsonFromUrl("http://ip-api.com/json/" + root.getString("ip_address"));

					if (ipcount <= 100) {
						iparr.put(root.getString("ip_address"));
						ipcount++;
					}

					if (ipcount == 100) {
						System.out.println(iparr.toString());
						ipres = readJsonFromUrl(iparr);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(ipres.toString());
						Jedis jedis = writeConnection(8);
						for (int z = 0; z <= ipcount - 1; z++) {
							JSONObject ip = ipres.getJSONObject(z);
							jedis.set(ip.getString("query"), ip.toString());
						}
						ipcount = 0;
						iparr = new JSONArray(new ArrayList<String>());
					}

				}

			}

		}

		// Catch block to handle exceptions
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}