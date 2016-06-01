/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scidb_manager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.scidb.client.SciDBException;
import org.scidb.jdbc.Connection;
import org.scidb.jdbc.IResultSetWrapper;
import org.scidb.jdbc.IStatementWrapper;

/**
 *
 * @author slottad
 */
public final class QueryManager {
    private static QueryManager instance = null;

    public static synchronized boolean initialize(String host, Integer port, String user, String pass) {
        if (instance != null) {
            Logger.getLogger(SciDB_Manager.class.getName()).log(Level.SEVERE, "QueryManager initialized more than once.");
        }
        try {
            instance = new QueryManager(host, port, user, pass);
        } catch (SQLException | SciDBException | IOException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE,null,"Unable to connect to host.");
            return false;
        }
        return true;
    }
    
    
    public static synchronized QueryManager getInstance() throws SQLException, SciDBException, IOException {
        if (instance == null) {
            Logger.getLogger(SciDB_Manager.class.getName()).log(Level.SEVERE, "QueryManager used before initialization.");
        }
        return instance;
    }
    
    private QueryManager(String host, Integer port, String user, String pass)
            throws SQLException, SciDBException, IOException {
        _host = host;
        _port = port;
        _user = user;
        _pass = pass;
        _conn = new Connection(_host, _port);
        _conn.getSciDBConnection().startNewClient(_user, _pass);
        _st = _conn.createStatement();
        IStatementWrapper stWrap = _st.unwrap(IStatementWrapper.class);
        stWrap.setAfl(true);
    }

    public final List<String> namespaces() throws SQLException {
        ResultSet res = _st.executeQuery("list('namespaces')");
        List<String> namespaces = new ArrayList<>();
        while (!res.isAfterLast()) {
            namespaces.add(res.getString("name"));
            res.next();
        }
        return namespaces;
    }

    public final List<SciDBArray> arrays(String namespace) throws SQLException {
        ResultSet res = _st.executeQuery("list('arrays')");
        List<SciDBArray> arrays = new ArrayList<>();
        while (!res.isAfterLast()) {
            arrays.add(new SciDBArray(res));
            res.next();
        }
        return arrays;
    }
    
    public ResultTableModel run_afl_query(String afl) throws SQLException {
        ResultSet res = _st.executeQuery(afl);
        return new ResultTableModel(res);
    }
    
    public final void test() {
        try {
            //create array A<a:string>[x=0:2,3,0, y=0:2,3,0];
            //select * into A from array(A, '[["a","b","c"]["d","e","f"]["123","456","789"]]');
            //ResultSet res = st.executeQuery("select * from array(<a:string>[x=0:2,3,0, y=0:2,3,0], '[[\"a\",\"b\",\"c\"][\"d\",\"e\",\"f\"][\"123\",\"456\",\"789\"]]')");
            ResultSet res = _st.executeQuery("build(<a:string>[x=0:2,3,0, y=0:2,3,0], '[[\"a\",\"b\",\"c\"][\"d\",\"e\",\"f\"][\"123\",\"456\",\"789\"]]',true)");
            ResultSetMetaData meta = res.getMetaData();

            System.out.println("Source array name: " + meta.getTableName(0));
            System.out.println(meta.getColumnCount() + " columns:");

            IResultSetWrapper resWrapper = res.unwrap(IResultSetWrapper.class);
            for (int i = 1; i <= meta.getColumnCount(); i++)
            {
                System.out.println(meta.getColumnName(i) + " - " + meta.getColumnTypeName(i) + "(" + Integer.toString(meta.getColumnType(i)) + ") - is attribute:" + resWrapper.isColumnAttribute(i));
            }
            System.out.println("=====");

            System.out.println("x y a");
            System.out.println("-----");
            while(!res.isAfterLast())
            {
                System.out.println(res.getLong("x") + " " + res.getLong("y") + " " + res.getString("a"));
                res.next();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private final String  _host;
    private final Integer _port;
    private final String  _user;
    private final String  _pass;

    private final Connection _conn;
    private final Statement _st;
    
    /**
     * @return the _host
     */
    public String getHost() {
        return _host;
    }

    /**
     * @return the _user
     */
    public String getUser() {
        return _user;
    }

    /**
     * @return the _port
     */
    public Integer getPort() {
        return _port;
    }

    /**
     * @return the _pass
     */
    public String getPass() {
        return _pass;
    }
}
