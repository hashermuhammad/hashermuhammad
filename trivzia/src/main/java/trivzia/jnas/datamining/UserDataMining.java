package trivzia.jnas.datamining;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.cloud.firestore.Query;

public class UserDataMining extends DataminingHelper {

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
		File directoryPath = new File("C:\\Users\\LENOVO\\Downloads\\New folder\\UserRedisData");
		// List of all files and directories
		File filesList[] = directoryPath.listFiles();
		System.out.println("List of files and directories in the specified directory:");
		for (File file : filesList) {
			System.out.println("File name: " + file.getName());
			System.out.println("File path: " + file.getAbsolutePath());
			System.out.println("Size :" + file.getTotalSpace());
			try {
				CsvSchema csv = CsvSchema.emptySchema().withHeader();
				CsvMapper csvMapper = new CsvMapper();
				MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv)
						.readValues(file);
				List<Map<?, ?>> list = mappingIterator.readAll();

				JSONArray jsArray = null;
				try {
					jsArray = new JSONArray(list);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				JSONObject json = null;
				PollBo pb = null;
				HashMap<String, JSONObject> ipadress = new HashMap<String, JSONObject>();
				int ipcount = 0;
				JSONArray iparr = new JSONArray();
				JSONArray ipres = null;
				for (int i = 0; i < jsArray.length(); i++) {

					JSONObject root = jsArray.getJSONObject(i);
					if (!root.getString("ip_address").equals("") && root.has("ip_address")
							&& !root.getString("ip_address").contains("html")) {

						if (ipcount <= 100) {
							iparr.put(root.getString("ip_address"));
							ipcount++;
						}

						if (ipcount == 100) {
							System.out.println(iparr.toString());
							ipres = readJsonFromUrl(iparr);
							try {
								Thread.sleep(4000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println(ipres.toString());

							for (int z = 0; z <= ipcount - 1; z++) {
								JSONObject ip = ipres.getJSONObject(z);
								ipadress.put(ip.getString("query"), ip);
							}
							ipcount = 0;
							iparr = new JSONArray(new ArrayList<String>());
						}
					}
				}
				for (int i = 0; i < jsArray.length(); i++) {
					count++;
					JSONObject root = jsArray.getJSONObject(i);
					DataMiningBo bo = new DataMiningBo();

					if (ipadress.containsKey(root.getString("ip_address"))) {
						json = ipadress.get(root.getString("ip_address"));
						String deviceInfo = root.getString("device_info");
						String[] mob = deviceInfo.split("\\|");
						bo.setMobileBrand(mob[0]);
						bo.setMobile(mob[1]);
						bo.setMobileOS(mob[2]);
						bo.setIpAddress(root.getString("ip_address"));
						bo.setAccountCode(getAccCode(root.getString("phone")));
						bo.setPollDate(root.getString("Joining_Time"));
						bo.setUserName(root.getString("username"));
						if (json.has("country")) {
							bo.setCountry(json.getString("country"));
						}
						if (json.has("countryCode")) {
							bo.setCountryCode(json.getString("countryCode"));
						}
						if (json.has("region")) {
							bo.setRegion(json.getString("region"));
						}
						if (json.has("regionName")) {
							bo.setRegionName(json.getString("regionName"));
						}
						if (json.has("city")) {
							bo.setCity(json.getString("city"));
						}
						if (json.has("zip")) {
							bo.setZip(json.getString("zip"));
						}
						if (json.has("lat")) {
							bo.setLat(json.getFloat("lat"));
						}
						if (json.has("lon")) {
							bo.setLongi(json.getFloat("lon"));
						}
						if (json.has("timezone")) {
							bo.setTimeZone(json.getString("timezone"));
						}
						if (json.has("isp")) {
							String result = json.getString("isp").replaceAll("[-+.^:,']", "");
							bo.setIsp(result);
						}
						if (json.has("org")) {
							String result = json.getString("org").replaceAll("[-+.^:,']", "");
							bo.setOrg(result);
						}
						if (json.has("as")) {
							String result = json.getString("as").replaceAll("[-+.^:,']", "");
							bo.setAs(result);
						}

						if (count <= (Math.round((jsArray.length() / 100) * 20))) {
							bo.setGender("Female");
						} else {
							bo.setGender("Male");
						}
						polluser.add(bo);

					}

				}

				// System.out.println(list);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Collections.shuffle(polluser);

			ArrayList<Integer> items1 = new ArrayList<Integer>();
			items1.add(18);
			items1.add(19);
			items1.add(20);
			items1.add(21);
			items1.add(22);
			items1.add(23);
			items1.add(24);
			ArrayList<Integer> items2 = new ArrayList<Integer>();
			items2.add(25);
			items2.add(26);
			items2.add(27);
			items2.add(28);
			items2.add(29);
			items2.add(30);
			items2.add(31);
			items2.add(32);
			items2.add(33);
			items2.add(34);

			float maleUsers = Math.round((polluser.size() / 100) * 59);
			int malemostages = (int) maleUsers;

			float maleUsers25 = Math.round((polluser.size() / 100) * 21);
			int malemostages25 = (int) maleUsers25;

			int maleCount = 0;
			for (DataMiningBo dbo : polluser) {
				if (dbo.getGender().equalsIgnoreCase("Male") && maleCount <= malemostages) {
					maleCount++;
					int age = items1.get(new Random().nextInt(items1.size()));
					dbo.setAge(age);
				}
				if (dbo.getGender().equalsIgnoreCase("Male") && maleCount > malemostages
						&& maleCount <= (malemostages25 + malemostages)) {
					maleCount++;
					int age = items2.get(new Random().nextInt(items2.size()));
					dbo.setAge(age);
				}
//					System.out.print(dbo);
			}

			try {
				if (getOldBillingValues1(polluser)) {
					System.out.println("Record Added");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static PollBo getPollDetail(int id) throws SQLException {

		String getvalues = " SELECT p.id,p.poll_opt_one,p.poll_opt_two,p.poll_opt_three "
				+ " FROM `trivzia_polls` p WHERE p.id=" + id;
		Connection connection = null;
		Statement prepStmt = null;
		Statement stmt = null;
		PollBo bo = new PollBo();
		try {
			connection = getSqlConnection();

			prepStmt = connection.createStatement();
			stmt = connection.createStatement();

			ResultSet result = prepStmt.executeQuery(getvalues);

			if (result.next()) {
				bo.setId(result.getInt("id"));
				bo.setOptionOne(result.getString("poll_opt_one"));
				bo.setOptionTwo(result.getString("poll_opt_two"));
				bo.setOptionThree(result.getString("poll_opt_three"));
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {

			prepStmt.close();
			stmt.close();
			connection.close();

		}
		return bo;
	}

	public String splitString(String str) {
		StringBuffer alpha = new StringBuffer(), num = new StringBuffer(), special = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i)))
				num.append(str.charAt(i));
			else if (Character.isAlphabetic(str.charAt(i)))
				alpha.append(str.charAt(i));
			else
				special.append(str.charAt(i));
		}

		System.out.println(alpha);
		System.out.println(num);
		System.out.println(special);
		return num.toString();
	}

	public static String getAccCode(String phone) throws SQLException {

		String getvalues = " SELECT u.accountcode FROM trivzia_users u WHERE u.phone='" + phone + "'";
		Connection connection = null;
		Statement prepStmt = null;
		Statement stmt = null;
		String acc = null;
		try {
			connection = getSqlConnection();

			prepStmt = connection.createStatement();
			stmt = connection.createStatement();

			ResultSet result = prepStmt.executeQuery(getvalues);

			if (result.next()) {
				acc = result.getString("accountcode");
			}

		} catch (SQLException e) {
			System.out.println(e);
		} finally {

			prepStmt.close();
			stmt.close();
			connection.close();

		}
		return acc;
	}

	public static boolean getOldBillingValues1(ArrayList<DataMiningBo> list) throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		int count = 0;

		try {
			connection = getSqlConnection();
			stmt = connection.createStatement();

			boolean condition = true;
			for (DataMiningBo bo : list) {
				count++;
				Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(bo.getUserName() == null ? "" : bo.getUserName());
				Matcher n = p.matcher(bo.getCity() == null ? "" : bo.getCity());
				Matcher o = p.matcher(bo.getCountry() == null ? "" : bo.getCountry());
				Matcher r = p.matcher(bo.getRegion() == null ? "" : bo.getRegion());
				Matcher q = p.matcher(bo.getRegionName() == null ? "" : bo.getRegionName());
				boolean b = m.find();
				boolean c = n.find();
				boolean d = o.find();
				boolean e = r.find();
				boolean f = q.find();

				if (b == false) {

					StringBuilder query = new StringBuilder();
					query.append(
							" INSERT INTO trivzia_users_data (accountcode,username,ipaddress,mobile_brand,country,countrycode,region,regionName, ");
					query.append(" city,zip,lat,`long`,timezone,isp,org,`as`,gender,mobile_model,mobile_OS,age) ");
					query.append(" VALUES ");
					query.append(" ( '" + bo.getAccountCode() + "','" + bo.getUserName() + "','" + bo.getIpAddress()
							+ "','" + bo.getMobileBrand() + "','" + bo.getCountry() + "', ");
					query.append(" '" + bo.getCountryCode() + "','" + bo.getRegion() + "','" + bo.getRegionName()
							+ "','" + bo.getCity() + "', ");
					query.append(" '" + bo.getZip() + "'," + bo.getLat() + "," + bo.getLongi() + ",'" + bo.getTimeZone()
							+ "','" + bo.getIsp() + "','" + bo.getOrg() + "','" + bo.getAs() + "','" + bo.getGender()
							+ "',  ");
					query.append(" '" + bo.getMobile() + "','" + bo.getMobileOS() + "','" + bo.getAge() + "')");
					query.append(" ON DUPLICATE KEY UPDATE ipaddress='" + bo.getIpAddress() + "',");
					query.append(" mobile_brand ='" + bo.getMobileBrand() + "',");
					query.append(" country='" + bo.getCountry() + "',countrycode='" + bo.getCountryCode() + "',");
					query.append(" region='" + bo.getRegion() + "',regionName='" + bo.getRegionName() + "',city='"
							+ bo.getCity() + "',");
					query.append(
							" zip='" + bo.getZip() + "',lat='" + bo.getLat() + "',`long`='" + bo.getLongi() + "',");
					query.append(" timezone='" + bo.getTimeZone() + "',isp='" + bo.getIsp() + "',org='" + bo.getOrg()
							+ "',`as`='" + bo.getAs() + "',gender='" + bo.getGender() + "',");
					query.append(" mobile_model='" + bo.getMobile() + "',mobile_OS='" + bo.getMobileOS() + "' , age='"+bo.getAge()+"'");

				//	System.out.println(query.toString());
				//	stmt.execute(query.toString());

					
				stmt.addBatch(query.toString());
				if (count % 1000 == 0) {
					stmt.executeBatch(); // Execute every 1000 items.
				}
					 

				}
			}

			stmt.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			stmt.close();
			connection.close();

		}
		return false;

	}

	public static void createLuckyWinnerFile(String luckyWinnersJSON, String filename) throws IOException {
		// System.out.println("Creating file at path : " + rootPath + "Winner.txt");
		File file = new File(root, filename);

		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
			FileUtils.writeStringToFile(file, luckyWinnersJSON, Charset.defaultCharset());
		} else {
			FileWriter writer = new FileWriter(file, true);
			writer.write("\r\n");
			writer.write(luckyWinnersJSON);
			writer.close();
			System.out.println("File already exists.");
		}

		// Write Content

		System.out.println(" file write" + filename + " completed.");
	}
}
