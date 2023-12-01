package pl.edu.pw.ee.aisd2023zlab5;

import pl.edu.pw.ee.aisd2023zlab5.compressor.CompressorHuff;
import pl.edu.pw.ee.aisd2023zlab5.decompressor.DecompressorHuff;

import java.util.Map;

public class Test {
    public static void main(String[] args) {

        String data = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\Pan Tadeusz.txt";

        Huffman huff = new Huffman();
        huff.huffman(data,true);
        huff.huffman("C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\Pan Tadeusz.bartek",false);

    }
}
