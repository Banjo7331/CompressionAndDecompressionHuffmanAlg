package pl.edu.pw.ee.aisd2023zlab5;

import pl.edu.pw.ee.aisd2023zlab5.compressor.CompressorHuff;
import pl.edu.pw.ee.aisd2023zlab5.decompressor.DecompressorHuff;
import pl.edu.pw.ee.aisd2023zlab5.exceptions.IncorrectFileToDecompress;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class Huffman {
    private String startPath;
    private String resultPath;
    private final static String EXTENSIONFORCOMPRESSEDFILES = ".bhuff";
    private String pathToStartingFileExtension="";
    private String inputExtension="";
    private String outputExtension="";

    public void start(String pathToFileOperating, String pathToResultFile, boolean compress){

        try {
            validateFile(pathToFileOperating);

            validateIfOperationNeeded(pathToResultFile);

            this.startPath = pathToFileOperating;
            this.resultPath = pathToResultFile;

            inputExtension = getExtension(startPath);
            outputExtension = getExtension(resultPath);

            if(compress){
                if(outputExtension != EXTENSIONFORCOMPRESSEDFILES){
                    resultPath = changeFileExtension(resultPath);
                }
                CompressorHuff compressorHuff = new CompressorHuff();
                compressorHuff.compress(startPath,resultPath);
            }else{
               if(!inputExtension.equals(EXTENSIONFORCOMPRESSEDFILES)){
                   throw new IncorrectFileToDecompress("Can't decompress files with this extension");
               }
               DecompressorHuff decompressorHuff = new DecompressorHuff();
               decompressorHuff.decompressFile(startPath, resultPath);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private String getExtension(String pathFile){
        StringBuilder reversedString = new StringBuilder(pathFile).reverse();
        StringBuilder result = new StringBuilder();

        for (char c : reversedString.toString().toCharArray()) {
            result.insert(0, c);

            if (result.toString().startsWith(".")) {
                break;
            }
        }
        return result.toString();
    }
    private String changeFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');

        if (lastDotIndex != -1) {
            return filePath.substring(0, lastDotIndex) + EXTENSIONFORCOMPRESSEDFILES;
        } else {
            return filePath + EXTENSIONFORCOMPRESSEDFILES;
        }
    }
    private void validateFile(String pathToFileOperating) throws FileNotFoundException {
        Path filePath = FileSystems.getDefault().getPath(pathToFileOperating);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File given to be operated doesn't exist: " + pathToFileOperating);
        }
    }
    private void validateIfOperationNeeded(String pathToResultFile) throws FileNotFoundException {
        Path filePath = FileSystems.getDefault().getPath(pathToResultFile);

        if (Files.exists(filePath)) {
            throw new FileNotFoundException("File already exist, not need to make it again: " + pathToResultFile);
        }
    }

    public String getCompressedDirectory(){
        if(!outputExtension.equals(EXTENSIONFORCOMPRESSEDFILES)){
            throw new RuntimeException("Can't find compressed FIle");
        }
        return resultPath;
    }
    public String getDecompressedDirectory(){
        return resultPath;
    }

}
