import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class HashMap<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final double DEFAULT_LOAD_FACTOR = 0.75;

    private Node<K, V>[] table = new Node[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);

    public HashMap() {
    }

    public V put(K key, V value) {

        if ((table == null) || (size == 0) || (size < threshold)) {
            resize();
        }

        int indexForNewValue = indexFor(hash(key));
        Node<K, V> newNode = new Node<>(hash(key), key, value, null);
        Node<K, V> currentNode = table[indexForNewValue];

        if (newNode.key == null) {
            table[0] = newNode;
            return null;
        }

        if (newNode.hash == currentNode.hash && ((newNode.value != currentNode.value) && (newNode.key != currentNode.key))) {
            for (Node<K, V> node = table[indexForNewValue]; currentNode != null; currentNode = currentNode.next) {
                if (node.hash == key.hashCode() && key == node.key) {
                    node.value = value;
                    return null;
                }
            }
        }
        table[indexForNewValue] = newNode;
        return null;
    }

    public V get(Object key) {
        int currentNodeIndex = indexFor(hash(key));
        for (Node<K, V> currentNode = table[currentNodeIndex]; currentNode != null; currentNode = currentNode.next) {
            if (currentNode.hash == key.hashCode() && currentNode.key == key)
                return currentNode.value;
        }
        return null;
    }

    public V remove(K key) {
        if ((table == null) || (size == 0))
            return null;

        int currentNodeIndex = indexFor(hash(key));
        Node currentNode = table[currentNodeIndex];
        Node prevNode = currentNode;

        while (currentNode != null) {
            Node nextNode = currentNode.next;
            if ((currentNode.hash == prevNode.hash) && (currentNode.key == key))
                if (currentNode == prevNode)
                    table[currentNodeIndex] = nextNode;
                else
                    prevNode.next = nextNode;

            prevNode = currentNode;
            currentNode = nextNode;
        }
        return null;
    }

    void resize() {
        int newCapacity = DEFAULT_INITIAL_CAPACITY * 2;
        Node[] newTable = new Node[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);
    }

    private void transfer(Node[] newTable) {
        for (Node node : table) {
            int hash = hash(node.getKey());
            indexFor(hash);
        }
    }

    public int indexFor(int h) {
        return h & (table.length - 1);
    }

    public int hash(Object key) {
        Random random = new Random();
        int randomValue = random.nextInt();
        return (key == null) ? 0 : Math.abs(key.hashCode() + randomValue);
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
