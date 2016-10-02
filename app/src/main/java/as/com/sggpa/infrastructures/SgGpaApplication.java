package as.com.sggpa.infrastructures;

import android.app.Application;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import as.com.sggpa.activities.BaseActivity;
import as.com.sggpa.activities.ModuleListActivity;
import as.com.sggpa.activities.TermListActivity;
import as.com.sggpa.databases.DatabaseOpenHelper;

/**
 * Created by anton on 4/6/2016.
 *
 * <pre>
 *     This class stores the user's information and a list of animal icons.
 *     It also provides methods to get this application instance, to set/get user's information and to log user out.
 *     This class is designed as a Singleton where there is only one application instance during the application lifecycle.
 * </pre>
 *
 *
 *
 */
public class SgGpaApplication extends Application {

    // Application instances
    private static SgGpaApplication sgGpaApp = null;
    private List<School> schoolList;
    private DatabaseOpenHelper mDbHelper;

    // Other variables
    private BaseActivity currActivity = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    private SgGpaApplication()
    {

    }

    public void refreshData(){
        mDbHelper = new DatabaseOpenHelper(currActivity);
        schoolList = mDbHelper.getAllData();

        if(TermListActivity.selectedSchool != null){
            //update selectedSchool in TermListActivity
            for(School school : schoolList){
                if(TermListActivity.selectedSchool.getSchoolName().equals(school.getSchoolName())){
                    TermListActivity.selectedSchool = school;
                    break;
                }
            }
        }

        if(ModuleListActivity.selectedTerm != null){
            //update selectedTerm in ModuleListActivity
            for(Term term : TermListActivity.selectedSchool.getListOfTerm()){
                if(ModuleListActivity.selectedTerm.getTermTitle().equals(term.getTermTitle())){
                    ModuleListActivity.selectedTerm = term;
                    break;
                }
            }
        }
    }

    /**
     * To get Mantro application instance
     * @return MantroApplication
     */
    public static SgGpaApplication getSgGpaApplication()
    {
        if(sgGpaApp == null)
            sgGpaApp = new SgGpaApplication();

        return sgGpaApp;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public BaseActivity getCurrActivity() {
        return currActivity;
    }

    public void setCurrActivity(BaseActivity currActivity) {
        this.currActivity = currActivity;
    }

}
