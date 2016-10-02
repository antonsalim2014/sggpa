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
import as.com.sggpa.infrastructures.Term;
import as.com.sggpa.utils.Constant;
import as.com.sggpa.views.CustomTermListAdapter;

public class TermListActivity extends BaseActivity {

    private ListView lv_term;
    private CustomTermListAdapter termListAdapter;
    public static School selectedSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseUIElements();

        loadTermList((ArrayList<Term>) selectedSchool.getListOfTerm());

        //set up icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialiseUIElements()
    {
        lv_term = (ListView) findViewById(R.id.lv_school);
    }

    public void loadTermList(ArrayList<Term> resultList){
        termListAdapter = new CustomTermListAdapter(this, resultList);
        lv_term.setAdapter(termListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term, menu);
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
            case R.id.action_add_term:
                moveToNextActivity(AddActivity.class,
                        new IntentExtra(Constant.EXTRA_ACTIVITY_ID, Constant.TERM_ACTIVITY_ID),
                        new IntentExtra(Constant.EXTRA_SCHOOL_NAME, selectedSchool.getSchoolName()));
                return true;
            case R.id.action_grade_settings:
                moveToNextActivity(GradeSettingActivity.class,
                        new IntentExtra(Constant.EXTRA_ACTIVITY_ID, Constant.TERM_ACTIVITY_ID),
                        new IntentExtra(Constant.EXTRA_SCHOOL_NAME, selectedSchool.getSchoolName()));
                return true;
            case android.R.id.home:
                moveToNextActivity(SchoolListActivity.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
