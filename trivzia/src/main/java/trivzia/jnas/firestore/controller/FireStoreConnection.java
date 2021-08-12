package trivzia.jnas.firestore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.common.collect.Lists;

public class FireStoreConnection {
	Firestore db;
	GoogleCredentials credentials = null;

	public FireStoreConnection() throws IOException {
//	String path="D:\\TrivziaDoc\\testingjeeto-firebase-adminsdk-eeokq-d27b582795.json";
	String path="/usr/local/src/SmartFoxServer_2X/SFS2X/data/Trivzia-7c763464a68f.json";

		
		
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(new File(path)))
					.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Exception" + e1.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Exception" + e1.toString());
		}

		System.out.println("Credentials : " + credentials.toString());

		FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder().setCredentials(credentials).setTimestampsInSnapshotsEnabled(true).build();
		Firestore db = firestoreOptions.getService();
		// [END fs_initialize_project_id]
		this.db = db;
	}
		
	

	public Firestore getDb() {
		return db;
	}
	
	
}
