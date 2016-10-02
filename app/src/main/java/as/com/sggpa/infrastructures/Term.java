package as.com.sggpa.infrastructures;

import java.util.List;
import java.util.Map;

/**
 * Created by anton on 29/9/2016.
 */
public class Term{

    private String termTitle;
    private List<Module> listOfModule;
    private Map<String, Double> listOfGradeGPA;

    public Term(String termTitle, List<Module> listOfModule, Map<String, Double> listOfGradeGPA){
        this.termTitle = termTitle;
        this.listOfModule = listOfModule;
        this.listOfGradeGPA = listOfGradeGPA;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public List<Module> getListOfModules() {
        return listOfModule;
    }

    public Map<String, Double> getListOfGradeGPA() {
        return listOfGradeGPA;
    }

    public void setListOfModule(List<Module> listOfModule) {
        this.listOfModule = listOfModule;
    }

    public void setListOfGradeGPA(Map<String, Double> listOfGradeGPA) {
        this.listOfGradeGPA = listOfGradeGPA;
    }

    public String getTermGpa(){
        double totalGpa = 0f;
        double totalCu = 0;

        for(Module eachModule : getListOfModules()){
            totalCu += eachModule.getCreditUnit();
            totalGpa += listOfGradeGPA.get(eachModule.getgrade()) * eachModule.getCreditUnit();
        }

        if(totalCu <= 0)
            return String.format("%.2f", 0.00);
        return String.format("%.2f", totalGpa/totalCu);
    }

    public String getHighestGpa(){
        if(listOfGradeGPA.size() <= 0)
            return String.format("%.2f", 0.00);

        double highestGpa = 0;
        for(Double gpa : listOfGradeGPA.values()){
            if(gpa > highestGpa)
                highestGpa = gpa;
        }
        return String.format("%.2f", highestGpa);
    }

    public String getTotalModules(){
        return String.valueOf(listOfModule.size());
    }
}
