package configuration;

	import org.apache.poi.ss.usermodel.*;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import java.io.*;

	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;

	public class ExcelUtils {
		private String filePath;
		private Sheet sheet;
		 private Workbook workbook;
	    private static final String FILE_PATH = "C:/Users/sriuppal2/OneDrive - Publicis Groupe/Desktop/Koha/patron.xlsx";
	   
	    public static Workbook getWorkbook() throws IOException {
	        FileInputStream fis = new FileInputStream(FILE_PATH);
	        return new XSSFWorkbook(fis);
	    }

	    public static void saveWorkbook(Workbook workbook) throws IOException {
	        FileOutputStream fos = new FileOutputStream(FILE_PATH);
	        workbook.write(fos);
	        fos.close();
	    }
	    public int getRowCount() {
	        return sheet.getPhysicalNumberOfRows();
	    }

	    // Read a cell value (String)
	    public String getCellData(int rowNum, int colNum) {
	        Row row = sheet.getRow(rowNum);
	        Cell cell = row.getCell(colNum);

	        if (cell == null) return "";
	        return cell.toString();
	    }

	    // Write data back to a cell
	    public void setCellData(int rowNum, int colNum, String data) throws IOException {
	        Row row = sheet.getRow(rowNum);
	        if (row == null) row = sheet.createRow(rowNum);

	        Cell cell = row.getCell(colNum);
	        if (cell == null) cell = row.createCell(colNum);

	        cell.setCellValue(data);

	        FileOutputStream fos = new FileOutputStream(filePath);
	        workbook.write(fos);
	        fos.close();
	    }

	    // Close the workbook
	    public void closeWorkbook() throws IOException {
	        workbook.close();
	    }
	

}
