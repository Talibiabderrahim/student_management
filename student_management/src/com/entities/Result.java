package com.entities;

public class Result {
    private Student student;
    private Cls cls;
    private float grad;

    public Result(){}
    public Result(Student student, Cls cls, float grad) {
        this.student = student;
        this.cls = cls;
        this.grad = grad;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Cls getCls() {
        return cls;
    }

    public void setCls(Cls cls) {
        this.cls = cls;
    }

    public float getGrad() {
        return grad;
    }

    public void setGrad(float grad) {
        this.grad = grad;
    }

    @Override
    public String toString() {
        return "Result{" +
                "student=" + student +
                ", cls=" + cls +
                ", grad=" + grad +
                '}';
    }
}
