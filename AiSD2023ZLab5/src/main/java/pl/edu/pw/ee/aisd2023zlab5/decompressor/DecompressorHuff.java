package pl.edu.pw.ee.aisd2023zlab5.decompressor;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DecompressorHuff {
    public Map<Byte, String> huffmanCodes = new HashMap<>();
    HuffmanTreeNode root = null;

    private ArrayList<HuffmanTreeNode> postOrderTreeNodes = new ArrayList<>();

    private static Map<String, Byte> generateInverseHuffmanCodes(Map<Byte, String> huffmanCodes) {
        Map<String, Byte> inverseHuffmanCodes = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            inverseHuffmanCodes.put(entry.getValue(), entry.getKey());
        }
        return inverseHuffmanCodes;
    }
    private void generateCodes(HuffmanTreeNode root, String currentCode) {
        if (root.isLeaf() == true) {
            huffmanCodes.put(root.GetCharacter(), currentCode);
            return;
        }

        generateCodes(root.GetLeft(), currentCode + "0");
        generateCodes(root.GetRight(), currentCode + "1");
    }
    public void decompressFile(String inputFilePath, String outputFileName) {
        File file = new File(inputFilePath);
        long fileLength = file.length();

        try (BitReader bis = new BitReader(new BufferedInputStream(new FileInputStream(inputFilePath)), fileLength);
             FileOutputStream outputStream = new FileOutputStream(outputFileName)) {

            readDictionary(bis);
            generateCodes(root,"");


            Map<String, Byte> inverseHuffmanCodes = generateInverseHuffmanCodes(huffmanCodes);

            StringBuilder currentCode = new StringBuilder();

            int bit;
            while ((bit = bis.readBit()) != -1) {
                currentCode.append(bit);

                if (inverseHuffmanCodes.containsKey(currentCode.toString())) {
                    int character = inverseHuffmanCodes.get(currentCode.toString());
                    outputStream.write(character);
                    currentCode = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readDictionary(BitReader bis) throws IOException {

        int bit;
        root = new HuffmanTreeNode();
        root.SetLeft(null);
        root.SetRight(null);
        root.SetFrequency(0);
        root.SetByte((byte) 'T');

        readBinary (bis, root);


    }
    public void readBinary(BitReader bis, HuffmanTreeNode rootNode) throws IOException {


        int bit = bis.readBit();
        byte currentByte = 0;

        if(bit == 0){

            rootNode.SetLeft(new HuffmanTreeNode());
            rootNode.GetLeft().SetLeft(null);
            rootNode.GetLeft().SetRight(null);
            rootNode.GetLeft().SetLeaf(false);
            rootNode.GetLeft().SetByte((byte) 'T');
            rootNode.GetLeft().SetFrequency(0);


            rootNode.SetRight(new HuffmanTreeNode());
            rootNode.GetRight().SetLeft(null);
            rootNode.GetRight().SetRight(null);
            rootNode.GetRight().SetLeaf(false);
            rootNode.GetRight().SetByte((byte) 'T');
            rootNode.GetRight().SetFrequency(0);


            readBinary(bis,rootNode.GetLeft());
            readBinary(bis,rootNode.GetRight());

        }else if (bit == 1){

            for(int i = 0; i < 8; i++){
                bit = bis.readBit();
                currentByte = (byte) ((currentByte << 1) | bit);
            }

            rootNode.SetLeaf(true);
            rootNode.SetByte(currentByte);
            rootNode.SetFrequency(0);
            rootNode.SetLeft(null);
            rootNode.SetRight(null);
            postOrderTreeNodes.add(rootNode);

        }

    }

}
