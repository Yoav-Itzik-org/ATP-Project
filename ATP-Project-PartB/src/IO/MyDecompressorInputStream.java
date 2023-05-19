package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyDecompressor - class to read a compressed maze which compressed using MyCompressor
 */
public class MyDecompressorInputStream extends InputStream {
    InputStream in;
    public MyDecompressorInputStream(InputStream in){this.in = in;}
    public int read()throws IOException {return in.read();}
    public int read(byte[] b)throws IOException{
        if(b == null) // Nowhere to read
            return -1;
        int bIndex = 0;
        if(in.available() < 8 || in.available() % 2 == 1)
            // Not enough bytes to read and decompressed a maze from (minimum 8 for maze dimensions)
            // The compressed maze read as pairs so has to be even
            return -1;
        for(; bIndex < 8 ; bIndex++) // copy the dimensions as is
            b[bIndex] = (byte) in.read();
        int rows = bytesToInteger(new byte[]{b[0], b[1], b[2], b[3]}); // calculate rows value
        int columns = bytesToInteger(new byte[]{b[4], b[5], b[6], b[7]}); // calculate columns value
        int lastByteSize = rows * columns % 8; // amount of bits in the last byte (without 0 added)
        lastByteSize = lastByteSize == 0 ? 8 : lastByteSize; // if the last byte is full
        while (in.available() > 0) {
            // read byte and it's sequence
            byte currentByte = (byte) in.read();
            int sequenceLength = in.read();
            while (sequenceLength-- > 0) { // read the byte sequence times
                int length = in.available() == 0 && sequenceLength == 0 ? lastByteSize : 8; // length of current byte
                if(bIndex + length > b.length) // not enough size to read to
                    return -1;
                insertByte(b, currentByte, bIndex, length); // insert the bits of the byte to the relevant cells
                bIndex += length;
            }
        }
        return b.length;
    }

    /**
     * Insert bits of the byte array in the relevant indexes
     * @param b the given byte array
     * @param currentByte byte of relevant bits
     * @param bIndex start of index to insert
     * @param length amount of bytes to insert
     */
    public void insertByte(byte[] b, byte currentByte, int bIndex, int length){
        int unsignedByte = currentByte & 0xff; // Get the correspond unsigned value
        // Calculate turned on bits and insert them to the array
        for (int bitOffset = 0; bitOffset < length; bitOffset++){
            b[bIndex + bitOffset] = (byte) (unsignedByte % 2);
            unsignedByte = unsignedByte / 2;
        }
    }

    /**
     * Calculate array of bytes to its relevant integer value (as 8 bytes together)
     * @param bytes 4 bytes
     * @return integer represent to value of the bytes as 4 bytes number
     */
    public int bytesToInteger(byte[] bytes){
        int result = 0;
        for(int byteIndex = 0; byteIndex < 4; byteIndex++) {
            int currentByte = bytes[byteIndex] & 0xff; // change to byte's value to unsigned
            for (int bitOffset = 0; bitOffset < 8; bitOffset++) { // and the bits with their correspond powers
                result += (currentByte % 2) * Math.pow(2, byteIndex * 8 + bitOffset);
                currentByte /= 2;
            }
        }
        return result;
    }
}