package bankingTestNG;

import java.io.File;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Util {

	public static final String FILE_PATH = "H:\\JAVA\\JAVA Projects\\BankingProject\\excel\\testData.xls"; // File Path
	public static final String SHEET_NAME = "Data"; // Sheet name
	public static final String TABLE_NAME = "testData";
	public static final String BASE_URL = "http://www.demo.guru99.com/";
	public static final int WAIT_TIME = 30; 
	public static final String EXPECT_TITLE = "Guru99 Bank HomePage";
	public static final String EXPECT_ERROR = "User or Password is not valid";
//	public static final String FIREFOX_PATH = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";

	public static String[][] getDataFromExcel(String xlFilePath,
			String sheetName, String tableName) throws Exception {
		String[][] tabArray = null;

		// get the workbook file. Provide the path of excel file
		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
		// get the sheet name
		Sheet sheet = workbook.getSheet(sheetName);

		int startRow, startCol, endRow, endCol, ci, cj;
		Cell tableStart = sheet.findCell(tableName);
		// Row position of FIRST appear table name
		startRow = tableStart.getRow();
		// Col position of FIRST appear table name
		startCol = tableStart.getColumn();

		// find cell position which contain last appear table name
		Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1,
				100, 64000, false);
		// Row position of LAST appear table name
		endRow = tableEnd.getRow();
		// Col position of LAST appear table name
		endCol = tableEnd.getColumn();

		tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;

		// Store all data in an array
		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				//Get content of each cell and store to each array element.
				tabArray[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}

		return (tabArray);
	}




}

