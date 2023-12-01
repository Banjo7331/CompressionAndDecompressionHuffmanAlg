package pl.edu.pw.ee.aisd2023zlab5.decompressor;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DecompressorHuff {
    private Map<Byte, String> huffmanCodes = new HashMap<>();
    private HuffmanTreeNode root = null;

    public void decompressFile(String inputFilePath, String outputFileName) {

        File file = new File(inputFilePath);
        long fileLength = file.length();

        try (BitReader bis = new BitReader(new BufferedInputStream(new FileInputStream(inputFilePath)), fileLength);
            FileOutputStream outputStream = new FileOutputStream(outputFileName)) {

            readDictionary(bis);
            generateCodes(root,"");


            Map<String, Byte> reversedKeysAndValuesMap = changeInMapKeysForValues(huffmanCodes);

            StringBuilder currentCode = new StringBuilder();

            int bit;
            while ((bit = bis.readBit()) != -1) {
                currentCode.append(bit);

                if (reversedKeysAndValuesMap.containsKey(currentCode.toString())) {
                    int character = reversedKeysAndValuesMap.get(currentCode.toString());
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
    private void generateCodes(HuffmanTreeNode root, String currentCode) {

        if (root.isLeaf() == true) {
            huffmanCodes.put(root.GetCharacter(), currentCode);
            return;
        }

        generateCodes(root.GetLeft(), currentCode + "0");
        generateCodes(root.GetRight(), currentCode + "1");
    }
    private Map<String, Byte> changeInMapKeysForValues(Map<Byte, String> huffmanCodes) {

        Map<String, Byte> reversedKeysAndValues = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            reversedKeysAndValues.put(entry.getValue(), entry.getKey());
        }
        return reversedKeysAndValues;
    }


}
