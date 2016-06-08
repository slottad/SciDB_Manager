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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author slottad
 */
public class SciDB_Manager {
    static Pattern brackets = Pattern.compile("[<\\[\\]>]+\\s*[<\\[\\]>]*");
    static Pattern dimsep = Pattern.compile("[^,]+,[^,]+,[^,]+,{0,1}");
    
    public static void schema_parse(String inSchema) {
        String[] schema = brackets.split(inSchema);
        //String name = schema[0];
        String attributes = schema[1];
        String dimensions = schema[2];

        System.out.println("Attributes: " + attributes);
        String[] items = attributes.split(",");                
        for (String i: items) {
            System.out.println(i);
        }

        System.out.println("Dimensions: " + dimensions);
        Matcher m = dimsep.matcher(dimensions);
        while (m.find()) {
            int s = m.start();
            int e = m.end();
            if (dimensions.charAt(e-1) == ',') e = e-1;
            System.out.println(dimensions.substring(s, e));
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String schema1 = "KG_VAR_SVD<u:double NOT NULL> [sample_id=0:2503,512,0,i=0:2503,512,0]";
        String schema2 = "MODIS_DATA<altitude:double> [latitude_e4=-900000:900000,20000,0,longitude_e4=-1800000:1800000,20000,0]";
        String schema3 = "MODIS_PLACES<FEATURE_ID:string,FEATURE_NAME:string,FEATURE_CLASS:string,STATE_ALPHA:string,STATE_NUMERIC:string,COUNTY_NAME:string,COUNTY_NUMERIC:string,PRIMARY_LAT_DMS:string,PRIM_LONG_DMS:string,PRIM_LAT_DEC:string,PRIM_LONG_DEC:string,SOURCE_LAT_DMS:string,SOURCE_LONG_DMS:string,SOURCE_LAT_DEC:string,SOURCE_LONG_DEC:string,ELEV_IN_M:string,ELEV_IN_FT:string,MAP_NAME:string,DATE_CREATED:string,DATE_EDITED:string> [i=0:*,10000,0]";
        
        schema_parse(schema1);

        System.exit(0);
    }
    
}
