package as.com.sggpa.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import as.com.sggpa.R;
import as.com.sggpa.infrastructures.IntentExtra;
import as.com.sggpa.infrastructures.Module;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.infrastructures.Term;
import as.com.sggpa.utils.Constant;
import as.com.sggpa.views.CustomModuleListAdapter;
import as.com.sggpa.views.CustomSchoolListAdapter;

public class ModuleListActivity extends BaseActivity {

    private ListView lv_module;
    private CustomModuleListAdapter moduleListAdapter;
    public static Term selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseUIElements();

        loadModuleList((ArrayList<Module>) selectedTerm.getListOfModules());

        //set up icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialiseUIElements()
    {
        lv_module = (ListView) findViewById(R.id.lv_school);
    }

    public void loadModuleList(ArrayList<Module> resultList){
        moduleListAdapter = new CustomModuleListAdapter(this, resultList);
        lv_module.setAdapter(moduleListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_module, menu);
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
            case R.id.action_add_module:
                moveToNextActivity(AddActivity.class,
                        new IntentExtra(Constant.EXTRA_ACTIVITY_ID, Constant.MODULE_ACTIVITY_ID),
                        new IntentExtra(Constant.EXTRA_TERM_TITLE, selectedTerm.getTermTitle()));
                return true;
            case android.R.id.home:
                moveToNextActivity(TermListActivity.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
