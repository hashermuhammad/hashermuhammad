 package trivzia.jnas.luckydraw.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.json.CDL;
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
import com.google.common.collect.Lists;

import redis.clients.jedis.Jedis;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawHelper;
import trivzia.jnas.utils.DateUtils;

public class LuckyDrawController
{
	public String realtime = "RealtimeTest";
	FireStoreConnection fb = null;
	Thread t;
	public int oldUserRange0=0;  public int newUserRange0=0;
	public int oldUserRange1=0;  public int newUserRange1=0;
	public int oldUserRange2=0;  public int newUserRange2=0;
	public int oldUserRange3=0;  public int newUserRange3=0;
	public int oldUserRange4=0;  public int newUserRange4=0;
	public int oldUserRange5=0;  public int newUserRange5=0;
	public int oldUserRange6=0;  public int newUserRange6=0;
	public int oldUserRange7=0;  public int newUserRange7=0;
	public int oldUserRange8=0;  public int newUserRange8=0;
	public int oldUserRange9=0;  public int newUserRange9=0;
	public int oldUserRange10=0; public int newUserRange10=0;
	public int giveAwayR0=0;	  public int giveAwayR1=0;
	public int giveAwayR2=0;	  public int giveAwayR4=0;
	public int giveAwayR3=0;
	public int totalOnlineUser = 0;
	int i=0;
	int luckydrawCount=1;
	String d1=null;
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	private static LuckyDrawController luckyDrawController = null;
	LuckyDrawController(final boolean test)
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
			luckyDrawController = this;
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
								"Event Listen is " + snapshot.getData().get("Question_Event"));
						if (snapshot.getData().get("Question_Event").equals("LuckyDraw"))
						{
							DateUtils d= new DateUtils();
							long start1 = System.currentTimeMillis();
							if(d1!=null)
							{
								try
								{
									Date date1 = sdformat.parse(d1);
									Date date2 = sdformat.parse(d.getCurrentDateString());
									if(date1.compareTo(date2) == 0)   
									{  
									System.out.println("Both dates are equal");  
									}
									else
									{
										flushLuckyDrawDb();
										cleanLuckyDrawFile("NewLuckyWinner.txt");	
									}
								}
								catch (ParseException e2)
								{
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}  
							}
							else
							{
								flushLuckyDrawDb();
								cleanLuckyDrawFile("NewLuckyWinner.txt");	
							}
							
							  
								
							
							updateQuestionEvent();
							
							
							Jedis jedis = LuckyDrawHelper.getConnection();
							Set<String> jKeys = jedis.keys("*");
							ArrayList<String> keysList = new ArrayList<String>();
							keysList.addAll(jKeys);
							int size = Math.round(keysList.size() / 10);
							ExecutorService es = Executors.newCachedThreadPool();
					//		System.out.println("size of keys"+keysList.size());
							
							for (List<String> partition : Lists.partition(keysList, size))
							{
								++i;
								if(i==11)
								{
									break;
								}
						//		System.out.println("size of partition"+partition.size());
								final ArrayList<String> arr = new ArrayList<String>(partition);
								
								es.execute( new LuckyDrawCalculation(test,
												fb, arr,luckyDrawController,i,luckydrawCount)
									);

							}
							es.shutdown();
							try
							{
								boolean finished = es.awaitTermination(15, TimeUnit.SECONDS);
								if(finished)
								{
									
									d1=d.getCurrentDateString();
									 luckywinners();
									 long end1 = System.currentTimeMillis();
									 System.out.println("Elapsed Time in milli seconds: "
												+ (end1 - start1));
									 winnerJsonArray();
									 ++luckydrawCount;
									 
									 
									 
										 oldUserRange0=0;  newUserRange0=0;
										 oldUserRange1=0;   newUserRange1=0;
										 oldUserRange2=0;   newUserRange2=0;
										 oldUserRange3=0;   newUserRange3=0;
										 oldUserRange4=0;   newUserRange4=0;
										 oldUserRange5=0;   newUserRange5=0;
										 oldUserRange6=0;   newUserRange6=0;
										 oldUserRange7=0;   newUserRange7=0;
										 oldUserRange8=0;   newUserRange8=0;
										 oldUserRange9=0;   newUserRange9=0;
										 oldUserRange10=0; newUserRange10=0;
										 giveAwayR0=0;	   giveAwayR1=0;
										 giveAwayR2=0;	   giveAwayR4=0;
										 giveAwayR3=0;
										 totalOnlineUser = 0;
									 
									 
									 
									 
									 
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
		LuckyDrawController luckydraw = new LuckyDrawController(
				realtimeTestValue.equalsIgnoreCase("test"));

	}

	public void updateQuestionEvent()
	{

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("Question_Event", "");

		ApiFuture<WriteResult> writeResult = fb.getDb().collection("TheGame").document(realtime)
				.set(event_update, SetOptions.merge());
	}

	public void luckywinners()
	{

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("Question_Event", "");
		event_update.put("lucky_winners_processed", true);

		ApiFuture<WriteResult> writeResult = fb.getDb().collection("TheGame").document(realtime)
				.set(event_update, SetOptions.merge());
	}

	public void winnerJsonArray()
	{
		JSONObject luckyWinnersObject = new JSONObject();
		JSONArray luckydrawArray = new JSONArray();
		Jedis jedis = LuckyDrawHelper.writeConnection(7);
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
				if(object.getInt("LuckyDraw")==luckydrawCount)
				{
				luckydrawArray.put(object);
				}
				
			}
			LuckyDrawHelper hp =new LuckyDrawHelper();
			
			JSONArray sortedarray=hp.getSortedData(luckydrawArray);
			luckyWinnersObject.put("Date", date.toString());
			luckyWinnersObject.put("lucky_winners", sortedarray);
			
			String csv = CDL.toString(sortedarray);
			LuckyDrawHelper.createLuckyWinnerFile(luckyWinnersObject.toString(),"NewLuckyWinner.txt");
			DateUtils date1= new DateUtils();
			LuckyDrawHelper.createLuckyWinnerFile(csv,date1.getCurrentDateString() + "NewLuckyWinner.csv ");

			
			
			hp.closeConnection(jedis);
		}
		catch (JSONException | IOException e)
		{
			// TODO Auto-generated catch block
			System.out.println("JSON Exception : " + e.toString());
		}
	}
	public void flushLuckyDrawDb()
	{

		Jedis jedis=LuckyDrawHelper.writeConnection(7);
		jedis.flushDB();
		LuckyDrawHelper hp =new LuckyDrawHelper();
		hp.closeConnection(jedis);
	}
	public void cleanLuckyDrawFile(String filename)
	{
//		String rootPath="C:\\Users\\LENOVO\\Downloads\\";
		String rootPath="/usr/local/src/SmartFoxServer_2X/SFS2X/data";
		File file = new File(rootPath + "/"+filename);
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
