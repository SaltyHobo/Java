


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Workbook;


public class Writer {
    
    private Workbook workbook;
    private FileOutputStream fileOut = new FileOutputStream("new-financials-analysis.xlsx");

    public Writer(Workbook workbook) throws FileNotFoundException {
        this.workbook = workbook;
    }

    public void write() throws Exception {
        this.workbook.write(fileOut);
        System.out.println("wrote xlsx file");
        fileOut.close();
    }
    
}
