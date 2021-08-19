package trivzia.jnas.luckydraw.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.Range;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;

import redis.clients.jedis.Jedis;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawHelper;

public class LuckyDrawCalculation extends LuckyDrawHelper implements Runnable 
{
	String gameType = "RealtimeTest";
	String root = "/usr/local/src/SmartFoxServer_2X/SFS2X/data";
//	 String root="C:\\Users\\LENOVO\\Downloads\\";
	FireStoreConnection fb;
	ArrayList<String> keys;
	int threadNumber=0;
	LuckyDrawController luckyDrawController=null;
	int id=0;
	int luckydrawCount;
	LuckyDrawCalculation(boolean test, FireStoreConnection fb, ArrayList<String> keys,LuckyDrawController luckyDrawController,int id,int luckydrawCount)
	{
		if (test)
		{

			this.gameType = "RealtimeTest";
		}
		else
		{

			this.gameType = "Realtime";
		}
		this.fb = fb;
		this.keys = keys;
		this.luckyDrawController=luckyDrawController;
		this.id=id;
		this.luckydrawCount=luckydrawCount;
	}
	@Override
	public void run()
	{
		callBusinessLogic();
		
	}
	public void callBusinessLogic()
	{
		long start4 = System.currentTimeMillis();
		Collections.shuffle(keys, new Random());
		long End4 = System.currentTimeMillis();	
		 System.out.println("Time for Shuffle Thread:-"
					+ (End4 - start4));
		String path = root + File.separator + "luckydraw.json";
		String giveAwaypath = root + File.separator + "giveaway.json";
		String jsonFile = readFile(path);
		JSONObject object = new JSONObject(jsonFile);
		String giveAwayJsonFile = readFile(giveAwaypath);
		JSONObject giveAwayObject = new JSONObject(giveAwayJsonFile);

		/*-------------- LuckyDraw Criteria Objects Created----------*/

		JSONObject range0 = object.getJSONObject("range0");
		JSONObject range1 = object.getJSONObject("range1");
		JSONObject range2 = object.getJSONObject("range2");
		JSONObject range3 = object.getJSONObject("range3");
		JSONObject range4 = object.getJSONObject("range4");
		JSONObject range5 = object.getJSONObject("range5");
		JSONObject range6 = object.getJSONObject("range6");
		JSONObject range7 = object.getJSONObject("range7");
		JSONObject range8 = object.getJSONObject("range8");
		JSONObject range9 = object.getJSONObject("range9");
		JSONObject range10 = object.getJSONObject("range10");

		/*------------- LuckyDraw Criteria Objects END----------------*/
		
		
		
		
		JSONObject garange0  = giveAwayObject.getJSONObject("range0");
		JSONObject garange1  = giveAwayObject.getJSONObject("range1");
		JSONObject garange2  = giveAwayObject.getJSONObject("range2");
		JSONObject garange3  = giveAwayObject.getJSONObject("range3");
		JSONObject garange4  = giveAwayObject.getJSONObject("range4");

		ArrayList<Integer> userCountOldJsonFile = new ArrayList<Integer>();
		userCountOldJsonFile.add(0, 0);
		userCountOldJsonFile.add(1, 0);
		userCountOldJsonFile.add(2, 0);
		userCountOldJsonFile.add(3, 0);
		userCountOldJsonFile.add(4, 0);
		userCountOldJsonFile.add(5, 0);
		userCountOldJsonFile.add(6, 0);
		userCountOldJsonFile.add(7, 0);
		userCountOldJsonFile.add(8, 0);
		userCountOldJsonFile.add(9, 0);
		userCountOldJsonFile.add(10, 0);
		ArrayList<Integer> userCountNewJsonFile = new ArrayList<Integer>();
		userCountNewJsonFile.add(0, 0);
		userCountNewJsonFile.add(1, 0);
		userCountNewJsonFile.add(2, 0);
		userCountNewJsonFile.add(3, 0);
		userCountNewJsonFile.add(4, 0);
		userCountNewJsonFile.add(5, 0);
		userCountNewJsonFile.add(6, 0);
		userCountNewJsonFile.add(7, 0);
		userCountNewJsonFile.add(8, 0);
		userCountNewJsonFile.add(9, 0);
		userCountNewJsonFile.add(10, 0);
		ArrayList<Integer> userCountGAJsonFile = new ArrayList<Integer>();
		userCountGAJsonFile.add(0, 0);
		userCountGAJsonFile.add(1, 0);
		userCountGAJsonFile.add(2, 0);
		userCountGAJsonFile.add(3, 0);
		userCountGAJsonFile.add(4, 0);
		Jedis jedis = getConnection();
		

		
		
		/*
		 * int totalOnlineUser = 0; int oldUserRange0=0; int newUserRange0=0;
		 * int oldUserRange1=0; int newUserRange1=0; int oldUserRange2=0; int
		 * newUserRange2=0; int oldUserRange3=0; int newUserRange3=0; int
		 * oldUserRange4=0; int newUserRange4=0; int oldUserRange5=0; int
		 * newUserRange5=0; int oldUserRange6=0; int newUserRange6=0; int
		 * oldUserRange7=0; int newUserRange7=0; int oldUserRange8=0; int
		 * newUserRange8=0; int oldUserRange9=0; int newUserRange9=0; int
		 * oldUserRange10=0; int newUserRange10=0; int giveAwayR0=0; int
		 * giveAwayR1=0; int giveAwayR2=0; int giveAwayR4=0; int giveAwayR3=0;
		 */
		
		long start1 = System.currentTimeMillis();
		// For Range 0
		setResultDocumentOldUser(jedis, range0, userCountOldJsonFile,0);
		setResultDocumentNewUser(jedis, range0, userCountNewJsonFile,0);
		// For Range 1
		setResultDocumentOldUser(jedis, range1, userCountOldJsonFile,1);
		setResultDocumentNewUser(jedis, range1, userCountNewJsonFile,1);
		// For Range 2
		setResultDocumentOldUser(jedis, range2, userCountOldJsonFile,2);
		setResultDocumentNewUser(jedis, range2, userCountNewJsonFile,2);
		// For Range 3
		setResultDocumentOldUser(jedis, range3, userCountOldJsonFile,3);
		setResultDocumentNewUser(jedis, range3, userCountNewJsonFile,3);
		// For Range 4
		setResultDocumentOldUser(jedis, range4, userCountOldJsonFile,4);
		setResultDocumentNewUser(jedis, range4, userCountNewJsonFile,4);
		// For Range 5
		setResultDocumentOldUser(jedis, range5, userCountOldJsonFile,5);
		setResultDocumentNewUser(jedis, range5, userCountNewJsonFile,5);
		// For Range 6
		setResultDocumentOldUser(jedis, range6, userCountOldJsonFile,6);
		setResultDocumentNewUser(jedis, range6, userCountNewJsonFile,6);
		// For Range 7
		setResultDocumentOldUser(jedis, range7, userCountOldJsonFile,7);
		setResultDocumentNewUser(jedis, range7, userCountNewJsonFile,7);
		// For Range 8
		setResultDocumentOldUser(jedis, range8, userCountOldJsonFile,8);
		setResultDocumentNewUser(jedis, range8, userCountNewJsonFile,8);
		// For Range 9
		setResultDocumentOldUser(jedis, range9, userCountOldJsonFile,9);
		setResultDocumentNewUser(jedis, range9, userCountNewJsonFile,9);
		// For Range 10
		setResultDocumentOldUser(jedis, range10, userCountOldJsonFile,10);
		setResultDocumentNewUser(jedis, range10, userCountNewJsonFile,10);
		long End1 = System.currentTimeMillis();	
		 System.out.println("Time for calculating user from json Thread:-"
					+ (End1 - start1));
		 long start2 = System.currentTimeMillis();	
		int i = 0;
		for (i = 1; i < keys.size(); i++)
		{

			String key = keys.get(i);
			String value = jedis.get(key);
			JSONObject json = new JSONObject(value);
			if (json.has("Online") && json.getBoolean("Online"))
			{
				luckyDrawController.totalOnlineUser++;
			}
		}
		System.out.println("Total Online User"+luckyDrawController.totalOnlineUser);
		long End2 = System.currentTimeMillis();	
		 System.out.println("Time for calculating Online Users Thread:-"
					+ (End2 - start2));
		
		 
		 
		 
			Range<Integer> category1 = Range.between(range0.getInt("minUserBalance"),
					range0.getInt("maxUserBalance"));

			Range<Integer> category2 = Range.between(range1.getInt("minUserBalance"),
					range1.getInt("maxUserBalance"));

			Range<Integer> category3 = Range.between(range2.getInt("minUserBalance"),
					range2.getInt("maxUserBalance"));

			Range<Integer> category4 = Range.between(range3.getInt("minUserBalance"),
					range3.getInt("maxUserBalance"));

			Range<Integer> category5 = Range.between(range4.getInt("minUserBalance"),
					range4.getInt("maxUserBalance"));

			Range<Integer> category6 = Range.between(range5.getInt("minUserBalance"),
					range5.getInt("maxUserBalance"));

			Range<Integer> category7 = Range.between(range6.getInt("minUserBalance"),
					range6.getInt("maxUserBalance"));

			Range<Integer> category8 = Range.between(range7.getInt("minUserBalance"),
					range7.getInt("maxUserBalance"));

			Range<Integer> category9 = Range.between(range8.getInt("minUserBalance"),
					range8.getInt("maxUserBalance"));

			Range<Integer> category10 = Range.between(range9.getInt("minUserBalance"),
					range9.getInt("maxUserBalance"));

			Range<Integer> category11 = Range.between(range10.getInt("minUserBalance"),
					range10.getInt("maxUserBalance"));
					 
		 
		 
		 int subOnline=(
				 range0.getInt("numberOfUsers")+
				 range1.getInt("numberOfUsers")+
				 range2.getInt("numberOfUsers")+
				 range3.getInt("numberOfUsers")+
				 range4.getInt("numberOfUsers")+
				 range5.getInt("numberOfUsers")+
				 range6.getInt("numberOfUsers")+
				 range7.getInt("numberOfUsers")+
				 range8.getInt("numberOfUsers")+
				 range9.getInt("numberOfUsers")+
				 range10.getInt("numberOfUsers")
				 );
		 
		 
		 
		int j = 0;
		Jedis setjedis = writeConnection(7);
	//	Jedis getjedis = writeConnection(6);
		long start3 = System.currentTimeMillis();
		for (j = 1; j < keys.size(); j++)
		{

			String key = keys.get(j);
			String value = jedis.get(key);
			JSONObject json = new JSONObject(value);
			JSONArray luckywinnerArray=null;
			if (json.has("Online") && json.getBoolean("Online") && json.has("new_balance")
					&& json.has("datetime"))
			{
				int userBalance = json.getInt("new_balance");
				String date = json.getString("datetime");
				String userType = getUserType(date);

				if (luckydrawCount == 1) {
					luckywinnerArray=new JSONArray();
				}
				else
				{
					if(setjedis.exists(key))
					{
					String winnerValue =	setjedis.get(key);
					luckywinnerArray=new JSONArray(winnerValue);
					}
					else
					{
						luckywinnerArray=new JSONArray();
					}
				}


	
					// Category1

					if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(0)>luckyDrawController.newUserRange0)
					{
						
						
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range0.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
						 
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range0.getInt("reward"));
							 */
							
							//++newUserRange0;
							if (userCountNewJsonFile.get(0) > luckyDrawController.newUserRange0)
							{
								
								++luckyDrawController.newUserRange0;
								luckywinnerArray.put(resultJson);
								setjedis.set(key, luckywinnerArray.toString());
	//							getjedis.set(key, resultJson.toString());
								

								System.out.println("hello " + " This is thread number - " + id
										+ "- " + luckyDrawController.newUserRange0);
							}
					}
					// Category2

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(1)>luckyDrawController.newUserRange1)
					{
						
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range1.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range1.getInt("reward"));
							 */
						
