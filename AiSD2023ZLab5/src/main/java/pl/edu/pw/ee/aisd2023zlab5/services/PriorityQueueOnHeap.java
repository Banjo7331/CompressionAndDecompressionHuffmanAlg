package pl.edu.pw.ee.aisd2023zlab5.services;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.interfaces.PriorityHeap;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueueOnHeap implements PriorityHeap {
    private List<HuffmanTreeNode> heap = new ArrayList<>();
    @Override
    public void add(HuffmanTreeNode node) {
        heap.add(node);
        int idx = heap.size() - 1;
        while (idx > 0) {

            int parentIndex = (idx - 1) / 2;
            if (heap.get(idx).compareTo(heap.get(parentIndex)) <= 0) {
                break;
            }
            swap(idx, parentIndex);
            idx = parentIndex;
        }
    }
    @Override
    public HuffmanTreeNode pull() {
        if (isEmpty()) {
            throw new RuntimeException("Empty!!");
        }
        HuffmanTreeNode root = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);

        heapify(0);
        return root;
    }

    private void heapify(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int largest = index;

        if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(largest)) > 0) {
            largest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(largest)) > 0) {
            largest = rightChild;
        }

        if (largest != index) {
            swap(index, largest);
            heapify(largest);
        }
    }

    private void swap(int i, int j) {
        HuffmanTreeNode temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    @Override
    public int size() {
        return heap.size();
    }
    @Override

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}

