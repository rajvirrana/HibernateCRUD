package com.hibernate;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "emp")
public class Emp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empid")
    private Integer empid;

    @Size(max = 45)
    @Column(name = "empname")
    private String empname; 

    @Column(name = "empsalary")
    private Integer empsalary;
 
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="employee_id")
    private List<Certificate> certificate;

    public Emp() {}

    public Emp(Integer empid) {
        this.empid = empid;
    }

    public Integer getEmpid() {
        return empid;
    }

    public void setEmpid(Integer empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Integer getEmpsalary() {
        return empsalary;
    }

    public void setEmpsalary(Integer empsalary) {
        this.empsalary = empsalary;
    }

    public List<Certificate> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<Certificate> certificate) {
        this.certificate = certificate;
    }
}
