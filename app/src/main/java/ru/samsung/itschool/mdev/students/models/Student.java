package ru.samsung.itschool.mdev.students.models;

public class Student {

    private long id;
    private String name;
    private String age;
    private String school;
    private String photo;

    public Student() {
    }

    public Student(String name, String age, String school, String photo) {
        this.name = name;
        this.age = age;
        this.school = school;
        this.photo = photo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}