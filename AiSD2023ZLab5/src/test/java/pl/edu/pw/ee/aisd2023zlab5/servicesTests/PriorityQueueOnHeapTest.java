package pl.edu.pw.ee.aisd2023zlab5.servicesTests;

import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;

import static org.junit.Assert.assertTrue;

public class PriorityQueueOnHeapTest {
    @Test
    public void Adding_To_PriorityQueue_AndGetInAscendingOrder() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int size = 10;
        for (int i = 0; i < size; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode((byte) i,i);
            pq.add(newNode);
        }
        assertTrue(pq.size() == size);
        for (int i = 0; i < size; i++){
            pq.pull();
        }
    }
    @Test
    public void Adding_To_PriorityQueue_AndGetInRandomOrder() {
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
    }
}
