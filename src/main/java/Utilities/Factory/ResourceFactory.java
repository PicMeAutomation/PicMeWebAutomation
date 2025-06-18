package Utilities.Factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import Utilities.ActionUtilities.ActionUtilities;

public class ResourceFactory {
	private static ActionUtilities au = new ActionUtilities();

    private static Map<Long, FileInputStream> threadToFileInputStreamForXLS = new HashMap<Long, FileInputStream>();
    private static Map<Long, FileOutputStream> threadToFileOutputStreamForXLS = new HashMap<Long, FileOutputStream>();
    private static Map<Long, HSSFWorkbook> threadToHSSFWorkbookForXLS = new HashMap<Long, HSSFWorkbook>();
    private static Map<Long, HSSFSheet> threadToHSSFSheetForXLS = new HashMap<Long, HSSFSheet>();
    private static Map<Long, HSSFRow> threadToHSSFRowForXLS = new HashMap<Long, HSSFRow>();

    private static Map<Long, FileInputStream> threadToFileInputStreamForProp = new HashMap<Long, FileInputStream>();
    private static Map<Long, FileOutputStream> threadToFileOutputStreamForProp = new HashMap<Long, FileOutputStream>();
    private static Map<Long, Properties> threadToPropertiesForProp = new HashMap<Long, Properties>();

