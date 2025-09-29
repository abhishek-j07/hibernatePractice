package com.orm;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private int employeeId;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @ManyToMany
    @JoinTable(name = "EMP_PRJ_JOIN_TABLE",
            joinColumns = {@JoinColumn(name = "EMP_ID")} ,
            inverseJoinColumns = {@JoinColumn(name = "PRJ_ID")})
    private List<Project> projects;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
