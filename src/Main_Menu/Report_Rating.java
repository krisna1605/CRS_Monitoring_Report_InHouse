package Main_Menu;

import javax.swing.*;
import Connection.SQLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.UIManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.table.TableColumn;

public final class Report_Rating extends javax.swing.JFrame {

    //Set Connection
    SQLConnection sqlConnection = new SQLConnection();
    Connection con = sqlConnection.getConnection();
    private Statement stat;
    private ResultSet res;

    //Other Variable
    //Set Table Model
    private DefaultTableModel tabmode;

    //Nominal Formated
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    //String hasil = String.format("%.10f", angka);
    //StringFormat stringFormat = new StringFormat("%.2f");
    //Call Other Class
    //Set Table ColumnContent
    Object[] ColumnContent = {"CIF", "Nama Debitur", "Report Type", "Month", "Year", "Ficial Date", "Rating By", "Last Date Order", "Current Ratio", "Debt Equity Ratio", "Shareholder Equity", "Operating Margin", "Opr Earn", "Net Sales", "Debt Serv Coverage", "Short Term Borrowing", "Long Term Borrowing", "Currency", "Land", "Building", "Cash & Deposit"};
    public int index;

    public Report_Rating() {
        initComponents();

        //Set Main
        FSearchCriteria.setEnabled(false);
        tabmode = new DefaultTableModel(null, ColumnContent);
        TReport.setModel(tabmode);

        //Filter Input Filled Allowed Number Only
        keyFilter();

    }

    class CustomCellRenderer extends DefaultTableCellRenderer {

        private final DecimalFormat numberFormat = new DecimalFormat("#,###"); // Format untuk pemisah ribuan

        @Override
        public void setValue(Object value) {
            if (value instanceof Double) {
                // Format nilai float/double dengan presisi tinggi (misalnya 10 digit desimal)
                setText(String.format("%.10f", (Double) value));
            } else if (value instanceof Integer) {
                // Format nilai integer dengan pemisah ribuan
                setText(numberFormat.format(value));
            } else {
                super.setValue(value); // Default rendering untuk tipe data lain
            }
        }
    }

