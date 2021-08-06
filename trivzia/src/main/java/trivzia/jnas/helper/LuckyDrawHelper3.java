package trivzia.jnas.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import trivzia.jnas.dao.DbConnectionDao;

public class LuckyDrawHelper3 extends DbConnectionDao
{
	public static String NEW_USER="newUser";
	public static String OLD_USER="oldUser";
//	String root="/usr/local/src/SmartFoxServer_2X/SFS2X/data";
	String root="C:\\Users\\LENOVO\\Downloads\\";
	
		
	

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

	public String getUserType(String Date)
	{
		String userType = "oldUser";
		try
		{  
			if(!Date.equals("") && Date !=null)
			{DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date sdate2= new Date();
	        String dateToString = df.format(sdate2);
			
	    //    System.out.println("Parsing date is  " + Date);
			
		    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(Date);
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
	//	int perthreadsize=Math.round(size/10);

		// percentage of old user
		float olduserper=range.getInt("perOldUsers");
		float oldUsers = Math.round((olduserper/100) * size);
		int oldUserPer=(int) oldUsers;
		userCountOldJsonFile.add(index,oldUserPer);

	}

	public void setGiveAwayResultDocument(Jedis jedis,ArrayList<String> totalarr, JSONObject obj,
			int totalUsers,ArrayList<Integer> userCountGAJsonFile,int subOnline)
	{
		int finalloopcount=(totalUsers-Math.round(subOnline/10));
		int giveAwayUsersCount = Math.round((finalloopcount / 2));
		setGAResult(jedis, giveAwayUsersCount, obj,userCountGAJsonFile);
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
	public static void createLuckyWinnerFile(String luckyWinnersJSON) throws IOException {
		//System.out.println("Creating file at path : " + rootPath + "Winner.txt");
		String rootPath="C:\\Users\\LENOVO\\Downloads\\";
	//	String rootPath="/usr/local/src/SmartFoxServer_2X/SFS2X/data";
		File file = new File(rootPath + "/LuckyWinner30.txt");

		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}

		// Write Content
		FileWriter writer = new FileWriter(file);
		writer.write(luckyWinnersJSON);
		writer.close();
		System.out.println("Lucky winners file write completed.");
	}
	
	
	
	
	public  void setResultDocumentNewUser(Jedis jedis, 
			JSONObject range,ArrayList<Integer>userCountNewJsonFile,int index)
	{
		int size = range.getInt("numberOfUsers");
	//	int perthreadsize=Math.round(size/10);
		// Percentage of new user
		float userper=range.getInt("perNewUsers");
		float users=Math.round( (userper/100) * size);
		int newUserPer=(int) users;
		userCountNewJsonFile.add(index,newUserPer);

	}
}
