package IO;
import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    //TODO - Verify with maze dimensions
    OutputStream out;
    public MyCompressorOutputStream(OutputStream out){
        this.out = out;
    }
    public void write(int b) throws IOException{out.write(b);}
    public void write(byte[] b)throws IOException{
        int amount = 0;
        byte currentSequence = b[0];
        for(byte currentByte : b){
            if(currentByte != currentSequence){
                splitAndWrite(amount, currentSequence);
                currentSequence = currentByte;
                amount = 1;
            }
            else
                amount++;
        }
        splitAndWrite(amount, currentSequence);
    }
    public void splitAndWrite(int amount, byte currentSequence)throws IOException{
        while (amount > 255){
            write(currentSequence);
            write(255);
            write((currentSequence + 1) % 2);
            write(0);
            amount -= 255;
        }
        write(currentSequence);
        write(amount);
    }
}