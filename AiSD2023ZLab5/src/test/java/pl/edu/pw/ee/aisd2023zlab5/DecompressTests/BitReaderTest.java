package pl.edu.pw.ee.aisd2023zlab5.DecompressTests;

import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.decompressor.BitReader;

import java.io.*;

public class BitReaderTest {
    @Test
    public void ReadByte() {
        String filePath = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\abc.txt";
        File f = new File(filePath);
        int l = (int) f.length();
        try {
            BitReader bis = new BitReader(new BufferedInputStream(new FileInputStream(filePath)), l);
            for(int i = 0; i < 8; i++){
                bis.readBit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
