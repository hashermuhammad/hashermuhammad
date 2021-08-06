package trivzia.jnas.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONObject;

import redis.clients.jedis.Jedis;

public class LuckyDrawDao extends DbConnectionDao
{
	

	public void uploadUsers(Jedis jedis)
	{
		 StringBuilder query =new StringBuilder();
		 query.append(" SELECT  u.accountcode,u.username,u.phone,u.gamePlayed,u.datetime,f.new_balance ");
		 query.append(" FROM trivzia_users u INNER JOIN trivzia_financials f ON u.accountcode=f.accountcode ");
		 query.append(" WHERE u.status=1 AND u.username IS NOT NULL AND u.username != '' AND DATE(u.lastGamePlayed) BETWEEN '2021-07-29' AND '2021-07-31' ");
		 Connection	DBConnection = getSqlConnection();
		Statement myStatement;
		try
		{
			myStatement = DBConnection.createStatement();
			ResultSet resultSet = myStatement.executeQuery(query.toString());
		//	int resultsCount = resultSet.getFetchSize();
			while (resultSet.next()) {
				JSONObject obj= new JSONObject();
				obj.put("accountcode", resultSet.getString("accountcode"));
				obj.put("username", resultSet.getString("username"));
				obj.put("phone", resultSet.getString("phone"));
				obj.put("gamePlayed", resultSet.getString("gamePlayed"));
				obj.put("datetime", resultSet.getString("datetime"));
				obj.put("new_balance", resultSet.getString("new_balance"));
				obj.put("Online", true);
				jedis.set(resultSet.getString("phone"), obj.toString());
			}
			DBConnection.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
}
