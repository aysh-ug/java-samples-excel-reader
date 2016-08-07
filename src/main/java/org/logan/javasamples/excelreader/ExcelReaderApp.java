package org.logan.javasamples.excelreader;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.logan.javasamples.excelreader.bean.Employee1;
import org.logan.javasamples.excelreader.bean.EmployeeAttendanceLogs;
import org.logan.javasamples.excelreader.service.ReadXls;
import org.logan.javasamples.excelreader.service.WriteXls;
import org.logan.javasamples.excelreader.util.PersistenceManager;

public class ExcelReaderApp {
    public static void main(String args[]) {
        ExcelReaderApp readerApp = new ExcelReaderApp();
        List<Employee1> employees = null;
        Collection<EmployeeAttendanceLogs> employeeAttendanceLogs = null;
        EntityManager em = null;

        ReadXls readXls = new ReadXls();
        //employees = readXls.read();
        //readerApp.saveEmployees(employees);
        
        //employeeAttendanceLogs = readXls.readEmployeeXls();
        
        employeeAttendanceLogs = readerApp.getEmployeeAttendanceLogs();
        
        /*for (EmployeeAttendanceLogs employeeAttendanceLog : employeeAttendanceLogs) {
            System.out.println(employeeAttendanceLog);
        }*/
        
        
        
        //readerApp.saveEmployeeAttendanceLogs(employeeAttendanceLogs);
        
        WriteXls writeXls = new WriteXls();
        //writeXls.writeXls();
        
        try {
            //writeXls.generateXlsFullEmployee(employeeAttendanceLogs);
            writeXls.generateXlsByReportToEmployee(employeeAttendanceLogs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        PersistenceManager.INSTANCE.close();
    }

    public void saveEmployees(List<Employee1> employees) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        
        for (Employee1 employee : employees) {
            System.out.println(employee);
            em.persist(employee);
        }

        em.getTransaction().commit();
        em.close();
    }
    
    public void saveEmployeeAttendanceLogs(Collection<EmployeeAttendanceLogs> employeeAttendanceLogs) {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        em.getTransaction().begin();
        
        for (EmployeeAttendanceLogs employeeAttendanceLog : employeeAttendanceLogs) {
            System.out.println(employeeAttendanceLog);
            em.merge(employeeAttendanceLog);
        }

        em.getTransaction().commit();
        em.close();
    }
    
    public Collection<EmployeeAttendanceLogs> getEmployeeAttendanceLogs() {
        EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
        Collection<EmployeeAttendanceLogs> employeeAttendanceLogs = null;
        em.getTransaction().begin();
        
        Query query = em.createQuery("from EmployeeAttendanceLogs order by employeeInfo.reportToEmployeeInfo,employeeInfo");
        employeeAttendanceLogs = query.getResultList();

        em.getTransaction().commit();
        em.close();
        
        return employeeAttendanceLogs;
    }

}
