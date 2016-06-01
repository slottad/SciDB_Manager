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

import java.net.URI;
import java.net.URISyntaxException;
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
