package trivzia.jnas.datamining;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import trivzia.jnas.dao.DbConnectionDao;

public class DataminingHelper extends DbConnectionDao {
	
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {

			JSONTokener tokener = new JSONTokener(new InputStreamReader(is, Charset.forName("UTF-8")));

			JSONObject json = new JSONObject(tokener);
			return json;
		} finally {
			is.close();
		}
	}
	
	public static JSONArray readJsonFromUrl(JSONArray ar) throws IOException, JSONException {
		
		JSONArray jsonar=null;
		String urlParameters  = ar.toString();
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		String request        = "http://ip-api.com/batch";
		URL    url            = new URL( request );
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
		conn.setDoOutput( true );
		conn.setInstanceFollowRedirects( false );
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty( "Content-Type", "application/json"); 
		conn.setRequestProperty( "charset", "utf-8");
		conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
		conn.setUseCaches( false );
		try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
		   wr.write( postData );
		   
		   Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		   JSONTokener tokener = new JSONTokener(in);
		   jsonar= new JSONArray(tokener);
	        
	            System.out.print(jsonar.toString());
		}
		return jsonar;
	}
	
   
}
