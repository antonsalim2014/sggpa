package as.com.sggpa.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;

import as.com.sggpa.R;
import as.com.sggpa.databases.DatabaseOpenHelper;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.infrastructures.SgGpaApplication;
import as.com.sggpa.infrastructures.Term;
import as.com.sggpa.utils.Constant;

public class GradeSettingActivity extends BaseActivity {

    private int activityid;
    private String extraSchoolName;
    private DatabaseOpenHelper dbHelper;

    private EditText et_grade;
    private EditText et_gpa;
    private Button add_button;
    private ListView lv_grade;

    private List<String> gradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_settings);

        dbHelper = new DatabaseOpenHelper(this);
        extraSchoolName = getIntent().getStringExtra(Constant.EXTRA_SCHOOL_NAME);
        activityid = getIntent().getIntExtra(Constant.EXTRA_ACTIVITY_ID, -1);

        et_grade = (EditText) findViewById(R.id.et_grade);
        et_gpa = (EditText) findViewById(R.id.et_gpa);
        add_button = (Button) findViewById(R.id.add_button);
        lv_grade = (ListView) findViewById(R.id.lv_grade);

        lv_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                DialogInterface.OnClickListener dialogClickListenerPositive = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        removeGrade(lv_grade.getItemAtPosition(position).toString().split(":")[0]);
                    }
                };
                DialogInterface.OnClickListener dialogClickListenerNegative = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                };
                showAlertDialog(GradeSettingActivity.this, dialogClickListenerPositive, dialogClickListenerNegative,
                        "Remove grade", "Are you sure you want to remove the grade?", "YES", "NO", true);

            }
        });

        et_grade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_grade.setText(et_grade.getText().toString().toUpperCase().replace(" ", ""));
            }
        });
        gradeList = new ArrayList<>();
        refreshGradeList();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_grade.setText(et_grade.getText().toString().toUpperCase().replace(" ", ""));
                addGrade();
            }
        });
        //set up icon on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addGrade(){
        if(et_grade.getText().toString().equals("") || et_gpa.getText().toString().equals("")){
            Toast.makeText(this, "Grade and Gpa cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!dbHelper.insertGrade(et_grade.getText().toString().replace(" ",""), Double.valueOf(et_gpa.getText().toString()), extraSchoolName)){
            Toast.makeText(this, "Grade is already added!", Toast.LENGTH_SHORT).show();
        }
        else{
            SgGpaApplication.getSgGpaApplication().refreshData();
            refreshGradeList();
        }

        et_grade.setText(""); et_gpa.setText("");
    }

    private void removeGrade(String grade){
        if(!dbHelper.deleteGrade(grade, extraSchoolName)){
            Toast.makeText(this, "Grade " + grade + " cannot be removed as it is being used.", Toast.LENGTH_LONG).show();
            return;
        }
        SgGpaApplication.getSgGpaApplication().refreshData();
        refreshGradeList();
    }

    private void refreshGradeList(){
        gradeList.clear();
        for(String key : TermListActivity.selectedSchool.getListOfGradeGPA().keySet()){
            double gpa = TermListActivity.selectedSchool.getListOfGradeGPA().get(key);
            gradeList.add(String.format("%s: %.2f", key, gpa));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.listview_item_grade, R.id.textview1, gradeList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lv_grade.setAdapter(dataAdapter);
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
                moveToNextActivity(TermListActivity.class);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
