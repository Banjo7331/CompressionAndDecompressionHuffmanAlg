package pl.edu.pw.ee.aisd2023zlab5.services.interfaces;

public interface MapInterface<K extends Comparable<K>, V> {

    public void setValue(K key, V value);

    public V getValue(K key);
    public void deleteMaxValue();
}
