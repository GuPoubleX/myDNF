package dnf.author.kritsu.lib;

import java.io.InputStream;

public class Streams {
    public static int readInt(InputStream in) throws Exception {
        return (int) readNumber(in, 4);
    }
    
    public static long readLong(InputStream in) throws Exception {
        return readNumber(in,8);
    }
    
    public static byte[] readLength(InputStream in,int len) throws Exception {
        byte[] buf=new byte[len];
        in.read(buf);
        return buf;
    }
    
    private static long readNumber(InputStream in,int len) throws Exception {
        byte[] buf=new byte[len];
        in.read(buf);
        long rs=0;
        for (int i=0; i < buf.length; i++) {
            rs|=(buf[i] & 0xff) << (i * 8);
        }
        return rs;
    }
}
