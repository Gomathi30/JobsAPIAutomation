package Utils;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelHandling {
	
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	public ExcelHandling(String filePath,String sheetName)
	{
		 //filePath = "/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/data/TestData.xlsx";
		try {
			//System.out.println(filePath +   sheetName);
			 workbook = new XSSFWorkbook(filePath);
			 sheet= workbook.getSheet(sheetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			e.getCause();
			e.printStackTrace();
		}
	}

	public static int getNoOfRows() {
			int countOfRows=0;
			try {
			//int noOfRow = sheet.getLastRowNum();
			countOfRows=sheet.getPhysicalNumberOfRows();
			//System.out.println("last row number in Excel Sheet"+noOfRow);
			//System.out.println("No of rows in Excel Sheet"+countOfRows);
			}
			catch(Exception e)
			{
				e.getMessage();
				e.getCause();
				e.printStackTrace();
			}
			return countOfRows;
					
	}
	
	public static int getNoOfColumns()
	{
		int noOfColumns=0; 
		try{
			noOfColumns= sheet.getRow(0).getPhysicalNumberOfCells();
			//System.out.println("No of columns in Excel Sheet"+ noOfColumns);
		}
		catch(Exception e)
		{
			e.getMessage();
			e.getCause();
			e.printStackTrace();
		}
		return noOfColumns;
	}
	
	public static Object getCellData(int rowCount ,int coulumnCount)
	{
		
		//String cellData=null;
		Object value=null;
		try{
			//cellData= sheet.getRow(rowCount).getCell(coulumnCount).toString(); //getStringCellValue();
			DataFormatter formatter = new DataFormatter();
		   value= formatter.formatCellValue(sheet.getRow(rowCount).getCell(coulumnCount));
			
		}
		catch(Exception e) {
			e.getMessage();
			e.getCause();
			e.printStackTrace();
		}
		return value;
	}
	
	
	public static Object[][] getTestData(String path,String sheetName) {
		ExcelHandling excel= new ExcelHandling(path, sheetName);
		int rowCount=excel.getNoOfRows();
		int columnCount=excel.getNoOfColumns();
		Object data[][]=new Object[rowCount-1][columnCount];
		for(int i=1;i<rowCount;i++) {
			for (int j=0;j<columnCount;j++)
			{
				Object cellData= excel.getCellData(i,j);
				
				System.out.print( cellData +"|");
				data[i-1][j]=cellData;
			}
			System.out.println();
		}
		return data;
	}
	
	/*
	public static void getCellDataTest()
	{
		String filePath = "/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/data/TestData.xlsx";
		try {
			workbook = new XSSFWorkbook(filePath);
			sheet= workbook.getSheetAt(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			e.getCause();
			e.printStackTrace();
		}

		//using data formatter to extract data from excel
		/*DataFormatter formatter = new DataFormatter();
		
		for(int i=0;i<=2;i++) {
			for(int j=0;j<=6;j++) {
				Object value= formatter.formatCellValue(sheet.getRow(i).getCell(j));
				//	int JobId= sheet.getRow(1).getCell(0);
				System.out.println("row:"+i+"column:"+j +  value);
			}
		}*/
		
		/*
		Iterator<Row> row = sheet.rowIterator();
		
		while(row.hasNext())
		{
			 Row currentRow = row.next();
             Iterator<Cell> cellvalue = currentRow.cellIterator();
             while (cellvalue.hasNext())
             {
            	Cell cellvalu = cellvalue.next();
            	//get the cell value data type:
            	System.out.println(cellvalu.getCellType());
            	cellvalu.getCellType();
            	//get the string value 
            	System.out.println(cellvalu.getStringCellValue());
            	//get the int value
            	System.out.println(cellvalu.getNumericCellValue());
             }

		}

	}*/
	
}
