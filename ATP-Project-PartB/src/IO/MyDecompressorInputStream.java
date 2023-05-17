package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    public MyDecompressorInputStream(InputStream in){this.in = in;}
    public int read()throws IOException {return in.read();}
    public int read(byte[] b)throws IOException{
        if(b == null)
            return -1;
        int bIndex = 0;
        if(in.available() < 8 || in.available() % 2 == 1)
            return -1;
        for(; bIndex < 8 ; bIndex++)
            b[bIndex] = (byte) in.read();
        int rows = bytesToInteger(new byte[]{b[0], b[1], b[2], b[3]});
        int columns = bytesToInteger(new byte[]{b[4], b[5], b[6], b[7]});
        int lastByteSize = rows * columns % 8;
        lastByteSize = lastByteSize == 0 ? 8 : lastByteSize;
        while (in.available() > 0) {
            byte currentByte = (byte) in.read();
            byte sequenceLength = (byte) in.read();
            while (sequenceLength-- > 0) {
                int length = in.available() == 0 && sequenceLength == 0 ? lastByteSize : 8;
//                System.out.printf("Byte: %d, index: %d, length: %d\n", currentByte & 0xff, bIndex, length);
                if(bIndex + length > b.length) {
                    System.out.println("HERERERERE");
                    return -1;
                }
                insertByte(b, currentByte, bIndex, length);
                bIndex += length;
            }
        }
        return b.length;
    }
    public void insertByte(byte[] b, byte currentByte, int bIndex, int length){
        int unsignedByte = currentByte & 0xff;
        for (int bitOffset = 0; bitOffset < length; bitOffset++){
            b[bIndex + bitOffset] = (byte) (unsignedByte % 2);
            unsignedByte = unsignedByte / 2;
        }
    }
    public int bytesToInteger(byte[] bytes){
        int result = bytes[0];
        for(int byteIndex = 1; byteIndex < 4; byteIndex++) {
            int currentByte = bytes[byteIndex];
            for (int bitOffset = 0; bitOffset < 8; bitOffset++)
                result += (currentByte % 2) * Math.pow(2, byteIndex * 8 + bitOffset);
        }
        return result;
    }
}