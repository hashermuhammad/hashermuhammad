 package trivzia.jnas.luckydraw.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;

import redis.clients.jedis.Jedis;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawHelper30;

public class LuckyDrawController30
{
	public String realtime = "RealtimeTest";
	FireStoreConnection fb = null;
	Thread t;
	 

	LuckyDrawController30(final boolean test)
	{

		try
		{
			if (test)
			{

				realtime = "RealtimeTest";
			}
			else
			{

				realtime = "Realtime";
			}
			fb = new FireStoreConnection();
			this.t = Thread.currentThread();
			DocumentReference docRef = fb.getDb().collection("TheGame").document(realtime);
			docRef.addSnapshotListener(new EventListener<DocumentSnapshot>()
			{
				@Override
				public void onEvent(@Nullable DocumentSnapshot snapshot,
						@Nullable FirestoreException e)
				{
					if (e != null)
					{
						System.err.println("Listen failed: " + e);
						return;
					}

					if (snapshot != null && snapshot.exists())
					{

						System.out.println(
								"Event Listen is " + snapshot.getData().get("lucky_draw_30"));
						if (snapshot.getData().get("lucky_draw_30").equals("LuckyDraw"))
						{
							long start1 = System.currentTimeMillis();
							updateQuestionEvent();
							flushLuckyDrawDb();
							cleanLuckyDrawFile();
							Jedis jedis = LuckyDrawHelper30.getConnection();
							Set<String> jKeys = jedis.keys("*");
							ArrayList<String> keysList = new ArrayList<String>();
							keysList.addAll(jKeys);
							int size = Math.round(keysList.size() / 10);
							ExecutorService es = Executors.newCachedThreadPool();
					//		System.out.println("size of keys"+keysList.size());
							int i=0;
							/*
							 * for (List<String> partition :
							 * Lists.partition(keysList, size)) { ++i; if(i==11)
							 * { break; }
							 */
						//		System.out.println("size of partition"+partition.size());
								final ArrayList<String> arr = new ArrayList<String>(keysList);
								
								es.execute(new Runnable()
								{
									public void run()
									{

										
										LuckyDrawCalculation30 ldc = new LuckyDrawCalculation30(test,
												fb, arr);
										ldc.callBusinessLogic();
										
										
									}
								});

			//				}
							es.shutdown();
							try
							{
								boolean finished = es.awaitTermination(15, TimeUnit.SECONDS);
								if(finished)
								{
									
									 luckywinners();
									 long end1 = System.currentTimeMillis();
									 System.out.println("Elapsed Time in milli seconds: "
												+ (end1 - start1));
									 winnerJsonArray();
								}
							}
							catch (InterruptedException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						
						
					}
				}
			});

			
			t.join();

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("This ");
		}
		catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args)
	{

		// long start1 = System.currentTimeMillis();
		String realtimeTestValue = "";
		if (args.length > 0)
		{
			realtimeTestValue = args[0];
		}
		LuckyDrawController30 luckydraw = new LuckyDrawController30(
				realtimeTestValue.equalsIgnoreCase("test"));

	}

	public void updateQuestionEvent()
	{

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("lucky_draw_30", "");

		ApiFuture<WriteResult> writeResult = fb.getDb().collection("TheGame").document(realtime)
				.set(event_update, SetOptions.merge());
	}

	public void luckywinners()
	{

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("lucky_draw_30", "");
	//	event_update.put("lucky_winners_processed", true);

		ApiFuture<WriteResult> writeResult = fb.getDb().collection("TheGame").document(realtime)
				.set(event_update, SetOptions.merge());
	}

	public void winnerJsonArray()
	{
		JSONObject luckyWinnersObject = new JSONObject();
		JSONArray luckydrawArray = new JSONArray();
		Jedis jedis = LuckyDrawHelper30.writeConnection(7);
		Set<String> jKeys = jedis.keys("*");
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(jKeys);
		Date date = new Date();
		try
		{
			int i = 0;
			for (i = 1; i < keys.size(); i++)
			{
				String key = keys.get(i);
				String value = jedis.get(key);
				JSONObject object = new JSONObject(value);
				luckydrawArray.put(object);
				
			}
			
			
			
			
			
			LuckyDrawHelper30 hp =new LuckyDrawHelper30();
			hp.closeConnection(jedis);
			luckyWinnersObject.put("Date", date.toString());
			luckyWinnersObject.put("lucky_winners", luckydrawArray);
			LuckyDrawHelper30.createLuckyWinnerFile(luckyWinnersObject.toString());
		}
		catch (JSONException | IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("JSON Exception : " + e.toString());
		}
	}
	public void flushLuckyDrawDb()
	{

		Jedis jedis=LuckyDrawHelper30.writeConnection(7);
		jedis.flushDB();
		LuckyDrawHelper30 hp =new LuckyDrawHelper30();
		hp.closeConnection(jedis);
	}
	public void cleanLuckyDrawFile()
	{
		String rootPath="C:\\Users\\LENOVO\\Downloads\\";
	//	String rootPath="/usr/local/src/SmartFoxServer_2X/SFS2X/data";
		File file = new File(rootPath + "/LuckyWinner30.txt");
		PrintWriter writer =null;
		try
		{
			writer = new PrintWriter(file);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in clearinf file"+e);
		}
		writer.print("");
		writer.close();
	}
}
