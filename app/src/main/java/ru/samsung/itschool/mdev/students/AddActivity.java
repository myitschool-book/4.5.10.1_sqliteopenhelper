package ru.samsung.itschool.mdev.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import ru.samsung.itschool.mdev.students.models.Student;
import ru.samsung.itschool.mdev.students.utils.StudentDBHelper;

public class AddActivity extends AppCompatActivity {

    private EditText tvName, tvAge, tvSchool, tvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tvName = findViewById(R.id.name);
        tvAge = findViewById(R.id.age);
        tvSchool = findViewById(R.id.school);
        tvPhoto = findViewById(R.id.photo);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            addStudent();
        });
    }

    private void addStudent(){
        String name = tvName.getText().toString().trim();
        String age = tvAge.getText().toString().trim();
        String school = tvSchool.getText().toString().trim();
        String image = tvPhoto.getText().toString().trim();
        StudentDBHelper dbHelper = new StudentDBHelper(this);

        if(name.isEmpty() || age.isEmpty() || school.isEmpty() || image.isEmpty()) {
            Snackbar.make(findViewById(R.id.addRoot),"Please enter all fields!",Snackbar.LENGTH_LONG).show();
        } else {
            Student student = new Student(name, age, school, image);
            dbHelper.saveStudent(student);
            startActivity(new Intent(AddActivity.this, MainActivity.class));
        }
    }
}