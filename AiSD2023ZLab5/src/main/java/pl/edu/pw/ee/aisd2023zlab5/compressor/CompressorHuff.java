package pl.edu.pw.ee.aisd2023zlab5.compressor;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;
import pl.edu.pw.ee.aisd2023zlab5.services.interfaces.MapInterface;
import pl.edu.pw.ee.aisd2023zlab5.services.map.Node;
import pl.edu.pw.ee.aisd2023zlab5.services.map.RbtMap;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompressorHuff {
    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private RbtMap<Byte,String> huffmanCodes1 = new RbtMap<>();
    private List<Byte> bytesToAddDictionary = new ArrayList<>();

    public void compress(String pathToRootDir, String compressedPath){
        RbtMap<Byte,Byte> /*Map<Byte,Integer>*/ frequency = ReadFileForData( pathToRootDir);
        HuffmanTreeNode root = huffmanTree(frequency);

        generateCodes(root, "");

        buildDictionaryPostOrder(root, (byte) 0);
        System.out.println(bytesToAddDictionary);
        writeBitsToFile(pathToRootDir,compressedPath);

    }

    public static RbtMap<Byte,Byte>/*Map<Byte, Integer>*/ ReadFileForData(String pathToRootDir){
        Map<Byte, Integer> byteCounter = new HashMap<>();

        RbtMap<Byte,Byte> rbtMap = new RbtMap<>();;

        try (InputStream inputStream = new FileInputStream(pathToRootDir)) {

            int znak;
            while ((znak = inputStream.read()) != -1) {
                byte byteKey = (byte) znak;

                byteCounter.put(byteKey, byteCounter.getOrDefault(byteKey, 0) + 1);
                rbtMap.setValue(byteKey,byteKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for (Map.Entry<Byte, Integer> entry : byteCounter.entrySet()) {
            //System.out.println("Znak: " + entry.getKey() + ", Ilość: " + entry.getValue());
        //}
        return rbtMap;//byteCounter;
    }
    private static void traverseInOrder(Node<Byte, Byte> node,PriorityQueueOnHeap priorityQueueOnHeap) {
        if (node != null) {
            traverseInOrder(node.getLeft(),priorityQueueOnHeap);

            byte loadedByte = node.getKey();
            int freq = node.getFrequency();

            HuffmanTreeNode newNode = new HuffmanTreeNode(loadedByte, freq, true);
            priorityQueueOnHeap.add(newNode);

            traverseInOrder(node.getRight(),priorityQueueOnHeap); // Rekurencyjnie przejdź prawe poddrzewo.
        }
    }
    public HuffmanTreeNode huffmanTree(RbtMap<Byte,Byte>/*Map<Byte,Integer>*/ frequency){

        PriorityQueueOnHeap<HuffmanTreeNode> priorityQueueOnHeap = new PriorityQueueOnHeap();
        /*for (Map.Entry<Byte, Integer> entry : frequency.entrySet()) {
            byte loadedByte = entry.getKey();
            int freq = entry.getValue();

            HuffmanTreeNode newNode = new HuffmanTreeNode(loadedByte,freq,true);
            priorityQueueOnHeap.add(newNode);
        }

        */
        traverseInOrder(frequency.getTree().getRoot(), priorityQueueOnHeap);
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
            huffmanCodes1.setValue(root.GetCharacter(),currentCode);
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

            try (InputStream inputStream = new FileInputStream(inputFilePath)) {
                int byteRead;
                while ((byteRead = inputStream.read()) != -1) {
                    byte byteKey = (byte) byteRead;

                    //String code = huffmanCodes.get(byteKey);
                    String code = huffmanCodes1.getValue(byteKey);
                    for (char bit : code.toCharArray()) {
                        bos.writeBit(bit == '1' ? true : false);
                    }
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
