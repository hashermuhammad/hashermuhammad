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
import trivzia.jnas.constant.SysConstants;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawHelper;
import trivzia.jnas.utils.DateUtils;

public class LuckyDrawController {
	public String realtime = "RealtimeTest";
	FireStoreConnection fb = null;
	Thread t;
	public int oldUserRange0 = 0;
	public int newUserRange0 = 0;
	public int oldUserRange1 = 0;
	public int newUserRange1 = 0;
	public int oldUserRange2 = 0;
	public int newUserRange2 = 0;
	public int oldUserRange3 = 0;
	public int newUserRange3 = 0;
	public int oldUserRange4 = 0;
	public int newUserRange4 = 0;
	public int oldUserRange5 = 0;
	public int newUserRange5 = 0;
	public int oldUserRange6 = 0;
	public int newUserRange6 = 0;
	public int oldUserRange7 = 0;
	public int newUserRange7 = 0;
	public int oldUserRange8 = 0;
	public int newUserRange8 = 0;
	public int oldUserRange9 = 0;
	public int newUserRange9 = 0;
	public int oldUserRange10 = 0;
	public int newUserRange10 = 0;
	public int giveAwayR0 = 0;
	public int giveAwayR1 = 0;
	public int giveAwayR2 = 0;
	public int giveAwayR4 = 0;
	public int giveAwayR3 = 0;
	public int totalOnlineUser = 0;
	int i = 0;
	int luckydrawCount = 1;
	String d1 = null;
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	private static LuckyDrawController luckyDrawController = null;

	LuckyDrawController(final boolean test) {

		try {
			if (test) {

				realtime = "RealtimeTest";
			} else {

				realtime = "Realtime";
			}
			luckyDrawController = this;
			fb = new FireStoreConnection();
			this.t = Thread.currentThread();
			DocumentReference docRef = fb.getDb().collection("TheGame").document(realtime);
			docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
				@Override
				public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirestoreException e) {
					if (e != null) {
						System.err.println("Listen failed: " + e);
						return;
					}

					if (snapshot != null && snapshot.exists()) {

						System.out.println("Event Listen is " + snapshot.getData().get("Question_Event"));
						if (snapshot.getData().get("Question_Event").equals("LuckyDraw")) {
							DateUtils d = new DateUtils();
							long start1 = System.currentTimeMillis();
							if (d1 != null) {
								try {
									Date date1 = sdformat.parse(d1);
									Date date2 = sdformat.parse(d.getCurrentDateString());
									if (date1.compareTo(date2) == 0) {
										
										System.out.println("Both dates are equal");
									} else {
										flushLuckyDrawDb();
										cleanLuckyDrawFile("LuckyWinnerFull.txt");
										cleanLuckyDrawFile("LuckyWinner.txt");
										cleanLuckyDrawFile("LuckyWinnerDashBoard.txt");
									}
								} catch (ParseException e2) {
									// TODO Auto-generated catch block
									System.out.println(e2);
								}
							} else {
								flushLuckyDrawDb();
								cleanLuckyDrawFile("LuckyWinnerFull.txt");
								cleanLuckyDrawFile("LuckyWinner.txt");
								cleanLuckyDrawFile("LuckyWinnerDashBoard.txt");
							}

							updateQuestionEvent();

							Jedis jedis = LuckyDrawHelper.getConnection();
							Set<String> jKeys = jedis.keys("*");
							ArrayList<String> keysList = new ArrayList<String>();
							keysList.addAll(jKeys);
							double lsize = keysList.size();
							int size = (int) Math.round(lsize / 10);
							ExecutorService es = Executors.newCachedThreadPool();
							// System.out.println("size of keys"+keysList.size());

							for (List<String> partition : Lists.partition(keysList, size)) {
								++i;
								
								// if (i == 11) {break;}
								// System.out.println("size of partition"+partition.size());
								final ArrayList<String> arr = new ArrayList<String>(partition);

								es.execute(new LuckyDrawCalculation(test, fb, arr, luckyDrawController, i,
										luckydrawCount));

							}
							es.shutdown();
							try {
								boolean finished = es.awaitTermination(15, TimeUnit.SECONDS);
								if (finished) {

									oldUserRange0 = 0;
									newUserRange0 = 0;
									oldUserRange1 = 0;
									newUserRange1 = 0;
									oldUserRange2 = 0;
									newUserRange2 = 0;
									oldUserRange3 = 0;
									newUserRange3 = 0;
									oldUserRange4 = 0;
									newUserRange4 = 0;
									oldUserRange5 = 0;
									newUserRange5 = 0;
									oldUserRange6 = 0;
									newUserRange6 = 0;
									oldUserRange7 = 0;
									newUserRange7 = 0;
									oldUserRange8 = 0;
									newUserRange8 = 0;
									oldUserRange9 = 0;
									newUserRange9 = 0;
									oldUserRange10 = 0;
									newUserRange10 = 0;
									giveAwayR0 = 0;
									giveAwayR1 = 0;
									giveAwayR2 = 0;
									giveAwayR4 = 0;
									giveAwayR3 = 0;
									totalOnlineUser = 0;
									
									
									
	
									d1 = d.getCurrentDateString();
									luckywinners();
									long end1 = System.currentTimeMillis();
									System.out.println("Elapsed Time in milli seconds: " + (end1 - start1));
									winnerJsonArray();

									topWinnerJsonArray();
									++luckydrawCount;
									
									

								}
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					}
				}
			});

			t.join();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("This ");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// long start1 = System.currentTimeMillis();
		String realtimeTestValue = "";
		if (args.length > 0) {
			realtimeTestValue = args[0];
		}
		LuckyDrawController luckydraw = new LuckyDrawController(realtimeTestValue.equalsIgnoreCase("test"));

	}

