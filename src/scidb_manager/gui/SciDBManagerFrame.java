/*
 * ===========================================================================
 * 
 *                            PUBLIC DOMAIN NOTICE
 *               National Center for Biotechnology Information
 * 
 *  This software/database is a "United States Government Work" under the
 *  terms of the United States Copyright Act.  It was written as part of
 *  the author's official duties as a United States Government employee and
 *  thus cannot be copyrighted.  This software/database is freely available
 *  to the public for use. The National Library of Medicine and the U.S.
 *  Government have not placed any restriction on its use or reproduction.
 * 
 *  Although all reasonable efforts have been taken to ensure the accuracy
 *  and reliability of the software and data, the NLM and the U.S.
 *  Government do not and cannot warrant the performance or results that
 *  may be obtained by using this software or data. The NLM and the U.S.
 *  Government disclaim all warranties, express or implied, including
 *  warranties of performance, merchantability or fitness for any particular
 *  purpose.
 * 
 *  Please cite the author in any work or product based on this material.
 * 
 * ===========================================================================
 */
package scidb_manager.gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultTreeModel;
import org.scidb.client.SciDBException;
import org.scidb.jdbc.Connection;
import org.scidb.jdbc.IStatementWrapper;
import scidb_manager.ResultTableModel;
import scidb_manager.SciDBArrayTreeCreator;

/**
 *
 * @author slottad
 */

public class SciDBManagerFrame extends javax.swing.JFrame {

    private final ResultTableModel _arrayResults;
    private Properties _properties;
    
    /**
     * Creates new form ManagerJFrame
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws org.scidb.client.SciDBException
     */
    public SciDBManagerFrame() throws SQLException, IOException, SciDBException {
        load_properties();
        ChooseHostDialog hostDialog = new ChooseHostDialog(this, true, _properties);
        hostDialog.setVisible(true);

        String iqueryHost = hostDialog.getHost();
        Integer iqueryPort = hostDialog.getPort();
        //String iqueryHost = "localhost";
        //String iqueryHost = "scidb-vm";
        //Integer iqueryPort = 1239;
        Connection conn = new Connection(iqueryHost, iqueryPort);
        conn.getSciDBConnection().startNewClient("slottad", "bigsecret");

        ArrayTreeModel = SciDBArrayTreeCreator.create(iqueryHost, conn);
        
        Statement st = conn.createStatement();
        IStatementWrapper stWrap = st.unwrap(IStatementWrapper.class);
        stWrap.setAfl(true);
        //create array A<a:string>[x=0:2,3,0, y=0:2,3,0];
        //select * into A from array(A, '[["a","b","c"]["d","e","f"]["123","456","789"]]');
//        ResultSet res = st.executeQuery("select * from array(<a:string>[x=0:2,3,0, y=0:2,3,0], '[[\"a\",\"b\",\"c\"][\"d\",\"e\",\"f\"][\"123\",\"456\",\"789\"]]')");
        ResultSet res = st.executeQuery("list('arrays')");

        _arrayResults = new ResultTableModel(res);
        
        initComponents();
    }

    private void load_properties() throws IOException {
        Properties defaultProps = new Properties();
        InputStream in = this.getClass().getResourceAsStream("/settings/defaults.properties");
        defaultProps.load(in);
        _properties = new Properties(defaultProps);
        Path user = Paths.get(System.getProperty("user.home"),".scidb_manager");
        if (Files.isRegularFile(user) & Files.isReadable(user)) {
            in = new FileInputStream(user.toString());
            _properties.load(in);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        QueryEditorScrollPane = new javax.swing.JScrollPane();
        QueryTextEditor = new javax.swing.JTextArea();
        ResultsTableScrollPane = new javax.swing.JScrollPane();
        ResultsTable = new javax.swing.JTable();
        jSplitPane3 = new javax.swing.JSplitPane();
        ArrayTreeScrollPane = new javax.swing.JScrollPane();
        ArrayTree = new javax.swing.JTree();
        ArrayPropertiesScrollPane = new javax.swing.JScrollPane();
        ArrayProperties = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(150);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        QueryTextEditor.setColumns(20);
        QueryTextEditor.setRows(5);
        QueryEditorScrollPane.setViewportView(QueryTextEditor);

        jSplitPane2.setTopComponent(QueryEditorScrollPane);

        ResultsTable.setModel(_arrayResults);
        ResultsTableScrollPane.setViewportView(ResultsTable);

        jSplitPane2.setRightComponent(ResultsTableScrollPane);

        jSplitPane1.setRightComponent(jSplitPane2);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        ArrayTree.setModel(ArrayTreeModel);
        ArrayTreeScrollPane.setViewportView(ArrayTree);

        jSplitPane3.setTopComponent(ArrayTreeScrollPane);

        ArrayProperties.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ArrayPropertiesScrollPane.setViewportView(ArrayProperties);

        jSplitPane3.setRightComponent(ArrayPropertiesScrollPane);

        jSplitPane1.setLeftComponent(jSplitPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            // Set System L&F
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SciDBManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SciDBManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SciDBManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SciDBManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new SciDBManagerFrame().setVisible(true);
                } catch (SQLException | IOException | SciDBException ex) {
                    Logger.getLogger(SciDBManagerFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    private DefaultTreeModel ArrayTreeModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ArrayProperties;
    private javax.swing.JScrollPane ArrayPropertiesScrollPane;
    private javax.swing.JTree ArrayTree;
    private javax.swing.JScrollPane ArrayTreeScrollPane;
    private javax.swing.JScrollPane QueryEditorScrollPane;
    private javax.swing.JTextArea QueryTextEditor;
    private javax.swing.JTable ResultsTable;
    private javax.swing.JScrollPane ResultsTableScrollPane;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    // End of variables declaration//GEN-END:variables
}
