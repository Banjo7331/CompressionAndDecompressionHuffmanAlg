package pl.edu.pw.ee.aisd2023zlab5.compressor;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompressorHuff {
    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private StringBuilder dictionary = new StringBuilder();
    ArrayList<Byte> bytesToAdd = new ArrayList<>();

    public static Map<Byte, Integer> ReadFileForData(String pathToRootDir) {
        Map<Byte, Integer> licznikZnakow = new HashMap<>();

        try (InputStream inputStream = new FileInputStream(pathToRootDir)) {

            int znak;
            while ((znak = inputStream.read()) != -1) {
                byte byteKey = (byte) znak;


                licznikZnakow.put(byteKey, licznikZnakow.getOrDefault(byteKey, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Map.Entry<Byte, Integer> entry : licznikZnakow.entrySet()) {
            System.out.println("Znak: " + entry.getKey() + ", Ilość: " + entry.getValue());
        }
        return licznikZnakow;
    }
    private void generateCodes(HuffmanTreeNode root, String currentCode) {
        if (root.isLeaf() == true) {
            huffmanCodes.put(root.GetCharacter(), currentCode);
            return;
        }

        generateCodes(root.GetLeft(), currentCode + "0");
        generateCodes(root.GetRight(), currentCode + "1");
    }
    public void MakeDictionary(String pathToRootDir, String compressedPath){
        Map<Byte,Integer> frequency = ReadFileForData( pathToRootDir);
        HuffmanTreeNode root = HuffmanTree(frequency);

        generateCodes(root, "");

        buildDictionaryPostOrder(root, (byte) 0);
        System.out.println(bytesToAdd);
        writeBitsToFile(pathToRootDir,compressedPath);

        System.out.println(dictionary);

    }
    public HuffmanTreeNode HuffmanTree(Map<Byte, Integer> frequency){


        PriorityQueueOnHeap priorityQueueOnHeap = new PriorityQueueOnHeap();

        for (Map.Entry<Byte, Integer> entry : frequency.entrySet()) {
            byte key = entry.getKey();
            int value = entry.getValue();

            HuffmanTreeNode newNode = new HuffmanTreeNode();
            newNode.SetLeaf(true);
            newNode.SetByte(key);
            newNode.SetFrequency(value);
            newNode.SetLeft(null);
            newNode.SetRight(null);

            priorityQueueOnHeap.add(newNode);
        }

        HuffmanTreeNode root = null;

        while (priorityQueueOnHeap.size() > 1) {


            HuffmanTreeNode left = priorityQueueOnHeap.pull();

            HuffmanTreeNode right = priorityQueueOnHeap.pull();

            HuffmanTreeNode parent = new HuffmanTreeNode();

            parent.SetFrequency(left.GetFrequency() + right.GetFrequency());
            parent.SetByte((byte) 'T');
            parent.SetLeaf(false);

            parent.SetLeft(left);

            parent.SetRight(right);

            root = parent;

            priorityQueueOnHeap.add(parent);
        }

        return root;
    }
    // 0 to zejście na dół, 1 to wejście do leafa
    private void buildDictionaryPostOrder(HuffmanTreeNode root,Byte bit){
        if (root != null) {
            if(root.isLeaf()){
                bytesToAdd.add((byte)1);
                bytesToAdd.add(root.GetCharacter());
            }else {
                bytesToAdd.add(bit);
                buildDictionaryPostOrder(root.GetLeft(), bit);
                buildDictionaryPostOrder(root.GetRight(), bit);
            }
        }
    }

    private static final String END_FILE_MARKER = "1";

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
            for (char bit : END_FILE_MARKER.toCharArray()) {
                bos.writeBit(bit == '1' ? true : false);
            }

            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeDictionary(BitWriter bos) throws IOException {

        char[] charArray = new char[dictionary.length()];
        dictionary.getChars(0, dictionary.length(), charArray, 0);

        byte  vec = 0;

        for (byte directElem : bytesToAdd) {
            if(vec == 1){
                boolean[] bits = new boolean[8];

                for (int i = 7; i >= 0; i--) {
                    bits[i] = ((directElem >> (7 - i)) & 1) == 1;
                }

                for (int i = 0; i < 8; i++) {
                    bos.writeBit(bits[i]);
                }
                vec = 0;
                continue;
            }
            if(directElem == 0){
                bos.writeBit(false);
                vec = 0;
            }else if(directElem == 1){
                bos.writeBit(true);
                vec = 1;
            }
        }
    }


}
