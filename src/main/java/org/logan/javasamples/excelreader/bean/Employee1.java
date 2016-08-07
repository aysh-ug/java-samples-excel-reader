package org.logan.javasamples.excelreader.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_details")
public class Employee1 {
    
    @Id
    private int employeeId;
    private String firstName;
    private String lastName;

    public Employee1() {

    }

    public Employee1(int employeeId, String firstName, String lastName, int age, double salary) {
        super();
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Employee Id:- ").append(this.getEmployeeId());
        str.append(" First Name:- ").append(this.getFirstName());
        str.append(" Last Name:- ").append(this.getLastName());

        return str.toString();
    }

}
