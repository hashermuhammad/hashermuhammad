package trivzia.jnas.datamining;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import redis.clients.jedis.Jedis;

public class PollDataController1 extends DataminingHelper {
//	static String root="/usr/local/src/SmartFoxServer_2X/SFS2X/data/";
	static String root = "C:\\Users\\LENOVO\\Downloads\\PollDb\\";

	public static void main(String[] args) {
		ArrayList<DataMiningBo> polluser = new ArrayList<DataMiningBo>();
		String realtimeTestValue = "";
		if (args.length > 0) {
			realtimeTestValue = args[0];
		}
//		JSONParser parser = new JSONParser();
		int count = 0;
		String resourceName = root + "12.csv";

		// Creating file object and specify file path
		File file = new File(resourceName);
		// create a stream to read contents of that file

		// Try block to check exception
		try {

			CsvSchema csv = CsvSchema.emptySchema().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
					.readValues(file);
			List<Map<?, ?>> list = mappingIterator.readAll();

			JSONArray a = null;
			try {
				a = new JSONArray(list);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			JSONArray iparr= new JSONArray();
			JSONArray ipres=null;
			int ipcount=0;
			for (int i = 0; i < a.length(); i++) {
				count++;
				JSONObject root = a.getJSONObject(i);
				

				if (root.has("ip_address") && !root.getString("ip_address").equals("") && root.getString("ip_address") != null) {
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