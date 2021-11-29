package trivzia.jnas.datamining.updated;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import redis.clients.jedis.Jedis;
import trivzia.jnas.datamining.DataMiningBo;
import trivzia.jnas.datamining.DataminingHelper;
import trivzia.jnas.datamining.PollBo;

public class PollDataController extends DataminingHelper {
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
			JSONObject json = null;
			PollBo pb = null;
			JSONArray iparr = new JSONArray();
			JSONArray ipres = null;
			int ipcount = 0;
			for (int i = 0; i < a.length(); i++) {
				count++;
				JSONObject root = a.getJSONObject(i);
				DataMiningBo bo = new DataMiningBo();

				if (!root.getString("ip_address").equals("") && root.getString("ip_address") != null
						&& root.has("Poll77") && root.get("Poll77") != "" && root.has("ip_address")
								&& !root.getString("ip_address").contains("html")) {

					Jedis jedis = writeConnection(8);
					if(jedis.exists(root.getString("ip_address")))
					{
					json = new JSONObject(jedis.get(root.getString("ip_address")));

					String deviceInfo = root.getString("device_info");
					String[] mob = deviceInfo.split("\\|");
					bo.setMobileBrand(mob[0]);
					bo.setMobile(mob[1]);
					bo.setMobileOS(mob[2]);
					bo.setIpAddress(root.getString("ip_address"));
					bo.setAccountCode(getAccCode(root.getString("phone")));
					bo.setPollDate(root.getString("Joining_Time"));
					bo.setUserName(root.getString("username"));
					if(json.has("country")) {
					bo.setCountry(json.getString("country"));
					}
					if(json.has("countryCode")) {
					bo.setCountryCode(json.getString("countryCode"));
					}
					if(json.has("region")) {
						String result = json.getString("region").replaceAll("[-+.^:,']","");
					bo.setRegion(result);
					}
					if(json.has("regionName")) {
						String result = json.getString("regionName").replaceAll("[-+.^:,']","");
					bo.setRegionName(result);
					}
					if(json.has("city")) {
						String result = json.getString("regionName").replaceAll("[-+.^:,']","");
					bo.setCity(result);
					}
					if(json.has("zip")) {
					bo.setZip(json.getString("zip"));
					}
					if(json.has("lat")) {
					bo.setLat(json.getFloat("lat"));
					}
					if(json.has("lon")) {
					bo.setLongi(json.getFloat("lon"));
					}
					if(json.has("timezone")) {
					bo.setTimeZone(json.getString("timezone"));
					}
					if(json.has("isp")) {
						String result = json.getString("isp").replaceAll("[-+.^:,']","");
					bo.setIsp(result);
					}
					if(json.has("org")) {
						String result = json.getString("org").replaceAll("[-+.^:,']","");
					bo.setOrg(result);
					}
					if(json.has("as")) {
						String result = json.getString("as").replaceAll("[-+.^:,']","");
					bo.setAs(result);
					}
					if (pb == null) {
						pb = getPollDetail(77);
					}
					bo.setPollId(77);
					bo.setPollOptionId(root.getInt("Poll77"));
					if (bo.getPollOptionId() == 1) {
						bo.setOption(pb.getOptionOne());
					} else if (bo.getPollOptionId() == 2) {
						bo.setOption(pb.getOptionTwo());
					} else {
						bo.setOption(pb.getOptionThree());
					}
					if (count <= (Math.round((a.length()/100)*20))) {
						bo.setGender("Female");
					} else {
						bo.setGender("Male");
					}
					polluser.add(bo);

				}
				}

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
			
			float maleUsers = Math.round((polluser.size()/100)*59);
			int malemostages=(int) maleUsers;
			
			float maleUsers25 = Math.round((polluser.size()/100)*21);
			int malemostages25=(int) maleUsers25;
			
			
			int maleCount=0;
			for(DataMiningBo dbo : polluser)
			{
				if(dbo.getGender().equalsIgnoreCase("Male") && maleCount<=malemostages )
				{
					maleCount++;
					int age = items1.get(new Random().nextInt(items1.size()));
					dbo.setAge(age);
				}
				if(dbo.getGender().equalsIgnoreCase("Male") && maleCount>malemostages &&  maleCount<=(malemostages25+malemostages))
				{
					maleCount++;
					int age = items2.get(new Random().nextInt(items2.size()));
					dbo.setAge(age);
				}
					
			}



			
			
			
			
			
			if (getOldBillingValues1(polluser)) {
				System.out.println("Record Added");
			}

		}

		// Catch block to handle exceptions
		catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				Matcher m = p.matcher(bo.getUserName()==null?"":bo.getUserName());
				Matcher n = p.matcher(bo.getCity()==null?"":bo.getCity());
				Matcher o = p.matcher(bo.getCountry()==null?"":bo.getCountry());
				Matcher r = p.matcher(bo.getRegion()==null?"":bo.getRegion());
				Matcher q = p.matcher(bo.getRegionName()==null?"":bo.getRegionName());
				boolean b = m.find();
				boolean c = n.find();
				boolean d = o.find();
				boolean e = r.find();
				boolean f = q.find();

				if (b == false && c==false && d==false && e==false && f==false ) {

				StringBuilder query = new StringBuilder();
				query.append(
						" INSERT INTO trivzia_users_poll_data (accountcode,username,ipaddress,mobile_brand,country,countrycode,region,regionName, ");
				query.append(
						" city,zip,lat,`long`,timezone,isp,org,`as`,gender,mobile_model,mobile_OS,poll_id,option_id,`option`,date) ");
				query.append(" VALUES ");
				query.append( " ( '" + bo.getAccountCode() + "','" + bo.getUserName() + "','" + bo.getIpAddress() + "','"
						+ bo.getMobileBrand() + "','" + bo.getCountry() + "', ");
				query.append( " '" + bo.getCountryCode() + "','" + bo.getRegion() + "','" + bo.getRegionName() + "','"
						+ bo.getCity() + "', ");
				query.append( " '" + bo.getZip() + "'," + bo.getLat() + "," + bo.getLongi() + ",'" + bo.getTimeZone()
						+ "','" + bo.getIsp() + "','" + bo.getOrg() + "','" + bo.getAs() + "','" + bo.getGender()
						+ "',  ");
				query.append( " '" + bo.getMobile() + "','" + bo.getMobileOS() + "','" + bo.getPollId() + "','"
						+ bo.getPollOptionId() + "','" + bo.getOption() + "','" + bo.getPollDate() + "')");

				System.out.println(query.toString());
				stmt.execute(query.toString());
				
				/*
				 * stmt.addBatch(query.toString()); if (count % 1000 == 0) {
				 * stmt.executeBatch(); // Execute every 1000 items. }
				 */
				 
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

}