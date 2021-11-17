package trivzia.jnas.datamining;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

public class PollDataController1 extends DataminingHelper {
//	static String root="/usr/local/src/SmartFoxServer_2X/SFS2X/data/";
	static String root = "C:\\Users\\LENOVO\\Downloads\\";

	public static void main(String[] args) {
		ArrayList<DataMiningBo> polluser = new ArrayList<DataMiningBo>();
		String realtimeTestValue = "";
		if (args.length > 0) {
			realtimeTestValue = args[0];
		}
//		JSONParser parser = new JSONParser();
		int count = 0;
		String resourceName = root + realtimeTestValue + ".json";

		// Creating file object and specify file path
		File file = new File(resourceName);
		// create a stream to read contents of that file

		// Try block to check exception
		try {

			FileInputStream input = new FileInputStream(file);
			// Representing input object in form of string

			String contents = IOUtils.toString(input, Charset.forName("UTF-8"));

			// Print contents of that file
			// System.out.println(contents);
			JSONArray a = null;
			try {
				a = new JSONArray(contents);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			JSONArray iparr= new JSONArray();
			JSONArray ipres=null;
			int ipcount=0;
			for (int i = 0; i < a.length(); i++) {
				count++;
				JSONObject root = a.getJSONObject(i);
				

				if (root.has("ip_address") && !root.getString("ip_address").equals("") && root.getString("ip_address") != null && root.has("Poll33") && root.get("Poll33")!="") {
//				json = readJsonFromUrl("http://ip-api.com/json/" + root.getString("ip_address"));
				
				if(ipcount<=100)
				{
					iparr.put(root.getString("ip_address"));
					ipcount++;
				}
				
				if(ipcount==100)
				{
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
					for(int z=0;z<=ipcount-1;z++)
					{
						JSONObject ip = ipres.getJSONObject(z);
						jedis.set(ip.getString("query"), ip.toString());	
					}
					ipcount=0;	
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