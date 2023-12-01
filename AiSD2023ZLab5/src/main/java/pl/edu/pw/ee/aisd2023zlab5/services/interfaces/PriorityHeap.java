package pl.edu.pw.ee.aisd2023zlab5.services.interfaces;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;

public interface PriorityHeap {
    public void add(HuffmanTreeNode node);
    public HuffmanTreeNode pull();
    public int size();

    public boolean isEmpty();
}
