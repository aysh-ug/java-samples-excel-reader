package org.logan.javasamples.excelreader.bean;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="Employee")
public class EmployeeInfo {
    
    @Id
    @Column(name="EmployeeId")
    private int employeeId;
    
    @Column(name="EmployeeName")
    private String employeeName;
    
    @Column(name="EmployeeCode")
    private String employeeCode;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @OneToOne
    @JoinColumn(name="ReportToEmployeeId")
    private EmployeeInfo reportToEmployeeInfo;
    
    @Column(name="Email")
    private String email;
    
    @OneToMany(mappedBy="employeeInfo")
    private Collection<EmployeeAttendanceLogs> employeeAttendanceLogsSet = new HashSet<>();

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public EmployeeInfo getReportToEmployeeInfo() {
        return reportToEmployeeInfo;
    }

    public void setReportToEmployeeInfo(EmployeeInfo reportToEmployeeInfo) {
        this.reportToEmployeeInfo = reportToEmployeeInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<EmployeeAttendanceLogs> getEmployeeAttendanceLogsSet() {
        return employeeAttendanceLogsSet;
    }

    public void setEmployeeAttendanceLogsSet(
                    Collection<EmployeeAttendanceLogs> employeeAttendanceLogsSet) {
        this.employeeAttendanceLogsSet = employeeAttendanceLogsSet;
    }
    
    protected String getAttributesString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" employeeId=").append(employeeId).append(", employeeName=")
                .append(employeeName);
        if(reportToEmployeeInfo!=null)
        builder.append(", reportToEmployeeInfo=")
                .append(reportToEmployeeInfo.employeeId);
        return builder.toString();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("org.logan.javasamples.excelreader.bean.EmployeeInfo [ ")
                .append(getAttributesString()).append(" ]");
        return builder.toString();
    }
    
    
    
}
