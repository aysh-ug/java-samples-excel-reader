package org.logan.javasamples.excelreader.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.logan.javasamples.excelreader.bean.Book;
import org.logan.javasamples.excelreader.bean.EmployeeAttendanceLogs;
import org.logan.javasamples.excelreader.bean.EmployeeInfo;

public class WriteXls {

    private static final File ROOT = new File("/Users/Loganathan/Documents/multixls");

    public void writeXls() {
        try {
            FileOutputStream fileOut = new FileOutputStream("poi-test.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

            // index from 0,0... cell A1 is cell(0,0)
            HSSFRow row1 = worksheet.createRow((short) 0);

            HSSFCell cellA1 = row1.createCell(0);
            cellA1.setCellValue("Hello");
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellA1.setCellStyle(cellStyle);

            HSSFCell cellB1 = row1.createCell(1);
            cellB1.setCellValue("Goodbye");
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellB1.setCellStyle(cellStyle);

            HSSFCell cellC1 = row1.createCell(2);
            cellC1.setCellValue(true);

            HSSFCell cellD1 = row1.createCell(3);
            cellD1.setCellValue(new Date());
            cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            cellD1.setCellStyle(cellStyle);

            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeXls1() {
        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        // This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
        data.put("2", new Object[] { 1, "Amit", "Shukla" });
        data.put("3", new Object[] { 2, "Lokesh", "Gupta" });
        data.put("4", new Object[] { 3, "John", "Adwards" });
        data.put("5", new Object[] { 4, "Brian", "Schultz" });

        // Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            // Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeExcel(Collection<EmployeeAttendanceLogs> employeeAttendanceLogs,
                    String excelFilePath) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        Row headerRow = sheet.createRow(rowCount);
        writeHeader(headerRow);
        for (EmployeeAttendanceLogs employeeAttendanceLog : employeeAttendanceLogs) {
            Row row = sheet.createRow(++rowCount);
            writeEmployeeDetails(employeeAttendanceLog, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        }
    }

    private void writeHeader(Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue("AttendanceLogId");

        cell = row.createCell(1);
        cell.setCellValue("EmployeeId");

        cell = row.createCell(2);
        cell.setCellValue("AttendanceDate");

        cell = row.createCell(3);
        cell.setCellValue("EmployeeCode");

        cell = row.createCell(4);
        cell.setCellValue("EmployeeName");

        cell = row.createCell(5);
        cell.setCellValue("InTime");

        cell = row.createCell(6);
        cell.setCellValue("OutTime");

        cell = row.createCell(7);
        cell.setCellValue("Duration");

        cell = row.createCell(8);
        cell.setCellValue("Email");

        cell = row.createCell(9);
        cell.setCellValue("ReportToEmployeeId");
    }

    private void writeEmployeeDetails(EmployeeAttendanceLogs employeeAttendanceLog, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(employeeAttendanceLog.getAttendanceLogId());

        cell = row.createCell(1);
        cell.setCellValue(employeeAttendanceLog.getEmployeeInfo().getEmployeeId());

        cell = row.createCell(2);
        cell.setCellValue(employeeAttendanceLog.getAttendanceDate());

        cell = row.createCell(3);
        cell.setCellValue(employeeAttendanceLog.getEmployeeInfo().getEmployeeCode());

        cell = row.createCell(4);
        cell.setCellValue(employeeAttendanceLog.getEmployeeInfo().getEmployeeName());

        cell = row.createCell(5);
        cell.setCellValue(employeeAttendanceLog.getInTime());

        cell = row.createCell(6);
        cell.setCellValue(employeeAttendanceLog.getOutTime());

        cell = row.createCell(7);
        cell.setCellValue(employeeAttendanceLog.getDuration());

        cell = row.createCell(8);
        cell.setCellValue(employeeAttendanceLog.getEmployeeInfo().getEmail());

        cell = row.createCell(9);
        if (employeeAttendanceLog.getEmployeeInfo().getReportToEmployeeInfo() != null)
            cell.setCellValue(employeeAttendanceLog.getEmployeeInfo().getReportToEmployeeInfo().getEmployeeId());
    }

    public void generateXlsFullEmployee(Collection<EmployeeAttendanceLogs> employeeAttendanceLogs)
                    throws IOException {
        String excelFilePath = "EmployeeFullAttendLogs.xls";
        writeExcel(employeeAttendanceLogs, excelFilePath);
    }

    public void generateXlsByReportToEmployee(
                    Collection<EmployeeAttendanceLogs> employeeAttendanceLogs) {

        EmployeeInfo reportToEmployee = null;
        File file = null;
        Workbook workbook = null;
        Sheet sheet = null;
        int reportToEmployeeId = -1;
        int rowCount = 0;
        for (EmployeeAttendanceLogs employeeAttendanceLog : employeeAttendanceLogs) {

            int rowReportToEmployeeId = 0;

            reportToEmployee = employeeAttendanceLog.getEmployeeInfo().getReportToEmployeeInfo();
            if (reportToEmployee != null) {
                rowReportToEmployeeId = reportToEmployee.getEmployeeId();
            }
            System.out.println("reportToEmployeeId:rowReportToEmployeeId:::::::::"+reportToEmployeeId+"==="+rowReportToEmployeeId);
            if (reportToEmployeeId != rowReportToEmployeeId) {
                reportToEmployeeId = rowReportToEmployeeId;
                if (workbook != null) {
                    writeFileForReportToEmployee(workbook, file);
                }
                file = createFileForReportToEmployee(reportToEmployeeId);

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                workbook = new HSSFWorkbook();
                sheet = workbook.createSheet();
                rowCount = 0;
                Row headerRow = sheet.createRow(0);
                writeHeader(headerRow);
            }

            Row row = sheet.createRow(++rowCount);
            writeEmployeeDetails(employeeAttendanceLog, row);

        }

        if (workbook != null) {
            writeFileForReportToEmployee(workbook, file);
        }

    }

    // Write a File for ReportToEmployeeId
    private void writeFileForReportToEmployee(Workbook workbook, File file) {

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Create a File for ReportToEmployeeId
    private File createFileForReportToEmployee(int reportToEmployeeId) {
        File file = new File(ROOT, String.valueOf(reportToEmployeeId) + "_" + System.nanoTime()
                        + ".xls");
        return file;
    }
}
