import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import javax.swing.table.*;
/*
 * Created by JFormDesigner on Mon May 26 13:56:12 GMT+07:00 2025
 */



/**
 * @author kris4721
 */
public class Report_Rating extends JFrame {
    public Report_Rating() {
	initComponents();
    }

    private void BSearchActionPerformed(ActionEvent e) {
	// TODO add your code here
    }

    private void BRefreshActionPerformed(ActionEvent e) {
	// TODO add your code here
    }

    private void BExportActionPerformed(ActionEvent e) {
	// TODO add your code here
    }

    private void jCheckBox15ActionPerformed(ActionEvent e) {
	// TODO add your code here
    }

    private void FSearchCriteriaKeyReleased(KeyEvent e) {
	// TODO add your code here
    }

    private void FSearchCriteriaKeyTyped(KeyEvent e) {
	// TODO add your code here
    }

    private void initComponents() {
	// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
	// Generated using JFormDesigner Evaluation license - unknown
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
	    PMain.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.
	    swing.border.EmptyBorder(0,0,0,0), "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e",javax.swing.border
	    .TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069al\u006fg"
	    ,java.awt.Font.BOLD,12),java.awt.Color.red),PMain. getBorder
	    ()));PMain. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java
	    .beans.PropertyChangeEvent e){if("\u0062or\u0064er".equals(e.getPropertyName()))throw new RuntimeException
	    ();}});

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
	// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
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
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
