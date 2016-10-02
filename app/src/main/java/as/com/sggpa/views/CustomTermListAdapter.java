package as.com.sggpa.views;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import as.com.sggpa.R;
import as.com.sggpa.activities.BaseActivity;
import as.com.sggpa.activities.ModuleListActivity;
import as.com.sggpa.activities.TermListActivity;
import as.com.sggpa.databases.DatabaseOpenHelper;
import as.com.sggpa.infrastructures.IntentExtra;
import as.com.sggpa.infrastructures.Module;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.infrastructures.SgGpaApplication;
import as.com.sggpa.infrastructures.Term;
import as.com.sggpa.utils.Constant;

/**
 * Created by anton on 30/6/2016.
 *
 * <pre>
 *     This class adapter is to customize a listview adapter show stations' information including stations' score.
 *     This adapter is currently used in ViewScoresStationsActivity
 * </pre>
 */
public class CustomTermListAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private BaseActivity activity;
    private ArrayList<Term> data;
    private static LayoutInflater inflater=null;
    private Term tempValues=null;

    /*************  CustomAdapter Constructor *****************/
    public CustomTermListAdapter(BaseActivity a, ArrayList<Term> d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
        return data.size();
    }

    public Term getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView tv_term_name;
        public TextView tv_total_modules;
        public TextView tv_term_gpa;
        public ImageView iv_delete;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listview_item_term, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tv_term_name = (TextView) vi.findViewById(R.id.tv_term_name);
            holder.tv_total_modules =(TextView)vi.findViewById(R.id.tv_total_modules);
            holder.tv_term_gpa = (TextView) vi.findViewById(R.id.tv_term_gpa);
            holder.iv_delete = (ImageView) vi.findViewById(R.id.iv_delete);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        /***** Get each Model object from Arraylist ********/
        tempValues=null;
        tempValues = data.get( position );

        holder.tv_term_name.setText(tempValues.getTermTitle());
        holder.tv_term_gpa.setText(tempValues.getTermGpa() + "/" + tempValues.getHighestGpa());
        holder.tv_total_modules.setText(tempValues.getTotalModules());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListenerPositive = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(activity, tempValues.getTermTitle() + " is deleted", Toast.LENGTH_LONG).show();
                        new DatabaseOpenHelper(activity).deleteTerm(
                                TermListActivity.selectedSchool.getSchoolName(),
                                data.get( position ).getTermTitle());
                        SgGpaApplication.getSgGpaApplication().refreshData();
                        ((TermListActivity)activity).loadTermList((ArrayList<Term>) TermListActivity.selectedSchool.getListOfTerm());
                    }
                };
                DialogInterface.OnClickListener dialogClickListenerNegative = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                };
                activity.showAlertDialog(activity, dialogClickListenerPositive, dialogClickListenerNegative,
                        "Remove term", "Are you sure you want to remove this term?", "YES", "NO", true);
            }
        });

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModuleListActivity.selectedTerm = getItem(position);
                activity.moveToNextActivity(ModuleListActivity.class);
            }
        });
        return vi;
    }
}