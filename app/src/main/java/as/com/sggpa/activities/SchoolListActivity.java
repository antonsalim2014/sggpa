package as.com.sggpa.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import as.com.sggpa.R;
import as.com.sggpa.infrastructures.IntentExtra;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.utils.Constant;
import as.com.sggpa.views.CustomSchoolListAdapter;

public class SchoolListActivity extends BaseActivity {

    private ListView lv_school;
    private CustomSchoolListAdapter schoolListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseUIElements();

        loadSchoolList((ArrayList<School>) getAppInstance().getSchoolList());
    }

    private void initialiseUIElements()
    {
        lv_school = (ListView) findViewById(R.id.lv_school);
    }

    public void loadSchoolList(ArrayList<School> resultList){
        schoolListAdapter = new CustomSchoolListAdapter(this, resultList);
        lv_school.setAdapter(schoolListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_school, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add_school:
                moveToNextActivity(AddActivity.class, new IntentExtra(Constant.EXTRA_ACTIVITY_ID, Constant.SCHOOL_ACTIVITY_ID));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
