package tw.lab5.zad1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    private final List<Result> results;
    private Sheet sheet;

    public Excel(List<Result> results) {
        this.results = results;
    }
    public void toExcel() throws IOException {

        FileInputStream input = new FileInputStream("src/main/java/tw/lab5/zad1/temp.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        sheet = workbook.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);

        ArrayList<String> headers = new ArrayList<>();
        headers.add("ITERATIONS");
        headers.add("THREADS");
        headers.add("TASKS");
        headers.add("TIME");
        headers.add("STD");

        createHeader(headers);
        createTable();

        FileOutputStream output = new FileOutputStream("src/main/java/tw/lab5/zad1/results.xlsx");
        workbook.write(output);
        output.close();
}
    private void createHeader(List<String> headers) {
        int position = 0;
        Row row = sheet.createRow(0);
        CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        for (String name: headers) {
            Cell cell = row.createCell(position++);
            cell.setCellValue(name);
            cell.setCellStyle(cellStyle);
        }
    }
    private void createTable() {
        int rowNum = 1;
        for (Result result : results) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(result.getMaxIter());
            row.createCell(1).setCellValue(result.getThreadNo());
            row.createCell(2).setCellValue(result.getTaskNo());
            row.createCell(3).setCellValue(result.getAvgTime());
            row.createCell(4).setCellValue(result.getStDev());
        }
    }
}
