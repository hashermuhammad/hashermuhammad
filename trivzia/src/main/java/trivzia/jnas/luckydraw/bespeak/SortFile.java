package trivzia.jnas.luckydraw.bespeak;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import trivzia.jnas.helper.LuckyDrawHelper15;

public class SortFile {
	static String root="/usr/local/src/SmartFoxServer_2X/SFS2X/data/";
//	static String root="C:\\Users\\LENOVO\\Downloads\\";
	public static void main(String[] args)
	{
		String realtimeTestValue = "";
		if (args.length > 0) {
			realtimeTestValue = args[0];
		}

		try
		{
			File file = new File(root+realtimeTestValue+".txt");
			FileReader fr = new FileReader(file); // reads the file
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
				sb.append("\n");
				
			}
			fr.close();
			String jsonOb=sb.toString();
			LuckyDrawHelper15 hp = new LuckyDrawHelper15();
			JSONObject root = new JSONObject(jsonOb);
			String Date=root.getString("Date");
			JSONArray jsonArray = (JSONArray) root.get("lucky_winners");
			JSONObject topluckyWinnersObject = new JSONObject();
			JSONArray topsortedarray = hp.getSortedData(jsonArray, "amount");
			topluckyWinnersObject.put("lucky_winners", topsortedarray);
			topluckyWinnersObject.put("Date", Date);
			createLuckyWinnerFile(topluckyWinnersObject.toString(), "LuckyWinner.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	public static void createLuckyWinnerFile(String luckyWinnersJSON,String filename) throws IOException {
		// System.out.println("Creating file at path : " + rootPath + "Winner.txt");
		File file = new File(root, filename);

		// Create the file
		if (file.createNewFile())
		{
			System.out.println("File is created!");
			FileUtils.writeStringToFile(file, luckyWinnersJSON, Charset.defaultCharset());
		}
		else
		{
			FileUtils.writeStringToFile(file, luckyWinnersJSON, Charset.defaultCharset());
			System.out.println("File already exists.");
		}

		// Write Content

		System.out.println(" file write" + filename + " completed.");
	}
}
