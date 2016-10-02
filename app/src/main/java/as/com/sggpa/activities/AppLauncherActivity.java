package as.com.sggpa.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import as.com.sggpa.utils.Commons;

/**
 * Created by Anton Salim on 6/14/2016.
 *
 */
public class AppLauncherActivity extends BaseActivity {

    private FirebaseDatabase firebaseDb;
    private DatabaseReference dbMainRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Initialise Application by getting the instance
        getAppInstance();

        firebaseDb = FirebaseDatabase.getInstance();
        dbMainRef = firebaseDb.getReference();
        dbMainRef.child(Commons.getDeviceID(this)).setValue(Commons.getDeviceID(this));

        moveToNextActivity(SchoolListActivity.class);
    }
}
