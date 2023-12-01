package pl.edu.pw.ee.aisd2023zlab5.compressor;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CompressorHuff {
    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private List<Byte> bytesToAddDictionary = new ArrayList<>();

    public void compress(String pathToRootDir, String compressedPath){
        Map<Byte,Integer> frequency = ReadFileForData( pathToRootDir);
        HuffmanTreeNode root = huffmanTree(frequency);

        generateCodes(root, "");

        buildDictionaryPostOrder(root, (byte) 0);
        System.out.println(bytesToAddDictionary);
        writeBitsToFile(pathToRootDir,compressedPath);

    }

    public static Map<Byte, Integer> ReadFileForData(String pathToRootDir){
        Map<Byte, Integer> byteCounter = new HashMap<>();

        try (InputStream inputStream = new FileInputStream(pathToRootDir)) {

            int znak;
            while ((znak = inputStream.read()) != -1) {
                byte byteKey = (byte) znak;

                byteCounter.put(byteKey, byteCounter.getOrDefault(byteKey, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for (Map.Entry<Byte, Integer> entry : byteCounter.entrySet()) {
            //System.out.println("Znak: " + entry.getKey() + ", Ilość: " + entry.getValue());
        //}
        return byteCounter;
    }
    public HuffmanTreeNode huffmanTree(Map<Byte,Integer> frequency){

        PriorityQueueOnHeap priorityQueueOnHeap = new PriorityQueueOnHeap();

        for (Map.Entry<Byte, Integer> entry : frequency.entrySet()) {
            byte loadedByte = entry.getKey();
            int freq = entry.getValue();

            HuffmanTreeNode newNode = new HuffmanTreeNode(loadedByte,freq,true);
            priorityQueueOnHeap.add(newNode);
        }

        HuffmanTreeNode root = null;
        byte NODENOTLEAF = 'T';

        while (priorityQueueOnHeap.size() > 1) {

            HuffmanTreeNode left = priorityQueueOnHeap.pull();
            HuffmanTreeNode right = priorityQueueOnHeap.pull();

            HuffmanTreeNode parent = new HuffmanTreeNode(NODENOTLEAF,left.GetFrequency() + right.GetFrequency(),left,right);

            root = parent;
            priorityQueueOnHeap.add(parent);
        }

        return root;
    }
    private void generateCodes(HuffmanTreeNode root, String currentCode) {
        if (root.isLeaf() == true) {
            huffmanCodes.put(root.GetCharacter(), currentCode);
            return;
        }

        generateCodes(root.GetLeft(), currentCode + "0");
        generateCodes(root.GetRight(), currentCode + "1");
    }
    // 0 to zejście na dół, 1 to wejście do leafa
    private void buildDictionaryPostOrder(HuffmanTreeNode root,Byte bit){
        if (root != null) {
            if(root.isLeaf()){
                bytesToAddDictionary.add((byte)1);
                bytesToAddDictionary.add(root.GetCharacter());
            }else {
                bytesToAddDictionary.add(bit);
                buildDictionaryPostOrder(root.GetLeft(), bit);
                buildDictionaryPostOrder(root.GetRight(), bit);
            }
        }
    }

    private static final char END_FILE_MARKER = '1';

    public  void writeBitsToFile(String inputFilePath, String outputFileName) {
        try (BitWriter bos = new BitWriter(new BufferedOutputStream(new FileOutputStream(outputFileName)))) {

            writeDictionary(bos);

            byte[] inputData = Files.readAllBytes(Paths.get(inputFilePath));
            for (byte b : inputData) {
                String code = huffmanCodes.get(b);
                for (char bit : code.toCharArray()) {
                    bos.writeBit(bit == '1' ? true : false);
                }
            }

            bos.writeBit(END_FILE_MARKER == '1' ? true : false);

            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeDictionary(BitWriter bos) throws IOException {

        byte lastRodeElement = 0;

        for (byte directElem : bytesToAddDictionary) {
            if(lastRodeElement == 1){
                boolean[] bits = new boolean[8];

                for (int i = 7; i >= 0; i--) {
                    bits[i] = ((directElem >> (7 - i)) & 1) == 1;
                }

                for (int i = 0; i < 8; i++) {
                    bos.writeBit(bits[i]);
                }
                lastRodeElement = 0;
                continue;
            }
            if(directElem == 0){
                bos.writeBit(false);
                lastRodeElement = 0;
            }else if(directElem == 1){
                bos.writeBit(true);
                lastRodeElement = 1;
            }
        }
    }
}
