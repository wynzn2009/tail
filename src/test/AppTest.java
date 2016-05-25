package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class AppTest {
	private static Logger logger = Logger.getLogger(AppTest.class);
	private static Workbook twb = null;
	private static Sheet tsheet = null;
	private static List<WordBean> testList;

	private static void readFile() {
		InputStream inp = null;
		try {
//			inp = new FileInputStream("E:\\project\\tai\\50.xlsx");
			inp = new FileInputStream("E:\\project\\tai\\70.xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("file not found");
			return;
		}
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inp);
			twb = wb;

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0);
		tsheet = sheet;
		// System.out.print(cell.getStringCellValue());
	}

	private static void getTestList() {
		if (null == twb) {
			readFile();
		}
		testList = new ArrayList<WordBean>();
		System.out.println(tsheet.getLastRowNum());
		for (int i = 1; i < tsheet.getLastRowNum() + 1; i++) {
			Row row = tsheet.getRow(i);
			WordBean bean = new WordBean(row.getCell(0).getStringCellValue()
					.replaceAll("\\W+", " "), row.getCell(1)
					.getStringCellValue());
			testList.add(bean);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getTestList();
		CellStyle style = twb.createCellStyle();
		for (int i = 0; i < testList.size(); i++) {
			WordBean bean = testList.get(i);
			String result = ReadList.translate(bean);
			System.out.println((i+2)+","+bean.getValue() + "," + result + ","
					+ result.equals(bean.getCword()));
			Row row = tsheet.getRow(i+1);
			Cell cell = row.createCell(2);
			cell.setCellValue(result);
			if(!result.equals(bean.getCword())){
				style.setFillForegroundColor(IndexedColors.YELLOW.index);
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(style);
			}
		}
		try {
			FileOutputStream fileOut = new FileOutputStream("E:\\project\\tai\\70.xlsx");
			twb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
