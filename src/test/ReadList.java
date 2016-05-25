package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadList {
	private static Logger logger = Logger.getLogger(ReadList.class);
	private static Workbook twb = null;
	private static Sheet tsheet = null;
	private final static String AWORD = "oeang|aeng|earn|erng|eung|uang|iang|ieng|oean|waen|wian|oung|uong"
			+ "|aung|ouei|oang|uang|oeng"
			+ "|arn|aen|ein|eng|uan|ian|ien|iaw|iew|aeo|aew|ieo|eow|oea|oun|oon|aun|uon|ong|ung|oei|uai|uay"
			+ "|uei|uag|wai|wae|ang|ing|uan|oan|uea|oen"
			+ "|aa|ag|ah|ar|ai|ay|ea|ei|ae|ey|eg|ao|au|aw|ow|an|al|en|ia|ie|ya|ee|in|il|iu|yu|eo|ew|oe|er|ua"
			+ "|wa|on|ul|oo|oi|oy|ui|un|ue" + "|a|e|i|o|u";
	private final static String EWORD = "oeang|aeng|earn|erng|eung|uang|iang|ieng|oean|waen|wian|oung|uong"
			+ "|aung|ouei|oang|uang|oeng"
			+ "|arn|aen|ein|eng|uan|ian|ien|iaw|iew|aeo|aew|ieo|eow|oea|oun|oon|aun|uon|ong|ung|oei|uai|uay"
			+ "|uei|uag|wai|wae|ang|ing|uan|oan|uea|oen"
			+ "|aa|ag|ah|ar|ai|ay|ea|ei|ae|ey|eg|ao|au|aw|ow|an|al|en|ia|ie|ee|in|il|iu|yu|eo|ew|er"
			+ "|on|ul|oo|oi|oy|ui" + "|i";
	private final static String TAIL = "kh|hk|dh|ch|bh|ph|pf|p|s|j|b|g|k|d|t";
	private final static String BWORD = "ag,ah,ar,ay,ey,eg,an,arn,al,aen,aeng,ein,en,eng,oeang,uan,aw,ow,iew"
			+ ",iaw,ew,aew,eow,uang,ian,ien,iang,ieng,in,il,on,oon,oean,aun,waen,ul,ong,oung,ung,uong,aung,un"
			+ ",oy,oay,wag,oang,ing,oan,oeng";
	private final static Set<String> set = new TreeSet<String>();
	// private final static String CWORD =
	// "kh|hk|dh|ch|bh|ph|pf|p|s|j|b|g|k|d|t";
	public static HashMap<String, String> map = new HashMap<String, String>();
	public static HashMap<String, String> mapa = new HashMap<String, String>();
	public static HashMap<String, String> mapt = new HashMap<String, String>();
	public static HashMap<String, String> mapw = new HashMap<String, String>();
	static {
		set.addAll(Arrays.asList(BWORD.split(",")));
	}

	private static void readFile() {
		InputStream inp = null;
		try {
			inp = new FileInputStream("E:\\project\\tai\\t.xlsx");
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
	}

	private static void getMap() {
		if (null == twb) {
			ReadList.readFile();
		}
		Row rowt = tsheet.getRow(0);
		final int rlength = tsheet.getLastRowNum();
		final int clength = rowt.getLastCellNum();
		for (Cell c : rowt) {
			String con = c.getStringCellValue();
			if (!con.isEmpty()) {
				String ch = tsheet.getRow(1).getCell(c.getColumnIndex())
						.getStringCellValue();
				String[] conArray = con.split(",");
				for (int i = 0; i < conArray.length; i++) {
					mapt.put(conArray[i], ch);
				}
			}
		}
		for (int i = 2; i < rlength; i++) {
			String con = tsheet.getRow(i).getCell(0).getStringCellValue();
			if (!con.isEmpty()) {
				String ch = tsheet.getRow(i).getCell(1).getStringCellValue();
				String[] conArray = con.split(",");
				for (int j = 0; j < conArray.length; j++) {
					mapa.put(conArray[j], ch);
				}
			}
		}
		String con = "";
		String[] tArray = null;
		String[] aArray = null;
		for (int i = 2; i < rlength; i++) {
			for (int j = 2; j < clength; j++) {
				con = tsheet.getRow(i).getCell(j).getStringCellValue();
				if (con.isEmpty()) {
					continue;
				}
				tArray = tsheet.getRow(0).getCell(j).getStringCellValue()
						.split(",");
				aArray = tsheet.getRow(i).getCell(0).getStringCellValue()
						.split(",");
				for (int t = 0; t < tArray.length; t++) {
					for (int v = 0; v < aArray.length; v++) {
						String key = tArray[t] + aArray[v];
						mapw.put(key, con);
					}
				}
			}
		}

		Sheet exception = twb.getSheetAt(1);
		for (int i = 1; i < exception.getLastRowNum() + 1; i++) {
			Row row = exception.getRow(i);
			mapw.put(row.getCell(0).getStringCellValue(), row.getCell(1)
					.getStringCellValue());
		}
		map.putAll(mapt);
		map.putAll(mapa);
		map.putAll(mapw);
	}

	public static void decode(WordBean word) {
		if (map.isEmpty()) {
			getMap();
		}
		String tword = word.getTword().toLowerCase();
		String[] twordArray = tword.split(" ");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < twordArray.length; i++) {
			String w = twordArray[i];
			w = format(w);

			if (map.containsKey(w)) {
				sb.append(map.get(w));
			} else {
				sb.append(split(w));
			}
		}
	}

	public static String translate(WordBean word) {
		if (map.isEmpty()) {
			getMap();
		}
		String tword = word.getTword().toLowerCase();
		String[] twordArray = tword.split(" ");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < twordArray.length; i++) {
			String w = twordArray[i];
			w = format(w);
			if (map.containsKey(w)) {
				sb.append(map.get(w));
			} else {
				sb.append(split(w));
			}
		}
		String temp = sb.toString();
		if (temp.startsWith("南")) {
			sb.replace(0, 1, "楠");
		}
		if (temp.startsWith("西")) {
			sb.replace(0, 1, "锡");
		}
		if (temp.startsWith("夫")) {
			sb.replace(0, 1, "弗");
		}
		if (temp.endsWith("江")) {
			sb.replace(sb.length() - 1, sb.length(), "姜");
		}

		return sb.toString();
	}

	private static String format(String word) {
		StringBuffer sb = new StringBuffer(word);
		if (word.contains("m")) {
			// Pattern p =
			// Pattern.compile("(("+AWORD+")"+"m"+"^("+AWORD+"))"+"|"+"(("+AWORD+")"+"m$)");
			Pattern p = Pattern.compile("((" + AWORD + ")" + "m" + "[^aeiou])"
					+ "|" + "((" + AWORD + ")" + "m$)");
			Matcher m = p.matcher(word);
			while (m.find()) {
				// System.out.println(true);
				int t = word.substring(m.start(), m.end()).indexOf("m")
						+ m.start();
				sb.replace(t, t + 1, "n");
			}
		}
		if (word.endsWith("i")) {
			Pattern pi = Pattern.compile("(" + EWORD + ")" + "i$");
			Matcher mi = pi.matcher(word);
			while (mi.find()) {
				// System.out.println(true);
				sb.replace(sb.length() - 1, sb.length(), "n");
			}
		}
		return sb.toString();
	}

	private static String innerDecode(String word) {
		return innerDecode(word, false);
	}

	private static String innerDecode(String word, boolean init) {
		if (map.isEmpty()) {
			getMap();
		}
		String tword = word.toLowerCase();
		String[] twordArray = tword.split(" ");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < twordArray.length; i++) {
			String w = twordArray[i];
			w = format(w);
			if (map.containsKey(w)) {
				sb.append(map.get(w));
			} else {
				if (w.length() > 1
						&& map.containsKey(w.substring(1, w.length()))) {
					if (init) {
						sb.append(map.get(w.substring(0, 1)));
					}
					sb.append(map.get(w.substring(1, w.length())));
				} else if (w.length() > 2
						&& map.containsKey(w.substring(2, w.length()))) {
					if (init) {
						sb.append(map.get(w.substring(0, 2)));
					}
					sb.append(map.get(w.substring(2, w.length())));
				} else {
					sb.append("?");
				}
			}
		}
		return sb.toString();
	}

	private static String split(String str) {
		String aw = AWORD;
		// str = format(str);
		StringBuffer sb = new StringBuffer(str);
		Pattern p = Pattern.compile(aw);
		Matcher m = p.matcher(str);
		Pattern pt = Pattern.compile(TAIL);
		ArrayList<String> list = new ArrayList<String>();
		int start = 0;
		while (m.find()) {
			// int end = m.end();
			// Matcher mt = pt.matcher(sb.toString());
			if (set.contains(m.group()) && str.length() > m.end()
					&& "aeiou".contains(String.valueOf(str.charAt(m.end())))) {
				list.add(sb.substring(0, m.end() - start - 1));
				sb.delete(0, m.end() - start - 1);
				start = m.end() - 1;
			} else {
				list.add(sb.substring(0, m.end() - start));
				sb.delete(0, m.end() - start);
				start = m.end();
			}
		}
		if (sb.length() > 0) {
			// Pattern pt = Pattern.compile(TAIL);
			Matcher mt = pt.matcher(sb.toString());
			if (!mt.find()) {
				list.add(sb.toString());
			}
		}
		StringBuffer result = new StringBuffer();
		result.append(innerDecode(list.get(0), true));
		for (int i = 1; i < list.size(); i++) {
			result.append(innerDecode(list.get(i)));
		}
		return result.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordBean test = new WordBean("khloeng", "农索巴杜");
		readFile();
		getMap();
		translate(test);
		// System.out.println(map.get("ko"));
		// System.out.println(test.getCword());
		// split("Bueng".toLowerCase());
		// String a = "Phikun";
		// System.out.println(format(a));
	}
}
