package pl.edu.pw.ee.aisd2023zlab5.decompressor;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;
import pl.edu.pw.ee.aisd2023zlab5.services.interfaces.HuffmanCoding;
import pl.edu.pw.ee.aisd2023zlab5.services.map.Node;
import pl.edu.pw.ee.aisd2023zlab5.services.map.RbtMap;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DecompressorHuff implements HuffmanCoding {
    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private RbtMap<Byte, String> huffmanCodes1 = new RbtMap<>();
    private HuffmanTreeNode root = null;

    public void decompressFile(String inputFilePath, String outputFileName) {

        File file = new File(inputFilePath);
        long fileLength = file.length();

        try (BitReader bis = new BitReader(new BufferedInputStream(new FileInputStream(inputFilePath)), fileLength);
            FileOutputStream outputStream = new FileOutputStream(outputFileName)) {

            readDictionary(bis);
            generateCodes(root,"");

            RbtMap<String,Byte> reversedKeysAndValuesMap = changeInMapKeysForValues(huffmanCodes1);

            StringBuilder currentCode = new StringBuilder();

            int bit;
            while ((bit = bis.readBit()) != -1) {
                currentCode.append(bit);

                if (reversedKeysAndValuesMap.getValue(currentCode.toString()) != null) {
                    int character = reversedKeysAndValuesMap.getValue(currentCode.toString());
                    outputStream.write(character);
                    currentCode = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final byte NODENOTLEAF = 'T';
    public void readDictionary(BitReader bis) throws IOException {

        root = new HuffmanTreeNode(NODENOTLEAF);
        readBinary (bis, root);
    }
    public void readBinary(BitReader bis, HuffmanTreeNode rootNode) throws IOException {

        int bit = bis.readBit();
        byte currentByte = 0;

        if(bit == 0){
            rootNode.SetLeft(new HuffmanTreeNode(NODENOTLEAF));
            rootNode.SetRight(new HuffmanTreeNode(NODENOTLEAF));

            readBinary(bis,rootNode.GetLeft());
            readBinary(bis,rootNode.GetRight());

        }else if (bit == 1){

            for(int i = 0; i < 8; i++){
                bit = bis.readBit();
                currentByte = (byte) ((currentByte << 1) | bit);
            }

            rootNode.SetLeaf(true);
            rootNode.SetByte(currentByte);
        }
    }
    public void generateCodes(HuffmanTreeNode root, String currentCode) {

        if (root.isLeaf() == true) {
            huffmanCodes.put(root.GetCharacter(), currentCode);
            huffmanCodes1.setValue(root.GetCharacter(), currentCode);
            return;
        }

        generateCodes(root.GetLeft(), currentCode + "0");
        generateCodes(root.GetRight(), currentCode + "1");
    }
    private RbtMap<String,Byte> changeInMapKeysForValues(RbtMap<Byte,String> huffmanCodes) {

        RbtMap<String,Byte> reversedKeysAndValues1 = new RbtMap<>();
        traverseInOrder(huffmanCodes.getTree().getRoot(),reversedKeysAndValues1);

        return reversedKeysAndValues1;
    }
    private static void traverseInOrder(Node<Byte, String> node,RbtMap<String,Byte> reversedKeysAndValues1) {
        if (node != null) {
            traverseInOrder(node.getLeft(),reversedKeysAndValues1);

            byte loadedByte = node.getKey();
            String freq = node.getValue();

            reversedKeysAndValues1.setValue(freq,loadedByte);

            traverseInOrder(node.getRight(),reversedKeysAndValues1);
        }
    }


}
