package trivzia.jnas.datamining;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import trivzia.jnas.helper.LuckyDrawHelper;

public class DataMiningIslamabad extends DataminingHelper {

	public boolean getOldBillingValues() throws SQLException {
		List<String> ptclip = new ArrayList<String>();
		List<String> zongip = new ArrayList<String>();
		List<String> mobilinkip = new ArrayList<String>();
		List<String> telenorip = new ArrayList<String>();
		List<String> ufoneip = new ArrayList<String>();
		List<String> stromip = new ArrayList<String>();
		for (int z = 0; z <= 255; z++) {
			ptclip.add("116.71.241." + z);
			ptclip.add("116.71.245." + z);
			ptclip.add("116.71.244." + z);
			ptclip.add("116.71.238." + z);

			zongip.add("111.119.172." + z);
			zongip.add("209.150.144." + z);
			zongip.add("111.119.168." + z);
			zongip.add("103.255.7." + z);

			mobilinkip.add("119.160.62." + z);
			mobilinkip.add("119.160.119." + z);
			mobilinkip.add("119.160.115." + z);
			mobilinkip.add("119.160.60." + z);

			telenorip.add("202.69.13." + z);
			telenorip.add("202.69.12." + z);
			telenorip.add("202.69.14." + z);
			telenorip.add("202.69.15." + z);

			ufoneip.add("42.83.84." + z);
			ufoneip.add("42.83.85." + z);
			ufoneip.add("42.83.86." + z);
			ufoneip.add("42.83.87." + z);

			stromip.add("175.107.205." + z);
			stromip.add("175.107.204." + z);
			stromip.add("175.107.205." + z);
			stromip.add("175.107.204." + z);
		}

		String getvalues = " select id,accountcode,username from trivzia_users  "
				+ " where accountcode NOT IN (select accountcode from trivzia_users_data) and username != ''  "
				+ " order by RAND() limit 122660 ";
		Connection connection = null;
		Statement prepStmt = null;
		Statement stmt = null;
		int count = 0;
		JSONObject json = null;
		String ip = null;
		int firstRange = 0;
		int secondRange = 0;
		int thirdRange = 0;
		int fourthRange = 0;
		try {
			connection = getSqlConnection();

			prepStmt = connection.createStatement();
			stmt = connection.createStatement();

			ResultSet result = prepStmt.executeQuery(getvalues);

			while (result.next()) {
				DataMiningBo bo = new DataMiningBo();
				count++;
				// ptcl
				if (count <= 30665) {

					String ipad = ptclip.get(new Random().nextInt(ptclip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Pakistan Telecommuication company limited");
					bo.setOrg("Akamai Technologies, Inc.");
					bo.setAs("AS17557 Pakistan Telecommunication Company Limited");
					bo.setZip("44000");
				}
				// zong
				else if (count > 30665 && count <= 58877) {
					String ipad = zongip.get(new Random().nextInt(zongip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("CMPak");
					bo.setOrg("CMPak");
					bo.setAs("AS59257 CMPak Limited");
					bo.setZip("45500");

				}
				// mobilink
				else if (count > 58877 && count <= 84636) {
					String ipad = mobilinkip.get(new Random().nextInt(mobilinkip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Mobilink GSM, Pakistan Mobile Communication Ltd.");
					bo.setOrg("Mobilink GSM, Pakistan Mobile Communication Ltd.");
					bo.setAs("AS45669 PMCL /LDI IP TRANSIT");
					bo.setZip("44000");

				}
				// Telenor
				else if (count > 84636 && count <= 98129) {
					String ipad = telenorip.get(new Random().nextInt(telenorip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Telenor Pakistan (Pvt) Ltd");
					bo.setOrg("Telenor Pakistan (Pvt) Ltd");
					bo.setAs("AS24499 Telenor Pakistan");
					bo.setZip("45500");
				}
				// Uphone
				else if (count > 98129 && count <= 110395) {
					String ipad = ufoneip.get(new Random().nextInt(ufoneip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("PTCL Triple Play Project");
					bo.setOrg("PTML");
					bo.setAs("AS56167 PTML-PK");
					bo.setZip("44000");
				}
				// StromFiber
				else if (count > 110395 && count <= 122660) {
					String ipad = stromip.get(new Random().nextInt(stromip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Cyber Internet Services Pakistan");
					bo.setOrg("Cyber Internet Services (Private) Limited");
					bo.setAs("AS9541 Cyber Internet Services (Pvt) Ltd.");
					bo.setZip("45500");
				}
				
				if(count<=24532)
				{
					bo.setGender("Female");
				}
				else
				{
					bo.setGender("Male");
				}
				
				// vivo
				if (count <= 22078) {
					
					bo.setMobileBrand("vivo");
				}
				// samsung
				else if (count > 22078 && count <= 44157) {
					
					bo.setMobileBrand("samsung");
				}
				// oppo
				else if (count > 44157 && count <= 62556) {
					
					bo.setMobileBrand("OPPO");
				}
				// infinix
				else if (count > 62556 && count <= 80955) {
					
					bo.setMobileBrand("INFINIX MOBILITY LIMITED");
				}
				// huawei
				else if (count > 80955 && count <= 94447) {
					
					bo.setMobileBrand("HUAWEI");
				}
				// tecno
				else if (count > 94447 && count <= 103033) {
					
					bo.setMobileBrand("TECNO MOBILE LIMITED");
				}
				// xiamoi
				else if (count > 103033 && count <= 110392) {
					
					bo.setMobileBrand("Xiaomi");
				}
				// realme
				else if (count > 110392 && count <= 116525) {
					
					bo.setMobileBrand("realme");
				} else {
					
					List<String> items = new ArrayList<String>();
					items.add("motorola");
					items.add("itel");
					items.add("LGE");
					items.add("OnePlus");
					items.add("lenovo");
					items.add("iPhone");
					items.add("ZTE");

					String mobile = items.get(new Random().nextInt(items.size()));
					bo.setMobileBrand(mobile);
				}
				
				

				bo.setAccountCode(result.getString("accountcode"));
				bo.setUserName(result.getString("username"));
				bo.setCountry("Pakistan");
				bo.setCountryCode("PK");
				bo.setRegion("IS");
				bo.setRegionName("Islamabad");
				bo.setCity("Islamabad");
				
				bo.setLat(33.6648);
				bo.setLongi(73.0419);
				bo.setTimeZone("Asia/Karachi");

				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json1 = ow.writeValueAsString(bo);
				Jedis jedis = writeConnection(8);
				jedis.set(result.getString("accountcode"), json1);

			}
		} catch (JSONException e) {
			System.out.println(e);
		} catch (JsonProcessingException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
		} finally {

			prepStmt.close();
			stmt.close();
			connection.close();

		}
		return true;
	}

	public boolean getOldBillingValues1() throws SQLException {
		Connection connection = null;
		Statement stmt = null;
		int count = 0;

		try {
			connection = getSqlConnection();
			stmt = connection.createStatement();
			Jedis jedis = LuckyDrawHelper.writeConnection(8);
			Set<String> jKeys = jedis.keys("*");
			ArrayList<String> keysList = new ArrayList<String>();
			keysList.addAll(jKeys);
			int size=keysList.size();
			boolean condition=true;
			for (int i = 1; i < size; i++)
			{
				count++;

				String key = jedis.randomKey();
				String value = jedis.get(key);
		//		JSONObject json = new JSONObject(value);
				
				DataMiningBo bo = new Gson().fromJson(value, DataMiningBo.class); 
				keysList.remove(key);
				
				

				Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(bo.getUserName());
				boolean b = m.find();

				if (b == false) {

					StringBuilder query = new StringBuilder();
					query.append(
							" INSERT INTO trivzia_users_data (accountcode,username,ipaddress,mobile_brand,country,countrycode,region,regionName, ");
					query.append(" city,zip,lat,`long`,timezone,isp,org,`as`,gender) ");
					query.append(" VALUES ");
					query.append(" ( '" + bo.getAccountCode() + "','" + bo.getUserName() + "','" + bo.getIpAddress()
							+ "','" + bo.getMobileBrand() + "','" + bo.getCountry() + "', ");
					query.append(" '" + bo.getCountryCode() + "','" + bo.getRegion() + "','" + bo.getRegionName()
							+ "','" + bo.getCity() + "', ");
					query.append(" '" + bo.getZip() + "'," + bo.getLat() + "," + bo.getLongi() + ",'" + bo.getTimeZone()
							+ "','" + bo.getIsp() + "','" + bo.getOrg() + "','" + bo.getAs() + "','"+bo.getGender()+"' ) ");

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
		}
		finally {
			stmt.close();
		    connection.close();

		}
		return false;

	}

}