	public void updateQuestionEvent() {

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("Question_Event", "");

		ApiFuture<WriteResult> writeResult = fb.getDb().collection("TheGame").document(realtime).set(event_update,
				SetOptions.merge());
	}

	public void luckywinners() {

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("Question_Event", "");
		event_update.put("lucky_winners_processed", true);

		ApiFuture<WriteResult> writeResult = fb.getDb().collection("TheGame").document(realtime).set(event_update,
				SetOptions.merge());
	}

	public void winnerJsonArray() {
		JSONObject luckyWinnersObject = new JSONObject();
		JSONArray luckydrawArray = new JSONArray();
		Jedis jedis = LuckyDrawHelper.writeConnection(7);
		Set<String> jKeys = jedis.keys("*");
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(jKeys);
		Date date = new Date();
		try {
			int i = 0;
			for (i = 1; i < keys.size(); i++) {
				String key = keys.get(i);
				String value = jedis.get(key);
				
				JSONArray obj= new JSONArray(value);
				for(int j=0;j<obj.length();++j)
				{
					JSONObject object =obj.getJSONObject(j); 
					
					if (object.getInt("LuckyDraw") == luckydrawCount) {
						System.out.println(object.toString());
						luckydrawArray.put(object);
					}
				}
				

			}
			LuckyDrawHelper hp = new LuckyDrawHelper();

			JSONArray sortedarray = hp.getSortedData(luckydrawArray, "quantity");

			luckyWinnersObject.put("Date", date.toString());
			luckyWinnersObject.put("lucky_winners", sortedarray);

			String csv = CDL.toString(sortedarray);
			if (luckydrawCount > 1) {
				LuckyDrawHelper.appendLuckyWinnerFile(luckyWinnersObject, "LuckyWinnerFull.txt", sortedarray);
			} else {
				LuckyDrawHelper.createLuckyWinnerFile(luckyWinnersObject.toString(), "LuckyWinnerFull.txt");
				DateUtils date1 = new DateUtils();
				LuckyDrawHelper.createLuckyWinnerFile(csv, date1.getCurrentDateString() + "LuckyWinnerFull.csv ");
			}
			

			hp.closeConnection(jedis);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("JSON Exception : " + e.toString());
		}
	}

