package pl.edu.pw.ee.aisd2023zlab5.compressor;

import java.io.IOException;
import java.io.OutputStream;

public class BitWriter implements AutoCloseable {
    private OutputStream outputStream;
    private int currentByte;
    private int numBitsFilled;

    public BitWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.currentByte = 0;
        this.numBitsFilled = 0;
    }

    public void writeBit(boolean bit) throws IOException {
        currentByte = (currentByte << 1) | (bit ? 1 : 0);
        numBitsFilled++;

        if (numBitsFilled == 8) {
            outputStream.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    public void flush() throws IOException {
        while (numBitsFilled != 0) {
            writeBit(false);
        }
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        flush();
        outputStream.close();
    }
}
