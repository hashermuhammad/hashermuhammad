package trivzia.jnas.luckydraw.bespeak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.SetOptions;

import redis.clients.jedis.Jedis;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawHelper;
import trivzia.jnas.utils.DateUtils;

public class SpecialLuckyDraw {
	public String realtime = "RealtimeTest";
	FireStoreConnection fb = null;
	Thread t;

	public int totalOnlineUser = 0;
	int i = 0;
	int luckydrawCount = 1;
	String d1 = null;
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	ConcurrentHashMap<String, JSONObject> criteriaFile = new ConcurrentHashMap<>();

	SpecialLuckyDraw(final boolean test) {

		try {
			if (test) {

				realtime = "RealtimeTest";
			} else {

				realtime = "Realtime";
			}
			fb = new FireStoreConnection();
			this.t = Thread.currentThread();
			LuckyDrawHelper hp = new LuckyDrawHelper();

			// String rootPath = "C:\\Users\\LENOVO\\Downloads\\";
			String rootPath = "/usr/local/src/SmartFoxServer_2X/SFS2X/data";
			File directoryPath = new File(rootPath + "/" + "04.csv");
			CsvSchema csv = CsvSchema.emptySchema().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
					.readValues(directoryPath);
			List<Map<?, ?>> list = mappingIterator.readAll();

			JSONArray jsArray = null;
			try {
				jsArray = new JSONArray(list);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Jedis jedis = LuckyDrawHelper.getConnection();
			for (int i = 0; i < jsArray.length(); i++) {

				JSONObject root = jsArray.getJSONObject(i);
				if (!root.getString("ip_address").equals("") && root.has("ip_address")
						&& !root.getString("ip_address").contains("html")) {

					root.put("Online", true);

					jedis.set(root.getString("phone"), root.toString());

				}

			}
			hp.closeConnection(jedis);

			DocumentReference docRef = fb.getDb().collection("TheGame").document(realtime);
			docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
				@Override
				public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirestoreException e) {
					if (e != null) {
						System.err.println("Listen failed: " + e);
						return;
					}

					if (snapshot != null && snapshot.exists()) {

						System.out.println("Event Listen is " + snapshot.getData().get("lucky_draw_special"));
						if (snapshot.getData().get("lucky_draw_special").equals("LuckyDraw")) {
							DateUtils d = new DateUtils();
							long start1 = System.currentTimeMillis();

							updateQuestionEvent();

							Jedis jedis = LuckyDrawHelper.getConnection();
							Set<String> jKeys = jedis.keys("*");
							ArrayList<String> keysList = new ArrayList<String>();
							keysList.addAll(jKeys);
							Collections.shuffle(keysList);
							Jedis setjedis = null;
							for (i = 1; i < keysList.size(); i++) {
								String index = keysList.get(new Random().nextInt(keysList.size()));

								System.out.println("Random Element is :" + index);
								String value = jedis.get(index);
								JSONObject json = new JSONObject(value);
								if (json.has("Online") && json.getBoolean("Online")) {
									totalOnlineUser++;

									if (totalOnlineUser == 1) {

										luckywinners();
										JSONObject resultJson = new JSONObject();
										setjedis = LuckyDrawHelper.writeConnection(8);
										resultJson.put("phone", json.getString("phone"));
										resultJson.put("accountcode", json.getString("accountcode"));
										resultJson.put("username", json.getString("username"));
										resultJson.put("ipaddress", json.getString("ip_address"));
										setjedis.set(index, resultJson.toString());
										topWinnerJsonArray();
										totalOnlineUser = 0;
										break;
									}
								}
							}

							hp.closeConnection(setjedis);

							/////////////////////////////////// TOP UP LIST CODE END
							/////////////////////////////////// ////////////////////////////////////////////

							d1 = d.getCurrentDateString();

							long end1 = System.currentTimeMillis();
							System.out.println("Elapsed Time in milli seconds: " + (end1 - start1));
						}

					}
				}
			});
			t.join();
		} catch (IOException e) {
			e.printStackTrace();
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
		new SpecialLuckyDraw(realtimeTestValue.equalsIgnoreCase("test"));

	}

	public void updateQuestionEvent() {

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("lucky_draw_special", "");

		fb.getDb().collection("TheGame").document(realtime).set(event_update, SetOptions.merge());
	}

	public void luckywinners() {

		Map<String, Object> event_update = new HashMap<>();
		event_update.put("lucky_draw_special", "");
		event_update.put("luckywinner_file", true);

		fb.getDb().collection("TheGame").document(realtime).set(event_update, SetOptions.merge());
	}

	public void topWinnerJsonArray() {

		Jedis jedis = LuckyDrawHelper.writeConnection(8);
		Set<String> jKeys = jedis.keys("*");
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(jKeys);

		DocumentReference docRef = fb.getDb().collection("TheGame").document(realtime);
		docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
			@Override
			public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirestoreException e) {
				if (e != null) {
					System.err.println("Top List Listen failed: " + e);
					return;
				}

				if (snapshot != null && snapshot.exists()) {

					JSONObject topluckyWinnersObject = new JSONObject();
					JSONArray luckydrawArray = new JSONArray();

					System.out.println("LIST Event Listen is " + snapshot.getData().get("luckywinner_file"));
					if (snapshot.getData().get("luckywinner_file").equals(true)) {

						Map<String, Object> event_update = new HashMap<>();
						event_update.put("luckywinner_file", false);

						fb.getDb().collection("TheGame").document(realtime).set(event_update, SetOptions.merge());
						try {
							TimeUnit.SECONDS.sleep(10);
						} catch (InterruptedException e2) {

							System.out.println("This is sleep thread " + e2);
							Thread.currentThread().interrupt();
						}
						int i = 0;
						for (i = 0; i < keys.size(); i++) {
							String key1 = keys.get(i);
							String value1 = jedis.get(key1);
							JSONObject object1 = new JSONObject(value1);
							String ip = object1.getString("ipaddress");
							object1.put("event", "lucky_winners");
							object1.put("username", object1.getString("username"));
							object1.put("accountcode", object1.getString("accountcode"));
							object1.put("phone", object1.getString("phone"));
							try {
								JSONObject json = readJsonFromUrl("http://ip-api.com/json/" + ip);
								if (json.has("regionName")) {
									object1.put("regionName", json.getString("regionName"));
								}
								if (json.has("city")) {
									object1.put("city", json.getString("city"));
								}
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							luckydrawArray.put(object1);

						}

						try {
							Date date = new Date();
							topluckyWinnersObject.put("lucky_winners", luckydrawArray);
							topluckyWinnersObject.put("Date", date.toString());
							LuckyDrawHelper.createLuckyWinnerFile(topluckyWinnersObject.toString(),
									"WashingMachine.txt");
						} catch (IOException e1) {

							System.out.println("WashingMachine : " + e1);
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

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {

			JSONTokener tokener = new JSONTokener(new InputStreamReader(is, Charset.forName("UTF-8")));

			JSONObject json = new JSONObject(tokener);
			return json;
		} finally {
			is.close();
		}
	}
}
