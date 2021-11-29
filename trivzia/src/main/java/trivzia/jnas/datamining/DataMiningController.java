package trivzia.jnas.datamining;

import java.sql.SQLException;

import trivzia.jnas.firestore.controller.FireStoreConnection;

public class DataMiningController {

	public String realtime = "RealtimeTest";
	FireStoreConnection fb = null;
	DataMiningController(final boolean test)
	{

		DataMiningRawalPindi lhr =new DataMiningRawalPindi();
		try {
			if(lhr.getOldBillingValues())
			{
				lhr.getOldBillingValues1();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		
	}

	public static void main(String[] args)
	{
		String realtimeTestValue = "";
		if (args.length > 0)
		{
			realtimeTestValue = args[0];
		}
		DataMiningController datamining = new DataMiningController(
				realtimeTestValue.equalsIgnoreCase("test"));

	}
}
