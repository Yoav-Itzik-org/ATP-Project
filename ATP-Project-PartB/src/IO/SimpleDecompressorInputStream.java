package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * SimpleDecompressor - class to read a compressed maze which compressed using SimpleCompressor
 */
public class SimpleDecompressorInputStream extends InputStream {
    InputStream in;
    public SimpleDecompressorInputStream(InputStream in){this.in = in;}
    public int read() throws IOException {return in.read();}

    /**
     * Read input to bytes array
     * @param b   the buffer into which the data is read.
     * @return number of bytes read, -1 if a problem acquired
     * @throws IOException according to in's exception
     */
    public int read(byte[] b)throws IOException{
        if(b == null) // Nowhere to read to
            return -1;
        int bIndex = 0;
        // Read the dimensions as is
        for(; bIndex < 8 ; bIndex++) {
            if (in.available() == 0) // No input to read the dimension
                return -1;
            b[bIndex] = (byte) in.read();
        }
        if(in.available() % 2 == 1) // Odd amount of input
        // The input read in pairs (bit and its correspond sequence) so has to be even
            return -1;
        // Start to read bit and it's sequence
        while (in.available() > 0){
            byte currentByte = (byte) in.read();
            int sequenceLength = in.read() & 0xff; // Get unsigned value of a byte
            if(bIndex + sequenceLength > b.length) // No place to write all the bits to in the given output array
                return -1;
            insertSequence(b, bIndex, sequenceLength, currentByte); // Insert a bit with it's correspond sequence
            bIndex += sequenceLength;
        }
        return 1;
    }

    /**
     * Write bit sequence times
     * @param b a byte array to write to
     * @param offsetIndex where to start writing to the byte array
     * @param sequenceLength amount of time to write the bit
     * @param currentByte the bit to write
     */

    public void insertSequence(byte[] b, int offsetIndex, int sequenceLength, byte currentByte){
        for (int bIndex = 0; bIndex < sequenceLength; bIndex++)
            b[bIndex + offsetIndex] = currentByte;
    }
}
