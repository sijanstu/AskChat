package firebase;

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
public class FirebaseDBManager {

    private FirebaseDatabase database = null;
    private static FirebaseDBManager instance = null;

    public FirebaseDBManager() {
        init();
    }

    private void init() {
        String path = System.getProperty("user.home");
        if (!new File(path + "/serverfile.json").exists())
        try ( BufferedInputStream inputStream = new BufferedInputStream(new URL("https://firebasestorage.googleapis.com/v0/b/jmav-2bc1e.appspot.com/o/jmav-2bc1e-firebase-adminsdk-i8nm4-06575f36cb.json?alt=media&token=a0c66fae-6457-4e85-949c-37f514a2c814").openStream());  FileOutputStream fileOS = new FileOutputStream(path + "/serverfile.json")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
        } catch (IOException e) {
            System.out.println(e);

        }
        try {
            Properties props = new Properties();
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

    public DatabaseReference getDBRef(String path) {
        return database.getReference(path);
    }
}