    //Field Year Input Only Number
    public void keyFilter() {
        ((AbstractDocument) FYear.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[0-9]*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    //Set Data Content
    public void dataTableContent() {
        tabmode = new DefaultTableModel(null, ColumnContent);
        TReport.setModel(tabmode);
        TReport.setFillsViewportHeight(true);
        //this.index = 1;

        //Filter Month
        ArrayList<String> selectedOptions = new ArrayList<>();
        ArrayList<String> selectedOptions2 = new ArrayList<>();

        if (jCheckBox1.isSelected()) {
            selectedOptions.add("'1'");
        }

        if (jCheckBox2.isSelected()) {
            selectedOptions.add("'2'");
        }

        if (jCheckBox3.isSelected()) {
            selectedOptions.add("'3'");
        }

        if (jCheckBox4.isSelected()) {
            selectedOptions.add("'4'");
        }

        if (jCheckBox5.isSelected()) {
            selectedOptions.add("'5'");
        }

        if (jCheckBox6.isSelected()) {
            selectedOptions.add("'6'");
        }

        if (jCheckBox7.isSelected()) {
            selectedOptions.add("'7'");
        }

        if (jCheckBox8.isSelected()) {
            selectedOptions.add("'8'");
        }

        if (jCheckBox9.isSelected()) {
            selectedOptions.add("'9'");
        }

        if (jCheckBox10.isSelected()) {
            selectedOptions.add("'10'");
        }

        if (jCheckBox11.isSelected()) {
            selectedOptions.add("'11'");
        }

        if (jCheckBox12.isSelected()) {
            selectedOptions.add("'12'");
        }

        if (jCheckBox13.isSelected()) {
            selectedOptions2.add("'Audited'");
        }

        if (jCheckBox14.isSelected()) {
            selectedOptions2.add("'Un-Audited'");
        }

        if (selectedOptions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Thick at Least One Month");
            return;
        }

        if (selectedOptions2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Thick at Least One Type");
            return;

        } else if (FYear.getText()
                .equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input Year");

        } else
            
            try {
            String sql
                    = "WITH RANKEDDATA AS (\n"
                    + "    SELECT \n"
                    + "        CRM.CU_REF,\n"
                    + "        MAX(CRM.AP_REGNO) AS AP_REGNO,\n"
                    + "        ROW_NUMBER() OVER (PARTITION BY CRM.CU_REF ORDER BY CRM.AP_REGNO DESC) AS APP,\n"
                    + "        C.CU_CIF AS CIF,\n"
                    + "        CC.CU_COMPNAME AS COMPNAME,\n"
                    + "        CRM.REPORTTYPE AS REPORTTYPE,\n"
                    + "        DATENAME(MONTH, MAX(CLM.IS_DATE_PERIODE)) AS RATINGMONTH,\n"
                    + "        YEAR(MAX(CLM.IS_DATE_PERIODE)) AS RATINGYEAR,\n"
                    + "        CAST(MAX(CLM.IS_DATE_PERIODE) AS DATE) AS FICIALDATE,\n"
                    + "        R.RATINGBY AS RATEBY,\n"
                    + "        CAST(MAX(CLM.IS_DATE_PERIODE) AS DATE) AS LASTDATEORDER,\n"
                    + "        CRM.CURRENT_RATIO AS CURRENTRATIO,\n"
                    + "        CRM.DEBT_EQUITY_RATIO AS DEBTRATIO,\n"
                    + "        CNM.BS_SHAREHOLDER_EQUITY AS BSEQUITY,\n"
                    + "        CRM.OPERATING_MARGIN AS OPRMARGIN,\n"
                    + "        CLM.IS_OPR_EARN AS OPREARN,\n"
                    + "        CLM.IS_NET_SALES AS NETSALES,\n"
                    + "        CRM.DEBT_SERV_COVERAGE AS DEBTSERV,\n"
                    + "        CNM.BS_SHT_TERM_LOAN AS SHORT_TERM_BORROWING,\n"
                    + "        CNM.BS_LG_TRM_LOAN_RECEIV AS LONG_TERM_BORROWING,\n"
                    + "        CLM.IS_CURRENCY AS CURRENCY,\n"
                    + "        CNM.BS_LAND AS LAND,\n"
                    + "        CNM.BS_BUILDINGS AS BUILDING,\n"
                    + "        CNM.BS_CASH_BANK AS CASH_DEPOSIT,\n"
                    + "        CNM.BS_DBST AS SHORT_TERM,\n"
                    + "        CNM.BS_LONGTERM_DEBT AS LONG_TERM\n"
                    + "    FROM CA_RATIO_MIDDLE CRM \n"
                    + "    LEFT JOIN CUSTOMER C ON CRM.CU_REF = C.CU_REF \n"
                    + "    LEFT JOIN CUST_COMPANY CC ON CRM.CU_REF = CC.CU_REF\n"
                    + "    LEFT JOIN RATINGFINALADJUSTMENT R ON CRM.AP_REGNO = R.AP_REGNO\n"
                    + "    LEFT JOIN RFRATINGCLASS R2 ON R.FINALRATING = R2.RATEID\n"
                    + "    LEFT JOIN CA_LABARUGI_MIDDLE CLM ON CRM.CU_REF = CLM.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CLM.AP_REGNO \n"
                    + "        AND CRM.DATE_PERIODE = CLM.IS_DATE_PERIODE \n"
                    + "    LEFT JOIN CA_NERACA_MIDDLE CNM ON CRM.CU_REF = CNM.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CNM.AP_REGNO \n"
                    + "        AND CRM.DATE_PERIODE = CNM.BS_DATE_PERIODE \n"
                    + "    LEFT JOIN CA_REKON_MIDDLE CRM2 ON CRM.CU_REF = CRM2.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CRM2.AP_REGNO \n"
                    + "        AND CRM.DATE_PERIODE = CRM2.DATE_PERIODE \n"
                    + "    LEFT JOIN CA_RATIO_BPR CPR ON CRM.CU_REF = CPR.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CPR.AP_REGNO\n"
                    + "    LEFT JOIN APPLICATION A ON CRM.CU_REF = A.CU_REF \n"
                    + "        AND CRM.AP_REGNO = A.AP_REGNO \n"
                    + "    LEFT JOIN SCOREBCG_RESULTHISTORY SR ON CRM.AP_REGNO = SR.AP_REGNO\n"
                    + "    WHERE \n"
                    + "        C.CU_CIF IS NOT NULL AND \n"
                    + "        C.CU_CIF NOT LIKE '%C%' AND \n"
                    + "        CRM.REPORTTYPE IN (" + String.join(", ", selectedOptions2) + ") AND \n"
                    + "        MONTH(CLM.IS_DATE_PERIODE) IN (" + String.join(", ", selectedOptions) + ") AND \n"
                    + "        YEAR(CLM.IS_DATE_PERIODE) = '" + FYear.getText() + "'\n"
                    + "    GROUP BY \n"
                    + "        C.CU_CIF,\n"
                    + "        CC.CU_COMPNAME,\n"
                    + "        R.FINALRATING,\n"
                    + "        R2.RATEDESC,\n"
                    + "        R.ADJUSTRATING,\n"
                    + "        R.RATINGDATE,\n"
                    + "        R.RATINGBY,\n"
                    + "        A.AP_USERNAME,\n"
                    + "        CRM.CURRENT_RATIO,\n"
                    + "        CRM.DEBT_EQUITY_RATIO,\n"
                    + "        CNM.BS_SHAREHOLDER_EQUITY,\n"
                    + "        CLM.IS_NET_SALES,\n"
                    + "        CLM.IS_DATE_PERIODE,\n"
                    + "        CRM.OPERATING_MARGIN,\n"
                    + "        CLM.IS_OPR_EARN,\n"
                    + "        CRM.DEBT_SERV_COVERAGE,\n"
                    + "        CRM.LONGTERM_DEBT_TO_EQUITY,\n"
                    + "        CRM.REPORTTYPE,\n"
                    + "        CPR.LDR,\n"
                    + "        CRM.AP_REGNO,\n"
                    + "        CRM.CU_REF,\n"
                    + "        CNM.BS_SHT_TERM_LOAN,\n"
                    + "        CNM.BS_LG_TRM_LOAN_RECEIV,\n"
                    + "        CLM.IS_CURRENCY,\n"
                    + "        CNM.BS_LAND,\n"
                    + "        CNM.BS_BUILDINGS,\n"
                    + "        CNM.BS_CASH_BANK,\n"
                    + "        CNM.BS_DBST,\n"
                    + "        CNM.BS_LONGTERM_DEBT\n"
                    + ")\n"
                    + "SELECT * \n"
                    + "FROM RANKEDDATA \n"
                    + "WHERE APP = 1";

            stat = con.createStatement();
            res = stat.executeQuery(sql);

            while (res.next()) {
                TReport.getColumnModel().getColumn(14).setCellRenderer(new CustomCellRenderer());
                tabmode.addRow(new Object[]{
                    res.getString("CIF"),
                    res.getString("CompName"),
                    res.getString("ReportType"),
                    res.getString("RatingMonth"),
                    res.getString("RatingYear"),
                    res.getString("FicialDate"),
                    res.getString("RateBy"),
                    res.getString("LastDateOrder"),
                    res.getDouble("CurrentRatio"),
                    String.format("%.10f", res.getFloat("DebtRatio")),
                    decimalFormat.format(res.getDouble("BsEquity")),
                    String.format("%.10f", res.getDouble("OprMargin")),
                    decimalFormat.format(res.getDouble("OprEarn")),
                    decimalFormat.format(res.getDouble("NetSales")),
                    String.format("%,.10f", res.getDouble("DebtServ")),
                    res.getString("SHORT_TERM_BORROWING"),
                    res.getString("LONG_TERM_BORROWING"),
                    res.getString("CURRENCY"),
                    res.getString("LAND"),
                    res.getString("BUILDING"),
                    res.getString("CASH_DEPOSIT")
                    //String.format("%.10f", res.getFloat("CASH_DEPOSIT"))

                /*res.getString("MappingCollect"),
                    /decimalFormat.format(res.getDouble("NetSales")),
                    decimalFormat.format(res.getDouble("OperatingIncome")),
                    decimalFormat.format(res.getDouble("NetIncome")),
                    decimalFormat.format(res.getDouble("ShareholdEquity")),
                    //decimalFormat.format(res.getDouble("CurrentRatio")),
                    String.format("%.2f", res.getFloat("CurrentRatio")),
                    String.format("%.2f", res.getDouble("EquityRatio")),
                    //decimalFormat2.format(res.getDouble("EquityRatio")),
                    res.getString("KAP"),
                    res.getDate("FS_DATE"),
                    res.getString("NUMBEROFMONTH"),
                    res.getString("YEARS"),
                    res.getString("REPORTTYPE"),*/
                });

            }
            //Set Display Laber & Other
            //FTotalRecord.setText(Integer.toString(TReport.getRowCount()));
            LReport.setText("REPORT: " + String.join(", ", selectedOptions) + " - " + FYear.getText() + " - " + String.join(", ", selectedOptions2) + " ");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fail Get Data" + e);
            System.out.println("--------- Query Fails" + e);

        }
        TableColumn column;
        for (int i = 0;
                i < TReport.getColumnCount();
                i++) {
            column = TReport.getColumnModel().getColumn(i);
            int width = 0;

            // 
            for (int j = 0; j < TReport.getRowCount(); j++) {
                Object value = TReport.getValueAt(j, i);
                if (value != null) {
                    width = Math.max(width, value.toString().length());
                }
            }

            // Menetapkan lebar kolom berdasarkan panjang teks terpanjang + sedikit padding
            column.setPreferredWidth(width * 15); // Lebar kolom disesuaikan
        }
        TReport.getColumnModel().getColumn(14).setCellRenderer(new CustomCellRenderer());
    }

    public void searchContent() {
        tabmode = new DefaultTableModel(null, ColumnContent);
        TReport.setModel(tabmode);
        TReport.setFillsViewportHeight(true);
        String search = FSearchCriteria.getText();

        //this.index = 1;
        //Object[] ColumnContent = {"CIF", "Nama Debitur", "Report Type", "Month", "Year", "Ficial Date", "Rating By", "Last Date Order", "Current Ratio", "Debt Equity Ratio", "Shareholder Equity", "Operating Margin", "Opr Earn", "Net Sales", "Debt Serv Coverage"};
        //Filter Month
        ArrayList<String> selectedOptions = new ArrayList<>();
        ArrayList<String> selectedOptions2 = new ArrayList<>();

        if (jCheckBox1.isSelected()) {
            selectedOptions.add("'1'");
        }

        if (jCheckBox2.isSelected()) {
            selectedOptions.add("'2'");
        }

        if (jCheckBox3.isSelected()) {
            selectedOptions.add("'3'");
        }

        if (jCheckBox4.isSelected()) {
            selectedOptions.add("'4'");
        }

        if (jCheckBox5.isSelected()) {
            selectedOptions.add("'5'");
        }

        if (jCheckBox6.isSelected()) {
            selectedOptions.add("'6'");
        }

        if (jCheckBox7.isSelected()) {
            selectedOptions.add("'7'");
        }

        if (jCheckBox8.isSelected()) {
            selectedOptions.add("'8'");
        }

        if (jCheckBox9.isSelected()) {
            selectedOptions.add("'9'");
        }

        if (jCheckBox10.isSelected()) {
            selectedOptions.add("'10'");
        }

        if (jCheckBox11.isSelected()) {
            selectedOptions.add("'11'");
        }

        if (jCheckBox12.isSelected()) {
            selectedOptions.add("'12'");
        }

        if (jCheckBox13.isSelected()) {
            selectedOptions2.add("'Audited'");
        }

        if (jCheckBox14.isSelected()) {
            selectedOptions2.add("'Un-Audited'");
        }

        if (selectedOptions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Thick at Least One Month");
            return;
        }

        if (selectedOptions2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Thick at Least One Type");
            return;

        } else if (FYear.getText()
                .equals("")) {
            JOptionPane.showMessageDialog(null, "Please Input Year");

        } else
            
            try {
            String sql
                    = "WITH RANKEDDATA AS (\n"
                    + "    SELECT \n"
                    + "        CRM.CU_REF,\n"
                    + "        MAX(CRM.AP_REGNO) AS AP_REGNO,\n"
                    + "        ROW_NUMBER() OVER (PARTITION BY CRM.CU_REF ORDER BY CRM.AP_REGNO DESC) AS APP,\n"
                    + "        C.CU_CIF AS CIF,\n"
                    + "        CC.CU_COMPNAME AS COMPNAME,\n"
                    + "        CRM.REPORTTYPE AS REPORTTYPE,\n"
                    + "        DATENAME(MONTH, MAX(CLM.IS_DATE_PERIODE)) AS RATINGMONTH,\n"
                    + "        YEAR(MAX(CLM.IS_DATE_PERIODE)) AS RATINGYEAR,\n"
                    + "        CAST(MAX(CLM.IS_DATE_PERIODE) AS DATE) AS FICIALDATE,\n"
                    + "        R.RATINGBY AS RATEBY,\n"
                    + "        CAST(MAX(CLM.IS_DATE_PERIODE) AS DATE) AS LASTDATEORDER,\n"
                    + "        CRM.CURRENT_RATIO AS CURRENTRATIO,\n"
                    + "        CRM.DEBT_EQUITY_RATIO AS DEBTRATIO,\n"
                    + "        CNM.BS_SHAREHOLDER_EQUITY AS BSEQUITY,\n"
                    + "        CRM.OPERATING_MARGIN AS OPRMARGIN,\n"
                    + "        CLM.IS_OPR_EARN AS OPREARN,\n"
                    + "        CLM.IS_NET_SALES AS NETSALES,\n"
                    + "        CRM.DEBT_SERV_COVERAGE AS DEBTSERV,\n"
                    + "        CNM.BS_SHT_TERM_LOAN AS SHORT_TERM_BORROWING,\n"
                    + "        CNM.BS_LG_TRM_LOAN_RECEIV AS LONG_TERM_BORROWING,\n"
                    + "        CLM.IS_CURRENCY AS CURRENCY,\n"
                    + "        CNM.BS_LAND AS LAND,\n"
                    + "        CNM.BS_BUILDINGS AS BUILDING,\n"
                    + "        CNM.BS_CASH_BANK AS CASH_DEPOSIT,\n"
                    + "        CNM.BS_DBST AS SHORT_TERM,\n"
                    + "        CNM.BS_LONGTERM_DEBT AS LONG_TERM\n"
                    + "    FROM CA_RATIO_MIDDLE CRM \n"
                    + "    LEFT JOIN CUSTOMER C ON CRM.CU_REF = C.CU_REF \n"
                    + "    LEFT JOIN CUST_COMPANY CC ON CRM.CU_REF = CC.CU_REF\n"
                    + "    LEFT JOIN RATINGFINALADJUSTMENT R ON CRM.AP_REGNO = R.AP_REGNO\n"
                    + "    LEFT JOIN RFRATINGCLASS R2 ON R.FINALRATING = R2.RATEID\n"
                    + "    LEFT JOIN CA_LABARUGI_MIDDLE CLM ON CRM.CU_REF = CLM.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CLM.AP_REGNO \n"
                    + "        AND CRM.DATE_PERIODE = CLM.IS_DATE_PERIODE \n"
                    + "    LEFT JOIN CA_NERACA_MIDDLE CNM ON CRM.CU_REF = CNM.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CNM.AP_REGNO \n"
                    + "        AND CRM.DATE_PERIODE = CNM.BS_DATE_PERIODE \n"
                    + "    LEFT JOIN CA_REKON_MIDDLE CRM2 ON CRM.CU_REF = CRM2.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CRM2.AP_REGNO \n"
                    + "        AND CRM.DATE_PERIODE = CRM2.DATE_PERIODE \n"
                    + "    LEFT JOIN CA_RATIO_BPR CPR ON CRM.CU_REF = CPR.CU_REF \n"
                    + "        AND CRM.AP_REGNO = CPR.AP_REGNO\n"
                    + "    LEFT JOIN APPLICATION A ON CRM.CU_REF = A.CU_REF \n"
                    + "        AND CRM.AP_REGNO = A.AP_REGNO \n"
                    + "    LEFT JOIN SCOREBCG_RESULTHISTORY SR ON CRM.AP_REGNO = SR.AP_REGNO\n"
                    + "    WHERE \n"
                    + "        C.CU_CIF IS NOT NULL AND \n"
                    + "        C.CU_CIF NOT LIKE '%C%' AND \n"
                    + "        CRM.REPORTTYPE IN (" + String.join(", ", selectedOptions2) + ") AND \n"
                    + "        MONTH(CLM.IS_DATE_PERIODE) IN (" + String.join(", ", selectedOptions) + ") AND \n"
                    + "        YEAR(CLM.IS_DATE_PERIODE) = '" + FYear.getText() + "' AND \n"
                    + "        CC.CU_COMPNAME LIKE '%" + search + "%' \n"
                    + "    GROUP BY \n"
                    + "        C.CU_CIF,\n"
                    + "        CC.CU_COMPNAME,\n"
                    + "        R.FINALRATING,\n"
                    + "        R2.RATEDESC,\n"
                    + "        R.ADJUSTRATING,\n"
                    + "        R.RATINGDATE,\n"
                    + "        R.RATINGBY,\n"
                    + "        A.AP_USERNAME,\n"
                    + "        CRM.CURRENT_RATIO,\n"
                    + "        CRM.DEBT_EQUITY_RATIO,\n"
                    + "        CNM.BS_SHAREHOLDER_EQUITY,\n"
                    + "        CLM.IS_NET_SALES,\n"
                    + "        CLM.IS_DATE_PERIODE,\n"
                    + "        CRM.OPERATING_MARGIN,\n"
                    + "        CLM.IS_OPR_EARN,\n"
                    + "        CRM.DEBT_SERV_COVERAGE,\n"
                    + "        CRM.LONGTERM_DEBT_TO_EQUITY,\n"
                    + "        CRM.REPORTTYPE,\n"
                    + "        CPR.LDR,\n"
                    + "        CRM.AP_REGNO,\n"
                    + "        CRM.CU_REF,\n"
                    + "        CNM.BS_SHT_TERM_LOAN,\n"
                    + "        CNM.BS_LG_TRM_LOAN_RECEIV,\n"
                    + "        CLM.IS_CURRENCY,\n"
                    + "        CNM.BS_LAND,\n"
                    + "        CNM.BS_BUILDINGS,\n"
                    + "        CNM.BS_CASH_BANK,\n"
                    + "        CNM.BS_DBST,\n"
                    + "        CNM.BS_LONGTERM_DEBT\n"
                    + ")\n"
                    + "SELECT * \n"
                    + "FROM RANKEDDATA \n"
                    + "WHERE APP = 1";

            stat = con.createStatement();
            res = stat.executeQuery(sql);

            while (res.next()) {
                TReport.getColumnModel().getColumn(14).setCellRenderer(new CustomCellRenderer());
                tabmode.addRow(new Object[]{
                    res.getString("CIF"),
                    res.getString("CompName"),
                    res.getString("ReportType"),
                    res.getString("RatingMonth"),
                    res.getString("RatingYear"),
                    res.getString("FicialDate"),
                    res.getString("RateBy"),
                    res.getString("LastDateOrder"),
                    res.getDouble("CurrentRatio"),
                    String.format("%.10f", res.getFloat("DebtRatio")),
                    decimalFormat.format(res.getDouble("BsEquity")),
                    String.format("%.10f", res.getDouble("OprMargin")),
                    decimalFormat.format(res.getDouble("OprEarn")),
                    decimalFormat.format(res.getDouble("NetSales")),
                    String.format("%,.10f", res.getDouble("DebtServ")),
                    res.getString("SHORT_TERM_BORROWING"),
                    res.getString("LONG_TERM_BORROWING"),
                    res.getString("CURRENCY"),
                    res.getString("LAND"),
                    res.getString("BUILDING"),
                    String.format("%.10f", res.getFloat("CASH_DEPOSIT"))

                /*res.getString("MappingCollect"),
                    /decimalFormat.format(res.getDouble("NetSales")),
                    decimalFormat.format(res.getDouble("OperatingIncome")),
                    decimalFormat.format(res.getDouble("NetIncome")),
                    decimalFormat.format(res.getDouble("ShareholdEquity")),
                    //decimalFormat.format(res.getDouble("CurrentRatio")),
                    String.format("%.2f", res.getFloat("CurrentRatio")),
                    String.format("%.2f", res.getDouble("EquityRatio")),
                    //decimalFormat2.format(res.getDouble("EquityRatio")),
                    res.getString("KAP"),
                    res.getDate("FS_DATE"),
                    res.getString("NUMBEROFMONTH"),
                    res.getString("YEARS"),
                    res.getString("REPORTTYPE"),*/
                });

            }
            //Set Display Laber & Other
            //FTotalRecord.setText(Integer.toString(TReport.getRowCount()));
            LReport.setText("REPORT: " + String.join(", ", selectedOptions) + " - " + FYear.getText() + " - " + String.join(", ", selectedOptions2) + " ");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fail Get Data" + e);
            System.out.println("--------- Query Fails" + e);

        }
        TableColumn column;
        for (int i = 0;
                i < TReport.getColumnCount();
                i++) {
            column = TReport.getColumnModel().getColumn(i);
            int width = 0;

            // 
            for (int j = 0; j < TReport.getRowCount(); j++) {
                Object value = TReport.getValueAt(j, i);
                if (value != null) {
                    width = Math.max(width, value.toString().length());
                }
            }

            // Menetapkan lebar kolom berdasarkan panjang teks terpanjang + sedikit padding
            column.setPreferredWidth(width * 15); // Lebar kolom disesuaikan
        }
        TReport.getColumnModel().getColumn(14).setCellRenderer(new CustomCellRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PMain = new javax.swing.JPanel();
        LMonth = new javax.swing.JLabel();
        LYears = new javax.swing.JLabel();
        BSearch = new javax.swing.JButton();
        LIcons = new javax.swing.JLabel();
        FYear = new javax.swing.JTextField();
        LType = new javax.swing.JLabel();
        LReport = new javax.swing.JLabel();
        LReportDes = new javax.swing.JLabel();
        BRefresh = new javax.swing.JButton();
        BExport = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jCheckBox13 = new javax.swing.JCheckBox();
        jCheckBox14 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        TReport = new javax.swing.JTable();
        jCheckBox15 = new javax.swing.JCheckBox();
        FSearchCriteria = new javax.swing.JTextField();
        LMonth1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFocusCycleRoot(false);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        PMain.setBackground(new java.awt.Color(255, 255, 255));
        PMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        LMonth.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LMonth.setText("MONTH");

        LYears.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LYears.setText("YEAR");

        BSearch.setBackground(new java.awt.Color(51, 255, 51));
        BSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BSearch.setForeground(new java.awt.Color(255, 255, 255));
        BSearch.setText("Search");
        BSearch.setFocusable(false);
        BSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSearchActionPerformed(evt);
            }
        });

        LIcons.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons.png"))); // NOI18N
        LIcons.setAlignmentY(0.0F);

        LType.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LType.setText("TYPE");

        LReport.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LReport.setText("REPORT :");

        LReportDes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        BRefresh.setBackground(new java.awt.Color(51, 255, 51));
        BRefresh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BRefresh.setForeground(new java.awt.Color(255, 255, 255));
        BRefresh.setText("Refresh");
        BRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRefreshActionPerformed(evt);
            }
        });

        BExport.setBackground(new java.awt.Color(51, 255, 51));
        BExport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BExport.setForeground(new java.awt.Color(255, 255, 255));
        BExport.setText("Export");
        BExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BExportActionPerformed(evt);
            }
        });

        jCheckBox1.setText("January");

        jCheckBox2.setText("February");

        jCheckBox3.setText("March");

        jCheckBox4.setText("April");

        jCheckBox5.setText("May");

        jCheckBox6.setText("June");

        jCheckBox7.setText("July");

        jCheckBox8.setText("August");

        jCheckBox9.setText("September");

        jCheckBox10.setText("October");

        jCheckBox11.setText("November");

        jCheckBox12.setText("December");

        jCheckBox13.setText("Audited");

        jCheckBox14.setText("Un-Audited");

        TReport.setAutoCreateRowSorter(true);
        TReport.setBorder(new com.formdev.flatlaf.ui.FlatBorder());
        TReport.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TReport.setForeground(new java.awt.Color(0, 0, 0));
        TReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TReport.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        TReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TReport.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        TReport.setDragEnabled(true);
        TReport.setFocusCycleRoot(true);
        TReport.setFocusTraversalPolicy(null);
        TReport.setFocusTraversalPolicyProvider(true);
        TReport.setGridColor(new java.awt.Color(0, 0, 0));
        TReport.setRowMargin(3);
        TReport.setSelectionBackground(new java.awt.Color(102, 255, 102));
        TReport.setSelectionForeground(new java.awt.Color(0, 0, 0));
        TReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TReport.setShowGrid(true);
        TReport.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(TReport);

        jCheckBox15.setText("Select / Unselected All");
        jCheckBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox15ActionPerformed(evt);
            }
        });

        FSearchCriteria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FSearchCriteriaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FSearchCriteriaKeyTyped(evt);
            }
        });

        LMonth1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LMonth1.setText("CUSTOMER NAME");

        javax.swing.GroupLayout PMainLayout = new javax.swing.GroupLayout(PMain);
        PMain.setLayout(PMainLayout);
        PMainLayout.setHorizontalGroup(
            PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PMainLayout.createSequentialGroup()
                .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PMainLayout.createSequentialGroup()
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LMonth)
                            .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox10)
                            .addComponent(jCheckBox11)
                            .addGroup(PMainLayout.createSequentialGroup()
                                .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PMainLayout.createSequentialGroup()
                                        .addComponent(jCheckBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LYears)
                                            .addComponent(FYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jCheckBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FSearchCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PMainLayout.createSequentialGroup()
                                        .addComponent(jCheckBox13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBox14))
                                    .addComponent(LType)
                                    .addComponent(LMonth1))))
                        .addGap(32, 32, 32)
                        .addComponent(LIcons))
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PMainLayout.createSequentialGroup()
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PMainLayout.createSequentialGroup()
                        .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox15))
                    .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PMainLayout.createSequentialGroup()
                                .addComponent(BSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PMainLayout.createSequentialGroup()
                                        .addGap(104, 104, 104)
                                        .addComponent(LReportDes))
                                    .addGroup(PMainLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BExport, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(LReport))))
                .addContainerGap(112, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        PMainLayout.setVerticalGroup(
            PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PMainLayout.createSequentialGroup()
                .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PMainLayout.createSequentialGroup()
                        .addComponent(LIcons, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PMainLayout.createSequentialGroup()
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LMonth)
                                .addComponent(LType))
                            .addComponent(LYears, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PMainLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox7)))
                            .addComponent(FYear, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox13)
                                .addComponent(jCheckBox14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox8)
                            .addComponent(LMonth1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox9)
                            .addComponent(FSearchCriteria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox4)
                            .addComponent(jCheckBox10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox5)
                            .addComponent(jCheckBox11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox6)
                            .addComponent(jCheckBox12)
                            .addComponent(jCheckBox15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LReportDes)
                        .addGap(18, 18, 18)
                        .addGroup(PMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BExport, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)))
                .addComponent(LReport)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSearchActionPerformed
        dataTableContent();
        FSearchCriteria.setEnabled(true);
        FSearchCriteria.setText("");
    }//GEN-LAST:event_BSearchActionPerformed

    private void BRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRefreshActionPerformed
        new Report_Rating().setVisible(true);
        dispose();
    }//GEN-LAST:event_BRefreshActionPerformed

    private void BExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BExportActionPerformed
        //Generate Export File to Excel
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet();

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < TReport.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(TReport.getColumnName(i));
                }

                for (int j = 0; j < TReport.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < TReport.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (TReport.getValueAt(j, k) != null) {
                            cell.setCellValue(TReport.getValueAt(j, k).toString());
                        }
                    }
                }

                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                JOptionPane.showMessageDialog(null, "Success Export");
                //openFile(saveFile.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Cancel Generated");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException io) {
            System.out.println(io);
        }


    }//GEN-LAST:event_BExportActionPerformed

    private void jCheckBox15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox15ActionPerformed
        if (jCheckBox15.isSelected()) {
            jCheckBox1.setSelected(true);
            jCheckBox2.setSelected(true);
            jCheckBox3.setSelected(true);
            jCheckBox4.setSelected(true);
            jCheckBox5.setSelected(true);
            jCheckBox6.setSelected(true);
            jCheckBox7.setSelected(true);
            jCheckBox8.setSelected(true);
            jCheckBox9.setSelected(true);
            jCheckBox10.setSelected(true);
            jCheckBox11.setSelected(true);
            jCheckBox12.setSelected(true);

        } else {
            jCheckBox1.setSelected(false);
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
            jCheckBox7.setSelected(false);
            jCheckBox8.setSelected(false);
            jCheckBox9.setSelected(false);
            jCheckBox10.setSelected(false);
            jCheckBox11.setSelected(false);
            jCheckBox12.setSelected(false);
        }
    }//GEN-LAST:event_jCheckBox15ActionPerformed

    private void FSearchCriteriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FSearchCriteriaKeyTyped
        searchContent();
    }//GEN-LAST:event_FSearchCriteriaKeyTyped

    private void FSearchCriteriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FSearchCriteriaKeyReleased
        searchContent();
    }//GEN-LAST:event_FSearchCriteriaKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
        }

        SwingUtilities.invokeLater(() -> {
            new Report_Rating().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BExport;
    private javax.swing.JButton BRefresh;
    private javax.swing.JButton BSearch;
    private javax.swing.JTextField FSearchCriteria;
    private javax.swing.JTextField FYear;
    private javax.swing.JLabel LIcons;
    private javax.swing.JLabel LMonth;
    private javax.swing.JLabel LMonth1;
    private javax.swing.JLabel LReport;
    private javax.swing.JLabel LReportDes;
    private javax.swing.JLabel LType;
    private javax.swing.JLabel LYears;
    private javax.swing.JPanel PMain;
    private javax.swing.JTable TReport;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
