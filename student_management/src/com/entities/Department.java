package com.entities;

import java.util.List;

public class Department {
    private int id;
    private String dname;
    private List<Professor> professors;

    public Department(){}

    public Department(int id, String dname) {
        this.id = id;
        this.dname = dname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", dname='" + dname + '\'' +
                ", professors=" + professors +
                '}';
    }
}
