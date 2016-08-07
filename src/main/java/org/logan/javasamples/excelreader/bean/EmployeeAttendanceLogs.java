package org.logan.javasamples.excelreader.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="AttendanceLogs")
public class EmployeeAttendanceLogs {
    
    @Id
    @Column(name="AttendanceLogId")
    private int attendanceLogId;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="EmployeeId")
    private EmployeeInfo employeeInfo;
    
    @Column(name="AttendanceDate")
    private Date attendanceDate;
    
    @Column(name="InTime")
    private String inTime;
    
    @Column(name="OutTime")
    private String outTime;
    
    @Column(name="Duration")
    private float duration;

    public int getAttendanceLogId() {
        return attendanceLogId;
    }

    public void setAttendanceLogId(int attendanceLogId) {
        this.attendanceLogId = attendanceLogId;
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
    
    protected String getAttributesString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" attendanceLogId=").append(attendanceLogId).append(", employeeInfo=")
                .append(employeeInfo);
        return builder.toString();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("org.logan.javasamples.excelreader.bean.EmployeeAttendanceLogs [ ")
                .append(getAttributesString()).append(" ]");
        return builder.toString();
    }
    
}
