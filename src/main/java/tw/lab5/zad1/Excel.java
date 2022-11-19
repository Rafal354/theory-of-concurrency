package tw.lab5.zad1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
    private final List<Result> results;
    private Sheet sheet;

    public Excel(List<Result> results) {
        this.results = results;
    }
    public void toExcel() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook("src/main/java/tw/lab5/zad1/temp.xlsx");
        sheet = workbook.getSheetAt(0);

        ArrayList<String> headers = new ArrayList<>();
        headers.add("ITERATIONS");
        headers.add("THREADS");
        headers.add("TASKS");
        headers.add("TIME");
        headers.add("STD");

        createHeader(headers);
        createTable();

        FileOutputStream out = new FileOutputStream("src/main/java/tw/lab5/zad1/results.xlsx");
        workbook.write(out);
        out.close();
}
    private void createHeader(List<String> headers) {
        int position = 0;
        Row row = sheet.createRow(0);
        for (String name: headers) {
            row.createCell(position++).setCellValue(name);
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
