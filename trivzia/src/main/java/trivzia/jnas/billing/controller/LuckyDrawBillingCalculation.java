package trivzia.jnas.billing.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import trivzia.jnas.constant.SysConstants;
import trivzia.jnas.dao.LuckyDrawBillingDao;
import trivzia.jnas.firestore.controller.FireStoreConnection;
import trivzia.jnas.helper.LuckyDrawBillingHelper;
import trivzia.jnas.luckydraw.bo.Billing;
import trivzia.jnas.utils.DateUtils;

public class LuckyDrawBillingCalculation extends LuckyDrawBillingHelper implements Runnable
{
	ArrayList<String> keys;
	String gameType = "RealtimeTest";
	LuckyDrawBillingController luckyDrawBillingController;
	int tCount = 0;

	LuckyDrawBillingCalculation(boolean test, FireStoreConnection fb, ArrayList<String> keys,
			LuckyDrawBillingController luckyDrawBillingController, int tCount)
	{
		if (test)
		{

			this.gameType = "RealtimeTest";
		}
		else
		{

			this.gameType = "Realtime";
		}
		this.tCount = tCount;
		this.keys = keys;
		this.luckyDrawBillingController = luckyDrawBillingController;
	}

	@Override
	public void run()
	{
		callBusinessLogic1();

	}

	public void callBusinessLogic1()
	{
		try
		{
			callBusinessLogic2();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.out.println("Error in Billing Logic" + e);
		}
	}

	public void callBusinessLogic2() throws SQLException
	{
		Jedis jedis = writeConnection(7);
		Jedis delKeys = writeConnection(6);
		Connection connection = null;
		connection = getSqlConnection();
		LuckyDrawBillingDao dao = new LuckyDrawBillingDao();
		ArrayList<JSONObject> objArr = new ArrayList<JSONObject>();
		int totalCount = 0;
		String key = null;
		String value = null;
		int i = 0;
		for (i = 0; i < keys.size(); i++)
		{
			++luckyDrawBillingController.count;
			try
			{
				key = keys.get(i);
				value = jedis.get(key);
			}
			catch (IndexOutOfBoundsException e)
			{
				System.out.println("The index you have entered is invalid" + i);
			}
			
			if (value != null && !value.equals("")) {

				try {
					JSONArray obj = new JSONArray(value);
					for (int j = 0; j < obj.length(); ++j) {
						JSONObject json = obj.getJSONObject(j);

						if (json.has("billing") && json.getBoolean("billing")) {
							++totalCount;
							String rewardType = json.getString("rewardType");
							objArr.add(json);

							String accountcode = json.getString("accountcode");
							int quantity = json.getInt("quantity");
							String notes = "lucky_draw";
							DateUtils date = new DateUtils();

							Billing b = LuckyDrawBillingDao.getOldBillingValues(accountcode, connection);
							// System.out.println(b.getTotalEarning()+"-"+b.getGamepoint()+"-"+b.getLifeline());
							if (rewardType.equals(SysConstants.REWARD_LIFE_LINE)) {
								int insertkey = dao.insertLifeLine(accountcode, quantity, b.getLifeline(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey > 0) {
									dao.updateLifeLine(accountcode, quantity, b.getLifeline(), notes,
											date.getCurrentDateTimeString(), connection);
								}
							} else if (rewardType.equals(SysConstants.REWARD_PLUS_TIME)) {

								int insertkey = dao.insertPlusFive(accountcode, quantity, b.getPlustime(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey > 0) {
									dao.updatePlustime(accountcode, quantity, b.getPlustime(), notes,
											date.getCurrentDateTimeString(), connection);
								}
							} else if (rewardType.equals(SysConstants.REWARD_SILVER)) {

								int insertkey = dao.insertSilver(accountcode, quantity, b.getSilver(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey > 0) {
									dao.updateSilver(accountcode, quantity, b.getSilver(), notes,
											date.getCurrentDateTimeString(), connection);
								}

							} else if (rewardType.equals(SysConstants.REWARD_GOLD)) {

								int insertkey = dao.insertGold(accountcode, quantity, b.getGold(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey > 0) {
									dao.updateGold(accountcode, quantity, b.getGold(), notes,
											date.getCurrentDateTimeString(), connection);
								}

							} else if (rewardType.equals(SysConstants.REWARD_BUNDLE)) {
								int insertkey = dao.insertLifeLine(accountcode, quantity, b.getLifeline(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey > 0) {
									dao.updateLifeLine(accountcode, quantity, b.getLifeline(), notes,
											date.getCurrentDateTimeString(), connection);
								}

								int insertkey2 = dao.insertPlusFive(accountcode, quantity, b.getPlustime(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey2 > 0) {
									dao.updatePlustime(accountcode, quantity, b.getPlustime(), notes,
											date.getCurrentDateTimeString(), connection);
								}

								int insertkey3 = dao.insertSilver(accountcode, quantity, b.getSilver(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey3 > 0) {
									dao.updateSilver(accountcode, quantity, b.getSilver(), notes,
											date.getCurrentDateTimeString(), connection);
								}

								int insertkey4 = dao.insertGold(accountcode, quantity, b.getGold(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey4 > 0) {
									dao.updateGold(accountcode, quantity, b.getGold(), notes,
											date.getCurrentDateTimeString(), connection);
								}
							} else if (rewardType.equals(SysConstants.REWARD_GAMEPOINT)) {

								int insertkey5 = dao.insertGamePoint(accountcode, quantity, b.getGamepoint(), notes,
										date.getCurrentDateTimeString(), connection);
								if (insertkey5 > 0) {
									dao.updateGamePoint(accountcode, quantity, b.getGamepoint(), notes,
											date.getCurrentDateTimeString(), connection);
								}

							}
						} else {
							// System.out.println(" This is Deleting object " + json.toString());
							if(obj.length()>1)
							{
								obj.remove(j);
								delKeys.set(json.getString("phone"), json.toString());
							}
							else
							{
								jedis.del(key);
								delKeys.set(json.getString("phone"), json.toString());
							}


						}
					}
				}
				catch (JSONException ex)
				{

					System.out.println("This is not a json Object against key" + keys.get(i) + ex);
				}

			}
			else
			{
				System.out.println(" Object Value is Empty Or Null Against " + keys.get(i));
			}
		}

		luckyDrawBillingController.luckyWinnerArray.addAll(objArr);
		closeConnection(jedis);
		closeConnection(delKeys);
		System.out.println("This is total Count" + totalCount);
		CloseDBConnection(connection);

	}

}
