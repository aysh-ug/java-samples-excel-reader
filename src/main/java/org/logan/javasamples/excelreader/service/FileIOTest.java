package org.logan.javasamples.excelreader.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIOTest {
    private static final File ROOT = new File( "/Users/Loganathan/Documents/multifiletest" );
    public static final File COMPOSITE_FILE = new File( ROOT, "composite.txt" );
    //private static final int SEGMENT_SIZE = 20000;
    //private static final int MESSAGE_NUMBER = 50000;
    
    private static final int SEGMENT_SIZE = 2;
    private static final int MESSAGE_NUMBER = 5;
 
    public static void main( String[] args ) throws IOException {
        //run these 2 methods to create a test set
        //makeCompositeFile( MESSAGE_NUMBER );
        makeSingleFiles( MESSAGE_NUMBER );
        //then reboot your computer and run the following method. Do not run them in a single program - your data
        //will be read from the OS memory cache
        //testReadSpeed( MESSAGE_NUMBER );
    }
 
    private static void testReadSpeed( final int count ) throws IOException {
        final long startComposite = System.currentTimeMillis();
        final List<Integer> resComposite = readCompositeFile();
        final long timeComposite = System.currentTimeMillis() - startComposite;
 
        final long startSingle = System.currentTimeMillis();
        final List<Integer> resSingle = readSingleFiles( count );
        final long timeSingle = System.currentTimeMillis() - startSingle;
        
        System.out.println( "resComposite: "+resComposite+" resSingle: "+resSingle );
 
        if ( !resComposite.equals( resSingle ) )
        System.out.println( "Results not equal!" );
 
        System.out.println( "Time to read composite file with " + count + " segments = " + ( timeComposite / 1000.0 ) + " sec" );
        System.out.println( "Time to read " + count + " single files = " + ( timeSingle / 1000.0 ) + " sec" );
    }
 
    private static List<Integer> readCompositeFile() throws IOException
    {
        final List<Integer> res = new ArrayList<Integer>( MESSAGE_NUMBER );
        final DataInputStream is = new DataInputStream( new BufferedInputStream( new FileInputStream( COMPOSITE_FILE ), 65536 ) );
        try
        {
            while ( true )
            {
                try
                {
                    final int id = is.readInt();
                    final int len = is.readInt();
                    final byte[] data = new byte[ len ];
                    is.readFully( data );
                    res.add( id );
                }
                catch ( EOFException ex ) {
                    //ex.printStackTrace();
                    return res;
                }
            }
        }
        finally {
            is.close();
        }
    }
 
    private static List<Integer> readSingleFiles( final int count ) throws IOException
    {
        final int maxLength = String.valueOf( count ).length();
        final List<Integer> res = new ArrayList<Integer>( count );
        for ( int i = 0; i < count; ++i )
        {
            final File file = indexToFile( i, maxLength );
            final DataInputStream dis = new DataInputStream( new BufferedInputStream( new FileInputStream( file ), SEGMENT_SIZE + 8 ) );
            try
            {
                res.add( i );
                final int id = dis.readInt();
                final int len = dis.readInt();
                final byte[] data = new byte[ len ];
                dis.readFully( data );
                res.add( id );
            }
            finally {
                dis.close();
            }
        }
        return res;
    }
 
    private static void makeCompositeFile( final int count ) throws IOException
    {
        if ( COMPOSITE_FILE.exists() )
            return;
        //this is segment data
        final byte[] data = getSegmentData();
        /*
        Composite file contains multiple segments. Each segment contains:
        id:int
        length:int
        data:byte[ length ]
        */
        final DataOutputStream os = new DataOutputStream( new FileOutputStream( COMPOSITE_FILE ) );
        try
        {
            for ( int i = 0; i < count; ++i )
            {
                os.writeInt( i ); //id
                os.writeInt( data.length ); //message length
                os.write( data ); //message content
            }
        }
        finally {
            os.close();
        }
    }
 
    private static void makeSingleFiles( final int count ) throws IOException
    {
        //this is segment data
        final byte[] data = getSegmentData();
        System.out.println(data.length);
        System.out.println(data);
        final int maxLength = String.valueOf( count ).length();
        //populate all files
        for ( int i = 0; i < count; ++i )
        {
            final File file = indexToFile( i, maxLength );
            if ( !file.getParentFile().exists() ) {
                System.out.println(file.getParentFile());
                file.getParentFile().mkdirs();
            }
            if ( file.exists())
                continue;
            final DataOutputStream os = new DataOutputStream( new FileOutputStream( file ) );
            try
            {
                os.writeInt( 25 ); //some sort of id
                System.out.println("i: "+i);
                os.writeInt( data.length ); //message length
                os.write( data ); //message contents
            }
            finally {
                os.close();
            }
        }
    }
 
    private static byte[] getSegmentData() {
        final byte[] data = new byte[ SEGMENT_SIZE ];
        for ( int i = 0; i < data.length; ++i ) {
            data[ i ] = ( byte ) i;
            System.out.println(data[ i ]);
        }
        return data;
    }
 
    private static File indexToFile( final int index, final int maxLength )
    {
        String val = String.valueOf( index );
        while ( val.length() < maxLength )
            val = "0" + val;
        File curFile = ROOT;
        while ( val.length() > 2 )
        {
            curFile = new File( curFile, val.substring( 0, 2 )+".txt" );
            val = val.substring( 2 );
        }
        return new File( curFile, val+".txt" );
    }
}
 
