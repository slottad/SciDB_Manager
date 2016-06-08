/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scidb_manager;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author slottad
 */
public class SciDBArray {
    private final String  _name;
    private final long    _uaid;
    private final long    _aid;
    private final String  _schema;
    private final boolean _availability;
    private final boolean _temporary;
    private DefaultMutableTreeNode _root;

    SciDBArray(ResultSet row) throws SQLException {
        _name = row.getString("name");
        _uaid = row.getLong("uaid");
        _aid = row.getLong("aid");
        _schema = row.getString("schema");
        _availability = row.getBoolean("availability");
        _temporary = row.getBoolean("temporary");
        schema_parse();
    }
    
    @Override
    public String toString() {
        return _name;
    }
    
    public void schema_parse() {
        Pattern brackets = Pattern.compile("[<\\[\\]>]+\\s*[<\\[\\]>]*");
        String[] schemaEntries = brackets.split(_schema);
        String name = schemaEntries[0];
        String attributeStr = schemaEntries[1];
        String dimensionStr = schemaEntries[2];

        _root = new DefaultMutableTreeNode(name);
        
        DefaultMutableTreeNode attributesBranch = new DefaultMutableTreeNode("Attributes");        
        String[] attributes = attributeStr.split(",");                
        for (String att: attributes) {
            DefaultMutableTreeNode attNode = new DefaultMutableTreeNode(att);
            attributesBranch.add(attNode);
        }
        _root.add(attributesBranch);
        
        DefaultMutableTreeNode dimensionsBranch = new DefaultMutableTreeNode("Dimensions");        
        Pattern dimsep = Pattern.compile("[^,]+,[^,]+,[^,]+,{0,1}");
        Matcher m = dimsep.matcher(dimensionStr);
        while (m.find()) {
            int s = m.start();
            int e = m.end();
            if (dimensionStr.charAt(e-1) == ',') e = e-1;
            String dim = dimensionStr.substring(s, e);
            DefaultMutableTreeNode dimNode = new DefaultMutableTreeNode(dim);
            dimensionsBranch.add(dimNode);
        }
        _root.add(dimensionsBranch);
        
        DefaultMutableTreeNode propertiesBranch = new DefaultMutableTreeNode("Properties");        
        propertiesBranch.add(new DefaultMutableTreeNode("uaid: " + Long.toString(_uaid)));
        propertiesBranch.add(new DefaultMutableTreeNode("aid: " + Long.toString(_aid)));
        propertiesBranch.add(new DefaultMutableTreeNode("Availability: " + Boolean.toString(_availability)));
        propertiesBranch.add(new DefaultMutableTreeNode("Temporary: " + Boolean.toString(_temporary)));

        _root.add(propertiesBranch);
    }

    /**
     * @return the _schemaTree
     */
    public DefaultMutableTreeNode getSchemaTree() {
        return _root;
    }
}
