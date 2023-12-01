package pl.edu.pw.ee.aisd2023zlab5;

import pl.edu.pw.ee.aisd2023zlab5.compressor.CompressorHuff;
import pl.edu.pw.ee.aisd2023zlab5.decompressor.DecompressorHuff;


public class Huffman {
    private String nameOfFileGeneral;
    private String whereToSaveCompressed;
    private String whereToSaveDecompressed;
    private final String Extension = ".bartek";
    private String ogExtension =".txt";

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
            ogExtension = "." +nameOfFileGeneral.split("\\.")[1];
            String nameWithoutExtension = nameOfFileGeneral.split("\\.")[0];

            whereToSaveCompressed = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfCompression\\"+nameWithoutExtension+Extension;
            CompressorHuff compressorHuff = new CompressorHuff();
            compressorHuff.compress(pathToRootDir,whereToSaveCompressed);
        }else{
            String nameWithoutExtension = nameOfFileGeneral.split("\\.")[0];

            whereToSaveDecompressed= "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfDecompression\\"+nameWithoutExtension+ogExtension;
            DecompressorHuff decompressorHuff = new DecompressorHuff();
            decompressorHuff.decompressFile(pathToRootDir, whereToSaveDecompressed);
        }

        return 0;
    }
    public String getCompressedDirectory(){
        return whereToSaveCompressed;
    }
    public String getDecompressedDirectory(){
        return whereToSaveDecompressed;
    }


}
