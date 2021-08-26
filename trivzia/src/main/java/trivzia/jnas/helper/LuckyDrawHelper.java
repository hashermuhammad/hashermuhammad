package trivzia.jnas.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import redis.clients.jedis.Jedis;
import trivzia.jnas.dao.DbConnectionDao;

public class LuckyDrawHelper extends DbConnectionDao
{
	public static String NEW_USER="newUser";
	public static String OLD_USER="oldUser";
	static String root="/usr/local/src/SmartFoxServer_2X/SFS2X/data";
//	static String root="C:\\Users\\LENOVO\\Downloads\\";
	
		
	

	public String readFile(String path)
	{
		try
		{
			File file = new File(path);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
				sb.append("\n");
			}
			fr.close();

			return sb.toString();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;

	}

	public String getUserType(String date)
	{
		String userType = "oldUser";
		try
		{  
			if(date !=null && !date.equals("") )
			{DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date sdate2= new Date();
	        String dateToString = df.format(sdate2);
			
	    //    System.out.println("Parsing date is  " + Date);
			
		    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
		    Date date3=new SimpleDateFormat("yyyy-MM-dd").parse(dateToString);
		    
		   
			boolean diff = date3.after(date1);
			if (diff)
			{
				userType = "oldUser";
			}else {
				userType = "newUser";
			}
			}
		}
		catch (ParseException e)
		{
			
			System.out.println(" error in comparing dates for luckydraw " + e);
		}

		return userType;

	}

	public  void setResultDocumentOldUser(Jedis jedis, 
			JSONObject range,ArrayList<Integer>userCountOldJsonFile,int index)
	{
		int size = range.getInt("numberOfUsers");
		int perthreadsize=Math.round(size);

		// percentage of old user
		float olduserper=range.getInt("perOldUsers");
		float oldUsers = Math.round((olduserper/100) * perthreadsize);
		int oldUserPer=(int) oldUsers;
		userCountOldJsonFile.add(index,oldUserPer);

	}

	public void setGiveAwayResultDocument(Jedis jedis,ArrayList<String> totalarr, JSONObject obj,
			int totalUsers,ArrayList<Integer> userCountGAJsonFile,int subOnline)
	{
		
		int giveAwayUsersCount = Math.round((totalUsers / 2));
		int finalloopcount=(giveAwayUsersCount-subOnline);
		setGAResult(jedis, finalloopcount, obj,userCountGAJsonFile);
	}
	
	
	public  void setGAResult(Jedis jedis,int giveAwayUsersCount, JSONObject obj,ArrayList<Integer> userCountGAJsonFile)
	{
		
		
		JSONObject range0  = obj.getJSONObject("range0");
		JSONObject range1  = obj.getJSONObject("range1");
		JSONObject range2  = obj.getJSONObject("range2");
		JSONObject range3  = obj.getJSONObject("range3");
		JSONObject range4  = obj.getJSONObject("range4");

		
		// Percentage of  user
		float userPerR0 = range0.getInt("numberOfUsers") ;
		float userPerR1 = range1.getInt("numberOfUsers") ;
		float userPerR2 = range2.getInt("numberOfUsers") ;
		float userPerR3 = range3.getInt("numberOfUsers") ;
		float userPerR4 = range4.getInt("numberOfUsers") ;
		
		float a=Math.round((userPerR0 / 100) * giveAwayUsersCount);
		float b=Math.round((userPerR1 / 100) * giveAwayUsersCount);
		float c=Math.round((userPerR2 / 100) * giveAwayUsersCount);
		float d=Math.round((userPerR3 / 100) * giveAwayUsersCount);
		float e=Math.round((userPerR4 / 100) * giveAwayUsersCount);
		
		
		int giveAwayR0= (int)a;
		int giveAwayR1= (int)b;
		int giveAwayR2= (int)c;
		int giveAwayR3= (int)d;
		int giveAwayR4= (int)e;

		userCountGAJsonFile.add(0, giveAwayR0);
		userCountGAJsonFile.add(1, giveAwayR1);
		userCountGAJsonFile.add(2, giveAwayR2);
		userCountGAJsonFile.add(3, giveAwayR3);
		userCountGAJsonFile.add(4, giveAwayR4);

		
		

	}
	public static void createLuckyWinnerFile(String luckyWinnersJSON,String filename) throws IOException {
		// System.out.println("Creating file at path : " + rootPath + "Winner.txt");
		File file = new File(root, filename);
		
		// Create the file
		if (file.createNewFile())
		{
			System.out.println("File is created!");
			FileUtils.writeStringToFile(file, luckyWinnersJSON, Charset.defaultCharset());
		}
		else
		{
			FileWriter writer = new FileWriter(file, true);
			writer.write("\r\n");
			writer.write(luckyWinnersJSON);
			writer.close();
			System.out.println("File already exists.");
		}

		// Write Content

		System.out.println(" file write" + filename + " completed.");
	}
	
	
	
	
	public  void setResultDocumentNewUser(Jedis jedis, 
			JSONObject range,ArrayList<Integer>userCountNewJsonFile,int index)
	{
		int size = range.getInt("numberOfUsers");
		int perthreadsize=Math.round(size);
		// Percentage of new user
		float userper=range.getInt("perNewUsers");
		float users=Math.round( (userper/100) * perthreadsize);
		int newUserPer=(int) users;
		userCountNewJsonFile.add(index,newUserPer);

	}
	
	public JSONArray getSortedData(JSONArray jsonArr,String key)
	{
		
		
	    JSONArray sortedJsonArray = new JSONArray();

	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jsonArr.length(); i++) {
	        jsonValues.add(jsonArr.getJSONObject(i));
	    }
	    Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        //You can change "Name" with "ID" if you want to sort by ID
	   //     private static final String KEY_NAME = "quantity";
	    	 private final String KEY_NAME = key;

	        @Override
	        public int compare(JSONObject a, JSONObject b) {
	            Integer valA = -1;
	            Integer valB =-1;

	            try {
	                valA =  a.getInt(KEY_NAME);
	                valB =  b.getInt(KEY_NAME);
	            } 
	            catch (JSONException e) {
	               System.out.println("This is exception"+e);
	            }

	            return valB.compareTo(valA);
	            //if you want to change the sort order, simply use the following:
	            //return -valA.compareTo(valB);
	        }
	    });

	    for (int i = 0; i < jsonArr.length(); i++) {
	        sortedJsonArray.put(jsonValues.get(i));
	    }
		return sortedJsonArray; 
	}
	
	
	public static void appendLuckyWinnerFile(JSONObject luckyWinnersJSON, String filename, JSONArray luckyWinnersJSONArr)
			throws IOException {
		// System.out.println("Creating file at path : " + rootPath + "Winner.txt");
		File file = new File(root, filename);
		// Create the file
		try {
			InputStream in = new FileInputStream(file);
			String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
			System.out.println(contents);
			 JSONTokener tokener = new JSONTokener(contents);
			JSONObject json = new JSONObject(tokener);

			JSONArray jsonObjecctArray = json.getJSONArray("lucky_winners");
			jsonObjecctArray.putAll(luckyWinnersJSONArr);
			luckyWinnersJSON.put("lucky_winners", jsonObjecctArray);
		} catch (IOException e) {
			System.out.println("Error in Append File" + e);
		} catch (JSONException e1) {
			System.out.println("Error in Append File json" + e1);
		}

		FileUtils.writeStringToFile(file, luckyWinnersJSON.toString(), Charset.defaultCharset());
		System.out.println("File already exists.");

	}
}