						//++newUserRange1;
						if(userCountNewJsonFile.get(1)>luckyDrawController.newUserRange1) {
							
							++luckyDrawController.newUserRange1;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
  //				    getjedis.set(key, resultJson.toString());
						System.out.println("hello r2" + " This is thread number - " + id + "- "
								+ luckyDrawController.newUserRange1);
						}
						
					}
					// Category3

					else if (userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(2)>luckyDrawController.newUserRange2)
					{
						
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range2.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range2.getInt("reward"));
							 */
						//++newUserRange2;
						 
						if (userCountNewJsonFile.get(2) > luckyDrawController.newUserRange2)
						{
							++luckyDrawController.newUserRange2;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
							System.out.println("hello r3 " + " This is thread number - " + id + "- "
									+ luckyDrawController.newUserRange2);
						}
						
					}
					// Category4

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(3)>luckyDrawController.newUserRange3)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range3.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range3.getInt("reward"));
							 */
						
						
						
						//++newUserRange3;
						
						if (userCountNewJsonFile.get(3) > luckyDrawController.newUserRange3)
						{
							++luckyDrawController.newUserRange3;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}
					}
					// Category5

					else if (userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(4)>luckyDrawController.newUserRange4)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range4.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range4.getInt("reward"));
							 */
						
						//++newUserRange4;
						
						if (userCountNewJsonFile.get(4) > luckyDrawController.newUserRange4)
						{
							++luckyDrawController.newUserRange4;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}

					}
					// Category6

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(5)>luckyDrawController.newUserRange5)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range5.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range5.getInt("reward"));
							 */
						
						
						//++newUserRange5;
						
						if (userCountNewJsonFile.get(5) > luckyDrawController.newUserRange5)
						{
						++luckyDrawController.newUserRange5;
						luckywinnerArray.put(resultJson);
						setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}
					}
					// Category7

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(6)>luckyDrawController.newUserRange6)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range6.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range6.getInt("reward"));
							 */
						
						
						//++newUserRange6;
						
						if (userCountNewJsonFile.get(6) > luckyDrawController.newUserRange6)
						{
						++luckyDrawController.newUserRange6;
						luckywinnerArray.put(resultJson);
						setjedis.set(key, luckywinnerArray.toString());
	// 				    getjedis.set(key, resultJson.toString());
						}
					}
					// Category8

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(7)>luckyDrawController.newUserRange7)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range7.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range7.getInt("reward"));
							 */
						
						
					//	++newUserRange7;
						
						if (userCountNewJsonFile.get(7) > luckyDrawController.newUserRange7)
						{
							++luckyDrawController.newUserRange7;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}
					}
					// Category9

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(8)>luckyDrawController.newUserRange8)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range8.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range8.getInt("reward"));
							 */
						
						
						if (userCountNewJsonFile.get(8) > luckyDrawController.newUserRange8)
						{
							++luckyDrawController.newUserRange8;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}
					}
					// Category10

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(9)>luckyDrawController.newUserRange9)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range9.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range9.getInt("reward"));
							 */
						
						if (userCountNewJsonFile.get(9) > luckyDrawController.newUserRange9)
						{
							++luckyDrawController.newUserRange9;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}
					}
					// Category11

					else if ( userType.equalsIgnoreCase(NEW_USER) && userCountNewJsonFile.get(10)>luckyDrawController.newUserRange10)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range10.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range10.getInt("reward"));
							 */
						
						
						if (userCountNewJsonFile.get(10) > luckyDrawController.newUserRange10)
						{
							++luckyDrawController.newUserRange10;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					getjedis.set(key, resultJson.toString());
						}
					}
					
					// Category1

					else if (category1.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(0)>luckyDrawController.oldUserRange0)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range0.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range0.getInt("reward"));
							 */
						
						
						//++oldUserRange0;
						if(userCountOldJsonFile.get(0)>luckyDrawController.oldUserRange0) {
							++luckyDrawController.oldUserRange0;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
						
					}
					// Category2

					else if (category2.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(1)>luckyDrawController.oldUserRange1)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range1.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range1.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(1)>luckyDrawController.oldUserRange1) {
							++luckyDrawController.oldUserRange1;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//					    getjedis.set(key, resultJson.toString());
						}
					}
					// Category3

					else if (category3.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(2)>luckyDrawController.oldUserRange2)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range2.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range2.getInt("reward"));
							 */
						
						
						if(userCountOldJsonFile.get(2)>luckyDrawController.oldUserRange2) {
							++luckyDrawController.oldUserRange2;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category4

					else if (category4.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(3)>luckyDrawController.oldUserRange3)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range3.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range3.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(3)>luckyDrawController.oldUserRange3) {
							++luckyDrawController.oldUserRange3;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category5

					else if (category5.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(4)>luckyDrawController.oldUserRange4)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range4.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range4.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(4)>luckyDrawController.oldUserRange4) {
							++luckyDrawController.oldUserRange4;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category6

					else if (category6.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(5)>luckyDrawController.oldUserRange5)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range5.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range5.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(5)>luckyDrawController.oldUserRange5) {
							++luckyDrawController.oldUserRange5;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category7

					else if (category7.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(6)>luckyDrawController.oldUserRange6)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range6.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range6.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(6)>luckyDrawController.oldUserRange6) {
							++luckyDrawController.oldUserRange6;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category8

					else if (category8.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(7)>luckyDrawController.oldUserRange7)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range7.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range7.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(7)>luckyDrawController.oldUserRange7) {
							++luckyDrawController.oldUserRange7;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category9

					else if (category9.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(8)>luckyDrawController.oldUserRange8)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range8.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
						 
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range8.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(8)>luckyDrawController.oldUserRange8) {
							++luckyDrawController.oldUserRange8;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category10

					else if (category10.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(9)>luckyDrawController.oldUserRange9)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range9.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range9.getInt("reward"));
							 */
						
						if(userCountOldJsonFile.get(9)>luckyDrawController.oldUserRange9) {
							++luckyDrawController.oldUserRange9;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					// Category11

					else if (category11.contains(userBalance) && userType.equalsIgnoreCase(OLD_USER) && userCountOldJsonFile.get(10)>luckyDrawController.oldUserRange10)
					{
						
						  JSONObject resultJson = new JSONObject();
						  resultJson.put("phone", json.getString("phone"));
						  resultJson.put("accountcode",
						  json.getString("accountcode"));
						  resultJson.put("username",
						  json.getString("username"));
						  resultJson.put("rewardType", "gamepoint");
						  resultJson.put("quantity", range10.getInt("reward"));
						  resultJson.put("billing", false);
						  resultJson.put("LuckyDraw", luckydrawCount);
							/*
							 * JSONObject resultJson = new JSONObject();
							 * resultJson.put("phone", json.getString("phone"));
							 * resultJson.put("accountcode",
							 * json.getString("accountcode"));
							 * resultJson.put("username",
							 * json.getString("username"));
							 * resultJson.put("event", "lucky_winners");
							 * resultJson.put("amount",
							 * range10.getInt("reward"));
							 */
						
						
						if(userCountOldJsonFile.get(10)>luckyDrawController.oldUserRange10) {
							++luckyDrawController.oldUserRange10;
							luckywinnerArray.put(resultJson);
							setjedis.set(key, luckywinnerArray.toString());
	//						getjedis.set(key, resultJson.toString());
						}
					}
					else
					{
						
						setGiveAwayResultDocument(jedis, keys, giveAwayObject,
								luckyDrawController.totalOnlineUser,userCountGAJsonFile,subOnline);
						
						if (userCountGAJsonFile.get(0)>luckyDrawController.giveAwayR0)
						{
							JSONObject resultJson = new JSONObject();
							resultJson.put("phone", json.getString("phone"));
							resultJson.put("accountcode", json.getString("accountcode"));
							resultJson.put("username", json.getString("username"));
							resultJson.put("rewardType", garange0.getString("reward"));
							resultJson.put("quantity", garange0.getInt("quantity"));
							resultJson.put("billing", false);
							 resultJson.put("LuckyDraw", luckydrawCount);
							if(userCountGAJsonFile.get(0)>luckyDrawController.giveAwayR0) {
								++luckyDrawController.giveAwayR0;
								luckywinnerArray.put(resultJson);
								setjedis.set(key, luckywinnerArray.toString());
		//						getjedis.set(key, resultJson.toString());
							}
							
						}
						else if (userCountGAJsonFile.get(1)>luckyDrawController.giveAwayR1)
						{
							JSONObject resultJson = new JSONObject();
							resultJson.put("phone", json.getString("phone"));
							resultJson.put("accountcode", json.getString("accountcode"));
							resultJson.put("username", json.getString("username"));
							resultJson.put("rewardType", garange1.getString("reward"));
							resultJson.put("quantity", garange1.getInt("quantity"));
							resultJson.put("billing", false);
							 resultJson.put("LuckyDraw", luckydrawCount);
							if(userCountGAJsonFile.get(1)>luckyDrawController.giveAwayR1) {
								++luckyDrawController.giveAwayR1;
								luckywinnerArray.put(resultJson);
								setjedis.set(key, luckywinnerArray.toString());
		//						getjedis.set(key, resultJson.toString());
							}
						}
						else if (userCountGAJsonFile.get(2)>luckyDrawController.giveAwayR2)
						{
							JSONObject resultJson = new JSONObject();
							resultJson.put("phone", json.getString("phone"));
							resultJson.put("accountcode", json.getString("accountcode"));
							resultJson.put("username", json.getString("username"));
							resultJson.put("rewardType", garange2.getString("reward"));
							resultJson.put("quantity", garange2.getInt("quantity"));
							resultJson.put("billing", false);
							 resultJson.put("LuckyDraw", luckydrawCount);
							if(userCountGAJsonFile.get(2)>luckyDrawController.giveAwayR2) {
								++luckyDrawController.giveAwayR2;
								luckywinnerArray.put(resultJson);
								setjedis.set(key, luckywinnerArray.toString());
		//						getjedis.set(key, resultJson.toString());
							}
						}
						else if (userCountGAJsonFile.get(3)>luckyDrawController.giveAwayR3)
						{
							JSONObject resultJson = new JSONObject();
							resultJson.put("phone", json.getString("phone"));
							resultJson.put("accountcode", json.getString("accountcode"));
							resultJson.put("username", json.getString("username"));
							resultJson.put("rewardType", garange3.getString("reward"));
							resultJson.put("quantity", garange3.getInt("quantity"));
							resultJson.put("billing", false);
							 resultJson.put("LuckyDraw", luckydrawCount);
							if(userCountGAJsonFile.get(3)>luckyDrawController.giveAwayR3) {
								++luckyDrawController.giveAwayR3;
								luckywinnerArray.put(resultJson);
								setjedis.set(key, luckywinnerArray.toString());
		//						getjedis.set(key, resultJson.toString());
							}
						}
						else if (userCountGAJsonFile.get(4)>luckyDrawController.giveAwayR4)
						{
							JSONObject resultJson = new JSONObject();
							resultJson.put("phone", json.getString("phone"));
							resultJson.put("accountcode", json.getString("accountcode"));
							resultJson.put("username", json.getString("username"));
							resultJson.put("rewardType", garange4.getString("reward"));
							resultJson.put("quantity", garange4.getInt("quantity"));
							resultJson.put("billing", false);
							 resultJson.put("LuckyDraw", luckydrawCount);
							if(userCountGAJsonFile.get(4)>luckyDrawController.giveAwayR4) {
								++luckyDrawController.giveAwayR4;
								luckywinnerArray.put(resultJson);
								setjedis.set(key, luckywinnerArray.toString());
		//						getjedis.set(key, resultJson.toString());
							}
						}
						
					}
					
					
					if (userCountGAJsonFile.get(4) == luckyDrawController.giveAwayR4 && userCountGAJsonFile.get(3) == luckyDrawController.giveAwayR3
							&& userCountGAJsonFile.get(2) == luckyDrawController.giveAwayR2
							&& userCountGAJsonFile.get(1) == luckyDrawController.giveAwayR1
							&& userCountGAJsonFile.get(0) == luckyDrawController.giveAwayR0
							&& userCountOldJsonFile.get(0) == luckyDrawController.oldUserRange0
							&& userCountOldJsonFile.get(1) == luckyDrawController.oldUserRange1
							&& userCountOldJsonFile.get(2) == luckyDrawController.oldUserRange2
							&& userCountOldJsonFile.get(3) == luckyDrawController.oldUserRange3
							&& userCountOldJsonFile.get(4) == luckyDrawController.oldUserRange4
							&& userCountOldJsonFile.get(5) == luckyDrawController.oldUserRange5
							&& userCountOldJsonFile.get(6) == luckyDrawController.oldUserRange6
							&& userCountOldJsonFile.get(7) == luckyDrawController.oldUserRange7
							&& userCountOldJsonFile.get(8) == luckyDrawController.oldUserRange8
							&& userCountOldJsonFile.get(9) == luckyDrawController.oldUserRange9
							&& userCountOldJsonFile.get(10) == luckyDrawController.oldUserRange10
							&& userCountNewJsonFile.get(0) == luckyDrawController.newUserRange0
							&& userCountNewJsonFile.get(1) == luckyDrawController.newUserRange1
							&& userCountNewJsonFile.get(2) == luckyDrawController.newUserRange2
							&& userCountNewJsonFile.get(3) == luckyDrawController.newUserRange3
							&& userCountNewJsonFile.get(4) == luckyDrawController.newUserRange4
							&& userCountNewJsonFile.get(5) == luckyDrawController.newUserRange5
							&& userCountNewJsonFile.get(6) == luckyDrawController.newUserRange6
							&& userCountNewJsonFile.get(7) == luckyDrawController.newUserRange7
							&& userCountNewJsonFile.get(8) == luckyDrawController.newUserRange8
							&& userCountNewJsonFile.get(9) == luckyDrawController.newUserRange9
							&& userCountNewJsonFile.get(10) == luckyDrawController.newUserRange10)
					{
						break;
					}
					
				
			
		/* ----------------------------------------- */

			
		}
	}
		
		System.out.println("assume count For life Line "+userCountGAJsonFile.get(0));
		System.out.println("assume count For Plus 5 "+userCountGAJsonFile.get(1));
		System.out.println("assume count For Silver "+userCountGAJsonFile.get(2));
		System.out.println("assume count For Gold "+userCountGAJsonFile.get(3));
		System.out.println("assume count For Bundle "+userCountGAJsonFile.get(4));
		System.out.println("count For life Line "+luckyDrawController.giveAwayR0);
		System.out.println("count For Plus 5 "+luckyDrawController.giveAwayR1);
		System.out.println("count For Silver "+luckyDrawController.giveAwayR2);
		System.out.println("count For Gold "+luckyDrawController.giveAwayR3);
		System.out.println("count For Bundle "+luckyDrawController.giveAwayR4);
		System.out.println("luckyDrawController.newUserRange0"+luckyDrawController.newUserRange0);
		System.out.println("userCountNewJsonFile.get(0)"+userCountNewJsonFile.get(0));
		
		
		/*
		 * System.out.println("assume count cat1 new "+userCountNewJsonFile.get(
		 * 0));
		 * System.out.println("assume count cat2 new "+userCountNewJsonFile.get(
		 * 1));
		 * System.out.println("assume count cat3 new "+userCountNewJsonFile.get(
		 * 2));
		 * System.out.println("assume count cat4 new "+userCountNewJsonFile.get(
		 * 3));
		 * System.out.println("assume count cat5 new "+userCountNewJsonFile.get(
		 * 4));
		 * System.out.println("assume count cat6 new "+userCountNewJsonFile.get(
		 * 5));
		 * System.out.println("assume count cat7 new "+userCountNewJsonFile.get(
		 * 6));
		 * System.out.println("assume count cat8 new "+userCountNewJsonFile.get(
		 * 7));
		 * System.out.println("assume count cat9 new "+userCountNewJsonFile.get(
		 * 8));
		 * System.out.println("assume count cat10 new "+userCountNewJsonFile.get
		 * (9));
		 * System.out.println("assume count cat11 new "+userCountNewJsonFile.get
		 * (10));
		 * 
		 * 
		 * System.out.println("assume count cat1 old "+userCountOldJsonFile.get(
		 * 0));
		 * System.out.println("assume count cat2 old "+userCountOldJsonFile.get(
		 * 1));
		 * System.out.println("assume count cat3 old "+userCountOldJsonFile.get(
		 * 2));
		 * System.out.println("assume count cat4 old "+userCountOldJsonFile.get(
		 * 3));
		 * System.out.println("assume count cat5 old "+userCountOldJsonFile.get(
		 * 4));
		 * System.out.println("assume count cat6 old "+userCountOldJsonFile.get(
		 * 5));
		 * System.out.println("assume count cat7 old "+userCountOldJsonFile.get(
		 * 6));
		 * System.out.println("assume count cat8 old "+userCountOldJsonFile.get(
		 * 7));
		 * System.out.println("assume count cat9 old "+userCountOldJsonFile.get(
		 * 8));
		 * System.out.println("assume count cat10 old "+userCountOldJsonFile.get
		 * (9));
		 * System.out.println("assume count cat11 old "+userCountOldJsonFile.get
		 * (10));
		 * 
		 * 
		 * System.out.println(" count cat1 new "+newUserRange0);
		 * System.out.println(" count cat2 new "+newUserRange1);
		 * System.out.println(" count cat3 new "+newUserRange2);
		 * System.out.println(" count cat4 new "+newUserRange3);
		 * System.out.println(" count cat5 new "+newUserRange4);
		 * System.out.println(" count cat6 new "+newUserRange5);
		 * System.out.println(" count cat7 new "+newUserRange6);
		 * System.out.println(" count cat8 new "+newUserRange7);
		 * System.out.println(" count cat9 new "+newUserRange8);
		 * System.out.println(" count cat10 new "+newUserRange9);
		 * System.out.println(" count cat11 new "+newUserRange10);
		 * 
		 * 
		 * System.out.println(" count cat1 old "+oldUserRange0);
		 * System.out.println(" count cat2 old "+oldUserRange1);
		 * System.out.println(" count cat3 old "+oldUserRange2);
		 * System.out.println(" count cat4 old "+oldUserRange3);
		 * System.out.println(" count cat5 old "+oldUserRange4);
		 * System.out.println(" count cat6 old "+oldUserRange5);
		 * System.out.println(" count cat7 old "+oldUserRange6);
		 * System.out.println(" count cat8 old "+oldUserRange7);
		 * System.out.println(" count cat9 old "+oldUserRange8);
		 * System.out.println(" count cat10 old "+oldUserRange9);
		 * System.out.println(" count cat11 old "+oldUserRange10);
		 */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		long End3 = System.currentTimeMillis();	
		 System.out.println("Time for calculating GA and GP:-"
					+ (End3 - start3));
		closeConnection(setjedis);
//		closeConnection(getjedis);
	}
	public void winnerJsonArray(JSONArray luckydrawArray)
	{
		JSONObject luckyWinnersObject = new JSONObject();
		Date date = new Date();
		try
		{
			luckyWinnersObject.put("Date", date.toString());
			luckyWinnersObject.put("lucky_draw", luckydrawArray);
			createLuckyWinnerFile(luckyWinnersObject.toString(),"LuckyDraw.txt");
		}
		catch (JSONException | IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("JSON Exception : " + e.toString());
		}
	}

	
}
