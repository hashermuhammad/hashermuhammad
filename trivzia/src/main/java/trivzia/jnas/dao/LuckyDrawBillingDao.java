package trivzia.jnas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import trivzia.jnas.luckydraw.bo.Billing;


public class LuckyDrawBillingDao extends DbConnectionDao
{
	public static Billing getOldBillingValues(String accountCode,Connection connection) throws SQLException
	{
		
		String getvalues=" SELECT u.gold,u.silver,u.plustime,u.lifeline,IF(f.new_balance IS NULL,0,f.new_balance) new_balance, "
				+ " IF(f.totalearnings IS NULL,0,f.totalearnings) totalearnings "
				+ " FROM trivzia_users u LEFT JOIN trivzia_financials f ON u.accountcode=f.accountcode "
				+ " WHERE u.accountcode=? ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		Billing bill =new Billing();
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(getvalues);

			prepStmt.setString(1, accountCode);
			
			ResultSet result = prepStmt.executeQuery();
			
			while(result.next()) {
			    bill.setGamepoint(result.getInt("new_balance"));
			    bill.setGold(result.getInt("gold"));
			    bill.setLifeline(result.getInt("lifeline"));
			    bill.setPlustime(result.getInt("plustime"));
			    bill.setSilver(result.getInt("silver"));
			    bill.setTotalEarning(result.getInt("totalearnings"));
			}
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return bill;
	}
	
	
	public int  insertLifeLine(String accountCode,int quantity,int lifeLines,String notes,String date,Connection connection) throws SQLException
	{
		
		String insertLifeline = " INSERT INTO trivzia_lifeline (accountcode,credit,cur_lifes,datetime,notes,debit) VALUES "
				+ " (?,?,?,?,?,?) ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(insertLifeline,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, accountCode);
			prepStmt.setInt(2, quantity);
			prepStmt.setInt(3, quantity+lifeLines);
			prepStmt.setString(4, date);
			prepStmt.setString(5, notes);
			prepStmt.setInt(6, 0);
			
			/*
			 * System.out.println(prepStmt.toString()); LOAD DATA INFILE
			 * 'C:/test/yourTableName.csv' INTO TABLE test.yourTableName FIELDS
			 * ENCLOSED BY '"' TERMINATED BY ';' ESCAPED BY '"' LINES TERMINATED
			 * BY '\r\n';
			 */
		     prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
		//		    System.out.println("Generated key for insertLifeline ="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	
	
	
	public int  updateLifeLine(String accountCode,int quantity,int lifeLines,String notes,String date,Connection connection) throws SQLException
	{
		
		String updateLifeline = " UPDATE trivzia_users u SET u.lifeline=? WHERE u.accountcode=? ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(updateLifeline,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setInt(1, quantity+lifeLines);
			prepStmt.setString(2, accountCode);
			
			
			
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
		//		    System.out.println("Generated key for updateLifeline="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	
	public int  insertPlusFive(String accountCode,int quantity,int plustime,String notes,String date,Connection connection) throws SQLException
	{
		
		String insertPlusTime = " INSERT INTO trivzia_plustime (accountcode,credit,plustime,datetime,notes,debit,question_num) VALUES "
				+ " (?,?,?,?,?,?,?) ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(insertPlusTime,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, accountCode);
			prepStmt.setInt(2, quantity);
			prepStmt.setInt(3, quantity+plustime);
			prepStmt.setString(4, date);
			prepStmt.setString(5, notes);
			prepStmt.setInt(6, 0);
			prepStmt.setString(7, "");
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
			//	    System.out.println("Generated key for insertPlusFive ="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	public int  updatePlustime(String accountCode,int quantity,int plustime,String notes,String date,Connection connection) throws SQLException
	{
		
		String updatePlusTime = " UPDATE trivzia_users u SET u.plustime=? WHERE u.accountcode=? ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(updatePlusTime,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setInt(1, quantity+plustime);
			prepStmt.setString(2, accountCode);
			
			
			
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
			//	    System.out.println("Generated key for updatePlusTime="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	
	
	public int  insertSilver(String accountCode,int quantity,int silver,String notes,String date,Connection connection) throws SQLException
	{
		
		String insertSilver = " INSERT INTO trivzia_silver (accountcode,credit,silver,datetime,notes,debit,question_num) VALUES "
				+ " (?,?,?,?,?,?,?) ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(insertSilver,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, accountCode);
			prepStmt.setInt(2, quantity);
			prepStmt.setInt(3, quantity+silver);
			prepStmt.setString(4, date);
			prepStmt.setString(5, notes);
			prepStmt.setInt(6, 0);
			prepStmt.setString(7, "");
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
				//    System.out.println("Generated key for insertSilver ="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	public int  updateSilver(String accountCode,int quantity,int silver,String notes,String date,Connection connection) throws SQLException
	{
		
		String updateSilver = " UPDATE trivzia_users u SET u.silver=? WHERE u.accountcode=? ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(updateSilver,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setInt(1, quantity+silver);
			prepStmt.setString(2, accountCode);
			
			
			
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
				//    System.out.println("Generated key for updateSilver="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	public int  insertGold(String accountCode,int quantity,int gold,String notes,String date,Connection connection) throws SQLException
	{
		
		String insertGold = " INSERT INTO trivzia_gold (accountcode,credit,gold,datetime,notes,debit,question_num) VALUES "
				+ " (?,?,?,?,?,?,?) ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(insertGold,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, accountCode);
			prepStmt.setInt(2, quantity);
			prepStmt.setInt(3, quantity+gold);
			prepStmt.setString(4, date);
			prepStmt.setString(5, notes);
			prepStmt.setInt(6, 0);
			prepStmt.setString(7, "");
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
			//	    System.out.println("Generated key for insertGold ="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	public int  updateGold(String accountCode,int quantity,int gold,String notes,String date,Connection connection) throws SQLException
	{
		
		String updateGold = " UPDATE trivzia_users u SET u.gold=? WHERE u.accountcode=? ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(updateGold,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setInt(1, quantity+gold);
			prepStmt.setString(2, accountCode);
			
			
			
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
			//	    System.out.println("Generated key for updateGold="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	public int  insertGamePoint(String accountCode,int quantity,int gp,String notes,String date,Connection connection) throws SQLException
	{
		
		String insertGamepoint=" INSERT INTO trivzia_ledger (accountcode,datetime,amount,new_credit,new_balance,new_debit) VALUES "
				+ " (?,?,?,?,?,?) " ;
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(insertGamepoint,Statement.RETURN_GENERATED_KEYS);

			prepStmt.setString(1, accountCode);
			prepStmt.setInt(2, quantity);
			prepStmt.setInt(3, quantity+gp);
			prepStmt.setString(4, date);
			prepStmt.setString(5, notes);
			prepStmt.setInt(6, 0);
			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
			//	    System.out.println("Generated key for insertGamepoint ="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
	
	
	public int  updateGamePoint(String accountCode,int quantity,int gp,String notes,String date,Connection connection) throws SQLException
	{
		
		String updateGamepoint=" UPDATE trivzia_financials tf SET tf.totalearnings=?,tf.new_balance=? WHERE tf.accountcode=? ";
	//	Connection connection = null;
		PreparedStatement prepStmt = null;
		int update=-1;
		try
		{
	//		connection = getSqlConnection();
			
			
			prepStmt = connection.prepareStatement(updateGamepoint,Statement.RETURN_GENERATED_KEYS);

			
			prepStmt.setInt(1, quantity+gp);
			prepStmt.setInt(2, quantity+gp);
			prepStmt.setString(3, accountCode);

			
			
		prepStmt.executeUpdate();
			 ResultSet result  = prepStmt.getGeneratedKeys();
			 int key = result.next() ? result.getInt(1) : 0;
			 if(key!=0){
			//	    System.out.println("Generated key for updateGamepoint ="+key);
				    update=key;
				}
			
			
		}
		finally
		{
			
				prepStmt.close();
		//		connection.close();
				
			
		}
		return update;
	}
}
