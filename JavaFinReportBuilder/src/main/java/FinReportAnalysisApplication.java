


public class FinReportAnalysisApplication {
    
    public static void main(String[] args) throws Exception {
        
        //input for reader = ( template , financial report )
        Reader reader = new Reader("demo-java-fa-template.xlsx", "AAPL/Financial_Report.xlsx");
        Writer writer = new Writer(reader.getFilledWorkbook());
        writer.write();
        
        System.out.println("fin");
    }
    
}