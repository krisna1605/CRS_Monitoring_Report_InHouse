package Main_Menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
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
    Object[] ColumnContent = {"CIF", "Nama Debitur", "Report Type", "Month", "Year", "Ficial Date", "Rating By", "Last Date Order", "Current Ratio", "Debt Equity Ratio", "Shareholder Equity", "Operating Margin", "Opr Earn", "Net Sales", "Debt Serv Coverage"};
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
            String sql = "WITH RankedData AS ("
                    + "SELECT "
                    + "    CRM.CU_REF, "
                    + "    MAX(CRM.AP_REGNO) AS AP_REGNO, "
                    + "    ROW_NUMBER() OVER (PARTITION BY CRM.CU_REF ORDER BY CRM.AP_REGNO DESC) AS APP, "
                    + "    c.CU_CIF AS CIF, "
                    + "    cc.CU_COMPNAME AS CompName, "
                    + "    CRM.REPORTTYPE AS ReportType, "
                    + "    DATENAME(MONTH, MAX(CLM.IS_DATE_PERIODE)) AS RatingMonth, "
                    + "    YEAR(MAX(CLM.IS_DATE_PERIODE)) AS RatingYear, "
                    + "    CAST(MAX(CLM.IS_DATE_PERIODE) AS DATE) AS FicialDate, "
                    + "    r.RATINGBY AS RateBy, "
                    + "    CAST(MAX(clm.IS_DATE_PERIODE) AS DATE) AS LastDateOrder, "
                    + "    CRM.CURRENT_RATIO AS CurrentRatio, "
                    + "    CRM.DEBT_EQUITY_RATIO AS DebtRatio, "
                    + "    cnm.BS_SHAREHOLDER_EQUITY AS BsEquity, "
                    + "    CRM.OPERATING_MARGIN AS OprMargin, "
                    + "    clm.IS_OPR_EARN AS OprEarn, "
                    + "    clm.IS_NET_SALES AS NetSales, "
                    + "    CRM.DEBT_SERV_COVERAGE AS DebtServ, "
                    + "    cpr.LDR "
                    + "FROM "
                    + "    CA_RATIO_MIDDLE CRM "
                    + "    LEFT JOIN CUSTOMER c ON CRM.CU_REF = c.CU_REF "
                    + "    LEFT JOIN CUST_COMPANY cc ON CRM.CU_REF = cc.CU_REF "
                    + "    LEFT JOIN RATINGFINALADJUSTMENT r ON CRM.AP_REGNO = r.AP_REGNO "
                    + "    LEFT JOIN RFRATINGCLASS r2 ON r.FINALRATING = r2.RATEID "
                    + "    LEFT JOIN CA_LABARUGI_MIDDLE clm ON CRM.CU_REF = clm.CU_REF "
                    + "        AND CRM.AP_REGNO = clm.AP_REGNO "
                    + "        AND CRM.DATE_PERIODE = clm.IS_DATE_PERIODE "
                    + "    LEFT JOIN CA_NERACA_MIDDLE cnm ON CRM.CU_REF = cnm.CU_REF "
                    + "        AND CRM.AP_REGNO = cnm.AP_REGNO "
                    + "        AND CRM.DATE_PERIODE = cnm.BS_DATE_PERIODE "
                    + "    LEFT JOIN CA_REKON_MIDDLE CRM2 ON CRM.CU_REF = CRM2.CU_REF "
                    + "        AND CRM.AP_REGNO = CRM2.AP_REGNO "
                    + "        AND CRM.DATE_PERIODE = CRM2.DATE_PERIODE "
                    + "    LEFT JOIN CA_RATIO_BPR cpr ON CRM.CU_REF = cpr.CU_REF "
                    + "        AND CRM.AP_REGNO = cpr.AP_REGNO "
                    + "    LEFT JOIN APPLICATION a ON CRM.CU_REF = a.CU_REF "
                    + "        AND CRM.AP_REGNO = a.AP_REGNO "
                    + "    LEFT JOIN SCOREBCG_RESULTHISTORY sr ON CRM.AP_REGNO = sr.AP_REGNO "
                    + "WHERE "
                    + "    c.CU_CIF IS NOT NULL "
                    + "    AND C.CU_CIF NOT LIKE '%C%' "
                    + "    AND CRM.REPORTTYPE IN (" + String.join(", ", selectedOptions2) + ") "
                    + "    AND MONTH (clm.IS_DATE_PERIODE) IN (" + String.join(", ", selectedOptions) + ") "
                    + "    AND YEAR(clm.IS_DATE_PERIODE) = " + "'" + FYear.getText() + "'"
                    + "GROUP BY "
                    + "    c.CU_CIF, "
                    + "    cc.CU_COMPNAME, "
                    + "    r.FINALRATING, "
                    + "    r2.RATEDESC, "
                    + "    r.ADJUSTRATING, "
                    + "    r.RATINGDATE, "
                    + "    r.RATINGBY, "
                    + "    a.AP_USERNAME, "
                    + "    CRM.CURRENT_RATIO, "
                    + "    CRM.DEBT_EQUITY_RATIO, "
                    + "    cnm.BS_SHAREHOLDER_EQUITY, "
                    + "    clm.IS_NET_SALES, "
                    + "    clm.IS_DATE_PERIODE, "
                    + "    CRM.OPERATING_MARGIN, "
                    + "    clm.IS_OPR_EARN, "
                    + "    CRM.DEBT_SERV_COVERAGE, "
                    + "    CRM.LONGTERM_DEBT_TO_EQUITY, "
                    + "    CRM.REPORTTYPE, "
                    + "    cpr.LDR, "
                    + "    CRM.AP_REGNO, "
                    + "    CRM.CU_REF "
                    + ") "
                    + "SELECT * "
                    + "FROM RankedData "
                    + "WHERE APP = 1;";

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
                    String.format("%,.10f", res.getDouble("DebtServ"))

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
        Object[] ColumnContent = {"CIF", "Nama Debitur", "Report Type", "Month", "Year", "Ficial Date", "Rating By", "Last Date Order", "Current Ratio", "Debt Equity Ratio", "Shareholder Equity", "Operating Margin", "Opr Earn", "Net Sales", "Debt Serv Coverage"};

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
            String sql = "WITH RankedData AS ("
                    + "SELECT "
                    + "    CRM.CU_REF, "
                    + "    MAX(CRM.AP_REGNO) AS AP_REGNO, "
                    + "    ROW_NUMBER() OVER (PARTITION BY CRM.CU_REF ORDER BY CRM.AP_REGNO DESC) AS APP, "
                    + "    c.CU_CIF AS CIF, "
                    + "    cc.CU_COMPNAME AS CompName, "
                    + "    CRM.REPORTTYPE AS ReportType, "
                    + "    DATENAME(MONTH, MAX(CLM.IS_DATE_PERIODE)) AS RatingMonth, "
                    + "    YEAR(MAX(CLM.IS_DATE_PERIODE)) AS RatingYear, "
                    + "    CAST(MAX(CLM.IS_DATE_PERIODE) AS DATE) AS FicialDate, "
                    + "    r.RATINGBY AS RateBy, "
                    + "    CAST(MAX(clm.IS_DATE_PERIODE) AS DATE) AS LastDateOrder, "
                    + "    CRM.CURRENT_RATIO AS CurrentRatio, "
                    + "    CRM.DEBT_EQUITY_RATIO AS DebtRatio, "
                    + "    cnm.BS_SHAREHOLDER_EQUITY AS BsEquity, "
                    + "    CRM.OPERATING_MARGIN AS OprMargin, "
                    + "    clm.IS_OPR_EARN AS OprEarn, "
                    + "    clm.IS_NET_SALES AS NetSales, "
                    + "    CRM.DEBT_SERV_COVERAGE AS DebtServ, "
                    + "    cpr.LDR "
                    + "FROM "
                    + "    CA_RATIO_MIDDLE CRM "
                    + "    LEFT JOIN CUSTOMER c ON CRM.CU_REF = c.CU_REF "
                    + "    LEFT JOIN CUST_COMPANY cc ON CRM.CU_REF = cc.CU_REF "
                    + "    LEFT JOIN RATINGFINALADJUSTMENT r ON CRM.AP_REGNO = r.AP_REGNO "
                    + "    LEFT JOIN RFRATINGCLASS r2 ON r.FINALRATING = r2.RATEID "
                    + "    LEFT JOIN CA_LABARUGI_MIDDLE clm ON CRM.CU_REF = clm.CU_REF "
                    + "        AND CRM.AP_REGNO = clm.AP_REGNO "
                    + "        AND CRM.DATE_PERIODE = clm.IS_DATE_PERIODE "
                    + "    LEFT JOIN CA_NERACA_MIDDLE cnm ON CRM.CU_REF = cnm.CU_REF "
                    + "        AND CRM.AP_REGNO = cnm.AP_REGNO "
                    + "        AND CRM.DATE_PERIODE = cnm.BS_DATE_PERIODE "
                    + "    LEFT JOIN CA_REKON_MIDDLE CRM2 ON CRM.CU_REF = CRM2.CU_REF "
                    + "        AND CRM.AP_REGNO = CRM2.AP_REGNO "
                    + "        AND CRM.DATE_PERIODE = CRM2.DATE_PERIODE "
                    + "    LEFT JOIN CA_RATIO_BPR cpr ON CRM.CU_REF = cpr.CU_REF "
                    + "        AND CRM.AP_REGNO = cpr.AP_REGNO "
                    + "    LEFT JOIN APPLICATION a ON CRM.CU_REF = a.CU_REF "
                    + "        AND CRM.AP_REGNO = a.AP_REGNO "
                    + "    LEFT JOIN SCOREBCG_RESULTHISTORY sr ON CRM.AP_REGNO = sr.AP_REGNO "
                    + "WHERE "
                    + "    c.CU_CIF IS NOT NULL "
                    + "    AND C.CU_CIF NOT LIKE '%C%' "
                    + "    AND CRM.REPORTTYPE IN (" + String.join(", ", selectedOptions2) + ") "
                    + "    AND MONTH (clm.IS_DATE_PERIODE) IN (" + String.join(", ", selectedOptions) + ") "
                    + "    AND cc.CU_COMPNAME LIKE '%" + search + "%'"
                    + "    AND YEAR(clm.IS_DATE_PERIODE) = " + "'" + FYear.getText() + "'"
                    + "GROUP BY "
                    + "    c.CU_CIF, "
                    + "    cc.CU_COMPNAME, "
                    + "    r.FINALRATING, "
                    + "    r2.RATEDESC, "
                    + "    r.ADJUSTRATING, "
                    + "    r.RATINGDATE, "
                    + "    r.RATINGBY, "
                    + "    a.AP_USERNAME, "
                    + "    CRM.CURRENT_RATIO, "
                    + "    CRM.DEBT_EQUITY_RATIO, "
                    + "    cnm.BS_SHAREHOLDER_EQUITY, "
                    + "    clm.IS_NET_SALES, "
                    + "    clm.IS_DATE_PERIODE, "
                    + "    CRM.OPERATING_MARGIN, "
                    + "    clm.IS_OPR_EARN, "
                    + "    CRM.DEBT_SERV_COVERAGE, "
                    + "    CRM.LONGTERM_DEBT_TO_EQUITY, "
                    + "    CRM.REPORTTYPE, "
                    + "    cpr.LDR, "
                    + "    CRM.AP_REGNO, "
                    + "    CRM.CU_REF "
                    + ") "
                    + "SELECT * "
                    + "FROM RankedData "
                    + "WHERE APP = 1;";

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
                    String.format("%,.10f", res.getDouble("DebtServ"))

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
    // Generated using JFormDesigner Evaluation license - unknown
    private void initComponents() {
	PMain = new JPanel();
	LMonth = new JLabel();
	LYears = new JLabel();
	BSearch = new JButton();
	LIcons = new JLabel();
	FYear = new JTextField();
	LType = new JLabel();
	LReport = new JLabel();
	LReportDes = new JLabel();
	BRefresh = new JButton();
	BExport = new JButton();
	LCopyright = new JLabel();
	jCheckBox1 = new JCheckBox();
	jCheckBox2 = new JCheckBox();
	jCheckBox3 = new JCheckBox();
	jCheckBox4 = new JCheckBox();
	jCheckBox5 = new JCheckBox();
	jCheckBox6 = new JCheckBox();
	jCheckBox7 = new JCheckBox();
	jCheckBox8 = new JCheckBox();
	jCheckBox9 = new JCheckBox();
	jCheckBox10 = new JCheckBox();
	jCheckBox11 = new JCheckBox();
	jCheckBox12 = new JCheckBox();
	jCheckBox13 = new JCheckBox();
	jCheckBox14 = new JCheckBox();
	jScrollPane2 = new JScrollPane();
	TReport = new JTable();
	jCheckBox15 = new JCheckBox();
	FSearchCriteria = new JTextField();
	LMonth1 = new JLabel();

	//======== this ========
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setAutoRequestFocus(false);
	setCursor(null);
	setFocusCycleRoot(false);
	Container contentPane = getContentPane();

	//======== PMain ========
	{
	    PMain.setBorder(new EmptyBorder(1, 1, 1, 1));
	    PMain.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
	    . EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax
	    . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,
	    12 ), java. awt. Color. red) ,PMain. getBorder( )) ); PMain. addPropertyChangeListener (new java. beans
	    . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .
	    getPropertyName () )) throw new RuntimeException( ); }} );

	    //---- LMonth ----
	    LMonth.setText("MONTH");

	    //---- LYears ----
	    LYears.setText("YEAR");

	    //---- BSearch ----
	    BSearch.setText("Search");
	    BSearch.setFocusable(false);
	    BSearch.addActionListener(e -> BSearchActionPerformed(e));

	    //---- LIcons ----
	    LIcons.setIcon(new ImageIcon(getClass().getResource("/Icons/icons.png")));
	    LIcons.setAlignmentY(0.0F);

	    //---- LType ----
	    LType.setText("TYPE");

	    //---- LReport ----
	    LReport.setText("REPORT :");

	    //---- BRefresh ----
	    BRefresh.setText("Refresh");
	    BRefresh.addActionListener(e -> BRefreshActionPerformed(e));

	    //---- BExport ----
	    BExport.setText("Export");
	    BExport.addActionListener(e -> BExportActionPerformed(e));

	    //---- LCopyright ----
	    LCopyright.setText("Credit By: Krisna Arisandi IT-System");

	    //---- jCheckBox1 ----
	    jCheckBox1.setText("January");

	    //---- jCheckBox2 ----
	    jCheckBox2.setText("February");

	    //---- jCheckBox3 ----
	    jCheckBox3.setText("March");

	    //---- jCheckBox4 ----
	    jCheckBox4.setText("April");

	    //---- jCheckBox5 ----
	    jCheckBox5.setText("May");

	    //---- jCheckBox6 ----
	    jCheckBox6.setText("June");

	    //---- jCheckBox7 ----
	    jCheckBox7.setText("July");

	    //---- jCheckBox8 ----
	    jCheckBox8.setText("August");

	    //---- jCheckBox9 ----
	    jCheckBox9.setText("September");

	    //---- jCheckBox10 ----
	    jCheckBox10.setText("October");

	    //---- jCheckBox11 ----
	    jCheckBox11.setText("November");

	    //---- jCheckBox12 ----
	    jCheckBox12.setText("December");

	    //---- jCheckBox13 ----
	    jCheckBox13.setText("Audited");

	    //---- jCheckBox14 ----
	    jCheckBox14.setText("Un-Audited");

	    //======== jScrollPane2 ========
	    {

		//---- TReport ----
		TReport.setAutoCreateRowSorter(true);
		TReport.setModel(new DefaultTableModel(
		    new Object[][] {
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
		    },
		    new String[] {
			"Title 1", "Title 2", "Title 3", "Title 4"
		    }
		) {
		    boolean[] columnEditable = new boolean[] {
			false, false, false, false
		    };
		    @Override
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnEditable[columnIndex];
		    }
		});
		TReport.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TReport.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		TReport.setDragEnabled(true);
		TReport.setFocusCycleRoot(true);
		TReport.setFocusTraversalPolicy(null);
		TReport.setFocusTraversalPolicyProvider(true);
		TReport.setGridColor(Color.black);
		TReport.setRowMargin(3);
		TReport.setSelectionBackground(new Color(0x66ff66));
		TReport.setSelectionForeground(Color.black);
		TReport.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TReport.setShowGrid(true);
		TReport.setVerifyInputWhenFocusTarget(false);
		jScrollPane2.setViewportView(TReport);
	    }

	    //---- jCheckBox15 ----
	    jCheckBox15.setText("Select / Unselected All");
	    jCheckBox15.addActionListener(e -> jCheckBox15ActionPerformed(e));

	    //---- FSearchCriteria ----
	    FSearchCriteria.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
		    FSearchCriteriaKeyReleased(e);
		}
		@Override
		public void keyTyped(KeyEvent e) {
		    FSearchCriteriaKeyTyped(e);
		}
	    });

	    //---- LMonth1 ----
	    LMonth1.setText("CUSTOMER NAME");

	    GroupLayout PMainLayout = new GroupLayout(PMain);
	    PMain.setLayout(PMainLayout);
	    PMainLayout.setHorizontalGroup(
		PMainLayout.createParallelGroup()
		    .addGroup(PMainLayout.createSequentialGroup()
			.addGroup(PMainLayout.createParallelGroup()
			    .addGroup(PMainLayout.createSequentialGroup()
				.addGroup(PMainLayout.createParallelGroup()
				    .addComponent(LMonth)
				    .addComponent(jCheckBox3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jCheckBox4, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
				.addGap(6, 6, 6)
				.addGroup(PMainLayout.createParallelGroup()
				    .addComponent(jCheckBox10)
				    .addComponent(jCheckBox11)
				    .addGroup(PMainLayout.createSequentialGroup()
					.addGroup(PMainLayout.createParallelGroup()
					    .addGroup(PMainLayout.createSequentialGroup()
						.addComponent(jCheckBox7, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addGap(30, 30, 30)
						.addGroup(PMainLayout.createParallelGroup()
						    .addComponent(LYears)
						    .addComponent(FYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					    .addComponent(jCheckBox9, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addGap(22, 22, 22)
					.addGroup(PMainLayout.createParallelGroup()
					    .addComponent(FSearchCriteria, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
					    .addGroup(PMainLayout.createSequentialGroup()
						.addComponent(jCheckBox13)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jCheckBox14))
					    .addComponent(LType)
					    .addComponent(LMonth1))))
				.addGroup(PMainLayout.createParallelGroup()
				    .addGroup(PMainLayout.createSequentialGroup()
					.addGap(32, 32, 32)
					.addComponent(LIcons))
				    .addGroup(PMainLayout.createSequentialGroup()
					.addGap(123, 123, 123)
					.addComponent(LCopyright))))
			    .addComponent(jCheckBox1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
			    .addGroup(PMainLayout.createSequentialGroup()
				.addComponent(jCheckBox2, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jCheckBox8, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
			    .addGroup(PMainLayout.createSequentialGroup()
				.addComponent(jCheckBox6, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jCheckBox12)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jCheckBox15))
			    .addComponent(jCheckBox5, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
			    .addGroup(PMainLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(PMainLayout.createParallelGroup()
				    .addGroup(PMainLayout.createSequentialGroup()
					.addComponent(BSearch, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addGroup(PMainLayout.createParallelGroup()
					    .addGroup(PMainLayout.createSequentialGroup()
						.addGap(104, 104, 104)
						.addComponent(LReportDes))
					    .addGroup(PMainLayout.createSequentialGroup()
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(BRefresh, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(BExport, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))))
				    .addComponent(LReport))))
			.addContainerGap(112, Short.MAX_VALUE))
		    .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING)
	    );
	    PMainLayout.setVerticalGroup(
		PMainLayout.createParallelGroup()
		    .addGroup(PMainLayout.createSequentialGroup()
			.addGroup(PMainLayout.createParallelGroup()
			    .addGroup(PMainLayout.createSequentialGroup()
				.addComponent(LIcons, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(LCopyright)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			    .addGroup(PMainLayout.createSequentialGroup()
				.addGroup(PMainLayout.createParallelGroup()
				    .addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(LMonth)
					.addComponent(LType))
				    .addComponent(LYears, GroupLayout.Alignment.TRAILING))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(PMainLayout.createParallelGroup()
				    .addGroup(PMainLayout.createSequentialGroup()
					.addGap(7, 7, 7)
					.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(jCheckBox1)
					    .addComponent(jCheckBox7)))
				    .addComponent(FYear, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
				    .addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jCheckBox13)
					.addComponent(jCheckBox14)))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jCheckBox2)
				    .addComponent(jCheckBox8)
				    .addComponent(LMonth1))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jCheckBox3)
				    .addComponent(jCheckBox9)
				    .addComponent(FSearchCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jCheckBox4)
				    .addComponent(jCheckBox10))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jCheckBox5)
				    .addComponent(jCheckBox11))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jCheckBox6)
				    .addComponent(jCheckBox12)
				    .addComponent(jCheckBox15))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(LReportDes)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(PMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(BSearch, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
				    .addComponent(BExport, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
				    .addComponent(BRefresh, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
				.addGap(23, 23, 23)))
			.addComponent(LReport)
			.addGap(18, 18, 18)
			.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
			.addGap(30, 30, 30))
	    );
	}

	GroupLayout contentPaneLayout = new GroupLayout(contentPane);
	contentPane.setLayout(contentPaneLayout);
	contentPaneLayout.setHorizontalGroup(
	    contentPaneLayout.createParallelGroup()
		.addGroup(contentPaneLayout.createSequentialGroup()
		    .addComponent(PMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    .addContainerGap())
	);
	contentPaneLayout.setVerticalGroup(
	    contentPaneLayout.createParallelGroup()
		.addGroup(contentPaneLayout.createSequentialGroup()
		    .addComponent(PMain, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    .addGap(0, 4, Short.MAX_VALUE))
	);
	pack();
	setLocationRelativeTo(getOwner());
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
    public static void main(String args[]) {
        try {
            // Untuk tema terang (Light)
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            // Untuk tema gelap (Dark)
            // UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Report_Rating().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel PMain;
    private JLabel LMonth;
    private JLabel LYears;
    private JButton BSearch;
    private JLabel LIcons;
    private JTextField FYear;
    private JLabel LType;
    private JLabel LReport;
    private JLabel LReportDes;
    private JButton BRefresh;
    private JButton BExport;
    private JLabel LCopyright;
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBox3;
    private JCheckBox jCheckBox4;
    private JCheckBox jCheckBox5;
    private JCheckBox jCheckBox6;
    private JCheckBox jCheckBox7;
    private JCheckBox jCheckBox8;
    private JCheckBox jCheckBox9;
    private JCheckBox jCheckBox10;
    private JCheckBox jCheckBox11;
    private JCheckBox jCheckBox12;
    private JCheckBox jCheckBox13;
    private JCheckBox jCheckBox14;
    private JScrollPane jScrollPane2;
    private JTable TReport;
    private JCheckBox jCheckBox15;
    private JTextField FSearchCriteria;
    private JLabel LMonth1;
    // End of variables declaration//GEN-END:variables

}
