package as.com.sggpa.infrastructures;

import android.os.Parcelable;

/**
 * Created by anton on 15/6/2016.
 *
 * <pre>
 *     This class is a customised class for Intent extra
 * </pre>
 */
public class IntentExtra {

    public String name;
    public Object value;
    public Class type;

    public IntentExtra()
    {
        name = "";
        value = "";
        type = null;
    }

    public IntentExtra(String name, int value){
        this.name = name;
        this.value = value;
        this.type = Integer.class;
    }

    public IntentExtra(String name, String value)
    {
        this.name = name;
        this.value = value;
        this.type = String.class;
    }

    public IntentExtra(String name, Boolean value)
    {
        this.name = name;
        this.value = value;
        this.type = Boolean.class;
    }

    public IntentExtra(String name, Parcelable value)
    {
        this.name = name;
        this.value = value;
        this.type = Parcelable.class;
    }
}
