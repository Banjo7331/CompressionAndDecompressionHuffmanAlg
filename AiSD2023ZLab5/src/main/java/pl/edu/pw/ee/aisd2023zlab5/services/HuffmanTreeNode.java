package pl.edu.pw.ee.aisd2023zlab5.services;

public class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {

    private byte character;
    private int frequency;

    private boolean leaf;
    private HuffmanTreeNode left = null;
    private HuffmanTreeNode right = null;
    public HuffmanTreeNode(){}
    public HuffmanTreeNode(byte character){
        this.character = character;
        this.leaf = false;
    }
    public HuffmanTreeNode(byte character,int frequency){
        this.character = character;
        this.frequency = frequency;
        this.leaf = false;
    }
    public HuffmanTreeNode(byte character,int frequency, boolean isLeaf){
        this.character = character;
        this.frequency = frequency;
        this.leaf = isLeaf;
    }
    public HuffmanTreeNode(byte character,int frequency,HuffmanTreeNode left, HuffmanTreeNode right){
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
        this.leaf = false;
    }

    public byte GetCharacter(){
        return character;
    }
    public int GetFrequency(){
        return frequency;
    }
    public HuffmanTreeNode GetRight(){
        return right;
    }
    public HuffmanTreeNode GetLeft(){
        return left;
    }
    public boolean isLeaf(){
        return leaf;
    }
    public void SetLeaf(boolean leafOrNot){
        leaf = leafOrNot;
    }
    public void SetRight(HuffmanTreeNode newRight){
        right = newRight;
    }
    public void SetLeft(HuffmanTreeNode newLeft){
        left = newLeft;
    }
    public void SetByte(byte byteToSet){
        character = byteToSet;
    }
    public void SetFrequency(int frequencyToSet){
        frequency = frequencyToSet;
    }

    @Override
    public String toString() {
        return "Byte: "+character+" Frequency: "+frequency;
    }

    @Override
    public int compareTo(HuffmanTreeNode o) {
        return this.frequency - o.frequency;
    }


}
