package gr.istl.collaborativechat.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Antonis
 */
public class FirebaseDBManager {

    private FirebaseDatabase database = null;
    private static FirebaseDBManager instance = null;

    public FirebaseDBManager() {
        init();
    }

    private void init() {
        try {
            Properties props = new Properties();
            props.loadFromXML(new FileInputStream("settings.xml"));
            String firebaseCredentialsFilename = props.getProperty("firebase_admin_sdk_service_key_filename");
            String firebaseDbUrl = props.getProperty("firebase_db_url");

            FileInputStream serviceAccount = new FileInputStream(firebaseCredentialsFilename);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseDbUrl).build();
            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
            database = FirebaseDatabase.getInstance(defaultApp);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public static FirebaseDBManager getInstance() {
        if (instance == null) {
            instance = new FirebaseDBManager();
        }

        return instance;
    }

    public DatabaseReference getDBRef(String path) {
        return database.getReference(path);
    }
}
