package pl.edu.pw.ee.aisd2023zlab5.utils;

import pl.edu.pw.ee.aisd2023zlab5.services.PriorityQueueOnHeap;

import java.lang.reflect.Field;

public class AdvancedGetters {

    public static <T extends Comparable<T>> T[] getQueue(PriorityQueueOnHeap<T> priorityQueue) {
        try {
            Field field = PriorityQueueOnHeap.class.getDeclaredField("queue");
            field.setAccessible(true);
            return (T[]) field.get(priorityQueue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
