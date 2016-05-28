/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scidb_manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.scidb.client.SciDBException;
import org.scidb.jdbc.Connection;

/**
 *
 * @author slottad
 */
public class SciDBArrayTreeCreator {
    public static DefaultTreeModel create(String name, Connection con) throws SQLException, SciDBException, IOException
    {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(name);
        QueryManager qm = QueryManager.getInstance();
        List<String> namespaces = qm.namespaces();
        
        for (String ns : namespaces) {
            DefaultMutableTreeNode nsNode = new DefaultMutableTreeNode(ns);
            top.add(nsNode);
            List<SciDBArray> arrays = qm.arrays(ns);
            for (SciDBArray arr : arrays) {
                DefaultMutableTreeNode arrNode = new DefaultMutableTreeNode(arr);
                nsNode.add(arrNode);
            }
        }
        return new DefaultTreeModel(top);
    }
}
