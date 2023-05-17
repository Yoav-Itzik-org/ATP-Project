package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;
    public SimpleDecompressorInputStream(InputStream in){this.in = in;}
    public int read() throws IOException {return in.read();}
    public int read(byte[] b)throws IOException{
        if(b == null)
            return -1;
        int bIndex = 0;
        for(; bIndex < 8 ; bIndex++) {
            if (in.available() == 0)
                return -1;
            b[bIndex] = (byte) in.read();
        }
        if(in.available() % 2 == 1)
            return -1;
        while (in.available() > 0){
            byte currentByte = (byte) in.read();
            int sequenceLength = in.read() & 0xff;
            if(bIndex + sequenceLength > b.length)
                return -1;
            insertSequence(b, bIndex, sequenceLength, currentByte);
            bIndex += sequenceLength;
        }
        return 1;
    }
    public void insertSequence(byte[] b, int offsetIndex, int sequenceLength, byte currentByte){
        for (int bIndex = 0; bIndex < sequenceLength; bIndex++)
            b[bIndex + offsetIndex] = currentByte;
    }
}
