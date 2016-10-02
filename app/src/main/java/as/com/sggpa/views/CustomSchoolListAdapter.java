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
import as.com.sggpa.activities.SchoolListActivity;
import as.com.sggpa.activities.TermListActivity;
import as.com.sggpa.databases.DatabaseOpenHelper;
import as.com.sggpa.infrastructures.*;
import as.com.sggpa.utils.Constant;

/**
 * Created by anton on 30/6/2016.
 *
 * <pre>
 *     This class adapter is to customize a listview adapter show stations' information including stations' score.
 *     This adapter is currently used in ViewScoresStationsActivity
 * </pre>
 */
public class CustomSchoolListAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private BaseActivity activity;
    private ArrayList<School> data;
    private static LayoutInflater inflater=null;
    private School tempValues=null;

    /*************  CustomAdapter Constructor *****************/
    public CustomSchoolListAdapter(BaseActivity a, ArrayList<School> d) {

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

    public School getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView tv_school_name;
        public TextView tv_total_terms;
        public TextView tv_c_gpa;
        public ImageView iv_delete;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listview_item_school, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tv_school_name = (TextView) vi.findViewById(R.id.tv_school_name);
            holder.tv_total_terms =(TextView)vi.findViewById(R.id.tv_total_terms);
            holder.tv_c_gpa = (TextView) vi.findViewById(R.id.tv_c_gpa);
            holder.iv_delete = (ImageView) vi.findViewById(R.id.iv_delete);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        /***** Get each Model object from Arraylist ********/
        tempValues=null;
        tempValues = data.get( position );

        holder.tv_school_name.setText(tempValues.getSchoolName());
        holder.tv_c_gpa.setText(tempValues.getCGPA() + "/" + tempValues.getHighestGpa());
        holder.tv_total_terms.setText(tempValues.getTotalTerms());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListenerPositive = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(activity, tempValues.getSchoolName() + " is deleted", Toast.LENGTH_LONG).show();
                        new DatabaseOpenHelper(activity).deleteSchool(
                                data.get( position ).getSchoolName());
                        SgGpaApplication.getSgGpaApplication().refreshData();
                        ((SchoolListActivity)activity).loadSchoolList((ArrayList<School>) SgGpaApplication.getSgGpaApplication().getSchoolList());
                    }
                };
                DialogInterface.OnClickListener dialogClickListenerNegative = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                };
                activity.showAlertDialog(activity, dialogClickListenerPositive, dialogClickListenerNegative,
                        "Remove school", "Are you sure you want to remove this school?", "YES", "NO", true);
            }
        });

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermListActivity.selectedSchool = getItem(position);
                activity.moveToNextActivity(TermListActivity.class);
            }
        });
        return vi;
    }
}