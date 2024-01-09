package com.entities;

import java.util.List;

public class Professor {
    private int id;
    private String fname;
    private String lname;
    private String login;
    private String passwrd;
    private Department department;
    private List<Cls> classes;

    public Professor(){}

    public Professor(String fname, String lname, String login, String passwrd, Department department) {
        this.fname = fname;
        this.lname = lname;
        this.login = login;
        this.passwrd = passwrd;
        this.department = department;
    }

    public Professor(int id, String fname, String lname, String login, String passwrd, Department department) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.login = login;
        this.passwrd = passwrd;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Cls> getClasses() {
        return classes;
    }

    public void setClasses(List<Cls> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", login='" + login + '\'' +
                ", passwrd='" + passwrd + '\'' +
                ", department=" + department +
                ", classes=" + classes +
                '}';
    }
}
