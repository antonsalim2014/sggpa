package as.com.sggpa.infrastructures;

import java.util.List;
import java.util.Map;

/**
 * Created by anton on 29/9/2016.
 */
public class School {

    private String schoolName;
    private List<Term> listOfTerm;
    private Map<String, Double> listOfGradeGPA;

    public School(String schoolName, List<Term> listOfTerm, Map<String, Double> listOfGradeGPA){
        this.schoolName = schoolName;
        this.listOfTerm = listOfTerm;
        this.listOfGradeGPA = listOfGradeGPA;
    }

    public List<Term> getListOfTerm() {
        return listOfTerm;
    }

    public Map<String, Double> getListOfGradeGPA() {
        return listOfGradeGPA;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setListOfTerm(List<Term> listOfTerm) {
        this.listOfTerm = listOfTerm;
    }

    public void setListOfGradeGPA(Map<String, Double> listOfGradeGPA) {
        this.listOfGradeGPA = listOfGradeGPA;
    }

    public String getCGPA() {
        double totalGpa = 0f;
        double totalCu = 0;

        for(Term eachTerm : listOfTerm){
            for(Module eachModule : eachTerm.getListOfModules()){
                totalCu += eachModule.getCreditUnit();
                totalGpa += listOfGradeGPA.get(eachModule.getgrade()) * eachModule.getCreditUnit();
            }
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

    public String getTotalTerms() {
        return String.valueOf(listOfTerm.size());
    }
}
