package auxiliary.filetype;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

import static org.apache.poi.ss.usermodel.CellType.STRING;

public class xlsx {

    public void createEmptyFile(String filepath, String sheetname) throws IOException {
        //System.out.println("xlsxCreateEmptyFile START");
        System.out.println("Creating empty Excel file: " +filepath);
        XSSFWorkbook xlsx = new XSSFWorkbook();
        xlsx.createSheet(sheetname);
        FileOutputStream fos = new FileOutputStream(filepath);
        xlsx.write(fos);
        xlsx.close();
    }

    // *** created based on https://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
    // *** but changed to handle 2D array of Strings
    public void appendWorkbookOnFirstSheet(String filepath, ArrayList<ArrayList<String>> array2d) throws IOException {
        //System.out.println("xlsxAppendWorkbookOnFirstSheet START");
        // *** The FileReader is meant for reading streams of characters. For reading streams of raw bytes,
        // *** consider using a FileInputStream. (from Oracle java docs)
        // *** Difference between FileReader and FileInputStream
        // *** https://www.geeksforgeeks.org/difference-between-fileinputstream-and-filereader-in-java/
        //File xlsxfile = new File(filepath); // *** see note on .write() below in the code
        System.out.println("Opening xlsx file: " +filepath);
        FileInputStream fis = new FileInputStream(filepath);
        // *** Finds the workbook instance for XLSX file
        XSSFWorkbook xlsxworkbook = new XSSFWorkbook(fis);
        // *** Return first sheet from the XLSX workbook
        XSSFSheet xlsxsheet = xlsxworkbook.getSheetAt(0);
        // *** index to iterate thru xlsx rows and set it to the last row number to append new data
        int sheetrowidx = xlsxsheet.getLastRowNum();
        // *** if sheet has no rows getLastRowNum() will return '-1'. Handler below:
        if (sheetrowidx == -1) {
            sheetrowidx = 0;
        }
        // *** iterate thru array2d and append workbook
        // *** array rows iteration from 0 to array2d.size()
        System.out.println("Building excel data... Processing line: ");
        int counter = 0;
        for (int arrayrowidx = 0; arrayrowidx < array2d.size(); arrayrowidx++) {
            // Create a new row in existing XLSX sheet
            XSSFRow xlsxrow = xlsxsheet.createRow(sheetrowidx++);
            // *** array column iteration from 0 to array2d.get(arrayrowidx).size()-1 (idx always less by 1 then size())
            int sheetcellidx = 0; // *** index to iterate thru xlsx cells of current row
            for (int arraycolumnidx = 0; arraycolumnidx < array2d.get(arrayrowidx).size(); arraycolumnidx++) {
                XSSFCell cell = xlsxrow.createCell(sheetcellidx++, STRING);
                cell.setCellValue(array2d.get(arrayrowidx).get(arraycolumnidx));
            }
            counter++;
            System.out.print("\033[2K\r"); // Erase line content
            System.out.print(counter);
        }
        // *** open an OutputStream (FileOutputStream) to save written data into XLSX file. See note at top of code
        System.out.println();
        System.out.println("Saving xlsx file. Wait...");
        FileOutputStream fos = new FileOutputStream(filepath);
        // *** .write() - if the Document was opened from a File rather than an InputStream, you must write out to a
        // *** different file, overwriting via an OutputStream isn't possible.
        // *** If stream is a FileOutputStream on a networked drive or has a high cost/latency associated with each written byte,
        // *** consider wrapping the OutputStream in a BufferedOutputStream to improve write performance.
        xlsxworkbook.write(fos);
        xlsxworkbook.close(); // Release workbook Resources (wtf is that ??? Is it needed if fis and fos are closed ???)
        fis.close(); // Release FileInputStream Resources
        fos.close(); // Release FileOutputStream Resources
        System.out.println("Writing xlsx file finished...");
    }

}