    @SuppressWarnings("deprecation")
	public synchronized static FileInputStream createFileInputStreamForProp(String propFilePath) {
        try {
            Thread.sleep(au.randomNumber(800, 900));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToFileInputStreamForProp.containsKey(Thread.currentThread().getId())) {
            try {
                FileInputStream file = new FileInputStream(propFilePath);
                Long threadID = Thread.currentThread().getId();
                threadToFileInputStreamForProp.put(threadID, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return threadToFileInputStreamForProp.get(Thread.currentThread().getId());
    }

    @SuppressWarnings("deprecation")
	public synchronized static FileOutputStream createFileOutputStreamForProp(String propFilePath) {
        if (!threadToFileOutputStreamForProp.containsKey(Thread.currentThread().getId())) {
            try {
                FileOutputStream outFile = new FileOutputStream(new File(propFilePath));
                Long threadID = Thread.currentThread().getId();
                threadToFileOutputStreamForProp.put(threadID, outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return threadToFileOutputStreamForProp.get(Thread.currentThread().getId());
    }

    @SuppressWarnings("deprecation")
	public synchronized static FileInputStream createFileInputStreamForXLS(String xlsFilePath) {
        try {
            Thread.sleep(au.randomNumber(800, 900));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToFileInputStreamForXLS.containsKey(Thread.currentThread().getId())) {
            try {
                FileInputStream file = new FileInputStream(xlsFilePath);
                Long threadID = Thread.currentThread().getId();
                threadToFileInputStreamForXLS.put(threadID, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return threadToFileInputStreamForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static FileOutputStream createFileOutputStreamForXLS(String xlsFilePath) {
        if (!threadToFileOutputStreamForXLS.containsKey(Thread.currentThread().getId())) {
            try {
                FileOutputStream outFile = new FileOutputStream(new File(xlsFilePath));
                Long threadID = Thread.currentThread().getId();
                threadToFileOutputStreamForXLS.put(threadID, outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return threadToFileOutputStreamForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static FileInputStream getInFileForProp() {
        return threadToFileInputStreamForProp.get(Thread.currentThread().getId());
    }

    public synchronized static Properties createPropertiesForProp() {
        try {
            Thread.sleep(au.randomNumber(600, 700));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToPropertiesForProp.containsKey(Thread.currentThread().getId())) {
            try {
                Properties props = new Properties();
                props.load(getInFileForProp());
                Long threadID = Thread.currentThread().getId();
                threadToPropertiesForProp.put(threadID, props);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return threadToPropertiesForProp.get(Thread.currentThread().getId());
    }

    public synchronized static Properties getPropertiesForProp() {
        return threadToPropertiesForProp.get(Thread.currentThread().getId());
    }

    public synchronized static FileInputStream getInFileForXLS() {
        return threadToFileInputStreamForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static FileOutputStream getOutFileForXLS() {
        return threadToFileOutputStreamForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static FileOutputStream getOutFileForProp() {
        return threadToFileOutputStreamForProp.get(Thread.currentThread().getId());
    }

    public synchronized static HSSFWorkbook createHSSFWorkbookForXLS() {
        try {
            Thread.sleep(au.randomNumber(600, 700));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToHSSFWorkbookForXLS.containsKey(Thread.currentThread().getId())) {
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(getInFileForXLS());
                Long threadID = Thread.currentThread().getId();
                threadToHSSFWorkbookForXLS.put(threadID, workbook);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return threadToHSSFWorkbookForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static HSSFWorkbook getWorkbookForXLS() {
        if (threadToHSSFWorkbookForXLS.get(Thread.currentThread().getId()) == null) {
            threadToHSSFWorkbookForXLS.remove(Thread.currentThread().getId());
            createHSSFWorkbookForXLS();
            return threadToHSSFWorkbookForXLS.get(Thread.currentThread().getId());
        } else {
            return threadToHSSFWorkbookForXLS.get(Thread.currentThread().getId());
        }
    }

    public synchronized static HSSFSheet createHSSFSheetForXLS(String sheetName) {
        try {
            Thread.sleep(au.randomNumber(600, 900));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToHSSFSheetForXLS.containsKey(Thread.currentThread().getId())) {
            HSSFSheet sheet = getWorkbookForXLS().getSheet(sheetName);
            Long threadID = Thread.currentThread().getId();
            threadToHSSFSheetForXLS.put(threadID, sheet);
        }
        return threadToHSSFSheetForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static HSSFSheet getSheetForXLS() {
        return threadToHSSFSheetForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static HSSFRow createHSSFRowForXLS(int row) {
        try {
            Thread.sleep(au.randomNumber(600, 700));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToHSSFRowForXLS.containsKey(Thread.currentThread().getId())) {
            HSSFRow sheetRow = getSheetForXLS().createRow(row);
            Long threadID = Thread.currentThread().getId();
            threadToHSSFRowForXLS.put(threadID, sheetRow);
        }
        return threadToHSSFRowForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static HSSFRow getSetHSSFRowForXLS(int row) {
        try {
            Thread.sleep(au.randomNumber(600, 700));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!threadToHSSFRowForXLS.containsKey(Thread.currentThread().getId())) {
            HSSFRow sheetRow = getSheetForXLS().getRow(row);
            Long threadID = Thread.currentThread().getId();
            threadToHSSFRowForXLS.put(threadID, sheetRow);
        }
        return threadToHSSFRowForXLS.get(Thread.currentThread().getId());
    }

    public synchronized static HSSFRow getSheetRowForXLS(int row) {
        if (threadToHSSFRowForXLS.get(Thread.currentThread().getId()) == null) {
            threadToHSSFRowForXLS.remove(Thread.currentThread().getId());
            createHSSFRowForXLS(row);
            return threadToHSSFRowForXLS.get(Thread.currentThread().getId());
        } else {
            return threadToHSSFRowForXLS.get(Thread.currentThread().getId());
        }
    }

    public synchronized static void checkHSSFRowForXLS(HSSFRow sheetRow, int row) {
        if (sheetRow == null) {
            createHSSFRowForXLS(row);
        }
    }

    public synchronized static Cell checkCellForXLS(Cell cell, int cellNo, int row) {
        if (cell == null) {
            cell = getSheetRowForXLS(row).createCell(cellNo);
        }
        return cell;
    }

    public synchronized static Cell getCellForXLS(int cellNo, int row) {
        Cell cell = getSheetRowForXLS(row).getCell(cellNo);
        cell = checkCellForXLS(cell, cellNo, row);
        return cell;
    }


    public synchronized static String getCellStringValueForXLS(int cellNo, int row, String sheetName, String filePath) {
        String value;
        createFileInputStreamForXLS(filePath);
        createHSSFWorkbookForXLS();
        createHSSFSheetForXLS(sheetName);
        getSetHSSFRowForXLS(row);
        checkHSSFRowForXLS(getSheetRowForXLS(row), row);
        Cell cell;
        cell = getCellForXLS(cellNo, row);
        value = cell.getStringCellValue();
        writeAndCloseFileForXLS(filePath);
        return value;
    }

    public synchronized static double getCellNumericValueForXLS(int cellNo, int row, String sheetName, String filePath) {
        double value;
        createFileInputStreamForXLS(filePath);
        createHSSFWorkbookForXLS();
        createHSSFSheetForXLS(sheetName);
        getSetHSSFRowForXLS(row);
        checkHSSFRowForXLS(getSheetRowForXLS(row), row);
        Cell cell;
        cell = getCellForXLS(cellNo, row);
        value = cell.getNumericCellValue();
        writeAndCloseFileForXLS(filePath);
        return value;
    }

    public synchronized static boolean getCellBooleanValueForXLS(int cellNo, int row, String sheetName, String filePath) {
        boolean value;
        createFileInputStreamForXLS(filePath);
        createHSSFWorkbookForXLS();
        createHSSFSheetForXLS(sheetName);
        getSetHSSFRowForXLS(row);
        checkHSSFRowForXLS(getSheetRowForXLS(row), row);
        Cell cell;
        cell = getCellForXLS(cellNo, row);
        value = cell.getBooleanCellValue();
        writeAndCloseFileForXLS(filePath);
        return value;
    }

    public synchronized static void setCellValueForXLS(String cellValue, int cellNo, int row, String sheetName, String filePath) {
        createFileInputStreamForXLS(filePath);
        createHSSFWorkbookForXLS();
        createHSSFSheetForXLS(sheetName);
        getSetHSSFRowForXLS(row);
        checkHSSFRowForXLS(getSheetRowForXLS(row), row);
        Cell cell;
        cell = getCellForXLS(cellNo, row);
        cell = checkCellForXLS(cell, cellNo, row);
        cell.setCellValue(cellValue);
        writeAndCloseFileForXLS(filePath);
    }

    public synchronized static void setCellValueForXLS(int cellValue, int cellNo, int row, String sheetName, String filePath) {
        createFileInputStreamForXLS(filePath);
        createHSSFWorkbookForXLS();
        createHSSFSheetForXLS(sheetName);
        getSetHSSFRowForXLS(row);
        checkHSSFRowForXLS(getSheetRowForXLS(row), row);
        Cell cell;
        cell = getCellForXLS(cellNo, row);
        cell = checkCellForXLS(cell, cellNo, row);
        cell.setCellValue(cellValue);
        writeAndCloseFileForXLS(filePath);
    }

    public synchronized static void closeFileInputStreamForXLS() {
        try {
            getInFileForXLS().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadToFileInputStreamForXLS.remove(Thread.currentThread().getId());
    }

    public synchronized static void closeFileInputStreamForProp() {
        try {
            getInFileForProp().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadToFileInputStreamForProp.remove(Thread.currentThread().getId());
    }

    public synchronized static void writeWorkbookForXLS() {
        try {
            getWorkbookForXLS().write(getOutFileForXLS());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void writePropFile() {
        try {
            getPropertiesForProp().store(getOutFileForProp(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void closeFileOutputStreamForXLS() {
        try {
            getOutFileForXLS().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadToFileOutputStreamForXLS.remove(Thread.currentThread().getId());
        threadToHSSFWorkbookForXLS.remove(Thread.currentThread().getId());
        threadToHSSFSheetForXLS.remove(Thread.currentThread().getId());
        threadToHSSFRowForXLS.remove(Thread.currentThread().getId());
    }

    public synchronized static void closeFileOutputStreamForProp() {
        try {
            getOutFileForProp().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadToFileOutputStreamForProp.remove(Thread.currentThread().getId());
        threadToPropertiesForProp.remove(Thread.currentThread().getId());
    }

    public synchronized static void writeAndCloseFileForXLS(String xlsFilePath) {
        closeFileInputStreamForXLS();
        createFileOutputStreamForXLS(xlsFilePath);
        writeWorkbookForXLS();
        closeFileOutputStreamForXLS();
    }

    public synchronized static void writeAndCloseFileForProp(String propFilePath) {
        closeFileInputStreamForProp();
        createFileOutputStreamForProp(propFilePath);
        writePropFile();
        closeFileOutputStreamForProp();
        //ru.unsetPropertyFlag();
    }

    public synchronized static String getPropValueForProp(String propKey, String filePath) {
        String value;
        createFileInputStreamForProp(filePath);
        createPropertiesForProp();
        value = getPropertiesForProp().getProperty(propKey);
        writeAndCloseFileForProp(filePath);
        return value;
    }

    public synchronized static void setPropValueForProp(String propKey, String propValue, String filePath) {
        createFileInputStreamForProp(filePath);
        createPropertiesForProp();
        getPropertiesForProp().setProperty(propKey, propValue);
        writeAndCloseFileForProp(filePath);
    }

}
