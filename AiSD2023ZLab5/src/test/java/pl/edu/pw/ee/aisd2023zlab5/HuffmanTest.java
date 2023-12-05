package pl.edu.pw.ee.aisd2023zlab5;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class HuffmanTest {
    private final String directoryOfTestFiles = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\testFiles\\";
    private final String directoryOfResultFiles = "C:\\Users\\cylwi\\OneDrive\\Pulpit\\AiSD2023ZLab5\\resultOfTestsForHuffman\\";
    private final String compressedExtension = ".bhuff";
    private static List<String> pathsToFilesArray = new ArrayList<>();
    @Before
    public void setup() {

        removeFilesBeforeStart();
        if(pathsToFilesArray.size() != 0){
            for(int i = 0; i < pathsToFilesArray.size(); i++){
                pathsToFilesArray.remove(i);
            }
        }
    }
    @Test
    public void test_For_Not_ExistingFile_() {
        String fileName = "Nie Istnieje";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        Throwable exceptionCaught = catchThrowable(() -> {
            huff.start(data,compressedFile,true);
        });

        assertThat(exceptionCaught)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("java.io.FileNotFoundException: "+"File given to be operated doesn't exist: " + data);
    }
    @Test
    public void test_For_Not_Decompressing_WhenFile_OfTheSameName_ExistsInSameDirectory() {
        String fileName = "Huffman_Strzemiński_oddanie_2022";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        Throwable exceptionCaught = catchThrowable(() -> {
            huff.start(compressedFile,decompressedFile,false);
        });

        assertThat(exceptionCaught)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("java.io.FileNotFoundException: "+"File of the same name in this directory already exists: " + decompressedFile);
    }
    @Test
    public void test_For_Not_Compressing_WhenFile_OfTheSameName_ExistsInSameDirectory() {
        String fileName = "Huffman_Strzemiński_oddanie_2022";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        Throwable exceptionCaught = catchThrowable(() -> {
            huff.start(data,compressedFile,true);
        });

        assertThat(exceptionCaught)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("java.io.FileNotFoundException: "+"File of the same name in this directory already exists: " + compressedFile);
    }
    @Test
    public void test_For_niemanie_AvarageSize_File() {
        String fileName = "niemanie";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        try (FileInputStream inputStream1 = new FileInputStream(data);
             FileInputStream inputStream2 = new FileInputStream(decompressedFile)) {

            int byte1;
            int byte2;

            do {
                byte1 = inputStream1.read();
                byte2 = inputStream2.read();

                assertThat(byte1 == byte2);

            } while (byte1 != -1 && byte2 != -1);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void test_For_abc_SmallSize_File() {
        String fileName = "abc";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        try (FileInputStream inputStream1 = new FileInputStream(data);
             FileInputStream inputStream2 = new FileInputStream(decompressedFile)) {

            int byte1;
            int byte2;

            do {
                byte1 = inputStream1.read();
                byte2 = inputStream2.read();

                assertThat(byte1 == byte2);

            } while (byte1 != -1 && byte2 != -1);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void test_For_Pan_Tadeusz_BigSize_File() {
        String fileName = "Pan Tadeusz";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        try (FileInputStream inputStream1 = new FileInputStream(data);
             FileInputStream inputStream2 = new FileInputStream(decompressedFile)) {

            int byte1;
            int byte2;

            do {
                byte1 = inputStream1.read();
                byte2 = inputStream2.read();

                assertThat(byte1 == byte2);

            } while (byte1 != -1 && byte2 != -1);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void test_For_PolishCharsSmallSize_File() {
        String fileName = "polskie";
        String ogFileExtension = ".txt";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        try (FileInputStream inputStream1 = new FileInputStream(data);
             FileInputStream inputStream2 = new FileInputStream(decompressedFile)) {

            int byte1;
            int byte2;

            do {
                byte1 = inputStream1.read();
                byte2 = inputStream2.read();

                assertThat(byte1 == byte2);

            } while (byte1 != -1 && byte2 != -1);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void test_For_Picture_File_() {
        String fileName = "Zdjecie";
        String ogFileExtension = ".png";
        String data = directoryOfTestFiles+fileName+ogFileExtension;
        String compressedFile = directoryOfResultFiles+fileName+compressedExtension;
        String decompressedFile = directoryOfResultFiles+fileName+ogFileExtension;
        Huffman huff = new Huffman();
        huff.start(data,compressedFile,true);
        huff.start(compressedFile,decompressedFile,false);

        pathsToFilesArray.add(compressedFile);
        pathsToFilesArray.add(decompressedFile);

        try (FileInputStream inputStream1 = new FileInputStream(data);
             FileInputStream inputStream2 = new FileInputStream(decompressedFile)) {

            int byte1;
            int byte2;

            do {
                byte1 = inputStream1.read();
                byte2 = inputStream2.read();

                assertThat(byte1 == byte2);

            } while (byte1 != -1 && byte2 != -1);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void removeFilesBeforeStart() {
        for(int i = 0; i < pathsToFilesArray.size(); i++) {
            File f = new File(pathsToFilesArray.get(i));
            f.delete();
        }
    }

}
