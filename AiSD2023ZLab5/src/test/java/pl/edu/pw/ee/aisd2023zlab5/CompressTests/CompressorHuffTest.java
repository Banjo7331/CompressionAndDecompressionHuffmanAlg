package pl.edu.pw.ee.aisd2023zlab5.CompressTests;

import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.Huffman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class CompressorHuffTest {
    private static final String filenameToCompressWithoutExtension = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\generatedNumbers";
    private static final String extensionForTexts = ".txt";
    private static final String extensionForCompressed = ".bhuff";
    @Before
    public void setup() {
        removeFilesBeforeStart();
    }
    @Test
    public void bigFileCompressTestKnowingThatCompressedSizeWillBeSmaller() {
        Huffman huffcompress = new Huffman();
        Random random = new Random();

        try {
            FileWriter fileWriter = new FileWriter(filenameToCompressWithoutExtension+extensionForTexts);

            for (int i = 0; i < 1000_000; i++) {
                int randomNumber = random.nextInt(10);
                fileWriter.write(Integer.toString(randomNumber));
            }
            fileWriter.close();

            File fileToCompress = new File(filenameToCompressWithoutExtension+extensionForTexts);
            long startingLength = fileToCompress.length();

            huffcompress.start(filenameToCompressWithoutExtension+extensionForTexts,filenameToCompressWithoutExtension+extensionForCompressed,true);

            File fileCompressed = new File(huffcompress.getCompressedDirectory());

            long compressedLength = fileCompressed.length();

            assertTrue(compressedLength < startingLength);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @Test
    public void smallFileCompressTestKnowingThatCompressedFileSizeCantBeSmaller() {
        Huffman huffcompress = new Huffman();
        Random random = new Random();

        try {
            FileWriter fileWriter = new FileWriter(filenameToCompressWithoutExtension+extensionForTexts);

            for (int i = 0; i < 5; i++) {
                int randomNumber = random.nextInt(10);
                fileWriter.write(Integer.toString(randomNumber));
            }
            fileWriter.close();

            File fileToCompress = new File(filenameToCompressWithoutExtension+extensionForTexts);
            long startingLength = fileToCompress.length();

            huffcompress.start(filenameToCompressWithoutExtension+extensionForTexts,filenameToCompressWithoutExtension+extensionForCompressed,true);

            File fileCompressed = new File(huffcompress.getCompressedDirectory());

            long compressedLength = fileCompressed.length();

            assertTrue(compressedLength >= startingLength);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static void removeFilesBeforeStart() {
        File f = new File((filenameToCompressWithoutExtension+extensionForTexts));
        f.delete();
        File f1 = new File((filenameToCompressWithoutExtension+extensionForCompressed));
        f1.delete();
    }
}
