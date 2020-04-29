package dnf.author.kritsu.lib;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Zlibs {
    public static byte[] decompress(byte[] data,int len){
        byte[] output=new byte[len];
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        try {
            inflater.inflate(output);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        return output;
    }
}
