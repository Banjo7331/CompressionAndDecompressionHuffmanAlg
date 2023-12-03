package pl.edu.pw.ee.aisd2023zlab5.services.interfaces;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;

public interface PriorityHeap<T> {
    public void add(T node);
    public T pull();
    public int size();

    public boolean isEmpty();
}
