package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {
    FileInputStream fileInput;
    FileOutputStream fileOutput;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;
    CellStyle style;
    String filePath;

    public ExcelUtility(String filePath){
        this.filePath = filePath;
    }

    public int getRowCount(String sheetName) throws IOException {
        fileInput = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInput);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        fileInput.close();
        return rowCount;
    }

    public int getCellCount(String sheetName, int rowNum) throws IOException {
        fileInput = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInput);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        int cellCount = row.getLastCellNum();
        workbook.close();
        fileInput.close();
        return cellCount;
    }

    public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
        fileInput = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInput);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);
        String cellValue ;
        try {
            DataFormatter dataFormatter = new DataFormatter();
            cellValue = dataFormatter.formatCellValue(cell);
        }catch (Exception e){
            cellValue = "";
        }
        workbook.close();
        fileInput.close();
        return cellValue;
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){     //If file not exists create a new file
            workbook = new XSSFWorkbook();
            fileOutput = new FileOutputStream(filePath);
            workbook.write(fileOutput);
        }

        fileInput = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInput);
        if(workbook.getSheetIndex(sheetName) == -1){    //If sheet not exists then create a new sheet
            workbook.createSheet(sheetName);
        }
        sheet = workbook.getSheet(sheetName);

        if(sheet.getRow(rowNum)==null){     //If row not exists then create a new row
            sheet.createRow(rowNum);
        }
        row = sheet.getRow(rowNum);

        cell = row.createCell(colNum);
        cell.setCellValue(data);

        fileOutput = new FileOutputStream(filePath);
        workbook.write(fileOutput);
        workbook.close();
        fileOutput.close();
        fileInput.close();


    }

    public void fillColour(String sheetName, int rowNum, int colNum, IndexedColors color) throws IOException {
        fileInput = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fileInput);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        style = workbook.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);

        fileOutput = new FileOutputStream(filePath);
        workbook.write(fileOutput);
        workbook.close();
        fileOutput.close();
        fileInput.close();

    }

}
