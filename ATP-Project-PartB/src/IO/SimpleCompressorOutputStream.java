package IO;
import java.io.IOException;
import java.io.OutputStream;

/**
 * SimpleCompressor
 * The compressor compress a maze using simple compression algorithm:
 * the maze presented as a [...bit, sequence,...] (sequence = how much time it appear consecutively) for all bit and sequences
 * the first 8 cells dedicated to the dimensions
 * the compressor write the compressed data using a given output stream
 */
public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;
    public SimpleCompressorOutputStream(OutputStream out){this.out = out;}

    /**
     * Override supers write function - write with the output field
     * @param b   the {@code byte}.
     * @throws IOException according to out's exception
     */
    public void write(int b) throws IOException{out.write(b);}

    /**
     * Write a byte array with compression
     * @param b   the data.
     * @throws IOException according to out's exception
     */
    public void write(byte[] b)throws IOException{
        if(b == null) // No array to write from
            return;
        int byteIndex = 0;
        // Copy dimensions as is
        for(; byteIndex < 8; byteIndex++)
            write(b[byteIndex]);
        // Shrink the cells as bit(saved in a byte) and it's sequence
        int sequenceLength = 0;
        byte previousByte = b[8];
        for(; byteIndex < b.length; byteIndex++){
            byte currentByte = b[byteIndex];
            if(currentByte != previousByte){ // Sequence ended
                splitAndWrite(sequenceLength, previousByte); // Write the sequence
                previousByte = currentByte;
                sequenceLength = 1;
            }
            else // Sequence continued
                sequenceLength++;
        }
        splitAndWrite(sequenceLength, previousByte); // Write last sequence
    }

    /**
     * Split a sequence and write it
     * The function split the sequence to byte sized ones (less-equal to 255)
     * In between every sequence write the opposite bit
     * @param amount sequence length
     * @param currentByte the current byte to write
     * @throws IOException according to outs exception
     */
    public void splitAndWrite(int amount, byte currentByte)throws IOException{
        while (amount > 255){
            write(currentByte);
            write(255);
            // Write opposite bit with sequence length of 0
            write((currentByte + 1) % 2);
            write(0);
            amount -= 255;
        }
        write(currentByte);
        write(amount);
    }
}