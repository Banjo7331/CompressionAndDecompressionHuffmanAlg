package pl.edu.pw.ee.aisd2023zlab5.DecompressTests;

import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.Huffman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class DecompressorHuffTest {
    private static final String bigFilenameToCompressWithoutExtension = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\niemanie";
    private static final String smallFilenameToCompressWithoutExtension = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\polskie";
    private static final String extensionForTexts = ".txt";
    private static final String extensionForCompressed = ".bhuff";
    @Test
    public void bigFileDecompressTest() {
        Huffman huffcompress = new Huffman();
        Random random = new Random();

        File fileCompressed = new File(bigFilenameToCompressWithoutExtension+extensionForCompressed);
        long compressedLength = fileCompressed.length();

        huffcompress.start(bigFilenameToCompressWithoutExtension+extensionForCompressed,bigFilenameToCompressWithoutExtension+extensionForTexts,false);

        File fileDecompressed = new File(huffcompress.getDecompressedDirectory());

        long decompressedLength = fileDecompressed.length();

        assertTrue(compressedLength < decompressedLength);


    }
    @Test
    public void smallFileDecompressTest() {
        Huffman huffcompress = new Huffman();
        Random random = new Random();

        File fileCompressed = new File(smallFilenameToCompressWithoutExtension+extensionForCompressed);
        long compressedLength = fileCompressed.length();

        huffcompress.start(smallFilenameToCompressWithoutExtension+extensionForCompressed,smallFilenameToCompressWithoutExtension+extensionForTexts,false);

        File fileDecompressed = new File(huffcompress.getDecompressedDirectory());

        long decompressedLength = fileDecompressed.length();

        assertTrue(compressedLength > decompressedLength);

    }
}
