package pl.edu.pw.ee.aisd2023zlab5.servicesTests;

import org.junit.Test;
import pl.edu.pw.ee.aisd2023zlab5.services.HuffmanTreeNode;
import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;

public class PriorityQueueOnHeapTest {
    @Test
    public void Adding_To_PriorityQueue_AndGettInAscendingOrder() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int size = 10;
        for (int i = 0; i < size; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode();
            newNode.SetFrequency(i);
            newNode.SetByte((byte) i);
            pq.add(newNode);
        }

        for (int i = 0; i < size; i++){
            System.out.println(pq.pull());
        }
    }
    @Test
    public void Adding_To_PriorityQueue_AndGettInRandomOrder() {
        PriorityQueueOnHeap pq = new PriorityQueueOnHeap();

        int tabble[] = {0,5,2,8,6,1,9,7,3,4};
        for (int i = 0; i < tabble.length; i++){
            HuffmanTreeNode newNode = new HuffmanTreeNode();
            newNode.SetFrequency(tabble[i]);
            newNode.SetByte((byte) tabble[i]);
            pq.add(newNode);
        }

        for (int i = 0; i < tabble.length; i++){
            System.out.println(pq.pull());
        }
    }
}
