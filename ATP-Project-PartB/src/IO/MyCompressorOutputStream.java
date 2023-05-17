package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;
    public MyCompressorOutputStream(OutputStream out){
        this.out = out;
    }
    public void write(int b) throws IOException{
//        System.out.println(b);
        out.write(b);
    }
    public void write(byte[ ]b) throws IOException {
        for (int bOffset = 0; bOffset < 8; bOffset++)
            out.write(b[bOffset]);
        ArrayList<Byte> bytesList = new ArrayList<>();
        int lastByteSize = b.length % 8; //how much 0 we added
        for (int byteIndex = 1; byteIndex < (b.length + 7) / 8; byteIndex++) {
            byte currentByte = 0;
            int byteLength = byteIndex + 1 < (b.length + 7) / 8 ? 8 : 8 - lastByteSize;
            for (int bitOffset = 0; bitOffset < byteLength; bitOffset++)
                currentByte += b[8 * byteIndex + bitOffset] * Math.pow(2, byteLength-bitOffset-1);
            bytesList.add(currentByte);
        }
        int sequenceLength = 0;
        byte previousByte = bytesList.get(0);
        for (Byte currentByte : bytesList) {
            if (currentByte != previousByte) {
                splitAndWrite(sequenceLength, previousByte);
                previousByte = currentByte;
                sequenceLength = 1;
            } else
                sequenceLength++;
        }
        splitAndWrite(sequenceLength, previousByte);
//        System.out.println("ENDED COMPRESS\n");
    }
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
