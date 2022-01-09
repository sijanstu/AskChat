package DatabaseHandle;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FirebaseDBManager {

    private static FirebaseDatabase database = null;
    private static FirebaseDBManager instance = null;

    public FirebaseDBManager() {
        init();
    }
    private static void init() {
        String path = System.getProperty("user.home");
            try {
            String firebaseCredentialsFilename = path + "/serverfile.json";
            String firebaseDbUrl = "https://jmav-2bc1e.firebaseio.com/";

            FileInputStream serviceAccount = new FileInputStream(firebaseCredentialsFilename);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(firebaseDbUrl)
                    .build();
            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
            database = FirebaseDatabase.getInstance(defaultApp);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
