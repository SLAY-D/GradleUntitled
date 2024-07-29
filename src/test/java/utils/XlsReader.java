package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class XlsReader {
    private final File xlsxFile;
    private XSSFSheet sheet; // Страница
    private XSSFWorkbook book; // Книга
    private String sheetName;

    public XlsReader(File xlsxFile) throws IOException {
        this.xlsxFile = xlsxFile;
        try{
            FileInputStream fs = new FileInputStream(xlsxFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheetAt(0);
        } catch (IOException e){
            throw new IOException("Wrong file");
        }
    }

    public XlsReader(File xlsxFile, String sheetName) throws IOException {
        this.xlsxFile = xlsxFile;
        try{
            FileInputStream fs = new FileInputStream(xlsxFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheet(sheetName);
        } catch (IOException e){
            throw new IOException("Wrong file");
        }
    }

    private String cellToString(XSSFCell cell) throws Exception {
        Object result = null;
        CellType type = cell.getCellType();
        switch(type){
            case NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case STRING:
                result = cell.getStringCellValue();
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            case BLANK:
                result = "";
                break;
            default:
                throw new Exception("Ошибка чтения ячейки");
        };
        return result.toString();
    }

    private int xlsxCountColumn(){ // Получение количества столбцов
        return sheet.getRow(0).getLastCellNum();
    }

    private int xlsxCountRow(){ // Получение количества строк
        return sheet.getLastRowNum() + 1;
    }

    private String[][] deleteNulls(String[][] oldData){
        return Arrays.stream(oldData)
                .filter(row->Arrays.stream(row).anyMatch(x->x!=null))
                .filter(row->Arrays.stream(row).anyMatch(x->x!=""))
                .toArray(String[][]::new);
    }

    public boolean isSheetContainsStringStream(String expected) throws Exception {
        return Arrays.stream(getSheetData())
                .flatMap(Arrays::stream)
                .anyMatch(x->x!=null && x.contains(expected));
    }

    public String[][] getSheetData() throws Exception{
        int numberOfColumn = xlsxCountColumn();
        int numberOfRow = xlsxCountRow();
        String[][] data = new String[numberOfRow-1][numberOfColumn];
        for (int i = 1; i<numberOfRow;i++){
            for (int j = 0; j<numberOfColumn; j++){
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                if (cell == null){
                    break;
                }
                String value = cellToString(cell);
                data[i-1][j] = value;
            }
        }
        data = deleteNulls(data);
        return data;
    }

    public String[][] getSheetData(String sheetName) throws Exception{
        int numberOfColumn = xlsxCountColumn();
        int numberOfRow = xlsxCountRow();
        String[][] data = new String[numberOfRow-1][numberOfColumn];
        for (int i = 1; i<numberOfRow;i++){
            for (int j = 0; j<numberOfColumn; j++){
                XSSFRow row = book.getSheet(sheetName).getRow(i);
                XSSFCell cell = row.getCell(j);
                String value = cellToString(cell);
                data[i-1][j] = value;
            }
        }
        data = deleteNulls(data);
        return data;
    }
}
