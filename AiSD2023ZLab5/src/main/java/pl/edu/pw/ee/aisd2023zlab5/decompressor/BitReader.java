package pl.edu.pw.ee.aisd2023zlab5.decompressor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitReader implements AutoCloseable {
    private InputStream inputStream;
    private int currentByte;
    private int numBitsFilled;
    private long lengthOfFile;
    private long howManyBytes = 0;
    private int howManyBitsIgnore=0;

    public BitReader(InputStream inputStream, long filelength) {
        this.inputStream = new BufferedInputStream(inputStream);
        this.currentByte = 0;
        this.numBitsFilled = 0;
        this.lengthOfFile = filelength;
    }

    public int readBit() throws IOException {
        if(howManyBytes == lengthOfFile){
            return readLastByteBits();
    }
        if (currentByte == -1) {
            return -1;
        }

        if (numBitsFilled == 0) {
            currentByte = inputStream.read();
            howManyBytes++;
            if (currentByte == -1) {
                return -1;
            }
            numBitsFilled = 8;

            if(howManyBytes == lengthOfFile){
                howManyBitsIgnore = findWhenOne();
                return readLastByteBits();
            }
        }

        int bit = (currentByte >> (numBitsFilled - 1)) & 1;
        numBitsFilled--;

        return bit;
    }
    public int readLastByteBits() throws IOException {
        if(numBitsFilled - howManyBitsIgnore <=0){
            return -1;
        }
        if (currentByte == -1) {
            return -1;
        }


        int bit = (currentByte >> (numBitsFilled - 1)) & 1;
        numBitsFilled--;

        return bit;
    }
    private int findWhenOne() {
        int onePositon = 0;

        for (int i = 0; i < 8; i++) {
            if ((currentByte & (1 << i)) != 0) {
                break;
            }
            onePositon++;
        }

        return onePositon+1;
    }
    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
