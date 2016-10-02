package as.com.sggpa.infrastructures;

/**
 * Created by anton on 29/9/2016.
 */
public class Module {

    private String moduleTitle;
    private String grade;
    private int creditUnit;

    public Module(String moduleTitle, String grade, int creditUnit){
        this.moduleTitle = moduleTitle;
        this.grade = grade;
        this.creditUnit = creditUnit;
    }

    public String getModuleTitle() {
        return this.moduleTitle;
    }

    public String getgrade() {
        return this.grade;
    }

    public int getCreditUnit() {
        return this.creditUnit;
    }
}
