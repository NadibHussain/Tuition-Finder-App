package com.example.admin.final2;

import java.util.ArrayList;

public class User {
    public String name;
    public String id;
    public String accountType;
    public String institution;
    public String grade;

    public String getPublicprivate() {
        return publicprivate;
    }

    public void setPublicprivate(String publicprivate) {
        this.publicprivate = publicprivate;
    }

    public String publicprivate;

    public int salary;

    public int getSalary() {
        return this.salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;
    ArrayList<String> area=new ArrayList<>();

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ArrayList<String> getArea() {
        return area;
    }

    public void setArea(String s,int num) {
        area.add(num,s);
    }

    public User(String name, String id, String accountType, String institution, String grade) {
        this.name = name;
        this.id = id;
        this.accountType = accountType;
        this.institution = institution;
        this.grade = grade;
    }
}
