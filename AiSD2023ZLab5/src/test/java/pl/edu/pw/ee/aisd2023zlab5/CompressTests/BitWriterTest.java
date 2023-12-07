package pl.edu.pw.ee.aisd2023zlab5.CompressTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.compressor.BitWriter;
import pl.edu.pw.ee.aisd2023zlab5.decompressor.BitReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class BitWriterTest {
    private static final String filenameToCompressWithoutExtension = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfTestsForHuffman\\byte";
    private static final String extensionForTexts = ".txt";
    private static final String extensionForCompressed = ".bhuff";
    private static final char DELETEMARKER= '1';
    @Before
    public void setup() {
        removeFilesBeforeStart();
    }
    @Test
    public void WriteByte() {

        try (BitWriter bos = new BitWriter(new BufferedOutputStream(new FileOutputStream(filenameToCompressWithoutExtension+extensionForCompressed)))) {

            byte [] bitArray = {0,1,1,0,1,0,0,1};
            for (byte bit : bitArray) {
                bos.writeBit(bit == 1 ? true : false);
            }
            bos.writeBit(DELETEMARKER == '1'? true: false );
            bos.flush();

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filenameToCompressWithoutExtension + extensionForCompressed))) {

                int byteRead = bis.read();

                byte riddenTestByte = (byte) byteRead;

                for (int i = 0; i < 8; i++) {
                    int bitInByteArray = (bitArray[i] == 1) ? 1 : 0;
                    int bitInReadByte = ((riddenTestByte >> (7 - i)) & 1);

                    assertTrue(bitInByteArray == bitInReadByte);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private static void removeFilesBeforeStart() {
        File f = new File((filenameToCompressWithoutExtension+extensionForTexts));
        f.delete();
    }
}
