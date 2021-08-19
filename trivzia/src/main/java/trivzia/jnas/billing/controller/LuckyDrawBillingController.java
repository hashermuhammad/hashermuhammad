package trivzia.jnas.billing.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.json.JSONObject;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.common.collect.Lists;

import redis.clients.jedis.Jedis;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawHelper;

public class LuckyDrawBillingController
{
	public String realtime = "RealtimeTest";
	FireStoreConnection fb = null;
	public ArrayList<JSONObject> luckyWinnerArray = new ArrayList<JSONObject>();
	public int count = 0;
	int tCount = 0;
	boolean gameStarted=false;
	Thread t;
	LuckyDrawBillingController luckyDrawBillingController;

	LuckyDrawBillingController(final boolean test)
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
			luckyDrawBillingController = this;
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
						if (snapshot.getData().get("status").equals("JoinOn") && gameStarted == false) {
							gameStarted = true;
						}

						System.out.println("Event Listen is " + snapshot.getData().get("status"));
						if (snapshot.getData().get("status").equals("Scheduled") && gameStarted == true)
						{
							gameStarted=false;
							long start1 = System.currentTimeMillis();

							Jedis jedis = LuckyDrawHelper.writeConnection(7);
							Set<String> jKeys = jedis.keys("*");
							ArrayList<String> keysList = new ArrayList<String>();
							keysList.addAll(jKeys);
							double lsize = keysList.size();
							int size = (int) Math.round(lsize / 4);
							ExecutorService es = Executors.newCachedThreadPool();
							// System.out.println("size of
							// keys"+keysList.size());

							for (List<String> partition : Lists.partition(keysList, size))
							{
								++tCount;
								// System.out.println("size of
								// partition"+partition.size());
								final ArrayList<String> arr = new ArrayList<String>(partition);

								es.execute(new LuckyDrawBillingCalculation(test, fb, arr,
										luckyDrawBillingController, tCount));

							}
							es.shutdown();

							try
							{
								boolean finished = es.awaitTermination(15, TimeUnit.MINUTES);
								if (finished)
								{
									long end1 = System.currentTimeMillis();
									System.out.println(
											"Elapsed Time in milli seconds: " + (end1 - start1));

									System.out.println("Elapsed Count" + count);

									/*
									 * LuckyDrawHelper hp = new
									 * LuckyDrawHelper(); JSONArray jsArr= new
									 * JSONArray(luckyWinnerArray); JSONArray
									 * sortedarray = hp.getSortedData(jsArr);
									 * jedis.flushDB(); int i; for (i = 0; i <
									 * sortedarray.length(); i++) { JSONObject
									 * sortobject = new JSONObject(
									 * sortedarray.get(i).toString());
									 * 
									 * jedis.set(sortobject.getString("phone"),
									 * sortobject.toString()); }
									 */

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
			System.out.println(e);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
		}
		
	}

	public static void main(String[] args)
	{
		String realtimeTestValue = "";
		if (args.length > 0)
		{
			realtimeTestValue = args[0];
		}
		LuckyDrawBillingController luckydrawbilling = new LuckyDrawBillingController(
				realtimeTestValue.equalsIgnoreCase("test"));

	}
}
