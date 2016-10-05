package as.com.sggpa.activities;

import android.os.Bundle;

/**
 * Created by Anton Salim on 6/14/2016.
 *
 */
public class AppLauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Initialise Application by getting the instance
        getAppInstance();

        moveToNextActivity(SchoolListActivity.class);
    }
}
