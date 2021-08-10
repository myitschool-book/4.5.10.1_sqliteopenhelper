package ru.samsung.itschool.mdev.students.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.LinkedList;
import java.util.List;
import ru.samsung.itschool.mdev.students.models.Student;

public class StudentDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Student";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PERSON_NAME = "name";
    public static final String COLUMN_PERSON_AGE = "age";
    public static final String COLUMN_PERSON_SCHOOL = "school";
    public static final String COLUMN_PERSON_PHOTO = "photo";

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PERSON_NAME + " TEXT NOT NULL, " +
                COLUMN_PERSON_AGE + " NUMBER NOT NULL, " +
                COLUMN_PERSON_SCHOOL + " TEXT NOT NULL, " +
                COLUMN_PERSON_PHOTO + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_NAME, student.getName());
        values.put(COLUMN_PERSON_AGE, student.getAge());
        values.put(COLUMN_PERSON_SCHOOL, student.getSchool());
        values.put(COLUMN_PERSON_PHOTO, student.getPhoto());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Student> studentList(String filter) {
        String query;
        if (filter.equals(""))
            query = "SELECT * FROM " + TABLE_NAME;
        else
            query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + filter;

        List<Student> studentLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Student student;

        if (cursor.moveToFirst()) {
            do {
                student = new Student();
                student.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_NAME)));
                student.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_AGE)));
                student.setSchool(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SCHOOL)));
                student.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_PHOTO)));
                studentLinkedList.add(student);
            } while (cursor.moveToNext());
        }
        return studentLinkedList;
    }

    public Student getStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        Student receivedStudent = new Student();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedStudent.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_NAME)));
            receivedStudent.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_AGE)));
            receivedStudent.setSchool(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SCHOOL)));
            receivedStudent.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_PHOTO)));
        }
        return receivedStudent;
    }

    public void deletePerson(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + id + "'");
    }

    public void updatePerson(long personId, Student updatedperson) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  " + TABLE_NAME + " SET name ='" + updatedperson.getName() + "', age ='" + updatedperson.getAge() + "', occupation ='" + updatedperson.getSchool() + "', image ='" + updatedperson.getPhoto() + "'  WHERE _id='" + personId + "'");
    }
}
