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
package scidb_manager;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author slottad
 */
public class ResultTableModel extends AbstractTableModel {
    private final ArrayList<String> columnNames;
    private final ArrayList<Object[]> data;
    
    public ResultTableModel(ResultSet results) {
        columnNames = new ArrayList<>();
        data = new ArrayList<>();
        try {
            ResultSetMetaData meta = results.getMetaData();
            //columnNames = new String[meta.getColumnCount()];
            for (int i=1; i<=meta.getColumnCount(); i++) {
                //columnNames[i-1]=meta.getColumnName(i);
                columnNames.add(meta.getColumnName(i));
            }
            while (!results.isAfterLast()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i=1; i<=meta.getColumnCount(); i++) {
                    switch (meta.getColumnTypeName(i)) {
                        case "int64":
                            row[i-1]=results.getLong(i);
                            break;
                        case "string":
                            try {
                                row[i-1]=results.getString(i);
                            } catch (Exception e) {
                                row[i-1]="error";
                            }
                            break;
                        case "bool":
                            row[i-1]=results.getBoolean(i);
                            break;
                        
                    }
                }
                data.add(row);
                results.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResultTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
