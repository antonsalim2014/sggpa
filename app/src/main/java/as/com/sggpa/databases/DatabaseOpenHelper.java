package as.com.sggpa.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import as.com.sggpa.infrastructures.Module;
import as.com.sggpa.infrastructures.School;
import as.com.sggpa.infrastructures.Term;

/**
 * Database Helper class that extends SQLiteOpenHelper
 * TODO: extend SQLiteOpenHelper and override methods
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper{

    final static String TABLE_SCHOOL = "school";
    final static String TABLE_TERM = "term";
    final static String TABLE_MODULE = "module";
    final static String TABLE_GRADE = "grade";

    final static String SCHOOL_VALUE = "school_name",
            TERM_TITLE_VALUE = "term_title",
            MODULE_TITLE_VALUE = "module_title",
            GRADE_VALUE = "grade",
            CU_VALUE = "cu",
            GPA_VALUE = "gpa";
    final static String[] columns_school = { SCHOOL_VALUE};
    final static String[] columns_term = {TERM_TITLE_VALUE, SCHOOL_VALUE };
    final static String[] columns_module = { SCHOOL_VALUE, MODULE_TITLE_VALUE, TERM_TITLE_VALUE, GRADE_VALUE, CU_VALUE };
    final static String[] columns_grade = { GRADE_VALUE, GPA_VALUE, SCHOOL_VALUE };

    // TODO: what is the string to create a table in SQL?
    final private static String CREATE_CMD1 = String.format("create table %s (%s TEXT);",
            TABLE_SCHOOL, columns_school[0]);
    final private static String CREATE_CMD2 = String.format("create table %s (%s TEXT, %s TEXT);",
            TABLE_TERM, columns_term[0], columns_term[1]);
    final private static String CREATE_CMD3 = String.format("create table %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER);",
            TABLE_MODULE, columns_module[0], columns_module[1], columns_module[2], columns_module[3], columns_module[4]);
    final private static String CREATE_CMD4 = String.format("create table %s (%s TEXT, %s DOUBLE, %s TEXT);",
            TABLE_GRADE, columns_grade[0], columns_grade[1], columns_grade[2]);

    final private static String DBNAME = "sggpa_db";
    final private static Integer VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        // logic to create database here
        super(context, DBNAME, null, VERSION);
    }

    public boolean insertSchool(String schoolName){
        SQLiteDatabase dbread = this.getReadableDatabase();
        String query = String.format("select * from %s where %s = '%s';",
                TABLE_SCHOOL, SCHOOL_VALUE, schoolName);
        Cursor cursor = dbread.rawQuery(query, null);
        if(cursor.getCount() > 0) return false;

        SQLiteDatabase dbwrite = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOL_VALUE, schoolName);
        dbwrite.insert(TABLE_SCHOOL, null, contentValues);
        return true;
    }

    public void deleteSchool(String schoolName){
        SQLiteDatabase dbwrite = this.getWritableDatabase();
        dbwrite.delete(TABLE_MODULE, SCHOOL_VALUE + " = '" + schoolName + "'", null);
        dbwrite.delete(TABLE_TERM, SCHOOL_VALUE + " = '" + schoolName + "'", null);
        dbwrite.delete(TABLE_SCHOOL, SCHOOL_VALUE + " = '" + schoolName + "'", null);
    }

    public boolean insertTerm(String termTitle, String schoolName){
        SQLiteDatabase dbread = this.getReadableDatabase();
        String query = String.format("select * from %s where %s = '%s' and %s = '%s';",
                TABLE_TERM, SCHOOL_VALUE, schoolName, TERM_TITLE_VALUE, termTitle);
        Cursor cursor = dbread.rawQuery(query, null);
        if(cursor.getCount() > 0) return false;

        SQLiteDatabase dbwrite = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TERM_TITLE_VALUE, termTitle);
        contentValues.put(SCHOOL_VALUE, schoolName);
        dbwrite.insert(TABLE_TERM, null, contentValues);
        return true;
    }

    public void deleteTerm(String schoolName, String termTitle){
        SQLiteDatabase dbwrite = this.getWritableDatabase();
        dbwrite.delete(TABLE_MODULE, SCHOOL_VALUE + " = '" + schoolName + "' and " + TERM_TITLE_VALUE + "='" + termTitle + "'", null);
        dbwrite.delete(TABLE_TERM, SCHOOL_VALUE + " = '" + schoolName + "' and " + TERM_TITLE_VALUE + "='" + termTitle + "'", null);
    }

    public boolean insertModule(String schoolName, String moduleTitle, String termTitle, String grade, int cu){
        SQLiteDatabase dbread = this.getReadableDatabase();
        String query = String.format("select * from %s where %s = '%s' and %s = '%s' and %s = '%s';",
                TABLE_MODULE, TERM_TITLE_VALUE, termTitle, MODULE_TITLE_VALUE, moduleTitle, SCHOOL_VALUE, schoolName);
        Cursor cursor = dbread.rawQuery(query, null);
        if(cursor.getCount() > 0) return false;

        SQLiteDatabase dbwrite = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOL_VALUE, schoolName);
        contentValues.put(MODULE_TITLE_VALUE, moduleTitle);
        contentValues.put(TERM_TITLE_VALUE, termTitle);
        contentValues.put(GRADE_VALUE, grade);
        contentValues.put(CU_VALUE, cu);
        dbwrite.insert(TABLE_MODULE, null, contentValues);
        return true;
    }

    public void deleteModule(String moduleTitle, String schoolName, String termTitle){
        SQLiteDatabase dbwrite = this.getWritableDatabase();
        dbwrite.delete(TABLE_MODULE, SCHOOL_VALUE + " = '" + schoolName + "' and " + TERM_TITLE_VALUE + "='" + termTitle + "' and " +
                MODULE_TITLE_VALUE + " ='" + moduleTitle + "'", null);
    }

    public boolean insertGrade(String grade, double gpa, String schoolName){
        SQLiteDatabase dbread = this.getReadableDatabase();
        String query = String.format("select * from %s where %s = '%s' and %s = '%s';",
                TABLE_GRADE, SCHOOL_VALUE, schoolName, GRADE_VALUE, grade);
        Cursor cursor = dbread.rawQuery(query, null);
        if(cursor.getCount() > 0) return false;

        SQLiteDatabase dbwrite = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GRADE_VALUE, grade);
        contentValues.put(GPA_VALUE, gpa);
        contentValues.put(SCHOOL_VALUE, schoolName);
        dbwrite.insert(TABLE_GRADE, null, contentValues);
        return true;
    }

    public boolean deleteGrade(String grade, String schoolName){
        SQLiteDatabase dbread = this.getReadableDatabase();
        String query = String.format("select * from %s where %s = '%s' and %s = '%s';",
                TABLE_MODULE, SCHOOL_VALUE, schoolName, GRADE_VALUE, grade);
        Cursor cursor = dbread.rawQuery(query, null);
        if(cursor.getCount() > 0) return false;

        SQLiteDatabase dbwrite = this.getWritableDatabase();
        dbwrite.delete(TABLE_GRADE, GRADE_VALUE + " = '" + grade + "' and " + SCHOOL_VALUE + "='" + schoolName + "'", null);
        return true;
    }

    public ArrayList<School> getAllData(){
        ArrayList<School> schoolList = new ArrayList<School>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res_school = db.rawQuery("select * from " + TABLE_SCHOOL, null);
        if(res_school.moveToFirst()) {
            while (!res_school.isAfterLast()) {
                String schoolName = res_school.getString(res_school.getColumnIndex(columns_school[0]));

                Map<String, Double> listOfGpaGrades = new LinkedHashMap<>();
                List<String> list = new ArrayList<>();
                Cursor res_grade = db.rawQuery("select * from " + TABLE_GRADE + " where " + columns_grade[2] + " = '" + schoolName + "'" +
                        " ORDER BY " + columns_grade[1] + " DESC;", null);
                if(res_grade.moveToFirst()) {
                    while (!res_grade.isAfterLast()) {
                        listOfGpaGrades.put(res_grade.getString(res_grade.getColumnIndex(columns_grade[0])),
                                Double.valueOf(res_grade.getString(res_grade.getColumnIndex(columns_grade[1]))));
                        list.add(res_grade.getString(res_grade.getColumnIndex(columns_grade[0])) + ": " +
                                Double.valueOf(res_grade.getString(res_grade.getColumnIndex(columns_grade[1]))));
                        res_grade.moveToNext();
                    }
                }

                ArrayList<Term> termList = new ArrayList<Term>();
                Cursor res_term = db.rawQuery("select * from " + TABLE_TERM + " where " + columns_term[1] + " = '" + schoolName + "';", null);
                if(res_term.moveToFirst()) {
                    while (!res_term.isAfterLast()) {
                        String termTitle = res_term.getString(res_term.getColumnIndex(columns_term[0]));
                        ArrayList<Module> moduleList = new ArrayList<Module>();

                        Cursor res_module = db.rawQuery("select * from " + TABLE_MODULE + " where " + columns_module[0]
                                + " = '" + schoolName + "' and " + columns_module[2] + "='" + termTitle + "';", null);
                        if(res_module.moveToFirst()) {
                            while (!res_module.isAfterLast()) {
                                String moduleTitle = res_module.getString(res_module.getColumnIndex(columns_module[1]));
                                String grade = res_module.getString(res_module.getColumnIndex(columns_module[3]));
                                int cu = Integer.valueOf(res_module.getString(res_module.getColumnIndex(columns_module[4])));

                                moduleList.add(new Module(moduleTitle, grade, cu));
                                res_module.moveToNext();
                            }
                        }
                        termList.add(new Term(termTitle, moduleList, listOfGpaGrades));
                        res_term.moveToNext();
                    }
                }

                schoolList.add(new School(schoolName, termList, listOfGpaGrades));
                res_school.moveToNext();
            }
        }

        return schoolList;
    }

    // TODO: implement onCreate, onUpgrade, maybe deleteDatabase()
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DbHelper", "sql: " + CREATE_CMD1);
        db.execSQL(CREATE_CMD1);
        Log.d("DbHelper", "sql: " + CREATE_CMD2);
        db.execSQL(CREATE_CMD2);
        Log.d("DbHelper", "sql: " + CREATE_CMD3);
        db.execSQL(CREATE_CMD3);
        Log.d("DbHelper", "sql: " + CREATE_CMD4);
        db.execSQL(CREATE_CMD4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOL);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE);
        onCreate(db);
    }

    class ValueComparator implements Comparator<String> {
        Map<String, Double> base;

        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
