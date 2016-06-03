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

import java.awt.event.ItemEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.scidb.client.SciDBException;
import scidb_manager.HostsManager;
import scidb_manager.QueryManager;

/**
 *
 * @author slottad
 */
public class ChooseHostDialog extends javax.swing.JDialog {
    private DefaultListModel _clusters;
    private HostsManager _hostManager;
    
    /**
     * Creates new form ChooseHostDialog
     * @param parent
     * @param modal
     * @param props
     */
    public ChooseHostDialog(java.awt.Frame parent, boolean modal, Properties props) {
        super(parent, modal);

        _hostManager = new HostsManager(props);
        _clusters = _hostManager.getListModel();

        initComponents();
    }
    
    public String getHost() {
        return hostField.getText();
    }

    public Integer getPort() {
        return Integer.parseInt(portField.getText());
    }
    
    public String getHostPort() {
        String rv = new StringBuilder(hostField.getText()).append(":").append(portField.getText()).toString();
        return rv;
    }
    
    public String getUser() {
        String t = userField.getText().trim();
        if (t.isEmpty())
            return null;
        return t;
    }
    
    public String getPass() {
        String t = new String(passField.getPassword());
        if (t.isEmpty())
            return null;
        return t;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        clusterJList = new javax.swing.JList<>();
        hostField = new javax.swing.JTextField();
        HostLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        userField = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        passField = new javax.swing.JPasswordField();
        passwordLabel = new javax.swing.JLabel();
        showPassCheckBox = new javax.swing.JCheckBox();
        openButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        clusterJList.setModel(_clusters);
        jScrollPane1.setViewportView(clusterJList);

        hostField.setText("localhost");

        HostLabel.setText("Host");

        portField.setText("1239");

        portLabel.setText("Port");

        usernameLabel.setText("Username");

        passwordLabel.setText("Password");

        showPassCheckBox.setText("Show Passwords");
        showPassCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showPassCheckBoxItemStateChanged(evt);
            }
        });

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HostLabel)
                            .addComponent(portLabel)))
                    .addComponent(showPassCheckBox)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(userField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HostLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(portLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showPassCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(openButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        boolean success = QueryManager.initialize(getHost(), getPort(), getUser(), getPass());
        if (success) {
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Unable to connect to " + getHostPort());
        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void showPassCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_showPassCheckBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED)  {
            passField.setEchoChar((char) 0);
        } else {
            passField.setEchoChar('*');
        }
    }//GEN-LAST:event_showPassCheckBoxItemStateChanged

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        URI uri = _hostManager.add_host(getHost(), getPort(), getUser(), getPass());
        //_clusters = _hostManager.getListModel();
        if (uri != null) _clusters.addElement(uri); 
    }//GEN-LAST:event_saveButtonActionPerformed

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChooseHostDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChooseHostDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChooseHostDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChooseHostDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChooseHostDialog dialog = new ChooseHostDialog(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel HostLabel;
    private javax.swing.JList<String> clusterJList;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField hostField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
    private javax.swing.JPasswordField passField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox showPassCheckBox;
    private javax.swing.JTextField userField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
