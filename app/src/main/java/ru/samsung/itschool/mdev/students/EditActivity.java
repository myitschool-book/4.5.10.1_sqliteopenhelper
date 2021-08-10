package ru.samsung.itschool.mdev.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;
import ru.samsung.itschool.mdev.students.models.Student;
import ru.samsung.itschool.mdev.students.utils.StudentDBHelper;


public class EditActivity extends AppCompatActivity {

    private EditText tvName, tvAge, tvSchool, tvPhoto;
    private Button editButton;
    private StudentDBHelper dbHelper;
    private long getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        tvName = findViewById(R.id.userNameUpdate);
        tvAge = findViewById(R.id.userAgeUpdate);
        tvSchool = findViewById(R.id.userOccupationUpdate);
        tvPhoto = findViewById(R.id.userProfileImageLinkUpdate);
        editButton = findViewById(R.id.updateUserButton);
        dbHelper = new StudentDBHelper(this);
        try {
            getId = getIntent().getLongExtra("USERID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Student queriedStudent = dbHelper.getStudent(getId);
        tvName.setText(queriedStudent.getName());
        tvAge.setText(queriedStudent.getAge());
        tvSchool.setText(queriedStudent.getSchool());
        tvPhoto.setText(queriedStudent.getPhoto());
        editButton.setOnClickListener(view -> {
            editStudent();
        });
    }

    private void editStudent(){
        String name = tvName.getText().toString().trim();
        String age = tvAge.getText().toString().trim();
        String school = tvSchool.getText().toString().trim();
        String image = tvPhoto.getText().toString().trim();

        if(name.isEmpty() || age.isEmpty() || school.isEmpty() || image.isEmpty()) {
            Snackbar.make(findViewById(R.id.addRoot),"Please enter all fields!",Snackbar.LENGTH_LONG).show();
        } else {
            Student updatedStudent = new Student(name, age, school, image);
            dbHelper.updatePerson(getId, updatedStudent);
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}