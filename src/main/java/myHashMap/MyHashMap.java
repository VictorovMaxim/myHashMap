package myHashMap;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Моя реализация HashMap, которая работает с несколькимим потоками
 */
public class MyHashMap<K,V> implements MyMap<K,V> {
    private static final int HASH_MAP_ARRAY_SIZE = 16;

    private MyNode<K,V>[] hashMapArray = new MyNode[HASH_MAP_ARRAY_SIZE];

    ReentrantReadWriteLock locker = new ReentrantReadWriteLock();

    /**
     * Подсчет хеша
     */
    private int getHash(K key) {
        return key.hashCode();
    }

    /**
     * Подсчет индекса
     */
    private int getIndex(K key) {
        return getHash(key) & (HASH_MAP_ARRAY_SIZE - 1);
    }

    /**
     * Получение значения по ключу и известному индексу в массиве
     */
    private V getForIndexHashMapArray(K key, MyNode<K,V> node) {
        locker.readLock().lock();
        try {
            if (node == null || !node.getKey().equals(key) && node.getNextNode() == null) {
                return null;
            }
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
            return getForIndexHashMapArray(key, node.getNextNode());
        } finally {
            locker.readLock().unlock();
        }
    }

    /**
     * Добавление объекта node односвязванным списком с необходимыми проверками
     */
    private void putSinglyLinkedList(MyNode<K,V> nodeToAdd, MyNode<K,V> oldNode) {
        if (oldNode.getKey().equals(nodeToAdd.getKey())) {
            oldNode.setValue(nodeToAdd.getValue());
            return;
        }
        if (oldNode.getNextNode() == null) {
            oldNode.setNextNode(nodeToAdd);
            return;
        }
        putSinglyLinkedList(nodeToAdd, oldNode.getNextNode());
    }

    /**
     * Получение значения по ключу
     */
    @Override
    public V get(K key) {
        return getForIndexHashMapArray(key, hashMapArray[getIndex(key)]);
    }

    /**
     * Добавление ключ-значение
     */
    @Override
    public V put(K key, V value) {
        int index = getIndex(key);
        MyNode<K,V> node = new MyNode<>(getHash(key), key, value, null);
        locker.writeLock().lock();
        try {
            if (hashMapArray[index] == null) {
                hashMapArray[index] = node;
            } else {
                putSinglyLinkedList(node, hashMapArray[index]);
            }
        } finally {
            locker.writeLock().unlock();
        }
        return node.getValue();
    }

    private StringBuilder getStringBuilderForToString(MyNode<K,V> node) {
        return new StringBuilder(node.getKey().toString())
                .append("=")
                .append(node.getValue())
                .append(", ");
    }
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder("{");
        MyNode<K,V> node;
        for (int i = 0; i < hashMapArray.length; i++) {
            if ((node = hashMapArray[i]) != null) {
                toString.append(getStringBuilderForToString(node));
                while (node.getNextNode() != null) {
                    node = node.getNextNode();
                    toString.append(getStringBuilderForToString(node));
                }
            }
        } if (toString.length() > 2) {
            toString.setLength(toString.length() - 2);
        }
        toString.append("}");
        return toString.toString();
    }
}