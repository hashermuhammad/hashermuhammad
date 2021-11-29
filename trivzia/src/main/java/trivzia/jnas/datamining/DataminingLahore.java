package trivzia.jnas.datamining;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class DataminingLahore extends DataminingHelper {

	public  boolean getOldBillingValues() throws SQLException {


		
		
		
		
		
		String getvalues = " select id,accountcode,username from trivzia_users  "
				+ " where accountcode NOT IN (select accountcode from trivzia_users_data) and username != ''  "
				+ " order by RAND() limit 238650 ";
		 Connection connection = null;
		Statement prepStmt = null;
		Statement stmt = null;
		int count = 0;
		int mobileCount = 0;
		JSONObject json = null;
		String ip = null;
		int firstRange = 0;
		int secondRange = 0;
		int thirdRange = 0;
		int fourthRange = 0;
		try {
			 connection = getSqlConnection();

			prepStmt = connection.createStatement();
			stmt=connection.createStatement();
			
			ResultSet result = prepStmt.executeQuery(getvalues);
			
			while (result.next()) {
				DataMiningBo bo = new DataMiningBo();
				count++;
				// ptcl
				if (count <= 59663) {
					
					
					
					
					
					if (firstRange < 255) {
						ip = "39.42.43." + firstRange;
						firstRange++;
						bo.setIpAddress(ip);
	//					json = readJsonFromUrl("http://ip-api.com/json/" + ip);

					} else if (secondRange < 255) {
						ip = "39.42.41." + secondRange;
						secondRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (thirdRange < 255) {
						ip = "39.42.44." + thirdRange;
						thirdRange++;
						bo.setIpAddress(ip);
	//					json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (fourthRange < 255) {
						ip = "39.42.42." + fourthRange;
						fourthRange++;
						bo.setIpAddress(ip);
	//					json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} 
					else {
						if (firstRange == 255 && secondRange == 255 && thirdRange == 255 && fourthRange == 255) {
							firstRange = 0;
							secondRange = 0;
							thirdRange = 0;
							fourthRange = 0;
						}
					}

				}
				// zong
				else if (count > 59663 && count <= 114552) {

					if (firstRange < 255) {
						ip = "103.255.4." + firstRange;
						firstRange++;
						bo.setIpAddress(ip);
//						json = readJsonFromUrl("http://ip-api.com/json/" + ip);

					} else if (secondRange < 255) {
						ip = "103.255.5." + secondRange;
						secondRange++;
						bo.setIpAddress(ip);
//						json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (thirdRange < 255) {
						ip = "103.255.5." + thirdRange;
						thirdRange++;
						bo.setIpAddress(ip);
//						json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (fourthRange < 255) {
						ip = "103.255.4." + fourthRange;
						fourthRange++;
						bo.setIpAddress(ip);
//						json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else {
						if (firstRange == 255 && secondRange == 255 && thirdRange == 255 && fourthRange == 255) {
							firstRange = 0;
							secondRange = 0;
							thirdRange = 0;
							fourthRange = 0;
						}
					}
				}
				// mobilink
				else if (count > 114552 && count <= 164669) {
					
					if (firstRange < 255) {
						ip = "119.160.97." + firstRange;
						firstRange++;
						bo.setIpAddress(ip);
	//					json = readJsonFromUrl("http://ip-api.com/json/" + ip);

					} else if (secondRange < 255) {
						ip = "119.160.96." + secondRange;
						secondRange++;
						bo.setIpAddress(ip);
	//					json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (thirdRange < 255) {
						ip = "119.160.102." + thirdRange;
						thirdRange++;
						bo.setIpAddress(ip);
	//					json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (fourthRange < 255) {
						ip = "119.160.103." + fourthRange;
						fourthRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else {
						if (firstRange == 255 && secondRange == 255 && thirdRange == 255 && fourthRange == 255) {
							firstRange = 0;
							secondRange = 0;
							thirdRange = 0;
							fourthRange = 0;
						}
					}
				}
				// Telenor
				else if (count > 164669 && count <= 190921) {

					if (firstRange < 255) {
						ip = "103.7.78." + firstRange;
						firstRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);

					} else if (secondRange < 255) {
						ip = "103.7.79." + secondRange;
						secondRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (thirdRange < 255) {
						ip = "37.111.129." + thirdRange;
						thirdRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (fourthRange < 255) {
						ip = "37.111.135." + fourthRange;
						fourthRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else {
						if (firstRange == 255 && secondRange == 255 && thirdRange == 255 && fourthRange == 255) {
							firstRange = 0;
							secondRange = 0;
							thirdRange = 0;
							fourthRange = 0;
						}
					}
				}
				// Uphone
				else if (count > 190921 && count <= 214786) {
					

					if (firstRange < 255) {
						ip = "43.245.9." + firstRange;
						firstRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);

					} else if (secondRange < 255) {
						ip = "119.152.120." + secondRange;
						secondRange++;
						bo.setIpAddress(ip);
		//				json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (thirdRange < 255) {
						ip = "119.152.121." + thirdRange;
						thirdRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (fourthRange < 255) {
						ip = "119.152.122." + fourthRange;
						fourthRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else {
						if (firstRange == 255 && secondRange == 255 && thirdRange == 255 && fourthRange == 255) {
							firstRange = 0;
							secondRange = 0;
							thirdRange = 0;
							fourthRange = 0;
						}
					}
				}
				// StromFiber
				else if (count > 214786 && count <= 238651) {

					if (firstRange < 255) {
						ip = "101.53.243." + firstRange;
						firstRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);

					} else if (secondRange < 255) {
						ip = "101.53.242." + secondRange;
						secondRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (thirdRange < 255) {
						ip = "101.53.233." + thirdRange;
						thirdRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else if (fourthRange < 255) {
						ip = "101.53.234." + fourthRange;
						fourthRange++;
						bo.setIpAddress(ip);
			//			json = readJsonFromUrl("http://ip-api.com/json/" + ip);
					} else {
						if (firstRange == 255 && secondRange == 255 && thirdRange == 255 && fourthRange == 255) {
							firstRange = 0;
							secondRange = 0;
							thirdRange = 0;
							fourthRange = 0;
						}
					}
				}
				// vivo
				if (mobileCount <= 42957) {
					mobileCount++;
					bo.setMobileBrand("vivo");
				}
				// samsung
				else if (mobileCount > 42957 && mobileCount <= 85914) {
					mobileCount++;
					bo.setMobileBrand("samsung");
				}
				// oppo
				else if (mobileCount > 85914 && mobileCount <= 121711) {
					mobileCount++;
					bo.setMobileBrand("OPPO");
				}
				// infinix
				else if (mobileCount > 121711 && mobileCount <= 157508) {
					mobileCount++;
					bo.setMobileBrand("INFINIX MOBILITY LIMITED");
				}
				// huawei
				else if (mobileCount > 157508 && mobileCount <= 183759) {
					mobileCount++;
					bo.setMobileBrand("HUAWEI");
				}
				// tecno
				else if (mobileCount > 183759 && mobileCount <= 200464) {
					mobileCount++;
					bo.setMobileBrand("TECNO MOBILE LIMITED");
				}
				// xiamoi
				else if (mobileCount > 200464 && mobileCount <= 214783) {
					mobileCount++;
					bo.setMobileBrand("Xiaomi");
				}
				// realme
				else if (mobileCount > 226715 && mobileCount <= 231488) {
					mobileCount++;
					bo.setMobileBrand("realme");
				} else {
					mobileCount++;
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
				/*
				 * bo.setAccountCode(result.getString("accountcode"));
				 * bo.setUserName(result.getString("username"));
				 * bo.setCountry(json.getString("country"));
				 * bo.setCountryCode(json.getString("countryCode"));
				 * bo.setRegion(json.getString("region"));
				 * bo.setRegionName(json.getString("regionName"));
				 * bo.setCity(json.getString("city")); bo.setZip(json.getString("zip"));
				 * bo.setLat(json.getFloat("lat")); bo.setLongi(json.getFloat("lon"));
				 * bo.setTimeZone(json.getString("timezone")); bo.setIsp(json.getString("isp"));
				 * bo.setOrg(json.getString("org")); bo.setAs(json.getString("as"));
				 */
				bo.setAccountCode(result.getString("accountcode"));
				bo.setUserName(result.getString("username"));
				bo.setCountry("Pakistan");
				bo.setCountryCode("PK");
				bo.setRegion("PB");
				bo.setRegionName("Punjab");
				bo.setCity("Lahore");
				bo.setZip("54000");
				bo.setLat(31.5822);
				bo.setLongi(74.3292);
				bo.setTimeZone("Asia/Karachi");
				bo.setIsp("Pakistan Telecommuication company limited");
				bo.setOrg("Pakistan Telecommuication company limited");
				bo.setAs("AS17557 Pakistan Telecommunication Company Limited");
				
				StringBuilder query = new StringBuilder();
				query.append(" INSERT INTO trivzia_users_data (accountcode,username,ipaddress,mobile_brand,country,countrycode,region,regionName, ");
				query.append(" city,zip,lat,`long`,timezone,isp,org,`as`) ");
				query.append(" VALUES ");
				query.append(" ( '"+bo.getAccountCode()+ "','"+bo.getUserName()+"','"+bo.getIpAddress()+"','"+bo.getMobileBrand()+"','"+bo.getCountry()+"', ");
				query.append(" '"+bo.getCountryCode()+"','"+bo.getRegion()+"','"+bo.getRegionName()+"','"+bo.getCity()+"', ");
				query.append(" '"+bo.getZip()+"','"+bo.getLat()+"','"+bo.getLongi()+"','"+bo.getTimeZone()+"','"+bo.getIsp()+"','"+bo.getOrg()+"','"+bo.getAs()+"' ) ");
				stmt.addBatch(query.toString());
				if (count % 1000 == 0 || count == result.getFetchSize()) {
	                stmt.executeBatch(); // Execute every 1000 items.
	            }
		//		Thread.sleep(1200);
			}
		} catch (JSONException e) {
			System.out.print(e);
		}  finally {

			prepStmt.close();
			stmt.close();
		    connection.close();

		}
		return true;
	}

}
