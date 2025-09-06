package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.*;

public class ExcelUtil {

    public static Object[][] getTestData(String filePath, String sheetName, String groupFilter) {
        List<Object[]> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter(); // ✅ handles all cell types

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String testCaseId = formatter.formatCellValue(row.getCell(0));
                String username   = formatter.formatCellValue(row.getCell(1));
                String password   = formatter.formatCellValue(row.getCell(2));
                String expected   = formatter.formatCellValue(row.getCell(3));
                String testType   = formatter.formatCellValue(row.getCell(4));
                String groups     = formatter.formatCellValue(row.getCell(5));
                String desc       = formatter.formatCellValue(row.getCell(6));
                
                // ✅ Filter by group (matches partial string like "sanity" inside "smoke,sanity,regression")
                if (groups != null && !groups.isEmpty() &&
                    groups.toLowerCase().contains(groupFilter.toLowerCase())) {
                    data.add(new Object[]{ 
                        testCaseId, username, password, expected, testType, groups, desc 
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toArray(new Object[0][0]);
    }
}
