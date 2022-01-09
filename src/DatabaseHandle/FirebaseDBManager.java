package DatabaseHandle;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Antonis
 */
public  class FirebaseDBManager {

    private static FirebaseDatabase database = null;
    private static FirebaseDBManager instance = null;

    public FirebaseDBManager() {
        init();
    }

    private static void init() {
        String path = System.getProperty("user.home");
            try {
           // Properties props = new Properties();
            String firebaseCredentialsFilename = path + "/serverfile.json";
            String firebaseDbUrl = "https://jmav-2bc1e.firebaseio.com/";

            FileInputStream serviceAccount = new FileInputStream(firebaseCredentialsFilename);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseDbUrl)
                    .build();
            //FirebaseOptions options = new FirebaseOptions.Builder()
            ///        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            //       .setDatabaseUrl(firebaseDbUrl).build();
            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
            database = FirebaseDatabase.getInstance(defaultApp);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            //System.exit(1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //System.exit(1);
        }
    }

    public static FirebaseDBManager getInstance() {
        if (instance == null) {
            instance = new FirebaseDBManager();
        }

        return instance;
    }

    public static DatabaseReference getDBRef(String path) {
        return database.getReference(path);
    }
}
