/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scidb_manager;

import java.sql.SQLException;
import java.sql.ResultSet;

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

    SciDBArray(ResultSet row) throws SQLException {
        _name = row.getString("name");
        _uaid = row.getLong("uaid");
        _aid = row.getLong("aid");
        _schema = row.getString("schema");
        _availability = row.getBoolean("availability");
        _temporary = row.getBoolean("temporary");       
    }
    
    @Override
    public String toString() {
        return _name;
    }
}
