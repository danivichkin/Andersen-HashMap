import java.util.Map;
import java.util.Random;

public class HashMap<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final double DEFAULT_LOAD_FACTOR = 0.75;

    private Node[] table;
    private double loadfactor;
    private int threshold;
    private int size;

    public HashMap() {
    }

    public V remove(Object key){
        Node<K, V> e;
        return removeNode(hash(key), key, null, false);
    }

    private V removeNode(int hash, Object key, Object o, boolean b) {
        table[indexFor(hash(key), table.length)] = null;
        return null;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false);
    }

    public V putVal(int hash, K key, V value, boolean hasPrev) {
        if ((table == null) || (table.length == 0))
            resize();
        if (key == null) {
            if (table[0].next != null)
                table[0].next = new Node(hash, key, value, null);
        } else {
            table[indexFor(hash, table.length)] = new Node(hash, key, value, null);
        }
        return null;
    }

    void resize() {
        int newCapacity = size * 2;
        Node[] newTable = new Node[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadfactor);
    }

    Node get(Object key) {
        return (key == null) ? table[0] : table[indexFor(hash(key), table.length)];
    }

    private Node getNode(int hash, Object key) {
        Node<K, V> first;
        if ((table != null) && (table.length > 0))
            return null;
    return null;
    }

    private void transfer(Node[] newTable) {
        for (Node node : table) {
            int hash = hash(node.getKey());
            indexFor(hash, newTable.length);
        }
    }

    public int indexFor(int h, int length) {
        return h & (length - 1);
    }

    public int hash(Object key) {
        Random random = new Random();
        int randomValue = random.nextInt();
        return (key == null) ? 0 : Math.abs(key.hashCode() + randomValue);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

class Node<K, V> implements Map.Entry<K, V> {

    final int hash;
    final K key;
    V value;
    Node<K, V> next;

    public Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V newValue) {
        this.value = newValue;
        return newValue;
    }
}
