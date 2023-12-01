package pl.edu.pw.ee.aisd2023zlab5.compressor;

import java.io.IOException;
import java.io.OutputStream;

public class BitWriter implements AutoCloseable {
    private int currentByte;
    private int numberOfFilledBits;
    private OutputStream outputStream;

    public BitWriter(OutputStream outputStream) {
        this.currentByte = 0;
        this.numberOfFilledBits = 0;
        this.outputStream = outputStream;
    }

    public void writeBit(boolean bit) throws IOException {
        currentByte = (currentByte << 1) | (bit ? 1 : 0);
        numberOfFilledBits++;

        if (numberOfFilledBits == 8) {
            outputStream.write(currentByte);
            currentByte = 0;
            numberOfFilledBits = 0;
        }
    }

    public void flush() throws IOException {
        while (numberOfFilledBits != 0) {
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
