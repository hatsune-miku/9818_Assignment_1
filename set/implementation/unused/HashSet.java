//
// Author: Zhen Guan
// Stu#:   202191382
// Email:  zguan@mun.ca
//
// This file was prepared by Zhen Guan.
// It was completed by me alone.
//

// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.
// Unfinished! Please use `Set` instead.

package set.implementation.unused;

import set.SetI;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class HashSet<T> implements Iterable<T> {
    protected interface Entry<Tk, Tv> {
        Tk getKey();
        Tv getValue();
        void setValue(Tv value);
    }

    protected class Node<Tk, Tv> implements Entry<Tk, Tv> {
        protected Tk key;
        protected Tv value;
        protected Node<Tk, Tv> next = null;

        Node(Tk key, Tv value) {
            this.key = key;
            this.value = value;
            setNext(null);
        }

        public Node<Tk, Tv> getNext() {
            return next;
        }

        public void setNext(Node<Tk, Tv> next) {
            this.next = next;
        }

        @Override
        public void setValue(Tv value) {
            this.value = value;
        }

        @Override
        public Tk getKey() {
            return key;
        }

        @Override
        public Tv getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return this == o || (
                o instanceof Entry<?, ?> node
                    && Objects.equals(key, node.getKey())
                    && Objects.equals(value, node.getValue())
            );
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    protected Node<T, Object>[] table;

    protected final int INITIAL_CAPACITY = 16;
    protected final float CAPACITY_FACTOR = 2.0f;

    protected int count;
    protected int capacity;

    protected Object PRESENT = 1;

    HashSet() {
        count = 0;
        capacity = 0;
        table = null;
    }

    @SuppressWarnings("unchecked")
    protected void ensureCapacity(int capacity) {
        if (table == null) {
            table = new Node[INITIAL_CAPACITY];
            count = 0;
            capacity = INITIAL_CAPACITY;
            Arrays.fill(table, null);
        }

        if (this.capacity >= capacity) {
            return;
        }

        // Double the capacity.
        this.capacity *= CAPACITY_FACTOR;

        // Migrate to new table: alloc and copy.
        Node<T, Object>[] newTable = new Node[this.capacity];
        System.arraycopy(table, 0, newTable, 0, count);

        // Migrate to new table: Fill nulls.
        for (int i = count; i < this.capacity; ++i) {
            newTable[i] = null;
        }
        table = newTable;
    }

    protected int hash(Object o) {
        if (o == null) {
            return 0;
        }
        int h = o.hashCode();
        return Math.abs(h ^ (h >>> 16)) % table.length;
    }

    private void checkBound(Node<T, Object>[] ret) {
        if (ret == null || ret.length != 2) {
            throw new IndexOutOfBoundsException(
                "ret must be exactly a 2-length array."
            );
        }
    }

    // ret[0]    ret[1]
    // (null,    nonnull)  : found, no prior, head
    // (null,    null)     : not found, no hash
    // (nonnull, null)     : not found, [0] is tail
    // (nonnull, nonnull)  : found, prior, target
    protected void findPriorAndTarget(T x, Node<T, Object>[] ret) {
        checkBound(ret);

        if (table == null) {
            ret[0] = ret[1] = null;
            return;
        }

        Node<T, Object> entry = table[hash(x)];
        if (entry == null) {
            ret[0] = ret[1] = null;
            return;
        }

        if (entry.getKey().equals(x)) {
            ret[0] = null;
            ret[1] = entry;
            return;
        }

        while (entry.getNext() != null) {
            Node<T, Object> next = entry.getNext();
            if (next.getKey().equals(x)) {
                ret[0] = entry;
                ret[1] = next;
                return;
            }
            entry = entry.getNext();
        }

        ret[0] = entry;
        ret[1] = null;
    }

    protected boolean isNotFound(Node<T, Object>[] ret) {
        checkBound(ret);
        return ret[1] == null;
    }

    @SuppressWarnings("unchecked")
    protected Node<T, Object>[] getRetHandler() {
        Node<T, Object>[] ret = new Node[2];
        ret[0] = ret[1] = null;
        return ret;
    }

    public boolean contains(T x) {
        if (table == null) {
            return false;
        }

        Node<T, Object>[] ret = getRetHandler();
        findPriorAndTarget(x, ret);

        return !isNotFound(ret);
    }

    public boolean add(T x) {
        Node<T, Object>[] ret = getRetHandler();
        findPriorAndTarget(x, ret);

        if (!isNotFound(ret)) {
            return false;
        }

        ensureCapacity(++count);

        Node<T, Object> node = new Node<>(x, PRESENT);
        Node<T, Object> tail = ret[0];
        if (tail == null) {
            // Not found, no hash.
            table[hash(x)] = node;
        }
        else {
            tail.setNext(node);
        }

        return true;
    }

    public boolean remove(T x) {
        if (table == null || count == 0) {
            return false;
        }

        Node<T, Object>[] ret = getRetHandler();
        findPriorAndTarget(x, ret);

        if (isNotFound(ret)) {
            return false;
        }

        Node<T, Object> prior = ret[0];
        if (prior == null) {
            // head
            table[hash(x)] = null;
        }
        else {
            prior.setNext(null);
        }

        --count;
        return true;
    }

    public int size() {
        return count;
    }

    public HashSet<T> unioning(Iterable<T> other) {
        HashSet<T> ret = new HashSet<>();
        for (T e : this) {

        }
        for (T e : other) {
            add(e);
        }
        return ret;
    }

    public SetI intersecting(SetI other) {
        return null;
    }

    public SetI subtracting(SetI other) {
        return null;
    }

    public boolean isSubsetOf(SetI other) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T, Object> currentHead = null;
            Node<T, Object> currentNode = null;

            private boolean isCurrentNodeReachedTail() {
                return currentNode == null;
            }

            private void findNextHead() {
                for (
                    int i = currentHead == null
                        ? 0
                        : hash(currentHead.key) + 1;
                    i < count;
                    ++i
                ) {
                    if (table[i] != null) {
                        currentHead = table[i];
                        currentNode = table[i];
                        return;
                    }
                }
                currentHead = null;
            }

            Iterator<T> init() {
                findNextHead();
                return this;
            }

            @Override
            public boolean hasNext() {
                if (isCurrentNodeReachedTail()) {
                    findNextHead();
                    return currentHead != null;
                }
                return true;
            }

            @Override
            public T next() {
                T ret = currentNode.key;
                currentNode = currentHead.getNext();
                return ret;
            }
        }.init();
    }
}
