package pl.edu.pw.ee.aisd2023zlab5.services;

import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueueOnHeap {
    private List<HuffmanTreeNode> heap = new ArrayList<>();
    public void add(HuffmanTreeNode node) {
        heap.add(node);
        int index = heap.size() - 1;
        while (index > 0) {

            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) <= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    public HuffmanTreeNode pull() {
        if (isEmpty()) {
            throw new RuntimeException("Priority queue is empty");
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

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}

