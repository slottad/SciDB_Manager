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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author slottad
 */
public class HostsManager {
    static Path user = Paths.get(System.getProperty("user.home"),".scidb_hosts");
    
    static public DefaultListModel load() {
        DefaultListModel lm = new DefaultListModel();
        //Path user = Paths.get(System.getProperty("user.home"),".scidb_hosts");
        if (Files.isRegularFile(user) & Files.isReadable(user)) {
            FileReader fileReader;
            try {
                fileReader = new FileReader(user.toFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);String line;
		while ((line = bufferedReader.readLine()) != null) {
                    lm.addElement(new URI(line));
                }
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        return lm;
    }
    
    static public void save(DefaultListModel newHosts) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(user.toFile());
            Enumeration e = newHosts.elements();
            while (e.hasMoreElements()) {
                String uri = e.nextElement().toString() + "\n";
                fileWriter.write(uri);
            }
        } catch (IOException ex) {
            Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    static public URI create_uri(String host, Integer port, String user, String pass) {
        String userinfo = null;
        if (user != null) {
            userinfo = user + ":" + pass;
        }
        try {
            URI uri = new URI("scidb", userinfo, host, port, null, null, null);
            return uri;
        } catch (URISyntaxException ex) {
            Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
