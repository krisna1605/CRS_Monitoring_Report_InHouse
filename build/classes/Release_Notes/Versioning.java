import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Mon May 26 13:56:12 GMT+07:00 2025
 */



/**
 * @author kris4721
 */
public class Versioning extends JFrame {
    public Versioning() {
	initComponents();
    }

    private void initComponents() {
	// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
	// Generated using JFormDesigner Evaluation license - unknown
	jPanel1 = new JPanel();
	jScrollPane1 = new JScrollPane();
	jTextArea1 = new JTextArea();

	//======== this ========
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	Container contentPane = getContentPane();

	//======== jPanel1 ========
	{
	    jPanel1.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new
	    javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax
	    . swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java
	    . awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt
	    . Color .red ) ,jPanel1. getBorder () ) ); jPanel1. addPropertyChangeListener( new java. beans .
	    PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062order" .
	    equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );

	    //======== jScrollPane1 ========
	    {

		//---- jTextArea1 ----
		jTextArea1.setEditable(false);
		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jTextArea1.setText("/***************************************************************************\\\n * Application/Interface to See Rating Report & Generated to Excel         \n *                                                                          \n * @author kris4721\n * \n * \n * 24-02-2025 \n * * Add new thick box to select/unselected all month period\n * * Add new text field to search based on filter \n *   - Event key\n * * Change logic query remove duplicate by eliminate join\n * \n * \n * \n * ************************************************************************/");
		jScrollPane1.setViewportView(jTextArea1);
	    }

	    GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	    jPanel1.setLayout(jPanel1Layout);
	    jPanel1Layout.setHorizontalGroup(
		jPanel1Layout.createParallelGroup()
		    .addGroup(jPanel1Layout.createSequentialGroup()
			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 757, GroupLayout.PREFERRED_SIZE)
			.addGap(0, 0, Short.MAX_VALUE))
	    );
	    jPanel1Layout.setVerticalGroup(
		jPanel1Layout.createParallelGroup()
		    .addGroup(jPanel1Layout.createSequentialGroup()
			.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
			.addContainerGap())
	    );
	}

	GroupLayout contentPaneLayout = new GroupLayout(contentPane);
	contentPane.setLayout(contentPaneLayout);
	contentPaneLayout.setHorizontalGroup(
	    contentPaneLayout.createParallelGroup()
		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	);
	contentPaneLayout.setVerticalGroup(
	    contentPaneLayout.createParallelGroup()
		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	);
	pack();
	setLocationRelativeTo(getOwner());
	// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
