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

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    
    public HostsManager(Properties props) {
        _properties = props;
        fill_hosts();
    }
    
    private void fill_hosts() {
        _hosts = new ArrayList<>();
        Enumeration e = _properties.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement().toString();
            if (key.startsWith("uri")) {
                try {
                    _hosts.add(new URI(_properties.getProperty(key)));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    public void reset_and_save_hosts(DefaultListModel newHosts) {
        // Clear old values
        Enumeration e = _properties.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement().toString();
            if (key.startsWith("uri")) {
                _properties.remove(key);
            }
        }
        e = newHosts.elements();
        Integer x = 1;
        while (e.hasMoreElements()) {
            String uri = e.nextElement().toString();
            String key = "uri" + x.toString();
            _properties.setProperty(key,uri);
            x++;
        }
        Path user = Paths.get(System.getProperty("user.home"),".scidb_manager");
        try (FileOutputStream out = new FileOutputStream(user.toFile())) {
            _properties.store(out, "---No Comment---");
        } catch (IOException ex) {
            Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public URI add_host(String host, Integer port, String user, String pass) {
        String userinfo = null;
        if (user != null) {
            userinfo = user + ":" + pass;
        }
        try {
            URI uri = new URI("scidb", userinfo, host, port, null, null, null);
            _hosts.add(uri);
            return uri;
        } catch (URISyntaxException ex) {
            Logger.getLogger(HostsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DefaultListModel getListModel() {
        DefaultListModel lm = new DefaultListModel();
        _hosts.stream().forEach((uri) -> {
            lm.addElement(uri);
        });
        return lm;
    }
    
    private final Properties _properties;
    private ArrayList<URI> _hosts;
}