	public void topWinnerJsonArray() {
		
		Jedis jedis = LuckyDrawHelper.writeConnection(7);
		Set<String> jKeys = jedis.keys("*");
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(jKeys);
		

		LuckyDrawHelper hp = new LuckyDrawHelper();
		DocumentReference docRef = fb.getDb().collection("TheGame").document(realtime);
		docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
			@Override
			public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirestoreException e) {
				if (e != null) {
					System.err.println("Top List Listen failed: " + e);
					return;
				}

				if (snapshot != null && snapshot.exists()) {
					JSONObject luckyWinnersObject = new JSONObject();
					JSONObject topluckyWinnersObject = new JSONObject();
					JSONArray luckydrawArray = new JSONArray();
					JSONArray luckydrawArraytop = new JSONArray();
					System.out.println("LIST Event Listen is " + snapshot.getData().get("luckywinner_file"));
					if (snapshot.getData().get("luckywinner_file").equals(true)) {

						Map<String, Object> event_update = new HashMap<>();
						event_update.put("luckywinner_file", false);

						ApiFuture<WriteResult> writeResult =fb.getDb().collection("TheGame").document(realtime).set(event_update, SetOptions.merge());
						try {
							TimeUnit.SECONDS.sleep(10);
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							System.out.println("This is sleep thread "+e2);
							Thread.currentThread().interrupt();
						}
						int i = 0;
						for (i = 1; i < keys.size(); i++) {
							String key1 = keys.get(i);
							String value1 = jedis.get(key1);
							JSONArray obj= new JSONArray(value1);
							for (int j = 0; j < obj.length(); ++j) {
								JSONObject object1 = obj.getJSONObject(j);

								if (object1.has("billing") && object1.getBoolean("billing")) {
									object1.put("event", "lucky_winners");
									object1.put("amount", object1.getInt("quantity"));

									luckydrawArray.put(object1);

									if (object1.getString("rewardType").equals(SysConstants.REWARD_GAMEPOINT)) {
										object1.remove("billing");
										object1.remove("LuckyDraw");
										object1.remove("quantity");
										object1.remove("rewardType");
										luckydrawArraytop.put(object1);
									}

								}
							}
						}
						

						
						
						try {
							Date date = new Date();
							topluckyWinnersObject.put("Date", date.toString());
							JSONArray topsortedarray = hp.getSortedData(luckydrawArraytop, "amount");
							topluckyWinnersObject.put("lucky_winners", topsortedarray);	
							LuckyDrawHelper.createLuckyWinnerFile(topluckyWinnersObject.toString(), "LuckyWinner.txt");
							
							
							Date date1 = new Date();
							JSONArray sortedarray = hp.getSortedData(luckydrawArray, "amount");
							luckyWinnersObject.put("Date", date1.toString());
							luckyWinnersObject.put("lucky_winners", sortedarray);
							
							LuckyDrawHelper.createLuckyWinnerFile(luckyWinnersObject.toString(), "LuckyWinnerDashBoard.txt");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.println("TopArrayException : " + e1);
						}

					}

				}
			}

		});

	}

	public void flushLuckyDrawDb() {

		Jedis jedis = LuckyDrawHelper.writeConnection(7);
		jedis.flushDB();
		LuckyDrawHelper hp = new LuckyDrawHelper();
		hp.closeConnection(jedis);
	}

	public void cleanLuckyDrawFile(String filename) {
//		String rootPath = "C:\\Users\\LENOVO\\Downloads\\";
		String rootPath = "/usr/local/src/SmartFoxServer_2X/SFS2X/data";
		File file = new File(rootPath + "/" + filename);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error in clearinf file" + e);
		}
		writer.print("");
		writer.close();
	}
}
