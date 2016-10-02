package as.com.sggpa.activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import as.com.sggpa.R;
import as.com.sggpa.databases.DatabaseOpenHelper;
import as.com.sggpa.infrastructures.IntentExtra;
import as.com.sggpa.infrastructures.Module;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.infrastructures.SgGpaApplication;
import as.com.sggpa.infrastructures.Term;
import as.com.sggpa.utils.Constant;
import as.com.sggpa.views.CustomModuleListAdapter;

public class AddActivity extends BaseActivity {

    private int activityid;
    private String extraSchoolName;
    private String extraTermTitle;
    private Button add_button;
    private String NO_GRAGEGPA_LIST = "no grade found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityid = getIntent().getIntExtra(Constant.EXTRA_ACTIVITY_ID, -1);

        switch (activityid){
            case Constant.SCHOOL_ACTIVITY_ID:
                setContentView(R.layout.activity_add_school);
                break;
            case Constant.TERM_ACTIVITY_ID:
                extraSchoolName = getIntent().getStringExtra(Constant.EXTRA_SCHOOL_NAME);
                setContentView(R.layout.activity_add_term);
                break;
            case Constant.MODULE_ACTIVITY_ID:
                extraTermTitle = getIntent().getStringExtra(Constant.EXTRA_TERM_TITLE);
                setContentView(R.layout.activity_add_module);

                Spinner gradeSpinner = (Spinner) findViewById(R.id.spinner_grade);
                if(ModuleListActivity.selectedTerm.getListOfGradeGPA().size() <= 0){
                    // Spinner Drop down elements
                    List<String> gradeList = new ArrayList<String>();
                    gradeList.add(NO_GRAGEGPA_LIST);

                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gradeList);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gradeSpinner.setAdapter(dataAdapter);
                }
                else{
                    // Creating adapter for spinner\
                    String gradeList[] = new String[ModuleListActivity.selectedTerm.getListOfGradeGPA().size()];
                    ModuleListActivity.selectedTerm.getListOfGradeGPA().keySet().toArray(gradeList);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gradeList);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gradeSpinner.setAdapter(dataAdapter);
                }

                Button grade_settings_button = (Button) findViewById(R.id.grade_settings_button);
                grade_settings_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveToNextActivity(GradeSettingActivity.class,
                                new IntentExtra(Constant.EXTRA_ACTIVITY_ID, Constant.MODULE_ACTIVITY_ID),
                                new IntentExtra(Constant.EXTRA_SCHOOL_NAME, TermListActivity.selectedSchool.getSchoolName()));
                    }
                });
                break;
        }

        add_button = (Button) findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (activityid){
                    case Constant.SCHOOL_ACTIVITY_ID:
                        add_school();
                        break;
                    case Constant.TERM_ACTIVITY_ID:
                        add_term();
                        break;
                    case Constant.MODULE_ACTIVITY_ID:
                        add_module();
                        break;
                }
            }
        });

        //set up icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void add_school(){
        EditText editText = (EditText) findViewById(R.id.et_schoolname);

        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "School name cannot be blank!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        if(!dbHelper.insertSchool(editText.getText().toString())){
            Toast.makeText(this, "School is already existed!", Toast.LENGTH_SHORT).show();
        }
        else{
            moveToNextActivity(SchoolListActivity.class);
        }
    }

    private void add_term(){
        EditText editText = (EditText) findViewById(R.id.et_termtitle);

        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "Term title cannot be blank!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        if(!dbHelper.insertTerm(editText.getText().toString(), extraSchoolName)){
            Toast.makeText(this, "Term is already existed!", Toast.LENGTH_SHORT).show();
        }
        else{
            moveToNextActivity(TermListActivity.class);
        }
    }

    private void add_module(){
        EditText moduleTitle = (EditText) findViewById(R.id.et_moduletitle);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_grade);
        EditText creditUnit = (EditText) findViewById(R.id.et_cu);

        if(spinner.getSelectedItem().toString().equals(NO_GRAGEGPA_LIST)){
            Toast.makeText(this, "Set the grades in Grade Settings first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(moduleTitle.getText().toString().equals("") || creditUnit.getText().toString().equals("")){
            Toast.makeText(this, "Module title and credit unit cannot be blank!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        if(!dbHelper.insertModule(TermListActivity.selectedSchool.getSchoolName(), moduleTitle.getText().toString(), extraTermTitle,
                spinner.getSelectedItem().toString(), Integer.valueOf(creditUnit.getText().toString()))){
            Toast.makeText(this, "Module is already existed!", Toast.LENGTH_SHORT).show();
        }
        else{
            moveToNextActivity(ModuleListActivity.class);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                switch (activityid){
                    case Constant.SCHOOL_ACTIVITY_ID:
                        moveToNextActivity(SchoolListActivity.class);
                        break;
                    case Constant.TERM_ACTIVITY_ID:
                        moveToNextActivity(TermListActivity.class);
                        break;
                    case Constant.MODULE_ACTIVITY_ID:
                        moveToNextActivity(ModuleListActivity.class);
                        break;
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
