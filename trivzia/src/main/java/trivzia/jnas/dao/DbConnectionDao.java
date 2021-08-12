package trivzia.jnas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import redis.clients.jedis.Jedis;

public class DbConnectionDao
{

 	static String connectionString = "jdbc:mysql://45.33.120.188:3306/trivzia?user=luckydraw&password=Je5sKwwDJJQjrnPs";
//	static String connectionString = "jdbc:mysql://localhost:3306/trivzia_dev?user=usr&password=system4242&useSSL=false";
	static String host = "192.168.159.148";
//	static String host="127.0.0.1";
	static String auth="NE94fEGeyfx2cQQ8";
//	static String auth="p@ss1234";
	
	public static Jedis writeConnection(int num)
	{
		try
		{
		//	String host = "192.168.159.148";
			 
			int port = 6379;
			Jedis jedis = new Jedis(host, port);
			/// Authentication information.
			jedis.auth(auth);// password
			jedis.select(num);
			return jedis;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Redis connection open exception : " + e.toString());
		}
		return null;
	}

	public static Jedis getConnection()
	{

		try
		{

			int port = 6379;
			Jedis jedis = new Jedis(host, port);
			// Authentication information.
			jedis.auth(auth);// password
			jedis.select(5);
			return jedis;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("Redis connection open exception : " + e.toString());
		}
		return null;
	}

	public void closeConnection(Jedis jedis)
	{
		try
		{
			jedis.quit();
			jedis.close();
			// System.out.println(" redis connection for luckydraw close
			// successfully");
		}
		catch (Exception e)
		{
			System.out.println(" error in close redis connection for luckydraw " + e);
		}

	}

	
	
    public static Connection getSqlConnection()
    {
		Connection con = null;

		try
		{

			con = DriverManager.getConnection(connectionString);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
        
    }



	public boolean CloseDBConnection(Connection myConn)
	{
		try
		{
			myConn.close();
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.out.println("Error in close connection"+e);
			return false;
		}
	}
}
