package org.logan.javasamples.excelreader.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.logan.javasamples.excelreader.bean.Employee1;
import org.logan.javasamples.excelreader.bean.EmployeeAttendanceLogs;
import org.logan.javasamples.excelreader.bean.EmployeeInfo;

public class ReadXls {

    public List read() {

        List employees = new ArrayList();
        try {
            FileInputStream file = new FileInputStream(new File("emp_details.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // Skip header
                if (row.getRowNum() == 0)
                    continue;

                Iterator<Cell> cells = row.cellIterator();
                Employee1 e = new Employee1();
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    if (0 == cell.getColumnIndex())
                        e.setEmployeeId((int) cell.getNumericCellValue());
                    else if (1 == cell.getColumnIndex())
                        e.setFirstName(cell.getStringCellValue());
                    else if (2 == cell.getColumnIndex())
                        e.setLastName(cell.getStringCellValue());
                }
                employees.add(e);
            }
            file.close();
            System.out.println("Reading Xls File");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;

    }
    
    public Collection<EmployeeAttendanceLogs> readEmployeeXls() {

        Collection<EmployeeAttendanceLogs> employeeAttendanceLogs = new HashSet<>();
        Map<Integer, EmployeeInfo> employeeInfoMap = new HashMap<>();
        Map<Integer, Integer> reportToEmployeeInfoMap = new HashMap<>();
        try {
            FileInputStream file = new FileInputStream(new File("emp_attendance_logs.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // Skip header
                if (row.getRowNum() == 0)
                    continue;

                Iterator<Cell> cells = row.cellIterator();
                EmployeeAttendanceLogs attendanceLog = new EmployeeAttendanceLogs();
                EmployeeInfo employeeInfo = null;
                boolean skip = false;
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    if (0 == cell.getColumnIndex())
                        attendanceLog.setAttendanceLogId((int) cell.getNumericCellValue());
                    else if (1 == cell.getColumnIndex()) {
                        Integer employeeId = (int) cell.getNumericCellValue();
                        if(!skip && (employeeInfo = employeeInfoMap.get(employeeId)) != null) {
                            skip = true;
                        } else {
                            employeeInfo = new EmployeeInfo();
                            employeeInfo.setEmployeeId(employeeId);
                            skip = false;
                        }
                        
                    }
                    else if (2 == cell.getColumnIndex())
                        attendanceLog.setAttendanceDate(new Date());
                    else if (!skip && 3 == cell.getColumnIndex())
                        employeeInfo.setEmployeeCode(cell.getStringCellValue());
                    else if (!skip && 4 == cell.getColumnIndex()) {
                        employeeInfo.setEmployeeName(cell.getStringCellValue());
                    }
                    else if (5 == cell.getColumnIndex())
                        attendanceLog.setInTime(cell.getStringCellValue());
                    else if (6 == cell.getColumnIndex())
                        attendanceLog.setOutTime(cell.getStringCellValue());
                    else if (7 == cell.getColumnIndex())
                        attendanceLog.setDuration((float) cell.getNumericCellValue());
                    else if (!skip && 8 == cell.getColumnIndex()) {
                        employeeInfo.setEmail(cell.getStringCellValue());
                        employeeInfoMap.put(employeeInfo.getEmployeeId(), employeeInfo);
                    } else if (9 == cell.getColumnIndex()) {
                        Integer reportToEmployeeId = (int) cell.getNumericCellValue();
                        if(!reportToEmployeeInfoMap.containsKey(employeeInfo.getEmployeeId())) {
                            reportToEmployeeInfoMap.put(employeeInfo.getEmployeeId(), reportToEmployeeId);
                        }
                    }
                        attendanceLog.setEmployeeInfo(employeeInfo);
                }
                employeeAttendanceLogs.add(attendanceLog);
            }
            
            for(Entry<Integer, Integer> entry:reportToEmployeeInfoMap.entrySet()) {
                if(entry.getValue()>0)
                employeeInfoMap.get(entry.getKey()).setReportToEmployeeInfo(employeeInfoMap.get(entry.getValue()));
                
            }
            System.out.println("reportToEmployeeInfoMap: "+reportToEmployeeInfoMap);
            file.close();
            System.out.println("Reading Employee Xls File");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeAttendanceLogs;

    }
}
