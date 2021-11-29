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

public class DataMiningKarachi extends DataminingHelper {

	public boolean getOldBillingValues() throws SQLException {
		List<String> ptclip = new ArrayList<String>();
		List<String> zongip = new ArrayList<String>();
		List<String> mobilinkip = new ArrayList<String>();
		List<String> telenorip = new ArrayList<String>();
		List<String> ufoneip = new ArrayList<String>();
		List<String> stromip = new ArrayList<String>();
		for (int z = 0; z <= 255; z++) {
			ptclip.add("104.99.130." + z);
			ptclip.add("113.203.206." + z);
			ptclip.add("104.99.134." + z);
			ptclip.add("104.99.133." + z);

			zongip.add("111.119.180." + z);
			zongip.add("111.119.181." + z);
			zongip.add("111.119.182." + z);
			zongip.add("111.119.183." + z);

			mobilinkip.add("119.160.120." + z);
			mobilinkip.add("119.160.119." + z);
			mobilinkip.add("119.160.118." + z);
			mobilinkip.add("119.160.119." + z);

			telenorip.add("37.111.132." + z);
			telenorip.add("37.111.131." + z);
			telenorip.add("37.111.130." + z);
			telenorip.add("37.111.136." + z);

			ufoneip.add("103.228.156." + z);
			ufoneip.add("103.228.157." + z);
			ufoneip.add("103.228.158." + z);
			ufoneip.add("103.228.159." + z);

			stromip.add("103.26.82." + z);
			stromip.add("103.26.83." + z);
			stromip.add("101.53.224." + z);
			stromip.add("101.53.225." + z);
		}

		String getvalues = " select id,accountcode,username from trivzia_users  "
				+ " where accountcode NOT IN (select accountcode from trivzia_users_data) and username != ''  "
				+ " order by RAND() limit 238650 ";
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
				if (count <= 59663) {

					String ipad = ptclip.get(new Random().nextInt(ptclip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Pakistan Telecommuication company limited");
					bo.setOrg("Akamai Technologies, Inc.");
					bo.setAs("AS17557 Pakistan Telecommunication Company Limited");
					bo.setZip("75300");
				}
				// zong
				else if (count > 59663 && count <= 114552) {
					String ipad = zongip.get(new Random().nextInt(zongip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("CMPak");
					bo.setOrg("CMPak");
					bo.setAs("AS59257 CMPak Limited");
					bo.setZip("75290");

				}
				// mobilink
				else if (count > 114552 && count <= 164669) {
					String ipad = mobilinkip.get(new Random().nextInt(mobilinkip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Mobilink GSM, Pakistan Mobile Communication Ltd.");
					bo.setOrg("Mobilink GSM, Pakistan Mobile Communication Ltd.");
					bo.setAs("AS45669 PMCL /LDI IP TRANSIT");
					bo.setZip("74700");

				}
				// Telenor
				else if (count > 164669 && count <= 190921) {
					String ipad = telenorip.get(new Random().nextInt(telenorip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Telenor Pakistan (Pvt) Ltd");
					bo.setOrg("Telenor Pakistan (Pvt) Ltd");
					bo.setAs("AS24499 Telenor Pakistan");
					bo.setZip("74400");
				}
				// Uphone
				else if (count > 190921 && count <= 214786) {
					String ipad = ufoneip.get(new Random().nextInt(ufoneip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("PTCL Triple Play Project");
					bo.setOrg("PTML");
					bo.setAs("AS56167 PTML-PK");
					bo.setZip("75290");
				}
				// StromFiber
				else if (count > 214786 && count <= 238651) {
					String ipad = stromip.get(new Random().nextInt(stromip.size()));
					bo.setIpAddress(ipad);
					bo.setIsp("Cyber Internet Services Pakistan");
					bo.setOrg("Cyber Internet Services (Private) Limited");
					bo.setAs("AS9541 Cyber Internet Services (Pvt) Ltd.");
					bo.setZip("75300");
				}
				
				if(count<=62049)
				{
					bo.setGender("Female");
				}
				else
				{
					bo.setGender("Male");
				}
				
				// vivo
				if (count <= 42957) {
					
					bo.setMobileBrand("vivo");
				}
				// samsung
				else if (count > 42957 && count <= 85914) {
					
					bo.setMobileBrand("samsung");
				}
				// oppo
				else if (count > 85914 && count <= 121711) {
					
					bo.setMobileBrand("OPPO");
				}
				// infinix
				else if (count > 121711 && count <= 157508) {
					
					bo.setMobileBrand("INFINIX MOBILITY LIMITED");
				}
				// huawei
				else if (count > 157508 && count <= 183759) {
					
					bo.setMobileBrand("HUAWEI");
				}
				// tecno
				else if (count > 183759 && count <= 200464) {
					
					bo.setMobileBrand("TECNO MOBILE LIMITED");
				}
				// xiamoi
				else if (count > 200464 && count <= 214783) {
					
					bo.setMobileBrand("Xiaomi");
				}
				// realme
				else if (count > 226715 && count <= 231488) {
					
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
				bo.setRegion("SD");
				bo.setRegionName("Sindh");
				bo.setCity("Karachi");
				
				bo.setLat(24.9246);
				bo.setLongi(67.087);
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

