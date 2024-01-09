package com.entities;

import java.util.List;

public class Cls {
    private int id;
    private String cname;
    private Professor professor;
    private List<Result> results;

    public Cls(){}
    public Cls(int id, String cname, Professor professor) {
        this.id = id;
        this.cname = cname;
        this.professor = professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Cls{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                ", professor=" + professor +
                ", results=" + results +
                '}';
    }
}
