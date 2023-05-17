package IO;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;
    public SimpleCompressorOutputStream(OutputStream out){this.out = out;}
    public void write(int b) throws IOException{out.write(b);}
    public void write(byte[] b)throws IOException{
        if(b == null)
            return;
        int sequenceLength = 0;
        byte previousByte = b[8];
        int byteIndex = 0;
        for(; byteIndex < 8; byteIndex++)
            write(b[byteIndex]);
        for(; byteIndex < b.length; byteIndex++){
            byte currentByte = b[byteIndex];
            if(currentByte != previousByte){
                splitAndWrite(sequenceLength, previousByte);
                previousByte = currentByte;
                sequenceLength = 1;
            }
            else
                sequenceLength++;
        }
        splitAndWrite(sequenceLength, previousByte);
    }
    public void splitAndWrite(int amount, byte currentByte)throws IOException{
        while (amount > 255){
            write(currentByte);
            write(255);
            write((currentByte + 1) % 2);
            write(0);
            amount -= 255;
        }
        write(currentByte);
        write(amount);
    }
}