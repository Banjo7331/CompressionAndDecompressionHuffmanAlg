package pl.edu.pw.ee.aisd2023zlab5;

import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class HuffmanTest {

    @Test
    public void General_Compression_and_DecompressionTestForPolishCharacters() {
        String data = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\polskie.txt";

        Huffman huff = new Huffman();
        huff.huffman(data,true);
        huff.huffman("C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\polskie.bartek",false);

    }
    @Test
    public void CatchFileNotFoundException_If_File_isnotInCorrectFolder_OrDoesntExist() {
        String data = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\bzdury.txt";
        Huffman huff = new Huffman();
        Throwable exceptionCaught = catchThrowable(() -> {
            huff.huffman(data,true);

        });

        assertThat(exceptionCaught)
                .isInstanceOf(NullPointerException.class);

    }
    @Test
    public void General_Compression_and_DecompressionTestForNiemanie() {
        String data = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\niemanie.txt";

        Huffman huff = new Huffman();
        huff.huffman(data,true);
        huff.huffman("C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\niemanie.bartek",false);

    }
}
