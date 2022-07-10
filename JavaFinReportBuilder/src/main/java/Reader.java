


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class Reader {

    private FileInputStream templateFile;
    private Workbook templateWorkbook;
    private FileInputStream secFile;
    private Workbook secFiling;
    private String quarterOfSECFiling;
    private Cell emptyColumnCell;
    private int templateColumnOfCurrentQuarter;

    public Reader(String template, String secFiling) throws Exception {
        
        this.templateFile = new FileInputStream(new File(template));
        this.templateWorkbook = new XSSFWorkbook(this.templateFile);
        //deleteExtraColumn();
        this.secFile = new FileInputStream(new File(secFiling));
        this.secFiling = new XSSFWorkbook(this.secFile);
        this.quarterOfSECFiling = "";
        verifyQuarter();
        this.emptyColumnCell = null;
        try {
            findEmptyColumn();
        } catch (Exception e) {
            System.out.println("Error Reader FindEmptyColumn: "+e.getMessage());
        }
        if (this.emptyColumnCell == null) {
            makeNewColumn();
        }
        this.templateColumnOfCurrentQuarter = emptyColumnCell.getColumnIndex();
    }

    //On my machine, Excel keeps adding an empty column at the end of the sheet. Need to delete it first.
    /*public void deleteExtraColumn() {
        for (Sheet sheet : this.templateWorkbook) {
            for (int r=0; r<sheet.getLastRowNum()+1; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                int lastColumn = row.getLastCellNum()-1;
                Cell cellToDel = row.getCell(lastColumn);
                row.removeCell(cellToDel);
            }
        }
    }*/

    public Workbook getFilledWorkbook() throws Exception {
        fillTemplate();
        return this.templateWorkbook;
    }

    public void makeNewColumn() throws Exception {
        Sheet balanceSheet = this.templateWorkbook.getSheet("Balance Sheet (quarters)");
        for (Row currentRow : balanceSheet) {
            currentRow.createCell(1, CellType.BLANK);
        }
        Sheet incomeStatement = this.templateWorkbook.getSheet("Income Statement (quarters)");
        for (Row currentRow : incomeStatement) {
            currentRow.createCell(1, CellType.BLANK);
        }
        Sheet cashFlows = this.templateWorkbook.getSheet("operating CF margin");
        for (Row currentRow : cashFlows) {
            currentRow.createCell(1, CellType.BLANK);
        }
    }

    public void findEmptyColumn() {
        try {
            System.out.println("start findEmptyColumn()");
            Sheet balanceSheet = this.templateWorkbook.getSheet("Balance Sheet (quarters)");
            Row r = balanceSheet.getRow(4);
            int columnCountdown = r.getLastCellNum()-1;
            while (columnCountdown > 0) {
                String columnLetter = CellReference.convertNumToColString(columnCountdown);
                CellReference isEmpty = new CellReference(columnLetter+"4");
                Row isEmptyRow = balanceSheet.getRow(isEmpty.getRow());
                Cell isEmptyCell = isEmptyRow.getCell(isEmpty.getCol(), MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (isEmptyCell != null) {
                    System.out.println("isEmptyCell != null");
                    System.out.println("docType: "+isEmptyCell.getRichStringCellValue().getString());
                    columnCountdown--;
                }
                if (isEmptyCell == null || isEmptyCell.getCellType() == CellType.BLANK) {
                    System.out.println("CellType.BLANK");
                    this.emptyColumnCell = isEmptyCell;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error findEmptyColumn(): "+e.getMessage());
        }
    }

    public void verifyQuarter() {
        try {

            Sheet coverSheet = this.secFiling.getSheet("Cover Page");
            CellReference docType = new CellReference("B4");
            Row docTypeRow = coverSheet.getRow(docType.getRow());
            Cell docTypeCell = docTypeRow.getCell(docType.getCol());

            if (docTypeCell != null) {
                System.out.println("docType: "+docTypeCell.getRichStringCellValue().getString());
            }
            if (docTypeCell.getRichStringCellValue().getString().equals("10-Q")) {

                System.out.println("Is 10-Q");
                CellReference quarterNum = new CellReference("B27");
                Row quarterNumRow = coverSheet.getRow(quarterNum.getRow());
                Cell quarterNumCell = quarterNumRow.getCell(quarterNum.getCol());

                if (quarterNumCell != null) {
                    System.out.println("quarterNum: "+quarterNumCell.getRichStringCellValue().toString());
                }
                if (quarterNumCell.getRichStringCellValue().getString().equals("Q1")) {
                    System.out.println("Is Q1");
                    this.quarterOfSECFiling = "Q1";
                }
                if (quarterNumCell.getRichStringCellValue().getString().equals("Q2")) {
                    System.out.println("Is Q2");
                    this.quarterOfSECFiling = "Q2";
                }
                if (quarterNumCell.getRichStringCellValue().getString().equals("Q3")) {
                    System.out.println("Is Q3");
                    this.quarterOfSECFiling = "Q3";
                }
            }
            else if (docTypeCell.getRichStringCellValue().getString().equals("10-K")) {
                System.out.println("Is Q4");
                this.quarterOfSECFiling = "Q4";
            }

        } catch (Exception e) {
            System.out.println("ErrorVerifyQuarter: "+e.getMessage());
        }
    }

    public void fillTemplate() {

        try {

            Sheet templateBalanceSheet = this.templateWorkbook.getSheet("Balance Sheet (quarters)");
            Sheet templateIncomeStatement = this.templateWorkbook.getSheet("Income Statement (quarters)");
            Sheet templateCashFlows = this.templateWorkbook.getSheet("operating CF margin ");

            for (int i=0; i<9; i++) {
                Sheet secSheet = this.secFiling.getSheetAt(i);
                for (Row row : secSheet) {
                    System.out.println("**start row: "+row.getRowNum());
                    if (row.getCell(0) == null) {
                        System.out.println("row.getCell(0) == null");
                        continue;
                    }
                    if (row.getCell(1) == null) {
                        System.out.println("row.getCell(1) == null");
                        continue;
                    }
                    String cellName = row.getCell(0).getStringCellValue();
                    System.out.println("cellName (first cell in row) :  "+cellName);
                    CellReference newCell = new CellReference(row.getCell(1));
                    System.out.println("newCell (second cell in row) :  "+newCell.toString());
                    Row secRow = this.secFiling.getSheetAt(i).getRow(newCell.getRow());
                    Cell secCell = secRow.getCell(newCell.getCol());
                    System.out.println("start switch");
                    switch (cellName) {
                        case "Trading Symbol" :
                            System.out.println("case trading symbol");
                            //Ticker
                            Row fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(0);
                            Cell filledCell = fillRow.getCell(0);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Entity Registrant Name" :
                            System.out.println("case Entity Registrant Name");
                            //Company Name
                            fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(0);
                            filledCell = fillRow.getCell(1);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Document Period End Date" :
                            System.out.println("case document period end date");
                            // row 4 Fiscal quarter ended
                            fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(3);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            fillRow = this.templateWorkbook.getSheet(templateIncomeStatement.getSheetName()).getRow(3);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            fillRow = this.templateWorkbook.getSheet(templateCashFlows.getSheetName()).getRow(3);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Total assets" :
                            System.out.println("case Total assets");
                            fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(4);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Total current assets" :
                            System.out.println("case Total current assets");
                            fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(5);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Intangible assets" :
                            System.out.println("case Intangible assets");
                            fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(6);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Total liabilities" :
                            System.out.println("case Total liabilities");
                            fillRow = this.templateWorkbook.getSheet(templateBalanceSheet.getSheetName()).getRow(7);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        //start income statement
                        case "Net sales" :
                            System.out.println("case Net sales");
                            fillRow = this.templateWorkbook.getSheet(templateIncomeStatement.getSheetName()).getRow(4);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);

                            if (this.quarterOfSECFiling.equals("Q4")) {
                                System.out.println("Q4: subtracting previous 3 quarters");
                                Cell q3 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 3);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - q3.getNumericCellValue()
                                                - q2.getNumericCellValue()
                                                - q1.getNumericCellValue()
                                );
                            }

                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Diluted (in shares)" :
                            System.out.println("Diluted (in shares)");
                            fillRow = this.templateWorkbook.getSheet(templateIncomeStatement.getSheetName()).getRow(5);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            filledCell.setCellValue(secCell.toString());
                            continue;
                        //Poi cannot find row 7, throws an error ??  if anyone knows how to fix this please contact me.
                        /*
                        case "Cost of sales" :
                            System.out.println("case Cost of sales");
                            fillRow = this.templateWorkbook.getSheet(templateIncomeStatement.getSheetName()).getRow(7);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);
                            
                            if (this.quarterOfSECFiling.equals("Q4")) {
                                System.out.println("Q4: subtracting previous 3 quarters");
                                Cell q3 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 3);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - Double.parseDouble(q3.getStringCellValue())
                                                - Double.parseDouble(q2.getStringCellValue())
                                                - Double.parseDouble(q1.getStringCellValue())
                                );
                            }
                            
                            filledCell.setCellValue(secCell.toString());
                            continue;
                            */
                        //start cash flow statement
                        case "Cash generated by operating activities" :
                            System.out.println("Cash generated by operating activities");
                            fillRow = this.templateWorkbook.getSheet(templateCashFlows.getSheetName()).getRow(4);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);

                            if (this.quarterOfSECFiling.equals("Q4")) {
                                System.out.println("Q4: subtracting previous 3 quarters");
                                Cell q3 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 3);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - Double.parseDouble(q3.getStringCellValue())
                                                - Double.parseDouble(q2.getStringCellValue())
                                                - Double.parseDouble(q1.getStringCellValue())
                                );
                            }
                            if (this.quarterOfSECFiling.equals("Q3")) {
                                System.out.println("Q3: subtracting previous 2 quarters");
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - Double.parseDouble(q2.getStringCellValue())
                                                - Double.parseDouble(q1.getStringCellValue())
                                );
                            }
                            
                            if (this.quarterOfSECFiling.equals("Q2")) {
                                System.out.println("Q2: subtracting previous 1 quarter");
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                System.out.println("test1");
                                System.out.println("secCell.getCellType(): "+secCell.getCellType()); //number
                                System.out.println("q1.getCellType(): "+q1.getCellType()); //string
                                System.out.println("q1.getStringCellValue(): "+q1.getStringCellValue());
                                System.out.println("Double.parseDouble(q1.getStringCellValue()): "+Double.parseDouble(q1.getStringCellValue()));
                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - Double.parseDouble(q1.getStringCellValue())
                                );
                                System.out.println("test1.1");
                                System.out.println("secCell: "+secCell.toString());
                                System.out.println("test2");
                            }
                            System.out.println("test3");
                            filledCell.setCellValue(secCell.toString());
                            System.out.println("test4");
                            continue;
                        case "Cash used in investing activities" :
                            System.out.println("Cash used in investing activities");
                            fillRow = this.templateWorkbook.getSheet(templateCashFlows.getSheetName()).getRow(5);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);

                            if (this.quarterOfSECFiling.equals("Q4")) {
                                System.out.println("Q4: subtracting previous 3 quarters");
                                Cell q3 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 3);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - q3.getNumericCellValue()
                                                - q2.getNumericCellValue()
                                                - q1.getNumericCellValue()
                                );
                            }
                            if (this.quarterOfSECFiling.equals("Q3")) {
                                System.out.println("Q3: subtracting previous 2 quarters");
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                        - q2.getNumericCellValue()
                                        - q1.getNumericCellValue()
                                );
                            }
                            if (this.quarterOfSECFiling.equals("Q2")) {
                                System.out.println("Q2: subtracting previous 1 quarter");
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                        - q1.getNumericCellValue()
                                );
                            }

                            filledCell.setCellValue(secCell.toString());
                            continue;
                        case "Cash used in financing activities" :
                            System.out.println("Cash used in financing activities");
                            fillRow = this.templateWorkbook.getSheet(templateCashFlows.getSheetName()).getRow(6);
                            filledCell = fillRow.getCell(this.templateColumnOfCurrentQuarter);

                            if (this.quarterOfSECFiling.equals("Q4")) {
                                System.out.println("Q4: subtracting previous 3 quarters");
                                Cell q3 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 3);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                                - q3.getNumericCellValue()
                                                - q2.getNumericCellValue()
                                                - q1.getNumericCellValue()
                                );
                            }
                            if (this.quarterOfSECFiling.equals("Q3")) {
                                System.out.println("Q3: subtracting previous 2 quarters");
                                Cell q2 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 2);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                        - q2.getNumericCellValue()
                                        - q1.getNumericCellValue()
                                );
                            }
                            if (this.quarterOfSECFiling.equals("Q2")) {
                                System.out.println("Q2: subtracting previous 1 quarter");
                                Cell q1 = fillRow.getCell(this.templateColumnOfCurrentQuarter + 1);

                                secCell.setCellValue(
                                        secCell.getNumericCellValue()
                                        - q1.getNumericCellValue()
                                );
                            }

                            filledCell.setCellValue(secCell.toString());
                            continue;
                    }
                    System.out.println("ended switch");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in fillTemplate: "+e.getMessage());
        }
    }
}
