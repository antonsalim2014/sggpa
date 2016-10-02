package as.com.sggpa.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import as.com.sggpa.infrastructures.IntentExtra;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.infrastructures.SgGpaApplication;
import as.com.sggpa.infrastructures.Term;

/**
 * Created by anton on 4/6/2016.
 *
 * <pre>
 *     This class is a base class activity that should be extended to any activity class.
 * </pre>
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        getAppInstance().setCurrActivity(this);
        getAppInstance().refreshData();
    }

    protected SgGpaApplication getAppInstance(){
        return SgGpaApplication.getSgGpaApplication();
    }


    /**
     * Move to next activity and close the current activity
     * @param activityClass (some_activity).class
     */
    public void moveToNextActivity(Class activityClass, IntentExtra... extras)
    {
        Intent intent = new Intent(BaseActivity.this, activityClass);
        for(IntentExtra extra : extras)
        {
            if(extra.type == Integer.class){
                intent.putExtra(extra.name, (Integer) extra.value);
            }
            else if(extra.type == String.class){

                intent.putExtra(extra.name, (String) extra.value);
            }
        }
        startActivity(intent);
        this.finish();
    }

    /**
     * To show alert dialog
     * @param context current activity context
     * @param onClickListenerPositive
     * @param title
     * @param message
     * @param positiveButtonText
     * @param cancelable
     */
    public static void showAlertDialog(
            Context context, DialogInterface.OnClickListener onClickListenerPositive,
            DialogInterface.OnClickListener onClickListenerNegative,
            String title, String message, String positiveButtonText, String negativeButtonText,
            boolean cancelable){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(Html.fromHtml(message))
                .setCancelable(cancelable)
                .setPositiveButton(positiveButtonText, onClickListenerPositive)
                .setNegativeButton(negativeButtonText, onClickListenerNegative);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
