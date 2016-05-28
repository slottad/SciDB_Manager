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

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.scidb.client.SciDBException;

/**
 *
 * @author slottad
 */
public class SciDB_Manager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            try
            {
                Class.forName("org.scidb.jdbc.Driver");
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("Driver is not in the CLASSPATH -> " + e);
            }
            
            QueryManager qm = QueryManager.getInstance();
            qm.test();
            qm.namespaces();
        }
        catch (SQLException | SciDBException | IOException ex)
        {
            Logger.getLogger(SciDB_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.exit(0);
    }
    
}
