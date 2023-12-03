package pl.edu.pw.ee.aisd2023zlab5.servicesTests;

import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.exceptions.ElementNotFoundException;
import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;
import static pl.edu.pw.ee.aisd2023zlab5.utils.AdvancedGetters.getQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertTrue;

public class PriorityQueueOnHeapTest {
    @Test
    public void should_throw_exception_while_Getting_From_Empty_Queue() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        Throwable exceptionCaught = catchThrowable(() -> {
            pq.pull();
        });

        assertThat(exceptionCaught)
                .isInstanceOf(ElementNotFoundException.class)
                .hasMessage("Element not found");
    }
    @Test
    public void Pulling_From_PriorityQueue() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int size = 10;
        for (int i = 0; i < size; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode((byte) i,i);
            pq.add(newNode);
        }
        assertTrue(pq.size() == size);
    }
    @Test
    public void Adding_To_PriorityQueue() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int size = 10;
        for (int i = 0; i < size; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode((byte) i,i);
            pq.add(newNode);
        }
        assertTrue(pq.size() == size);
    }
    @Test
    public void Adding_To_PriorityQueueInRandomOrder_AndGetTheme() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int tabble[] = {0,5,2,8,6,1,9,7,3,4};
        for (int i = 0; i < tabble.length; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode((byte) tabble[i],tabble[i]);
            pq.add(newNode);
        }
        assertTrue(pq.size() == tabble.length);
        for (int i = 0; i < tabble.length; i++){
            pq.pull();
        }
        assertTrue(pq.isEmpty());
    }
    @Test
    public void Adding_To_PriorityQueue_1MilionElems() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int size = 1000_000;
        for (int i = 0; i < size; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode((byte) i,i);
            pq.add(newNode);
        }
        assertTrue(pq.size() == size);
        for (int i = 0; i < size; i++){
            pq.pull();
        }
        assertTrue(pq.isEmpty());
    }
    @Test
    public void testing_MinHeapProperty_For_100k_ELements() throws NoSuchFieldException, IllegalAccessException {
        PriorityQueueOnHeap<Integer> pq = new PriorityQueueOnHeap<>();

        int size = 100_000;

        for (int i = 0; i < size; i++) {
            pq.add(i);
        }
        assertTrue(pq.size() == size);
        assertTrue(isMinHeapCorrect(pq, size));
    }

    private <T extends Comparable<T>> boolean isMinHeapCorrect(PriorityQueueOnHeap<T> priorityQueue, int size) {
        T[] heap = getQueue(priorityQueue);

        for (int i = 0; i < size; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            if (leftChild < size && heap[leftChild].compareTo(heap[i]) < 0) {
                return false;
            }

            if (rightChild < size && heap[rightChild].compareTo(heap[i]) < 0) {
                return false;
            }
        }

        return true;
    }
}
