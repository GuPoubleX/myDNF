package dnf.author.kritsu.lib;

import dnf.author.kritsu.constants.ColorBits;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Colors {
    public static List<byte[]> readPalette(InputStream in, int count) throws IOException {
        List<byte[]> palette=new ArrayList<>();
        for (int i=0; i < count; i++) {
            byte[] data=new byte[4];
            in.read(data);
            palette.add(data);
        }
        return palette;
    }
    
    static byte[] readColor(InputStream in, int bits) throws IOException {
        if (bits == ColorBits.ARGB_8888) {
            byte[] bs=new byte[4];
            in.read(bs);
            return bs;
        }
        int a=0;
        int r=0;
        int g=0;
        int b=0;
        byte[] bs=new byte[2];
        in.read(bs);
        int left=((int) bs[0]) & 0xff;//Byte.toUnsignedInt(bs[0]);
        int right=((int) bs[1]) & 0xff;//Byte.toUnsignedInt(bs[1]);
        switch (bits) {
            case ColorBits.ARGB_1555:
                a=right >> 7;
                r=(right >> 2) & 0x1f;
                g=((left >> 5) | (right & 3) << 3);
                b=(left & 0x1f);
                a=(a * 0xff);
                r=(r << 3 | r >> 2);
                g=(g << 3 | g >> 2);
                b=(b << 3 | b >> 2);
                break;
            case ColorBits.ARGB_4444:
                a=(right & 0xf0);
                r=((right & 0xf) << 4);
                g=(left & 0xf0);
                b=((left & 0xf) << 4);
                break;
        }
        return new byte[]{(byte) b, (byte) g, (byte) r, (byte) a};
    }
    
    

    
}
