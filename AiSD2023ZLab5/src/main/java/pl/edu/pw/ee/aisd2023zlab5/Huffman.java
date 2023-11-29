package pl.edu.pw.ee.aisd2023zlab5;

import pl.edu.pw.ee.aisd2023zlab5.compressor.CompressorHuff;
import pl.edu.pw.ee.aisd2023zlab5.decompressor.DecompressorHuff;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Huffman {
    private String nameOfFileGeneral;
    private final String Extension = ".bartek";

    public int huffman(String pathToRootDir, boolean compress){
        String reversedString = new StringBuilder(pathToRootDir).reverse().toString();

        StringBuilder result = new StringBuilder();
        for (char c : reversedString.toCharArray()) {
            result.insert(0, c);  // Wstawianie znaku na przód

            if (result.toString().startsWith("\\")) {
                break;
            }
        }
        nameOfFileGeneral = result.toString();


        if(compress){
            String nameWithoutExtension = nameOfFileGeneral.split("\\.")[0];

            String whereToSaveCompressed = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\"+nameWithoutExtension+Extension;
            CompressorHuff compressorHuff = new CompressorHuff();
            compressorHuff.MakeDictionary(pathToRootDir,whereToSaveCompressed);
        }else{
            String nameWithoutExtension = nameOfFileGeneral.split("\\.")[0];

            String whereToSaveDecompressed= "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfDecompression\\"+nameWithoutExtension+".txt";
            DecompressorHuff decompressorHuff = new DecompressorHuff();
            decompressorHuff.decompressFile(pathToRootDir, whereToSaveDecompressed);
        }

        return 0;
    }

}