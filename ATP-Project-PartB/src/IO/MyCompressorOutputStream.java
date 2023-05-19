package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * My compressor class - the class compress a maze to byte array
 * the compression technique is to calculate the decimal value of 8 consecutive cells (as binary byte) and save them as
 * one byte. then write every byte and its sequence length
 */
public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;
    public MyCompressorOutputStream(OutputStream out){this.out = out;}

    /**
     * Override supers write function - write with the output field
     * @param b   the {@code byte}.
     * @throws IOException according to out's exception
     */
    public void write(int b) throws IOException{out.write(b);}

    /**
     * Write byte array
     * @param b   the data.
     * @throws IOException according to out's exception
     */
    public void write(byte[ ]b) throws IOException {
        // Copy the first 8 cells - representing the description
        for (int bOffset = 0; bOffset < 8; bOffset++)
            write(b[bOffset]);
        // Get all the bytes
        ArrayList<Byte> bytesList = new ArrayList<>();
        int lastByteSize = b.length % 8; // Length of the last bytes without zeros added
        for (int byteIndex = 1; byteIndex < (b.length + 7) / 8; byteIndex++) {
            byte currentByte = 0;
            int byteLength = byteIndex + 1 < (b.length + 7) / 8 || lastByteSize == 0 ? 8 : lastByteSize; // Cut the last byte if there aren't enough bits
            // Calculate the decimal value
            for (int bitOffset = 0; bitOffset < byteLength; bitOffset++)
                currentByte += b[8 * byteIndex + bitOffset] * Math.pow(2, bitOffset);
            bytesList.add(currentByte);
        }
        // Calculate byte and it's sequence
        int sequenceLength = 0;
        byte previousByte = bytesList.get(0);
        for (Byte currentByte : bytesList) {
            if (currentByte != previousByte) { // The sequence has ended
                splitAndWrite(sequenceLength, previousByte);
                // Reset the current byte and the sequence range
                previousByte = currentByte;
                sequenceLength = 1;
            } else // Sequence continue
                sequenceLength++;
        }
        splitAndWrite(sequenceLength, previousByte);
    }

    /**
     * Split a sequence and write it
     * The function split the sequence to byte sized ones (less-equal to 255)
     * @param amount sequence length
     * @param currentByte the current byte to write
     * @throws IOException according to outs exception
     */
    public void splitAndWrite(int amount, byte currentByte)throws IOException{
        while (amount > 255){
            write(currentByte);
            write(255);
            amount -= 255;
        }
        write(currentByte);
        write(amount);
    }
}
