package pl.edu.pw.ee.aisd2023zlab5.services;

import pl.edu.pw.ee.aisd2023zlab5.services.interfaces.PriorityHeap;

import java.util.Arrays;
import pl.edu.pw.ee.aisd2023zlab5.exceptions.ElementNotFoundException;

public class PriorityQueueOnHeap<T extends Comparable<T>> implements PriorityHeap<T> {
    private T[] queue;
    private int size;
    private int elements;

    public PriorityQueueOnHeap() {
        this.size = 2;
        this.elements = 0;
        this.queue = (T[]) new Comparable[size];
    }
    public PriorityQueueOnHeap(int startingArraySize) {
        this.size = startingArraySize;
        this.elements = 0;
        this.queue = (T[]) new Comparable[size];
    }
    @Override
    public void add(T node) {
        resizeIfNeeded();

        queue[elements] = node;
        elements++;

        heapUp(elements - 1);
    }

    @Override
    public T pull() {
        if (isEmpty()) {
            throw new ElementNotFoundException("Element not found");
        }

        T minElement = queue[0];
        swap(0, elements - 1);
        elements--;
        heapDown(0);

        return minElement;
    }
    private void heapUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (queue[idx].compareTo(queue[parent]) >= 0) {
                break;
            }
            swap(idx, parent);
            idx = parent;
        }
    }
    private void heapDown(int idx) {
        while (true) {
            int leftChild = 2 * idx + 1;
            int rightChild = 2 * idx + 2;
            int smallest = idx;

            if (leftChild < elements && queue[leftChild].compareTo(queue[smallest]) < 0) {
                smallest = leftChild;
            }

            if (rightChild < elements && queue[rightChild].compareTo(queue[smallest]) < 0) {
                smallest = rightChild;
            }

            if (smallest != idx) {
                swap(idx, smallest);
                idx = smallest;
            } else {
                break;
            }
        }
    }
    private void swap(int i, int j) {
        T temp = queue[i];
        queue[i] = queue[j];
        queue[j] = temp;
    }
    private void resizeIfNeeded() {
        if (elements == size) {
            doubleSize();
        }
    }
    private void doubleSize() {
        size *= 2;
        queue = Arrays.copyOf(queue, size);
    }
    @Override
    public int size() {
        return elements;
    }
    @Override
    public boolean isEmpty() {
        return elements == 0;
    }
}

