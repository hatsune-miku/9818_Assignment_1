//
// Author: Zhen Guan
// Stu#:   202191382
// Email:  zguan@mun.ca
//
// This file was prepared by Zhen Guan.
// It was completed by me alone.
//

package set.implementation;

import set.SetI;

import java.util.*;

/**
 * An integer set impl with TreeSet-like performance.
 *
 * Values in this set will be keep sorted just like a
 * flat binary tree.
 *
 * I am not writing a HashSet because every operation in
 * this assignment are required to return a NEW set,
 * this is not that compatible with a HashSet.
 */
public class Set implements SetI {
    /**
     * The count of valid elements.
     */
    protected int count;

    /**
     * The actual allocated units of memory of int.
     */
    protected final int capacity;

    /**
     * The array representing the data of a set
     * which is kept sorted to provide better
     * query performance.
     */
    protected final int[] arr;

    /**
     * set { first, first+1, first+2, ..., first+count-1 }
     *
     * Avg time complexity: O(n)
     *
     * @param first The value of first element.
     * @param count The count of elements.
     */
    public Set(int first, int count) {
        this.count = count;
        this.capacity = count;
        this.arr = new int[count];
        for (int i = 0; i < count; ++i) {
            this.arr[i] = first + i;
        }
    }

    /**
     * Create a set from a sorted int array.
     *
     * Warning: This function will not check if the array is well sorted.
     * Passing in unsorted arrays will ruin the set!
     *
     * Avg time complexity: O(1)
     *
     * @param sortedArray The sorted array.
     * @param newSize The new size of set.
     *                The array will be cropped according to this.
     */
    protected Set(int[] sortedArray, int newSize) {
        this.count = newSize;
        this.capacity = sortedArray.length;
        this.arr = sortedArray;
    }

    /**
     * Create a set from another SetI implementation.
     *
     * Avg time complexity: O(n + nlogn)
     *
     * @param anotherImpl Another set.
     * @return Converted set.
     */
    public static Set fromAnotherImpl(SetI anotherImpl) {
        int[] anotherArr = Set.intArrayFromIterable(anotherImpl);
        Arrays.sort(anotherArr);
        return new Set(anotherArr, anotherArr.length);
    }

    /**
     * Check if the set contain `x`.
     *
     * Avg time complexity: O(logn)
     *
     * @param x The element whose existence to be examined.
     * @return True if the set contains `x`, otherwise False.
     */
    @Override
    public boolean contains(int x) {
        return binarySearch(x) != -1;
    }

    /**
     * Find out the location of given element in the internal array.
     *
     * Avg time complexity: O(logn)
     *
     * This should be private because a set is not supposed to
     * have indices.
     *
     * @param x Element to look for.
     * @return Subscript in array. -1 if not found.
     */
    protected int binarySearch(int x) {
        if (arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = count - 1;

        while (left <= right) {
            int mid = (right + left) >>> 1;
            int val = arr[mid];

            if (val < x) {
                left = mid + 1;
            } else if (val > x) {
                right = mid - 1;
            }
            else {
                return mid;
            }
        }

        return -1;
    }

    /**
     * Get the set's size.
     *
     * @return The size.
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * The union of set.
     *
     * Avg time complexity (Same impl): O(n + mlogm)
     * Avg time complexity (other impl): O(n + m + nlogn + mlogm)
     *
     * @param other Another set.
     * @return A new set including all elements in `this`
     * and elements from `other` that `this` does not have.
     */
    @Override
    public SetI union(SetI other) {
        Set o;

        if (!(other instanceof Set)) {
            // Different Set impl. Convertion needed.
            // Will impact performance.
            o = Set.fromAnotherImpl(other);
        }
        else {
            o = (Set) other;
        }

        int[] newArr = new int[count + o.count];
        int i = 0, j = 0, k = 0;

        // Merge sorted arrays.
        while (i < count && j < o.count) {
            // Skip duplicated elements.
            if (contains(o.arr[j])) {
                ++j;
            }
            else if (arr[i] < o.arr[j]) {
                newArr[k++] = arr[i++];
            }
            else {
                newArr[k++] = o.arr[j++];
            }
        }

        while (i < count) {
            newArr[k++] = arr[i++];
        }

        while (j < o.count) {
            // Skip duplicated elements.
            if (!contains(o.arr[j])) {
                newArr[k++] = o.arr[j];
            }
            ++j;
        }

        return new Set(newArr, k);
    }

    /**
     * The intersection of set.
     *
     * Avg time complexity (Shorter set is same impl): O(mlogn)
     * Avg time complexity (Shorter set is other impl): Unknown
     *
     * @param other Another set.
     * @return A new set including all elements that were
     * included in both `this` and `other`.
     */
    @Override
    public SetI intersection(SetI other) {
        int[] newArr;
        int i = 0;
        SetI shorter, longer;

        // Begin with a shorter one.
        if (count > other.size()) {
            shorter = other;
            longer = this;
            newArr = new int[other.size()];
        }
        else {
            shorter = this;
            longer = other;
            newArr = new int[count];
        }

        // For each element I have,
        for (Integer e : shorter) {
            // If you also have it...
            if (longer.contains(e)) {
                newArr[i++] = e;
            }
        }

        return new Set(newArr, i);
    }

    /**
     * The subtraction of set.
     *
     * Avg time complexity (Same impl): O(nlogm)
     * Avg time complexity (other impl): Unknown
     *
     * @param other Another set.
     * @return A new set including all elements included
     * in `this` that `other` does not.
     */
    @Override
    public SetI subtract(SetI other) {
        int[] newArr = new int[count];
        int i = 0;

        // For each element I have,
        for (Integer e : this) {
            // If you do not...
            if (!other.contains(e)) {
                newArr[i++] = e;
            }
        }

        return new Set(newArr, i);
    }

    /**
     * Check if `this` is a subset of `other`.
     *
     * Avg time complexity (Same impl): O(nlogm)
     * Avg time complexity (other impl): Unknown
     *
     * @param other Another set.
     * @return True if `this` is a subset of `other`,
     *         otherwise False.
     */
    @Override
    public boolean subset(SetI other) {
        if (count > other.size()) {
            return false;
        }

        for (Integer e : this) {
            // I have but other do not...
            if (!other.contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Create an iterator that walks all elements in the
     * set in ascend order.
     *
     * @return The iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < Set.this.count;
            }

            @Override
            public Integer next() {
                return Set.this.arr[i++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        if (count == 0) {
            return builder.append("}").toString();
        }

        for (int i = 0; i < count - 1; ++i) {
            builder.append(arr[i]).append(", ");
        }
        return builder.append(arr[count - 1]).append("}")
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        // Exactly the same one?
        if (this == o) {
            return true;
        }

        // Not SetI?
        if (!(o instanceof SetI s)) {
            return false;
        }

        Iterator<Integer> anotherIter = s.iterator();

        for (Integer e : this) {
            if (!anotherIter.hasNext()) {
                // Not in same length.
                return false;
            }
            if (!e.equals(anotherIter.next())) {
                return false;
            }
        }

        // Not in same length?
        return !anotherIter.hasNext();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    protected static int[] intArrayFromIterable(Iterable<Integer> iterable) {
        final ArrayList<Integer> list = new ArrayList<>();
        iterable.forEach(list::add);

        int[] ret = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}
