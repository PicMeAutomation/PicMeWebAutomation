package Utilities.ExcelConfig;

import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcel {

	//@SuppressWarnings("deprecation")
	public static Object[][] readTCdata(String TCName) throws Exception 
	{
		FileInputStream ip = new FileInputStream("./src/main/java/Data/UtilitiesData/Testdata.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ip);
		HSSFSheet sh =wb.getSheet(TCName);

		int totalRows=sh.getPhysicalNumberOfRows();
		System.out.println("totalRows="+totalRows);
		int totalColumns=sh.getRow(0).getPhysicalNumberOfCells();
		System.out.println("totalColumns="+totalColumns);

		//create two D OBject array
		Object arr[][]= new Object[(totalRows-1)][totalColumns];
		for(int i=1;i<totalRows;i++)
		{
			for(int j=0;j<totalColumns;j++)
			{
				try{
					String s = sh.getRow(i).getCell(j).getStringCellValue();
					arr[(i-1)][j]= s;
				} catch(NullPointerException NPE) {
					arr[(i-1)][j]= "";
				} catch(IllegalStateException e) {
					int in = (int)sh.getRow(i).getCell(j).getNumericCellValue();
					arr[(i-1)][j]= in;
				}
				
			}
		}
		wb.close();
		return arr;
	}
}
