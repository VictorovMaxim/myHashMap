package myHashMap;

public class MyNode<K,V> {
    private int hash;
    private K key;
    private V value;
    private MyNode<K,V> nextNode;

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public MyNode<K, V> getNextNode() {
        return nextNode;
    }

    public void setNextNode(MyNode<K, V> nextNode) {
        this.nextNode = nextNode;
    }

    public MyNode(int hash, K key, V value, MyNode<K, V> nextNode) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.nextNode = nextNode;
    }
}